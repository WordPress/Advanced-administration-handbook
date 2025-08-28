# HTTPS

HTTPS (Hypertext Transfer Protocol Secure) is the encrypted communication protocol used on the modern web. It ensures that traffic between a visitor’s browser and your server is private and cannot be intercepted or modified in transit. Virtually all major websites now require HTTPS, and most browsers label non-HTTPS sites as *Not Secure*.

If a site is using HTTPS, browsers display a padlock icon in the address bar:

*Screenshot of the "secure site" padlock icon*

---

## Why Use HTTPS?

- **Performance**: Modern web performance features such as HTTP/2 and HTTP/3 require HTTPS. These protocols enable multiplexing, header compression, and faster delivery — making HTTPS sites faster than HTTP.  
- **Trust**: Users expect to see the padlock icon or “secure” connection indicators. Without it, browsers display warnings that discourage engagement.  
- **Security for Payments & Data**: HTTPS protects login details, payment information, and other sensitive data. For e-commerce sites, HTTPS is also a PCI DSS requirement.  
- **Search Engine Optimization**: Google and other search engines use HTTPS as a ranking factor. Sites without HTTPS may rank lower.  
- **Reputation**: Browsers mark non-HTTPS sites as *Not Secure*. Running your site without HTTPS risks damaging visitor trust.

---

## HTTPS in WordPress

WordPress is fully compatible with HTTPS once a TLS/SSL certificate is installed and configured on your web server. Today, most hosting providers automatically provision free certificates (often via [Let’s Encrypt](https://letsencrypt.org/)). If your host does not, you can install and manage your own certificate using tools such as [Certbot](https://certbot.eff.org/).

### Force WordPress Admin over HTTPS

To enforce encrypted logins and admin sessions, set the following constant in your `wp-config.php` file:

```php
define( 'FORCE_SSL_ADMIN', true );
```

> ⚠️ Note: The older constant `FORCE_SSL_LOGIN` was deprecated in WordPress 4.0. Use `FORCE_SSL_ADMIN` instead.

This ensures all login and admin traffic runs over HTTPS.

---

## Using a Reverse Proxy or CDN

If your WordPress site is behind a reverse proxy (e.g., Nginx, Varnish, Cloudflare) that handles SSL termination, WordPress may need to be told to recognize forwarded HTTPS headers. Without this, you may experience redirect loops.

Example:

```php
define( 'FORCE_SSL_ADMIN', true );

// In some setups HTTP_X_FORWARDED_PROTO might contain a list (e.g., http,https)
// so check for 'https' explicitly.
if ( isset($_SERVER['HTTP_X_FORWARDED_PROTO']) && strpos($_SERVER['HTTP_X_FORWARDED_PROTO'], 'https') !== false ) {
    $_SERVER['HTTPS'] = 'on';
}
```

For Nginx, ensure you pass the correct headers:

```nginx
location / {
    proxy_pass http://your_host_name:your_port;
    proxy_set_header Host $host:$server_port;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Host $server_name;
    proxy_set_header X-Forwarded-Proto $scheme;
    proxy_redirect off;
}
```

---

## Best Practices for HTTPS

- **Automatic Certificates**: Use Let’s Encrypt or your host’s SSL manager to automatically renew certificates.  
- **Redirects**: Configure your server or CDN to redirect all HTTP traffic to HTTPS (301 redirect).  
- **Mixed Content**: Ensure all scripts, images, and assets load over HTTPS to avoid browser warnings.  
- **HSTS**: Add an HTTP Strict Transport Security (HSTS) header to enforce HTTPS for returning visitors. Example (Nginx):  
  ```nginx
  add_header Strict-Transport-Security "max-age=31536000; includeSubDomains" always;
  ```  
- **Testing**: Use [Qualys SSL Labs](https://www.ssllabs.com/ssltest/) to check your certificate and configuration.

---

## Summary

HTTPS is no longer optional. It improves speed, boosts SEO, secures sensitive data, and maintains user trust.  

WordPress makes it simple to enforce HTTPS via `FORCE_SSL_ADMIN`, and most hosting providers include free SSL certificates by default. For advanced setups with reverse proxies or CDNs, ensure forwarded headers are passed correctly and configure redirects and HSTS for maximum security.
