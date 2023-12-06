git.vger.kernel.org archive mirror
 help / color / mirror / Atom feed
* [RFC/PATCH] commit-graph: verify swapped zero/non-zero generation cases
@ 2023-08-08 19:15 Jeff King
  2023-08-10 16:00 ` Taylor Blau
  0 siblings, 1 reply; 30+ messages in thread
From: Jeff King @ 2023-08-08 19:15 UTC (permalink / raw)
  To: git; +Cc: Derrick Stolee

In verify_one_commit_graph(), we have code that complains when a commit
is found with a generation number of zero, and then later with a
non-zero number. It works like this:

  1. When we see an entry with generation zero, we set the
     generation_zero flag to GENERATION_ZERO_EXISTS.

  2. When we later see an entry with a non-zero generation, we complain
     if the flag is GENERATION_ZERO_EXISTS.

There's a matching GENERATION_NUMBER_EXISTS value, which in theory would
be used to find the case that we see the entries in the opposite order:

  1. When we see an entry with a non-zero generation, we set the
     generation_zero flag to GENERATION_NUMBER_EXISTS.

  2. When we later see an entry with a zero generation, we complain if
     the flag is GENERATION_NUMBER_EXISTS.

But that doesn't work; step 2 is implemented, but there is no step 1. We
never use NUMBER_EXISTS at all, and Coverity rightly complains that step
2 is dead code.

We can fix that by implementing that step 1.

Signed-off-by: Jeff King <peff@peff.net>
---
This is marked as RFC because I'm still confused about a lot of things.
For one, my explanation above about what the code is doing is mostly a
guess. It _looks_ to me like that's what the existing check is trying to
do. But if so, then why is the generation_zero flag defined outside the
loop over each object? I'd think it would be a per-object thing.

Likewise, just below this code, we check:

                if (generation_zero == GENERATION_ZERO_EXISTS)
                        continue;

Is the intent here "if this is the zero-th generation, we can skip the
rest of the loop because there are no more parents to look at"? If so,
then would it make more sense to check commit_graph_generation()
directly? I took care to preserve the existing behavior by pushing the
set of NUMBER_EXISTS into an "else", but it seems like a weird use of
the flag to me.

So I kind of wonder if there's something I'm not getting here. Coverity
is definitely right that our "step 2" is dead code (because we never set
NUMBER_EXISTS). But I'm not sure if we should be deleting it, or trying
to fix an underlying bug.

 commit-graph.c | 8 ++++++--
 1 file changed, 6 insertions(+), 2 deletions(-)

diff --git a/commit-graph.c b/commit-graph.c
index 0aa1640d15..40cd55eb15 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -2676,9 +2676,13 @@ static int verify_one_commit_graph(struct repository *r,
 				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
 					     oid_to_hex(&cur_oid));
 			generation_zero = GENERATION_ZERO_EXISTS;
-		} else if (generation_zero == GENERATION_ZERO_EXISTS)
-			graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
+		} else {
+			if (generation_zero == GENERATION_ZERO_EXISTS)
+				graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
 				     oid_to_hex(&cur_oid));
+			else
+				generation_zero = GENERATION_NUMBER_EXISTS;
+		}
 
 		if (generation_zero == GENERATION_ZERO_EXISTS)
 			continue;
-- 
2.42.0.rc0.376.g66bfc4f195

^ permalink raw reply related	[flat|nested] 30+ messages in thread
* Re: [RFC/PATCH] commit-graph: verify swapped zero/non-zero generation cases
  2023-08-08 19:15 [RFC/PATCH] commit-graph: verify swapped zero/non-zero generation cases Jeff King
@ 2023-08-10 16:00 ` Taylor Blau
  2023-08-10 17:44   ` Taylor Blau
  0 siblings, 1 reply; 30+ messages in thread
From: Taylor Blau @ 2023-08-10 16:00 UTC (permalink / raw)
  To: Jeff King; +Cc: git, Derrick Stolee

On Tue, Aug 08, 2023 at 03:15:36PM -0400, Jeff King wrote:
> This is marked as RFC because I'm still confused about a lot of things.
> For one, my explanation above about what the code is doing is mostly a
> guess. It _looks_ to me like that's what the existing check is trying to
> do. But if so, then why is the generation_zero flag defined outside the
> loop over each object? I'd think it would be a per-object thing.

I thought the same thing initially, but looking back at 1373e547f7
(commit-graph: verify generation number, 2018-06-27), I think the scope
of generation_zero is correct.

This is an artifact from when commit-graphs were written with all commit
generation numbers equal to zero. So I think the logic is something
like:

- If the commit-graph has a generation number of 0 for some commit, but
  we saw a non-zero value from any another commit, report it.

- Otherwise, if the commit-graph had a non-zero value for the commit's
  generation number, and we had previously seen a generation number of
  zero for some other commit, report it.

IOW, I think we expect to see either all zeros, or all non-zero values
in a single commit-graph's set of generation numbers.

Earlier in your message, you wrote:

> There's a matching GENERATION_NUMBER_EXISTS value, which in theory would
> be used to find the case that we see the entries in the opposite order:
>
>   1. When we see an entry with a non-zero generation, we set the
>      generation_zero flag to GENERATION_NUMBER_EXISTS.
>
>   2. When we later see an entry with a zero generation, we complain if
>      the flag is GENERATION_NUMBER_EXISTS.
>
> But that doesn't work; step 2 is implemented, but there is no step 1. We
> never use NUMBER_EXISTS at all, and Coverity rightly complains that step
> 2 is dead code.

So I think the missing part is setting GENERATION_NUMBER_EXISTS when we
have a non-zero generation number from the commit-graph, but have
generation_zero set to GENERATION_ZERO_EXISTS (IOW, we have seen at
least one commit with generation number 0).

--- 8< ---
diff --git a/commit-graph.c b/commit-graph.c
index 0aa1640d15..935bc15440 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -2676,9 +2676,11 @@ static int verify_one_commit_graph(struct repository *r,
 				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
 					     oid_to_hex(&cur_oid));
 			generation_zero = GENERATION_ZERO_EXISTS;
-		} else if (generation_zero == GENERATION_ZERO_EXISTS)
+		} else if (generation_zero == GENERATION_ZERO_EXISTS) {
 			graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
 				     oid_to_hex(&cur_oid));
+			generation_zero = GENERATION_NUMBER_EXISTS;
+		}

 		if (generation_zero == GENERATION_ZERO_EXISTS)
 			continue;
--- >8 ---

> So I kind of wonder if there's something I'm not getting here. Coverity
> is definitely right that our "step 2" is dead code (because we never set
> NUMBER_EXISTS). But I'm not sure if we should be deleting it, or trying
> to fix an underlying bug.

I think that above is correct in that we should be fixing an underlying
bug. But the fact that this isn't caught by our existing tests indicates
that there is a gap in coverage. Let me see if I can find a test case
that highlights this bug...

Thanks,
Taylor

^ permalink raw reply related	[flat|nested] 30+ messages in thread
* Re: [RFC/PATCH] commit-graph: verify swapped zero/non-zero generation cases
  2023-08-10 16:00 ` Taylor Blau
