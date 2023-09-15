# Editing wp-config.php

One of the most important files in your WordPress installation is the `wp-config.php` file. This file is located in the root of your WordPress file directory and contains your website’s base configuration details, such as database connection information.

When you first download WordPress, the `wp-config.php` file isn’t included. The WordPress setup process will create a `wp-config.php` file for you based on the information you provide in the [installation](https://developer.wordpress.org/advanced-administration/before-install/howto-install/) process.

It is unlikely that a non-developer would have to edit the wp-config.php file, in the case you are acting on trouble shooting steps provided by a technical person or by your webhost, this [page](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) should help.

# wp-config.php

TEMPORALLY NOTE: this may link for the simple part, to:
* https://developer.wordpress.org/advanced-administration/wordpress/wp-config/
* https://developer.wordpress.org/advanced-administration/debug/debug-wordpress/

## Advanced Options {#advanced-options}

The following sections may contain advanced information and some changes might result in unforeseen issues. Please make sure you practice [regular backups](https://developer.wordpress.org/advanced-administration/security/backup/) and know how to restore them before modifying these settings.

### table_prefix {#table-prefix}

The **$table_prefix** is the value placed in the front of your database tables. Change the value if you want to use something other than **wp_** for your database prefix. Typically this is changed if you are [installing multiple WordPress blogs](https://developer.wordpress.org/advanced-administration/before-install/multiple-instances/) in the same database, as is done with the multisite feature.

It is possible to have multiple installations in one database if you give each a unique prefix. Keep security in mind if you choose to do this.

```
$table_prefix = 'example123_'; // Only numbers, letters, and underscores please!
```

### WP_SITEURL {#wp-siteurl}

WP_SITEURL allows the WordPress address (URL) to be defined. The value defined is the address where your WordPress core files reside. It should include the http:// part too. Do not put a slash "**/**" at the end. Setting this value in `wp-config.php` overrides the [wp_options table](https://codex.wordpress.org/Database_Description#Table:_wp_options) value for **siteurl**. Adding this in can reduce the number of database calls when loading your site. **Note:** This will **not** change the database stored value. The URL will revert to the old database value if this line is ever removed from `wp-config`. [Use the **RELOCATE** constant](https://developer.wordpress.org/advanced-administration/upgrade/migrating/) to change the **siteurl** value in the database.

If WordPress is installed into a directory called "wordpress" for the [domain](http://en.wikipedia.org/wiki/Domain_name_system) example.com, define `WP_SITEURL` like this:

```
define( 'WP_SITEURL', 'https://example.com/wordpress' );
```

Dynamically set WP_SITEURL based on $_SERVER['HTTP_HOST']

```
define( 'WP_SITEURL', 'https://' . $_SERVER['HTTP_HOST'] . '/path/to/wordpress' );
```

**Note:** HTTP_HOST is created dynamically by PHP based on the value of the HTTP HOST Header in the request, thus possibly allowing for [file inclusion vulnerabilities](https://en.wikipedia.org/wiki/File_inclusion_vulnerability). SERVER_NAME may also be created dynamically. However, when Apache is configured as UseCanonicalName "on", SERVER_NAME is set by the server configuration, instead of dynamically. In that case, it is safer to user SERVER_NAME than HTTP_HOST.

Dynamically set `WP_SITEURL` based on `$_SERVER['SERVER_NAME']`

```
define( 'WP_SITEURL', 'http://' . $_SERVER['SERVER_NAME'] . '/path/to/wordpress' );
```

### Blog address (URL) {#blog-address-url}

Similar to WP_SITEURL, WP_HOME _overrides the [wp_options table](https://codex.wordpress.org/Database_Description#Table:_wp_options) value for_ home _but does not change it in the database._ **home** is the address you want people to type in their browser to reach your WordPress blog. It should include the http:// part and should not have a slash "**/**" at the end. Adding this in can reduce the number of database calls when loading your site.

```
define( 'WP_HOME', 'http://example.com/wordpress' );
```

If you are using the technique described in [Giving WordPress Its Own Directory](https://developer.wordpress.org/advanced-administration/server/wordpress-in-directory/) then follow the example below. Remember, you will also be placing an `index.php` in your web-root directory if you use a setting like this.

```
define( 'WP_HOME', 'http://example.com' );
```

Dynamically set `WP_HOME` based on `$_SERVER['HTTP_HOST']`

```
define( 'WP_HOME', 'http://' . $_SERVER['HTTP_HOST'] . '/path/to/wordpress' );
```

### Moving wp-content folder {#moving-wp-content-folder}

You can move the `wp-content` directory, which holds your themes, plugins, and uploads, outside of the WordPress application directory.

Set WP_CONTENT_DIR to the full **local path** of this directory (no trailing slash), e.g.

```
define( 'WP_CONTENT_DIR', dirname(__FILE__) . '/blog/wp-content' );
```

Set WP_CONTENT_URL to the full **URL** of this directory (no trailing slash), e.g.

```
define( 'WP_CONTENT_URL', 'http://example/blog/wp-content' );
```

### Moving plugin folder {#moving-plugin-folder}

Set WP_PLUGIN_DIR to the full **local path** of this directory (no trailing slash), e.g.

```
define( 'WP_PLUGIN_DIR', dirname(__FILE__) . '/blog/wp-content/plugins' );
```

Set WP_PLUGIN_URL to the full **URI** of this directory (no trailing slash), e.g.

```
define( 'WP_PLUGIN_URL', 'http://example/blog/wp-content/plugins' );
```

If you have compability issues with plugins Set PLUGINDIR to the full **local path** of this directory (no trailing slash), e.g.

```
define( 'PLUGINDIR', dirname(__FILE__) . '/blog/wp-content/plugins' );
```

### Moving themes folder {#moving-themes-folder}

You cannot move the themes folder because its path is hardcoded relative to the `wp-content` folder:

```
$theme_root = WP_CONTENT_DIR . '/themes';
```

However, you can register additional theme directories using [register_theme_directory](https://developer.wordpress.org/reference/functions/register_theme_directory/).

See how to [move the wp-content](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) folder. For more details how the themes folder is determined, see `wp-includes/theme.php`.

### Moving uploads folder {#moving-uploads-folder}

Set UPLOADS to:

```
define( 'UPLOADS', 'blog/wp-content/uploads' );
```

This path can not be absolute. It is always relative to ABSPATH, therefore does not require a leading slash.

### Modify AutoSave Interval {#modify-autosave-interval}

When editing a post, WordPress uses Ajax to auto-save revisions to the post as you edit. You may want to increase this setting for longer delays in between auto-saves, or decrease the setting to make sure you never lose changes. The default is 60 seconds.

```
define( 'AUTOSAVE_INTERVAL', 180 ); // Seconds
```

### Post Revisions {#post-revisions}

WordPress, by default, will save copies of each edit made to a post or page, allowing the possibility of reverting to a previous version of that post or page. The saving of revisions can be disabled, or a maximum number of revisions per post or page can be specified.

#### Disable Post Revisions {#disable-post-revisions}

If you do **not** set this value, WordPress defaults WP_POST_REVISIONS to _true_ (enable post revisions). If you want to disable the awesome revisions feature, use this setting:

```
define( 'WP_POST_REVISIONS', false );
```

Note: Some users could not get this to function until moving the command to the first line under the initial block comment in `wp-config.php`.

#### Specify the Number of Post Revisions {#specify-the-number-of-post-revisions}

If you want to specify a maximum number of revisions that WordPress stores, change _false_ to an integer/number (_e.g._, 3 or 12).

```
define( 'WP_POST_REVISIONS', 3 );
```

Note: Some users could not get this to function until moving the command to the first line under the initial block comment in `wp-config.php`.

### Set Cookie Domain {#set-cookie-domain}

The domain set in the cookies for WordPress can be specified for those with unusual domain setups. For example, if subdomains are used to serve static content, you can set the cookie domain to only your non-static domain to prevent WordPress cookies from being sent with each request to static content on your subdomain .

```
define( 'COOKIE_DOMAIN', 'www.example.com' );
```

### Enable Multisite / Network Ability {#enable-multisite-network-ability}

WP_ALLOW_MULTISITE is a feature enable multisite functionality. If this setting is absent from `wp-config.php` it defaults to false.

```
define( 'WP_ALLOW_MULTISITE', true );
```

### Redirect Nonexistent Blogs {#redirect-nonexistent-blogs}

NOBLOGREDIRECT can be used to redirect the browser if the visitor tries to access a nonexistent subdomain or a subfolder.

```
define( 'NOBLOGREDIRECT', 'http://example.com' );
```

### Fatal Error Handler {#wp-disable-fatal-error-handler}

WordPress 5.2 introduced [Recovery Mode](https://make.wordpress.org/core/2019/04/16/fatal-error-recovery-mode-in-5-2/) which displays error message instead of white screen when plugins causes fatal error.

_The site is experiencing technical difficulties. Please check your site admin email inbox for instructions._

White screens and PHP error messages are not displayed to users any more. But in a development environment, if you want to enable WP_DEBUG_DISPLAY, you have to disable recovery mode by set true to WP_DISABLE_FATAL_ERROR_HANDLER.

```
define( 'WP_DISABLE_FATAL_ERROR_HANDLER', true ); // 5.2 and later
define( 'WP_DEBUG', true );
define( 'WP_DEBUG_DISPLAY', true );
```

### WP_DEBUG {#wp-debug}

The [WP_DEBUG](https://developer.wordpress.org/advanced-administration/debug/debug-wordpress/) option controls the reporting of some errors and warnings and enables use of the WP_DEBUG_DISPLAY and WP_DEBUG_LOG settings. The default boolean value is false.

```
define( 'WP_DISABLE_FATAL_ERROR_HANDLER', true ); // 5.2 and later
define( 'WP_DEBUG', true );
```

[Database errors are printed only if WP_DEBUG is set to true](https://trac.wordpress.org/ticket/5473). Database errors are handled by the [wpdb](https://developer.wordpress.org/reference/classes/wpdb/) class and are not affected by [PHP's error settings](http://www.php.net/errorfunc).

Setting WP_DEBUG to true also raises the [error reporting level](http://www.php.net/error-reporting) to E_ALL and activates warnings when deprecated functions or files are used; otherwise, WordPress sets the error reporting level to E_ALL ^ E_NOTICE ^ E_USER_NOTICE.

### WP_ENVIRONMENT_TYPE {#wp-environment-type}

The WP_ENVIRONMENT_TYPE option controls the environment type for a site: `local`, `development`, `staging`, and `production`.

The values of environment types are processed in the following order with each sequential method overriding any previous values: the WP_ENVIRONMENT_TYPE [PHP environment variable](https://www.php.net/manual/en/reserved.variables.environment.php) and the WP_ENVIRONMENT_TYPE constant.

For both methods, if the value of an environment type provided is not in the list of allowed environment types, the default `production` value will be returned.

The simplest way to set the value is probably through defining the constant:

```
define( 'WP_ENVIRONMENT_TYPE', 'staging' );
```

Note: When `development` is returned by [wp_get_environment_type()](https://developer.wordpress.org/reference/functions/wp_get_environment_type/), WP_DEBUG will be set to `true` if it is not defined in the `wp-config.php` file of the site.

### SCRIPT_DEBUG {#script-debug}

[SCRIPT_DEBUG](https://developer.wordpress.org/advanced-administration/debug/debug-wordpress/) is a related constant that will force WordPress to use the "dev" versions of scripts and stylesheets in `wp-includes/js`, `wp-includes/css`, `wp-admin/js`, and `wp-admin/css` will be loaded instead of the `.min.css` and `.min.js` versions. If you are planning on modifying some of WordPress' built-in JavaScript or Cascading Style Sheets, you should add the following code to your config file:

```
define( 'SCRIPT_DEBUG', true );
```

### Disable Javascript Concatenation {#disable-javascript-concatenation}

To result in faster administration screens, all JavaScript files are [concatenated](http://en.wikipedia.org/wiki/Concatenation) into one URL. If JavaScript is failing to work in an administration screen, you can try disabling this feature:

```
define( 'CONCATENATE_SCRIPTS', false );
```

### Configure Error Logging {#configure-error-logging}

Configuring error logging can be a bit tricky. First of all, default PHP error log and display settings are set in the php.ini file, which you may or may not have access to. If you do, they should be set to the desired settings for live PHP pages served to the public. It's strongly recommended that no error messages are displayed to the public and instead routed to an error log. Further more, error logs should not be located in the publicly accessible portion of your server. Sample recommended php.ini error settings:

```
error_reporting = 4339
display_errors = Off
display_startup_errors = Off
log_errors = On
error_log = /home/example.com/logs/php_error.log
log_errors_max_len = 1024
ignore_repeated_errors = On
ignore_repeated_source = Off
html_errors = Off
```

**About Error Reporting 4339** This is a custom value that only logs issues that affect the functioning of your site, and ignores things like notices that may not even be errors. See [PHP Error Constants](http://php.net/manual/en/errorfunc.constants.php) for the meaning of each binary position for 1000011110011, which is the binary number equal to 4339. The far left 1 means report any E_RECOVERABLE_ERROR. The next 0 means do not report E_STRICT, (which is thrown when sloppy but functional coding is used) and so on. Feel free to determine your own custom error reporting number to use in place of 4339.

Obviously, you will want different settings for your development environment. If your staging copy is on the same server, or you don't have access to `php.ini`, you will need to override the default settings at run time. It's a matter of personal preference whether you prefer errors to go to a log file, or you prefer to be notified immediately of any error, or perhaps both. Here's an example that reports all errors immediately that you could insert into your `wp-config.php` file:

```
@ini_set( 'log_errors', 'Off' );
@ini_set( 'display_errors', 'On' );
define( 'WP_DISABLE_FATAL_ERROR_HANDLER', true ); // 5.2 and later
define( 'WP_DEBUG', true );
define( 'WP_DEBUG_LOG', false );
define( 'WP_DEBUG_DISPLAY', true );
```

Because `wp-config.php` is loaded for every page view not loaded from a cache file, it is an excellent location to set `php.ini` settings that control your PHP installation. This is useful if you don't have access to a `php.ini` file, or if you just want to change some settings on the fly. One exception is 'error_reporting'. When WP_DEBUG is defined as true, 'error_reporting' will be set to E_ALL by WordPress regardless of anything you try to set in wp-config.php. If you really have a need to set 'error_reporting' to something else, it must be done after `wp-settings.php` is loaded, such as in a plugin file.

If you turn on error logging, remember to delete the file afterwards, as it will often be in a publicly accessible location, where anyone could gain access to your log.

Here is an example that turns PHP error_logging on and logs them to a specific file. If WP_DEBUG is defined to true, the errors will also be saved to this file. Just place this above any _require_once_ or _include_ commands.

```
@ini_set( 'log_errors', 'On' );
@ini_set( 'display_errors', 'Off' );
@ini_set( 'error_log', '/home/example.com/logs/php_error.log' );
/* That's all, stop editing! Happy blogging. */
```

Another example of logging errors, as suggested by Mike Little on the [wp-hackers email list](http://lists.automattic.com/pipermail/wp-hackers/2010-September/034830.html):

```
/**
 * This will log all errors notices and warnings to a file called debug.log in
 * wp-content (if Apache does not have write permission, you may need to create
 * the file first and set the appropriate permissions (i.e. use 666) )
 */
define( 'WP_DEBUG', true );
define( 'WP_DEBUG_LOG', true );
define( 'WP_DEBUG_DISPLAY', false );
@ini_set( 'display_errors', 0 );
```

A refined version from Mike Little on the [Manchester WordPress User Group](http://groups.google.com/group/manchester-wordpress-user-group/msg/dcab0836cabc7f76):

```
/**
 * This will log all errors notices and warnings to a file called debug.log in
 * wp-content only when WP_DEBUG is true. if Apache does not have write permission,
 * you may need to create the file first and set the appropriate permissions (i.e. use 666).
 */
define( 'WP_DEBUG', true ); // Or false
if ( WP_DEBUG ) {
	define( 'WP_DEBUG_LOG', true );
	define( 'WP_DEBUG_DISPLAY', false );
	@ini_set( 'display_errors', 0 );
}
```

Confusing the issue is that WordPress has three (3) constants that look like they could do the same thing. First off, remember that if WP_DEBUG is false, it and the other two WordPress DEBUG constants do not do anything. The PHP directives, whatever they are, will prevail. Except for 'error_reporting', WordPress will set this to 4983 if WP_DEBUG is defined as false. Second, even if WP_DEBUG is true, the other constants only do something if they too are set to true. If they are set to false, the PHP directives remain unchanged. For example, if your `php.ini` file has the directive ('display_errors' = 'On'); but you have the statement define( 'WP_DEBUG_DISPLAY', false ); in your `wp-config.php` file, errors will still be displayed on screen even though you tried to prevent it by setting WP_DEBUG_DISPLAY to false because that is the PHP configured behavior. This is why it's very important to set the PHP directives to what you need in case any of the related WP constants are set to false. To be safe, explicitly set/define both types. More detailed descriptions of the WP constants is available at [Debugging in WordPress](https://developer.wordpress.org/advanced-administration/debug/debug-wordpress/).

For your public, production WordPress installation, you might consider placing the following in your `wp-config.php` file, even though it may be partly redundant:

```
@ini_set( 'log_errors', 'On' );
@ini_set( 'display_errors', 'Off' );
define( 'WP_DISABLE_FATAL_ERROR_HANDLER', false ); // 5.2 and later
define( 'WP_DEBUG', false );
define( 'WP_DEBUG_LOG', false );
define( 'WP_DEBUG_DISPLAY', false );
```

The default debug log file is `/wp-content/debug.log`. Placing error logs in publicly accessible locations is a security risk. Ideally, your log files should be placed above you site's public root directory. If you can't do this, at the very least, set the log file permissions to 600 and add this entry to the `.htaccess` file in the root directory of your WordPress installation:

```
<Files debug.log>
  Order allow,deny
  Deny from all
</Files>
```

This prevents anyone from accessing the file via HTTP. You can always view the log file by retrieving it from your server via FTP.

### Increasing memory allocated to PHP {#increasing-memory-allocated-to-php}

**WP_MEMORY_LIMIT** option allows you to specify the maximum amount of memory that can be consumed by PHP. This setting may be necessary in the event you receive a message such as "Allowed memory size of xxxxxx bytes exhausted".

This setting increases PHP Memory only for WordPress, not other applications. By default, WordPress will attempt to increase memory allocated to PHP to 40MB (code is at the beginning of `/wp-includes/default-constants.php`) for single site and 64MB for multisite, so the setting in `wp-config.php` should reflect something higher than 40MB or 64MB depending on your setup.

WordPress will automatically check if PHP has been allocated less memory than the entered value before utilizing this function. For example, if PHP has been allocated 64MB, there is no need to set this value to 64M as WordPress will automatically use all 64MB if need be.

Note: Some hosts do not allow for increasing the PHP memory limit automatically. In that event, contact your host to increase the PHP memory limit. Also, many hosts set the PHP limit at 8MB.

Adjusting the WordPress memory limit potentially creates problems as well. You might end up hiding the root of the issue for it to happen later down the line as you add in more plugins or functionalities.

If you are facing Out of Memory issues even with an elevated memory limit, you should properly debug your installation. Chances are you have too many memory intensive functions tied to a specific action and should move these functions to a cronjob.

Increase PHP Memory to 64MB

```
define( 'WP_MEMORY_LIMIT', '64M' );
```

Increase PHP Memory to 96MB

```
define( 'WP_MEMORY_LIMIT', '96M' );
```

Administration tasks require may require memory than usual operation. When in the administration area, the memory can be increased or decreased from the WP_MEMORY_LIMIT by defining WP_MAX_MEMORY_LIMIT.

```
define( 'WP_MAX_MEMORY_LIMIT', '128M' );
```

Note: this has to be put before wp-settings.php inclusion.

### Cache {#cache}

The **WP_CACHE** setting, if true, includes the `wp-content/advanced-cache.php` script, when executing `wp-settings.php`.

```
define( 'WP_CACHE', true );
```

### Custom User and Usermeta Tables {#custom-user-and-usermeta-tables}

**CUSTOM_USER_TABLE** and **CUSTOM_USER_META_TABLE** are used to designate that the user and usermeta tables normally utilized by WordPress are not used, instead these values/tables are used to store your user information.

```
define( 'CUSTOM_USER_TABLE', $table_prefix.'my_users' );
define( 'CUSTOM_USER_META_TABLE', $table_prefix.'my_usermeta' );
```

Note: Even if 'CUSTOM_USER_META_TABLE' is manually set, a usermeta table is still created for each database with the corresponding permissions for each instance. By default, the WordPress installer will add permissions for the first user (ID #1). You also need to manage permissions to each of the site via a plugin or custom function. If this isn't setup you will experience permission errors and log-in issues.

CUSTOM_USER_TABLE is easiest to adopt during initial Setup your first instance of WordPress. The define statements of the `wp-config.php` on the first instance point to where `wp_users` data will be stored by default. After the first site setup, copying the working `wp-config.php` to your next instance will only require a change the `$table_prefix` variable. Do not use an e-mail address that is already in use by your original install. Once you have finished the setup process log in with the auto generated admin account and password. Next, promote your normal account to the administrator level and Log out of admin. Log back in as yourself, delete the admin account and promote the other user accounts as is needed.

### Language and Language Directory {#language-and-language-directory}

WordPress [Version 4.0](https://wordpress.org/documentation/wordpress-version/version-4-0/) allows you to change the language in your WordPress [Administration Screens](https://wordpress.org/documentation/article/administration-screens/). To change the language in the admin settings screen. Go to [Settings](https://wordpress.org/documentation/article/administration-screens/#settings-configuration-settings) > [General](https://wordpress.org/documentation/article/settings-general-screen/) and select Site Language.

#### WordPress v3.9.6 and below {#wordpress-v3-9-6-and-below}

**WPLANG** defines the name of the language translation (.mo) file. **WP_LANG_DIR** defines what directory the WPLANG .mo file resides. If WP_LANG_DIR is not defined WordPress looks first to wp-content/languages and then `wp-includes/languages` for the .mo defined by WPLANG file.

```
define( 'WPLANG', 'de_DE' );
define( 'WP_LANG_DIR', dirname(__FILE__) . 'wordpress/languages' );
```

To find out the WPLANG language code, please [refer here](https://make.wordpress.org/polyglots/teams/). The code in WP Local column is what you need.

### Save queries for analysis {#save-queries-for-analysis}

The **SAVEQUERIES** definition saves the database queries to an array and that array can be displayed to help analyze those queries. The information saves each query, what function called it, and how long that query took to execute. **Note:** This will have a performance impact on your site, so make sure to turn this off when you aren't debugging.

First, add this to the `wp-config.php` file:

```
define( 'SAVEQUERIES', true );
```

Then in the footer of your theme put this:

```
if ( current_user_can( 'administrator' ) ) {
  global $wpdb;
  echo "<pre>";
  print_r( $wpdb->queries );
  echo "</pre>";
}
```

Alternatively, consider using [Query Monitor](https://wordpress.org/plugins/query-monitor/)

### Override of default file permissions {#override-of-default-file-permissions}

The **FS_CHMOD_DIR** and **FS_CHMOD_FILE** define statements allow override of default file permissions. These two variables were developed in response to the problem of the core update function failing with hosts running under [suexec](https://en.wikipedia.org/wiki/SuEXEC). If a host uses restrictive file permissions (e.g. 400) for all user files, and refuses to access files which have group or world permissions set, these definitions could solve the problem.

```
define( 'FS_CHMOD_DIR', ( 0755 & ~ umask() ) );
define( 'FS_CHMOD_FILE', ( 0644 & ~ umask() ) );
```

Example to provide setgid:

```
define( 'FS_CHMOD_DIR', ( 02755 & ~umask() ) );
```

Note: '**0755′** and '**02755**' are octal values. Octal values must be prefixed with a 0 and are not delineated with single quotes ('). See Also: [Changing File Permissions](https://developer.wordpress.org/advanced-administration/server/file-permissions/)

### WordPress Upgrade Constants {#wordpress-upgrade-constants}

**Note: Define as few of the below constants as needed to correct your update issues.**

The most common causes of needing to define these are:

Host running with a special installation setup involving symlinks. You may need to define the path-related constants (FTP_BASE, FTP_CONTENT_DIR, and FTP_PLUGIN_DIR). Often defining simply the base will be enough.

Certain PHP installations shipped with a PHP FTP extension which is incompatible with certain FTP servers. Under these rare situations, you may need to define FS_METHOD to "ftpsockets".

The following are valid constants for WordPress updates:

* **FS_METHOD** forces the filesystem method. It should only be "direct", "ssh2", "ftpext", or "ftpsockets". Generally, you should only change this if you are experiencing update problems. If you change it and it doesn't help, **change it back/remove it**. Under most circumstances, setting it to 'ftpsockets' will work if the automatically chosen method does not.
  * **(Primary Preference) "direct"** forces it to use Direct File I/O requests from within PHP, this is fraught with opening up security issues on poorly configured hosts, This is chosen automatically when appropriate.
  * **(Secondary Preference) "ssh2"** is to force the usage of the SSH PHP Extension if installed
  * **(3rd Preference) "ftpext"** is to force the usage of the FTP PHP Extension for FTP Access, and finally
  * **(4th Preference) "ftpsockets"** utilises the PHP Sockets Class for FTP Access.
* **FTP_BASE** is the full path to the "base"(ABSPATH) folder of the WordPress installation.
* **FTP_CONTENT_DIR** is the full path to the wp-content folder of the WordPress installation.
* **FTP_PLUGIN_DIR** is the full path to the plugins folder of the WordPress installation.
* **FTP_PUBKEY** is the full path to your SSH public key.
* **FTP_PRIKEY** is the full path to your SSH private key.
* **FTP_USER** is either user FTP or SSH username. Most likely these are the same, but use the appropriate one for the type of update you wish to do.
* **FTP_PASS** is the password for the username entered for **FTP_USER**. If you are using SSH public key authentication this can be omitted.
* **FTP_HOST** is the hostname:port combination for your SSH/FTP server. The default FTP port is 21 and the default SSH port is 22. These do not need to be mentioned.
* **FTP_SSL** TRUE for SSL-connection _if supported by the underlying transport_ (not available on all servers). This is for "Secure FTP" not for SSH SFTP.

```
define( 'FS_METHOD', 'ftpext' );
define( 'FTP_BASE', '/path/to/wordpress/' );
define( 'FTP_CONTENT_DIR', '/path/to/wordpress/wp-content/' );
define( 'FTP_PLUGIN_DIR ', '/path/to/wordpress/wp-content/plugins/' );
define( 'FTP_PUBKEY', '/home/username/.ssh/id_rsa.pub' );
define( 'FTP_PRIKEY', '/home/username/.ssh/id_rsa' );
define( 'FTP_USER', 'username' );
define( 'FTP_PASS', 'password' );
define( 'FTP_HOST', 'ftp.example.org' );
define( 'FTP_SSL', false );
```

Some configurations should set FTP_HOST to localhost to avoid 503 problems when trying to update plugins or WP itself.

#### Enabling SSH Upgrade Access {#enabling-ssh-upgrade-access}

There are two ways to upgrade using SSH2.

The first is to use the [SSH SFTP Updater Support plugin](https://wordpress.org/plugins/ssh-sftp-updater-support/). The second is to use the built-in SSH2 upgrader, which requires the pecl SSH2 extension be installed.

To install the pecl SSH2 extension you will need to issue a command similar to the following or talk to your web hosting provider to get this installed:

```
pecl install ssh2
```

After installing the pecl ssh2 extension you will need to modify your PHP configuration to automatically load this extension.

pecl is provided by the pear package in most linux distributions. To install pecl in Redhat/Fedora/CentOS:

```
yum -y install php-pear
```

To install pecl in Debian/Ubuntu:

```
apt-get install php-pear
```

It is recommended to use a private key that is not pass-phrase protected. There have been numerous reports that pass phrase protected private keys do not work properly. If you decide to try a pass phrase protected private key you will need to enter the pass phrase for the private key as FTP_PASS, or entering it in the "Password" field in the presented credential field when installing updates.

### Alternative Cron {#alternative-cron}

There might be reason to use an alternative Cron with WP. Most commonly this is done if scheduled posts are not getting published as predicted. This alternative method uses a redirection approach. The users' browser get a redirect when the cron needs to run, so that they come back to the site immediately while cron continues to run in the connection they just dropped. This method has certain risks, since it depends on a non-native WordPress service.

```
define( 'ALTERNATE_WP_CRON', true );
```

### Disable Cron and Cron Timeout {#disable-cron-and-cron-timeout}

Disable cron entirely by setting DISABLE_WP_CRON to true.

```
define( 'DISABLE_WP_CRON', true );
```

Make sure a cron process cannot run more than once every WP_CRON_LOCK_TIMEOUT seconds.

```
define( 'WP_CRON_LOCK_TIMEOUT', 60 );
```

### Additional Defined Constants {#additional-defined-constants}

Here are additional constants that can be defined. These probably shouldn't be set unless other methodologies have been attempted first. The Cookie definitions can be particularly useful if you have an unusual domain setup.

```
define( 'COOKIEPATH', preg_replace( '|https?://[^/]+|i', '', get_option( 'home' ) . '/' ) );
define( 'SITECOOKIEPATH', preg_replace( '|https?://[^/]+|i', '', get_option( 'siteurl' ) . '/' ) );
define( 'ADMIN_COOKIE_PATH', SITECOOKIEPATH . 'wp-admin' );
define( 'PLUGINS_COOKIE_PATH', preg_replace( '|https?://[^/]+|i', '', WP_PLUGIN_URL ) );
define( 'TEMPLATEPATH', get_template_directory() );
define( 'STYLESHEETPATH', get_stylesheet_directory() );
```

### Empty Trash {#empty-trash}

This constant controls the number of days before WordPress permanently deletes posts, pages, attachments, and comments, from the trash bin. The default is 30 days:

```
define( 'EMPTY_TRASH_DAYS', 30 ); // 30 days
```

To disable trash set the number of days to zero.

```
define( 'EMPTY_TRASH_DAYS', 0 ); // Zero days
```

**Note:** WordPress will not ask for confirmation when someone clicks on "Delete Permanently" using this setting.

### Automatic Database Optimizing {#automatic-database-optimizing}

There is automatic database repair support, which you can enable by adding the following define to your `wp-config.php` file.

Note: This should only be enabled if needed and disabled once the issue is solved. When enabled, a user does not need to be logged in to access the functionality, since its main intent is to repair a corrupted database and users can often not login when the database is corrupt.

```
define( 'WP_ALLOW_REPAIR', true );
```

The script can be found at `{$your_site}/wp-admin/maint/repair.php`.

### DO_NOT_UPGRADE_GLOBAL_TABLES {#do-not-upgrade-global-tables}

A **DO_NOT_UPGRADE_GLOBAL_TABLES** define prevents [dbDelta()](https://developer.wordpress.org/reference/functions/dbdelta/) and the upgrade functions from doing expensive queries against global tables.

Sites that have large global tables (particularly users and usermeta), as well as sites that share user tables with bbPress and other WordPress installs, can prevent the upgrade from changing those tables during upgrade by defining **DO_NOT_UPGRADE_GLOBAL_TABLES** to true. Since an ALTER, or an unbounded DELETE or UPDATE, can take a long time to complete, large sites usually want to avoid these being run as part of the upgrade so they can handle it themselves. Further, if installations are sharing user tables between multiple bbPress and WordPress installs you may to want one site to be the upgrade master.

```
define( 'DO_NOT_UPGRADE_GLOBAL_TABLES', true );
```

### View All Defined Constants {#view-all-defined-constants}

PHP has a function that returns an array of all the currently defined constants with their values.

```
print_r( @get_defined_constants() );
```

### Disable the Plugin and Theme File Editor {#disable-the-plugin-and-theme-file-editor}

Occasionally you may wish to disable the plugin or theme file editor to prevent overzealous users from being able to edit sensitive files and potentially crash the site. Disabling these also provides an additional layer of security if a hacker gains access to a well-privileged user account.

```
define( 'DISALLOW_FILE_EDIT', true );
```

**Note**: The functionality of some plugins may be affected by the use of `current_user_can('edit_plugins')` in their code. Plugin authors should avoid checking for this capability, or at least check if this constant is set and display an appropriate error message. Be aware that if a plugin is not working this may be the cause.

### Disable Plugin and Theme Update and Installation {#disable-plugin-and-theme-update-and-installation}

This will block users being able to use the plugin and theme installation/update functionality from the WordPress admin area. Setting this constant also disables the Plugin and Theme File editor (i.e. you don't need to set DISALLOW_FILE_MODS and DISALLOW_FILE_EDIT, as on its own DISALLOW_FILE_MODS will have the same effect).

```
define( 'DISALLOW_FILE_MODS', true );
```

### Require SSL for Admin and Logins {#require-ssl-for-admin-and-logins}

**Note:** WordPress [Version 4.0](https://wordpress.org/documentation/wordpress-version/version-4-0/) deprecated FORCE_SSL_LOGIN. Please use FORCE_SSL_ADMIN.

FORCE_SSL_ADMIN is for when you want to secure logins and the admin area so that both passwords and cookies are never sent in the clear. See also [HTTPS](https://developer.wordpress.org/advanced-administration/security/https/) for more details.

```
define( 'FORCE_SSL_ADMIN', true );
```

### Block External URL Requests {#block-external-url-requests}

Block external URL requests by defining WP_HTTP_BLOCK_EXTERNAL as true and this will only allow localhost and your blog to make requests. The constant WP_ACCESSIBLE_HOSTS will allow additional hosts to go through for requests. The format of the WP_ACCESSIBLE_HOSTS constant is a comma separated list of hostnames to allow, wildcard domains are supported, eg `*.wordpress.org` will allow for all subdomains of wordpress.org to be contacted.

```
define( 'WP_HTTP_BLOCK_EXTERNAL', true );
define( 'WP_ACCESSIBLE_HOSTS', 'api.wordpress.org,*.github.com' );
```

### Disable WordPress Auto Updates {#disable-wordpress-auto-updates}

There might be reason for a site to not auto-update, such as customizations or host supplied updates. It can also be done before a major release to allow time for testing on a development or staging environment before allowing the update on a production site.

```
define( 'AUTOMATIC_UPDATER_DISABLED', true );
```

### Disable WordPress Core Updates {#disable-wordpress-core-updates}

The easiest way to manipulate core updates is with the WP_AUTO_UPDATE_CORE constant:

**Disable all core updates:**
```
define( 'WP_AUTO_UPDATE_CORE', false );
```

**Enable all core updates, including minor and major:**
```
define( 'WP_AUTO_UPDATE_CORE', true );
```

**Enable core updates for minor releases (default):**
```
define( 'WP_AUTO_UPDATE_CORE', 'minor' );
```

Reference: [Disabling Auto Updates in WordPress 3.7](https://make.wordpress.org/core/2013/10/25/the-definitive-guide-to-disabling-auto-updates-in-wordpress-3-7/)

### Cleanup Image Edits {#cleanup-image-edits}

By default, WordPress creates a new set of images every time you edit an image and when you restore the original, it leaves all the edits on the server. Defining IMAGE_EDIT_OVERWRITE as true changes this behaviour. Only one set of image edits are ever created and when you restore the original, the edits are removed from the server.

```
define( 'IMAGE_EDIT_OVERWRITE', true );
```

## Double Check Before Saving {#double-check-before-saving}

_**Be sure to check for leading and/or trailing spaces around any of the above values you entered, and DON'T delete the single quotes!**_

Before you save the file, be sure to **double-check** that you have not accidentally deleted any of the single quotes around the parameter values. Be sure there is nothing after the closing PHP tag in the file. The last thing in the file should be **?>** and nothing else. No spaces.

To save the file, choose **File > Save As > wp-config.php** and save the file in the root of your WordPress install. Upload the file to your web server and you're ready to install WordPress!

## Changelog

- 2022-10-25: Fix content and links.
- 2022-09-04: Original content from [wp-config.php](https://developer.wordpress.org/apis/wp-config-php/); ticket [Github](https://github.com/WordPress/Documentation-Issue-Tracker/issues/349).
- 2023-01-20: Add content to the start from [documentation](https://wordpress.org/documentation/article/editing-wp-config-php/) ticket [Github](https://github.com/WordPress/Advanced-administration-handbook/issues/89)
