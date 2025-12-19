# Application Passwords

Application Passwords are a WordPress feature that lets you generate **revocable, per-application credentials** for programmatic access (for example, a mobile app, an integration, or a script). They are designed to avoid sharing your main account password with third-party tools.

Application Passwords were introduced in **WordPress 5.6** (December 2020). See: [Application Passwords: Integration Guide](https://make.wordpress.org/core/2020/11/05/application-passwords-integration-guide/).

### What are Application Passwords? {#what-are-application-passwords}

An Application Password is:

* A password tied to a specific WordPress user account.
* Intended for API authentication (for example, WordPress REST API, and (where enabled) XML-RPC), not for interactive browser logins.
* Stored hashed in WordPress and **shown only once** at creation time.
* Individually revocable, so you can disable a single integration without changing the user’s primary password.

Application Passwords **cannot be used to log into wp-admin via `wp-login.php`**. They are meant for applications and scripts, not humans typing credentials into a login form.

### When to use Application Passwords {#when-to-use-application-passwords}

Use Application Passwords when you need a tool to authenticate as a WordPress user without giving it that user’s main password, for example:

* A deployment/maintenance script that needs to call the REST API.
* A third-party service that posts content or uploads media to WordPress.
* A desktop/mobile client that needs authenticated access to your site’s APIs.

If the use-case is interactive logins in a browser, use a strong password and consider enabling **Two-Step Authentication** (also known as two-factor authentication, or 2FA): [Two-Step Authentication](https://developer.wordpress.org/advanced-administration/security/mfa/).

### Creating an Application Password in wp-admin {#creating-an-application-password-in-wp-admin}

1. Log into wp-admin as the user who needs the credential (or an admin editing that user).
2. Go to **Users → Profile** (or **Users → All Users → Edit** for another user).
3. Find the **Application Passwords** section.
4. Enter a descriptive name (for example: `CI deploy bot`, `My iPhone app`, `Reporting integration`).
5. Generate the password and **copy/store it immediately** (it will not be shown again).

WordPress displays Application Passwords in grouped chunks for readability. They can be used with or without the spaces (spaces are stripped before validation). See: [Application Passwords: Integration Guide](https://make.wordpress.org/core/2020/11/05/application-passwords-integration-guide/).

### Using an Application Password {#using-an-application-password}

Application Passwords are typically used via HTTP Basic Authentication:

* **Username**: the WordPress username (login).
* **Password**: the generated Application Password.

Because Basic Auth credentials can be intercepted on the network if unencrypted, you should use HTTPS. See: [HTTPS](https://developer.wordpress.org/advanced-administration/security/https/).

Example (REST API request with `curl`):

```
curl --user "USERNAME:APPLICATION_PASSWORD" https://example.com/wp-json/wp/v2/users/me
```

### Managing and rotating Application Passwords {#managing-and-rotating-application-passwords}

In the same **Application Passwords** section on the user profile, you can:

* Review existing Application Passwords (by name).
* See usage metadata (for example, last used time / last IP).
* Revoke individual passwords.

Operational best practice is to treat Application Passwords like secrets:

* Create one per integration/app (don’t reuse across tools).
* Revoke any credential that is no longer needed.
* Rotate credentials on a schedule or after any suspected leak.

### Managing Application Passwords with WP-CLI {#managing-application-passwords-with-wp-cli}

If you manage WordPress via SSH, WP-CLI can create, list, update, and revoke Application Passwords.

Examples:

```
# Create an application password for user ID 123 (prints the password once).
wp user application-password create 123 "myapp"

# Create and output just the password for user ID 123 (useful for scripts/CI).
wp user application-password create 123 "myapp" --porcelain

# List passwords for user ID 123 and show common fields.
wp user application-password list 123 --fields=uuid,name,created,last_used,last_ip

# Delete a specific application password by UUID for user ID 123.
wp user application-password delete 123 <uuid>
```

Official command reference: [wp user application-password](https://developer.wordpress.org/cli/commands/user/application-password/).

### Availability and disabling {#availability-and-disabling}

By default, Application Passwords are available when requests are served over HTTPS. Availability can be customized (or disabled) with filters; see the core dev note for examples: [Application Passwords: Integration Guide](https://make.wordpress.org/core/2020/11/05/application-passwords-integration-guide/).

If you do not want to allow Application Passwords on a site, you can disable them via code (for example, in a must-use plugin) using the `wp_is_application_passwords_available` filter. For more granular control (for example, only allow specific users/roles), use `wp_is_application_passwords_available_for_user`.

### Troubleshooting {#troubleshooting}

If authentication fails (401/403) or Application Passwords don’t appear in the user profile:

* **Confirm HTTPS**: Application Passwords are intended for HTTPS requests. Ensure the site is correctly configured for TLS and that WordPress detects it as HTTPS. See: [HTTPS](https://developer.wordpress.org/advanced-administration/security/https/).
* **Confirm you’re using an Application Password**: The user’s regular password may still be required for interactive logins; the Application Password is separate.
* **Check whether the feature is disabled**: Security plugins, must-use plugins, or custom code may disable Application Passwords via filters.
* **Confirm your client sends Basic Auth**: Some HTTP clients and proxies can strip `Authorization` headers unless explicitly configured.

### Related {#related}

* [HTTPS](https://developer.wordpress.org/advanced-administration/security/https/)
* [Two-Step Authentication](https://developer.wordpress.org/advanced-administration/security/mfa/)
* WP-CLI reference: [wp user application-password](https://developer.wordpress.org/cli/commands/user/application-password/)

