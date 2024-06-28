# Control Panels

## WP Toolkit for cPanel & WHM and Plesk

WP Toolkit is a single management interface that allows you to install, configure, and manage WordPress® easily. WP Toolkit can install, configure, and manage WordPress versions 4.9 or later.

cPanel & WHM versions 102 and above install WP Toolkit by default. WP Toolkit is available in Plesk if the server administrator has installed the WP Toolkit extension.

## cPanel & WHM

This tutorial describes how to install WordPress in the cPanel & WHM control panel using WP Toolkit. For other options to install WordPress in cPanel & WHM, read cPanel's [How to Install WordPress with cPanel](https://docs.cpanel.net/knowledge-base/third-party/how-to-install-wordpress-with-cpanel/) documentation.

To install WordPress via WP Toolkit, perform the following actions:

1. Log in to the cPanel interface with the information provided by your hosting company.
2. Select _WP Toolkit_ from the lefthand menu bar. The _WP Toolkit_ interface will appear.
3. Click _Install WordPress_ to create a new WordPress installation.

### Install WordPress

In the _Install Wordpress_ interface, click _Install_ to use the default settings. Or provide the following optional information to customize your installation, then click _Install_:

   * The installation directory. By default, the installation uses the account's `public_html` directory.
   * The title for the website.
   * The plugin or theme set.
   * The language of the website.
   * The version of WordPress to install. By default, the installation uses the current WordPress version.
   * The WordPress Administrator username and password.
   * The database name, table prefix, username and password.
   * The automatic update settings for the WordPress software, plugins, and themes.

Click _Install Plugins_ if you want to install plugins and themes, or click _No, thanks_ to optionally install them later.

## Plesk

This tutorial provides step-by-step examples of installing Plesk WP Toolkit using the Plesk Web Installer.
 
### Using Web Installer

Install Plesk using Web Installer please open your browser and go to [get.plesk.com](https://get.plesk.com/). For Plesk installation, it is required a fresh Linux server with access to the Internet. You can install Plesk on any supported Linux-based OS.

![Image_1](https://user-images.githubusercontent.com/19301688/189542599-4fce4d63-8060-416e-9fdf-f21ae62c87e1.png)

Provide your server's IP address or hostname, enter your root password, or just add a private key.
 
* Accept the terms of End-User License Agreement and click Install button.
* Relax and wait for some time to let the installation be finalized.
* Click on the Login link. No worries about "secure connection warnings", just make an exception.

### Installing WordPress

![image_2](https://user-images.githubusercontent.com/19301688/189542665-78f52a1c-e92b-4d70-bb5d-899ac02cc57e.png)

* For an express installation, click Install (Quick). The latest version of WordPress will be installed, and the default settings will be used. The new instance will be available via HTTPS if SSL/TLS support is enabled for the domain.
* If you want to change the default installation settings, click Install (Custom). This enables you to set up the administrator user, select the desired WordPress version, specify the database name, select auto-update settings, and more.

### Managing WordPress Instances

Go to WordPress to see all your WordPress instances. WP Toolkit groups information about each instance in blocks we call cards.

![image_3](https://user-images.githubusercontent.com/19301688/189542692-5d6f38b5-1b32-4de8-8f40-2abe9a5d1d86.png)

A card shows a screenshot of your website and features several controls that give you easy access to frequently used tools. The screenshot changes in real time to reflect the changes you make to your website. For example, if you switch the maintenance mode on or change the WordPress theme, the screenshot of the website will change immediately.

### Tools

In the "Tools" section, click to access the following WP Toolkit features:

![image_4](https://user-images.githubusercontent.com/19301688/189542713-abf476de-fcbd-4113-9975-1c2961765190.png)

* "Sync" to synchronize the content of your website with another one.
* "Clone" to make a full copy of your website.
* "Manage Files" to manage the website's files in File Manager.
* "Back Up/Restore" to create a backup of your website and restore it if necessary.

The controls below give you easy access to the following settings and tools:

* "Search engine indexing" shows your website in search results of search engines.
* "Caching (nginx)" speeds up the website load time and reduces server load.
* "Debugging" helps you debug a website that is not ready for viewing and being tested or developed.
* "Maintenance mode" hides your website's content from visitors.
* "Password Protection" specifies the password you will use to log in to WordPress from Plesk.

## Changelog

- 2023-04-25: Removed outdated manual instructions from cPanel section and combined common WP Toolkit info for cPanel and Plesk.
- 2023-01-26: Original copied from [Using cPanel](https://wordpress.org/documentation/article/using-cpanel/).
- 2022-09-11: Original copied for Plesk.
