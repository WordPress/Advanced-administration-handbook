# WordPress Advanced Administration Handbook
This is the repository for the **WordPress Advanced Administration Handbook** a collaboration between the Hosting Team and the Documentation Team.

The **WordPress Advanced Administration Handbook** will be a new section in the "Hub" [developer.wordpress.org](https://developer.wordpress.org/) where all the most technical documentation for users and developers will be moved, so the documentation will be simple, and this one will have code and be more complex.

## Project information

- [Project](https://github.com/orgs/WordPress/projects/47)
- [Inventory](https://github.com/orgs/WordPress/projects/26/views/1)
- [Tickets](https://github.com/WordPress/Documentation-Issue-Tracker/labels/advanced%20administration)
- [Handbook](https://github.com/WordPress/Advanced-administration-handbook)
- [Meta ticket](https://meta.trac.wordpress.org/ticket/6411)

The future URL for this handbook will be at [https://developer.wordpress.org/advanced-administration/](https://developer.wordpress.org/advanced-administration/) (by [Meta ticket](https://meta.trac.wordpress.org/ticket/6411)).

## Roadmap

- [x] Phase 0: Create an initial structure to understand the categorization.
- [x] Phase 1: Create the empty-files with a link inside, so there is all the structure.
- [x] Phase 2: Add the content (only copying from the original page and create the content structure).
- [x] Phase 3: Publish a first version of the Advanced Admin Documentation.
- [ ] Phase 4: Check and improve the content.

## File Structure

Based on [WordPress Advanced Administration Handbook](https://docs.google.com/document/d/1fVIw3DztzyVY18RDPCGk-kDYTO6gzHtx81o7aitGijo/)

- [README](README.md)
- [LICENSE](LICENSE)
- [CODE_OF_CONDUCT](CODE_OF_CONDUCT.md)
- [WordPress Advanced Administration Handbook](index.md) ([🔗](https://developer.wordpress.org/advanced-administration/))
  - [Before You Install](before-install/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/))
    - [Creating Database for WordPress](before-install/creating-database.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/creating-database/))
    - [How to install WordPress](before-install/howto-install.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/howto-install/))
    - [Running a Development Copy of WordPress](before-install/development.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/development/))
    - [Installing WordPress in your language](before-install/in-your-language.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/in-your-language/))
    - [Installing Multiple WordPress Instances](before-install/multiple-instances.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/multiple-instances/))
    - [Install WordPress at popular providers](before-install/popular-providers.md) ([🔗](https://developer.wordpress.org/advanced-administration/before-install/popular-providers/))
  - [Server configuration](server/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/))
    - [Changing File Permissions](server/file-permissions.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/file-permissions/))
    - [Finding Server Info](server/server-info.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/server-info/))
    - [Giving WordPress Its Own Directory](server/wordpress-in-directory.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/wordpress-in-directory/))
    - [Configuring Wildcard Subdomains](server/subdomains-wildcard.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/subdomains-wildcard/))
    - [Emptying a Database Table](server/empty-database.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/empty-database/))
    - [Web servers](server/web-server.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/web-server/))
      - [nginx](server/nginx.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/web-server/nginx/))
    - [Control Panels](server/control-panel.md) ([🔗](https://developer.wordpress.org/advanced-administration/server/control-panel/))
  - [WordPress configuration](wordpress/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/))
    - [wp-config.php](wordpress/wp-config.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/))
    - [Site Architecture](wordpress/site-architecture.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/site-architecture/))
    - [Cookies](wordpress/cookies.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/cookies/))
    - [Update Services](wordpress/update-services.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/update-services/))
    - [Editing Files](wordpress/edit-files.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/edit-files/))
    - [CSS](wordpress/css.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/css/))
    - [Feeds](wordpress/feeds.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/feeds/))
    - [Multilingual WordPress](wordpress/multilingual.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/multilingual/))
    - [oEmbed](wordpress/oembed.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/oembed/))
    - [Loopbacks](wordpress/loopback.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/loopback/))
    - [Common errors](wordpress/common-errors.md) ([🔗](https://developer.wordpress.org/advanced-administration/wordpress/common-errors/))
  - [Upgrading / Migration](upgrade/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/upgrade/))
    - [FTP Clients](upgrade/ftp.md) ([🔗](https://developer.wordpress.org/advanced-administration/upgrade/ftp/))
      - [Using FileZilla](upgrade/filezilla.md) ([🔗](https://developer.wordpress.org/advanced-administration/upgrade/ftp/filezilla/))
    - [phpMyAdmin](upgrade/phpmyadmin.md) ([🔗](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/))
    - [Upgrading](upgrade/upgrading.md) ([🔗](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/))
    - [Migration](upgrade/migrating.md) ([🔗](https://developer.wordpress.org/advanced-administration/upgrade/migrating/))
  - [WordPress Multisite / Network](multisite/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/))
    - [Before You Create A Network](multisite/prepare-network.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/))
    - [Create A Network](multisite/create-network.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/create-network/))
    - [WordPress Multisite Domain Mapping](multisite/domain-mapping.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/domain-mapping/))
    - [Multisite Network Administration](multisite/administration.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/administration/))
    - [Network Admin](multisite/admin.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/admin/))
    - [Migrate WordPress sites into WordPress Multisite](multisite/sites-multisite.md) ([🔗](https://developer.wordpress.org/advanced-administration/multisite/sites-multisite/))
  - [Plugins](plugins/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/plugins/))
    - [Must Use Plugins](plugins/mu-plugins.md) ([🔗](https://developer.wordpress.org/advanced-administration/plugins/mu-plugins/))
  - [Themes](themes/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/themes/))
  - [Security](security/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/))
    - [Your password](security/logging-in.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/logging-in/))
    - [Multi-Factor Authentication](security/mfa.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/mfa/))
    - [Backups](security/backup.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/backup/))
      - [Database Backup](security/backup.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/backup/database/))
      - [Files Backup](security/backup.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/backup/files/))
    - [HTTPS](security/https.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/https/))
    - [Brute Force Attacks](security/brute-force.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/brute-force/))
    - [Hardening WordPress](security/hardening.md) ([🔗](https://developer.wordpress.org/advanced-administration/security/hardening/))
  - [Performance / Optimization](performance/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/performance/))
    - [Cache](performance/cache.md) ([🔗](https://developer.wordpress.org/advanced-administration/performance/cache/))
    - [Optimization](performance/optimization.md) ([🔗](https://developer.wordpress.org/advanced-administration/performance/optimization/))
  - [Debugging WordPress](debug/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/debug/))
    - [Debugging in WordPress](debug/debug-wordpress.md) ([🔗](https://developer.wordpress.org/advanced-administration/debug/debug-wordpress/))
    - [Debugging a WordPress Network](debug/debug-network.md) ([🔗](https://developer.wordpress.org/advanced-administration/debug/debug-network/))
    - [Using Your Browser to Diagnose JavaScript Errors](debug/debug-javascript.md) ([🔗](https://developer.wordpress.org/advanced-administration/debug/debug-javascript/))
    - [Test Driving WordPress](debug/test-driving.md) ([🔗](https://developer.wordpress.org/advanced-administration/debug/test-driving/))
  - [Resources](resources/index.md) ([🔗](https://developer.wordpress.org/advanced-administration/resources/))

## Recommendations

### Style guide

- [WordPress Documentation Style Guide](https://make.wordpress.org/docs/style-guide/)
  - [General guidelines](https://make.wordpress.org/docs/style-guide/general-guidelines/)
  - [Language and grammar](https://make.wordpress.org/docs/style-guide/language-grammar/)
  - [Punctuation](https://make.wordpress.org/docs/style-guide/punctuation/)
  - [Formatting](https://make.wordpress.org/docs/style-guide/formatting/)
  - [Linking](https://make.wordpress.org/docs/style-guide/linking/)
  - [Developer content](https://make.wordpress.org/docs/style-guide/developer-content/)

### External linking

- [External Linking Policy (Summary)](https://make.wordpress.org/docs/handbook/documentation-team-handbook/external-linking-policy/)

### Example domains

When using an example domain, please, use _example.com_, _example.net_, _example.org_, _example.info_, or _example.biz_ (and whatever subdomain you want).

The country domains have their own example names, like _dominio.es_, that probably need check.

See [RFC 2606](https://www.rfc-editor.org/rfc/rfc2606), [.INFO Reserved Strings](https://www.icann.org/en/registry-agreements/info/info-registry-agreement--list-of-reserved-tld-strings-26-5-2010-en), [.BIZ Reserved Strings](https://www.icann.org/en/registry-agreements/biz/biz-registry-agreement--list-of-reserved-tld-strings-19-6-2009-en).

### Example IP / IP network / IP range

When using IP addresses, please use:

- TEST-NET-1: 192.0.2.0/24 (192.0.2.0–192.0.2.255)
- TEST-NET-2: 198.51.100.0/24 (198.51.100.0–198.51.100.255)
- TEST-NET-3: 203.0.113.0/24 (203.0.113.0–203.0.113.255)

See [RFC 1166](https://datatracker.ietf.org/doc/html/rfc1166).
