# Brute Force Attacks

A **brute force attack** is the simplest way to break in: an attacker repeatedly tries username/password combinations until one works. Because these attempts are automated and often distributed (botnets), even unsuccessful attacks can overwhelm your site with requests.

This is not unique to WordPress—every web application that exposes a login surface can be targeted—but WordPress’s popularity makes it a common focus. The guidance below reflects current best practices for WordPress sites in 2025 and replaces older, more fragile techniques.

---

## Key Defenses at a Glance {#key-defenses}
- **Use strong, unique passwords and a password manager.**
- **Enable two‑factor authentication (2FA)** for all administrator accounts (use a plugin or your identity provider; WordPress core does not include 2FA).  
- **Consider passkeys (WebAuthn)** via a reputable plugin for phishing‑resistant login.
- **Rate‑limit login attempts** at the edge (WAF/CDN) or the web server.  
- **Put a CAPTCHA/turnstile on login** to slow bots (e.g., Cloudflare Turnstile, reCAPTCHA).  
- **Protect or disable XML‑RPC** if you don’t need it; otherwise restrict and rate‑limit it.  
- **Keep WordPress core, themes, and plugins up to date.**
- **Monitor and alert** on authentication anomalies; ban abusive IPs temporarily.  
- Prefer **edge/WAF protections** (Cloudflare, Sucuri, host‑provided WAF) so bad traffic is blocked before it reaches your server.

> Tip: Obscuring the login URL can reduce noise but should not be your only defense.

---

## WordPress‑Level Protections {#wordpress-level}
### Enforce Strong Passwords {#strong-passwords}
WordPress shows a strength meter when changing passwords. Encourage unique, long passwords (or passphrases) and the use of password managers. Avoid dictionary words and personal info.

### Two‑Factor Authentication (2FA) {#2fa}
Enable 2FA for all administrators and privileged users using a reputable plugin or your identity provider (TOTP app, hardware key, SMS fallback). As of 2025, **WordPress core does not ship 2FA**; it must be added via a plugin or SSO/IdP.

### Passkeys (WebAuthn) {#passkeys}
Passkeys provide phishing‑resistant, passwordless login using platform authenticators (Face ID/Touch ID, Windows Hello, security keys). Add via a maintained plugin that supports WebAuthn/Passkeys and enroll at least two authenticators per admin.

### Application Passwords (for Integrations) {#application-passwords}
For API access by trusted apps/services, use **Application Passwords** (introduced in WordPress 5.6). They scope access and can be revoked without changing your user password.

### Limit Login Attempts (App Layer) {#limit-login}
If your host/CDN doesn’t rate‑limit at the edge, a security plugin can throttle login attempts. Note that app‑level plugins still execute within PHP and thus consume some resources under heavy attack; prefer edge or server‑level throttling when possible.

### XML‑RPC Considerations {#xmlrpc}
`xmlrpc.php` is a frequent brute‑force target (especially the `system.multicall` method). If you don’t use XML‑RPC, disable it. If you do (e.g., Jetpack, mobile apps), restrict it (WAF rules) and rate‑limit aggressively.

---

## Server / Proxy / WAF Protections {#server-proxy}
> These examples require server or proxy access and may vary by environment. Test in staging before applying to production.

### Apache (examples) {#apache}
**Block or rate‑limit abusive login attempts** (examples require appropriate modules such as `mod_rewrite`, `mod_authz_host`, or third‑party tools like ModSecurity or mod_evasive).

**Deny by IP (Apache 2.4+):**
```apache
# wp-login.php: allow specific IPs only
<Files "wp-login.php">
    Require ip 203.0.113.15 203.0.113.16
</Files>
```

**Send 401/403 to a static error page:**
```apache
ErrorDocument 401 /401.html
ErrorDocument 403 /403.html
```

> Consider ModSecurity rulesets (e.g., OWASP CRS) to detect and block brute‑force patterns at the server layer.

### Nginx (examples) {#nginx}
**Rate‑limit login and XML‑RPC:**
```nginx
# Define a shared zone for rate limiting
limit_req_zone $binary_remote_addr zone=logins:10m rate=10r/m;

server {
    # ...

    location = /wp-login.php {
        limit_req zone=logins burst=20 nodelay;
        include fastcgi_params;
        # pass to PHP-FPM or upstream as usual
    }

    location = /xmlrpc.php {
        limit_req zone=logins burst=20 nodelay;
        include fastcgi_params;
        # pass to PHP-FPM or upstream as usual
    }
}
```

**Deny by IP:**
```nginx
location = /wp-login.php {
    allow 203.0.113.15;
    allow 203.0.113.16;
    deny all;
    # pass to PHP-FPM or upstream as usual
}
```

