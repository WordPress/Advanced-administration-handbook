# Upgrading WordPress

## Upgrading WordPress – Extended Instructions

This page contains a more detailed version of [the upgrade instructions](https://wordpress.org/documentation/article/updating-wordpress/).

### Detailed Instructions {#detailed-instructions}

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
		– `wp-images` folder;
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

##### Step 1: Back up your database {#step-1-back-up-your-database}

Perform a backup of your database. All of your WordPress data, such as Users, Posts, Pages, Links, and Categories, are stored in your [MySQL](https://wordpress.org/documentation/article/glossary#mysql) [database](https://codex.wordpress.org/Database_Description). Please read [Backing Up Your Database](https://wordpress.org/article/backing-up-your-database/) for a detailed explanation of this process.

It is extremely important to back up your database before beginning the upgrade. If, for some reason, you find it necessary to revert back to the ‘old' version of WordPress, you may have to restore your database from these backups.

##### Step 2: Back up ALL your WordPress files {#step-2-back-up-all-your-wordpress-files}

Back up ALL of your files in your WordPress directory and your [`.htaccess`](https://wordpress.org/documentation/article/wordpress-glossary/#.htaccess) file. Typically, this process involves using an [FTP program](https://developer.wordpress.org/advanced-administration/upgrade/ftp/) to download ALL your WordPress files from your host to your local computer.

Please read [Backing Up Your WordPress Site](https://developer.wordpress.org/advanced-administration/security/backup/#backing-up-your-wordpress-site) for further explanation.

If you have made changes to any core WordPress files, or if you've got customized Plugins or Themes, you will want to have a good backup of those files. It is extremely important to back up your files before beginning the upgrade. If for some reason you find it necessary to revert back to the ‘old' version of WordPress you will need to upload these files.

##### Step 3: Verify the backups {#step-3-verify-the-backups}

Verify that the backups you created are there and usable. **This is the most important step in the upgrade process!**

The verification process involves making sure you can see the backup files on your local computer (or wherever you've stored them) and that you can navigate into any sub-folders. If the files are in a zip file, make sure you can open the zip file. Also consider opening a _.sql_ file in an [editor](https://wordpress.org/documentation/article/glossary#text-editor) to see if the tables and data are represented.

##### Step 4: Deactivate ALL your Plugins {#step-4-deactivate-all-your-plugins}

In your [Administration Screen](https://wordpress.org/documentation/article/administration-screens/), under the Plugins choice, deactivate any Plugins. Because of the changes to WordPress, some Plugins may conflict with the upgrade process. If you're not able to access the administrative menus you can deactivate all plugins by [resetting the plugins folder](https://wordpress.org/documentation/article/faq-troubleshooting/#how-to-deactivate-all-plugins-when-not-able-to-access-the-administrative-menus).

##### Step 5: Ensure first four steps are completed {#step-5-ensure-first-four-steps-are-completed}

If you have not completed the first four procedures, STOP, and do them! Do not attempt the upgrade unless you have completed the first four steps.

The best resource for problems with your upgrade is the [WordPress Support Forums](https://wordpress.org/support/forums/), and if you have problems, the volunteers at the [WordPress Support Forums](https://wordpress.org/support/forums/) will likely ask if you have completed the first four steps.

##### Step 6: Download and extract the WordPress package {#step-6-download-and-extract-the-wordpress-package}

Download and unzip the WordPress package from [https://wordpress.org/download/](https://wordpress.org/download/).

* If you will be uploading WordPress to a remote web server, download the WordPress package to your computer with your favorite web browser and unzip the package.
* If you have [shell](https://wordpress.org/documentation/article/glossary#shell) access to your web server, and are comfortable using console-based tools, you may wish to download WordPress directly to your [web server](https://wordpress.org/documentation/article/glossary#web-server). You can do so using `wget` , `lynx` or another console-based web browser, which are valuable if you want to avoid [FTPing](https://wordpress.org/documentation/article/wordpress-glossary/#FTP). Place the package in a directory parallel to your current wordpress directory (like "uploads," for example). Then, unzip it using: `gunzip -c wordpress-_Version_.tar.gz | tar -xf -` or by using: `tar -xzvf latest.tar.gz`

The WordPress package will be extracted into a folder called `wordpress`.

##### Step 7: Delete the old WordPress files {#step-7-delete-the-old-wordpress-files}

**Why Delete?** Generally, it is a good idea to delete whatever is possible because the uploading (or upgrading through cPanel) process may not correctly overwrite an existing file and that may cause problems later.

**DO NOT DELETE these folders and files:**

* `wp-config.php` file;
* `wp-content` folder;
* `wp-includes/languages/` folder–if you are using a language file, and it is here rather than in `wp-content/languages/`, do not delete this folder (you might want to move your language files to `wp-content/languages/` for easier upgrading in the future);.
* `.htaccess` file–if you have added custom rules to your `.htaccess`, do not delete it;
* Custom Content and/or Plugins–if you have any images or other custom content or Plugins inside the `wp-content` folder, do NOT delete them.

**Delete these Files and Folders:**

* `wp-*` (except for those above), `readme.html`, `wp.php`, `xmlrpc.php`, and `license.txt` files; Typically files in your root or wordpress folder. Again, don't delete the `wp-config.php` file. **Note**: some files may not exist in later versions.
* `wp-admin` folder;
* `wp-includes` folder;
* `wp-content/plugins/widgets` folder; You only see this folder if you previously installed the Sidebar Widgets plugin. The Sidebar Widgets code conflicts with the built-in widget ability.

**How to Delete?** There are several ways to delete the files from your WordPress site. You can use your FTP Client, or if you have access to SSH you can use that. Some host providers also provide the ability to delete files and folders.

**Using FTP to delete files and folders**

The same [FTP client](https://developer.wordpress.org/advanced-administration/upgrade/ftp/) you use for [uploading](https://developer.wordpress.org/advanced-administration/upgrade/ftp/filezilla/) can be used to delete files and folders. If your [FTP client](https://developer.wordpress.org/advanced-administration/upgrade/ftp/) does not appear to permit you to delete non-empty folders, check the available options for your [FTP client](https://developer.wordpress.org/advanced-administration/upgrade/ftp/). You'll usually find an option that permits deleting non-empty folders. Deleting non-empty folders is a quick and thorough method cleaning out an old installation of WordPress. It is recommended that once the deleting is done, you switch back to the original setting for safety reasons.

**Using SSH to delete file**

If you have a command-line login (ssh), you can enter the following commands to make backup copies of the files you need to keep and to delete ONLY the wordpress files in your directory (plus .htaccess). If you've customized other files (like `index.php`) not included by the `cp` commands below, copy them as well:

```
$ mkdir backup
cp wp-config.php .htaccess backup
cp -R wp-content backup
rm wp*.php .htaccess license.txt readme.html xmlrpc.php
rm -rf wp-admin wp-includes
cp backup/wp-config.php .
```

After you have finished with the upgrade, you can restore any customizations to your templates or plugins from your backup directory. For example, use `cp backup/index.php .` to restore `index.php`.

Alternatively, using SSH, you could copy `wp-config.php, .htaccess`, and any content files you've added or altered into the _new_ wordpress directory. Then, rename the old one (to archive it), and move the new one into its place.

##### Step 8: Upload the new files {#step-8-upload-the-new-files}

With the new upgrade on your local computer, and using [FTP](https://wordpress.org/documentation/article/glossary#ftp), [upload](https://developer.wordpress.org/advanced-administration/upgrade/ftp/filezilla/) the new files to your site server just as you did when you first installed WordPress. See [Using FileZilla](https://developer.wordpress.org/advanced-administration/upgrade/ftp/filezilla/) and [Uploadi](https://codex.wordpress.org/Uploading_WordPress_to_a_remote_host)[n](https://developer.wordpress.org/advanced-administration/upgrade/ftp/filezilla/)[g WordPress to a remote host](https://codex.wordpress.org/Uploading_WordPress_to_a_remote_host) for detailed guidelines in using an FTP Client to upload.

**NOTE: If you did not delete the `wp-content` folder, you will need to overwrite some files during the upload.**

The `wp-content` folder holds your WordPress Themes and Plugins. These should remain. Upload everything else first, then upload only those WordPress files that are new or changed to your new `wp-content` folder. Overwrite any old versions of default plugins with the new ones.

The WordPress default theme has changed so you will want to upload the `wp-content/themes/default` folder. If you have custom changes to the default theme, those changes will need to be reviewed and installed after the upgrade.

##### Step 9: Run the WordPress upgrade program {#step-9-run-the-wordpress-upgrade-program}

Using a web browser, go to the WordPress admin pages at the normal /wp-admin location. WordPress will check to see if a database upgrade is necessary, and if it is, it will give you a new link to follow.

This link will lead you to run the WordPress upgrade script by accessing `wp-admin/upgrade.php`. Follow the instructions presented on your screen.

Note: Make sure the database user name registered to WordPress has permission to create, modify, and delete database tables before you do this step. If you installed WordPress in the standard way, and nothing has changed since then, you are fine.

If you want to run the upgrade script manually:

* If WordPress is installed in the root directory, point your browser to: http://example.com/wp-admin/upgrade.php
* If WordPress is installed in its own subdirectory called `blog`, for example, point your browser to: http://example.com/blog/wp-admin/upgrade.php

If you experience difficulties with login after your upgrade, it is worth clearing your browser's cookies.

##### Step 10: Update Permalinks and .htaccess {#step-10-update-permalinks-and-htaccess}

In your [Administration Screen](https://wordpress.org/documentation/article/administration-screens/) > [Settings](https://wordpress.org/documentation/article/administration-screens/#permalinks) > [Permalinks](https://wordpress.org/documentation/article/settings-permalinks-screen/) screen update your Permalink Structure and, if necessary, place the rules in your [`.htaccess`](https://wordpress.org/documentation/article/wordpress-glossary/#.htaccess) file. Also see [Using Permalinks](https://wordpress.org/documentation/article/using-permalinks/) for details regarding Permalinks and the [`.htaccess`](https://wordpress.org/documentation/article/wordpress-glossary/#.htaccess) file.

##### Step 11: Install updated Plugins and Themes {#step-11-install-updated-plugins-and-themes}

Please visit individual plugin and theme pages and look for the compatibility information with your new WordPress version. Install new versions of your Plugins and Themes, if necessary.

##### Step 12: Reactivate Plugins {#step-12-reactivate-plugins}

Use your Administration Screen, Plugins, to activate your Plugins. If you are not sure if they will work correctly with the new version, activate each plugin, one at a time, and test that there are no problems before continuing.

##### Step 13: Review what has changed in WordPress {#step-13-review-what-has-changed-in-wordpress}

Please review these resources to see what's new in WordPress:

* [Version history](https://codex.wordpress.org/WordPress_Versions)

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

If you get any errors following an upgrade, check [Troubleshooting: Common Installation Problems](https://developer.wordpress.org/advanced-administration/before-install/howto-install/#common-installation-problems), [Troubleshooting](https://codex.wordpress.org/Troubleshooting), and the [Installation Category of Articles](https://wordpress.org/documentation/category/installation/). If you can't find an answer, post a clear question on the [WordPress Suppport Forums](https://wordpress.org/support/forums/). You will be asked if you have used any old code. You'll be told to change it then, so you may as well change it now 🙂.

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
* More information here: [http://wordpress.stackexchange.com/questions/120081/how-do-i-configure-automatic-updates-in-wordpress-3-7](http://wordpress.stackexchange.com/questions/120081/how-do-i-configure-automatic-updates-in-wordpress-3-7)
* Info about wp-cli conflict: [https://github.com/wp-cli/wp-cli/issues/1310](https://github.com/wp-cli/wp-cli/issues/1310)

## Changelog

- 2022-10-25: Original content from [Configuring Automatic Background Updates](https://wordpress.org/documentation/article/configuring-automatic-background-updates/), and [Upgrading WordPress – Extended Instructions](https://wordpress.org/documentation/article/upgrading-wordpress-extended-instructions/).
