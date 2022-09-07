# WordPress Advanced Administration Handbook
This is the repository for the **WordPress Advanced Administration Handbook** a collaboration between the Hosting Team and the Documentation Team.

The **WordPress Advanced Administration Handbook** will be a new section in the "Hub" [developer.wordpress.org](https://developer.wordpress.org/) where all the most technical documentation for users and developers will be moved, so the documentation will be simple, and this one will have code and be more complex.

## Some information
- [Project](https://github.com/orgs/WordPress/projects/47)
- [Inventory](https://github.com/orgs/WordPress/projects/26/views/1)
- [Tickets](https://github.com/WordPress/Documentation-Issue-Tracker/labels/advanced%20administration)
- [Handbook](https://github.com/zzap/WordPress-Advanced-administration-handbook)
- [Meta ticket](https://meta.trac.wordpress.org/ticket/6411)

## Roadmap
- [x] Phase 0: Create an initial structure to understand the categorization.
- [x] Phase 1: Create the empty-files with a link inside, so there is all the structure.
- [ ] Phase 2: Add the content (only copying from the original page and create the content structure).
- [ ] Phase 3: Check and improve the content.
- [ ] Phase 4: Publish a first version of the Advanced Admin Documentation.

## File Structure

Based on [WordPress Advanced Administration Handbook](https://docs.google.com/document/d/1fVIw3DztzyVY18RDPCGk-kDYTO6gzHtx81o7aitGijo/)

- [README](README.md)
- [LICENSE](LICENSE)
- [CODE_OF_CONDUCT](CODE_OF_CONDUCT.md)
- [WordPress Advanced Administration Handbook](index.md)
  - [Before You Install](before-install/index.md)
    - [Creating Database for WordPress](before-install/creating-database.md)
    - [How to install WordPress](before-install/howto-install.md)
      - Installing WordPress at popular Hosting Companies
    - [Running a Development Copy of WordPress](before-install/development.md)
      - Installing WordPress on your own Computer
    - [Installing WordPress in your language](before-install/in-your-language.md)
    - [Installing Multiple WordPress Instances](before-install/multiple-instances.md)
  - [Server configuration](server/index.md)
    - [Changing File Permissions](server/file-permissions.md)
    - [Finding Server Info](server/server-info.md)
    - [Giving WordPress Its Own Directory](server/wordpress-in-directory.md)
    - [Configuring Wildcard Subdomains](server/subdomains-wildcard.md)
    - [Emptying a Database Table](server/empty-database.md)
    - [Web servers](server/web-server.md)
      - Apache HTTPD
      - Nginx
    - [Control Panels](server/control-panel.md)
      - Using cPanel
      - Using Plesk
  - [WordPress configuration](wordpress/index.md)
    - [wp-config.php](wordpress/wp-config.md)
      - Editing wp-config.php
    - [Site Architecture](wordpress/site-architecture.md)
    - [Cookies](wordpress/cookies.md)
    - [Update Services](wordpress/update-services.md)
    - [Editing Files](wordpress/edit-files.md)
    - [CSS](wordpress/css.md)
    - [Multilingual WordPress](wordpress/multilingual.md)
    - [oEmbed](wordpress/oembed.md)
  - [Upgrading / Migration](upgrade/index.md)
    - [FTP Clients](upgrade/ftp.md)
      - Using FileZilla
    - [phpMyAdmin](upgrade/phpmyadmin.md)
    - [Upgrading](upgrade/upgrading.md)
      - Upgrading WordPress – Extended Instructions
    - [Migration](upgrade/migrating.md)
      - Changing The Site URL
      - Migrating multiple blogs into WordPress multisite
      - Moving WordPress
  - [WordPress Multisite / Network](multisite/index.md)
    - [Before You Create A Network](multisite/prepare-network.md)
    - [Create A Network](multisite/create-network.md)
    - [WordPress Multisite Domain Mapping](multisite/domain-mapping.md)
    - [Multisite Network Administration](multisite/administration.md)
    - [Network Admin](multisite/admin.md)
      - Network Admin Sites Screen
      - Network Admin Updates Screen
  - [Plugins](plugins/index.md)
    - [Must Use Plugins](plugins/mu-plugins.md)
  - [Themes](themes/index.md)
  - [Security](security/index.md)
    - [Backups](security/backup.md)
      - Backing Up Your Database
      - Backing Up Your WordPress Files
      - Configuring Automatic Background Updates
      - Restoring Your Database From Backup
    - [Security](security/security.md)
      - Why should I use HTTPS
      - Administration Over SSL
      - Hardening WordPress
      - Brute Force Attacks
      - Two Step Authentication
  - [Performance / Optimization](performance/index.md)
    - [Cache](performance/cache.md)
    - [Optimization](performance/optimization.md)
  - [Debugging WordPress](debug/index.md)
    - [Debugging in WordPress](debug/debug-wordpress.md)
    - [Debugging a WordPress Network](debug/debug-network.md)
    - [Using Your Browser to Diagnose JavaScript Errors](debug/debug-javascript.md)
    - [Test Driving WordPress](debug/test-driving.md)
  - [Resources](resources/index.md)
