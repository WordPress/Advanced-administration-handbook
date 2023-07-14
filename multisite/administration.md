# Multisite Network Administration

Once you've [created a Multisite Network](https://developer.wordpress.org/advanced-administration/multisite/create-network/), there are some additional things you might need to know about advanced administration, due to the additional complexity of a Multisite. Even if you're familiar with WordPress, the location and behavior of Multisite Network Administration can be confusing.

## User Access & Capabilities {#user-access-capabilities}

By design, all users who are added to your network will have _subscriber_ access to **all sites** on your network. To allocate a different default role for users on individual sites, you must use a plugin.

The capabilities of the site administrator role are also reduced in a WordPress Network. Site admins cannot install new themes or plugins and cannot edit the profiles of users on their site. Only the Network Admin (aka Super Admin) has the ability to perform these tasks in a WordPress network.

## Permalinks in SubFolder Installs {#permalinks-in-subfolder-installs}

While permalinks will continue to work, the main site (i.e. the first one created) will have an extra entry of `blog`, making your URLs appear like `domain.com/blog/YYYY/MM/POSTNAME`.

This is by design, in order to prevent collisions with SubFolder installs. Currently there is no easy way to change it, as doing so prevents WordPress from auto-detecting collisions between your main site and any subsites. This will be addressed, and customizable, in a future version of WordPress.

Also note that the `blog` prefix is not used for static pages which will be accessible directly under the base address, e.g. `domain.com/PAGENAME`. If you try to create a static page in the first site with the name of another existing site on the network, the page's permalink will get a suffix (e.g. `domain.com/PAGENAME-2`). If you create a new site with the slug of an existing static page, the static page will not be reachable anymore. To prevent this, you can add the names of your static pages to the blacklist so that no site with that name can be created.

## Uploaded File Path {#uploaded-file-path}

Your first site on a fresh install will put uploaded files in the traditional location of `/wp-content/uploads/`, however all _subsequent_ sites on your network will be in the `/wp-content/uploads/sites/` folder, in their own subfolder based on the site number, designated by the database. These files will be accessible via that URL.

This is a change from Multisite 3.0-3.4.2, where images of subsites were stored in `/wp-content/blogs.dir/` and were shown in http://example.com/files/ and http://example.com/sitename/files and so on. If you started with a Multisite install older than 3.5, it is _not_ an error if your images show with the URL of `/files/`.

Regardless of WP version, these locations cannot be changed by site admins. Only the network admin can make changes on the site settings page. It is not recommended that you change these without understanding how both the `ms-files.php` works in conjunction with your `.htaccess`, as it can easily become non-functional. If the `/files/` urls aren't working, it's indicative of a misconfigured .htaccess or httpd.conf file on your server.

## Plugins {#plugins}

Plugins now have additional flexibility, depending upon their implementation across the network. All plugins are installed on the network dashboard's plugin page, and can be activated either per-site or for the entire network.

* **Site Specific Plugins:** These plugins are activated from within the plugins page of a single specific site. Some plugins (contact forms, for example) work best when they are single-site activated, so that they can store data and settings in that single site's database tables, instead of the tables for the whole network. WordPress Plugins to be single-site activated/deactivated are stored in the plugins directory.
* **Network Plugins:** Network admins may ‘network activate' plugins in the Network Admin dashboard for plugins. Once ‘network activated', plugins will become active in all sites. ‘Network Activated' plugins are indicated as "Network Active" in plugin lists in the dashboards of individual sites. Some plugins only function in a multisite environment when they are network activated. WordPress Plugins that are Network Activated are also stored in the plugins directory.
* **Must-Use Plugins:** Plugins to be used by all sites on the entire network may also be installed in the mu-plugins directory as single files, or a file to include a subfolder. Any files within a folder will not be read. These files are not activated or deactivated; if they exist, they are used. These plugins are hidden entirely from per-site plugin lists.

Not all plugins in the repository will work in a multisite environment. Consult the plugin's repository page or contact the developer for information about whether a specific plugin will function in a multisite network.

If you would like single site administrators to be able to activate/deactivate site-specific plugins for their site, you need to enable the Plugins page for single site administrators from the Network Admin's Settings -> Network Settings menu ("Menu Settings"). Network Admins will always have access to the plugins of every site. Administrators of a single site will be able to activate and deactivate plugins that are not Network Activated, but will see the Network Activated plugins as "Network Active" with no options for deactivation or settings.