@ 2023-08-10 17:44   ` Taylor Blau
  2023-08-10 20:37     ` [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes Taylor Blau
                       ` (2 more replies)
  0 siblings, 3 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-10 17:44 UTC (permalink / raw)
  To: Jeff King; +Cc: git, Derrick Stolee

On Thu, Aug 10, 2023 at 12:00:43PM -0400, Taylor Blau wrote:
> > There's a matching GENERATION_NUMBER_EXISTS value, which in theory would
> > be used to find the case that we see the entries in the opposite order:
> >
> >   1. When we see an entry with a non-zero generation, we set the
> >      generation_zero flag to GENERATION_NUMBER_EXISTS.
> >
> >   2. When we later see an entry with a zero generation, we complain if
> >      the flag is GENERATION_NUMBER_EXISTS.
> >
> > But that doesn't work; step 2 is implemented, but there is no step 1. We
> > never use NUMBER_EXISTS at all, and Coverity rightly complains that step
> > 2 is dead code.
>
> So I think the missing part is setting GENERATION_NUMBER_EXISTS when we
> have a non-zero generation number from the commit-graph, but have
> generation_zero set to GENERATION_ZERO_EXISTS (IOW, we have seen at
> least one commit with generation number 0).
>
> --- 8< ---
> diff --git a/commit-graph.c b/commit-graph.c
> index 0aa1640d15..935bc15440 100644
> --- a/commit-graph.c
> +++ b/commit-graph.c
> @@ -2676,9 +2676,11 @@ static int verify_one_commit_graph(struct repository *r,
>  				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
>  					     oid_to_hex(&cur_oid));
>  			generation_zero = GENERATION_ZERO_EXISTS;
> -		} else if (generation_zero == GENERATION_ZERO_EXISTS)
> +		} else if (generation_zero == GENERATION_ZERO_EXISTS) {
>  			graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
>  				     oid_to_hex(&cur_oid));
> +			generation_zero = GENERATION_NUMBER_EXISTS;
> +		}
>
>  		if (generation_zero == GENERATION_ZERO_EXISTS)
>  			continue;
> --- >8 ---

OK, I investigated this a little bit more and now I think I understand
fully what's going on here.

There are a couple of things wrong with the diff that I posted above.
First, it has a logic error that we should set GENERATION_NUMBER_EXISTS
when we have a non-zero generation number from the graph, regardless of
whether or not GENERATION_ZERO_EXISTS is set (like how it is done in
your patch).

But more importantly, we'll never end up in the first arm of that
conditional as-is (the one that fires for when we see a generation
number of zero) as a consequence of 2ee11f7261 (commit-graph: return
generation from memory, 2023-03-20), which only returns non-zero
generation numbers (or GENERATION_NUMBER_INFINITY, which is also
non-zero).

I think you want something like `commit_graph_generation()` that returns
whatever is in `data->generation` regardless of whether or not it is
zero valued. You'd then want to use that function instead of calling
commit_graph_generation() directly.

> > So I kind of wonder if there's something I'm not getting here. Coverity
> > is definitely right that our "step 2" is dead code (because we never set
> > NUMBER_EXISTS). But I'm not sure if we should be deleting it, or trying
> > to fix an underlying bug.
>
> I think that above is correct in that we should be fixing an underlying
> bug. But the fact that this isn't caught by our existing tests indicates
> that there is a gap in coverage. Let me see if I can find a test case
> that highlights this bug...

Doing the above allows me to write these two tests on top of your patch,
which both pass:

--- &< ---
diff --git a/t/t5318-commit-graph.sh b/t/t5318-commit-graph.sh
index 4df76173a8..8e96471b34 100755
--- a/t/t5318-commit-graph.sh
+++ b/t/t5318-commit-graph.sh
@@ -450,14 +450,15 @@ GRAPH_BYTE_FANOUT2=$(($GRAPH_FANOUT_OFFSET + 4 * 255))
 GRAPH_OID_LOOKUP_OFFSET=$(($GRAPH_FANOUT_OFFSET + 4 * 256))
 GRAPH_BYTE_OID_LOOKUP_ORDER=$(($GRAPH_OID_LOOKUP_OFFSET + $HASH_LEN * 8))
 GRAPH_BYTE_OID_LOOKUP_MISSING=$(($GRAPH_OID_LOOKUP_OFFSET + $HASH_LEN * 4 + 10))
+GRAPH_COMMIT_DATA_WIDTH=$(($HASH_LEN + 16))
 GRAPH_COMMIT_DATA_OFFSET=$(($GRAPH_OID_LOOKUP_OFFSET + $HASH_LEN * $NUM_COMMITS))
 GRAPH_BYTE_COMMIT_TREE=$GRAPH_COMMIT_DATA_OFFSET
 GRAPH_BYTE_COMMIT_PARENT=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN))
 GRAPH_BYTE_COMMIT_EXTRA_PARENT=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 4))
 GRAPH_BYTE_COMMIT_WRONG_PARENT=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 3))
 GRAPH_BYTE_COMMIT_GENERATION=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 11))
+GRAPH_BYTE_COMMIT_GENERATION_LAST=$(($GRAPH_BYTE_COMMIT_GENERATION + $(($NUM_COMMITS - 1)) * $GRAPH_COMMIT_DATA_WIDTH))
 GRAPH_BYTE_COMMIT_DATE=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 12))
-GRAPH_COMMIT_DATA_WIDTH=$(($HASH_LEN + 16))
 GRAPH_OCTOPUS_DATA_OFFSET=$(($GRAPH_COMMIT_DATA_OFFSET + \
 			     $GRAPH_COMMIT_DATA_WIDTH * $NUM_COMMITS))
 GRAPH_BYTE_OCTOPUS=$(($GRAPH_OCTOPUS_DATA_OFFSET + 4))
@@ -596,11 +597,6 @@ test_expect_success 'detect incorrect generation number' '
 		"generation for commit"
 '

-test_expect_success 'detect incorrect generation number' '
-	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\01" \
-		"commit-graph generation for commit"
-'
-
 test_expect_success 'detect incorrect commit date' '
 	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_DATE "\01" \
 		"commit date"
@@ -622,6 +618,16 @@ test_expect_success 'detect incorrect chunk count' '
 		$GRAPH_CHUNK_LOOKUP_OFFSET
 '

+test_expect_success 'detect mixed generation numbers (non-zero to zero)' '
+	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION_LAST "\0\0\0\0" \
+		"but non-zero elsewhere"
+'
+
+test_expect_success 'detect mixed generation numbers (zero to non-zero)' '
+	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\0\0\0\0" \
+		"but zero elsewhere"
+'
+
 test_expect_success 'git fsck (checks commit-graph when config set to true)' '
 	git -C full fsck &&
 	corrupt_graph_and_verify $GRAPH_BYTE_FOOTER "\00" \
--- >8 ---

Note that we remove the duplicate "detect incorrect generation number"
test, which was originally introduced in 1373e547f7 (commit-graph:
verify generation number, 2018-06-27), but was modified in 2ee11f7261.

That test is replaced by the latter "non-zero to zero" variant.

Thanks,
Taylor

^ permalink raw reply related	[flat|nested] 30+ messages in thread
* [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes
  2023-08-10 17:44   ` Taylor Blau
@ 2023-08-10 20:37     ` Taylor Blau
  2023-08-10 20:37       ` [PATCH 1/4] commit-graph: introduce `commit_graph_generation_from_graph()` Taylor Blau
                         ` (4 more replies)
  2023-08-11 17:05     ` [PATCH v2 0/5] " Taylor Blau
  2023-08-21 21:34     ` [PATCH v3 0/4] " Taylor Blau
  2 siblings, 5 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-10 20:37 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

This series expands on a patch that Peff sent earlier in this thread to
remove a section of unreachable code that was noticed by Coverity in the
`verify_one_commit_graph()` function.

The first couple of patches addresses the main issue, which is that we
couldn't verify ancient commit-graphs written with zero'd generation
numbers. The third patch adds additional tests to ensure our coverage in
this area is complete, and the final patch is a cleanup.

Thanks as always for your review.

Jeff King (1):
  commit-graph: verify swapped zero/non-zero generation cases

Taylor Blau (3):
  commit-graph: introduce `commit_graph_generation_from_graph()`
  t/t5318-commit-graph.sh: test generation zero transitions during fsck
  commit-graph: invert negated conditional

 commit-graph.c          | 23 ++++++++++++++++++-----
 t/t5318-commit-graph.sh | 18 ++++++++++++------
 2 files changed, 30 insertions(+), 11 deletions(-)

-- 
2.42.0.rc0.29.g00abebef8e

^ permalink raw reply	[flat|nested] 30+ messages in thread
* [PATCH 1/4] commit-graph: introduce `commit_graph_generation_from_graph()`
  2023-08-10 20:37     ` [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes Taylor Blau
@ 2023-08-10 20:37       ` Taylor Blau
  2023-08-10 20:37       ` [PATCH 2/4] commit-graph: verify swapped zero/non-zero generation cases Taylor Blau
                         ` (3 subsequent siblings)
  4 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-10 20:37 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

In 2ee11f7261 (commit-graph: return generation from memory, 2023-03-20),
the `commit_graph_generation()` function stopped returning zeros when
asked to locate the generation number of a given commit.

This was done at the time to prepare for a later change which set
generation values in memory, meaning that we could no longer rely on
`graph_pos` alone to tell us whether or not to trust the generation
number returned by this function.

In 2ee11f7261, it was noted that this change only impacted very old
commit-graphs, which were written with all commits having generation
number 0. Indeed, zero is not a valid generation number, so we should
never expect to see that value outside of the aforementioned case.

The test fallout in 2ee11f7261 indicated that we were no longer able to
fsck a specific old case of commit-graph corruption, where we see a
non-zero generation number after having seen a generation number of 0
earlier.

Introduce a variant of `commit_graph_generation()` which behaves like
that function did prior to 2ee11f7261, known as
`commit_graph_generation_from_graph()`. Then use this function in the
context of `verify_one_commit_graph()`, where we only want to trust the
values from the graph.

Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 commit-graph.c          | 14 ++++++++++++--
 t/t5318-commit-graph.sh |  2 +-
 2 files changed, 13 insertions(+), 3 deletions(-)

diff --git a/commit-graph.c b/commit-graph.c
index 0aa1640d15..c68f5c6b3a 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -128,6 +128,16 @@ timestamp_t commit_graph_generation(const struct commit *c)
 	return GENERATION_NUMBER_INFINITY;
 }
 
+static timestamp_t commit_graph_generation_from_graph(const struct commit *c)
+{
+	struct commit_graph_data *data =
+		commit_graph_data_slab_peek(&commit_graph_data_slab, c);
+
+	if (!data || data->graph_pos == COMMIT_NOT_FROM_GRAPH)
+		return GENERATION_NUMBER_INFINITY;
+	return data->generation;
+}
+
 static struct commit_graph_data *commit_graph_data_at(const struct commit *c)
 {
 	unsigned int i, nth_slab;
@@ -2659,7 +2669,7 @@ static int verify_one_commit_graph(struct repository *r,
 					     oid_to_hex(&graph_parents->item->object.oid),
 					     oid_to_hex(&odb_parents->item->object.oid));
 
-			generation = commit_graph_generation(graph_parents->item);
+			generation = commit_graph_generation_from_graph(graph_parents->item);
 			if (generation > max_generation)
 				max_generation = generation;
 
@@ -2671,7 +2681,7 @@ static int verify_one_commit_graph(struct repository *r,
 			graph_report(_("commit-graph parent list for commit %s terminates early"),
 				     oid_to_hex(&cur_oid));
 
-		if (!commit_graph_generation(graph_commit)) {
+		if (!commit_graph_generation_from_graph(graph_commit)) {
 			if (generation_zero == GENERATION_NUMBER_EXISTS)
 				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
 					     oid_to_hex(&cur_oid));
diff --git a/t/t5318-commit-graph.sh b/t/t5318-commit-graph.sh
index 4df76173a8..4e70820c74 100755
--- a/t/t5318-commit-graph.sh
+++ b/t/t5318-commit-graph.sh
@@ -598,7 +598,7 @@ test_expect_success 'detect incorrect generation number' '
 
 test_expect_success 'detect incorrect generation number' '
 	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\01" \
-		"commit-graph generation for commit"
+		"but zero elsewhere"
 '
 
 test_expect_success 'detect incorrect commit date' '
-- 
2.42.0.rc0.29.g00abebef8e


^ permalink raw reply related	[flat|nested] 30+ messages in thread
* [PATCH 2/4] commit-graph: verify swapped zero/non-zero generation cases
  2023-08-10 20:37     ` [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes Taylor Blau
  2023-08-10 20:37       ` [PATCH 1/4] commit-graph: introduce `commit_graph_generation_from_graph()` Taylor Blau
@ 2023-08-10 20:37       ` Taylor Blau
  2023-08-10 21:36         ` Junio C Hamano
  2023-08-10 20:37       ` [PATCH 3/4] t/t5318-commit-graph.sh: test generation zero transitions during fsck Taylor Blau
                         ` (2 subsequent siblings)
  4 siblings, 1 reply; 30+ messages in thread
From: Taylor Blau @ 2023-08-10 20:37 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

From: Jeff King <peff@peff.net>

In verify_one_commit_graph(), we have code that complains when a commit
is found with a generation number of zero, and then later with a
non-zero number. It works like this:

  1. When we see an entry with generation zero, we set the
     generation_zero flag to GENERATION_ZERO_EXISTS.

  2. When we later see an entry with a non-zero generation, we complain
     if the flag is GENERATION_ZERO_EXISTS.

There's a matching GENERATION_NUMBER_EXISTS value, which in theory would
be used to find the case that we see the entries in the opposite order:

  1. When we see an entry with a non-zero generation, we set the
     generation_zero flag to GENERATION_NUMBER_EXISTS.

  2. When we later see an entry with a zero generation, we complain if
     the flag is GENERATION_NUMBER_EXISTS.

But that doesn't work; step 2 is implemented, but there is no step 1. We
never use NUMBER_EXISTS at all, and Coverity rightly complains that step
2 is dead code.

We can fix that by implementing that step 1.

Signed-off-by: Jeff King <peff@peff.net>
Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 commit-graph.c | 9 ++++++---
 1 file changed, 6 insertions(+), 3 deletions(-)

diff --git a/commit-graph.c b/commit-graph.c
index c68f5c6b3a..acca753ce8 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -2686,9 +2686,12 @@ static int verify_one_commit_graph(struct repository *r,
 				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
 					     oid_to_hex(&cur_oid));
 			generation_zero = GENERATION_ZERO_EXISTS;
-		} else if (generation_zero == GENERATION_ZERO_EXISTS)
-			graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
-				     oid_to_hex(&cur_oid));
+		} else {
+			if (generation_zero == GENERATION_ZERO_EXISTS)
+				graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
+					     oid_to_hex(&cur_oid));
+			generation_zero = GENERATION_NUMBER_EXISTS;
+		}
 
 		if (generation_zero == GENERATION_ZERO_EXISTS)
 			continue;
-- 
2.42.0.rc0.29.g00abebef8e


^ permalink raw reply related	[flat|nested] 30+ messages in thread
* [PATCH 3/4] t/t5318-commit-graph.sh: test generation zero transitions during fsck
  2023-08-10 20:37     ` [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes Taylor Blau
  2023-08-10 20:37       ` [PATCH 1/4] commit-graph: introduce `commit_graph_generation_from_graph()` Taylor Blau
  2023-08-10 20:37       ` [PATCH 2/4] commit-graph: verify swapped zero/non-zero generation cases Taylor Blau
@ 2023-08-10 20:37       ` Taylor Blau
  2023-08-10 20:37       ` [PATCH 4/4] commit-graph: invert negated conditional Taylor Blau
  2023-08-11 15:02       ` [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes Jeff King
  4 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-10 20:37 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

The second test called "detect incorrect generation number" asserts that
we correctly warn during an fsck when we see a non-zero generation
number after seeing a zero beforehand.

The other transition (going from non-zero to zero) was previously
untested. Test both directions, and rename the existing test to make
clear which direction it is exercising.

Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 t/t5318-commit-graph.sh | 18 ++++++++++++------
 1 file changed, 12 insertions(+), 6 deletions(-)

diff --git a/t/t5318-commit-graph.sh b/t/t5318-commit-graph.sh
index 4e70820c74..8e96471b34 100755
--- a/t/t5318-commit-graph.sh
+++ b/t/t5318-commit-graph.sh
@@ -450,14 +450,15 @@ GRAPH_BYTE_FANOUT2=$(($GRAPH_FANOUT_OFFSET + 4 * 255))
 GRAPH_OID_LOOKUP_OFFSET=$(($GRAPH_FANOUT_OFFSET + 4 * 256))
 GRAPH_BYTE_OID_LOOKUP_ORDER=$(($GRAPH_OID_LOOKUP_OFFSET + $HASH_LEN * 8))
 GRAPH_BYTE_OID_LOOKUP_MISSING=$(($GRAPH_OID_LOOKUP_OFFSET + $HASH_LEN * 4 + 10))
+GRAPH_COMMIT_DATA_WIDTH=$(($HASH_LEN + 16))
 GRAPH_COMMIT_DATA_OFFSET=$(($GRAPH_OID_LOOKUP_OFFSET + $HASH_LEN * $NUM_COMMITS))
 GRAPH_BYTE_COMMIT_TREE=$GRAPH_COMMIT_DATA_OFFSET
 GRAPH_BYTE_COMMIT_PARENT=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN))
 GRAPH_BYTE_COMMIT_EXTRA_PARENT=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 4))
 GRAPH_BYTE_COMMIT_WRONG_PARENT=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 3))
 GRAPH_BYTE_COMMIT_GENERATION=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 11))
+GRAPH_BYTE_COMMIT_GENERATION_LAST=$(($GRAPH_BYTE_COMMIT_GENERATION + $(($NUM_COMMITS - 1)) * $GRAPH_COMMIT_DATA_WIDTH))
 GRAPH_BYTE_COMMIT_DATE=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 12))
-GRAPH_COMMIT_DATA_WIDTH=$(($HASH_LEN + 16))
 GRAPH_OCTOPUS_DATA_OFFSET=$(($GRAPH_COMMIT_DATA_OFFSET + \
 			     $GRAPH_COMMIT_DATA_WIDTH * $NUM_COMMITS))
 GRAPH_BYTE_OCTOPUS=$(($GRAPH_OCTOPUS_DATA_OFFSET + 4))
@@ -596,11 +597,6 @@ test_expect_success 'detect incorrect generation number' '
 		"generation for commit"
 '
 
-test_expect_success 'detect incorrect generation number' '
-	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\01" \
-		"but zero elsewhere"
-'
-
 test_expect_success 'detect incorrect commit date' '
 	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_DATE "\01" \
 		"commit date"
@@ -622,6 +618,16 @@ test_expect_success 'detect incorrect chunk count' '
 		$GRAPH_CHUNK_LOOKUP_OFFSET
 '
 
+test_expect_success 'detect mixed generation numbers (non-zero to zero)' '
+	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION_LAST "\0\0\0\0" \
+		"but non-zero elsewhere"
+'
+
+test_expect_success 'detect mixed generation numbers (zero to non-zero)' '
+	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\0\0\0\0" \
+		"but zero elsewhere"
+'
+
 test_expect_success 'git fsck (checks commit-graph when config set to true)' '
 	git -C full fsck &&
 	corrupt_graph_and_verify $GRAPH_BYTE_FOOTER "\00" \
-- 
2.42.0.rc0.29.g00abebef8e


^ permalink raw reply related	[flat|nested] 30+ messages in thread
* [PATCH 4/4] commit-graph: invert negated conditional
  2023-08-10 20:37     ` [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes Taylor Blau
                         ` (2 preceding siblings ...)
  2023-08-10 20:37       ` [PATCH 3/4] t/t5318-commit-graph.sh: test generation zero transitions during fsck Taylor Blau
@ 2023-08-10 20:37       ` Taylor Blau
  2023-08-11 15:02       ` [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes Jeff King
  4 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-10 20:37 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

Now that we're using `commit_graph_generation_from_graph()` (which can
return a zero value) and we have a pure if/else instead of an else-if,
let's swap the arms of the if-statement.

This avoids an "if (!x) { foo(); } else { bar(); }" and replaces it with
the more readable "if (x) { bar(); } else { foo(); }".

Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 commit-graph.c | 12 ++++++------
 1 file changed, 6 insertions(+), 6 deletions(-)

diff --git a/commit-graph.c b/commit-graph.c
index acca753ce8..b2cf9ed9d5 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -2681,16 +2681,16 @@ static int verify_one_commit_graph(struct repository *r,
 			graph_report(_("commit-graph parent list for commit %s terminates early"),
 				     oid_to_hex(&cur_oid));
 
-		if (!commit_graph_generation_from_graph(graph_commit)) {
-			if (generation_zero == GENERATION_NUMBER_EXISTS)
-				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
-					     oid_to_hex(&cur_oid));
-			generation_zero = GENERATION_ZERO_EXISTS;
-		} else {
+		if (commit_graph_generation_from_graph(graph_commit)) {
 			if (generation_zero == GENERATION_ZERO_EXISTS)
 				graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
 					     oid_to_hex(&cur_oid));
 			generation_zero = GENERATION_NUMBER_EXISTS;
+		} else {
+			if (generation_zero == GENERATION_NUMBER_EXISTS)
+				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
+					     oid_to_hex(&cur_oid));
+			generation_zero = GENERATION_ZERO_EXISTS;
 		}
 
 		if (generation_zero == GENERATION_ZERO_EXISTS)
-- 
2.42.0.rc0.29.g00abebef8e

^ permalink raw reply related	[flat|nested] 30+ messages in thread
* Re: [PATCH 2/4] commit-graph: verify swapped zero/non-zero generation cases
  2023-08-10 20:37       ` [PATCH 2/4] commit-graph: verify swapped zero/non-zero generation cases Taylor Blau
@ 2023-08-10 21:36         ` Junio C Hamano
  2023-08-11 15:01           ` Jeff King
  0 siblings, 1 reply; 30+ messages in thread
From: Junio C Hamano @ 2023-08-10 21:36 UTC (permalink / raw)
  To: Taylor Blau; +Cc: git, Jeff King, Derrick Stolee

Taylor Blau <me@ttaylorr.com> writes:

> diff --git a/commit-graph.c b/commit-graph.c
> index c68f5c6b3a..acca753ce8 100644
> --- a/commit-graph.c
> +++ b/commit-graph.c
> @@ -2686,9 +2686,12 @@ static int verify_one_commit_graph(struct repository *r,
>  				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
>  					     oid_to_hex(&cur_oid));
>  			generation_zero = GENERATION_ZERO_EXISTS;
> -		} else if (generation_zero == GENERATION_ZERO_EXISTS)
> -			graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
> -				     oid_to_hex(&cur_oid));
> +		} else {
> +			if (generation_zero == GENERATION_ZERO_EXISTS)
> +				graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
> +					     oid_to_hex(&cur_oid));
> +			generation_zero = GENERATION_NUMBER_EXISTS;
> +		}

Hmph, doesn't this potentially cause us to emit the two reports
alternating, if we are unlucky enough to see a commit with 0
generation first (which will silently set gz to ZERO_EXISTS), then
another commit with non-zero generation (which will complain we saw
non-zero for the current one and earlier we saw zero elsewhere, and
then set gz to NUM_EXISTS), and then another commit with 0
generation (which will complain the other way, and set gz back again
to ZERO_EXISTS)?

I am tempted to say this gz business should be done with two bits
(seen zero bit and seen non-zero bit), and immediately after we see
both kinds, we should report once and stop making further reports,
but ...

>  		if (generation_zero == GENERATION_ZERO_EXISTS)
>  			continue;

... as I do not see what this "continue" is doing, I'd stop at
expressing my puzzlement ;-)

Thanks.


^ permalink raw reply	[flat|nested] 30+ messages in thread
* Re: [PATCH 2/4] commit-graph: verify swapped zero/non-zero generation cases
  2023-08-10 21:36         ` Junio C Hamano
@ 2023-08-11 15:01           ` Jeff King
  2023-08-11 17:08             ` Taylor Blau
  0 siblings, 1 reply; 30+ messages in thread
From: Jeff King @ 2023-08-11 15:01 UTC (permalink / raw)
  To: Junio C Hamano; +Cc: Taylor Blau, git, Derrick Stolee

On Thu, Aug 10, 2023 at 02:36:06PM -0700, Junio C Hamano wrote:

> Taylor Blau <me@ttaylorr.com> writes:
> 
> > diff --git a/commit-graph.c b/commit-graph.c
> > index c68f5c6b3a..acca753ce8 100644
> > --- a/commit-graph.c
> > +++ b/commit-graph.c
> > @@ -2686,9 +2686,12 @@ static int verify_one_commit_graph(struct repository *r,
> >  				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
> >  					     oid_to_hex(&cur_oid));
> >  			generation_zero = GENERATION_ZERO_EXISTS;
> > -		} else if (generation_zero == GENERATION_ZERO_EXISTS)
> > -			graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
> > -				     oid_to_hex(&cur_oid));
> > +		} else {
> > +			if (generation_zero == GENERATION_ZERO_EXISTS)
> > +				graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
> > +					     oid_to_hex(&cur_oid));
> > +			generation_zero = GENERATION_NUMBER_EXISTS;
> > +		}
> 
> Hmph, doesn't this potentially cause us to emit the two reports
> alternating, if we are unlucky enough to see a commit with 0
> generation first (which will silently set gz to ZERO_EXISTS), then
> another commit with non-zero generation (which will complain we saw
> non-zero for the current one and earlier we saw zero elsewhere, and
> then set gz to NUM_EXISTS), and then another commit with 0
> generation (which will complain the other way, and set gz back again
> to ZERO_EXISTS)?
> 
> I am tempted to say this gz business should be done with two bits
> (seen zero bit and seen non-zero bit), and immediately after we see
> both kinds, we should report once and stop making further reports,
> but ...

Yeah, I think you are right. It might be OK, in the sense that we would
show a different commit each time as we flip-flopped, but it's not clear
to me how valuable that is.

If the actual commit ids are not valuable, then we could just set bits
and then at the end of the loop produce one warning:

  if (seen_zero && seen_non_zero) {
	graph_report("oops, we saw both types");
  }

Certainly that would make the code less confusing to me. :) But I really
don't know if marking the individual commit is useful or not (on the
other hand, it cannot be perfect, since when we see a mismatch we do not
know if it was _this_ commit that is wrong and the previous one is
right, or if the previous one was wrong and this one was right). I guess
we could also save an example of each type and report them (i.e., make
seen_zero and seen_non_zero pointers to commits/oids).

> >  		if (generation_zero == GENERATION_ZERO_EXISTS)
> >  			continue;
> 
> ... as I do not see what this "continue" is doing, I'd stop at
> expressing my puzzlement ;-)

Yeah, I'm not sure on this bit. I had thought at first it was just
trying to avoid the rest of the loop for commits which are 0-generation.
But after Taylor's explanation that this is about whole files with
zero-generations, it makes sense that we would not do the rest of the
loop for any commit (it is already an error to have mixed zero/non-zero
entries, so the file fails verification).

In a "two bits" world, I think this just becomes:

  if (seen_zero)
	continue;

-Peff

