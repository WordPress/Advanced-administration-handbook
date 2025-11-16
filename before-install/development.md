before-install/development.md# Running a Development Copy of WordPress

Having a development instance of WordPress is a good way to update, develop, and modify a website without interrupting the live version of WordPress. There are many ways to set up a development copy of WordPress, but this article will cover the basics, best practices, tips, and some tools to make running a development copy of WordPress much easier.

## Installing WordPress on your computer

Use these instructions to set up a local server environment for testing and development.

Installing WordPress locally is usually meant for development. Those interested in development can follow the instructions below to download and install WordPress locally.
- [wp-env](https://developer.wordpress.org/block-editor/reference-guides/packages/packages-env/) - a free, open-source development environment maintained by the WordPress core developer community.
- [VVV or Varying Vagrant Vagrants](https://varyingvagrantvagrants.org/) - free, open-source local development environment maintained by members of the WordPress community.
- [XAMPP](https://www.apachefriends.org/) - free and open-source local development environment maintained by Apache Friends
- [MAMP](https://www.mamp.info/en/mac/) - free local development environment that everything you need to install WordPress locally.
- [DDEV](https://ddev.readthedocs.io/en/stable/users/quickstart/#wordpress) - free, open-source, development environment. Seamlessly share local sites over public domains, includes a database editor, Xdebug, and other performance profiling tools.
- [Lando](https://lando.dev/) – free, open-source development environment that offers a [plugin to install WordPress locally](https://docs.lando.dev/plugins/wordpress/).
- [AMPPS](https://ampps.com/downloads/) – free WAMP/MAMP/LAMP stack with Softaculous Installer built in. It can 1-click install and upgrade WordPress and others as well.
- [Bitnami package for WordPress](https://bitnami.com/stack/wordpress) and [Bitnami package for WordPress Multisite](https://bitnami.com/stack/wordpress-multisite) - Bitnami packages for WordPress that provide a one-click install solution for WordPress or WordPress Multisite on your local computer or in the cloud.
- [Instant WordPress](https://instantwp.com/) - free, standalone, portable WordPress development environment for Windows that will run from a USB key.
- [WordPress Studio](https://developer.wordpress.com/studio/) - free, open-source app to install and manage multiple WordPress sites locally.

### Software Appliance - Ready-to-use

You may find that using a pre-integrated [software appliance](https://en.wikipedia.org/wiki/Software_appliance) is a great way to get up and running with WordPress, especially in combination with virtual machine software (e.g., VMWare, VirtualBox, Xen HVM, KVM).

Parallels is another software that can be used. Unlike virtual machine software, it requires payment. It allows you to run both Mac and Windows on your machine.

A software appliance allows users to skip the manual installation of WordPress and its dependencies and instead deploy a self-contained system that requires little to no setup in just a couple of minutes.

- [TurnKey WordPress Appliance](https://www.turnkeylinux.org/wordpress): a free Debian-based appliance that just works. It bundles a collection of popular WordPress plugins and features a small footprint, automatic security updates, SSL support, and a Web administration interface. Available as ISO, virtual machine images, or launch in the cloud.

### Unattended/automated installation of WordPress on Ubuntu Server 16.04 LTS

## Two WordPress Installations with One Database

**Note:** This method is NOT recommended if you plan on doing database development.

A popular approach to running a local copy of your live site is using the same local and live database. Using the same database will allow you to work on your local copy and push changes from local to your production with no break in uptime.

**Setup of the local copy**

Once you have your local files set up, you must modify wp-config.php in the root of your local install.

```
define('WP_HOME',  "https://{$_SERVER['HTTP_HOST']}");
define('WP_SITEURL', "https://{$_SERVER['HTTP_HOST']}");

ob_start( 'ob_replace_home_url' );
function ob_replace_home_url( $content ) {
    $home_urls = array(
        'https://site.testing.example.com',
        'https://site.example.com',
        'https://site.authoring.testing.example.com',
        'https://site.authoring.example.com',
    );

    $content = str_replace( $home_urls, WP_HOME, $content );

    return $content;
}
```

### Using a Drop-In

What if we don’t want to hack core code? Avoiding changes to core code is a good practice for easy upgrading and code-sharing. There is even a filter for this (`pre_option_siteurl` and `pre_option_home`) but there’s a problem: within **wp-settings.php**,

- the filter can’t be defined until after line 65 when `functions.php` is included
- WordPress makes calls to `get_option` on line 155 of (via `wp_plugin_directory_constants()`)
- plugins aren’t defined until later down around line 194.

However, between lines 65 and 155, there is something we can use, namely the loading of the drop-in `db.php`; the filter can be safely defined there. (However, this is perhaps only halfway towards “not core” code.) Check if you already have an existing wp-content/db.php before trying this technique. Plugins like W3 Total Cache use it for similar reasons.

```
<?php
// paste this in a (new) file, wp-content/db.php
add_filter ( 'pre_option_home', 'test_localhosts' );
add_filter ( 'pre_option_siteurl', 'test_localhosts' );
function test_localhosts( ) {
  if (... same logic as before to see if on dev site ...) {
     return "https://my.example.com/dev";
  }
  else return false; // act as normal; will pull main site info from db
}
```
