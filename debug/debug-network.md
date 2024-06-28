# Debugging a WordPress Network

## Debugging a WordPress Network

If you have reached this page, chances are you have received an error in your [WordPress network](https://wordpress.org/documentation/article/multisite-network-administration/). This failure occurs when WordPress cannot find one or more of the global tables for the network in the database.

On some shared web hosts, the host has disabled the functionality from running. It is always best to check with your web host **before** [creating a network](https://developer.wordpress.org/advanced-administration/multisite/create-network/) to make sure your web host account fulfills the technical requirements.

## If You just installed your network

Check your [wp-config.php](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) file for:

- correct database details
- `SUBDOMAIN_INSTALL` constant
- `MULTISITE` constant
- `$base` variable
- table prefix

You should not have anything after

```
/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');
```

Move any code that is after

```
require_once(ABSPATH . 'wp-settings.php');
```

to above the stop editing line.

### Mod_rewrite not working

The main site works, but 404 errors show up when trying to access added child subdomain sites. An Ubuntu with Apache HTTPD installation needs these steps:

```
sudo a2enmod rewrite
sudo nano /etc/apache2/sites-avail/default
```

and change in two places the 'AllowOverride None' to 'AllowOverride all'

```
/etc/init.d/apache2 restart
```

to restart apache2. Note that on more modern versions of Ubuntu the following syntax is preferred (for restarting services such as Apache – also note that in either case you may need to use prepend _sudo_):

```
service apache2 restart
```

### Check the database

Assuming all that is correct, check the database itself and see if [all the extra network tables](https://codex.wordpress.org/Database_Description#Multisite_Table_Overview) were created. The tables are:

- wp_blogs
- wp_blogmeta
- wp_blog_versions
- wp_registration_log
- wp_signups
- wp_site
- wp_sitemeta

If you have these DB tables or added them manually but wp_site and/or wp_blogs is empty, you may have to run some SQL queries to insert rows for your main site. Be sure to adjust the table prefixes, domains, dates, username, and other parts of the queries below to match your installation.

```
INSERT INTO wp_site VALUES ( 1, 'domain.com', '/' );
# change domain.com to the full domain of your original site and / to the path

INSERT INTO wp_blogs VALUES( 1, 1, 'domain.com', '/', '2015-01-01', '2015-01-01', 1, 0, 0, 0, 0, 0 );
# change domains.com and / to domain and path of your site. Change dates if you want.

INSERT INTO wp_sitemeta VALUES( 1, 1, 'site_admins', 'a:1:{i:1;s:5:"admin";}' );
# Sets the admin user as a Super Admin. Change "admin" to your user_login. 
# Change "s:5" to "s:#" where # is the number of characters in user_login.
```

## If new site creation suddenly stopped working

Please take a look at your database as above. Double-check that the location of the database server hasn't changed, or is so, that you've updated your `wp-config.php` file.

## Other lesser-known issues

Check that the database user has ALL permissions on the database.

Also, on very few upgrades from WordPressMU to 3.0 and up, a few users experienced a problem with creating new sites and receiving errors. This turned out to be a database collation issue.

Check that the `.htaccess` instructions are not throwing up errors in the Apache logs.

Like this one:

```
Options FollowSymLinks or SymLinksIfOwnerMatch is off which implies that RewriteRule directive is forbidden:

This will result in a Network install appearing to fail and may show WP errors like

One or more database tables are unavailable. The database may need to be repaired.
```

## Related Articles

* [WordPress Multisite Network: A Complete Guide](https://multilingualpress.org/wordpress-multisite-network/)

## External Links

* [WordPress → Support → Multisite](https://wordpress.org/support/forum/multisite/)

## Changelog

- 2023-02-17: Updated original content
- 2022-10-21: Original content from [Debugging a WordPress Network](https://wordpress.org/documentation/article/debugging-a-wordpress-network/).