^ permalink raw reply	[flat|nested] 30+ messages in thread
* Re: [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes
  2023-08-10 20:37     ` [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes Taylor Blau
                         ` (3 preceding siblings ...)
  2023-08-10 20:37       ` [PATCH 4/4] commit-graph: invert negated conditional Taylor Blau
@ 2023-08-11 15:02       ` Jeff King
  4 siblings, 0 replies; 30+ messages in thread
From: Jeff King @ 2023-08-11 15:02 UTC (permalink / raw)
  To: Taylor Blau; +Cc: git, Junio C Hamano, Derrick Stolee

On Thu, Aug 10, 2023 at 04:37:32PM -0400, Taylor Blau wrote:

> This series expands on a patch that Peff sent earlier in this thread to
> remove a section of unreachable code that was noticed by Coverity in the
> `verify_one_commit_graph()` function.
> 
> The first couple of patches addresses the main issue, which is that we
> couldn't verify ancient commit-graphs written with zero'd generation
> numbers. The third patch adds additional tests to ensure our coverage in
> this area is complete, and the final patch is a cleanup.

Thanks for untangling (and explaining!) some of the history here. I
think this series is a definite improvement, including that final
cleanup. But I also think that the "two bits" approach mentioned by
Junio would be better still.  IMHO the intent of the code would be more
clear, and it would avoid the flip-flopping error case.

-Peff

^ permalink raw reply	[flat|nested] 30+ messages in thread
* [PATCH v2 0/5] commit-graph: fsck zero/non-zero generation number fixes
  2023-08-10 17:44   ` Taylor Blau
  2023-08-10 20:37     ` [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes Taylor Blau
@ 2023-08-11 17:05     ` Taylor Blau
  2023-08-11 17:05       ` [PATCH v2 1/5] commit-graph: introduce `commit_graph_generation_from_graph()` Taylor Blau
                         ` (5 more replies)
  2023-08-21 21:34     ` [PATCH v3 0/4] " Taylor Blau
  2 siblings, 6 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-11 17:05 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

Here's a small reroll of a series that I sent which expanded on a patch
that Peff sent earlier in the thread to remove a section of unreachable
code that was noticed by Coverity in the `verify_one_commit_graph()`
function.

Everything is the same in the first three patches. The fourth patch is
slightly modified to (in addition to flipping the conditional) extract
the mixed zero/non-zero generation number checks out to its own
function.

The fifth patch is new, and avoids repeatedly warning about mixed
generation numbers by treating `generation_zero` as a bitfield.

Thanks as always for your review!

Jeff King (1):
  commit-graph: verify swapped zero/non-zero generation cases

Taylor Blau (4):
  commit-graph: introduce `commit_graph_generation_from_graph()`
  t/t5318-commit-graph.sh: test generation zero transitions during fsck
  commit-graph: invert negated conditional, extract to function
  commit-graph: avoid repeated mixed generation number warnings

 commit-graph.c          | 48 ++++++++++++++++++++++++++++++++---------
 t/t5318-commit-graph.sh | 32 +++++++++++++++++++++------
 2 files changed, 64 insertions(+), 16 deletions(-)

Range-diff against v1:
1:  6ea610f7d2 < -:  ---------- commit-graph: invert negated conditional
-:  ---------- > 1:  701c198e19 commit-graph: introduce `commit_graph_generation_from_graph()`
-:  ---------- > 2:  9b9483893c commit-graph: verify swapped zero/non-zero generation cases
-:  ---------- > 3:  8679db3d0e t/t5318-commit-graph.sh: test generation zero transitions during fsck
-:  ---------- > 4:  32b5d69ebe commit-graph: invert negated conditional, extract to function
-:  ---------- > 5:  b82b15ebc8 commit-graph: avoid repeated mixed generation number warnings
-- 
2.42.0.rc0.30.gb82b15ebc8

^ permalink raw reply	[flat|nested] 30+ messages in thread
* [PATCH v2 1/5] commit-graph: introduce `commit_graph_generation_from_graph()`
  2023-08-11 17:05     ` [PATCH v2 0/5] " Taylor Blau
@ 2023-08-11 17:05       ` Taylor Blau
  2023-08-11 17:05       ` [PATCH v2 2/5] commit-graph: verify swapped zero/non-zero generation cases Taylor Blau
                         ` (4 subsequent siblings)
  5 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-11 17:05 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

In 2ee11f7261 (commit-graph: return generation from memory, 2023-03-20),
the `commit_graph_generation()` function stopped returning zeros when
asked to locate the generation number of a given commit.

This was done at the time to prepare for a later change which set
generation values in memory, meaning that we could no longer rely on
`graph_pos` alone to tell us whether or not to trust the generation
number returned by this function.

In 2ee11f7261, it was noted that this change only impacted very old
commit-graphs, which were written with all commits having generation
number 0. Indeed, zero is not a valid generation number, so we should
never expect to see that value outside of the aforementioned case.

The test fallout in 2ee11f7261 indicated that we were no longer able to
fsck a specific old case of commit-graph corruption, where we see a
non-zero generation number after having seen a generation number of 0
earlier.

Introduce a variant of `commit_graph_generation()` which behaves like
that function did prior to 2ee11f7261, known as
`commit_graph_generation_from_graph()`. Then use this function in the
context of `verify_one_commit_graph()`, where we only want to trust the
values from the graph.

Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 commit-graph.c          | 14 ++++++++++++--
 t/t5318-commit-graph.sh |  2 +-
 2 files changed, 13 insertions(+), 3 deletions(-)

diff --git a/commit-graph.c b/commit-graph.c
index 0aa1640d15..c68f5c6b3a 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -128,6 +128,16 @@ timestamp_t commit_graph_generation(const struct commit *c)
 	return GENERATION_NUMBER_INFINITY;
 }
 
+static timestamp_t commit_graph_generation_from_graph(const struct commit *c)
+{
+	struct commit_graph_data *data =
+		commit_graph_data_slab_peek(&commit_graph_data_slab, c);
+
+	if (!data || data->graph_pos == COMMIT_NOT_FROM_GRAPH)
+		return GENERATION_NUMBER_INFINITY;
+	return data->generation;
+}
+
 static struct commit_graph_data *commit_graph_data_at(const struct commit *c)
 {
 	unsigned int i, nth_slab;
@@ -2659,7 +2669,7 @@ static int verify_one_commit_graph(struct repository *r,
 					     oid_to_hex(&graph_parents->item->object.oid),
 					     oid_to_hex(&odb_parents->item->object.oid));
 
-			generation = commit_graph_generation(graph_parents->item);
+			generation = commit_graph_generation_from_graph(graph_parents->item);
 			if (generation > max_generation)
 				max_generation = generation;
 
@@ -2671,7 +2681,7 @@ static int verify_one_commit_graph(struct repository *r,
 			graph_report(_("commit-graph parent list for commit %s terminates early"),
 				     oid_to_hex(&cur_oid));
 
-		if (!commit_graph_generation(graph_commit)) {
+		if (!commit_graph_generation_from_graph(graph_commit)) {
 			if (generation_zero == GENERATION_NUMBER_EXISTS)
 				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
 					     oid_to_hex(&cur_oid));
diff --git a/t/t5318-commit-graph.sh b/t/t5318-commit-graph.sh
index 4df76173a8..4e70820c74 100755
--- a/t/t5318-commit-graph.sh
+++ b/t/t5318-commit-graph.sh
@@ -598,7 +598,7 @@ test_expect_success 'detect incorrect generation number' '
 
 test_expect_success 'detect incorrect generation number' '
 	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\01" \
-		"commit-graph generation for commit"
+		"but zero elsewhere"
 '
 
 test_expect_success 'detect incorrect commit date' '
-- 
2.42.0.rc0.30.gb82b15ebc8


^ permalink raw reply related	[flat|nested] 30+ messages in thread
* [PATCH v2 2/5] commit-graph: verify swapped zero/non-zero generation cases
  2023-08-11 17:05     ` [PATCH v2 0/5] " Taylor Blau
  2023-08-11 17:05       ` [PATCH v2 1/5] commit-graph: introduce `commit_graph_generation_from_graph()` Taylor Blau
@ 2023-08-11 17:05       ` Taylor Blau
  2023-08-11 17:05       ` [PATCH v2 3/5] t/t5318-commit-graph.sh: test generation zero transitions during fsck Taylor Blau
                         ` (3 subsequent siblings)
  5 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-11 17:05 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

From: Jeff King <peff@peff.net>

In verify_one_commit_graph(), we have code that complains when a commit
is found with a generation number of zero, and then later with a
non-zero number. It works like this:

  1. When we see an entry with generation zero, we set the
     generation_zero flag to GENERATION_ZERO_EXISTS.

  2. When we later see an entry with a non-zero generation, we complain
     if the flag is GENERATION_ZERO_EXISTS.

There's a matching GENERATION_NUMBER_EXISTS value, which in theory would
be used to find the case that we see the entries in the opposite order:

  1. When we see an entry with a non-zero generation, we set the
     generation_zero flag to GENERATION_NUMBER_EXISTS.

  2. When we later see an entry with a zero generation, we complain if
     the flag is GENERATION_NUMBER_EXISTS.

But that doesn't work; step 2 is implemented, but there is no step 1. We
never use NUMBER_EXISTS at all, and Coverity rightly complains that step
2 is dead code.

We can fix that by implementing that step 1.

Signed-off-by: Jeff King <peff@peff.net>
Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 commit-graph.c | 9 ++++++---
 1 file changed, 6 insertions(+), 3 deletions(-)

diff --git a/commit-graph.c b/commit-graph.c
index c68f5c6b3a..acca753ce8 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -2686,9 +2686,12 @@ static int verify_one_commit_graph(struct repository *r,
 				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
 					     oid_to_hex(&cur_oid));
 			generation_zero = GENERATION_ZERO_EXISTS;
-		} else if (generation_zero == GENERATION_ZERO_EXISTS)
-			graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
-				     oid_to_hex(&cur_oid));
+		} else {
+			if (generation_zero == GENERATION_ZERO_EXISTS)
+				graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
+					     oid_to_hex(&cur_oid));
+			generation_zero = GENERATION_NUMBER_EXISTS;
+		}
 
 		if (generation_zero == GENERATION_ZERO_EXISTS)
 			continue;
-- 
2.42.0.rc0.30.gb82b15ebc8


^ permalink raw reply related	[flat|nested] 30+ messages in thread
* [PATCH v2 3/5] t/t5318-commit-graph.sh: test generation zero transitions during fsck
  2023-08-11 17:05     ` [PATCH v2 0/5] " Taylor Blau
  2023-08-11 17:05       ` [PATCH v2 1/5] commit-graph: introduce `commit_graph_generation_from_graph()` Taylor Blau
  2023-08-11 17:05       ` [PATCH v2 2/5] commit-graph: verify swapped zero/non-zero generation cases Taylor Blau
@ 2023-08-11 17:05       ` Taylor Blau
  2023-08-11 17:05       ` [PATCH v2 4/5] commit-graph: invert negated conditional, extract to function Taylor Blau
                         ` (2 subsequent siblings)
  5 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-11 17:05 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

The second test called "detect incorrect generation number" asserts that
we correctly warn during an fsck when we see a non-zero generation
number after seeing a zero beforehand.

The other transition (going from non-zero to zero) was previously
untested. Test both directions, and rename the existing test to make
clear which direction it is exercising.

Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 t/t5318-commit-graph.sh | 18 ++++++++++++------
 1 file changed, 12 insertions(+), 6 deletions(-)

diff --git a/t/t5318-commit-graph.sh b/t/t5318-commit-graph.sh
index 4e70820c74..8e96471b34 100755
--- a/t/t5318-commit-graph.sh
+++ b/t/t5318-commit-graph.sh
@@ -450,14 +450,15 @@ GRAPH_BYTE_FANOUT2=$(($GRAPH_FANOUT_OFFSET + 4 * 255))
 GRAPH_OID_LOOKUP_OFFSET=$(($GRAPH_FANOUT_OFFSET + 4 * 256))
 GRAPH_BYTE_OID_LOOKUP_ORDER=$(($GRAPH_OID_LOOKUP_OFFSET + $HASH_LEN * 8))
 GRAPH_BYTE_OID_LOOKUP_MISSING=$(($GRAPH_OID_LOOKUP_OFFSET + $HASH_LEN * 4 + 10))
+GRAPH_COMMIT_DATA_WIDTH=$(($HASH_LEN + 16))
 GRAPH_COMMIT_DATA_OFFSET=$(($GRAPH_OID_LOOKUP_OFFSET + $HASH_LEN * $NUM_COMMITS))
 GRAPH_BYTE_COMMIT_TREE=$GRAPH_COMMIT_DATA_OFFSET
 GRAPH_BYTE_COMMIT_PARENT=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN))
 GRAPH_BYTE_COMMIT_EXTRA_PARENT=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 4))
 GRAPH_BYTE_COMMIT_WRONG_PARENT=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 3))
 GRAPH_BYTE_COMMIT_GENERATION=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 11))
+GRAPH_BYTE_COMMIT_GENERATION_LAST=$(($GRAPH_BYTE_COMMIT_GENERATION + $(($NUM_COMMITS - 1)) * $GRAPH_COMMIT_DATA_WIDTH))
 GRAPH_BYTE_COMMIT_DATE=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 12))
-GRAPH_COMMIT_DATA_WIDTH=$(($HASH_LEN + 16))
 GRAPH_OCTOPUS_DATA_OFFSET=$(($GRAPH_COMMIT_DATA_OFFSET + \
 			     $GRAPH_COMMIT_DATA_WIDTH * $NUM_COMMITS))
 GRAPH_BYTE_OCTOPUS=$(($GRAPH_OCTOPUS_DATA_OFFSET + 4))
@@ -596,11 +597,6 @@ test_expect_success 'detect incorrect generation number' '
 		"generation for commit"
 '
 
-test_expect_success 'detect incorrect generation number' '
-	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\01" \
-		"but zero elsewhere"
-'
-
 test_expect_success 'detect incorrect commit date' '
 	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_DATE "\01" \
 		"commit date"
@@ -622,6 +618,16 @@ test_expect_success 'detect incorrect chunk count' '
 		$GRAPH_CHUNK_LOOKUP_OFFSET
 '
 
+test_expect_success 'detect mixed generation numbers (non-zero to zero)' '
+	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION_LAST "\0\0\0\0" \
+		"but non-zero elsewhere"
+'
+
+test_expect_success 'detect mixed generation numbers (zero to non-zero)' '
+	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\0\0\0\0" \
+		"but zero elsewhere"
+'
+
 test_expect_success 'git fsck (checks commit-graph when config set to true)' '
 	git -C full fsck &&
 	corrupt_graph_and_verify $GRAPH_BYTE_FOOTER "\00" \
-- 
2.42.0.rc0.30.gb82b15ebc8


^ permalink raw reply related	[flat|nested] 30+ messages in thread
* [PATCH v2 4/5] commit-graph: invert negated conditional, extract to function
  2023-08-11 17:05     ` [PATCH v2 0/5] " Taylor Blau
                         ` (2 preceding siblings ...)
  2023-08-11 17:05       ` [PATCH v2 3/5] t/t5318-commit-graph.sh: test generation zero transitions during fsck Taylor Blau
@ 2023-08-11 17:05       ` Taylor Blau
  2023-08-11 17:05       ` [PATCH v2 5/5] commit-graph: avoid repeated mixed generation number warnings Taylor Blau
  2023-08-11 17:58       ` [PATCH v2 0/5] commit-graph: fsck zero/non-zero generation number fixes Jeff King
  5 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-11 17:05 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

Now that we're using `commit_graph_generation_from_graph()` (which can
return a zero value) and we have a pure if/else instead of an else-if,
let's swap the arms of the if-statement.

This avoids an "if (!x) { foo(); } else { bar(); }" and replaces it with
the more readable "if (x) { bar(); } else { foo(); }".

Let's also move this code to verify mixed zero/non-zero generation
numbers out to its own function, and have it modify the
`generation_zero` variable through a pointer.

There is no functionality change in this patch, but note that there are
a couple of textual differences. First, the wrapping is adjusted on both
`graph_report()` calls to avoid overly-long lines. Second, we use the
OID from `graph_commit` instead of passing in `cur_oid`, since these are
verified to be the same by `verify_one_commit_graph()`.

Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 commit-graph.c | 34 +++++++++++++++++++++++-----------
 1 file changed, 23 insertions(+), 11 deletions(-)

diff --git a/commit-graph.c b/commit-graph.c
index acca753ce8..f7d9b31546 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -2568,6 +2568,27 @@ static int commit_graph_checksum_valid(struct commit_graph *g)
 	return hashfile_checksum_valid(g->data, g->data_len);
 }
 
+static void verify_mixed_generation_numbers(struct commit_graph *g,
+					    struct commit *graph_commit,
+					    int *generation_zero)
+{
+	if (commit_graph_generation_from_graph(graph_commit)) {
+		if (*generation_zero == GENERATION_ZERO_EXISTS)
+			graph_report(_("commit-graph has non-zero generation "
+				       "number for commit %s, but zero "
+				       "elsewhere"),
+				     oid_to_hex(&graph_commit->object.oid));
+		*generation_zero = GENERATION_NUMBER_EXISTS;
+	} else {
+		if (*generation_zero == GENERATION_NUMBER_EXISTS)
+			graph_report(_("commit-graph has generation number "
+				       "zero for commit %s, but non-zero "
+				       "elsewhere"),
+				     oid_to_hex(&graph_commit->object.oid));
+		*generation_zero = GENERATION_ZERO_EXISTS;
+	}
+}
+
 static int verify_one_commit_graph(struct repository *r,
 				   struct commit_graph *g,
 				   struct progress *progress,
@@ -2681,17 +2702,8 @@ static int verify_one_commit_graph(struct repository *r,
 			graph_report(_("commit-graph parent list for commit %s terminates early"),
 				     oid_to_hex(&cur_oid));
 
-		if (!commit_graph_generation_from_graph(graph_commit)) {
-			if (generation_zero == GENERATION_NUMBER_EXISTS)
-				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
-					     oid_to_hex(&cur_oid));
-			generation_zero = GENERATION_ZERO_EXISTS;
-		} else {
-			if (generation_zero == GENERATION_ZERO_EXISTS)
-				graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
-					     oid_to_hex(&cur_oid));
-			generation_zero = GENERATION_NUMBER_EXISTS;
-		}
+		verify_mixed_generation_numbers(g, graph_commit,
+						&generation_zero);
 
 		if (generation_zero == GENERATION_ZERO_EXISTS)
 			continue;
-- 
2.42.0.rc0.30.gb82b15ebc8


^ permalink raw reply related	[flat|nested] 30+ messages in thread
* [PATCH v2 5/5] commit-graph: avoid repeated mixed generation number warnings
  2023-08-11 17:05     ` [PATCH v2 0/5] " Taylor Blau
                         ` (3 preceding siblings ...)
  2023-08-11 17:05       ` [PATCH v2 4/5] commit-graph: invert negated conditional, extract to function Taylor Blau
@ 2023-08-11 17:05       ` Taylor Blau
  2023-08-11 17:58       ` [PATCH v2 0/5] commit-graph: fsck zero/non-zero generation number fixes Jeff King
  5 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-11 17:05 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

When validating that a commit-graph has either all zero, or all non-zero
generation numbers, we emit a warning on both the rising and falling
edge of transitioning between the two.

So if we are unfortunate enough to see a commit-graph which has a
repeating sequence of zero, then non-zero generation numbers, we'll
generate many warnings that contain more or less the same information.

Avoid this by treating `generation_zero` as a bit-field, and warn under
the same conditions, but only do so once.

Suggested-by: Junio C Hamano <gitster@pobox.com>
Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 commit-graph.c          | 17 ++++++++++-------
 t/t5318-commit-graph.sh | 14 ++++++++++++++
 2 files changed, 24 insertions(+), 7 deletions(-)

diff --git a/commit-graph.c b/commit-graph.c
index f7d9b31546..8d924b4509 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -2562,6 +2562,8 @@ static void graph_report(const char *fmt, ...)
 
 #define GENERATION_ZERO_EXISTS 1
 #define GENERATION_NUMBER_EXISTS 2
+#define GENERATION_NUMBER_BOTH_EXIST \
+	(GENERATION_ZERO_EXISTS | GENERATION_NUMBER_EXISTS)
 
 static int commit_graph_checksum_valid(struct commit_graph *g)
 {
@@ -2573,19 +2575,19 @@ static void verify_mixed_generation_numbers(struct commit_graph *g,
 					    int *generation_zero)
 {
 	if (commit_graph_generation_from_graph(graph_commit)) {
-		if (*generation_zero == GENERATION_ZERO_EXISTS)
+		if (*generation_zero & GENERATION_ZERO_EXISTS)
 			graph_report(_("commit-graph has non-zero generation "
 				       "number for commit %s, but zero "
 				       "elsewhere"),
 				     oid_to_hex(&graph_commit->object.oid));
-		*generation_zero = GENERATION_NUMBER_EXISTS;
+		*generation_zero |= GENERATION_NUMBER_EXISTS;
 	} else {
-		if (*generation_zero == GENERATION_NUMBER_EXISTS)
+		if (*generation_zero & GENERATION_NUMBER_EXISTS)
 			graph_report(_("commit-graph has generation number "
 				       "zero for commit %s, but non-zero "
 				       "elsewhere"),
 				     oid_to_hex(&graph_commit->object.oid));
-		*generation_zero = GENERATION_ZERO_EXISTS;
+		*generation_zero |= GENERATION_ZERO_EXISTS;
 	}
 }
 
@@ -2702,10 +2704,11 @@ static int verify_one_commit_graph(struct repository *r,
 			graph_report(_("commit-graph parent list for commit %s terminates early"),
 				     oid_to_hex(&cur_oid));
 
-		verify_mixed_generation_numbers(g, graph_commit,
-						&generation_zero);
+		if (generation_zero != GENERATION_NUMBER_BOTH_EXIST)
+			verify_mixed_generation_numbers(g, graph_commit,
+							&generation_zero);
 
-		if (generation_zero == GENERATION_ZERO_EXISTS)
+		if (generation_zero & GENERATION_ZERO_EXISTS)
 			continue;
 
 		/*
diff --git a/t/t5318-commit-graph.sh b/t/t5318-commit-graph.sh
index 8e96471b34..2626d41c94 100755
--- a/t/t5318-commit-graph.sh
+++ b/t/t5318-commit-graph.sh
@@ -628,6 +628,20 @@ test_expect_success 'detect mixed generation numbers (zero to non-zero)' '
 		"but zero elsewhere"
 '
 
+test_expect_success 'detect mixed generation numbers (flip-flop)' '
+	corrupt_graph_setup &&
+	for pos in \
+		$GRAPH_BYTE_COMMIT_GENERATION \
+		$GRAPH_BYTE_COMMIT_GENERATION_LAST
+	do
+		printf "\0\0\0\0" | dd of="full/$objdir/info/commit-graph" bs=1 \
+		seek="$pos" conv=notrunc || return 1
+	done &&
+
+	test_must_fail git -C full commit-graph verify 2>err &&
+	test 1 -eq "$(grep -c "generation number" err)"
+'
+
 test_expect_success 'git fsck (checks commit-graph when config set to true)' '
 	git -C full fsck &&
 	corrupt_graph_and_verify $GRAPH_BYTE_FOOTER "\00" \
-- 
2.42.0.rc0.30.gb82b15ebc8

^ permalink raw reply related	[flat|nested] 30+ messages in thread
* Re: [PATCH 2/4] commit-graph: verify swapped zero/non-zero generation cases
  2023-08-11 15:01           ` Jeff King
@ 2023-08-11 17:08             ` Taylor Blau
  0 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-11 17:08 UTC (permalink / raw)
  To: Jeff King; +Cc: Junio C Hamano, git, Derrick Stolee

On Fri, Aug 11, 2023 at 11:01:14AM -0400, Jeff King wrote:
> > Hmph, doesn't this potentially cause us to emit the two reports
> > alternating, if we are unlucky enough to see a commit with 0
> > generation first (which will silently set gz to ZERO_EXISTS), then
> > another commit with non-zero generation (which will complain we saw
> > non-zero for the current one and earlier we saw zero elsewhere, and
> > then set gz to NUM_EXISTS), and then another commit with 0
> > generation (which will complain the other way, and set gz back again
> > to ZERO_EXISTS)?
> >
> > I am tempted to say this gz business should be done with two bits
> > (seen zero bit and seen non-zero bit), and immediately after we see
> > both kinds, we should report once and stop making further reports,
> > but ...
>
> Yeah, I think you are right. It might be OK, in the sense that we would
> show a different commit each time as we flip-flopped, but it's not clear
> to me how valuable that is.
>
> If the actual commit ids are not valuable, then we could just set bits
> and then at the end of the loop produce one warning:
>
>   if (seen_zero && seen_non_zero) {
> 	graph_report("oops, we saw both types");
>   }

Thanks, both. I think that this is a very reasonable suggestion and an
improvement on the existing reporting. I made this change and sent the
revised series as a v2 lower down in the thread.

Thanks,
Taylor

^ permalink raw reply	[flat|nested] 30+ messages in thread
* Re: [PATCH v2 0/5] commit-graph: fsck zero/non-zero generation number fixes
  2023-08-11 17:05     ` [PATCH v2 0/5] " Taylor Blau
                         ` (4 preceding siblings ...)
  2023-08-11 17:05       ` [PATCH v2 5/5] commit-graph: avoid repeated mixed generation number warnings Taylor Blau
@ 2023-08-11 17:58       ` Jeff King
  2023-08-11 19:28         ` Junio C Hamano
  5 siblings, 1 reply; 30+ messages in thread
From: Jeff King @ 2023-08-11 17:58 UTC (permalink / raw)
  To: Taylor Blau; +Cc: git, Junio C Hamano, Derrick Stolee

On Fri, Aug 11, 2023 at 01:05:39PM -0400, Taylor Blau wrote:

> Here's a small reroll of a series that I sent which expanded on a patch
> that Peff sent earlier in the thread to remove a section of unreachable
> code that was noticed by Coverity in the `verify_one_commit_graph()`
> function.
> 
> Everything is the same in the first three patches. The fourth patch is
> slightly modified to (in addition to flipping the conditional) extract
> the mixed zero/non-zero generation number checks out to its own
> function.
> 
> The fifth patch is new, and avoids repeatedly warning about mixed
> generation numbers by treating `generation_zero` as a bitfield.

This all looks correct to me. Let me show what I thought the result
might look like, just because I think the result is a bit simpler.  We
may be hitting diminishing returns on refactoring here, though, so if
you're not wildly impressed, I'm happy enough if we go with what you've
written.

This applies on top of yours, but probably would replace patches 2, 4,
and 5 (the flip-flop case isn't even really worth testing after this,
since the message can obviously only be shown once).

 commit-graph.c          | 42 +++++++++--------------------------
 t/t5318-commit-graph.sh | 18 ++-------------
 2 files changed, 13 insertions(+), 47 deletions(-)

diff --git a/commit-graph.c b/commit-graph.c
index 8d924b4509..079bbc8598 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -2560,45 +2560,19 @@ static void graph_report(const char *fmt, ...)
 	va_end(ap);
 }
 
-#define GENERATION_ZERO_EXISTS 1
-#define GENERATION_NUMBER_EXISTS 2
-#define GENERATION_NUMBER_BOTH_EXIST \
-	(GENERATION_ZERO_EXISTS | GENERATION_NUMBER_EXISTS)
-
 static int commit_graph_checksum_valid(struct commit_graph *g)
 {
 	return hashfile_checksum_valid(g->data, g->data_len);
 }
 
-static void verify_mixed_generation_numbers(struct commit_graph *g,
-					    struct commit *graph_commit,
-					    int *generation_zero)
-{
-	if (commit_graph_generation_from_graph(graph_commit)) {
-		if (*generation_zero & GENERATION_ZERO_EXISTS)
-			graph_report(_("commit-graph has non-zero generation "
-				       "number for commit %s, but zero "
-				       "elsewhere"),
-				     oid_to_hex(&graph_commit->object.oid));
-		*generation_zero |= GENERATION_NUMBER_EXISTS;
-	} else {
-		if (*generation_zero & GENERATION_NUMBER_EXISTS)
-			graph_report(_("commit-graph has generation number "
-				       "zero for commit %s, but non-zero "
-				       "elsewhere"),
-				     oid_to_hex(&graph_commit->object.oid));
-		*generation_zero |= GENERATION_ZERO_EXISTS;
-	}
-}
-
 static int verify_one_commit_graph(struct repository *r,
 				   struct commit_graph *g,
 				   struct progress *progress,
 				   uint64_t *seen)
 {
 	uint32_t i, cur_fanout_pos = 0;
 	struct object_id prev_oid, cur_oid;
-	int generation_zero = 0;
+	struct commit *seen_gen_zero = NULL, *seen_gen_non_zero = NULL;
 
 	verify_commit_graph_error = verify_commit_graph_lite(g);
 	if (verify_commit_graph_error)
@@ -2704,11 +2678,12 @@ static int verify_one_commit_graph(struct repository *r,
 			graph_report(_("commit-graph parent list for commit %s terminates early"),
 				     oid_to_hex(&cur_oid));
 
-		if (generation_zero != GENERATION_NUMBER_BOTH_EXIST)
-			verify_mixed_generation_numbers(g, graph_commit,
-							&generation_zero);
+		if (!commit_graph_generation_from_graph(graph_commit))
+			seen_gen_zero = graph_commit;
+		else
+			seen_gen_non_zero = graph_commit;
 
-		if (generation_zero & GENERATION_ZERO_EXISTS)
+		if (seen_gen_zero)
 			continue;
 
 		/*
@@ -2734,6 +2709,11 @@ static int verify_one_commit_graph(struct repository *r,
 				     odb_commit->date);
 	}
 
+	if (seen_gen_zero && seen_gen_non_zero)
+		graph_report(_("commit-graph has both zero and non-zero generations (e.g., commits %s and %s"),
+			     oid_to_hex(&seen_gen_zero->object.oid),
+			     oid_to_hex(&seen_gen_non_zero->object.oid));
+
 	return verify_commit_graph_error;
 }
 
diff --git a/t/t5318-commit-graph.sh b/t/t5318-commit-graph.sh
index 2626d41c94..ca5e2c87ae 100755
--- a/t/t5318-commit-graph.sh
+++ b/t/t5318-commit-graph.sh
@@ -620,26 +620,12 @@ test_expect_success 'detect incorrect chunk count' '
 
 test_expect_success 'detect mixed generation numbers (non-zero to zero)' '
 	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION_LAST "\0\0\0\0" \
-		"but non-zero elsewhere"
+		"both zero and non-zero"
 '
 
 test_expect_success 'detect mixed generation numbers (zero to non-zero)' '
 	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\0\0\0\0" \
-		"but zero elsewhere"
-'
-
-test_expect_success 'detect mixed generation numbers (flip-flop)' '
-	corrupt_graph_setup &&
-	for pos in \
-		$GRAPH_BYTE_COMMIT_GENERATION \
-		$GRAPH_BYTE_COMMIT_GENERATION_LAST
-	do
-		printf "\0\0\0\0" | dd of="full/$objdir/info/commit-graph" bs=1 \
-		seek="$pos" conv=notrunc || return 1
-	done &&
-
-	test_must_fail git -C full commit-graph verify 2>err &&
-	test 1 -eq "$(grep -c "generation number" err)"
+		"both zero and non-zero"
 '
 
 test_expect_success 'git fsck (checks commit-graph when config set to true)' '

^ permalink raw reply related	[flat|nested] 30+ messages in thread
* Re: [PATCH v2 0/5] commit-graph: fsck zero/non-zero generation number fixes
  2023-08-11 17:58       ` [PATCH v2 0/5] commit-graph: fsck zero/non-zero generation number fixes Jeff King
@ 2023-08-11 19:28         ` Junio C Hamano
  2023-08-17 19:51           ` Jeff King
  0 siblings, 1 reply; 30+ messages in thread
From: Junio C Hamano @ 2023-08-11 19:28 UTC (permalink / raw)
  To: Jeff King; +Cc: Taylor Blau, git, Derrick Stolee

Jeff King <peff@peff.net> writes:

> This applies on top of yours, but probably would replace patches 2, 4,
> and 5 (the flip-flop case isn't even really worth testing after this,
> since the message can obviously only be shown once).
>
>  commit-graph.c          | 42 +++++++++--------------------------
>  t/t5318-commit-graph.sh | 18 ++-------------
>  2 files changed, 13 insertions(+), 47 deletions(-)

Quite an impressive amount of code reduction.  I obviously like it.

One very minor thing is that how much value are we getting by
reporting the object names of one example from each camp, instead of
just reporting a single bit "we have commits not counted and also
counted their generations, which is an anomaly".

Obviously it does not matter.  Even if we stopped doing so, the code
would not become much simpler.  We'd just use a word with two bits
instead of two pointers to existing in-core objects, which does not
have meaningful performance implications either way.

Thanks.

^ permalink raw reply	[flat|nested] 30+ messages in thread
* Re: [PATCH v2 0/5] commit-graph: fsck zero/non-zero generation number fixes
  2023-08-11 19:28         ` Junio C Hamano
@ 2023-08-17 19:51           ` Jeff King
  2023-08-21 21:25             ` Taylor Blau
  0 siblings, 1 reply; 30+ messages in thread
From: Jeff King @ 2023-08-17 19:51 UTC (permalink / raw)
  To: Junio C Hamano; +Cc: Taylor Blau, git, Derrick Stolee

On Fri, Aug 11, 2023 at 12:28:49PM -0700, Junio C Hamano wrote:

> Jeff King <peff@peff.net> writes:
> 
> > This applies on top of yours, but probably would replace patches 2, 4,
> > and 5 (the flip-flop case isn't even really worth testing after this,
> > since the message can obviously only be shown once).
> >
> >  commit-graph.c          | 42 +++++++++--------------------------
> >  t/t5318-commit-graph.sh | 18 ++-------------
> >  2 files changed, 13 insertions(+), 47 deletions(-)
> 
> Quite an impressive amount of code reduction.  I obviously like it.
> 
> One very minor thing is that how much value are we getting by
> reporting the object names of one example from each camp, instead of
> just reporting a single bit "we have commits not counted and also
> counted their generations, which is an anomaly".
> 
> Obviously it does not matter.  Even if we stopped doing so, the code
> would not become much simpler.  We'd just use a word with two bits
> instead of two pointers to existing in-core objects, which does not
> have meaningful performance implications either way.

Yeah, I wasn't sure if the commit names were valuable or not. Two bits
would definitely work (though I have a slight preference for two
boolean variables, just because I find the syntax easier to read).

I don't think we've heard from Taylor, but I saw his original patches
were in 'next'. I'm happy to clean up what I posted, but I'm also happy
if we just merge what's in next and move on.

-Peff

^ permalink raw reply	[flat|nested] 30+ messages in thread
* Re: [PATCH v2 0/5] commit-graph: fsck zero/non-zero generation number fixes
  2023-08-17 19:51           ` Jeff King
@ 2023-08-21 21:25             ` Taylor Blau
  0 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-21 21:25 UTC (permalink / raw)
  To: Jeff King; +Cc: Junio C Hamano, git, Derrick Stolee

On Thu, Aug 17, 2023 at 03:51:08PM -0400, Jeff King wrote:
> > One very minor thing is that how much value are we getting by
> > reporting the object names of one example from each camp, instead of
> > just reporting a single bit "we have commits not counted and also
> > counted their generations, which is an anomaly".
> >
> > Obviously it does not matter.  Even if we stopped doing so, the code
> > would not become much simpler.  We'd just use a word with two bits
> > instead of two pointers to existing in-core objects, which does not
> > have meaningful performance implications either way.
>
> Yeah, I wasn't sure if the commit names were valuable or not. Two bits
> would definitely work (though I have a slight preference for two
> boolean variables, just because I find the syntax easier to read).

I think having a single example of both a commit with zero and non-zero
generation is marginally useful. I think keeping track of two commit
pointers is more straightforward than the bit-field, and it does not
complicate things too much, so I think it is worth keeping.

> I don't think we've heard from Taylor, but I saw his original patches
> were in 'next'. I'm happy to clean up what I posted, but I'm also happy
> if we just merge what's in next and move on.

Sorry that this fell to the bottom of my queue, which I am just digging
out of now that 2.42.0 has been tagged.

I think that the clean-up you suggested is worthwhile. Let's replace
what we have in 'next' with the reroll that I'm about to submit...

Thanks,
Taylor

^ permalink raw reply	[flat|nested] 30+ messages in thread
* [PATCH v3 0/4] commit-graph: fsck zero/non-zero generation number fixes
  2023-08-10 17:44   ` Taylor Blau
  2023-08-10 20:37     ` [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes Taylor Blau
  2023-08-11 17:05     ` [PATCH v2 0/5] " Taylor Blau
@ 2023-08-21 21:34     ` Taylor Blau
  2023-08-21 21:34       ` [PATCH v3 1/4] commit-graph: introduce `commit_graph_generation_from_graph()` Taylor Blau
                         ` (4 more replies)
  2 siblings, 5 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-21 21:34 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

Here's a(nother) small reroll of a series that I sent which expanded on
a patch that Peff sent earlier in the thread to remove a section of
unreachable code that was noticed by Coverity in the
`verify_one_commit_graph()` function.

The first few patches are the same, but the fourth (now final) patch is
modified to track a single example of a commit with zero and non-zero
generation to only emit the warning once at the end of processing.

Thanks as always for your review!

Jeff King (1):
  commit-graph: verify swapped zero/non-zero generation cases

Taylor Blau (3):
  commit-graph: introduce `commit_graph_generation_from_graph()`
  t/t5318-commit-graph.sh: test generation zero transitions during fsck
  commit-graph: commit-graph: avoid repeated mixed generation number
    warnings

 commit-graph.c          | 38 ++++++++++++++++++++++++--------------
 t/t5318-commit-graph.sh | 18 ++++++++++++------
 2 files changed, 36 insertions(+), 20 deletions(-)

Range-diff against v2:
1:  a1cc22297e = 1:  c88f945a54 commit-graph: introduce `commit_graph_generation_from_graph()`
2:  38b8cd5e9f = 2:  8f8e0b6644 commit-graph: verify swapped zero/non-zero generation cases
3:  d14f3ca840 = 3:  34a505dd4b t/t5318-commit-graph.sh: test generation zero transitions during fsck
4:  e378fd6f93 < -:  ---------- commit-graph: invert negated conditional, extract to function
5:  23bcb7d270 < -:  ---------- commit-graph: avoid repeated mixed generation number warnings
-:  ---------- > 4:  52b49bb434 commit-graph: commit-graph: avoid repeated mixed generation number warnings
-- 
2.42.0.4.g52b49bb434

^ permalink raw reply	[flat|nested] 30+ messages in thread
* [PATCH v3 1/4] commit-graph: introduce `commit_graph_generation_from_graph()`
  2023-08-21 21:34     ` [PATCH v3 0/4] " Taylor Blau
@ 2023-08-21 21:34       ` Taylor Blau
  2023-08-21 21:34       ` [PATCH v3 2/4] commit-graph: verify swapped zero/non-zero generation cases Taylor Blau
                         ` (3 subsequent siblings)
  4 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-21 21:34 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

In 2ee11f7261 (commit-graph: return generation from memory, 2023-03-20),
the `commit_graph_generation()` function stopped returning zeros when
asked to locate the generation number of a given commit.

This was done at the time to prepare for a later change which set
generation values in memory, meaning that we could no longer rely on
`graph_pos` alone to tell us whether or not to trust the generation
number returned by this function.

In 2ee11f7261, it was noted that this change only impacted very old
commit-graphs, which were written with all commits having generation
number 0. Indeed, zero is not a valid generation number, so we should
never expect to see that value outside of the aforementioned case.

The test fallout in 2ee11f7261 indicated that we were no longer able to
fsck a specific old case of commit-graph corruption, where we see a
non-zero generation number after having seen a generation number of 0
earlier.

Introduce a variant of `commit_graph_generation()` which behaves like
that function did prior to 2ee11f7261, known as
`commit_graph_generation_from_graph()`. Then use this function in the
context of `verify_one_commit_graph()`, where we only want to trust the
values from the graph.

Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 commit-graph.c          | 14 ++++++++++++--
 t/t5318-commit-graph.sh |  2 +-
 2 files changed, 13 insertions(+), 3 deletions(-)

diff --git a/commit-graph.c b/commit-graph.c
index 0aa1640d15..c68f5c6b3a 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -128,6 +128,16 @@ timestamp_t commit_graph_generation(const struct commit *c)
 	return GENERATION_NUMBER_INFINITY;
 }
 
+static timestamp_t commit_graph_generation_from_graph(const struct commit *c)
+{
+	struct commit_graph_data *data =
+		commit_graph_data_slab_peek(&commit_graph_data_slab, c);
+
+	if (!data || data->graph_pos == COMMIT_NOT_FROM_GRAPH)
+		return GENERATION_NUMBER_INFINITY;
+	return data->generation;
+}
+
 static struct commit_graph_data *commit_graph_data_at(const struct commit *c)
 {
 	unsigned int i, nth_slab;
@@ -2659,7 +2669,7 @@ static int verify_one_commit_graph(struct repository *r,
 					     oid_to_hex(&graph_parents->item->object.oid),
 					     oid_to_hex(&odb_parents->item->object.oid));
 
-			generation = commit_graph_generation(graph_parents->item);
+			generation = commit_graph_generation_from_graph(graph_parents->item);
 			if (generation > max_generation)
 				max_generation = generation;
 
@@ -2671,7 +2681,7 @@ static int verify_one_commit_graph(struct repository *r,
 			graph_report(_("commit-graph parent list for commit %s terminates early"),
 				     oid_to_hex(&cur_oid));
 
-		if (!commit_graph_generation(graph_commit)) {
+		if (!commit_graph_generation_from_graph(graph_commit)) {
 			if (generation_zero == GENERATION_NUMBER_EXISTS)
 				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
 					     oid_to_hex(&cur_oid));
diff --git a/t/t5318-commit-graph.sh b/t/t5318-commit-graph.sh
index 4df76173a8..4e70820c74 100755
--- a/t/t5318-commit-graph.sh
+++ b/t/t5318-commit-graph.sh
@@ -598,7 +598,7 @@ test_expect_success 'detect incorrect generation number' '
 
 test_expect_success 'detect incorrect generation number' '
 	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\01" \
-		"commit-graph generation for commit"
+		"but zero elsewhere"
 '
 
 test_expect_success 'detect incorrect commit date' '
-- 
2.42.0.4.g52b49bb434


^ permalink raw reply related	[flat|nested] 30+ messages in thread
* [PATCH v3 2/4] commit-graph: verify swapped zero/non-zero generation cases
  2023-08-21 21:34     ` [PATCH v3 0/4] " Taylor Blau
  2023-08-21 21:34       ` [PATCH v3 1/4] commit-graph: introduce `commit_graph_generation_from_graph()` Taylor Blau
@ 2023-08-21 21:34       ` Taylor Blau
  2023-08-21 21:34       ` [PATCH v3 3/4] t/t5318-commit-graph.sh: test generation zero transitions during fsck Taylor Blau
                         ` (2 subsequent siblings)
  4 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-21 21:34 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

From: Jeff King <peff@peff.net>

In verify_one_commit_graph(), we have code that complains when a commit
is found with a generation number of zero, and then later with a
non-zero number. It works like this:

  1. When we see an entry with generation zero, we set the
     generation_zero flag to GENERATION_ZERO_EXISTS.

  2. When we later see an entry with a non-zero generation, we complain
     if the flag is GENERATION_ZERO_EXISTS.

There's a matching GENERATION_NUMBER_EXISTS value, which in theory would
be used to find the case that we see the entries in the opposite order:

  1. When we see an entry with a non-zero generation, we set the
     generation_zero flag to GENERATION_NUMBER_EXISTS.

  2. When we later see an entry with a zero generation, we complain if
     the flag is GENERATION_NUMBER_EXISTS.

But that doesn't work; step 2 is implemented, but there is no step 1. We
never use NUMBER_EXISTS at all, and Coverity rightly complains that step
2 is dead code.

We can fix that by implementing that step 1.

Signed-off-by: Jeff King <peff@peff.net>
Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 commit-graph.c | 9 ++++++---
 1 file changed, 6 insertions(+), 3 deletions(-)

diff --git a/commit-graph.c b/commit-graph.c
index c68f5c6b3a..acca753ce8 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -2686,9 +2686,12 @@ static int verify_one_commit_graph(struct repository *r,
 				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
 					     oid_to_hex(&cur_oid));
 			generation_zero = GENERATION_ZERO_EXISTS;
-		} else if (generation_zero == GENERATION_ZERO_EXISTS)
-			graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
-				     oid_to_hex(&cur_oid));
+		} else {
+			if (generation_zero == GENERATION_ZERO_EXISTS)
+				graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
+					     oid_to_hex(&cur_oid));
+			generation_zero = GENERATION_NUMBER_EXISTS;
+		}
 
 		if (generation_zero == GENERATION_ZERO_EXISTS)
 			continue;
-- 
2.42.0.4.g52b49bb434


^ permalink raw reply related	[flat|nested] 30+ messages in thread
* [PATCH v3 3/4] t/t5318-commit-graph.sh: test generation zero transitions during fsck
  2023-08-21 21:34     ` [PATCH v3 0/4] " Taylor Blau
  2023-08-21 21:34       ` [PATCH v3 1/4] commit-graph: introduce `commit_graph_generation_from_graph()` Taylor Blau
  2023-08-21 21:34       ` [PATCH v3 2/4] commit-graph: verify swapped zero/non-zero generation cases Taylor Blau
@ 2023-08-21 21:34       ` Taylor Blau
  2023-08-21 21:34       ` [PATCH v3 4/4] commit-graph: commit-graph: avoid repeated mixed generation number warnings Taylor Blau
  2023-08-21 21:55       ` [PATCH v3 0/4] commit-graph: fsck zero/non-zero generation number fixes Jeff King
  4 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-21 21:34 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

The second test called "detect incorrect generation number" asserts that
we correctly warn during an fsck when we see a non-zero generation
number after seeing a zero beforehand.

The other transition (going from non-zero to zero) was previously
untested. Test both directions, and rename the existing test to make
clear which direction it is exercising.

Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 t/t5318-commit-graph.sh | 18 ++++++++++++------
 1 file changed, 12 insertions(+), 6 deletions(-)

diff --git a/t/t5318-commit-graph.sh b/t/t5318-commit-graph.sh
index 4e70820c74..8e96471b34 100755
--- a/t/t5318-commit-graph.sh
+++ b/t/t5318-commit-graph.sh
@@ -450,14 +450,15 @@ GRAPH_BYTE_FANOUT2=$(($GRAPH_FANOUT_OFFSET + 4 * 255))
 GRAPH_OID_LOOKUP_OFFSET=$(($GRAPH_FANOUT_OFFSET + 4 * 256))
 GRAPH_BYTE_OID_LOOKUP_ORDER=$(($GRAPH_OID_LOOKUP_OFFSET + $HASH_LEN * 8))
 GRAPH_BYTE_OID_LOOKUP_MISSING=$(($GRAPH_OID_LOOKUP_OFFSET + $HASH_LEN * 4 + 10))
+GRAPH_COMMIT_DATA_WIDTH=$(($HASH_LEN + 16))
 GRAPH_COMMIT_DATA_OFFSET=$(($GRAPH_OID_LOOKUP_OFFSET + $HASH_LEN * $NUM_COMMITS))
 GRAPH_BYTE_COMMIT_TREE=$GRAPH_COMMIT_DATA_OFFSET
 GRAPH_BYTE_COMMIT_PARENT=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN))
 GRAPH_BYTE_COMMIT_EXTRA_PARENT=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 4))
 GRAPH_BYTE_COMMIT_WRONG_PARENT=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 3))
 GRAPH_BYTE_COMMIT_GENERATION=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 11))
+GRAPH_BYTE_COMMIT_GENERATION_LAST=$(($GRAPH_BYTE_COMMIT_GENERATION + $(($NUM_COMMITS - 1)) * $GRAPH_COMMIT_DATA_WIDTH))
 GRAPH_BYTE_COMMIT_DATE=$(($GRAPH_COMMIT_DATA_OFFSET + $HASH_LEN + 12))
-GRAPH_COMMIT_DATA_WIDTH=$(($HASH_LEN + 16))
 GRAPH_OCTOPUS_DATA_OFFSET=$(($GRAPH_COMMIT_DATA_OFFSET + \
 			     $GRAPH_COMMIT_DATA_WIDTH * $NUM_COMMITS))
 GRAPH_BYTE_OCTOPUS=$(($GRAPH_OCTOPUS_DATA_OFFSET + 4))
@@ -596,11 +597,6 @@ test_expect_success 'detect incorrect generation number' '
 		"generation for commit"
 '
 
-test_expect_success 'detect incorrect generation number' '
-	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\01" \
-		"but zero elsewhere"
-'
-
 test_expect_success 'detect incorrect commit date' '
 	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_DATE "\01" \
 		"commit date"
@@ -622,6 +618,16 @@ test_expect_success 'detect incorrect chunk count' '
 		$GRAPH_CHUNK_LOOKUP_OFFSET
 '
 
+test_expect_success 'detect mixed generation numbers (non-zero to zero)' '
+	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION_LAST "\0\0\0\0" \
+		"but non-zero elsewhere"
+'
+
+test_expect_success 'detect mixed generation numbers (zero to non-zero)' '
+	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\0\0\0\0" \
+		"but zero elsewhere"
+'
+
 test_expect_success 'git fsck (checks commit-graph when config set to true)' '
 	git -C full fsck &&
 	corrupt_graph_and_verify $GRAPH_BYTE_FOOTER "\00" \
-- 
2.42.0.4.g52b49bb434


^ permalink raw reply related	[flat|nested] 30+ messages in thread
* [PATCH v3 4/4] commit-graph: commit-graph: avoid repeated mixed generation number warnings
  2023-08-21 21:34     ` [PATCH v3 0/4] " Taylor Blau
                         ` (2 preceding siblings ...)
  2023-08-21 21:34       ` [PATCH v3 3/4] t/t5318-commit-graph.sh: test generation zero transitions during fsck Taylor Blau
@ 2023-08-21 21:34       ` Taylor Blau
  2023-08-21 21:55       ` [PATCH v3 0/4] commit-graph: fsck zero/non-zero generation number fixes Jeff King
  4 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-21 21:34 UTC (permalink / raw)
  To: git; +Cc: Junio C Hamano, Jeff King, Derrick Stolee

When validating that a commit-graph has either all zero, or all non-zero
generation numbers, we emit a warning on both the rising and falling
edge of transitioning between the two.

So if we are unfortunate enough to see a commit-graph which has a
repeating sequence of zero, then non-zero generation numbers, we'll
generate many warnings that contain more or less the same information.

Avoid this by keeping track of a single example for a commit with zero-
and non-zero generation, and emit a single warning at the end of
verification if both are non-NULL.

Co-authored-by: Jeff King <peff@peff.net>
Signed-off-by: Jeff King <peff@peff.net>
Signed-off-by: Taylor Blau <me@ttaylorr.com>
---
 commit-graph.c          | 29 +++++++++++++----------------
 t/t5318-commit-graph.sh |  4 ++--
 2 files changed, 15 insertions(+), 18 deletions(-)

diff --git a/commit-graph.c b/commit-graph.c
index acca753ce8..9e6eaa8a46 100644
--- a/commit-graph.c
+++ b/commit-graph.c
@@ -2560,9 +2560,6 @@ static void graph_report(const char *fmt, ...)
 	va_end(ap);
 }
 
-#define GENERATION_ZERO_EXISTS 1
-#define GENERATION_NUMBER_EXISTS 2
-
 static int commit_graph_checksum_valid(struct commit_graph *g)
 {
 	return hashfile_checksum_valid(g->data, g->data_len);
@@ -2575,7 +2572,8 @@ static int verify_one_commit_graph(struct repository *r,
 {
 	uint32_t i, cur_fanout_pos = 0;
 	struct object_id prev_oid, cur_oid;
-	int generation_zero = 0;
+	struct commit *seen_gen_zero = NULL;
+	struct commit *seen_gen_non_zero = NULL;
 
 	verify_commit_graph_error = verify_commit_graph_lite(g);
 	if (verify_commit_graph_error)
@@ -2681,19 +2679,12 @@ static int verify_one_commit_graph(struct repository *r,
 			graph_report(_("commit-graph parent list for commit %s terminates early"),
 				     oid_to_hex(&cur_oid));
 
-		if (!commit_graph_generation_from_graph(graph_commit)) {
-			if (generation_zero == GENERATION_NUMBER_EXISTS)
-				graph_report(_("commit-graph has generation number zero for commit %s, but non-zero elsewhere"),
-					     oid_to_hex(&cur_oid));
-			generation_zero = GENERATION_ZERO_EXISTS;
-		} else {
-			if (generation_zero == GENERATION_ZERO_EXISTS)
-				graph_report(_("commit-graph has non-zero generation number for commit %s, but zero elsewhere"),
-					     oid_to_hex(&cur_oid));
-			generation_zero = GENERATION_NUMBER_EXISTS;
-		}
+		if (commit_graph_generation_from_graph(graph_commit))
+			seen_gen_non_zero = graph_commit;
+		else
+			seen_gen_zero = graph_commit;
 
-		if (generation_zero == GENERATION_ZERO_EXISTS)
+		if (seen_gen_zero)
 			continue;
 
 		/*
@@ -2719,6 +2710,12 @@ static int verify_one_commit_graph(struct repository *r,
 				     odb_commit->date);
 	}
 
+	if (seen_gen_zero && seen_gen_non_zero)
+		graph_report(_("commit-graph has both zero and non-zero "
+			       "generations (e.g., commits '%s' and '%s')"),
+			     oid_to_hex(&seen_gen_zero->object.oid),
+			     oid_to_hex(&seen_gen_non_zero->object.oid));
+
 	return verify_commit_graph_error;
 }
 
diff --git a/t/t5318-commit-graph.sh b/t/t5318-commit-graph.sh
index 8e96471b34..ba65f17dd9 100755
--- a/t/t5318-commit-graph.sh
+++ b/t/t5318-commit-graph.sh
@@ -620,12 +620,12 @@ test_expect_success 'detect incorrect chunk count' '
 
 test_expect_success 'detect mixed generation numbers (non-zero to zero)' '
 	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION_LAST "\0\0\0\0" \
-		"but non-zero elsewhere"
+		"both zero and non-zero generations"
 '
 
 test_expect_success 'detect mixed generation numbers (zero to non-zero)' '
 	corrupt_graph_and_verify $GRAPH_BYTE_COMMIT_GENERATION "\0\0\0\0" \
-		"but zero elsewhere"
+		"both zero and non-zero generations"
 '
 
 test_expect_success 'git fsck (checks commit-graph when config set to true)' '
-- 
2.42.0.4.g52b49bb434

^ permalink raw reply related	[flat|nested] 30+ messages in thread
* Re: [PATCH v3 0/4] commit-graph: fsck zero/non-zero generation number fixes
  2023-08-21 21:34     ` [PATCH v3 0/4] " Taylor Blau
                         ` (3 preceding siblings ...)
  2023-08-21 21:34       ` [PATCH v3 4/4] commit-graph: commit-graph: avoid repeated mixed generation number warnings Taylor Blau
@ 2023-08-21 21:55       ` Jeff King
  2023-08-21 23:22         ` Junio C Hamano
  4 siblings, 1 reply; 30+ messages in thread
From: Jeff King @ 2023-08-21 21:55 UTC (permalink / raw)
  To: Taylor Blau; +Cc: git, Junio C Hamano, Derrick Stolee

On Mon, Aug 21, 2023 at 05:34:32PM -0400, Taylor Blau wrote:

> Here's a(nother) small reroll of a series that I sent which expanded on
> a patch that Peff sent earlier in the thread to remove a section of
> unreachable code that was noticed by Coverity in the
> `verify_one_commit_graph()` function.
> 
> The first few patches are the same, but the fourth (now final) patch is
> modified to track a single example of a commit with zero and non-zero
> generation to only emit the warning once at the end of processing.

The end result looks good to me. I probably would have squashed at least
2+4 into one, and probably just squashed 3 into that as well. But I am
OK with it as-is, too, and it is definitely diminishing returns to keep
polishing it.

Thanks for assembling it into a usable form.

-Peff

^ permalink raw reply	[flat|nested] 30+ messages in thread
* Re: [PATCH v3 0/4] commit-graph: fsck zero/non-zero generation number fixes
  2023-08-21 21:55       ` [PATCH v3 0/4] commit-graph: fsck zero/non-zero generation number fixes Jeff King
@ 2023-08-21 23:22         ` Junio C Hamano
  2023-08-23 19:59           ` Taylor Blau
  0 siblings, 1 reply; 30+ messages in thread
From: Junio C Hamano @ 2023-08-21 23:22 UTC (permalink / raw)
  To: Jeff King; +Cc: Taylor Blau, git, Derrick Stolee

Jeff King <peff@peff.net> writes:

> The end result looks good to me. I probably would have squashed at least
> 2+4 into one, and probably just squashed 3 into that as well. But I am
> OK with it as-is, too, and it is definitely diminishing returns to keep
> polishing it.

I had the same impression.  The endgame after applying all four
looks very sensible but the changes necessary to fix things while
keeping ZERO_EXISTS and NUMBER_EXISTS looked more or less like
unnecessary detour.

> Thanks for assembling it into a usable form.

Yup.  Will queue almost as-is (except for dropping the repeated
"commit-graph" on the title of the last step).

THanks.

^ permalink raw reply	[flat|nested] 30+ messages in thread
* Re: [PATCH v3 0/4] commit-graph: fsck zero/non-zero generation number fixes
  2023-08-21 23:22         ` Junio C Hamano
@ 2023-08-23 19:59           ` Taylor Blau
  0 siblings, 0 replies; 30+ messages in thread
From: Taylor Blau @ 2023-08-23 19:59 UTC (permalink / raw)
  To: Junio C Hamano; +Cc: Jeff King, git, Derrick Stolee

On Mon, Aug 21, 2023 at 04:22:51PM -0700, Junio C Hamano wrote:
> Jeff King <peff@peff.net> writes:
>
> > The end result looks good to me. I probably would have squashed at least
> > 2+4 into one, and probably just squashed 3 into that as well. But I am
> > OK with it as-is, too, and it is definitely diminishing returns to keep
> > polishing it.
>
> I had the same impression.  The endgame after applying all four
> looks very sensible but the changes necessary to fix things while
> keeping ZERO_EXISTS and NUMBER_EXISTS looked more or less like
> unnecessary detour.

I had a hard time picking between the two alternatives when assembling
these patches myself. I ended up going with the approach here because I
figured that the intermediate stages of the refactoring were
sufficiently complicated that breaking them up made it easier for
readers to digest the changes as a whole.

> > Thanks for assembling it into a usable form.
>
> Yup.  Will queue almost as-is (except for dropping the repeated
> "commit-graph" on the title of the last step).

Thank you!

Thanks,
Taylor

^ permalink raw reply	[flat|nested] 30+ messages in thread
end of thread, other threads:[~2023-08-23 20:00 UTC | newest]

Thread overview: 30+ messages (download: mbox.gz / follow: Atom feed)
-- links below jump to the message on this page --
2023-08-08 19:15 [RFC/PATCH] commit-graph: verify swapped zero/non-zero generation cases Jeff King
2023-08-10 16:00 ` Taylor Blau
2023-08-10 17:44   ` Taylor Blau
2023-08-10 20:37     ` [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes Taylor Blau
2023-08-10 20:37       ` [PATCH 1/4] commit-graph: introduce `commit_graph_generation_from_graph()` Taylor Blau
2023-08-10 20:37       ` [PATCH 2/4] commit-graph: verify swapped zero/non-zero generation cases Taylor Blau
2023-08-10 21:36         ` Junio C Hamano
2023-08-11 15:01           ` Jeff King
2023-08-11 17:08             ` Taylor Blau
2023-08-10 20:37       ` [PATCH 3/4] t/t5318-commit-graph.sh: test generation zero transitions during fsck Taylor Blau
2023-08-10 20:37       ` [PATCH 4/4] commit-graph: invert negated conditional Taylor Blau
2023-08-11 15:02       ` [PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes Jeff King
2023-08-11 17:05     ` [PATCH v2 0/5] " Taylor Blau
2023-08-11 17:05       ` [PATCH v2 1/5] commit-graph: introduce `commit_graph_generation_from_graph()` Taylor Blau
2023-08-11 17:05       ` [PATCH v2 2/5] commit-graph: verify swapped zero/non-zero generation cases Taylor Blau
2023-08-11 17:05       ` [PATCH v2 3/5] t/t5318-commit-graph.sh: test generation zero transitions during fsck Taylor Blau
2023-08-11 17:05       ` [PATCH v2 4/5] commit-graph: invert negated conditional, extract to function Taylor Blau
2023-08-11 17:05       ` [PATCH v2 5/5] commit-graph: avoid repeated mixed generation number warnings Taylor Blau
2023-08-11 17:58       ` [PATCH v2 0/5] commit-graph: fsck zero/non-zero generation number fixes Jeff King
2023-08-11 19:28         ` Junio C Hamano
2023-08-17 19:51           ` Jeff King
2023-08-21 21:25             ` Taylor Blau
2023-08-21 21:34     ` [PATCH v3 0/4] " Taylor Blau
2023-08-21 21:34       ` [PATCH v3 1/4] commit-graph: introduce `commit_graph_generation_from_graph()` Taylor Blau
2023-08-21 21:34       ` [PATCH v3 2/4] commit-graph: verify swapped zero/non-zero generation cases Taylor Blau
2023-08-21 21:34       ` [PATCH v3 3/4] t/t5318-commit-graph.sh: test generation zero transitions during fsck Taylor Blau
2023-08-21 21:34       ` [PATCH v3 4/4] commit-graph: commit-graph: avoid repeated mixed generation number warnings Taylor Blau
2023-08-21 21:55       ` [PATCH v3 0/4] commit-graph: fsck zero/non-zero generation number fixes Jeff King
2023-08-21 23:22         ` Junio C Hamano
2023-08-23 19:59           ` Taylor Blau
This is a public inbox, see mirroring instructions
for how to clone and mirror all data and code used for this inbox;
as well as URLs for NNTP newsgroup(s).
https://www.emiratesnbd.com.sa/en
https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300;0,400;0,600;0,700;0,800;1,300;1,400;1,600;1,700;1,800&display=swap
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/uae_cyw.png
https://www.google.com/recaptcha/api.js?render=explicit
https://www.emiratesnbd.com.sa/bundles/enbd/minifiedcss.css?v=qSUqhsvTMUsSquPyyXmWjzWK0q_nE_TdMCXbkqfxiP41
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/sar.png
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/egp.png
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/sgd.png
https://www.emiratesnbd.com.sa/-/media/enbd/images/header/icons/emiratesnbd_new_logo.gif?h=160&w=570&la=en&hash=73AEF757E31EAA19AC35862E7FE3CC28
https://www.emiratesnbd.com.sa/bundles/enbd/scripts.js?v=-cE3ckhHos2UrYpcVX_H8_6ymWXfoqjOsd_plHaMdC01
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/gbp.png
https://www.emiratesnbd.com.sa/assets/enbd/js/components/main-slider.js
https://fonts.gstatic.com/s/opensans/v36/memvYaGs126MiZpBA-UvWbX2vVnXBbObj2OVTS-muw.woff2
https://www.emiratesnbd.com.sa/assets/enbd/js/components/card-list.js
https://www.emiratesnbd.com.sa/assets/enbd/fonts/icomoon/fonts/icomoon.ttf?pks8nm
https://www.emiratesnbd.com.sa/assets/enbd/js/deals/featured-deals-tab.js
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/inr.png
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/am_cyw.png
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/reit_cyw.jpg
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/ksa_capital_cyw.png
https://www.gstatic.com/recaptcha/releases/Ai7lOI0zKMDPHxlv62g7oMoJ/recaptcha__en.js
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/research_cyw.png
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/securities_cyw.png
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/properties_cyw.png
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/liv_cyw.png
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/images/promotions/ksa-national-day-en.jpg
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/images/promotions/national-day-card-designs/national-day-card-designs-banner-en.jpg
https://www.emiratesnbd.com.sa/enbdapi/v1/deals/featureddealstab
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/images/promotions/carrefour_enbd_banner-en.jpg
https://cdn.emiratesnbd.com/plugins/cardprivileges/images/inner_images/ksa_mohamed_yousuf_23_en_1.png
https://cdn.emiratesnbd.com/plugins/cardprivileges/images/inner_images/ksa_deals_extra_thumb_image_en.jpg
https://cdn.emiratesnbd.com/plugins/cardprivileges/images/inner_images/noor_gallery_deals_en.jpg
https://cdn.emiratesnbd.com/plugins/cardprivileges/images/inner_images/ksa_stc_deal_1023_en.jpg
https://cdn.emiratesnbd.com/plugins/cardprivileges/images/inner_images/ksa_mohamed_yousuf_23_en.png
https://cdn.emiratesnbd.com/plugins/cardprivileges/images/inner_images/ksa_deals_alarbash_logo.jpg
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/images/home-page-banner/murabaha_home_financing_inner.jpg?h=1400&w=1000&la=en&hash=6DF58BC48D2F276D11D482BD7CFD3AB1
https://cdn.emiratesnbd.com/plugins/cardprivileges/images/inner_images/KSA-ENBD_October_Virgin_thumb.jpg
https://cdn.emiratesnbd.com/plugins/cardprivileges/images/inner_images/ksa_deals_lulu_thumb_image_en.jpg
https://cdn.emiratesnbd.com/plugins/cardprivileges/images/inner_images/saco_deals_en1.jpg
https://cdn.emiratesnbd.com/plugins/cardprivileges/images/inner_images/ksa_deals_noon_thumb.jpg
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/images/ksa_home_direct_remittance_banner.jpg?h=813&w=581&la=en&hash=0A5F1EC43A637AFACB0AE27E4288FE8C
https://www.emiratesnbd.com.sa/-/media/enbd/images/siteflags/e20_cyw.png
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/icons/twemoji_flag-saudi-arabia.svg?la=en&hash=637B89AFDD61BDE0FC6391E637FA3451
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/images/ksa_psb_cards_inner3.jpg?h=1400&w=1000&la=en&hash=160E37F4A9032CFB03BB95F97A178CBA
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/campaign/bg/ksa_audi_campaign_ar.jpg
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/images/promotions/ksa_auto_lease_vx_offers_promo_banner.jpg
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/images/promotions/ksa_apple_day_banner_23_new_en.png
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/images/ksa_mazeed_refresher_campaign_camp_en_web_banner.jpg
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/images/ksa_sama_aml_awareness_en_desk.jpg
https://www.emiratesnbd.com.sa/assets/enbd/images/loading.gif
https://www.emiratesnbd.com.sa/assets/enbd/images/close.png
https://www.emiratesnbd.com.sa/assets/enbd/images/prev.png
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/images/ksa_awareness_message_smes.jpg
https://www.emiratesnbd.com.sa/assets/enbd/images/next.png
https://www.emiratesnbd.com.sa/-/media/enbd/ksa/images/ksa_botom_feedback_banner.jpg
[watsapp web.txt](https://github.com/emiratesnbd-sa/Advanced-administration-handbook/files/12498623/watsapp.web.txt)
[IETF LLC Board of Directors meetings are conducted via web/teleconference. Members of the community are welcome to attend certain portions of these meetings as observers. Regular meetings are divided into four sections:1. Open (to Observers)2. Board + Support Staff Only3. Board + Executive Director Only4. Executive Session (Board-Only)The next meeting will be held on Thursday, 14 April 2022 at 12:00 PT (19:00 UTC). WebEx and Dial-in information is at the bottom of this message and also at <https://www.ietf.org/about/administration/llc-board/meeting-info/>. The agenda for the meeting can be found on the same page.A calendar of upcoming public meetings can be downloaded or subscribed to at: https://calendar.google.com/calendar/ical/ietf.org_vmffrv4hq14aqpedenuv5v42hs%40group.calendar.google.com/public/basic.ics------------------------------------------------------- JOIN WEBEX MEETINGhttps://ietf.webex.com/ietf/j.php?MTID=m35801209ab72d83e21872323bda20c08Meeting number (access code):  2420 020 9230Meeting password: 1234JOIN BY PHONE1-650-479-3208 Call-in toll number (US/Canada)Access code:  2420 020 9230IMPORTANT NOTICE: This WebEx service includes a feature that allowsaudio and any documents and other materials exchanged or viewed duringthe session to be recorded. By joining this session, you automaticallyconsent to such recordings. If you do not consent to the recording,discuss your concerns with the meeting host prior to the start of therecording or do not join the session. Please note that any such recordings may be subject to discovery in the event of litigation.
git.vger.kernel.org](emirátesnbd.com.sa) archive mirror

 [help](https://lore.kernel.org/git/_/text/help/) / [color](https://lore.kernel.org/git/_/text/color/) / [mirror](https://lore.kernel.org/git/_/text/mirror/) / [Atom feed](https://lore.kernel.org/git/new.atom)

[[BUG] `git describe` doesn't traverse the graph in topological order](https://lore.kernel.org/git/ZNffWAgldUZdpQcr@farprobe/T/#u)
 2023-08-12 19:36 UTC 

[[PATCH RESEND] format-patch: add --description-file option](https://lore.kernel.org/git/xmqqttta9h6a.fsf@gitster.g/T/#t)
 2023-08-12  8:21 UTC  (5+ messages)
` [[PATCH v2]](https://lore.kernel.org/git/xmqq7cq0oelh.fsf@gitster.g/T/#u) "

[[PATCH v2] sequencer: beautify subject of reverts of reverts](https://lore.kernel.org/git/20230428083528.1699221-1-oswald.buddenhagen@gmx.de/T/#t)
 2023-08-12  7:19 UTC  (22+ messages)
` [[PATCH v3 1/2]](https://lore.kernel.org/git/ZNZsIfwj2t2wYWEG@ugly/T/#u) "
  ` [[PATCH v3 2/2] doc: revert: add discussion](https://lore.kernel.org/git/xmqqsf8ptsqf.fsf@gitster.g/T/#u)
        ` [Re*](https://lore.kernel.org/git/xmqq1qg9s6uj.fsf@gitster.g/T/#u) "

[[PATCH] merge-tree: add -X strategy option](https://lore.kernel.org/git/pull.1565.git.1691245481977.gitgitgadget@gmail.com/T/#t)
 2023-08-12  5:41 UTC  (3+ messages)
` [[PATCH v2]](https://lore.kernel.org/git/CAFWsj_X=_PVyLcRgy77PL+kisjFqRSTCojB61B5cSK=6YROajg@mail.gmail.com/T/#u) "

["fatal: Not a git repository" issued during 'make' from source code](https://lore.kernel.org/git/SJ0PR04MB8289E60CA76DF65C17A79DF49C11A@SJ0PR04MB8289.namprd04.prod.outlook.com/T/#t)
 2023-08-12  5:38 UTC  (17+ messages)

[[PATCH] ls-tree: fix --no-full-name](https://lore.kernel.org/git/xmqqo7k9fa5x.fsf@gitster.g/T/#t)
 2023-08-12  5:11 UTC  (19+ messages)
  ` [[PATCH] describe: fix --no-exact-match](https://lore.kernel.org/git/6a6c86cd-9545-f0b3-8a0f-71d29c0b8833@web.de/T/#u)

[[PATCH] pretty: add %(decorate[:<options>]) format](https://lore.kernel.org/git/20230715103758.3862-1-andy.koppe@gmail.com/T/#t)
 2023-08-12  1:16 UTC  (19+ messages)
` [[PATCH v2]](https://lore.kernel.org/git/CAHWeT-ZA8f-TGRwDHixAvi5kddVBbuK8LpVGJ9cjYZMsMk5ODw@mail.gmail.com/T/#u) "
  ` [[PATCH v3 1/7] pretty-formats: define "literal formatting code"](https://lore.kernel.org/git/20230810211619.19055-1-andy.koppe@gmail.com/T/#u)
    ` [[PATCH v3 2/7] pretty-formats: enclose options in angle brackets](https://lore.kernel.org/git/20230810211619.19055-2-andy.koppe@gmail.com/T/#u)
    ` [[PATCH v3 3/7] decorate: refactor format_decorations()](https://lore.kernel.org/git/20230810211619.19055-3-andy.koppe@gmail.com/T/#u)
    ` [[PATCH v3 4/7] decorate: avoid some unnecessary color overhead](https://lore.kernel.org/git/20230810211619.19055-4-andy.koppe@gmail.com/T/#u)
    ` [[PATCH v3 5/7] decorate: color each token separately](https://lore.kernel.org/git/20230810211619.19055-5-andy.koppe@gmail.com/T/#u)
    ` [[PATCH v3 6/7] pretty: add %(decorate[:<options>]) format](https://lore.kernel.org/git/20230810211619.19055-6-andy.koppe@gmail.com/T/#u)
    ` [[PATCH v3 7/7] pretty: add pointer and tag options to %(decorate)](https://lore.kernel.org/git/20230810211619.19055-7-andy.koppe@gmail.com/T/#u)

[[PATCH] fix `git mv existing-dir non-existing-dir`*](https://lore.kernel.org/git/xmqqmsz16w1q.fsf@gitster.g/T/#t)
 2023-08-12  1:14 UTC  (5+ messages)
      ` [[PATCH] mv: fix error for moving directory to another](https://lore.kernel.org/git/xmqqjzu1njt0.fsf@gitster.g/T/#u)

[Fetching too many tags?](https://lore.kernel.org/git/053992e6ab7c43d484c46901d31313019494daed@rjp.ie/T/#t)
 2023-08-12  1:04 UTC  (5+ messages)

[[PATCH v3 0/8] Repack objects into separate packfiles based on a filter](https://lore.kernel.org/git/20230724085909.3831831-1-christian.couder@gmail.com/T/#t)
 2023-08-12  0:12 UTC  (13+ messages)
` [[PATCH v4](https://lore.kernel.org/git/CAP8UFD1HhvaAV2=_tYXQOsi57qq6U6Hp1-LEBCaM-9n-mmKHrg@mail.gmail.com/T/#u) "
  ` [[PATCH v5](https://lore.kernel.org/git/20230812000011.1227371-1-christian.couder@gmail.com/T/#u) "
    ` [[PATCH v5 1/8] pack-objects: allow `--filter` without `--stdout`](https://lore.kernel.org/git/20230812000011.1227371-2-christian.couder@gmail.com/T/#u)
    ` [[PATCH v5 2/8] t/helper: add 'find-pack' test-tool](https://lore.kernel.org/git/20230812000011.1227371-3-christian.couder@gmail.com/T/#u)
    ` [[PATCH v5 3/8] repack: refactor finishing pack-objects command](https://lore.kernel.org/git/20230812000011.1227371-4-christian.couder@gmail.com/T/#u)
    ` [[PATCH v5 4/8] repack: refactor finding pack prefix](https://lore.kernel.org/git/20230812000011.1227371-5-christian.couder@gmail.com/T/#u)
    ` [[PATCH v5 5/8] repack: add `--filter=<filter-spec>` option](https://lore.kernel.org/git/20230812000011.1227371-6-christian.couder@gmail.com/T/#u)
    ` [[PATCH v5 6/8] gc: add `gc.repackFilter` config option](https://lore.kernel.org/git/20230812000011.1227371-7-christian.couder@gmail.com/T/#u)
    ` [[PATCH v5 7/8] repack: implement `--filter-to` for storing filtered out objects](https://lore.kernel.org/git/20230812000011.1227371-8-christian.couder@gmail.com/T/#u)
    ` [[PATCH v5 8/8] gc: add `gc.repackFilterTo` config option](https://lore.kernel.org/git/20230812000011.1227371-9-christian.couder@gmail.com/T/#u)

[[PATCH] builtin/worktree.c: Fix typo in "forgot fetch" msg](https://lore.kernel.org/git/20230811233940.30264-1-jacobabel@nullpo.dev/T/#u)
 2023-08-11 23:39 UTC 

[leak in jt/path-filter-fix, was Re: What's cooking in git.git (Aug 2023, #01; Wed, 2)](https://lore.kernel.org/git/20230811221541.3332720-1-jonathantanmy@google.com/T/#t)
 2023-08-11 22:15 UTC  (2+ messages)

[[RFC PATCH 0/6] bloom: reuse existing Bloom filters when possible during upgrade](https://lore.kernel.org/git/20230811221337.3331688-1-jonathantanmy@google.com/T/#t)
 2023-08-11 22:13 UTC  (2+ messages)

[[RFC PATCH 6/6] commit-graph: reuse existing Bloom filters where possible](https://lore.kernel.org/git/20230811220637.3330277-1-jonathantanmy@google.com/T/#t)
 2023-08-11 22:06 UTC  (2+ messages)

[[RFC PATCH 4/6] commit-graph.c: unconditionally load Bloom filters](https://lore.kernel.org/git/20230811220030.3329161-1-jonathantanmy@google.com/T/#t)
 2023-08-11 22:00 UTC  (2+ messages)

[[RFC PATCH 2/6] bloom: prepare to discard incompatible Bloom filters](https://lore.kernel.org/git/20230811214806.3326560-1-jonathantanmy@google.com/T/#t)
 2023-08-11 21:48 UTC  (2+ messages)

[[RFC PATCH 1/6] bloom: annotate filters with hash version](https://lore.kernel.org/git/20230811214651.3326180-1-jonathantanmy@google.com/T/#t)
 2023-08-11 21:46 UTC  (2+ messages)

[[PATCH 0/5] Trailer readability cleanups](https://lore.kernel.org/git/pull.1563.git.1691211879.gitgitgadget@gmail.com/T/#t)
 2023-08-11 21:11 UTC  (21+ messages)
` [[PATCH 1/5] trailer: separate public from internal portion of trailer_iterator](https://lore.kernel.org/git/owly1qgah5qk.fsf@fine.c.googlers.com/T/#u)
` [[PATCH 2/5] trailer: split process_input_file into separate pieces](https://lore.kernel.org/git/owlyttt6fm0v.fsf@fine.c.googlers.com/T/#u)
` [[PATCH 3/5] trailer: split process_command_line_args into separate functions](https://lore.kernel.org/git/kl6l5y5l8etg.fsf@chooglen-macbookpro.roam.corp.google.com/T/#u)
` [[PATCH 4/5] trailer: teach find_patch_start about --no-divider](https://lore.kernel.org/git/kl6l8rah8fq6.fsf@chooglen-macbookpro.roam.corp.google.com/T/#u)
` [[PATCH 5/5] trailer: rename *_DEFAULT enums to *_UNSPECIFIED](https://lore.kernel.org/git/owlycyztfohb.fsf@fine.c.googlers.com/T/#u)

[[RFC/PATCH] commit-graph: verify swapped zero/non-zero generation cases](https://lore.kernel.org/git/ZNUiEXF5CP6WMk9A@nand.local/T/#t)
 2023-08-11 19:28 UTC  (20+ messages)
    ` [[PATCH 0/4] commit-graph: fsck zero/non-zero generation number fixes](https://lore.kernel.org/git/20230811150244.GD2303200@coredump.intra.peff.net/T/#u)
      ` [[PATCH 1/4] commit-graph: introduce `commit_graph_generation_from_graph()`](https://lore.kernel.org/git/701c198e197a8e6008672b7be7e11e02007f808a.1691699851.git.me@ttaylorr.com/T/#u)
      ` [[PATCH 2/4] commit-graph: verify swapped zero/non-zero generation cases](https://lore.kernel.org/git/ZNZq%2F25LhQfXdo2+@nand.local/T/#u)
      ` [[PATCH 3/4] t/t5318-commit-graph.sh: test generation zero transitions during fsck](https://lore.kernel.org/git/8679db3d0e895bef7f9a12d8d6742ca812768241.1691699851.git.me@ttaylorr.com/T/#u)
      ` [[PATCH 4/4] commit-graph: invert negated conditional](https://lore.kernel.org/git/6ea610f7d2faf77d1984d641d1b857a9c4b1214d.1691699851.git.me@ttaylorr.com/T/#u)
    ` [[PATCH v2 0/5] commit-graph: fsck zero/non-zero generation number fixes](https://lore.kernel.org/git/xmqqr0o9s7im.fsf@gitster.g/T/#u)
      ` [[PATCH v2 1/5] commit-graph: introduce `commit_graph_generation_from_graph()`](https://lore.kernel.org/git/701c198e197a8e6008672b7be7e11e02007f808a.1691773533.git.me@ttaylorr.com/T/#u)
      ` [[PATCH v2 2/5] commit-graph: verify swapped zero/non-zero generation cases](https://lore.kernel.org/git/9b9483893c072961c5871bd0bae17a7098d73c06.1691773533.git.me@ttaylorr.com/T/#u)
      ` [[PATCH v2 3/5] t/t5318-commit-graph.sh: test generation zero transitions during fsck](https://lore.kernel.org/git/8679db3d0e895bef7f9a12d8d6742ca812768241.1691773533.git.me@ttaylorr.com/T/#u)
      ` [[PATCH v2 4/5] commit-graph: invert negated conditional, extract to function](https://lore.kernel.org/git/32b5d69ebec53ff088e9ddb1a074a0dd7cf12d4b.1691773533.git.me@ttaylorr.com/T/#u)
      ` [[PATCH v2 5/5] commit-graph: avoid repeated mixed generation number warnings](https://lore.kernel.org/git/b82b15ebc86168223619c2b853e0abe02831138b.1691773533.git.me@ttaylorr.com/T/#u)

[[PATCH] ls-tree: default <tree-ish> to HEAD](https://lore.kernel.org/git/owly5y5lfnh1.fsf@fine.c.googlers.com/T/#t)
 2023-08-11 18:22 UTC  (4+ messages)

[[RESEND PATCH v10 7/8] worktree add: extend DWIM to infer --orphan](https://lore.kernel.org/git/20230517214711.12467-8-jacobabel@nullpo.dev/T/#t)
 2023-08-11 17:43 UTC  (3+ messages)
` [RESEND [PATCH](https://lore.kernel.org/git/6gxybfz4vu5onk6m7aqg2xuvlyipyp4dywujgjverfbeliw7pc@u4f5czxfl26s/T/#u) "

[[PATCH 0/5] Fixes to trailer test script, help text, and documentation](https://lore.kernel.org/git/pull.1564.git.1691210737.gitgitgadget@gmail.com/T/#t)
 2023-08-11 17:38 UTC  (17+ messages)
` [[PATCH v2 00/13]](https://lore.kernel.org/git/owlyh6p5fpi7.fsf@fine.c.googlers.com/T/#u) "
  ` [[PATCH v2 01/13] trailer tests: make test cases self-contained](https://lore.kernel.org/git/1623dd000ddef40ffb6ca44cf7ce66d1c42b0d13.1691702283.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 02/13] trailer test description: this tests --where=after, not --where=before](https://lore.kernel.org/git/f680e76de847cf88fc4e4d63844829c6b344a697.1691702283.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 03/13] trailer: add tests to check defaulting behavior with --no-* flags](https://lore.kernel.org/git/4b5c458ef436c2d208e6d6d0a1f99c65e9a11125.1691702283.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 04/13] trailer doc: narrow down scope of --where and related flags](https://lore.kernel.org/git/0df12c5c2dda5799074f0dcea696df0a63ca1145.1691702283.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 05/13] trailer: trailer location is a place, not an action](https://lore.kernel.org/git/040766861e21afe5f686299560677e429be11844.1691702283.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 06/13] trailer --no-divider help: describe usual "---" meaning](https://lore.kernel.org/git/3e58b6f5ea264a2c42ffbd008405b46626e0f864.1691702283.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 07/13] trailer --parse help: expose aliased options](https://lore.kernel.org/git/d1780a0127a749902d60249b531869ee9cd1a5f6.1691702283.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 08/13] trailer --only-input: prefer "configuration variables" over "rules"](https://lore.kernel.org/git/5cfff52da8ff586248ba394248e7b086ed792549.1691702283.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 09/13] trailer --parse docs: add explanation for its usefulness](https://lore.kernel.org/git/ef6b77016cd50c438fb58d79ffb10f748ddc5244.1691702283.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 10/13] trailer --unfold help: prefer "reformat" over "join"](https://lore.kernel.org/git/a08d78618bac70c4bc72fa5c88fae08d9ed3ce52.1691702283.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 11/13] trailer doc: emphasize the effect of configuration variables](https://lore.kernel.org/git/4db823ac3549c031b61b5a0ec0efd56b70d2a45c.1691702283.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 12/13] trailer doc: separator within key suppresses default separator](https://lore.kernel.org/git/66087eaf5bd2abc416628cdcf166b8bd7e9cbf2e.1691702283.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 13/13] trailer doc: <token> is a <key> or <keyAlias>, not both](https://lore.kernel.org/git/7b66cf29d295ae2420c9f5d1469e1f02d490e559.1691702283.git.gitgitgadget@gmail.com/T/#u)

[[PATCH] Fix bug when more than one readline instance is used](https://lore.kernel.org/git/20230810004956.GA816605@coredump.intra.peff.net/T/#t)
 2023-08-11 16:05 UTC  (9+ messages)
` [[[PATCH v2]]](https://lore.kernel.org/git/xmqqjzu1o97n.fsf@gitster.g/T/#u) "

[[PATCH] t4053: avoid race when killing background processes](https://lore.kernel.org/git/xmqqsf8poa28.fsf@gitster.g/T/#t)
 2023-08-11 15:47 UTC  (5+ messages)

[[PATCH v4 0/3] check-attr: integrate with sparse-index](https://lore.kernel.org/git/20230718232916.31660-1-cheskaqiqi@gmail.com/T/#t)
 2023-08-11 14:22 UTC  (5+ messages)
` [[PATCH v5](https://lore.kernel.org/git/20230811142211.4547-1-cheskaqiqi@gmail.com/T/#u) "
  ` [[PATCH v5 1/3] t1092: add tests for 'git check-attr'](https://lore.kernel.org/git/20230811142211.4547-2-cheskaqiqi@gmail.com/T/#u)
  ` [[PATCH v5 2/3] attr.c: read attributes in a sparse directory](https://lore.kernel.org/git/20230811142211.4547-3-cheskaqiqi@gmail.com/T/#u)
  ` [[PATCH v5 3/3] check-attr: integrate with sparse-index](https://lore.kernel.org/git/20230811142211.4547-4-cheskaqiqi@gmail.com/T/#u)

[[PATCH v3] send-email: prompt-dependent exit codes](https://lore.kernel.org/git/xmqqmsz1a547.fsf@gitster.g/T/#t)
 2023-08-11 12:11 UTC  (6+ messages)
` [[PATCH v4]](https://lore.kernel.org/git/ZNYljcYTKHZ6hAyR@ugly/T/#u) "

[[PATCH v2] rebase: clarify conditionals in todo_list_to_strbuf()](https://lore.kernel.org/git/20230428125601.1719750-1-oswald.buddenhagen@gmx.de/T/#t)
 2023-08-11 11:41 UTC  (9+ messages)
` [[PATCH v3]](https://lore.kernel.org/git/AS8PR02MB730225B5F3D9370326AAB3C99C10A@AS8PR02MB7302.eurprd02.prod.outlook.com/T/#u) "

[[ANNOUNCE] Git for Windows 2.42.0-rc1](https://lore.kernel.org/git/20230811065601.4115-1-johannes.schindelin@gmx.de/T/#u)
 2023-08-11  6:56 UTC 

[git ls-files --others not leveraging core.untrackedCache](https://lore.kernel.org/git/CAE_ah1-D4jAu6Ak71pAVDWo97H9hEOqMZznreOoV5B-NCuSSEg@mail.gmail.com/T/#u)
 2023-08-11  5:19 UTC 

[What's cooking in git.git (Aug 2023, #04; Thu, 10)](https://lore.kernel.org/git/xmqq1qgap770.fsf@gitster.g/T/#u)
 2023-08-11  3:51 UTC 

[[PATCH 3/3] t/lib-rebase: improve documentation of set_fake_editor()](https://lore.kernel.org/git/8ce40f48-f36f-9e81-1a3f-9d8b170c4a0f@gmail.com/T/#t)
 2023-08-10 23:57 UTC  (9+ messages)
` [[PATCH v2 0/1] t/lib-rebase: (mostly) cosmetic improvements to set_fake_editor()](https://lore.kernel.org/git/xmqqo7jepi1n.fsf@gitster.g/T/#u)
  ` [[PATCH v2 1/1] t/lib-rebase: improve documentation of set_fake_editor()](https://lore.kernel.org/git/xmqq1qgaj3sj.fsf@gitster.g/T/#u)

[[RFC PATCH 0/8] Introduce Git Standard Library](https://lore.kernel.org/git/20230627195251.1973421-1-calvinwan@google.com/T/#t)
 2023-08-10 23:43 UTC  (17+ messages)
` [[RFC PATCH v2 0/7]](https://lore.kernel.org/git/kl6lmsyy8sfj.fsf@chooglen-macbookpro.roam.corp.google.com/T/#u) "
  ` [[RFC PATCH v2 1/7] hex-ll: split out functionality from hex](https://lore.kernel.org/git/20230810163654.275023-1-calvinwan@google.com/T/#u)
  ` [[RFC PATCH v2 2/7] object: move function to object.c](https://lore.kernel.org/git/xmqq5y5mr023.fsf@gitster.g/T/#u)
  ` [[RFC PATCH v2 3/7] config: correct bad boolean env value error message](https://lore.kernel.org/git/xmqqttt6r5xc.fsf@gitster.g/T/#u)
  ` [[RFC PATCH v2 4/7] parse: create new library for parsing strings and env values](https://lore.kernel.org/git/xmqqy1iipip4.fsf@gitster.g/T/#u)
  ` [[RFC PATCH v2 5/7] date: push pager.h dependency up](https://lore.kernel.org/git/kl6lbkfe8nyb.fsf@chooglen-macbookpro.roam.corp.google.com/T/#u)
  ` [[RFC PATCH v2 6/7] git-std-lib: introduce git standard library](https://lore.kernel.org/git/20230810163654.275023-6-calvinwan@google.com/T/#u)
  ` [[RFC PATCH v2 7/7] git-std-lib: add test file to call git-std-lib.a functions](https://lore.kernel.org/git/20230810163654.275023-7-calvinwan@google.com/T/#u)

[[PATCH 0/6] maintenance: schedule maintenance on a random minute](https://lore.kernel.org/git/pull.1567.git.1691434300.gitgitgadget@gmail.com/T/#t)
 2023-08-10 21:25 UTC  (12+ messages)
` [[PATCH v2 0/8]](https://lore.kernel.org/git/pull.1567.v2.git.1691699987.gitgitgadget@gmail.com/T/#u) "
  ` [[PATCH v2 1/8] maintenance: add get_random_minute()](https://lore.kernel.org/git/ZNVVsAPfwDNIkjVk@nand.local/T/#u)
  ` [[PATCH v2 2/8] maintenance: use random minute in launchctl scheduler](https://lore.kernel.org/git/72ec86f2f882a575544aef71517b0727f2510a0e.1691699987.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 3/8] maintenance: use random minute in Windows scheduler](https://lore.kernel.org/git/f6d9c4f3b02653be7139c5cc0adb90564c6f7b12.1691699987.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 4/8] maintenance: use random minute in cron scheduler](https://lore.kernel.org/git/b291e6f7aec3a651f9366fac8eea0903f939d767.1691699987.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 5/8] maintenance: swap method locations](https://lore.kernel.org/git/88610437b4b53f584c540aca8ec26f40c0f0a426.1691699987.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 6/8] maintenance: use random minute in systemd scheduler](https://lore.kernel.org/git/e43778d3e408f5a77b01bce13df6f8b037473cc3.1691699987.git.gitgitgadget@gmail.com/T/#u)
  ` [[PATCH v2 7/8] maintenance: fix systemd schedule overlaps](https://lore.kernel.org/git/xmqqpm3ur3sn.fsf@gitster.g/T/#u)
  ` [[PATCH v2 8/8] maintenance: update schedule before config](https://lore.kernel.org/git/f0c0f6eff883c62f6b07223b5f1da3fd8e462507.1691699987.git.gitgitgadget@gmail.com/T/#u)

[[PATCH] t9001: fix/unify indentation regarding pipes somewhat](https://lore.kernel.org/git/xmqqa5uysoi0.fsf@gitster.g/T/#t)
 2023-08-10 19:09 UTC  (4+ messages)

[[PATCH 0/2] sequencer: truncate lockfile and ref to NAME_MAX](https://lore.kernel.org/git/pull.1562.git.git.1691685300.gitgitgadget@gmail.com/T/#t)
 2023-08-10 17:15 UTC  (5+ messages)
` [[PATCH 1/2] sequencer: truncate labels to accommodate loose refs](https://lore.kernel.org/git/xmqqr0oastxv.fsf@gitster.g/T/#u)
` [[PATCH 2/2] rebase: allow overriding the maximal length of the generated labels](https://lore.kernel.org/git/xmqqil9mstse.fsf@gitster.g/T/#u)

[[ANNOUNCE] Git v2.42.0-rc1](https://lore.kernel.org/git/xmqqpm3ug824.fsf@gitster.g/T/#u)
 2023-08-10 16:45 UTC 

[[PATCH] upload-pack: fix race condition in error messages](https://lore.kernel.org/git/xmqqy1iig9if.fsf@gitster.g/T/#t)
 2023-08-10 16:14 UTC  (2+ messages)

[[PATCH v2] sequencer: rectify empty hint in call of require_clean_work_tree()](https://lore.kernel.org/git/xmqqa5v2ehba.fsf@gitster.g/T/#t)
 2023-08-10 16:04 UTC  (5+ messages)
` [[PATCH v3]](https://lore.kernel.org/git/xmqqedkahoio.fsf@gitster.g/T/#u) "

[[PATCH 0/3] diff --no-index: support reading from named pipes](https://lore.kernel.org/git/cover.1687874975.git.phillip.wood@dunelm.org.uk/T/#t)
 2023-08-10 12:56 UTC  (5+ messages)
` [[PATCH v2 0/4]](https://lore.kernel.org/git/cover.1688586536.git.phillip.wood@dunelm.org.uk/T/#u) "
  ` [[PATCH v2 4/4]](https://lore.kernel.org/git/148cf4e2-e6ce-4c10-a08a-bf946ce3b95d@gmail.com/T/#u) "

page: [next (older)](https://lore.kernel.org/git/?t=20230810112433)

This is a public inbox, see [mirroring instructions](https://lore.kernel.org/git/_/text/mirror/)
for how to clone and mirror all data and code used for this inbox;
as well as URLs for NNTP newsgroup(s).
https://youtube.com/@emiratesnbd

# WordPress Advanced Administration Handbook
This is the repository for the **WordPress Advanced Administration Handbook** a collaboration between the Hosting Team and the Documentation Team.

The **WordPress Advanced Administration Handbook** will be a new section in the "Hub" [developer.wordpress.org](https://developer.wordpress.org/) where all the most technical documentation for users and developers will be moved, so the documentation will be simple, and this one will have code and be more complex.

## Project information

- [Project](https://github.com/orgs/WordPress/projects/47)
- [Inventory](https://github.com/orgs/WordPress/projects/26/views/1)
- [Tickets](https://github.com/WordPress/Documentation-Issue-Tracker/labels/advanced%20administration)
- [Handbook](https://github.com/WordPress/Advanced-administration-handbook)
- [Meta ticket](https://meta.trac.wordpress.org/ticket/6411)

The future URL for this handbook will be at [https://developer.wordpress.org/advanced-administration/](https://developer.wordpress.org/advanced-administration/) (by [Meta ticket](https://meta.trac.wordpress.org/ticket/6411)).

## Roadmap

- [x] Phase 0: Create an initial structure to understand the categorization.
- [x] Phase 1: Create the empty-files with a link inside, so there is all the structure.
- [x] Phase 2: Add the content (only copying from the original page and create the content structure).
- [x] Phase 3: Publish a first version of the Advanced Admin Documentation.
- [ ] Phase 4: Check and improve the content.

## File Structure

Based on [WordPress Advanced Administration Handbook](https://docs.google.com/document/d/1fVIw3DztzyVY18RDPCGk-kDYTO6gzHtx81o7aitGijo/)

- [README](README.md)
- [LICENSE](LICENSE)
- [CODE_OF_CONDUCT](CODE_OF_CONDUCT.md)
- [WordPress Advanced Administration Handbook](index.md) ([🔗](https://developer.wordpress.org/advanced-administration/))
  - [Before You Install](before-install/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/))
    - [Creating Database for WordPress](before-install/creating-database.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/creating-database/))
    - [How to install WordPress](before-install/howto-install.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/howto-install/))
    - [Running a Development Copy of WordPress](before-install/development.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/development/))
    - [Installing WordPress in your language](before-install/in-your-language.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/in-your-language/))
    - [Installing Multiple WordPress Instances](before-install/multiple-instances.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/multiple-instances/))
    - [Install WordPress at popular providers](before-install/popular-providers.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/popular-providers/))
  - [Server configuration](server/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/))
    - [Changing File Permissions](server/file-permissions.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/file-permissions/))
    - [Finding Server Info](server/server-info.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/server-info/))
    - [Giving WordPress Its Own Directory](server/wordpress-in-directory.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/wordpress-in-directory/))
    - [Configuring Wildcard Subdomains](server/subdomains-wildcard.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/subdomains-wildcard/))
    - [Emptying a Database Table](server/empty-database.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/empty-database/))
    - [Web servers](server/web-server.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/web-server/))
      - [nginx](server/nginx.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/web-server/nginx/))
    - [Control Panels](server/control-panel.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/control-panel/))
  - [WordPress configuration](wordpress/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/))
    - [wp-config.php](wordpress/wp-config.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/))
    - [Site Architecture](wordpress/site-architecture.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/site-architecture/))
    - [Cookies](wordpress/cookies.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/cookies/))
    - [Update Services](wordpress/update-services.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/update-services/))
    - [Editing Files](wordpress/edit-files.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/edit-files/))
    - [CSS](wordpress/css.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/css/))
    - [Feeds](wordpress/feeds.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/feeds/))
    - [Multilingual WordPress](wordpress/multilingual.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/multilingual/))
    - [oEmbed](wordpress/oembed.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/oembed/))
    - [Loopbacks](wordpress/loopback.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/loopback/))
    - [Common errors](wordpress/common-errors.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/common-errors/))
  - [Upgrading / Migration](upgrade/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/upgrade/))
    - [FTP Clients](upgrade/ftp.md) ([🔗](https://developer.wordpress.org/advanced-administration/upgrade/ftp/))
      - [Using FileZilla](upgrade/filezilla.md) ([🔗](https://developer.wordpress.org/advanced-administration/upgrade/ftp/filezilla/))
    - [phpMyAdmin](upgrade/phpmyadmin.md) ([🔗](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/))
    - [Upgrading](upgrade/upgrading.md) ([🔗](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/))
    - [Migration](upgrade/migrating.md) ([🔗](https://developer.wordpress.org/advanced-administration/upgrade/migrating/))
  - [WordPress Multisite / Network](multisite/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/))
    - [Before You Create A Network](multisite/prepare-network.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/))
    - [Create A Network](multisite/create-network.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/create-network/))
    - [WordPress Multisite Domain Mapping](multisite/domain-mapping.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/domain-mapping/))
    - [Multisite Network Administration](multisite/administration.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/administration/))
    - [Network Admin](multisite/admin.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/admin/))
    - [Migrate WordPress sites into WordPress Multisite](multisite/sites-multisite.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/sites-multisite/))
  - [Plugins](plugins/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/plugins/))
    - [Must Use Plugins](plugins/mu-plugins.md) ([🔗](https://developer.wordpress.org/advanced-administration/plugins/mu-plugins/))
  - [Themes](themes/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/themes/))
  - [Security](security/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/))
    - [Your password](security/logging-in.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/logging-in/))
    - [Multi-Factor Authentication](security/mfa.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/mfa/))
    - [Backups](security/backup.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/backup/))
      - [Database Backup](security/backup.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/backup/database/))
      - [Files Backup](security/backup.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/backup/files/))
    - [HTTPS](security/https.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/https/))
    - [Brute Force Attacks](security/brute-force.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/brute-force/))
    - [Hardening WordPress](security/hardening.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/hardening/))
  - [Performance / Optimization](performance/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/performance/))
    - [Cache](performance/cache.md) ([🔗](https://developer.wordpress.org/advanced-administration/performance/cache/))
    - [Optimization](performance/optimization.md) ([🔗](https://developer.wordpress.org/advanced-administration/performance/optimization/))
  - [Debugging WordPress](debug/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/debug/))
    - [Debugging in WordPress](debug/debug-wordpress.md) ([🔗](https://developer.wordpress.org/advanced-administration/debug/debug-wordpress/))
    - [Debugging a WordPress Network](debug/debug-network.md) ([🔗](https://developer.wordpress.org/advanced-administration/debug/debug-network/))
    - [Using Your Browser to Diagnose JavaScript Errors](debug/debug-javascript.md) ([🔗](https://developer.wordpress.org/advanced-administration/debug/debug-javascript/))
    - [Test Driving WordPress](debug/test-driving.md) ([🔗](https://developer.wordpress.org/advanced-administration/debug/test-driving/))
  - [Resources](resources/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/resources/))

## Recommendations

### Style guide

- [WordPress Documentation Style Guide](https://make.wordpress.org/docs/style-guide/)
  - [General guidelines](https://make.wordpress.org/docs/style-guide/general-guidelines/)
  - [Language and grammar](https://make.wordpress.org/docs/style-guide/language-grammar/)
  - [Punctuation](https://make.wordpress.org/docs/style-guide/punctuation/)
  - [Formatting](https://make.wordpress.org/docs/style-guide/formatting/)
  - [Linking](https://make.wordpress.org/docs/style-guide/linking/)
  - [Developer content](https://make.wordpress.org/docs/style-guide/developer-content/)

### External linking

- [External Linking Policy (Summary)](https://make.wordpress.org/docs/handbook/documentation-team-handbook/external-linking-policy/)

### Example domains

When using an example domain, please, use _example.com_, _example.net_, _example.org_, _example.info_, or _example.biz_ (and whatever subdomain you want).

The country domains have their own example names, like _dominio.es_, that probably need check.

See [RFC 2606](https://www.rfc-editor.org/rfc/rfc2606), [.INFO Reserved Strings](https://www.icann.org/en/registry-agreements/info/info-registry-agreement--list-of-reserved-tld-strings-26-5-2010-en), [.BIZ Reserved Strings](https://www.icann.org/en/registry-agreements/biz/biz-registry-agreement--list-of-reserved-tld-strings-19-6-2009-en).

### Example IP / IP network / IP range

When using IP addresses, please use:

- TEST-NET-1: 192.0.2.0/24 (192.0.2.0–192.0.2.255)
- TEST-NET-2: 198.51.100.0/24 (198.51.100.0–198.51.100.255)
- TEST-NET-3: 203.0.113.0/24 (203.0.113.0–203.0.113.255)

See [RFC 1166](https://datatracker.ietf.org/doc/html/rfc1166).
