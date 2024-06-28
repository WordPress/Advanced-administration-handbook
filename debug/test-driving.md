# Test Driving WordPress

There are times when you need to test changes to your WordPress powered site out of the public eye. Making changes to a live site could adversely affect your readers.

You have several choices.

### Creating a Sandbox

Do this for test driving your WordPress Theme and style sheet, allowing you to develop your [WordPress Theme](https://wordpress.org/documentation/article/using-themes/) on your computer. This limits you to only working on CSS and not using plugins and other power features of WordPress. This is best for just styling a page.

### Hiding Your WordPress Test Area

You can also close off access to your WordPress test site on your website server. This involves some familiarity with .htaccess and Apache, but it allows you to continue working on the Internet while not exposing your test site to the public.

### Install WordPress on Your Computer

If you are determined to put WordPress through its paces, you can install WordPress on your own computer with a few modifications. This allows you total control over the actions and capabilities of WordPress. You can still use plugins, template files, Themes, and redesign everything as if it were on the Internet without using bandwidth or suffering with slow Internet access times. We have two sets of explanation for this: Installing a New Installation on Your Computer and Installing an Existing WordPress Site. We also cover Moving WordPress Onto Your Website after you have finished developing your site on your computer.

## Creating a Sandbox

A **Sandbox** is a term related to the sandbox you might have played in and built sand castles in as a child. It is a playground for working on concepts and exploring your imagination. A WordPress Sandbox is basically a copy of a generated page on a WordPress site that is saved to your hard drive for you to play with as you develop your final theme and look for your site.

WordPress uses different [template files](https://codex.wordpress.org/Templates) to generate different views on your site. In general, there is the **front page view**, the **single post view**, and the **multi-post view**, used for categories, archives, and search. For more information on the structure of WordPress Themes, see [Site Architecture 1.5](https://developer.wordpress.org/advanced-administration/wordpress/site-architecture/). As different page views use different CSS styles, at the least you need to put three page views in your sandbox following these instructions.

1. Choose the WordPress Theme you want to work from in your Appearance screen of your Administration Screen.
2. From your initial or test WordPress site, view one of the following page views:
   - Front Page
   - Single Post
   - Mult-Post Page
3. From your browser:
   - Choose File > Save As.
   - Name the saved page with one of the above “titles.”
   - Save each page’s file to your sandbox folder.
4. From your Theme’s folder, copy the style.css style sheet file to your sandbox folder.
5. Open each of the three files you have saved in a text editor and change the following:

```
<style type="text/css" media="screen">
@import url('/wp-content/themes/yourtheme/style.css');
</style>
``` 

```
<link rel="stylesheet" type="text/css" media="screen" href="style.css" />
```

Lastly, find all the image files and graphics associated with the style sheet, like background images, icons, bullets, or others, and copy them to your sandbox folder. Links to these items from within your style sheet should have no folders in their links, or link to a subfolder within your sandbox folder, such as:


```
header {
  margin:5px;
  padding:10px;
  background:url(images/background.jpg)....
}
```

To test this, double click on one of the pages in that folder to view it in your browser. If the the styles and graphics are visible, it worked. If not, check the link to your style sheet.

Now, you have a sandbox to play in.

To use your sandbox, have two programs open. One is your text editor with the style.css file and the other is your browser with the page you are working on in view. Make a change in your style sheet, save it, then do a [total refresh](https://wordpress.org/support/topic/i-make-changes-and-nothing-happens/) or your browser screen and look at what changes. Then repeat the process. When you are done with one page, go to the next page and check those changes and add more if necessary.

### Sandbox Tips

Here are a few tips for playing in your sandbox.

### Backup Frequently

As you work, copy the files in the folder before making major renovations to a backup folder. Or you can use a file compression utility like a zip program to save the entire folder. If the changes you make do not work, you have recent backups to work from.

### Trace DIVS and CLASSES

Before you begin, go through the source code file of each of the three page views in your text editor and add a comment as to where each division begins and ends. These often cross lines between template files and can be difficult to trace. Make your life easier by documenting where these begin and end.

### Make Notes

Before making a huge change, write down what you are changing. This way you have notes to refer to when things do not work later and you are trying to trace the history of the changes.

### Make Notes Inside

When making significant changes to the style sheet or to the web page source code, add notes or comments to the code. This will also help you to keep track of the changes you’ve made.

### Make Small Steps

Making a lot of changes at once makes it harder to find the small change that made things go out of whack. A good approach would be to take small steps and check, then make additional, small modifications and check again.

### Avoid Changing the Template Files

Begin by staying with the site architecture and style references already in place. If you will be releasing your Theme to the public, modifications to the template files must be seriously reconsidered and done carefully. If you are rebuilding your site for your private use, then you can make those changes to your HTML saved pages source code, then move those changes into the template files later.

### Moving Your Sandbox To Your Theme
When you have made all your changes and are satisfied with the results, it is time to move your sandbox back into your WordPress Theme.

1. Upload the style.css style sheet file to your WordPress Theme folder on your site, replacing the old file.
2. Upload all graphics and images to the Theme folder or subfolder.
3. View your site in your browser. The changes should be immediate.
4. If you made changes to the source code of any of the three pages, track those down to their specific template file and make the changes in those template files on your site.

### Install another Blog

1. Install WordPress again, but in the wp-config.php file, use a different table-prefix.
2. In Options > Writing > Update Services, clear the box.
3. Tell no one where your blog is located.

If you go to another site from your blog, then your site could be discovered because of the referer in the browser. To prevent this, go to your real blog, then to another site.

This method is useful toward the end of testing as you can ask for people to test using other browsers / screen resolutions.

## Hiding Your WordPress Test Area

To hide your WordPress test folder from others, you can use the `.htaccess` file on an Apache web server. The `.htaccess` file is a file that stores server directives, instructions which tell the server what to do in specific situations. You could also use the Apache config file (httpd.conf) or other methods, but the `.htaccess` file can apply only to the folder in which the .htaccess file resides, and all the folders under that one, allowing you to restrict access to a specific folder.

Remember, this will only work on servers that support `.htaccess`. If you are unsure that your server supports `.htaccess`, contact your hosting provider. You may or may not be able to do this depending upon the access permissions you have with your host server. You may need their assistance. If you are running your own server, or if your hosting provider is clue-free, consult the [AllowOverride documentation](http://httpd.apache.org/docs-2.0/mod/core.html#allowoverride).

Using the `.htaccess` file, you need to provide instructions to tell the server to restrict or deny access to your WordPress test site. In the folder or directory in which WordPress is installed, do the following:

1. Using a text editor create a blank text file called `.htaccess`.
2. You need the following information:
3. - The full path of a directory on your site server that is not accessible to the public (like http://example.com/public_html/ is accessible but http://example.com/private/ is not. Use the latter.
4. - The name of the secured area such as “Enter Password” or “Secure Area” (this is not important, just simple).
5. In the file type the following, replacing /full/path/of/directory/ and Security Area with the above information: `AuthUserFile /full/path/of/directory/.htpasswd AuthName "Security Area" AuthType Basic require valid-user`
6. Save this .htaccess file and upload it to the directory on your server you want hidden and secured. This would be the installation directory for WordPress such as `/wordpress/` or `blog`.
7. Using Telnet, cPanel, or another way to access your server’s command panel, go to the directory specified as `AuthUserFile`.
8. Type the following command, where `user_name` is the user name for the `access:htpasswd -c .htpasswd user_name`
9. When prompted, enter the password, and confirm it.
10. Write down your password and user name and keep it in a safe place.

When you are ready to open your site to the public and remove the protection, delete the password and `.htaccess` files from their locations.

It is highly recommended that you remove the default ping URL to [Ping-o-Matic!](http://www.pingomatic.com/), otherwise your test posts will ping and your test blog will be made public though not accessible.

### Htaccess Resources

- [.htaccess files howto](http://httpd.apache.org/docs-2.0/howto/htaccess.html)
- [Authentication, Authorization and Access Control](http://httpd.apache.org/docs-2.0/howto/auth.html)

## Installing WordPress on a Mac

Use these instruction for setting up a local server environment for testing and development on a Mac.

- [Installing_WordPress_Locally_on_Your_Mac_With_MAMP](https://codex.wordpress.org/Installing_WordPress_Locally_on_Your_Mac_With_MAMP)

## Installing WordPress on Your Windows Desktop

In order for WordPress to work, it must have access to an Apache server, MySQL/MariaDB, and phpMyAdmin. Installing these separately can be painful. Luckily for us, [XAMPP](http://www.apachefriends.org/en/xampp-windows.html) installs all of these with one program, allowing you to run WordPress on your computer. There are two versions of the program, Basic and Lite. The Lite version is usually adequate.

1. Download and install [XAMPP](http://www.apachefriends.org/en/xampp-windows.html).
2. This installs by default into `C:/xampplite` or `C:\xampp`.
3. Start XAMPP from `c:\xampplite` or `c:\xampp`.
4. You may need to restart your computer to allow apache services to start.
5. In your browser, go to http://localhost/xampp.
6. In the left column under Tools, click **phpMyAdmin**.
7. Login is admin.
8. In **Create new database** enter **wordpress**.
9. In the next box, select **utf8 unicode ci**.
10. Click **Create** button.
11. Unzip your WordPress download into the `htdocs` directory `– c:\xampp\htdocs\`.
12. From the folder, open `wp-config-sample.php` in a text editor.
13. The connection details you need are as follows:
```
// ** MySQL settings ** //
define('DB_NAME', 'wordpress'); // The name of the database
define('DB_USER', 'root'); // Your MySQL username
define('DB_PASSWORD', ''); // ...and password
define('DB_HOST', 'localhost'); // 99% chance you won't need to change this
```
14. Save as `wp-config.php`.
15. Install by going to http://localhost/wordpress/wp-admin/install.php

**IMPORTANT:** It is possible to use this to actually host your blog if you have a good enough connection. If you want to do this, you MUST increase the security level. This description is NOT SECURE if you allow web access to your blog.

## Installing an Existing WordPress Site

With the help of XAMPP, you can install WordPress directly on your computer and play with it to your heart’s content. This way, it is totally isolated from public exposure and all your mistakes are hidden. When you are ready, you can then move it onto your website, ready for all to see.

### Requirements

1. Access to your server database.
2. Ability to download your entire WordPress installation to your computer.
3. [Basic XAMPP for Windows](http://www.apachefriends.org/en/xampp-windows.html)
4. Enough room on your hard drive to accommodate your database, WordPress installation, and XAMPP.

### Backup WordPress

![phpMyAdmin MySQL Databases](https://user-images.githubusercontent.com/6118303/189545243-6ac55696-9f98-41fe-ab06-061c505f5ab2.png)

Begin by backing up your WordPress site completely, the files and the database. This will ensure that you have a good copy to fall back on, just in case.

A second backup is then required of your database, but it requires you do a little housekeeping.

As the WordPress database normally stands, there are statistics tables which contain a huge amount of data which add to the overall size of the database, and slow down the process of backing up and downloading this backup copy of your database. You do not have to clear these, but it is generally considered a good idea.

1. Login to PHPMyAdmin on your website server.
2. From the main login screen, select **Databases**.
3. Choose the name of your WordPress database.
4. From the tags at the top of the screen, choose **Export**.
5. In the frame at the top of the [Export section](https://wordpress.org/documentation/files/2018/11/phpmyadmin-export-tab.jpg) you will see a list of tables in your database.
6. You will need to choose only those tables that correspond to your WordPress install. They will be the ones with the `table_prefix` found in your `wp-config.php` file. If you only have WordPress installed, then choose **Select All** from the left column.
7. Make sure the SQL button is selected.
8. On the right side of the panel, make sure the following boxes are checked.

![phpMyAdmin export screen](https://user-images.githubusercontent.com/6118303/189546285-35103e54-cba6-42ee-86ea-8ea480bf1630.png)

- Structure
- Add AUTO_INCREMENT value
- Enclose table and field names with backquotes
- Data Tick the Save as file option, and leave the template name alone. 
- For Compression, select **None**. Click **Go**. 
- You should be prompted for a file to download. Save the file to your computer. Depending on the database size, this may take a few moments.

### Download WordPress
Now, download your entire WordPress site to your computer. This is usually done with an [FTP client program](https://developer.wordpress.org/advanced-administration/upgrade/ftp/). Make sure you include all core WordPress files within your root or WordPress directory, including the `index.php`.

You should now have in your computer two items:

1. One or more database backups.
2. All your WordPress files, folders, and images directories.

Copy the backup files again to somewhere safe on your machine so you work on a copy of the backup for the next stage.

### Install Basic XAMPP

1. Install XAMPP. By default, it will install to `C:\xampp`.
2. Go to `C:\xampp\apache\conf` and open the file called `httpd.conf` in a [text editor](https://wordpress.org/documentation/article/wordpress-glossary/#Text%20editor).
3. About line 166 you will find: `#LoadModule rewrite_module modules/mod_rewrite.so`.
4. Remove the # and save the file (this switches `mod_rewrite` on).
5. Create a folder inside `C:\xampp\htdocs`. This will be for WordPress.
6. Copy all your downloaded WordPress files (not the sql backup) into that directory.
7. With a text editor, open the file `wp-config.php` on your WordPress install.
8. Change the details for your new MySQL connection:
```
// ** MySQL settings ** //
define('DB_NAME', 'wordpress'); // The name of the new database you made
define('DB_USER', 'root'); // keep this as is
define('DB_PASSWORD', ''); // keep this empty
define('DB_HOST', 'localhost'); // 99% chance you won't need to change this
```
9. Your main `index.php` should be in the `/htdocs` folder or in a subdirectory such as `/htdocs/wordpress/`. Write that down.
10. Start XAMPP by clicking the orange `xampp_start.exe` or using the console program from `Program Files \ApacheFriends\XAMPP\XAMPP Control Panel`.
11. Once it is working, click on link in the left side bar for phpMyAdmin.
12. Create a database. The name should match the one used in your `wp-config.php` file.

### Importing Your SQL Backup File

Before you begin to import your SQL backup file, you need to change some information inside your `.SQL` file.

1. Using your text editor, open the `.sql` backup database file you downloaded.
2. Find and replace all the instances of your old URL with your new URL. For instance if your blog address is at http://example.com/wordpress/, and your files on your computer are at `/htdocs/wordpress/`, replace it with http://127.0.0.1/wordpress/.
3. Click **Save – Do not use Save as**.

![phpMyAdmin SQL tab](https://user-images.githubusercontent.com/6118303/189546617-26a843c4-e793-4c44-b2a6-13a32b366a8e.png)

Now it is time to import your sql file. From within the phpMyAdmin on your computer, click on **your database name**, then choose the **SQL** tab. From this screen, click **Browse** and find your backup files on your computer. Click **Go**. This can take a few minutes to import.

![Success message](https://user-images.githubusercontent.com/6118303/189546659-7e3cefe1-1744-4458-ac49-98fbd04626c5.png)

Once the procedure is complete, your database will be restored and will work just as it did before. If something goes wrong with this last part of the process, it could be that either your backup was corrupted in some way, or something went wrong with the database itself. **Keep your backup files safe!**

If everything so far has gone well, it is now time to visit your blog! In your browser, type in http://127.0.0.1/wordpress/index.php or the actual name of the folder you created for your WordPress files.

If you get a 404, check you have the right place. You do not need to put htdocs or xampp or anything else after the http://127.0.0.1/ except use your directory name.

WordPress should now function just as it did on the web. You do not need to use the built-in editor in WordPress to alter your files. Just open the files directly in a text editor and edit them as you would any other file. Refreshing your browser’s web page will then show the effects.

### Moving Your Test WordPress Site to Your Website

Coming soon – how to move your test site from your computer back live onto your host server site.

### Resources
- [Test themes on a live blog with Theme Test Drive](http://www.prelovac.com/vladimir/wordpress-plugins/theme-test-drive)
- [qSandbox.com – Create a free WordPress test site to try (new) plugins and themes](http://qsandbox.com/)

## Changelog

- 2022-09-11: Original content from [Test driving WordPress](https://wordpress.org/documentation/article/test-driving-wordpress/).
