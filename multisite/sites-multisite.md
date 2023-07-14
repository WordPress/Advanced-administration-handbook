# Migrate WordPress sites into WordPress Multisite

This tutorial explains how to migrate multiples WordPress installs to a WordPress multisite install. You can migrate sites that use their own domain names, as well as sites that use a subdomain on your primary domain.

This tutorial assumes that you are hosting WordPress on a server using cPanel. If you are using another solution to manage your server, you'll have to adapt these instructions.

## Steps to follow {#steps-to-follow}

### Backup your site {#backup-your-site}

Generate a full site backup in cPanel. It might also help to copy all the files on the server via FTP, so that you can easily access the files for plugins and themes, which you'll need in a later step.

### Export from your existing WordPress installs {#export-from-your-existing-wordpress-installs}

In each of your existing WordPress installations, go Tools > Export in WordPress. Download the WXR files that contain all your posts and pages for each site. See the instructions on the [Tools Export Screen](https://wordpress.org/documentation/article/tools-export-screen/).

Make sure that your export file actually has all the posts and pages. You can verify this by looking at the last entry of the exported file using a text editor. The last entry should be the most recent post.

Some plugins can conflict with the export process, generating an empty file, or a partially complete file. To be on the safe side, you should probably disable all plugins before doing the exports.

It's also a good idea to first delete all quarantined spam comments as these will also be exported, making the file unnecessarily large.

Note: widget configuration and blog/plugin settings are NOT exported in this method. If you are migrating within a single hosting account, make note of those settings at this stage, because when you delete the old domain, they will disappear.

### Install WordPress {#install-wordpress}

Install WordPress. Follow the instructions for [Installing WordPress](https://developer.wordpress.org/advanced-administration/before-install/howto-install/).

### Activate multisite {#activate-multisite}

Activate multi-site in your WordPress install. This involves editing wp-config.php a couple of times. You need to use the subdomain, not the subdirectory, option. See the instructions on how to [Create A Network](https://developer.wordpress.org/advanced-administration/multisite/create-network/).

### Create blogs for each site you want to import {#create-blogs-for-each-site-you-want-to-import}

Create blogs for each of the sites you want to host at separate domains. For example, `importedblogdotorg.mydomain.com`.

Note: choose the name carefully, because changing it causes admin redirection issues. This is particularly important if you are migrating a site within the same hosting account.

### Import WXR files for each blog {#import-wxr-files-for-each-blog}

Go to the backend of each blog, and import the exported WXR file for each blog. Map the authors to the proper users, or create new ones. Be sure to check the box that will pull in photos and other attachments. See the instructions on Tools Import SubPanel.

Note: if you choose to import images from the source site into the target site, make sure they have been uploaded into the right place and are displayed correctly in the respective post or page.

### Copy theme and plugin files {#copy-theme-and-plugin-files}

Before you start, check that your plugins will work in the network installation. If a plugin is not supported, do not install it. Find suitable alternatives for it by searching for the plugin's function with "multisite" or even "mu", as in "social bookmarking plugin wordpress multisite".

Copy the theme and plugin files from your old WP installs to their respective directories in the new wp-content. You can activate themes for the network, or you can go to Superadmin > Sites, then click edit on the site you want, and enable a given theme for just that site.

Note: if you are using a child theme, copy both parent and child themes to the new site.

### Edit WordPress configuration settings for each site {#edit-wordpress-configuration-settings-for-each-site}

Edit the configuration settings, widget, etc. for each site. By the end of this step, each site should look exactly as it did before, only with the URL subdomain.example.com or example.com/subsite rather than its correct, final URL.

## Potential problems {#potential-problems}

### Limitations of PHP configuration {#limitations-of-php-configuration}

You may run into trouble with the PHP configuration on your host. There are two potential problems. One is that PHP's `max_upload_size` will be too small for the WXR file. The other problem is that the PHP memory limit might be too small for importing all the posts.

There are a couple ways to solve it. One is to ask your hosting provider to up the limits, even temporarily. The other is to put a php.ini file in your `/wp-admin/` and `/wp-includes` directories that ups the limits for you (php.ini files are not recursive, so it has to be in those directories). Something like a 10 MB upload limit and a 128 MB memory limit should work, but check with your hosting provider first so that you don't violate the terms of your agreement.

Search the [WordPress forum support](https://wordpress.org/documentation/forums/) for help with PHP configuration problems.

### Converting add-on domains to parked domains {#converting-add-on-domains-to-parked-domains}

Deleting add-on domains in cPanel and replacing them with parked domains will also delete any domain forwarders and e-mail forwarders associated with those domains. Be aware of this, so that you can restore those forwarders once you've made the switch.

### Limitations of importing users {#limitations-of-importing-users}

As there is the above way to import the content into an instance of the Multisite-blog, you are running into massive troubles, when it gets to import multiple users. Users are generated during the import, but you won't get any roles or additional information into the new blog.

### Losing settings {#losing-settings}

If the old site is no longer available and you find you have forgotten to copy some setting or you want to make sure you have configured everything correctly, run a google search for your site and then click to view the cached version. This option is available only until your new site has been crawled, so you'd better be quick.

Another option might be the [Internet Archive Wayback Machine](https://archive.org/web/). They may have a copy of the site (or some part of it) archived.

## Changelog

- 2023-01-20: Original content from [Migrating multiple blogs into WordPress multisite](https://wordpress.org/documentation/article/migrating-multiple-blogs-into-wordpress-multisite/)
