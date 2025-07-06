# Upgrading WordPress

## Upgrading WordPress – Extended Instructions

This page contains a more detailed version of [the upgrade instructions](https://wordpress.org/documentation/article/updating-wordpress/).

### Back up WordPress {#backup-up-wordpress}

Before you get started, it’s a good idea to back up your website. This means if there are any issues you can restore your website. Complete instructions to make a backup can be found in the WordPress Backup.

### One-click Upgrade {#one-click-upgrade}

WordPress lets you update with the click of a button.  You can launch the update by clicking the link in the new version banner (if it’s there) or by going to the Dashboard > Updates screen. Once you are on the “Update WordPress” page, click the button “Update Now” to start the process off. You shouldn’t need to do anything else and, once it’s finished, you will be up-to-date.

One-click updates work on most servers. If you have any problems, it is probably related to permissions issues on the filesystem.

#### Hosting Services Tools {#hosting-services-tools}

You may have access to WP-Toolkit if your websites are hosted in cPanel (Look for "WordPress Management" in the left menu bar) or Plesk (Look for "WordPress" in the left menu bar). You can perform one-click update of your WordPress websites inside WP-Toolkit. You will also have the ability to configure automatic updates in WP-Toolkit. 

If your websites are hosted in a WP Squared server, you will see a notification and a button to perform one-click update of your WordPress website when a new update is available. Automatic update is enabled by default, and you have the ability to configure whether to enable or disable automatic updates in WP Squared.

_Hosting providers: If your tools are missing here, feel free to create a pull request in Github to add it._

### Manual Upgrade {#manual-upgrade}

#### Overview of the Upgrade Process {#overview-of-the-upgrade-process}

1. [Backup your database](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-1-back-up-your-database).
2. [Backup ALL your WordPress files](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-2-back-up-all-your-wordpress-files) in your WordPress directory. Don't forget your [`.htaccess`](https://wordpress.org/documentation/article/wordpress-glossary/#.htaccess) file.
3. [Verify the backups](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-3-verify-the-backups) you created are there and usable. This is essential.
4. [Deactivate ALL your Plugins](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-4-deactivate-all-your-plugins).
5. [Ensure first four steps are completed](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-5-ensure-first-four-steps-are-completed). Do not attempt the upgrade unless you have completed the first four steps.
6. [Download and extract the WordPress package](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-6-download-and-extract-the-wordpress-package) from [https://wordpress.org/download/](https://wordpress.org/download/).
7. [Delete the old WordPress files](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-7-delete-the-old-wordpress-files) on your site, but **DO NOT DELETE**  
		– `wp-config.php` file;
		– `wp-content` folder; Special Exception: the `wp-content/cache` and the `wp-content/plugins/widgets` folders should be deleted.
		– `.htaccess` file–if you have added custom rules to your `.htaccess`, do not delete it;
		– `robots.txt` file–if your blog lives in the root of your site (ie. the blog is the site) and you have created such a file, do not delete it.

8. [Upload the new files](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-8-upload-the-new-files) from your computer's hard drive to the appropriate WordPress folder on your site.
9. [Run the WordPress upgrade program](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-9-run-the-wordpress-upgrade-program) and follow the instructions on the screen.
10. [Update Permalinks and .htaccess](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-10-update-permalinks-and-htaccess).
11. [Install updated Plugins and Themes](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-11-install-updated-plugins-and-themes).
12. [Reactivate Plugins](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-12-reactivate-plugins)
13. [Review what has changed in WordPress](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/#step-14-review-what-has-changed-in-wordpress).

That's the overview of the upgrade process. Please continue reading the Detailed Upgrade Instructions.

Remember, if you do encounter problems, re-read the Instructions below to insure you've followed the proper procedures and consult [Troubleshooting: Common Installation Problems](https://developer.wordpress.org/advanced-administration/before-install/howto-install/#common-installation-problems).

#### Upgrading Across Multiple Versions {#upgrading-across-multiple-versions}

_While the methodology given below is the "safe" approach, as long as you have proper backups, then it is indeed possible to upgrade directly from the very first version of WordPress to the very latest version in one-easy-step. WordPress does support this process, and WordPress is extremely backwards compatible in this respect. That said, if you have a large site, the upgrade process may take longer than expected, in which case an incremental approach may help. Just remember to retain a backup of a working site so that you always have a fallback position._

If you plan on upgrading across more than **two** major releases, you should consider upgrading incrementally to avoid potential conflicts and minimize the risks of database damage. Older versions of WordPress can be downloaded from the [release archive](https://wordpress.org/download/release-archive/).

WordPress 3.7 introduced an easy to use one-button updater which will take you directly to Current Version. This update step is safe, and it is possible to one-click update from 3.7 to any later version.

##### Upgrading from WordPress 0.7 - 3.6 (by migration)

Goals:
- WordPress: upgrade to WordPress 6.2
- PHP: upgrade to PHP 7.4
- SQL: upgrade to MySQL 8.0 / MariaDB 10.11

Losses:
- Content: none
- Plugins: all
- Themes: all

These are the oldest versions of WordPress and the ones that have not been supported for years. In general, have to assume some losses, although not of the contents, but probably of some functionality on themes and plugins.

Considering that the goal is to keep the contents and assuming the loss of the rest of the elements, there are some steps.

As with any upgrade, the first thing to do is to make a backup copy. The best way to upgrade from WP < 3.6 is to perform a content migration. 

1. Create a brand-new WordPress, without the database.
2. Copy the old WordPress files from the "/wp-content/uploads/" content to the new one.
3. Create a new database with the old database information. The best way is using "mysqldump".
4. Configure the wp-config.php with all the new data.
5. Access the "/wp-admin/" page, and follow the upgrading process.

With this way, WordPress will be able to maintain and update the contents in the database and be able to work with these contents in an updated version of WordPress.

A WordPress with the default theme, and all the contents should now be available.

Character Encoding commonly presents technical hiccups when restoring a database. It is possible that backup data is not encoded in UTF-8 and instead may be in an ISO or ASCII "deprecated" format. Make sure that the character encoding is updated correctly upon restoring a database! More information on [converting Character Sets in a WordPress database can be found here](https://codex.wordpress.org/Converting_Database_Character_Sets).

##### Upgrading from WordPress 3.7 - 4.0

Goals
- WordPress: upgrade to WordPress 4.1
- PHP: upgrade to PHP 5.6.20+
- SQL: upgrade to MySQL 5.6 / MariaDB 10.0

Losses:
- Content: none
- Plugins: probably yes
- Themes: probably yes

WordPress Versions <= 4.0 are compatible with PHP versions that are hardly available today. They can range from PHP 5.2 (or even earlier) to PHP 5.5. That is why the main goal will be to go to a version that is still easy to get on many operating systems.

The same will happen with the database. It is very likely that there is a MySQL 5.5 or earlier. Depending on whether want to continue with MySQL or move to MariaDB, choose which way to go and migrate the database to a MySQL 5.6 or MariaDB 10.0.

Note that WP-CLI is not available for PHP versions lower than PHP 5.6.20, so this process still must be done somewhat manually.

As with any upgrade, the first thing to do is to make a backup copy.

Remove all themes that are not active, leaving only the main theme. If there is a child theme active, please, maintain the child and parent.

Install and activate the [Twenty Ten](https://wordpress.org/themes/twentyten/) theme and activate it. This theme works in all sites since WordPress 3.7.

In the same way, delete all deactivated plugins (and, therefore, not working).

Deactivate all left active plugins.

Now, WordPress will have:
-	Core: any version (between WordPress 3.7 and WordPress 4.0)
-	Themes: Twenty Ten is active, and the main theme is deactivated.
-	Plugins: all plugins that should be active are deactivated.

At this point, overwrite the WordPress Core with [WordPress 4.1](https://wordpress.org/wordpress-4.1.zip), available in the [release list](https://wordpress.org/download/releases/). Install WordPress 4.1 major version or, if available and recommended, the latest 4.1.x minor version.

Upgrade the systems up to PHP 5.6.20+ and MySQL 5.6.x or MariaDB 10.0.x. Please, do not install the newest major version.

Access the "/wp-admin/" page, and follow the upgrading process.

WordPress will be able to maintain and update the contents in the database and be able to work with these contents. WordPress, with the default theme and all the contents should now be available and working.

Character Encoding commonly presents technical hiccups when restoring a database. It is possible that backup data is not encoded in UTF-8 and instead may be in an ISO or ASCII "deprecated" format. Make sure that the character encoding is updated correctly upon restoring a database! More information on [converting Character Sets in a WordPress database can be found here](https://codex.wordpress.org/Converting_Database_Character_Sets).

Proceed to the next step, which is upgrade to WordPress 4.9 from WordPress 4.1.

##### Upgrading from WordPress 4.1 - 4.8

Goals
- WordPress: upgrade to WordPress 4.9
- PHP: upgrade to PHP 7.2
- SQL: maintain or upgrade to MySQL 5.6 / MariaDB 10.0

Losses:
- Content: none
- Plugins: probably yes
- Themes: probably yes

_If you don't have PHP 5.6.20+ configured yet, do it. Chances are that everything will still work normally._

From WordPress 4.1 and PHP 5.6.20+, you can continue with the manual update process, or start using [WP-CLI](https://wp-cli.org/), the tool to run WordPress commands directly via console, something that can easy the process.

As with any upgrade, the first thing to do is to make a backup copy.

Remove all themes that are not active, leaving only the main theme. If there is a child theme active, please, maintain the child and parent.

Install and activate the [Twenty Ten](https://wordpress.org/themes/twentyten/) theme and activate it. This theme works in all sites since WordPress 3.7.

In the same way, delete all deactivated plugins (and, therefore, not working).

Now, WordPress will have:
-	Core: any version (between WordPress 4.1 and WordPress 4.8)
-	Themes: Twenty Ten is active, and the main theme is deactivated.
-	Plugins: all plugins that should be active are deactivated.

At this point, overwrite the WordPress Core with [WordPress 4.9](https://wordpress.org/wordpress-4.9.zip), available in the [release list](https://wordpress.org/download/releases/). Install WordPress 4.9 major version or, if available and recommended, the latest 4.9.x minor version.

Upgrade the systems up to PHP 7.2 and, if they are not already, to MySQL 5.6.x or MariaDB 10.0.x. Please, do not install the newest major version.

Access the "/wp-admin/" page, and follow the upgrading process.

WordPress will be able to maintain and update the contents in the database and be able to work with these contents. WordPress, with the default theme and all the contents should be available and working.

Character Encoding commonly presents technical hiccups when restoring a database. It is possible that backup data is not encoded in UTF-8 and instead may be in an ISO or ASCII "deprecated" format. Make sure that the character encoding is updated correctly upon restoring a database! More information on [converting Character Sets in a WordPress database can be found here](https://codex.wordpress.org/Converting_Database_Character_Sets).

Proceed to the next step, which is upgrade to WordPress 5.3 from WordPress 4.9.

##### WordPress 4.9 - 5.2

Goals
- WordPress: upgrade to WordPress 5.3
- PHP: upgrade to PHP 7.4
- SQL: maintain or upgrade to MySQL 8.0 / MariaDB 10.3

Losses:
- Content: none
- Plugins: probably no
- Themes: probably no

_If you don't have PHP 7.4 configured yet, do it. Chances are that everything will still work normally._

WordPress 4.9 was the last version with the Classic Editor, so, a lot of people, afraid of the new editor, stopped updating WordPress. WordPress 5.0+ is fully compatible with the Classic Editor content, so it can be upgraded without losing any content.

Also, when WordPress 4.9 was released, PHP 7.0+ was very stablished and WordPress 5.0 version had support. Upgrading from PHP 5.6.20+ to PHP 7.0+ should be very stable.

From WordPress 4.9, you can continue with the manual update process, or start using [WP-CLI](https://wp-cli.org/), the tool to run WordPress commands directly via console, something that can ease the process.

As with any upgrade, the first thing to do is to make a backup copy.

Remove all themes that are not active, leaving only the main theme. If there is a child theme active, please, maintain the child and parent.

Install and activate the [Twenty Ten](https://wordpress.org/themes/twentyten/) theme and activate it. This theme works in all sites since WordPress 3.7.

In the same way, delete all deactivated plugins.

Now, WordPress will have:
-	Core: any version (between WordPress 4.9 and WordPress 5.2)
-	Themes: Twenty Ten is active, and the main theme is deactivated.
-	Plugins: all plugins that should be active are deactivated.

At this point, overwrite the WordPress Core with [WordPress 5.3](https://wordpress.org/wordpress-5.3.zip), available in the [release list](https://wordpress.org/download/releases/). Install WordPress 5.3 major version or, if available and recommended, the latest 5.3.x minor version.

Upgrade the systems up to PHP 7.4 and, if they are not already, to MySQL 8.0.x or MariaDB 10.3.x. Please, do not install the newest major version.

Access the "/wp-admin/" page, and follow the upgrading process.

WordPress will be able to maintain and update the contents in the database and be able to work with these contents.

Getting this moment, make a new backup copy, because some more updates will be made and, at this point, there is a good WordPress situation.

Most of the plugins available in WordPress 4.9+ should work with WordPress 5.3, so try to update everything available in the plugin list. Please, do it one by one and check all the warnings and errors. If you get some big error, try an older release for this plugin. Usually they are at the end of the "Developer" tab in each plugin page at wordpress.org.

Try the same with the theme. Most of the themes available in WordPress 4.9+ should work with WordPress 5.3.

Proceed to the next step, which is upgrade to WordPress 6.2 from WordPress 5.3.

##### WordPress 5.3 - 6.2

Goals
- WordPress: upgrade to WordPress 6.2
- PHP: upgrade to PHP 7.4
- SQL: maintain or upgrade to MySQL 8.0 LTS / MariaDB 10.11 LTS

Losses:
- Content: none
- Plugins: probably no
- Themes: probably no

_If you don't have PHP 7.4 configured yet, do it. Chances are that everything will still work normally._

Upgrade everything normally. Everything should work fine.

##### WordPress 6.3 - 6.8

Goals
- WordPress: upgrade to WordPress 6.8
- PHP: upgrade to at least PHP 8.1 (WordPress 6.6+ supports PHP 8.2)
- SQL: maintain or upgrade to MySQL 8.0 LTS / MariaDB 10.11 LTS

Losses:
- Content: none
- Plugins: probably no
- Themes: probably no

_If you don't have PHP 8.1 configured yet, do it. Chances are that everything will still work normally._

When WordPress 6.3 was released, support for PHP 5.6 dropped and PHP 7.0 was stablished as the minimum PHP version. Upgrading from PHP 5.6.20+ to PHP 7.0+ should be very stable.

When WordPress 6.6 was released, support for PHP 7.0 and 7.1 dropped and PHP 7.2.24 was stablished as the minimum PHP version. Upgrading from PHP 7.0+, or PHP 7.1+ to PHP 7.2+ should be very stable. WordPress 6.6 also supports PHP 8.2 so you can try switching to PHP 8.2 when upgraded WordPress. 

Upgrade everything normally. Everything should work fine.

### Troubleshooting {#troubleshooting}

**Scrambled Layout or Errors**

If your blog looks scrambled now or features line errors, an old plugin that doesn't work with the new code may be the culprit. In your WordPress [Administration Screen](https://wordpress.org/documentation/article/administration-screens), deactivate all plugins that do not come with WordPress by default. Re-activate them one by one.

**Made Custom Changes/Hacks?**

If you have made changes to other WordPress files ("hacked" WordPress), you are supposed to keep track of your changes. You will have to transfer your edits into the new code. [WordPress Versions](https://codex.wordpress.org/WordPress_Versions) lists the files that have changed in each release.

**Resist Using Old Code**

Upgrading gives you the newest and best code. Using your old code, no matter how much you have customised it, almost certainly will cause problems. The temptation just to use your old modified code will be great, but the chances of errors are much greater.

**Can I Go Back to Old Versions**

You can, but it is usually not recommended to rollback (revert) your current version to an older version. That is because newer versions often include security updates and a rollback may put your site at risk. Second, the change between the database structure between versions may cause complications in maintaining your site content, posts, comments, and plugins that are dependent upon the information stored in the database. If you are still intent on this, proceed at your own risk. **Please note, that without a backup of your entire site and your database, made prior to your upgrade attempt, a successful rollback is near impossible.** Delete all WordPress files except for `wp-config`. [Upload](https://developer.wordpress.org/advanced-administration/upgrade/ftp/filezilla/) the files from your backup to your server and [restore your database backup](https://wordpress.org/article/restoring-your-database-from-backup/). Remember, you must have good backups for the rollback to work. For older WordPress versions, a rollback might not work.

**Get More Help**

If you get any errors following an upgrade, check [Troubleshooting: Common Installation Problems](https://developer.wordpress.org/advanced-administration/before-install/howto-install/#common-installation-problems), [Troubleshooting](https://codex.wordpress.org/Troubleshooting), and the [Installation Category of Articles](https://wordpress.org/documentation/category/installation/). If you can't find an answer, post a clear question on the [WordPress Support Forums](https://wordpress.org/support/forums/). You will be asked if you have used any old code. You'll be told to change it then, so you may as well change it now 🙂.

## Configuring Automatic Background Updates

### Update Types {#update-types}

Automatic background updates were introduced in [WordPress 3.7](https://wordpress.org/documentation/wordpress-version/version-3-7/) in an effort to promote better security, and to streamline the update experience overall. By default, automatic updates of WordPress are enabled on most sites. In special cases, plugins and themes may be automatically updated as well. Translation files are auto-updated by default.

In WordPress, there are four types of automatic background updates:

1. Core updates
2. Plugin updates
3. Theme updates
4. Translation file updates

#### Core Updates {#core-updates}

Core updates are subdivided into three types:

1. Core development updates, known as the "bleeding edge"
2. Minor core updates, such as maintenance and security releases
3. Major core release updates

Before WordPress 5.6, by default, every site had automatic updates enabled for minor core releases and translation files only. Starting WordPress 5.6 and above, every new site has automatic enabled for both minor and major releases.

Sites already running a development version also have automatic updates to further development versions enabled by default.

### Update Configuration {#update-configuration}

Automatic updates can be configured using one of two methods: defining constants in `wp-config.php`, or adding filters using a Plugin.

#### Configuration via wp-config.php {#configuration-via-wp-config-php}

Using `wp-config.php`, automatic updates can be disabled completely, and core updates can be disabled or configured based on update type.

##### Constant to Disable All Updates {#constant-to-disable-all-updates}

The core developers made a conscious decision to enable automatic updates for minor releases and translation files out of the box. Going forward, this will be one of the best ways to guarantee your site stays up to date and secure and, as such, disabling these updates is strongly discouraged.

To completely disable all types of automatic updates, core or otherwise, add the following to your `wp-config.php` file:

```
define( 'AUTOMATIC_UPDATER_DISABLED', true );
```

##### Constant to Configure Core Updates {#constant-to-configure-core-updates}

To enable automatic updates for major releases or development purposes, the place to start is with the `WP_AUTO_UPDATE_CORE` constant. Defining this constant one of three ways allows you to blanket-enable, or blanket-disable several types of core updates at once.

```
define( 'WP_AUTO_UPDATE_CORE', true );
```

`WP_AUTO_UPDATE_CORE` can be defined with one of three values, each producing a different behavior:

* Value of `true` – Development, minor, and major updates are all **enabled**
* Value of `false` – Development, minor, and major updates are all **disabled**
* Value of `'minor'` – Minor updates are **enabled**, development, and major updates are **disabled**

Note that only sites already running a development version will receive development updates.

For development sites, the default value of `WP_AUTO_UPDATE_CORE` is `true`. For other sites, the default value of `WP_AUTO_UPDATE_CORE` is `minor`.

Starting WordPress 5.6, the default value of `WP_AUTO_UPDATE_CORE` for new WordPress installations is `true`. For new website, the default value of `WP_AUTO_UPDATE_CORE` is `minor`.

#### Configuration via Filters {#configuration-via-filters}

Using filters allows for fine-tuned control of automatic updates.

The best place to put these filters is in a [must-use plugin](https://developer.wordpress.org/advanced-administration/plugins/mu-plugins/).

Do _not_ add `add_filter()` calls directly in `wp-config.php`. WordPress isn't fully loaded and can cause conflicts with other applications such as WP-CLI.

##### Disabling All Updates Via Filter {#disabling-all-updates-via-filter}

You can also disable all automatic updates using the following filter:

```
add_filter( 'automatic_updater_disabled', '__return_true' );
```

##### Core Updates via Filter {#core-updates-via-filter}

To enable all core-type updates only, use the following filter:

```
add_filter( 'auto_update_core', '__return_true' );
```

But let's say rather than enabling or disabling all three types of core updates, you want to selectively enable or disable them. That's where the `allow_dev_auto_core_updates`, `allow_minor_auto_core_updates`, and `allow_major_auto_core_updates` filters come in.

There are two shorthand functions built into WordPress that will allow you to enable or disable specific types of core updates with single lines of code. They are [__return_true](https://developer.wordpress.org/reference/functions/__return_true) and [__return_false](https://developer.wordpress.org/reference/functions/__return_false). Here are some example filters:

To specifically _enable_ them individually (for disabling, use **false** instead of **true**):

```
add_filter( 'allow_dev_auto_core_updates', '__return_true' ); // Enable development updates
add_filter( 'allow_minor_auto_core_updates', '__return_true' ); // Enable minor updates
add_filter( 'allow_major_auto_core_updates', '__return_true' ); // Enable major updates
```

For Developers: To _enable_ automatic updates even if a VCS folder (.git, .hg, .svn etc) was found in the WordPress directory or any of its parent directories:

```
add_filter( 'automatic_updates_is_vcs_checkout', '__return_false', 1 );
```

##### Plugin & Theme Updates via Filter {#plugin-theme-updates-via-filter}

By default, automatic background updates only happen for plugins and themes in special cases, as determined by the WordPress.org API response, which is controlled by the WordPress security team for patching critical vulnerabilities. To enable or disable updates in all cases, you can leverage the `auto_update_$type` filter, where `$type` would be replaced with "plugin" or "theme".

Automatic updates for All plugins

```
add_filter( 'auto_update_plugin', '__return_true' );
```

Automatic updates for All themes:

```
add_filter( 'auto_update_theme', '__return_true' );
```

You can use `__return_false` instead of `__return_true` to specifically disable all plugin & theme updates, even forced security pushes from the WordPress security team.

The `auto_update_$type` filters also allow for more fine-grained control, as the specific item to updated is also passed into the filter. If you wanted to enable auto-updates for specific plugins only, then you could use code like this:

```
function auto_update_specific_plugins ( $update, $item ) {
	// Array of plugin slugs to always auto-update
	$plugins = array (
		'akismet',
		'buddypress',
	);
	if ( in_array( $item->slug, $plugins ) ) {
		 // Always update plugins in this array
		return true;
	} else {
	 	// Else, use the normal API response to decide whether to update or not
		return $update;
	}
}
add_filter( 'auto_update_plugin', 'auto_update_specific_plugins', 10, 2 );
```

##### Translation Updates via Filter {#translation-updates-via-filter}

Automatic translation file updates are already enabled by default, the same as minor core updates.

To disable translation file updates, use the following:

```
add_filter( 'auto_update_translation', '__return_false' );
```

##### Disable Emails via Filter {#disable-emails-via-filter}

```
// Disable update emails
add_filter( 'auto_core_update_send_email', '__return_false' );
```

This filter can also be used to manipulate update emails according to email $type (success, fail, critical), update type object $core_update, or $result:

```
/* @param bool   $send        Whether to send the email. Default true.
@param string $type        The type of email to send. Can be one of 'success', 'fail', 'critical'.
@param object $core_update The update offer that was attempted.
@param mixed  $result      The result for the core update. Can be WP_Error.
*/
apply_filters( 'auto_core_update_send_email', true, $type, $core_update, $result );
```

### Resources {#resources}

* More examples at [https://make.wordpress.org/core/2013/10/25/the-definitive-guide-to-disabling-auto-updates-in-wordpress-3-7/](https://make.wordpress.org/core/2013/10/25/the-definitive-guide-to-disabling-auto-updates-in-wordpress-3-7/)
* More information here: [How Do I Configure Automatic Updates in WordPress 3.7?](https://wordpress.stackexchange.com/questions/120081/how-do-i-configure-automatic-updates-in-wordpress-3-7)
* Info about wp-cli conflict: [https://github.com/wp-cli/wp-cli/issues/1310](https://github.com/wp-cli/wp-cli/issues/1310)

## Changelog

- 2024-06-05: Original content from [Upgrading WordPress](https://make.wordpress.org/hosting/handbook/upgrading/)
- 2022-10-25: Original content from [Configuring Automatic Background Updates](https://wordpress.org/documentation/article/configuring-automatic-background-updates/), and [Upgrading WordPress – Extended Instructions](https://wordpress.org/documentation/article/upgrading-wordpress-extended-instructions/).
