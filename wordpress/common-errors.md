# Common WordPress errors

If you are encountering a WordPress error message or white screen, don't panic. Someone has likely encountered the same message before and it can easily be solved.

This page lists the most common WordPress errors experienced by WordPress users, and provides a starting point for fixing them. At [WordPress Support](https://wordpress.org/documentation/), you will also find links to more detailed pages or forums where a volunteer will be there to help.

## The White Screen of Death {#the-white-screen-of-death}

Both PHP errors and database errors can manifest as a white screen, a blank screen with no information, commonly known in the WordPress community as the _WordPress White Screen of Death_ (WSOD).

Before resorting to desperate measures, there are a number of reasons for the WordPress white screen of death:

* **A Plugin is causing compatibility issues**. If you can access the [Administration Screens](https://wordpress.org/documentation/article/administration-screens/) try deactivating all of your Plugins and then reactivating them one by one. If you are unable to access your Screens, log in to your website via [FTP](https://developer.wordpress.org/advanced-administration/upgrade/ftp/). Locate the folder `wp-content/plugins` and rename the Plugin folder `plugins_old`. This will deactivate all of your Plugins. You can read more about manually deactivating your plugins in the [Troubleshooting FAQ](https://wordpress.org/documentation/article/faq-troubleshooting/#how-to-deactivate-all-plugins-when-not-able-to-access-the-administrative-menus).
* **Your Theme may be causing the problem**. This is especially likely if you are experiencing the white screen of death after you have just activated a new Theme, or created a New Site in a WordPress Network. Log in to the WordPress Administration Screens and activate a default WordPress Theme (e.g. Twenty Twenty-One). If you are using WordPress 5.8 and below, please switch to Twenty Twenty-One theme since the Twenty Twenty-Two theme requires 5.9 and above. If you can't access your Administration Screens, access your website via FTP and navigate to the `/wp-content/themes/` folder. Rename the folder for the active Theme.

The [WP_DEBUG feature](https://developer.wordpress.org/advanced-administration/debug/debug-wordpress/#wp_debug) often provides additional information.

## Internal Server Error {#internal-server-error}

[![](https://wordpress.org/documentation/files/2018/11/internalservererror2.jpg)](https://wordpress.org/documentation/files/2018/11/internalservererror2.jpg)
Internal Server Error message

There can be a number of reasons for an Internal Server Error. Here are some thing you can do to solve it:

* The most likely issue is a corrupted `.htaccess` file. Log in to your site root using FTP and rename your `.htaccess` file to `.htaccess_old`. Try loading your site to see if this has solved your problem. If it works, make sure to visit [Settings](https://wordpress.org/documentation/article/administration-screens/#settings-configuration-settings) > [Permalinks](https://wordpress.org/documentation/article/administration-screens/#permalinks) and reset your [permalinks](https://wordpress.org/documentation/article/using-permalinks/). This will generate a new `.htaccess` file for you.
* Try deactivating all of your Plugins to see if it is a Plugin issue. If you are unable to access your WordPress Administration Screens, deactivate your Plugins via FTP by following [these instructions](https://wordpress.org/documentation/article/faq-troubleshooting/#how-to-deactivate-all-plugins-when-not-able-to-access-the-administrative-menus).
* Switch the Theme to a WordPress default Theme (e.g. Twenty Twenty-One) to eliminate any Theme-related problems. If you are using WordPress 5.8 and below, please switch to Twenty Twenty-One theme since the Twenty Twenty-Two theme requires 5.9 and above.
* Increase the [PHP Memory limit](https://wordpress.org/documentation/article/editing-wp-config-php/#increasing-memory-allocated-to-php)
* Try re-uploading the `wp-admin` and `wp-includes` folders from a [fresh install of WordPress](https://wordpress.org/download/).

## Error Establishing Database Connection {#error-establishing-database-connection}

If you get a page featuring the message "Error Establishing Database Connection", this means that there is a problem with the connection to your database and there could be a number of reasons for this. The following are possible reasons and solutions.

### Incorrect wp-config.php Information {#incorrect-wp-config-php-information}

"Error establishing a database connection" is usually caused by an error in your [wp-config.php](https://wordpress.org/documentation/article/editing-wp-config-php/) file. Access your site in your FTP client. Open up `wp-config.php` and ensure that the following are correct:

* Database name
* Database username
* Database password
* Database host

Learn more about [editing wp-config.php](https://wordpress.org/documentation/article/editing-wp-config-php/).

If you are sure your configuration is correct you could [try resetting your MySQL password manually](https://developer.wordpress.org/advanced-administration/before-install/howto-install/#common-installation-problems).

### Problems with Your Web Host {#problems-with-your-web-host}

The next step is to contact your web host. The following hosting issues may be causing the problem:

* Your database has met its quota and has been shut down.
* The server is down.

Contact your hosting provider to see if either of these issues is causing your problem.

### Compromised Website {#compromised-website}

If you have checked `wp-config.php` for errors, and confirmed with your host for hosting issues, it is possible that your site has been hacked.

Scan your site with [Sucuri SiteCheck](http://sitecheck.sucuri.net/) to ensure that it hasn't been compromised. If it has you should check out [My Site was Hacked](https://wordpress.org/documentation/article/faq-my-site-was-hacked/).

## Failed Auto-Upgrade {#failed-auto-upgrade}

There will be situations when the WordPress auto-update feature fails. Symptoms include:

* A blank white screen and no information.
* A warning that the update failed.
* A PHP error message.

The WordPress automatic upgrade feature may fail due to a glitch in the connection with the main WordPress files, a problem with your Internet connection during upgrade, or incorrect [File Permissions](https://developer.wordpress.org/advanced-administration/server/file-permissions/).

To update your WordPress site manually, see the [Manual Update article](https://wordpress.org/documentation/article/updating-wordpress/#manual-update).

## Connection Timed Out {#connection-timed-out}

The connection timed out error appears when your website is trying to do more than your server can manage. It is particularly common on shared hosting where your memory limit is restricted. Here are some things you can try:

* **Deactivate all Plugins.** If deactivating all the WordPress Plugins on your site resolves the issue, reactivate them one-by-one to see which plugin is causing the problem. If you are unable to access your Administration Screens, [read about how to manually deactivate your plugins](https://wordpress.org/documentation/article/faq-troubleshooting/#how-to-deactivate-all-plugins-when-not-able-to-access-the-administrative-menus).
* **Switch to a default WordPress Theme.** If you are using WordPress 5.8 and below, please switch to Twenty Twenty-One theme since the Twenty Twenty-Two theme requires 5.9 and above. This should rule out any Theme-related problems.
* **Increase your [memory limit in wp-config.php](https://wordpress.org/documentation/article/editing-wp-config-php/#increasing-memory-allocated-to-php)**. If you are on shared hosting you may have to ask your hosting provider to increase your memory limit for you.
* **Increase the maximum execution time in your [php.ini](https://php.net/manual/en/ini.core.php) file.** This is not a WordPress core file so if you are not sure how to edit it, contact your hosting provider to ask them to increase your maximum execution time. See below instructions for increasing maximum execution time.

## Maintenance Mode Following Upgrade {#maintenance-mode-following-upgrade}

[![](https://wordpress.org/documentation/files/2018/11/maintenancemode1.jpg)](https://wordpress.org/documentation/files/2018/11/maintenancemode1.jpg)

When WordPress updates, it automatically installs a `.maintenance` file. Following upgrade, you may receive a message that says "Briefly unavailable for scheduled maintenance. Please check back in a minute." The maintenance file may not have been removed properly.

To remove this message do the following:

1. Log in to your website using your FTP program
2. Delete the `.maintenance` file, which will be found in your site root.

Read more about the [maintenance mode issue](https://wordpress.org/documentation/article/faq-troubleshooting/#how-to-clear-the-briefly-unavailable-for-scheduled-maintenance-message-after-doing-automatic-upgrade).

## You Make Changes and Nothing Happens {#you-make-changes-and-nothing-happens}

If you are making changes to your website and you do not see the changes in your browser, you may need to clear your [browser cache](https://wordpress.org/support/topic/i-make-changes-and-nothing-happens/). Your browser stores information about the websites that you visit. This makes it faster to load websites when you visit them because the browser just has to reload information already stored on your computer, rather than downloading it again.

If you make a change to a website and the browser does not think it is significant, it will simply load the data from your cache, and you won't see your changes. To fix the problem, simply [empty your browser cache](https://wordpress.org/support/topic/i-make-changes-and-nothing-happens/) or close the tab and reopen the link.

## Pretty Permalinks 404 and Images not Working {#pretty-permalinks-404-and-images-not-working}

If you are experiencing 404 errors with pretty [permalinks](https://wordpress.org/documentation/article/using-permalinks/) and a white screen when you upload images, [mod_rewrite](https://wordpress.org/documentation/article/glossary#mod_rewrite) may not be enabled in Apache by default. Mod_rewrite is an extension module of the [Apache web server](https://wordpress.org/documentation/article/glossary#apache) software which allows for "rewriting" of [URLs](https://en.wikipedia.org/wiki/Url) on-the-fly. It's what you need to make pretty permalinks work.

[WordPress Multisite](https://wordpress.org/documentation/article/glossary#multisite) networks usually experience this but it can also occur on shared hosting providers or after a [site migration or server move](https://wordpress.org/documentation/article/moving-wordpress/).

Reset your permalinks through **Settings > Permalinks.** If this does not work, you may have to edit the `.htaccess` file manually.

```
# BEGIN WordPress  
<IfModule mod\_rewrite.c>  
RewriteEngine On  
RewriteBase /  
RewriteRule ^index\.php$ - [L]  
RewriteCond %{REQUEST_FILENAME} !-f  
RewriteCond %{REQUEST_FILENAME} !-d  
RewriteRule . /index.php [L]  
</IfModule>  
# END WordPress
```

If you are not familiar with editing your `.htaccess` file, contact your hosting provider to ask them to turn on mod_rewrite rules. There is more information on [pretty permalinks in the WordPress Codex](https://wordpress.org/documentation/article/using-permalinks/#using-pretty-permalinks).

## Custom Post Type 404 Errors {#custom-post-type-404-errors}

You may experience problems with 404 errors and [custom post types](https://wordpress.org/documentation/article/post-types/#custom-types). Try the following steps:

1. Make sure that none of your Custom Post Types and single pages have the same name. If they do, rename the single page, including the [slug](https://wordpress.org/documentation/article/glossary/#post-slug).
2. Log in to your WordPress Administration Screens, navigate to **Settings > Permalinks**. Select the default permalinks. Save. Then reselect your preferred permalinks. This will flush the rewrite rules and should solve your problem.

## Specific Error Messages {#specific-error-messages}

There are a number of different errors that will appear in your error logs. To access your error logs you will need to turn on [debugging](https://wordpress.org/documentation/article/editing-wp-config-php/#wp_debug) and then locate your error log via FTP. The following information will help you to decipher some of the common error messages.

### PHP Errors {#php-errors}

Below are some common PHP error messages.

#### Fatal Errors and Warnings {#fatal-errors-and-warnings}

##### Cannot modify header information – headers already sent

If you receive a warning that WordPress cannot modify header information and headers are already sent, it usually means that you have spaces or characters before the opening tags or after the closing tags. Read how to [fix the headers already sent error](https://wordpress.org/documentation/article/faq-troubleshooting/#how-do-i-solve-the-headers-already-sent-warning-problem).

If you are experiencing this problem when you have just installed WordPress you may have introduced a syntax error into `wp-config.php`. [These instructions will help you to fix the error](https://developer.wordpress.org/advanced-administration/before-install/howto-install/#common-installation-problems).

##### Call to undefined function

An error reading call to undefined function could mean that a WordPress Plugin is trying to find a file or data which isn't present or accessible in the code. Reasons for this include:

* An error when trying to auto-install or auto-upgrade a Plugin. Try [installing or upgrading the Plugin manually](https://wordpress.org/documentation/article/managing-plugins/#manual-plugin-installation).
* An error when trying to auto-install or auto-upgrade a Theme. Try [installing or upgrading the Theme manually](https://wordpress.org/documentation/article/using-themes/#adding-new-themes).
* You may be using an [incompatible WordPress Plugin](https://wordpress.org/documentation/article/managing-plugins/#plugin-compatibility) or incompatible Theme. This could happen with older versions of WordPress and a new WordPress Plugin, or if you are trying to use a WordPress Multisite Plugin on a single site installation. Upgrade WordPress to resolve this issue.
* You may be trying to call a function that doesn't exist. Check `functions.php` for misspellings.

Try deactivating the WordPress Plugin or changing the WordPress Theme that caused the error to appear. If you are unable to do this from within the Administration Screens, you may have to do this [manually via FTP](https://wordpress.org/documentation/article/faq-troubleshooting/#how-to-deactivate-all-plugins-when-not-able-to-access-the-administrative-menus).

##### Allowed memory size exhausted

An Allowed Memory Size Exhausted error means that your WordPress installation doesn't have enough memory to achieve what you want. You can try out the following steps:

* Increase your [memory limit in wp-config.php](https://wordpress.org/documentation/article/editing-wp-config-php/#increasing-memory-allocated-to-php)
* Increase your memory limit by editing `php.ini`. This is not a file that comes with WordPress so if you are unfamiliar with it you should contact your web host about increasing your memory limit.

##### Maximum execution time exceeded

You may receive a message such as "Maximum execution time of 30 seconds exceeded" or "Maximum execution time of 60 seconds exceeded". This means that it is taking to longer for a process to complete and it is timing out. There are a number of ways to fix this error.

**Editing `.htaccess`**

**Make sure you back up `.htaccess` before you edit it.**

Add the following line to `.htaccess`:

```
php_value max_execution_time 60
```

**Editing `php.ini`**

Add the following to `php.ini`

```
max_execution_time = 60
```

If you are unsure of how to make these changes, or if you are on shared hosting that prevents you from making them yourself, you should contact your hosting provider and ask them to increase your maximum execution time.

#### Parse errors {#parse-errors}

##### Syntax Error

A syntax error means that you have made a mistake while creating your PHP structure. You could, for example, be:

* Missing a `;` at the end of an individual line.
* Using curly quotation marks.
* Missing a curly bracket.

When this error appears it will tell you which file the error appears in (`functions.php` for example) and approximately which line (it may not always be the exact line so be sure to check just before and just after) in the code.

##### Unexpected

If you are receiving an error which says 'parse error: unexpected' this usually means that you have forgotten to include a character. The most common are:

* **Unexpected '='** : you have forgotten to include the $ when referencing a variable
* **Unexpected ')'** : you have forgotten to include the opening bracket (
* **Unexpected '('** : you have forgotten to include the closing bracket )
* **Unexpected T_STRING**: you have forgotten a quotation mark or a semi-colon at the end of the previous line
* **Unexpected T_ELSE**: you have an else statement with no opening if statement

#### Use of an undefined constant {#use-of-an-undefined-constant}

As with parse errors, "use of an undefined constant" means that you are missing a character. It could be one of the following:

* Missing a $ when referencing a variable
* Missing quotation marks around array keys

### Database Errors {#database-errors}

The following errors may appear in relation to your WordPress database.

#### Error 13 – Cannot Create/Write to File {#error-13-cannot-create-write-to-file}

There are a number of reasons why you may be experiencing this error.

**MySQL cannot create a temporary file.** 

The MySQL variable `tmpdir` is set to a directory that cannot be written to when using PHP to access MySQL. To verify this, enter MySQL at the command line and type `show variables`. You'll get a long list and one of them will read: **tmpdir = /somedir/** (whatever your setting is.)

To solve this, alter the **tmpdir** variable to point to a writable directory.

1. Find the **my.cnf** file. On *nix systems this is usually in **/etc/**. On Windows system, Find the **my.ini**.
2. Once found, open this in a simple text editor and find the **[mysqld]** section.
3. Under this section, find the **tmpdir** line. If this line is commented (has a **#** at the start), delete the **#** and edit the line so that it reads: **tmpdir = /writable/dir** where **/writable/dir** is a directory to which you can write. Some use **/tmp**, or you might also try **/var/tmp** or **/usr/tmp**. On Windows, use **C:/Windows/tmp**.
4. Save the file.
5. Shutdown MySQL by typing `mysqlshutdown -u -p shutdown`.
6. Start MySQL by going to the MySQL directory and typing `./bin/safe_mysqld &`. Usually the MySQL directory is in **/usr/local** or sometimes in **/usr/** on Linux systems.

**The** [**file permissions**](https://developer.wordpress.org/advanced-administration/server/file-permissions/) **are incorrect**

Correct the [File Permissions](https://developer.wordpress.org/advanced-administration/server/file-permissions/).

If none of this make sense and you have someone to administrate your system for you, show the above to them and they should be able to figure it out.

#### CREATE Command Denied to User {#create-command-denied-to-user}

This error occurs when the user assigned to the database does not have adequate permissions to perform the action to create columns and tables in the database. You will need to log in to [CPanel](https://wordpress.org/documentation/article/using-cpanel/) or [Plesk](https://www.plesk.com/) to give your database user adequate permissions.

Alternatively you can [create a new user to assign to your database](https://wordpress.org/documentation/article/using-cpanel/#step-3-add-user-to-database). If you do create a new user you will need to ensure that it is [updated in](https://wordpress.org/documentation/article/using-cpanel/#editing-the-wordpress-config-file) `[wp-config.php](https://wordpress.org/documentation/article/using-cpanel/#editing-the-wordpress-config-file)`.

#### Error {#error-28}

It could be because:

* you are out of space on /tmp (wherever tmpdir is), or,
* you have too many files in /tmp (even if there is lots of free space), or,
* Your cache on your server is full

This is a MySQL error and has nothing to do with WordPress directly; you should contact your host about it. Some users have reported that running a "repair table" command in [phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/) fixed the problem.

#### Error 145 {#error-145}

This indicates that a table in your database is damaged or corrupted. If you are comfortable using [phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/) you can use [these instructions on repairing your MySQL database tables](https://wordpress.org/documentation/article/faq-troubleshooting-2/#how-do-you-repair-a-mysql-database-table).

**Always [backup your database](https://wordpress.org/documentation/article/backing-up-your-database/) before performing any actions on it.**

If you have not used phpMyAdmin before, or are uncomfortable doing so, contact your web host and ask them to run CHECK/REPAIR on your database.

#### Unknown Column {#unknown-column}

An unknown column error can be caused by a missing column in the database. If you have just upgraded WordPress then try manually upgrading again. To update your WordPress site manually, see the [Update article](https://wordpress.org/documentation/article/updating-wordpress/#manual-update).

If you are running a database query when you encounter the error then you may by using incorrect quotation marks for the identifier quote character. This [question on Stack Overflow provides more details](https://stackoverflow.com/questions/1346209/unknown-column-in-field-list-error-on-mysql-update-query). Also see the [MySQL documentation](http://dev.mysql.com/doc/refman/en/identifiers.html).

## Resources {#resources}

* [MySQL Error Codes and Messages](http://dev.mysql.com/doc/refman/en/error-messages-server.html)

## Changelog

- 2023-01-20: Copy content from [Common WordPress Errors](https://wordpress.org/documentation/article/common-wordpress-errors/)
