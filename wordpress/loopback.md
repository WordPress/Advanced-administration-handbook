# Loopbacks

A loopback request is when WordPress tries to connect back to its own site.

WordPress uses loopback requests to start WP-Cron, which runs scheduled posts and other scheduled events that plugins or themes may add.

Loopback requests are also used by the built-in plugin and theme file editors. When you make a change, WordPress connects back to the site to check that the change does not break the site.

## Troubleshooting loopback issues {#troubleshooting-loopback-issues}

If scheduled posts or other timed events are not running, or if Site Health reports that loopback requests are failing, you may need to troubleshoot the site.

The most common cause of loopback failures is a plugin or theme conflict. Start with the normal troubleshooting steps:

## Common troubleshooting steps {#common-troubleshooting-steps}

- Deactivate **all plugins** to see whether this resolves the problem. If it does, reactivate the plugins one by one until you find the plugin causing the issue. If you cannot access your admin dashboard, try resetting the plugins folder by [SFTP/FTP](https://developer.wordpress.org/advanced-administration/upgrade/ftp/) or [phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/). For more help, read [How to deactivate all plugins when you can't log in to wp-admin](https://wordpress.org/documentation/article/faq-troubleshooting/). Sometimes, an apparently inactive plugin can still cause problems. Also remember to deactivate any plugins in the `mu-plugins` folder. The easiest way is to temporarily rename that folder to `mu-plugins-old`.
- Switch to a bundled default WordPress theme to rule out theme-specific problems. If you cannot log in to change themes, you can temporarily move or rename the active theme folder by [SFTP/FTP](https://developer.wordpress.org/advanced-administration/upgrade/ftp/) so WordPress can fall back to an installed default theme.
- If the problem continues after disabling plugins and switching themes, contact your hosting provider. Ask whether server-side security rules, firewall rules, DNS configuration, SSL configuration, or HTTP authentication are blocking the site from requesting its own URL.
