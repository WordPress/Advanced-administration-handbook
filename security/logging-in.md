# Logging In

This guide covers WordPress authentication - accessing your site, managing login sessions, and resetting passwords.

## Accessing the Login Page

The WordPress login page is located at `wp-login.php` in your site's root directory:

```
https://example.com/wp-login.php
```

Visiting `/wp-admin/` while logged out redirects to the login page automatically.

### Login Form Fields

- **Username or Email Address:** Enter either your username or the email associated with your account
- **Password:** Your account password (case-sensitive)
- **Remember Me:** Keeps you logged in for 14 days instead of the default session length

## Login Cookies

WordPress uses cookies to manage authentication. Understanding these helps with troubleshooting and security configuration.

| Cookie | Purpose |
|--------|---------|
| `wordpress_[hash]` | Authentication cookie (for insecure/non-HTTPS logins) |
| `wordpress_logged_in_[hash]` | Indicates logged-in status to the frontend |
| `wordpress_sec_[hash]` | Secure authentication cookie (for HTTPS logins) |

**Note:** The `wordpress_sec_[hash]` cookie is used when you log in over a secure connection (HTTPS), which is strongly recommended for all sites. Non-secure logins (HTTP) are vulnerable to credential theft and should be avoided.

**Default expiration:** 2 days (48 hours). When **Remember Me** is checked, cookies persist for 14 days.

