# Debugging a WordPress Network

If you have reached this page, chances are you have received an error while setting up or running a [WordPress Multisite Network](https://wordpress.org/documentation/article/multisite-network-administration/). These errors often occur when WordPress cannot find one or more of the required global tables for the network in the database or when the web server is not configured correctly.

This guide outlines the most common causes and fixes for WordPress Network (Multisite) setup or runtime issues.  

---

## Before You Begin {#before-you-begin}
- **Back up your database and files** before making changes.  
- Ensure your hosting environment meets the [technical requirements for Multisite](https://developer.wordpress.org/advanced-administration/multisite/create-network/).  
- Check with your host if they allow network/multisite installations (some shared hosts may restrict this).  

---

## If You Just Installed Your Network {#new-install}

### Verify wp-config.php {#verify-wp-config}
Check your [wp-config.php](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) file for:

- Correct database details (`DB_NAME`, `DB_USER`, `DB_PASSWORD`, `DB_HOST`)  
- The `MULTISITE` constant  
- The `SUBDOMAIN_INSTALL` constant (true/false depending on setup)  
- The `DOMAIN_CURRENT_SITE`, `PATH_CURRENT_SITE`, `SITE_ID_CURRENT_SITE` and `BLOG_ID_CURRENT_SITE` constants, as shown on the Network Setup screen
- The table prefix (`$table_prefix`)  

Ensure no code is placed after the following block:  

```php
/* That's all, stop editing! Happy publishing. */

/** Absolute path to the WordPress directory. */
if ( ! defined('ABSPATH') ) {
    define('ABSPATH', __DIR__ . '/');
}

/** Sets up WordPress vars and included files. */
require_once ABSPATH . 'wp-settings.php';
```

If you find custom code *after* the `require_once` line, move it above the "stop editing" comment.

---

### Mod_rewrite or Rewrites Not Working {#rewrites}

If your main site loads but child sites return 404 errors, your rewrite rules may not be active.

#### Apache
On Ubuntu/Debian systems, enable the rewrite module:

```bash
sudo a2enmod rewrite
```

Then allow `.htaccess` files in your WordPress directory: in the `<Directory>` block for that directory, change `AllowOverride None` to `AllowOverride All`. On a default install, this is the `<Directory /var/www/>` block in `/etc/apache2/apache2.conf`. Do not change the `<Directory />` block.

Restart Apache:

```bash
sudo systemctl restart apache2
```

#### Nginx
Nginx does not read `.htaccess` files, so the rewrite rules go in the server block. They depend on whether the network uses subdirectories or subdomains, and on whether Multisite was first activated on WordPress 3.5 or later, or on 3.4 or earlier. Use the configuration under [WordPress Multisite](https://developer.wordpress.org/advanced-administration/server/web-server/nginx/#wordpress-multisite) on the Nginx page that matches your network, then restart Nginx:

```bash
sudo systemctl restart nginx
```

---

### Check the Database {#check-database}

A Multisite network uses these core tables, with your prefix, for example `wp_`:

- `wp_blogs`
- `wp_site`
- `wp_sitemeta`
- `wp_signups`
- `wp_registration_log`

Since WordPress 5.1, `wp_blogmeta` stores site metadata. If it is missing, WordPress treats site metadata as unsupported.

To see which sites the network expects, run these read-only queries (adjust the prefix):

```sql
SELECT id, domain, path FROM wp_site;
SELECT blog_id, site_id, domain, path FROM wp_blogs;
```

Compare the domain and path with the address you are visiting, and with `DOMAIN_CURRENT_SITE` and `PATH_CURRENT_SITE` in `wp-config.php`. If the network moved to a new domain, see [Moving WordPress Multisite](https://developer.wordpress.org/advanced-administration/upgrade/migrating/#moving-wordpress-multisite). If tables or rows are missing, restore them from a backup rather than adding rows by hand.

---

## If New Site Creation Suddenly Stops Working {#site-creation-stopped}
- Confirm the database server hostname has not changed (check `DB_HOST` in `wp-config.php`).  
- Check that the database user has **ALL privileges** on the database.  
- Review web server error logs for `.htaccess` or rewrite-related issues.  

---

## Other Issues {#other-issues}
- **Database Collation Conflicts**: Rarely seen today, but legacy upgrades from WordPress MU may cause site creation errors due to collation mismatches. Check your database collation settings if upgrading from very old versions.  
- **Apache Rewrite Restrictions**: If you see errors like:  

  ```
  Options FollowSymLinks or SymLinksIfOwnerMatch is off which implies that RewriteRule directive is forbidden
  ```

  This may prevent rewrites and cause WordPress to report missing tables. Update your Apache configuration to allow rewrites.

---

## Related Articles {#related-articles}
- [WordPress Multisite Network: A Complete Guide](https://multilingualpress.org/wordpress-multisite-network/)  
- [Hardening WordPress](https://developer.wordpress.org/advanced-administration/security/hardening/)

## External Links {#external-links}
- [WordPress Support → Multisite Forum](https://wordpress.org/support/forum/multisite/)  

---

## Legacy Notes {#legacy-notes}
Some issues date back to migrations from WordPress MU (pre‑3.0). These are rare in modern networks but may still appear when running very old sites. Where possible, consider migrating to a fresh Multisite install.