There are plugins that will assist with mass activating/deactivating plugins for single sites.

## Themes {#themes}

All themes are installed for the entire network. If you edit the code of one theme, you edit it for all sites using that theme. You can install the plugin [WP Add Custom CSS](https://wordpress.org/plugins/wp-add-custom-css/) to allow each site to tweak their own CSS without affecting anyone else. Also, individual sites may use the Theme Customizer, and their settings will be stored only in the tables for their site.

"Network Activating" a theme does not make it the active theme on each site, but merely makes it available to be activated on all individual sites.  To be available for activation in the dashboard of a single site, a theme must be either network activated or enabled in Network Admin – Edit Site – Themes tab. After a theme has been activated in a single site, it may be network deactivated without affecting the single site where it remains activated.

By default, WordPress assigns the most recent "Twenty ..." as the theme for all new sites. This can be customized by adding a line like `define('WP_DEFAULT_THEME', 'classic');` to your `wp-config.php` file, where ‘classic' is replaced with the folder name of your theme.

## Categories and Tags {#categories-and-tags}

Global terms (i.e. sharing tags and categories between sites on the network) is not available in WordPress 3.0. You can use plugin to incorporate global tags on the portal/front page of the site or on specific pages or sites within the network to increase navigation based upon micro-categorized content.

## Content Sharing Between Sites {#content-sharing-between-sites}

The sites of a network are separate sites that don't by default share content. Think of your network as a mini version of WordPress.com. There are several plugins which may help you share content between your sites, [like this one](https://wordpress.org/plugins/network-posts-extended/).

## Switching network types {#switching-network-types}

It's possible to switch between domain-based (sub-domain) and path-based (sub-directory) installations of Multisite. If you have had WordPress installed for longer than a month on a single site, and are attempting to activate that site into a network, you will be told to use **Sub-domain** sites. This is in order to ensure you don't have conflicts between pages (i.e. example.com/pagename ) and sites (i.e. example.com/sitename ). If you are confident you will not have this issue, then you can change this after you finish the initial setup.

In your `wp-config.php` file, you'll want to change the define call for `SUBDOMAIN_INSTALL`: For a domain-based network (sub-domain install)

```
define( 'SUBDOMAIN_INSTALL', true );
```

For a path-based network (sub-directory install)

```
define( 'SUBDOMAIN_INSTALL', false );
```

You'll also have to change your `.htaccess` to the new setup. You can go to Network Admin — Settings — Network Setup to find the new `.htaccess` rules, or see below.

Note that per the [Settings Requirements](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/#WordPress_Settings_Requirements) you cannot switch from **Sub-directory** to **Sub-domain** when running on `127.0.0.1` or `localhost`. This can potentially cause an endless loop of reauth=1 on your root site due to cookie handling.

## Apache Virtual Hosts and Mod Rewrite {#apache-virtual-hosts-and-mod-rewrite}

To enable mod_rewrite to work within an Apache Virtual host you may need to set some options on the DocumentRoot.

```
<VirtualHost *:80>
	...
	DocumentRoot /var/www/vhosts/wordpress
	<Directory /var/www/vhosts/wordpress>
		AllowOverride Fileinfo Options
	</Directory>
	...
</VirtualHost>
```

In some instances, you will need to add All to your AllowOverride for all htaccess rules to be honored.

## .htaccess and Mod Rewrite {#htaccess-and-mod-rewrite}

Unlike Single Site WordPress, which can work with "ugly" [Permalinks](https://wordpress.org/documentation/article/customize-permalinks/) and thus does not need Mod Rewrite, MultiSite _requires_ its use to format URLs for your subsites. This necessitates the use of an .htaccess file, the format of which will be slightly different if you're using SubFolders or SubDomains. The examples below are the standard .htaccess entries for WordPress SubFolders and SubDomains, when WordPress is installed in the root folder of your website. If you have WordPress in its own folder, you will need to change the value for RewriteBase appropriately.

As a reminder, these are **EXAMPLES** and work in most, but not all, installs.

**SubFolder Example**

WordPress 3.0 through 3.4+

```
# BEGIN WordPress
RewriteEngine On
RewriteBase /
RewriteRule ^index.php$ - [L]

# uploaded files
RewriteRule ^([_0-9a-zA-Z-]+/)?files/(.+) wp-includes/ms-files.php?file=$2 [L]

# add a trailing slash to /wp-admin
RewriteRule ^([_0-9a-zA-Z-]+/)?wp-admin$ $1wp-admin/ [R=301,L]

RewriteCond %{REQUEST_FILENAME} -f [OR]
RewriteCond %{REQUEST_FILENAME} -d
RewriteRule ^ - [L]
RewriteRule ^[_0-9a-zA-Z-]+/(wp-(content|admin|includes).*) $1 [L]
RewriteRule ^[_0-9a-zA-Z-]+/(.*\.php)$ $1 [L]
RewriteRule . index.php [L]
# END WordPress
```

WordPress 3.5+ _ONLY use this if you STARTED Multisite on 3.5. If you upgraded from 3.4 to 3.5, use the old one!_

```
# BEGIN WordPress
RewriteEngine On
RewriteBase /
RewriteRule ^index.php$ - [L]

# add a trailing slash to /wp-admin
RewriteRule ^([_0-9a-zA-Z-]+/)?wp-admin$ $1wp-admin/ [R=301,L]

RewriteCond %{REQUEST_FILENAME} -f [OR]
RewriteCond %{REQUEST_FILENAME} -d
RewriteRule ^ - [L]
RewriteRule ^([_0-9a-zA-Z-]+/)?(wp-(content|admin|includes).*) $2 [L]
RewriteRule ^([_0-9a-zA-Z-]+/)?(.*\.php)$ $2 [L]
RewriteRule . index.php [L]
# END WordPress
```

**SubDomain Example**

WordPress 3.0 through 3.4+

```
# BEGIN WordPress
RewriteEngine On
RewriteBase /
RewriteRule ^index.php$ - [L]

# uploaded files
RewriteRule ^files/(.+) wp-includes/ms-files.php?file=$1 [L]

RewriteCond %{REQUEST_FILENAME} -f [OR]
RewriteCond %{REQUEST_FILENAME} -d
RewriteRule ^ - [L]
RewriteRule . index.php [L]
# END WordPress
```

WordPress 3.5+

```
# BEGIN WordPress
RewriteEngine On
RewriteBase /
RewriteRule ^index.php$ - [L]

# add a trailing slash to /wp-admin
RewriteRule ^wp-admin$ wp-admin/ [R=301,L]

RewriteCond %{REQUEST_FILENAME} -f [OR]
RewriteCond %{REQUEST_FILENAME} -d
RewriteRule ^ - [L]
RewriteRule ^(wp-(content|admin|includes).*) $1 [L]
RewriteRule ^(.*\.php)$ wp/$1 [L]
RewriteRule . index.php [L]
# END WordPress
```

**Issues with old WPMU installs**

If you installed WordPress MU in subfolder/subdirectory (not in root folder on your server via ftp) and you have problem with image library, where thumbnails and images do not show, you may need to manually add in rewrite rules for your file directories as follows:

```
RewriteRule ^([_0-9a-zA-Z-]+/)?siteN/files/(.+) wp-content/blogs.dir/N/files/$2 [L]
```

Put those _below_ the normal call for uploaded files.

## Network Admin Link Location {#network-admin-link-location}

The Network Admin Link has moved with each major release of WordPress, as this is still a work in progress. Depending on which version of WordPress you are using, the link can be found in the following locations:

* 3.0 – A menu called _Super Admin_
* 3.1 – On the admin header by "Howdy, YOURNAME."
* 3.2 – On the admin header, as a drop-down under "Howdy, YOURNAME."
* 3.3+ – On the admin bar, as a drop-down under your "My Sites"

## Domain Mapping {#domain-mapping}

Before WordPress 4.5, domain mapping requires a domain mapping plugin. In WordPress 4.5+, domain mapping is a native feature in Multisites. Learn how to use this feature at [WordPress Multisite Domain Mapping](https://developer.wordpress.org/advanced-administration/multisite/domain-mapping/)

## Moving Multisite {#moving-multisite}

Moving Multisite is more complicated than moving a single install. Please read [Moving WordPress Multisite](https://developer.wordpress.org/advanced-administration/upgrade/migrating/#moving-wordpress-multisite) before continuing.

## Importing into a Network {#importing-into-a-network}

When you've created your WordPress Network for importing other sites, you need to look at the [Migrating Multiple Blogs into WordPress Multisite](https://wordpress.org/documentation/article/migrating-multiple-blogs-into-wordpress-multisite/) article.

## Changelog

- 2022-10-25: Original content from [Multisite Network Administration](https://wordpress.org/documentation/article/multisite-network-administration/).
