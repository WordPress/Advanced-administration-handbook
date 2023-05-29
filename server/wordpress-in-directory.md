# Giving WordPress Its Own Directory

Many people want WordPress to power their website's root (e.g. https://example.com) but they don't want all of the WordPress files cluttering up their root directory. WordPress allows you to install it into a subdirectory, but have your website served from the website root.

As of [Version 3.5](https://wordpress.org/documentation/wordpress-version/version-3-5/), Multisite users may use all of the functionality listed below. If you are running a version of WordPress older than 3.5, please update before installing a Multisite WordPress install on a subdirectory.

**Note to theme/plugin developers:** this will not separate your code from WordPress. Themes and plugins will still reside under `wp-content` folder.

## Moving a Root install to its own directory

Let's say you've installed WordPress at `example.com`. Now you have two different methods to move wordpress installations into subdirectory:

1. Without change of SITE-URL (remains `example.com`)
2. With change in SITE-URL (it will redirect to `example.com/subdirectory`)

## Method I (Without URL change)

1. After Installing the wordpress in root folder, move EVERYTHING from root folder into subdirectory.
2. Create a `.htaccess` file in root folder, and put this content inside (just change `example.com` and `my_subdir`):

```
<IfModule mod_rewrite.c>
RewriteEngine on
RewriteCond %{HTTP_HOST} ^(www.)?example.com$
RewriteCond %{REQUEST_URI} !^/my_subdir/
RewriteCond %{REQUEST_FILENAME} !-f
RewriteCond %{REQUEST_FILENAME} !-d
RewriteRule ^(.*)$ /my_subdir/$1
RewriteCond %{HTTP_HOST} ^(www.)?example.com$
RewriteRule ^(/)?$ my_subdir/index.php \[L\] 
</IfModule>
```

That's all 🙂

## Method II (With URL change)

### Moving process

_(p.s. If you've already installed WP in subdirectory, some steps might be already done automatically)._

1. Create the new location for the core WordPress files to be stored (we will use `/wordpress` in our examples). (On linux, use `mkdir wordpress` from your `www` directory. You'll probably want to use `chown apache:apache` on the `wordpress` directory you created.)
2. Go to the [General](https://wordpress.org/documentation/article/administration-screens/#settings-configuration-settings) Screen.
3. In **WordPress address (URL):** set the address of your main WordPress core files. Example: http://example.com/wordpress
4. In **Site address (URL):** set root directory's URL. Example: http://example.com
5. Click **Save Changes**. (Do not worry about the errors that happen now! Continue reading)
6. Now move your WordPress core files (from root directory) to the subdirectory.
7. Copy (NOT MOVE!) the `index.php` and `.htaccess` files from the WordPress directory into the root directory of your site (Blog address). The `.htaccess` file is invisible, so you may have to set your FTP client to [show hidden files](https://developer.wordpress.org/advanced-administration/server/file-permissions/#Unhide_the_hidden_files). If you are not using [pretty permalinks](https://wordpress.org/documentation/article/using-permalinks/#using-pretty-permalinks), then you may not have a .`htaccess` file. _**If you are running WordPress on a Windows (IIS) server** and are using pretty permalinks, you'll have a `web.config` rather than a `.htaccess` file in your WordPress directory. For the `index.php` file the instructions remain the same, copy (don't move) the index.php file to your root directory. The `web.config` file, must be treated differently than the `.htaccess` file so you must MOVE (DON'T COPY) the `web.config` file to your root directory._
8. Open your root directory's `index.php` file in a [text editor](https://wordpress.org/documentation/article/glossary#text-editor)
9. Change the following and save the file. Change the line that says:`require dirname( __FILE__ ) . '/wp-blog-header.php';`to the following, using your directory name for the WordPress core files: `require dirname( __FILE__ ) . '/wordpress/wp-blog-header.php';`
10. Login to the new location. It might now be http://example.com/wordpress/wp-admin/
11. If you have set up [Permalinks](https://wordpress.org/documentation/article/using-permalinks/), go to the [Permalinks Screen](https://wordpress.org/documentation/article/administration-screens/#permalinks) and update your Permalink structure. WordPress will automatically update your `.htaccess` file if it has the appropriate file permissions. If WordPress can't write to your `.htaccess` file, it will display the new rewrite rules to you, which you should manually copy into your `.htaccess` file (in the same directory as the main `index.php` file.)

### .htaccess modification

In some cases, some people like to install separate versions in a subdirectory (such as `/2010`, `/2011`, `/latest` and etc..), and want that website (by default) used the latest version, then Install WordPress in a subdirectory, such as `/my_subdir` and in your root folder's .htaccess file add the following (just change the words as you need):

```
RewriteEngine On
RewriteCond %{HTTP_HOST} ^(www.)?example.com$
RewriteRule ^(/)?$ my_subdir\[L\]
```

Now when users to go your root domain (`example.com`), it will automatically redirect to the subdirectory you specified.

Note: This code comes from Site 5's post here: [How to Redirect Your Domain to a Subfolder Using .htaccess](https://qa.site5.com/advanced/how-to-redirect-your-domain-to-a-subfolder-using-htaccess/).

## Moving Specific WordPress Folders

The following links explains how to change specific directories within WordPress:

* [Moving wp-content folder](https://wordpress.org/documentation/article/editing-wp-config-php/#moving-wp-content-folder)
* [Moving Plugin Folder](https://wordpress.org/documentation/article/editing-wp-config-php/#moving-plugin-folder)
* [Moving Themes Folder](https://wordpress.org/documentation/article/editing-wp-config-php/#moving-themes-folder)
* [Moving Uploads Folder](https://wordpress.org/documentation/article/editing-wp-config-php/#moving-uploads-folder)

## See also

* [Using Caddy to give WordPress its own directory](https://caddy.community/t/using-caddy-to-give-wordpress-its-own-directory/13185)

## Changelog

- 2022-09-11: Original content from [Giving WordPress Its Own Directory](https://wordpress.org/documentation/article/giving-wordpress-its-own-directory/).
