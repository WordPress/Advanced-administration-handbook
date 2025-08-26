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
- The `$base` variable  
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
On Ubuntu/Debian systems:

```bash
sudo a2enmod rewrite
sudo nano /etc/apache2/sites-available/000-default.conf
```

Change both instances of `AllowOverride None` to `AllowOverride All`, then restart Apache:

```bash
sudo systemctl restart apache2
```

#### Nginx
For Nginx, ensure your server block includes:

```nginx
location / {
    try_files $uri $uri/ /index.php?$args;
}
```

Restart Nginx:

```bash
sudo systemctl restart nginx
```

---

### Check the Database {#check-database}

Multisite requires additional database tables. Confirm that the following exist (with your prefix, e.g., `wp_`):  

- `wp_blogs`  
- `wp_blogmeta`  
- `wp_blog_versions`  
- `wp_registration_log`  
- `wp_signups`  
- `wp_site`  
- `wp_sitemeta`  

If tables are missing, or `wp_site`/`wp_blogs` are empty, you may need to insert rows manually. **Back up first!**  

Example SQL (adjust prefix, domain, path, user, and dates):  

```sql
INSERT INTO wp_site VALUES (1, 'example.com', '/');
INSERT INTO wp_blogs VALUES (1, 1, 'example.com', '/', '2025-01-01', '2025-01-01', 1, 0, 0, 0, 0, 0);
INSERT INTO wp_sitemeta VALUES (1, 1, 'site_admins', 'a:1:{i:1;s:5:"admin";}');
```

⚠️ Change `"admin"` to your actual username, and adjust `s:5` to match its character length.

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
- [Hardening WordPress Multisite](https://developer.wordpress.org/advanced-administration/security/hardening/)  

## External Links {#external-links}
- [WordPress Support → Multisite Forum](https://wordpress.org/support/forum/multisite/)  

---

## Legacy Notes {#legacy-notes}
Some issues date back to migrations from WordPress MU (pre‑3.0). These are rare in modern networks but may still appear when running very old sites. Where possible, consider migrating to a fresh Multisite install.