When WordPress needs to check an authentication cookie, it uses [`wp_validate_auth_cookie()`](https://developer.wordpress.org/reference/functions/wp_validate_auth_cookie/). The function validates the cookie, checks whether it has expired, verifies the cookie hash, and returns the user ID when the cookie is valid.

### Cookie Configuration

**Changing cookie expiration** requires the [`auth_cookie_expiration`](https://developer.wordpress.org/reference/hooks/auth_cookie_expiration/) filter. Add to your theme's `functions.php` or a custom plugin:

```php
add_filter( 'auth_cookie_expiration', 'custom_cookie_expiration', 10, 3 );

function custom_cookie_expiration( $length, $user_id, $remember ) {
    if ( $remember ) {
        return 30 * DAY_IN_SECONDS; // 30 days when "Remember Me" is checked
    }
    return 2 * DAY_IN_SECONDS; // 2 days for standard login (default)
}
```

**Cookie domain** can be set in `wp-config.php` for subdomain sharing:

```php
define( 'COOKIE_DOMAIN', '.example.com' );
```

## Troubleshooting Login Issues

### "Cookies are blocked or not supported"

This error appears when WordPress cannot set the test cookie (`wordpress_test_cookie`) or authentication cookies:

- **Browser settings:** Ensure cookies are enabled for your site
- **Plugin conflicts:** Disable plugins temporarily to test
- **Cookie domain mismatch:** Verify `COOKIE_DOMAIN` matches your site URL
- **Mixed content:** Ensure your site uses HTTPS consistently
- **Server caching:** Exclude `wp-login.php` and cookie-based sessions from page caching

### Login Redirect Loop

If the login page keeps reloading after entering credentials:

1. Clear browser cookies for your site
2. Check `wp-config.php` for correct `WP_HOME` and `WP_SITEURL` values
3. Check the `siteurl` and `home` values in the database options table
4. Temporarily rename your `plugins` folder to rule out plugin conflicts

### "Too many redirects"

Often caused by conflicting site URL settings, HTTPS settings, or proxy configuration. Check for:

- Conflicting `WP_HOME` and `WP_SITEURL` values in `wp-config.php`
- Incorrect `siteurl` and `home` values in the database options table
- Mixed `http://` and `https://` URLs in the database after moving to SSL
- Conflicting SSL or redirect settings in a CDN, DNS proxy, web host proxy, Nginx, or Apache

You can force the expected URLs in `wp-config.php` while troubleshooting:

```php
// Add to wp-config.php to force correct URLs
define( 'WP_HOME', 'https://example.com' );
define( 'WP_SITEURL', 'https://example.com' );
```

If the site was moved between domains or from HTTP to HTTPS, run a database search-replace after taking a backup. WP-CLI's [`wp search-replace`](https://developer.wordpress.org/cli/commands/search-replace/) command can update stored URLs, and the `--dry-run` flag can preview changes before writing to the database.

## Resetting Your Password

This section covers methods for resetting a WordPress password, from standard recovery to advanced technical approaches.

### Standard Password Recovery

If you forget your WordPress password, use the **Lost your password?** link on the login screen. WordPress will email a password reset link to the address associated with your account.

This is the simplest and safest method when:
- You have access to your account's email address
- Your site's email functionality is working properly

### Technical Password Reset Methods

When standard recovery isn't available, administrators can reset passwords using the methods below. **WP-CLI is the recommended approach** as it's the safest and most straightforward.

#### Using WP-CLI (Recommended)

WP-CLI provides a secure, command-line method for password resets without directly modifying database tables.

**Set a specific password** using [`wp user update`](https://developer.wordpress.org/cli/commands/user/update/). The `--prompt=user_pass` flag prompts for the password instead of exposing it in your shell history:

```bash
wp user update USERNAME --prompt=user_pass
```

**Generate a new random password** using [`wp user reset-password`](https://developer.wordpress.org/cli/commands/user/reset-password/):

```bash
wp user reset-password USERNAME --show-password
```

This generates a new password and emails the user to notify them that their password was changed. The email does not include the new password. Admins can use the `--show-password` option to display the new password after it has been changed, if they need to share it with the user separately.

Use `--show-password` with caution. It prints the new password in plain text, which can expose it in terminal output, logs, screenshots, or shared support sessions.

See the [WP-CLI user command reference](https://developer.wordpress.org/cli/commands/user/) for additional options.

#### Creating a Password Hash

The database methods below need a password hash, not the password itself. WordPress stores only hashes in the `user_pass` column, so a plain-text password entered there will not work.

On any computer with PHP installed, including your own, run:

```bash
php -r 'echo password_hash( trim( fgets( STDIN ) ), PASSWORD_BCRYPT ), PHP_EOL;'
```

Type the new password and press Enter. The command prints a hash that starts with `$2y$`. Because PHP reads the password as input instead of as part of the command, the password is not saved in your shell history.

WordPress accepts this hash, and replaces it with its own hash format the next time the user logs in. Do not use an online hash generator, because it sends your password to a third party.

#### Using phpMyAdmin

Updating passwords directly in the database is high risk. Back up the database first, verify the table prefix, and make sure you are updating the intended user.

1. Open phpMyAdmin and select your WordPress database
2. Navigate to the `wp_users` table (your prefix may differ)
3. Click **Edit** on the user row you want to modify
4. In the `user_pass` field:
   - Leave the function dropdown empty
   - Paste the password hash into the value field
5. Click **Go** to save

#### Using MySQL Command Line

Updating passwords directly in the database is high risk and should only be done by experienced database administrators. Back up the database first, verify the table prefix, and make sure you are updating the intended user.

Connect to your database, then find the users table and the user's ID:

```sql
SHOW TABLES LIKE '%users';
SELECT ID, user_login FROM wp_users;
```

Then store the password hash:

```sql
UPDATE wp_users
SET user_pass = 'PASSWORD_HASH'
WHERE ID = USER_ID;
```

Replace `wp_users` with your actual table name, `PASSWORD_HASH` with the hash you created, and `USER_ID` with the user's ID. If you copy these commands, make sure the quotes are straight quotes (`'`), not curly quotes (`‘ ’`), which MySQL rejects.

Run these commands at the `mysql>` prompt. The hash contains `$` characters. If you pass the `UPDATE` command to `mysql -e` inside double quotes, your shell replaces parts of the hash, such as `$2y`, and stores a hash that will not work.

## Additional Resources

- [Official WordPress password recovery documentation](https://wordpress.org/documentation/article/reset-your-password/)
- [WordPress Cookies](https://wordpress.org/support/article/cookies/) - Detailed explanation of cookies used by WordPress
- [Editing wp-config.php](https://wordpress.org/support/article/editing-wp-config-php/) - Official guide on configuring constants
- [Hardening WordPress](https://developer.wordpress.org/advanced-administration/security/hardening/) - Security best practices including strong passwords, HTTPS, and two-factor authentication
