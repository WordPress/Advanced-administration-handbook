# Running a Development Copy of WordPress

Having a development instance of WordPress is a good way to update, develop and make modifications to a website while not interrupting the live version of WordPress. There are many ways to setup a development copy of WordPress but this article will cover the **basics, best practices, tips** and some **tools** to make running a development copy of WordPress a lot easier.

## Installing WordPress on your own computer

Setting up a local server environment is fundamental for testing and developing WordPress. This list is not exhaustive, but here are several local server options to choose from:

- [Local](https://localwp.com/) (Mac, Windows, Linux)
- [Lando](https://docs.lando.dev/wordpress/) (Mac, Windows, Linux)
- [AMPPS](https://ampps.com/downloads/) (Mac, Windows, Linux)
- [MAMP](https://www.mamp.info/) (Mac & Windows)
- [Bitnami package for WordPress](https://bitnami.com/stack/wordpress) (Mac, Windows, Linux)
- [Instant WordPress](https://instantwp.com/) (Mac & Windows)
- [Studio by WordPress.com](https://developer.wordpress.com/studio/) (Mac & Windows)
- [Docker](https://www.docker.com/) (Mac, Windows, Linux)
- [XAMPP](https://www.apachefriends.org/) (Mac, Windows, Linux)

It's advisable to research various options before settling on a local server program that best matches your needs. For more information, read the [Setting Up a Development Environment](https://make.wordpress.org/core/handbook/tutorials/installing-a-local-server/) documentation in the Core Handbook, which includes tutorials for getting started with some of the aforementioned options.

### Software Appliance - Ready-to-use

You may find that using a pre-integrated [software appliance](https://en.wikipedia.org/wiki/Software_appliance) is a great way to get up and running with WordPress, especially in combination with virtual machine software (e.g., VMWare, VirtualBox, Xen HVM, KVM).

Another software that can be used is Parallels, which you would have to pay for unlike virtual machine software. It allows you to run both Mac and Windows on your machine.

A software appliance allows users to altogether skip manual installation of WordPress and its dependencies, and instead deploy a self-contained system that requires little to no setup, in just a couple of minutes.

- [TurnKey WordPress Appliance](https://www.turnkeylinux.org/wordpress): a free Debian-based appliance that just works. It bundles a collection of popular WordPress plugins and features a small footprint, automatic security updates, SSL support and a Web administration interface. Available as ISO, various virtual machine images, or launch in the cloud.

### Unattended/automated installation of WordPress on Ubuntu Server 16.04 LTS

## Two WordPresses, One Database

**Note:** If you are planing on doing database development, this method is NOT recommended.

A popular approach to running a local copy of your live site is to use the same database for both local and live. Using the same database will allow you to work on you local copy and simply push changes from local to your production with no break in uptime.

**Setup of the local copy**

Once you have your local files setup, you will need to modify wp-config.php in the root of your local install.

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

What if we don’t want to hack core code? (Which is a good practice for easy upgrading and sharing code.) There is even a filter for this (pre_option_siteurl and pre_option_home) but there’s a problem: within **wp-settings.php**,

- the filter can’t be defined until after line 65 when `functions.php` is included
- WordPress makes calls to `get_option` on line 155 of (via `wp_plugin_directory_constants()`)
- plugins aren’t defined until later down around line 194.

However, in between lines 65 and 155, there is something we can use, namely the loading of the drop-in `db.php`; the filter can be safely defined there. (However, this is perhaps only halfway towards “not core” code.) Check if you already have an existing wp-content/db.php before trying this technique. It is used by packages like W3 Total Cache for similar reasons.

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

## Changelog

- 2022-09-27: Original content from [Running a development copy of wordpress](https://wordpress.org/documentation/article/running-a-development-copy-of-wordpress/) and [installing wordpress on your own computer](https://wordpress.org/documentation/article/installing-wordpress-on-your-own-computer/).
