# Importing Content

Using the WordPress Import tool, you can import content into your site from another WordPress site, or from another publishing system.

You can find many of the importers described here under [Tools](https://wordpress.org/documentation/article/administration-screens/#tools-managing-your-blog) -> [Import](https://wordpress.org/documentation/article/tools-import-screen/) in the left nav of the WordPress [Administration Screen](https://wordpress.org/documentation/article/administration-screens/).

You can import content from publishing systems beyond those listed on the Administration Screen. Procedures differ for each system, so use the procedures and plugins listed below as necessary. If you're new to WordPress, review the [WordPress Features](https://wordpress.org/about/features/) and [Working with WordPress](https://codex.wordpress.org/Working%20with%20WordPress) pages to get started.

If you run into problems, search the [WordPress Support Forum](https://wordpress.org/support/forums/) for a solution, or try the [FAQ](https://wordpress.org/documentation/article/faq-work-with-wordpress/).

## Before Importing

If the file you're importing is too large, your server may run out of memory when you import it. If this happens, you'll see an error like "Fatal error: Allowed memory size of 8388608 bytes exhausted."

If you have sufficient permissions on the server, you can edit the `php.ini` file to increase the available memory. Alternatively, you could ask your hosting provider to do this. Otherwise, you can edit your import file and save it as several smaller files, then import each one.

If your import process fails, it still may create some content. When you resolve the error and try again, you may create duplicate data. Review your site after a failed import and remove records as necessary to avoid this.

## b2evolution

There are two methods of importing b2evolution content into WordPress.

* **Movable Type Export Format**: You can re-skin a b2evolution blog so that when its source is viewed it appears to be in the [Movable Type export format](https://movabletype.org/documentation/appendices/import-export-format.html). You can save the export and import it as Movable Type data. See Movable Type and TypePad.
* **BIMP Importer script**: You can use the [BIMP Importer script](https://wittyfinch.com/bimp-importer-migrate-b2evolution-to-wordpress/) to import b2evolution blogs, categories, posts, comments, files and users into your WordPress installation (v3 and higher). Note that this requires payment.

## Blogger

You can import posts, comments, categories and authors from Blogger. WordPress includes an import tool designed specifically for importing content from Blogger.

1. Export your Blogger contents as XML.
2. In your WordPress site, select Tools -> Import on the left nav of the admin screen.
3. Under "Blogger", if you haven't already installed the Blogger importer, click "Install Now".
4. Click the "Run Importer" link.
5. Click "Choose File" and navigate to your Blogger XML file.
6. Click "Upload file and import".

## Blogroll

WordPress includes an import tool designed specifically for importing content from Blogroll.

1. In your WordPress site, select Tools -> Import on the left nav of the admin screen.
2. Under "Blogroll", if you haven't already installed the importer, click "Install Now".
3. Click the "Run Importer" link.
4. Click "Choose File" and navigate to your Blogroll OPML file.
5. Click "Upload file and import".

## Drupal

Many resources are available to help you migrate content from Drupal to WordPress. A few are highlighted here, and you're likely to find many others by searching the web.

* [FG Drupal to WordPress](https://wordpress.org/plugins/fg-drupal-to-wp/). This is compatible with Drupal 4, 5, 6, 7, 8 and 9.
* [Drupal2WordPress Plugin](https://github.com/jpSimkins/Drupal2WordPress-Plugin). Use this plugin to import terms, content, media, comments and users. Any external images included in your Drupal site can be fetched and added to the media library, and added to your pages and posts.
* [Drupal to WordPress migration SQL queries explained](https://anothercoffee.net/drupal-to-wordpress-migration-sql-queries-explained/) includes workarounds for some migration issues such as duplicate terms, terms exceeding maximum character length and duplicate URL aliases.

## XML and CSV

Here are some resources that can help guide you in importing XML or CSV content into WordPress.

* The [WP All Import](https://wordpress.org/plugins/wp-all-import/) plugin can import any XML or CSV file. It integrates with the [WP All Export](https://wordpress.org/plugins/wp-all-export/) plugin.

## HTML

WordPress includes an import tool designed specifically for importing content from static HTML pages.

1. In your WordPress site, select Tools -> Import on the left nav of the admin screen.
2. Under "HTML", click the "Run Importer" link.
3. Click "Choose File" and navigate to your HTML file.
4. Click "Upload file and import".

## Joomla

For Joomla you can use [FG Joomla to WordPress](https://wordpress.org/plugins/fg-joomla-to-wordpress/). This plugin has been tested with Joomla versions 1.5 through 4.0 on huge databases. It is compatible with multisite installations.

## LiveJournal

WordPress includes an import tool designed specifically for importing content from LiveJournal.

1. In your WordPress site, select Tools -> Import on the left nav of the admin screen.
2. Under "LiveJournal", if you haven't already installed the LiveJournal importer, click "Install Now".
3. Click the "Run Importer" link.
4. Enter your LiveJournal username and password, and click "Connect to LiveJournal and Import".

## Live Space

See [Live Space Mover](https://b2.broom9.com/?page_id=519) for an article explaining how to use a python script for importing blog entries from live space to WordPress.

## Magento

The [FG Magento to WooCommerce](https://wordpress.org/plugins/fg-magento-to-woocommerce/) plugin migrates your Magento products and CMS pages to WooCommerce.

## Mambo

You can use the plugin [FG Joomla to WordPress](https://wordpress.org/plugins/fg-joomla-to-wordpress/). This WordPress plugin works with Mambo 4.5 and 4.6.

## Movable Type and TypePad

WordPress includes an import tool designed specifically for importing content from Movable Type and TypePad.

1. In your WordPress site, select Tools -> Import on the left nav of the admin screen.
2. Under "Movable Type and TypePad", if you haven't already installed the importer, click "Install Now".
3. Click the "Run Importer" link.
4. Click "Choose File" and navigate to your export file.
5. Click "Upload file and import".

These articles provide more information on this process:

* [Importing from Movable Type to WordPress](https://codex.wordpress.org/Importing%20from%20Movable%20Type%20to%20WordPress)
* [Importing and Exporting Content](https://www.movabletype.org/documentation/administrator/maintenance/import-export.html)
* [Notes on a Massive WordPress Migration](https://blog.birdhouse.org/2008/02/07/notes-on-a-massive-wordpress-migration/)

## Nucleus CMS

Here are some resources that can help guide you in migrating content from Nucleus CMS to WordPress.

* [A Guide to Importing From Nucleus CMS](https://mamchenkov.net/wordpress/2005/04/26/nucleus2wordpress/)
* [Script for Importing From Nucleus CMS](http://james.onegoodcookie.com/pub/import-nucleus.phps)
* [Nucleus to WordPress](https://github.com/AbdussamadA/nucleus-importer)

## PrestaShop

[FG PrestaShop to WooCommerce](https://wordpress.org/plugins/fg-prestashop-to-woocommerce/). This WordPress plugin is compatible with PrestaShop versions 1.0 to 1.7.

## Roller

See [Importing From Roller](https://codex.wordpress.org/Importing%20From%20Roller).

See also [Migrating a Roller Blog to WordPress](https://nullpointer.debashish.com/migrating-a-roller-blog-to-wordpress).

## RSS

WordPress includes an import tool designed specifically for importing content from RSS.

1. In your WordPress site, select Tools -> Import on the left nav of the admin screen.
2. Under "RSS", if you haven't already installed the importer, click "Install Now".
3. Click the "Run Importer" link.
4. Click "Choose File" and navigate to your XML file.
5. Click "Upload file and import".

## Serendipity

* [Serendipity to WordPress – Post Import](https://obviate.io/2010/06/11/serendipity-to-wordpress-post-import/).

## SPIP

The plugin [FG SPIP to WordPress](https://wordpress.org/plugins/fg-spip-to-wp/) migrates categories, articles, news and images from SPIP to WordPress. It has been tested with SPIP versions 1.8, 1.9, 2.0, 3.0, 3.1 and 3.2. It is compatible with multisite installations.

## Sunlog

1. Open [phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/) to see the database of your Sunlog install. You only need two tables, "blogname_entries" and "blogname_comments".
2. Use phpMyAdmin to export both tables as XML files.
3. Install the [WP All Import](https://wordpress.org/plugins/wp-all-import/) plugin to your WordPress site.
4. Create the following field mappings:
  * `headline=title`
  * `content=entry+more`
  * `date=timestamp` in Unix format
  * `categories="cat,"` with each value separated by a semicolon.

## Textpattern

* [Fix Textpattern import](https://wordpress.org/support/topic/fix-textpattern-import/)
* [Textpattern to WordPress exporter](https://github.com/drewm/textpattern-to-wordpress)

## Tumblr

WordPress includes an import tool designed specifically for importing content from Tumblr.

1. In your WordPress site, select Tools -> Import on the left nav of the admin screen.
2. Under "Tumblr", if you haven't already installed the importer, click "Install Now".
3. Click the "Run Importer" link.
4. Click "Choose File" and navigate to your export file.
5. Click "Upload file and import".
6. Create an app on Tumblr that provides a connection point between your blog and Tumblr's servers.
7. Copy and paste the "OAuth Consumer Key" and "Secret Key".
8. Click "Connect to Tumblr".

## Twitter

There are several plugins to import your tweets into WordPress, such as [Get Your Twitter Timeline into WordPress](https://blog.birdhouse.org/2008/08/17/get-your-twitter-timeline-into-wordpress/).

## TypePad

See [Movable Type and TypePad](#movable-type-and-typepad).

## WooCommerce products (CSV)

If you've installed the WooCommerce plugin, this importer will already be installed. Click "Run Importer" to upload a CSV file.

## WooCommerce tax rates (CSV)

If you've installed the WooCommerce plugin, this importer will already be installed. Click "Run Importer" to upload a CSV file.

## WordPress

WordPress includes an import tool designed specifically for importing content from another WordPress blog.

1. In your WordPress site, select Tools -> Import on the left nav of the admin screen.
2. Under "WordPress", if you haven't already installed the importer, click "Install Now".
3. Click the "Run Importer" link.
4. Click "Choose File" and navigate to the WXR file exported from your source.
5. Click "Upload file and import".

You will first be asked to map the authors in this export file to users on the blog. For each author, you may choose to map to an existing user on the blog or to create a new user. WordPress will then import each of the posts, comments and categories contained in the uploaded file into your blog. In addition, you can import attachments by checking the "Download and import file attachments" option.

## Xanga

[xanga.r](https://www.timwylie.com/xword.html) is a program that parses xanga pages to get the post and comments. Then it can output them in the WordPress rss 2.0 xml format for WordPress to import.

## Changelog

- 2023-04-25: Added content from [Importing Content](https://wordpress.org/documentation/article/importing-content/).
