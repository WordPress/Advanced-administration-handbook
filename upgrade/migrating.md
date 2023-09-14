# Migrating WordPress

## Changing The Site URL

On the `Settings -> General` screen in a single site installation of WordPress, there are two fields named "WordPress Address (URL)" and "Site Address (URL)". They are important settings, since they control where WordPress is located. These settings control the display of the URL in the admin section of your page, as well as the front end, and are used throughout the WordPress code.

- The "Site Address (URL)" setting is the address you want people to type in their browser to reach your WordPress blog.
- The "WordPress Address (URL)" setting is the address where your WordPress core files reside.

**Note:** Both settings should include the http:// part and should not have a slash `/` at the end.

Every once in a while, somebody finds a need to manually change (or fix) these settings. Usually this happens when they change one or both and discover that their site no longer works properly. This can leave the user with no easily discoverable way to correct the problem. This article tells you how to change these settings directly.

Additional information is presented here for the case where you are moving WordPress from one site to another, as this will also require changing the site URL. You should not attempt to use this additional information if you're only attempting to correct a "broken" site.

**Alert!** These directions are for single installs of WordPress only. If you are using WordPress MultiSite, you will need to manually edit your database.

There are four easy methods to change the Site URL manually. Any of these methods will work and perform much the same function.

#### Edit wp-config.php

It is possible to set the site URL manually in the `wp-config.php` file.

Add these two lines to your `wp-config.php`, where "example.com" is the correct location of your site.

```
define( 'WP_HOME', 'http://example.com' );
define( 'WP_SITEURL', 'http://example.com' );
```

This is not necessarily the best fix, it's just hard-coding the values into the site itself. You won't be able to edit them on the General settings page anymore when using this method.

#### Edit functions.php

If you have access to the site via FTP, then this method will help you quickly get a site back up and running, if you changed those values incorrectly.

1. FTP to the site, and get a copy of the active theme's functions.php file. You're going to edit it in a simple text editor and upload it back to the site.
2. Add these two lines to the file, immediately after the initial `<?php` line:

```
update_option( 'siteurl', 'http://example.com' );
update_option( 'home', 'http://example.com' );
``` 
Use your own URL instead of `example.com`, obviously.

3. Upload the file back to your site, in the same location. FileZilla offers a handy "edit file" function to do all of the above rapidly; if you can use that, do so.
4. Load the login or admin page a couple of times. The site should come back up.

**Important!** Do not leave this code in the `functions.php` file. Remove them after the site is up and running again.

Note: If your theme doesn't have a `functions.php` file create a new one with a text editor. Add the `<?php` tag and the two lines using your own URL instead of `example.com`:

```	
<?php
update_option( 'siteurl', 'http://example.com' );
update_option( 'home', 'http://example.com' );
```

Upload this file to your theme directory. Remove the lines or the remove the file after the site is up and running again.

#### LAN-based site to externally accessible site

Here are some additional details that step you through transferring a LAN-based WordPress site into an externally accessible site, as well as enabling editing the wordpress site from inside the LAN.

Two important keys are router/firewall modifications and the "wait 10+ minutes" after making the changes at the end.

1. Using ssh to log into your server (nano is a server preinstalled text editor):

`$ nano /var/www/books/wp-content/themes/twentyeleven/functions.php`

2. Add lines just after `<?php`

```
update_option( 'siteurl', 'http://example.com:port/yourblog');
update_option( 'home', 'http://example.com:port/yourblog');
```

3. Refresh your web browser using your external site URL:

http://example.com:port/yourblog
`$ nano /var/www/books/wp-content/themes/twentyeleven/functions.php`

4. Remove those lines you just added (or comment them out)

5. Access your router, these steps are for pfSense, other routers should have similar settings to look for/watch out for)

6. Add to firewall/nat table a line like this

`wan/tcp/port/LAN.server.IP/80`

7. Add to firewall/rules table a line like this:

`tcp/*/port/LAN.server.IP/port/*`

8. Uncheck the box at System/advanced/network address translation/Disable NAT Reflection

