# Running a Development Copy of WordPress

Having a development instance of WordPress is a good way to update, develop, and modify a website without interrupting the live version of WordPress. There are many ways to set up a development copy of WordPress, but this article will cover the basics, best practices, tips, and some tools to make running a development copy of WordPress much easier.

## Installing WordPress on your computer

Use these instructions to set up a local server environment for testing and development.

Installing WordPress locally is usually meant for development. Those interested in development can follow the instructions below to download and install WordPress locally.
- [wp-env](https://developer.wordpress.org/block-editor/reference-guides/packages/packages-env/) - a free, open-source development environment maintained by the WordPress core developer community.
- [WordPress Playground](https://developer.wordpress.org/playground/developers/local-development/) - a free, open-source project that runs WordPress in the browser. Its Playground CLI and Visual Studio Code extension run WordPress on your computer.
- [VVV or Varying Vagrant Vagrants](https://varyingvagrantvagrants.org/) - free, open-source local development environment maintained by members of the WordPress community.
- [XAMPP](https://www.apachefriends.org/) - free and open-source local development environment maintained by Apache Friends
- [MAMP](https://www.mamp.info/en/mac/) - free local development environment that has everything you need to install WordPress locally.
- [DDEV](https://docs.ddev.com/en/stable/users/quickstart/#wordpress) - free, open-source, development environment. Seamlessly share local sites over public domains, includes a database editor, Xdebug, and other performance profiling tools.
- [Lando](https://lando.dev/) – free, open-source development environment that offers a [plugin to install WordPress locally](https://docs.lando.dev/plugins/wordpress/).
- [AMPPS](https://ampps.com/downloads/) - WAMP/MAMP/LAMP stack with the Softaculous installer built in. It has a free plan.
- [Instant WordPress](https://instantwp.com/) - free, standalone, portable WordPress development environment for Windows and macOS.
- [WordPress Studio](https://developer.wordpress.com/studio/) - free, open-source app to install and manage multiple WordPress sites locally.

### Software Appliance - Ready-to-use

You may find that using a pre-integrated [software appliance](https://en.wikipedia.org/wiki/Software_appliance) is a great way to get up and running with WordPress, especially in combination with virtual machine software (e.g., VMware, VirtualBox, Xen HVM, KVM, Parallels).

A software appliance allows users to skip the manual installation of WordPress and its dependencies and instead deploy a self-contained system that requires little to no setup in just a couple of minutes.

- [TurnKey WordPress Appliance](https://www.turnkeylinux.org/wordpress): a free, open-source, Debian-based appliance with automatic security updates. It is available as an ISO image, or you can deploy it in the cloud.

## Copying a live site to a development copy

A development copy should have its own database tables. If it shares the live site's tables, every change you make while developing, such as changing a setting or publishing a test post, is also made on the live site.

When you copy a single site (for a multisite network, see [Moving WordPress Multisite](https://developer.wordpress.org/advanced-administration/upgrade/migrating/#moving-wordpress-multisite)), keep these points in mind:

- Restore a backup of the live site's [database](https://developer.wordpress.org/advanced-administration/security/backup/database/) and [files](https://developer.wordpress.org/advanced-administration/security/backup/files/) into your development environment. In the copy's `wp-config.php`, use your development database details and keep the live site's `$table_prefix`, so WordPress finds the imported tables.
- Change the site address before you open the copy in a browser. Until then, its login page and links use the live site's address. See [Changing The Site URL](https://developer.wordpress.org/advanced-administration/upgrade/migrating/#changing-the-site-url) and [Changing Your Domain Name and URLs](https://developer.wordpress.org/advanced-administration/upgrade/migrating/#changing-your-domain-name-and-urls).
- Set the [environment type](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/#wp-environment-type) to `local` for a copy on your own computer, or `staging` for a copy on a server that others can reach. By default, WordPress allows Application Passwords on a site without HTTPS only when the environment type is `local`.
- A copy keeps the live site's plugins, settings, and keys. When the copy is loaded, its active plugins, [scheduled tasks](https://developer.wordpress.org/plugins/cron/), and emails can still run and reach real users or outside services such as payment gateways. Review them before you work on the copy, for example by [disabling WP-Cron](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/#disable-cron-and-cron-timeout) before you first load the copy, so page loads do not start scheduled tasks (a direct request to `wp-cron.php`, for example from a server scheduler, still runs them), and by switching plugins that use outside services to test settings.

If your development copy is on a server that others can reach, such as a staging site, limit who can access it, for example with a password. You can also go to **Settings > Reading** and check **Discourage search engines from indexing this site**, but that only asks search engines not to index the site. It does not stop anyone from visiting it, and it is up to search engines to honor the request.
