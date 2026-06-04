# Server configuration

Server configuration covers the hosting environment that runs WordPress. This includes the web server, file and directory permissions, database tools, email delivery, domain routing, and hosting control panels.

Use this section when you need to prepare a server for WordPress, adjust an existing server, or understand which server-level setting affects a WordPress feature. These pages are most useful for site administrators, hosting providers, and users who have access to server or hosting control panel settings.

Some server changes can make a site unavailable if they are applied incorrectly. Review the relevant page before making changes and create a current backup when working with files or the database.

## Choose a server topic

- Use [Web servers](https://developer.wordpress.org/advanced-administration/server/web-server/) when you need to understand how WordPress works with server software such as Apache HTTPD or Nginx.
- Use [Changing File Permissions](https://developer.wordpress.org/advanced-administration/server/file-permissions/) when WordPress cannot write files, updates fail, uploads fail, or you need to review ownership and permissions.
- Use [Mail](https://developer.wordpress.org/advanced-administration/server/mail/) when WordPress email is not being sent or delivered reliably.
- Use [Finding Server Info](https://developer.wordpress.org/advanced-administration/server/server-info/) when you need PHP, database, operating system, or server software details for installation or troubleshooting.
- Use [Control Panels](https://developer.wordpress.org/advanced-administration/server/control-panel/) when your host provides tools such as WP Toolkit, cPanel, WHM, or Plesk.

## Web server configuration

The [Web servers](https://developer.wordpress.org/advanced-administration/server/web-server/) page explains the role of the web server and links to configuration guidance for Apache HTTPD and Nginx.

- [Apache HTTPD / .htaccess](https://developer.wordpress.org/advanced-administration/server/web-server/httpd/) covers WordPress rewrite rules, `.htaccess` examples, and related Apache configuration.
- [Nginx](https://developer.wordpress.org/advanced-administration/server/web-server/nginx/) covers standalone Nginx configuration for WordPress, including single site and multisite examples.

## Files, directories, and database tasks

- [Changing File Permissions](https://developer.wordpress.org/advanced-administration/server/file-permissions/) explains how file and directory permissions work and how they affect WordPress updates, uploads, plugins, themes, and configuration files.
- [Emptying a Database Table](https://developer.wordpress.org/advanced-administration/server/empty-database/) explains how to empty a table with phpMyAdmin. This should only be done after confirming the correct table and creating a backup.
- [Finding Server Info](https://developer.wordpress.org/advanced-administration/server/server-info/) explains how to collect server details such as PHP version, server software, and database version for installation or troubleshooting.

## Domains, subdomains, and directory layout

- [Giving WordPress Its Own Directory](https://developer.wordpress.org/advanced-administration/server/wordpress-in-directory/) explains how to keep WordPress core files in a subdirectory while serving the site from the domain root.
- [Configuring Wildcard Subdomains](https://developer.wordpress.org/advanced-administration/server/subdomains-wildcard/) explains wildcard subdomain setup for domain-based multisite networks.

## Mail and hosting tools

- [Mail](https://developer.wordpress.org/advanced-administration/server/mail/) explains how WordPress sends email through the server environment and what administrators should consider for SMTP, relays, and deliverability.
- [Control Panels](https://developer.wordpress.org/advanced-administration/server/control-panel/) covers common hosting control panel tools such as WP Toolkit for cPanel, WHM, and Plesk.

## Before changing server settings

If you are using managed hosting, check your host's documentation or support channels before changing server configuration. Hosts often manage web server rules, PHP settings, mail delivery, backups, and control panel features for you.

For self-managed servers, test changes in a staging environment when possible. Keep a record of the original configuration so you can restore it if a change does not work as expected.