`"Disables the automatic creation of NAT redirect rules for access to your public IP addresses from within your internal networks. Note: Reflection only works on port forward type items and does not work for large ranges > 500 ports."`

9. Then go do something for ten minutes and when you get back see if the external url http://example.com:port/yourblog from a LAN browser brings the page up correctly. 

#### Relocate method

WordPress supports an automatic relocation method intended to be a quick assist to getting a site working when relocating a site from one server to another.

#### Code function

When RELOCATE has been defined as true in `wp-config.php` (see next chapter), the following code in `wp-login.php` will take action:

```
if ( defined( 'RELOCATE' ) AND RELOCATE ) {    
	// Move flag is set
	if ( isset( $_SERVER['PATH_INFO'] ) AND ($_SERVER['PATH_INFO'] != $_SERVER['PHP_SELF']) ) 
		$_SERVER['PHP_SELF'] = str_replace( $_SERVER['PATH_INFO'], "", $_SERVER['PHP_SELF'] );
	$url = dirname( set_url_scheme( 'http://'. $_SERVER['HTTP_HOST'] . $_SERVER['PHP_SELF'] ) );
	if ( $url != get_option( 'siteurl' ) )
		update_option( 'siteurl', $url );
}
```

**Steps**

1. Edit the `wp-config.php` file.
2. After the "define" statements (just before the comment line that says "That's all, stop editing!"), insert a new line, and type: `define('RELOCATE',true);`
3. Save your `wp-config.php` file.
4. Open a web browser and manually point it to `wp-login.php` on the new server. For example, if your new site is at http://www.example.com, then type http://www.example.com/wp-login.php into your browser's address bar.
5. Login as per normal.
6. Look in your web browser's address bar to verify that you have, indeed, logged in to the correct server. If this is the case, then in the Admin back-end, navigate to `Settings > General` and verify that both the address settings are correct. Remember to Save Changes.
7. Once this has been fixed, edit `wp-config.php` and either completely remove the line that you added (delete the whole line), comment it out (with `//`) or change the true value to false if you think it's likely you will be relocating again.

**Note:** When the `RELOCATE` flag is set to true, the Site URL will be automatically updated to whatever path you are using to access the login screen. This will get the admin section up and running on the new URL, but it will not correct any other part of the setup. You'll still need to alter those manually. 

**Important!** Leaving the RELOCATE constant in your `wp-config.php` file is insecure, as it allows an attacker to change your site URL to anything they want in some configurations. Always remove the RELOCATE line from `wp-config.php` after you're done. 

#### Changing the URL directly in the database

If you know how to access phpMyAdmin on your host, then you can edit these values directly to get your site up and running again.

