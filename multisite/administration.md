# Multisite Network Administration

Once you've [created a Multisite Network](https://developer.wordpress.org/advanced-administration/multisite/create-network/), there are some additional things you might need to
know about advanced administration, due to the additional complexity of a Multisite. Even if you’re familiar with WordPress, the structure and behavior of Multisite Network
Administration might seem confusing at first.

## User Access & Capabilities {#user-access-capabilities}

Users are created in common database tables, but they must be assigned a role to a site before they have access to it.

The capabilities of the site administrator role are also reduced in a WordPress Network. Site admins cannot install new themes or plugins and cannot edit the profiles of users on
their site. Only the Network Admin (aka Super Admin) can perform these tasks in a WordPress network.

## Permalinks in Subdirectory Installs {#permalinks-in-subdirectory-installs}

While permalinks will continue to work, the main site (i.e. the first one created) will have an extra entry of `blog`, making your URLs appear like
`domain.com/blog/YYYY/MM/POSTNAME`.

This is by design, in order to prevent collisions with subdirectory installs. Currently there is no easy way to change it, as doing so prevents WordPress from auto-detecting
collisions between your main site and any subsites.

Also note that the `blog` prefix is not used for static pages which will be accessible directly under the base address, e.g. `domain.com/PAGENAME`. If you try to create a static
page in the first site with the name of another existing site on the network, the page's permalink will get a suffix (e.g. `domain.com/PAGENAME-2`). If you create a new site with
the slug of an existing static page, the static page will not be reachable anymore. To prevent this, you can add the names of your static pages to the blacklist so that no site
with that name can be created.

## Uploaded File Path {#uploaded-file-path}

Your first site on a fresh install will put uploaded files in the traditional location of `/wp-content/uploads/`, however all _subsequent_ sites on your network will be in the
`/wp-content/uploads/sites/` folder, in their own subdirectory based on the site number, designated by the database. These files will be accessible via that URL.

## Plugins {#plugins}

Plugins now have additional flexibility, depending upon their implementation across the network. Plugins are installed on the network dashboard's plugin page, and can be activated
either per-site or for the entire network. Plugins can be designated as network-only by including `Network: true` in the plugin file header.

- **Site Activated Plugins:** When plugins are activated on individual sites, they are loaded only on those sites.
- **Network Activated Plugins:** Network admins may 'network activate' plugins in the Network Admin dashboard for plugins. Then they will become active in all sites and show as
  "Network Active" in plugin lists of individual sites. Some plugins only function in a Multisite environment when they are network activated.
- **Must-Use Plugins:** Plugins to be used by all sites on the entire network may also be installed in the mu-plugins directory as single files, or a file to include a
  subdirectory. Any files within a directory will not be read. These files are not activated or deactivated; if they exist, they are used. These plugins are hidden entirely from
  per-site plugin lists.

Not all plugins in the repository will work in a Multisite environment. Consult the plugin's repository page or contact the developer for information about whether a specific
plugin will function in a Multisite network.

If you would like single site administrators to be able to activate/deactivate site-specific plugins for their site, you need to enable the Plugins page for single site
administrators from the Network Admin's Settings -> Network Settings menu ("Menu Settings"). Network Admins will always have access to the plugins of every site. Administrators of
a single site will be able to activate and deactivate plugins that are not Network Activated, but will see the Network Activated plugins as "Network Active" with no options for
deactivation or settings.

There are plugins that will assist with mass activating/deactivating plugins for single sites.

## Themes {#themes}

All themes are installed for the entire network. If you edit the code of one theme, you edit it for all sites using that theme.

Style changes made in the Site Editor affect only individual sites. For classic themes, individual sites may use the Customizer to set Additional CSS or make other style changes.
The settings are stored only in the tables for that site.

"Network Activating" a theme does not make it the active theme on each site, but merely makes it available to be activated on all individual sites. To be available for activation
in the dashboard of a single site, a theme must be either network activated or enabled in Network Admin – Edit Site – Themes tab. After a theme has been activated in a single site,
it may be network deactivated without affecting the single site where it remains activated.

By default, WordPress assigns the most recent "Twenty ..." as the theme for all new sites. This can be customized by adding a line like `define('WP_DEFAULT_THEME', 'classic');` to
your `wp-config.php` file, where ‘classic' is replaced with the folder name of your theme.

## Content Sharing Between Sites {#content-sharing-between-sites}

The sites of a network are separate sites that don't by default share content. Think of your network as a mini version of WordPress.com. There are several plugins which may help
you share content between your sites.

## Switching network types {#switching-network-types}

Switching an existing network between subdomain and subdirectory configurations is an advanced operation. Changing `SUBDOMAIN_INSTALL` and the rewrite rules alone does not migrate
existing site addresses or configure DNS, virtual hosts, TLS certificates, redirects, and cookies. Back up the network and test the change in a staging environment first.

As part of the change, set `SUBDOMAIN_INSTALL` in `wp-config.php`. For a subdomain network, use:

```php
define( 'SUBDOMAIN_INSTALL', true );
```

For a subdirectory network, use:

```php
define( 'SUBDOMAIN_INSTALL', false );
```

You must also update the stored site addresses and configure the web server for the new network type. See [URL Rewrites](#url-rewrites) for the appropriate web-server guidance.

During network creation, WordPress normally requires a subdomain configuration when the main site has published content older than one month. This restriction helps avoid
collisions between existing page paths and new site paths.

Per the [Settings Requirements](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/#wordpress-settings-requirements), a subdomain network cannot use
an IP address or `localhost` as its server address.

## URL Rewrites {#url-rewrites}

Multisite requires URL rewriting to route requests for sites in the network. The required configuration depends on the web server. WordPress can generate Apache and IIS rules, but
it cannot generate Nginx configuration.

Configure the appropriate rules for your web server:

- [Apache HTTP Server rules](https://developer.wordpress.org/advanced-administration/server/web-server/httpd/#multisite)
- [Nginx Multisite rules](https://developer.wordpress.org/advanced-administration/server/web-server/nginx/#wordpress-multisite)

## Domain Mapping {#domain-mapping}

Before WordPress 4.5, domain mapping requires a domain mapping plugin. In WordPress 4.5+, domain mapping is a native Multisite feature. Learn how to use this feature at
[WordPress Multisite Domain Mapping](https://developer.wordpress.org/advanced-administration/multisite/domain-mapping/)

## Moving Multisite {#moving-multisite}

Moving Multisite is more complicated than moving a single install. Please read
[Moving WordPress Multisite](https://developer.wordpress.org/advanced-administration/upgrade/migrating/#moving-wordpress-multisite) before continuing.

## Importing into a Network {#importing-into-a-network}

When you've created your WordPress Network for importing other sites, you need to look at the
[Migrating Multiple Blogs into WordPress Multisite](https://wordpress.org/documentation/article/migrating-multiple-blogs-into-wordpress-multisite/) article.