**Custom error pages:**
```nginx
error_page 401 /401.html;
error_page 403 /403.html;
```

### Caddy (v2) (examples) {#caddy}
**Password‑protect `/wp-login.php` with Basic Auth:**
```caddyfile
# Hash passwords first: `caddy hash-password`
basicauth /wp-login.php {
    user1 JDJhJDEw$example-hash-value...
    # add more users as needed (one per line)
}
```
> Caddy requires **hashed** passwords in the Caddyfile.

**Limit access to `/wp-login.php` by IP:**
```caddyfile
@blacklist {
    not client_ip forwarded 203.0.113.15 203.0.113.16
    path /wp-login.php
}
respond @blacklist "Forbidden" 403 {
    close
}
```

**Return 401 for `/wp-admin/*` and serve a custom error page:**
```caddyfile
@wpadmin path /wp-admin/*
respond @wpadmin "Unauthorized" 401

handle_errors {
    @need401 status 401
    rewrite @need401 /401.html
    file_server
}
```

**Deny “no‑referrer” POSTs to login/comments (optional, use with caution):**
```caddyfile
# Legitimate clients or privacy tools may omit Referer; test before enforcing.
@protected path_regexp protected (wp-comments-post|wp-login)\.php$
@no_referer {
    not header Referer https://{host}*
    method POST
}
abort @no_referer
```
> Using `abort` immediately drops the connection, which is efficient for bots.

For more Caddy discussion and rationale, see: *Using Caddy to deter brute force attacks in WordPress* (community thread).

### Windows IIS (examples) {#iis}
**Restrict WP Admin by IP using `web.config`:**
```xml
<location path="wp-admin">
  <system.webServer>
    <security>
      <ipSecurity allowUnlisted="false">
        <add ipAddress="203.0.113.15" allowed="true" />
        <add ipAddress="203.0.113.16" allowed="true" />
      </ipSecurity>
    </security>
  </system.webServer>
</location>
```

**Custom 401 page:**
```xml
<system.webServer>
  <httpErrors errorMode="Custom">
    <remove statusCode="401" />
    <error statusCode="401" path="/401.html" responseMode="File" />
  </httpErrors>
</system.webServer>
```

---

## Host / CDN WAF Protections {#waf}
A managed WAF (Cloudflare, Sucuri, or your hosting provider) can:
- Filter known bad IPs and automated login attempts at the edge.
- Enforce bot management, challenge‑pages, and login‑specific rules.
- Apply **rate limits** to `/wp-login.php` and `/xmlrpc.php` globally.
- Add **CAPTCHA/turnstile** challenges for suspicious requests.

> Advantage: traffic is blocked before reaching your server, preserving resources during high‑volume attacks.

---

## Additional Hardening & Operational Tips {#hardening}
- **Do not use the username `admin`.** Create a separate admin account and demote or remove legacy users.
- **Limit administrator count;** use least‑privilege roles for day‑to‑day work.
- **Audit logs** for failed logins and enumerate sources; temporarily block abusive IPs (e.g., Fail2ban).
- **Avoid permanent country blocklists.** They can block legitimate users and are difficult to maintain.
- **Ensure HTTPS everywhere** to protect credentials in transit.
- **Backups:** Maintain tested, offline‑capable backups and rehearse restore procedures.

---

## See Also {#see-also}
- WordPress Advanced Administration: [Security](https://developer.wordpress.org/advanced-administration/security/)
- WordPress Advanced Administration: [Hardening WordPress](https://developer.wordpress.org/advanced-administration/security/hardening/)
- WordPress.com: [Brute Force Attack Protection](https://developer.wordpress.com/docs/platform-features/brute-force-attack-protection/)
- WordPress Core: [Application Passwords (integration guide)](https://make.wordpress.org/core/2020/11/05/application-passwords-integration-guide/)
- Using Caddy to deter brute force attacks in WordPress – community thread: https://caddy.community/t/using-caddy-to-deter-brute-force-attacks-in-wordpress/13579

---

## Notes on Deprecated / Legacy Content {#legacy-notes}
Older guidance often recommended heavy `.htaccess` rewrites, country IP blocklists, or BasicAuth over the entire `/wp-admin` directory. Today these measures are either unreliable, break AJAX‑based plugins, or degrade user experience. Prefer **edge‑level WAF**, **2FA**, **passkeys**, and **targeted rate‑limiting** instead. When you do apply server‑level blocks, scope them narrowly (e.g., `/wp-login.php`, `/xmlrpc.php`) and document exceptions your site needs (mobile apps, Jetpack, SSO).