1. [Backup your database](https://developer.wordpress.org/advanced-administration/security/backup/database/) and save the copy off-site.
2. Login to [phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/).
3. Click the link to your **Databases**.
4. A list of your databases will appear. Choose the one that is your WordPress database.
5. All the tables in your database will appear on the screen.
6. From the list, look for `wp_options`. **Note:** The table prefix of `wp_` may be different if you changed it when installing.
7. Click on the small icon indicated as **Browse**.
8. A screen will open with a list of the fields within the wp_options table.
9. Under the field option_name, scroll down and look for `siteurl`.
10. Click the **Edit Field** icon which usually is found at the far left at the beginning of the row.
11. The **Edit Field** window will appear.
12. In the input box for option_value, carefully change the URL information to the new address.
13. Verify this is correct and click **Go** to save the information.
14. You should be returned to your `wp_options` table.
15. Look for the home field in the table and click **Edit Field**. **Note:** There are several pages of tables inside `wp_options`. Look for the > symbol to page through them.
16. In the input box for option_value, carefully change the URL information to the new address.
17. Verify this is correct and click **Go** to save the information.

### Moving Sites

When moving sites from one location to another, it is sometimes necessary to manually modify data in the database to make the new site URL information to be recognized properly. Many tools exist to assist with this, and those should generally be used instead of manual modifications.

This is presented here as information only. This data may not be complete or accurate.

You should read the [Moving WordPress](https://developer.wordpress.org/advanced-administration/upgrade/migrating/) article first, if attempting to move WordPress from one system to another. 

#### Altering Table Prefixes

Like many WordPress administrators, you may be running several WordPress installations off of one database using various `wp-config.php` hacks. Many of these hacks involve dynamically setting table prefixes, and if you do end up altering your table prefix, you must update several entries within the `prefix_usermeta` table as well.

As in the above section, remember that SQL changes are permanent and so you should back up your database first: 

If you are changing table prefixes for a site, then remember to alter the table prefix in the usermeta tables as well. This will allow the new site to properly recognize user permissions from the old site. 
```
UPDATE `newprefix_usermeta` SET `meta_key` = REPLACE( `meta_key` , 'oldprefix_', 'newprefix_' );
```

#### Changing Template Files

In your WordPress Theme, open each template file and search for any manually entered references to your old domain name and replace it with the new one. Look for specific hand coded links you may have entered on the various template files such as the `sidebar.php` and `footer.php`. WordPress uses a template tag called `bloginfo()` to automatically generate your site address from information entered in your Administration > Settings > General panel. The tag in your template files will not have to be modified. 

#### Changing the Config file

You will need to update your WordPress configuration file if your database has moved or changed in certain ways.

1. You will only need to modify the config file if:
  - your database has moved to another server and is not running on your localhost
  - you have renamed your database
  - you have changed the database user name
2. Make a backup copy of your `wp-config.php` file.
3. Open the `wp-config.php` file in a [text editor](https://wordpress.org/documentation/article/wordpress-glossary/#Text_editor).
4. Review its contents. In particular, you are looking for the [database host entry](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/#set-database-host).
5. Save the file.
6. At this point, your WordPress blog should be working.  

#### Verify the Profile

1. In your [Administration](https://wordpress.org/documentation/article/administration-screens/) > [Settings](https://wordpress.org/documentation/article/administration-screens/#general) > [General](https://wordpress.org/documentation/article/settings-general-screen/) panel, you will verify that the changes you made in Changing the URL above, are correct.
2. Verify that the reference in your **WordPress Address (URL)** contains the new address.
3. Verify that the reference in your **Site Address (URL)** contains the new address.
4. If you have made changes, click **Save Changes**.

#### Changing the .htaccess file

After changing the information in your Administration > Settings > General panel, you will need to update your .htaccess file if you are using Permalinks or any rewrites or redirects. 

1. **Make a backup copy** of your `.htaccess` file. This is not a recommendation but a requirement.
2. Open the `.htaccess` file in a [text editor](https://wordpress.org/documentation/article/glossary/#text-editor).
3. Review its contents, looking for any custom rewrites or redirects you entered. **Copy** these to another text file for safe keeping.
4. Close the file.
5. Follow the instructions on the Permalinks SubPanel for updating your Permalinks to the `.htaccess` file.
6. Open the new `.htaccess` file and check to see if your custom rewrites and redirects are still there. If not, copy them from the saved file and paste them into the new `.htaccess` file.
7. Make any changes necessary in those custom rewrites and redirects to reflect the new site address.
8. Save the file.
9. Test those redirects to ensure they are working.

If you make a mistake, you can [Restore Your Database](https://developer.wordpress.org/advanced-administration/security/backup/) from your backup and try this again. So make sure it is right the first time.

#### Additional items of note

There are other things you may wish to change in order to correct URLs when moving sites.

1. Images link: image links are stored in "post_content" in the `wp_posts` table. You can use the similar code above to update image links.
2. wp_options: Besides the "siteurl" and "home" items mentioned above, there are other option_value which also need revision, such as "upload path", and some plugin items (depends on what you've installed, such as widgets, stats, DMSGuestbook, sitemap, etc.)
3. To fix widgets that contain outdated URL's, you may edit them in Dashboard / Appearance / Widgets.
4. Do a FULL database search for any items left. **MAKE SURE** you know what you are changing and go through each item for possible improper replacement.
5. If you a running a network / have multiple sites, you will need to replace instances of the URL in the database. They are stored in many tables, including each one of the sites (blogs). Be careful in what you replace and be sure you know the meaning of the field before changing it. See the Important GUID note below for an example of what not to change.
6. **Note:** If you find your old url in the database options table under `dashboard_incoming_links`, you can ignore or delete that option. It's unused since WP 3.8.

#### Important GUID Note

When doing the above and changing the URLs directly in the database, you will come across instances of the URL being located in the "guid" column in the `wp_posts` tables. It is critical that you do NOT change the contents of this field.   

The term "GUID" stands for "Globally Unique Identifier". It is a field that is intended to hold an identifier for the post which a) is unique across the whole of space and time and b) never, ever changes. The GUID field is primarily used to create the WordPress feeds. 

When a feed-reader is reading feeds, it uses the contents of the GUID field to know whether or not it has displayed a particular item before. It does this in one of various ways, but the most common method is simply to store a list of GUID's that it has already displayed and "marked as read" or similar. 

Thus, changing the GUID will mean that many feedreaders will suddenly display your content in the user's reader again as if it was new content, possibly annoying your users. 

In order for the GUID field to be "globally" unique, it is an accepted convention that the URL or some representation of the URL is used. Thus, if you own `example.com`, then you're the only one using `example.com` and thus it's unique to you and your site. This is why WordPress uses the permalink, or some form thereof, for the GUID. 

However, the second part of that is that the GUID must _never_ change. Even if you shift domains around, the post is still the same post, even in a new location. Feed readers being shifted to your new feeds when you change URLs should still know that they've read some of your posts before, and thus the GUID _must_ remain unchanged. 

**Never, ever, change the contents of the GUID column, under any circumstances.**

If the default uploads folder needs to be changed to a different location, then any media URLs will need to be changed in the `post_content` column of the posts table. For example, if the default uploads folder is changing from `wp-content/uploads` to `images`: 

`UPDATE wp_posts SET post_content = REPLACE(post_content,'www.domain.com/wp-content/uploads','www.domain.com/images');`

#### Multi-site notes

See [Moving WordPress Multisite](https://developer.wordpress.org/advanced-administration/upgrade/migrating/#moving-wordpress-multisite)

#### wp-cli

[wp-cli](http://wp-cli.org/) is a super useful shell tool.

`wp search-replace 'example.dev' 'example.com' --skip-columns=guid`

Or, if you only want to change the option, you can do:
```
wp option update home 'http://example.com'
wp option update siteurl 'http://example.com'
```

# Moving WordPress

Whether you are moving WordPress to a new server or to a different location on your server, you don't need to reinstall. WordPress is flexible enough to handle all of these situations.

## Moving to a New Server

If you are moving WordPress from one server to another, begin by backing up your WordPress directory, images, plugins, and other files on your site as well as the database. See [WordPress Backups](https://developer.wordpress.org/advanced-administration/security/backup/) and [Backing Up Your Database](https://developer.wordpress.org/advanced-administration/security/backup/database/).

### Keeping Your Domain Name and URLs

Moving your domain without changing the Home and Site URLs of your WordPress site is very simple, and in most cases can be done by moving the files.

- If database and URL remain the same, you can move by just copying your files and database.
- If database name or user changes, [edit wp-config.php](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) to have the correct values.
- If you want to test before you switch, you must temporarily change "siteurl" and "home" in the database table "wp_options" (through phpMyAdmin or similar).
- If you had any kind of rewrites (permalinks) setup you must disable .htaccess and reconfigure permalinks when it goes live.

### Changing Your Domain Name and URLs

Moving a website and changing your domain name or URLs (i.e. from http://example.com/site to http://example.com, or http://example.com to http://example.net) requires the following steps – in sequence.

1. Download your existing site files.
2. Export your database – go in to MySQL and export the database.
3. Move the backed up files and database into a new folder – somewhere safe – this is your site backup.
4. Log in to the site you want to move and go to Settings > General, then change the URLs. (ie from http://example.com/ to http://example.net) – save the settings and expect to see a 404 page.
5. Download your site files again.
6. Export the database again.
7. Edit `wp-config.php` with the new server's MySQL database name, user and password.
8. Upload the files.
9. Import the database on the new server.

When your domain name or URLs change there are additional concerns. The files and database can be moved, however references to the old domain name or location will remain in the database, and that can cause issues with links or theme display.

If you do a search and replace on your entire database to change the URLs, you can cause issues with data serialization, due to the fact that some themes and widgets store values with the length of your URL marked. When this changes, things break. To avoid that serialization issue, you have three options:

1. Use the [Velvet Blues Update URLs](https://wordpress.org/plugins/velvet-blues-update-urls/) or [Better Search Replace](https://wordpress.org/plugins/better-search-replace/) plugins if you can access your Dashboard.
2. Use [WP-CLI's search-replace](http://wp-cli.org/commands/search-replace/) if your hosting provider (or you) have installed WP-CLI.
3. Use the [Search and Replace for WordPress Databases Script](https://interconnectit.com/products/search-and-replace-for-wordpress-databases/) to safely change all instances on your old domain or path to your new one. (**only use this option if you are comfortable with database administration** )

Note: Only perform a search and replace on the wp_posts table.
Note: Search and Replace from Interconnectit is a 3rd party script

## Moving Directories On Your Existing Server

Moving the WordPress files from one location on your server to another – i.e. changing its URL – requires some special care. If you want to move WordPress to its own folder, but have it run from the root of your domain, please read Giving WordPress Its Own Directory for detailed instructions.

Here are the step-by-step instructions to move your WordPress site to a new location on the same server:

1. Create the new location using one of these two options:
   - If you will be moving your WordPress core files to a new directory, create the new directory.
   - If you want to move WordPress to your root directory, make sure all `index.php`, [.htaccess](https://wordpress.org/documentation/article/glossary/#htaccess), and other files that might be copied over are backed up and/or moved, and that the root directory is ready for the new WordPress files.
2. Log in to your site.
3. Go to the [Administration](https://wordpress.org/documentation/article/administration-screens/) > [Settings](https://wordpress.org/documentation/article/administration-screens/#settings-configuration-settings) > [General](https://wordpress.org/documentation/article/settings-general-screen/) screen.
4. In the box for **WordPress Address (URL)**: change the address to the new location of your main WordPress core files.
5. In the box for **Site Address (URL)**: change the address to the new location, which should match the WordPress (your public site) address.
6. Click **Save Changes**.
7. (Do not try to open/view your site now!)
8. Move your WordPress core files to the new location. This includes the files found within the original directory, such as http://example.com/wordpress, and all the sub-directories, to the new location.
9. Now, try to open your site by going to yourdomain.com/wp-admin. Note, you may need to go to yourdomain.com/wp-login.php
10. If you are using [Permalinks](https://wordpress.org/documentation/article/using-permalinks/), go to the Administration > Settings > [Permalinks](https://wordpress.org/documentation/article/settings-permalinks-screen/) panel and update your Permalink structure to your [.htaccess](https://wordpress.org/documentation/article/glossary/#htaccess), file, which should be in the same directory as the main `index.php` file.
11. Existing image/media links uploaded media will refer to the old folder and must be updated with the new location. You can do this with the [Better Search Replace](https://wordpress.org/plugins/better-search-replace/) or [Velvet Blues Update URLs](https://wordpress.org/plugins/velvet-blues-update-urls/) plugins, [WP-CLI's search-replace](http://wp-cli.org/commands/search-replace/) if your hosting provider (or you) have installed WP-CLI, manually in your SQL database, or by using the 3rd party database updating tool [Search and Replace Databases Script](https://interconnectit.com/products/search-and-replace-for-wordpress-databases/) * **Note:** this script is best used by experienced developers.
12. In some cases your permissions may have changed, depending on your ISP. Watch for any files with "0000" permissions and change them back to "0644".
13. If your theme supports menus, links to your home page may still have the old subdirectory embedded in them. Go to Appearance > Menus and update them.
14. Sometimes you would need to restart your server, otherwise your server may give out an error. (happens in MAMP software (Mac)).
15. It is important that you set the URI locations BEFORE you move the files.

#### If You Forget to Change the Locations

If you accidentally moved the files before you changed the URIs: you have two options.

1. Suppose the files were originally in /path/to/old/ and you moved them to /path/to/new before changing the URIs. The way to fix this would be to make 
```
/path/to/old/ a symlink (for Windows users, "symlink" is equivalent to "shortcut") to /path/to/new/, i.e.
ln -s /path/to/new /path/to/old
```
and then follow the steps above as normal. Afterwards, delete the symlink if you want.

2. If you forget to change the WordPress Address and Blog Address, you will be unable to change it using the wordpress interface. However, you can fix it if you have access to the database. Go to the database of your site and find the wp_options table. This table stores all the options that you can set in the interface. The WordPress Address and Blog Address are stored as `siteurl` and `home` (the option_name field). All you have to do is change the option_value field to the correct URL for the records with `option_name='siteurl‘` or `option_name='home‘`.

Note: Sometimes, the WordPress Address and Blog Address are stored in [WordPress Transients](https://developer.wordpress.org/apis/handbook/transients/). Search and replace scripts can have trouble modifying those to the new address and some plugins might therefore refer to the old address because of them. Transients are temporary (cached) values stored in the wp_options database table that can be recreated on-demand when removed. It's therefore safe to delete them from the migrated database copy and let them be recreated. This database query (again, have a backup!) clears all transients:

```
DELETE FROM `wp_options` WHERE option_name LIKE '%\_transient\_%' 
```

#### If You Have Accidentally Changed your WordPress Site URL

Suppose you accidentally changed the URIs where you cannot move the files (but can still access the login page, through a redirection or something).

`wp-login.php` can be used to (re-)set the URIs. Find this line:

```
require( dirname(__FILE__) . '/wp-load.php' );
```

and insert the following lines below:

```
//FIXME: do comment/remove these hack lines. (once the database is updated)
update_option('siteurl', 'http://your.domain.name/the/path' );
update_option('home', 'http://your.domain.name/the/path' );
```

You're done. Test your site to make sure that it works right. If the change involves a new address for your site, make sure you let people know the new address, and consider adding some redirection instructions in your `.htaccess` file to guide visitors to the new location.

[Changing The Site URL](https://developer.wordpress.org/advanced-administration/upgrade/migrating/) also provides the details of this process.


## Managing Your Old Site

### Shutting It Down
1. Download a copy of the main wordpress files from your OLD site to your hard drive and [edit wp-config.php](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) to suit the new server.
2. Go back to your OLD site and go to [Administration](https://wordpress.org/documentation/article/administration-screens/) > [Settings](https://wordpress.org/documentation/article/administration-screens/#settings-configuration-settings) > [General](https://wordpress.org/documentation/article/settings-general-screen/) screen and change the URL (both of them) to that of your new site.
3. Login on your server, go to phpMyAdmin, export as file, and save your database (but keep the old one just in case). Now, upload this new database and the copy of the wordpress core files with the edited wp-config.php to your new server. That's it!

#### Keeping it Running
Caution: Make sure you have a backup of your old site's WordPress database before proceeding!

_Part A – Activating Your New Site_

1. Download your entire WordPress installation to your hard drive. Name the folder appropriately to indicate that this is your OLD site's installation.
2. Download your database.
3. Go back to your OLD site and go to options and change the url (both of them) to that of your new site.
4. Again, download your entire WordPress installation to your hard drive. Name the folder appropriately to indicate that this is your NEW site's installation.
5. Download your database once again (but keep the old one). Upload this database to your new server. It will be easiest if you use the same database name and you create a user with the same login credentials on your new server as on your old server.
6. If you used a different database name and/or user (see previous step), [edit wp-config.php](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) in your NEW site's installation folder appropriately.
7. Upload the NEW site's installation folder to your new site. Presto, your NEW site should be working!

_Part B – Restoring Your Old Site_

1. On the original server, delete your OLD site's database (remember, you should have a copy on your local computer that you made at the very beginning).
2. Upload your OLD site's installation folder to your original server, overwriting the files that are currently there (you may also delete the installation folder on the server and simply re-upload the OLD site's files).
3. Upload your OLD site's database from your local computer to the server. That should do it!

Another procedure for making copies of posts, comments, pages, categories and custom field (post status, data, permalinks, ping status, etc.) easy to follow:

1. Install a new WordPress site
2. Go on old site Admin panel. Here, in Manage > Export select "all" in menu Restrict Author.
3. Click on Download Export File
4. In new site go on Manage > Import, choose WordPress item.
5. In the page that will be shown, select the file just exported. Click on Upload file and Import
6. It will appear a page. In Assign Authors, assign the author to users that already exist or create new ones.
7. Click on Submit
8. At the end, click on Have fun

_Note: using this method, if there are some articles in the new site (like Hello World, Info Page, etc.), these will not be erased. Articles are only added. Using the former procedure, the articles in new site will be deleted._

## Moving WordPress Multisite

[Multisite](https://developer.wordpress.org/advanced-administration/multisite/create-network/) is somewhat more complicated to move, as the database itself has multiple references to the server name as well as the folder locations. If you're simply moving to a new server with the same domain name, you can copy the files and database over, exactly as you would a traditional install.

If, instead, you are changing domains, then the best way to move Multisite is to move the files, edit the `.htaccess` and `wp-config.php` (if the folder name containing Multisite changed), and then manually edit the database. Search for all instances of your domain name, and change them as needed. This step cannot yet be easily automated. It's safe to search/replace any of the `wp_x_posts` tables, however do not attempt blanket search/replace without the [Search and Replace for WordPress Databases](https://github.com/interconnectit/Search-Replace-DB) script (aka the interconnectit script).

If you're moving Multisite from one folder to another, you will need to make sure you edit the `wp_blogs` entries to change the folder name correctly. You should manually review both `wp_site` and `wp_blogs` regardless, to ensure all sites were changed correctly.

Also, manually review all the wp_x_options tables and look for three fields and edit them as needed:

- home
- siteurl
- fileupload_url

If you are moving from subdomains to subfolders, or vice-versa, remember to adjust the .htaccess file and the value for SUBDOMAIN_INSTALL in your `wp-config.php` file accordingly.

### Related Links

- [Moving a blog from wordpress.com to self-hosted blog](http://www.problogger.net/archives/2009/01/03/how-to-move-from-wordpresscom-to-wordpressorg/)
- [Moving WordPress to a new domain or server](http://sltaylor.co.uk/blog/moving-wordpress-new-domain-server/)
- [Italian version of this article – Versione italiana dell'articolo](http://www.valent-blog.eu/2007/09/14/trasferire-wordpress/)
- [Search and Replace for WordPress Databases](http://interconnectit.com/124/search-and-replace-for-wordpress-databases/)
- [PHP script to replace site url in WordPress database dump, even with WPML](http://blog.lavoie.sl/2012/07/php-script-to-replace-site-url-in.html)
- [The Duplicator plugin helps administrators move a site from one location to another](https://wordpress.org/plugins/duplicator/)
- [Technical tutorial on moving your WordPress blog to Bitnami's AWS configuration](http://www.agileweboperations.com/migrate-your-wordpress-blog-to-a-bitnami-ec2-instance)

# Migrating multiple blogs into WordPress multisite

This tutorial explains how to migrate multiples WordPress installs to a WordPress multisite install. You can migrate sites that use their own domain names, as well as sites that use a subdomain on your primary domain.

This tutorial assumes that you are hosting WordPress on a server using cPanel. If you are using another solution to manage your server, you'll have to adapt these instructions.

### Steps to Follow

#### Backup your site

Generate a full site backup in cPanel. It might also help to copy all the files on the server via FTP, so that you can easily access the files for plugins and themes, which you'll need in a later step.

#### Export from your existing WordPress installs
In each of your existing WordPress installations, go Tools > Export in WordPress. Download the WXR files that contain all your posts and pages for each site. See the instructions on the [Tools Export Screen](https://wordpress.org/documentation/article/tools-export-screen/).

Make sure that your export file actually has all the posts and pages. You can verify this by looking at the last entry of the exported file using a text editor. The last entry should be the most recent post.

Some plugins can conflict with the export process, generating an empty file, or a partially complete file. To be on the safe side, you should probably disable all plugins before doing the exports.

It's also a good idea to first delete all quarantined spam comments as these will also be exported, making the file unnecessarily large.

**Note:** Widget configuration and blog/plugin settings are NOT exported in this method. If you are migrating within a single hosting account, make note of those settings at this stage, because when you delete the old domain, they will disappear.

#### Install WordPress
Install WordPress. Follow the instructions for [Installing WordPress](https://developer.wordpress.org/advanced-administration/before-install/howto-install/).

#### Activate multisite
Activate multi-site in your WordPress install. This involves editing `wp-config.php` a couple of times. You need to use the subdomain, not the subdirectory, option. See the instructions on how to [Create A Network](https://developer.wordpress.org/advanced-administration/multisite/create-network/).

#### Create blogs for each site you want to import
Create blogs for each of the sites you want to host at separate domains. For example, `importedblogdotorg.mydomain.com`.

Note: choose the name carefully, because changing it causes admin redirection issues. This is particularly important if you are migrating a site within the same hosting account.

#### Import WXR files for each blog
Go to the backend of each blog, and import the exported WXR file for each blog. Map the authors to the proper users, or create new ones. Be sure to check the box that will pull in photos and other attachments. See the instructions on Tools Import SubPanel.

**Note:** if you choose to import images from the source site into the target site, make sure they have been uploaded into the right place and are displayed correctly in the respective post or page.

#### Edit WordPress configuration settings for each site

Edit the configuration settings, widget, etc. for each site. By the end of this step, each site should look exactly as it did before, only with the URL `subdomain.example.com` or `example.com/subsite` rather than its correct, final URL.

#### Limitations of PHP configuration
You may run into trouble with the PHP configuration on your host. There are two potential problems. One is that PHP's `max_upload_size` will be too small for the WXR file. The other problem is that the PHP memory limit might be too small for importing all the posts.

There are a couple ways to solve it. One is to ask your hosting provider to up the limits, even temporarily. The other is to put a php.ini file in your /wp-admin/ and /wp-includes directories that ups the limits for you (php.ini files are not recursive, so it has to be in those directories). Something like a 10 MB upload limit and a 128 MB memory limit should work, but check with your hosting provider first so that you don't violate the terms of your agreement.

Search the [WordPress forum support](https://wordpress.org/support/forums/) for help with PHP configuration problems.

#### Converting add-on domains to parked domains

Deleting add-on domains in cPanel and replacing them with parked domains will also delete any domain forwarders and e-mail forwarders associated with those domains. Be aware of this, so that you can restore those forwarders once you've made the switch.

#### Limitations of importing users

As there is the above way to import the content into an instance of the Multisite-blog, you are running into massive troubles, when it gets to import multiple users. Users are generated during the import, but you won't get any roles or additional information into the new blog.

#### Losing settings
If the old site is no longer available and you find you have forgotten to copy some setting or you want to make sure you have configured everything correctly, run a google search for your site and then click to view the cached version. This option is available only until your new site has been crawled, so you'd better be quick.

Another option might be the [Internet Archive Wayback Machine](https://archive.org/web/). They may have a copy of the site (or some part of it) archived.

## Changelog

- 2022-09-11: Original content from [Changing The Site URL](https://wordpress.org/documentation/article/changing-the-site-url/), and [Moving WordPress](https://wordpress.org/documentation/article/moving-wordpress/).
