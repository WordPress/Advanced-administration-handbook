# Giving WordPress Its Own Directory

Many people want WordPress to power their website's root (e.g. https://example.com) but they don't want all of the WordPress files cluttering up their root directory. WordPress allows you to install it into a subdirectory, but have your website served from the website root.

**Note:** This guide covers configuration for Apache (`.htaccess`), nginx (server blocks), and IIS (`web.config`).

As of [Version 3.5](https://wordpress.org/documentation/wordpress-version/version-3-5/), Multisite users may move a network into its own directory using Method II below. If you are running a version of WordPress older than 3.5, please update before installing a Multisite WordPress install on a subdirectory. Method I is not suitable for Multisite: its catch-all rule in the root `.htaccess` competes with the network's own rewrite rules rather than composing with them.

**Note to theme/plugin developers:** this will not separate your code from WordPress. Themes and plugins will still reside under `wp-content` folder.

## Moving a Root install to its own directory

Let's say you've installed WordPress at `example.com`. Now you have two different methods to move WordPress installations into subdirectory:

1. Without change of SITE-URL (remains `example.com`)
2. With change in SITE-URL (it will redirect to `example.com/subdirectory`)

## Method I (Without URL change)

1. After Installing WordPress in the root folder, move EVERYTHING from the root folder into subdirectory.

### Apache (.htaccess)
2. Create a `.htaccess` file in the root folder, and put this content inside (just change `my_subdir`):

```apache
# BEGIN WordPress in a subdirectory
<IfModule mod_rewrite.c>
RewriteEngine On
RewriteRule .* - [E=HTTP_AUTHORIZATION:%{HTTP:Authorization}]
RewriteBase /

RewriteRule ^$ my_subdir/index.php [L]

# The !^/my_subdir/ condition is required: this file runs again on the
# internal redirect, and without it /my_subdir/foo would be rewritten to
# /my_subdir/my_subdir/foo in a loop.
RewriteCond %{REQUEST_URI} !^/my_subdir/
RewriteCond %{REQUEST_FILENAME} !-f
RewriteCond %{REQUEST_FILENAME} !-d
RewriteRule ^(.*)$ my_subdir/$1 [L]
</IfModule>
# END WordPress in a subdirectory
```

If your server hosts more than one domain from this document root, add a host check to each `RewriteRule` — for example `RewriteCond %{HTTP_HOST} ^(www\.)?example\.com$`. Note the escaped dots: an unescaped `.` in a regular expression matches any character.

### nginx (server block)
2. Add this to your nginx server block:

```nginx
location /my_subdir/ {
    try_files $uri $uri/ /my_subdir/index.php?$args;
}

location ~ \.php$ {
    fastcgi_split_path_info ^(.+\.php)(/.*)$;
    fastcgi_pass unix:/var/run/php/php8.3-fpm.sock;
    fastcgi_param SCRIPT_FILENAME $document_root$fastcgi_script_name;
    include fastcgi_params;
}
```

That's all 🙂

## Method II (With URL change)

### Moving process

_(p.s. If you've already installed WP in subdirectory, some steps might be already done automatically)._

1. Create the new location for the core WordPress files to be stored—we will use `/wordpress` in our examples. On Linux, use `mkdir wordpress` from your `www` directory. You'll probably want to use `chown apache:apache` on the `wordpress` directory you created.
2. Go to the [General](https://wordpress.org/documentation/article/administration-screens/#settings-configuration-settings) screen.
3. In **WordPress address (URL):** set the address of your main WordPress core files. Example: `https://example.com/wordpress`.
4. In **Site address (URL):** set root directory's URL. Example: `https://example.com`.
5. Click **Save Changes**. Do not worry about the errors that happen now! Continue reading.
6. Now move your WordPress core files (from root directory) to the subdirectory.
7. Copy (NOT MOVE!) the `index.php` and `.htaccess` files from the WordPress directory into the root directory of your site (Blog address). The `.htaccess` file is invisible, so you may have to set your FTP client to [show hidden files](https://developer.wordpress.org/advanced-administration/server/file-permissions/#Unhide_the_hidden_files). If you are not using [pretty permalinks](https://wordpress.org/documentation/article/using-permalinks/#using-pretty-permalinks), then you may not have a .`htaccess` file. _**If you are running WordPress on a Windows (IIS) server** and are using pretty permalinks, you'll have a `web.config` rather than a `.htaccess` file in your WordPress directory. For the `index.php` file the instructions remain the same, copy (don't move) the index.php file to your root directory. The `web.config` file, must be treated differently than the `.htaccess` file so you must MOVE (DON'T COPY) the `web.config` file to your root directory._
8. Open your root directory's `index.php` file in a [text editor](https://wordpress.org/documentation/article/glossary#text-editor).
9. Change the following and save the file. Change the line that says:`require __DIR__ . '/wp-blog-header.php';`to the following, using your directory name for the WordPress core files: `require __DIR__ . '/wordpress/wp-blog-header.php';`. (Older versions of WordPress shipped this line as `require dirname( __FILE__ ) . '/wp-blog-header.php';` — edit it the same way.)
10. Login to the new location. It might now be `https://example.com/wordpress/wp-admin/`.
11. If you have set up [Permalinks](https://wordpress.org/documentation/article/using-permalinks/), go to the [Permalinks Screen](https://wordpress.org/documentation/article/administration-screens/#permalinks) and update your Permalink structure. WordPress will automatically update your `.htaccess` file if it has the appropriate file permissions. If WordPress can't write to your `.htaccess` file, it will display the new rewrite rules to you, which you should manually copy into your `.htaccess` file (in the same directory as the main `index.php` file).

### The root .htaccess file

Unlike `index.php`, the `.htaccess` file needs no edits after you copy it to the root directory. WordPress builds the `RewriteBase` line from the **Site address (URL)** (the `home` option), not from the **WordPress address (URL)** (the `siteurl` option), so the rules already point at your site root and are identical to those of a plain root install.

For a single site with **Site address** set to `https://example.com`, the file looks like this:

```apache
# BEGIN WordPress
<IfModule mod_rewrite.c>
RewriteEngine On
RewriteRule .* - [E=HTTP_AUTHORIZATION:%{HTTP:Authorization}]
RewriteBase /
RewriteRule ^index\.php$ - [L]
RewriteCond %{REQUEST_FILENAME} !-f
RewriteCond %{REQUEST_FILENAME} !-d
RewriteRule . /index.php [L]
</IfModule>
# END WordPress
```

The `HTTP_AUTHORIZATION` line was added in [Version 5.6](https://wordpress.org/documentation/wordpress-version/version-5-6/) to support [Application Passwords](https://developer.wordpress.org/rest-api/using-the-rest-api/authentication/#application-passwords); it is absent from files generated by earlier versions.

#### Multisite

A Multisite network's rules are different, and the subdirectory *is* part of them. With WordPress in `/wordpress` and the network served from the root, a sub-directory network's rules are:

```apache
# BEGIN WordPress Multisite
RewriteEngine On
RewriteRule .* - [E=HTTP_AUTHORIZATION:%{HTTP:Authorization}]
RewriteBase /
RewriteRule ^index\.php$ - [L]

# add a trailing slash to /wp-admin
RewriteRule ^([_0-9a-zA-Z-]+/)?wp-admin$ $1wp-admin/ [R=301,L]

RewriteCond %{REQUEST_FILENAME} -f [OR]
RewriteCond %{REQUEST_FILENAME} -d
RewriteRule ^ - [L]
RewriteRule ^([_0-9a-zA-Z-]+/)?(wp-(content|admin|includes).*) wordpress/$2 [L]
RewriteRule ^([_0-9a-zA-Z-]+/)?(.*\.php)$ wordpress/$2 [L]
RewriteRule . index.php [L]
# END WordPress Multisite
```

`RewriteBase` remains `/` because it follows the network's home URL; the `wordpress/` prefix appears on the rewrite *targets* instead. For a sub-domain network, remove every `([_0-9a-zA-Z-]+/)?` group, drop the `$1` from the `wp-admin` rule, and use `$1` in place of `$2` on the last two `RewriteRule` lines:

```apache
RewriteRule ^wp-admin$ wp-admin/ [R=301,L]
RewriteRule ^(wp-(content|admin|includes).*) wordpress/$1 [L]
RewriteRule ^(.*\.php)$ wordpress/$1 [L]
```

Rather than copying these by hand, visit **My Sites → Network Admin → Settings → Network Setup**, which prints the exact rules for your installation.

### .htaccess modification

In some cases, some people like to install separate versions in a subdirectory (such as `/2010`, `/2011`, `/latest` and etc..), and want that website (by default) used the latest version, then Install WordPress in a subdirectory, such as `/my_subdir` and in your root folder's .htaccess file add the following (just change the words as you need):

**Apache (.htaccess):**
```apache
RewriteEngine On
RewriteRule ^$ /my_subdir/ [R=301,L]
```

**nginx (server block):**
```nginx
location = / {
    return 301 /my_subdir/;
}

location /my_subdir/ {
    try_files $uri $uri/ /my_subdir/index.php?$args;
}

location ~ \.php$ {
    fastcgi_split_path_info ^(.+\.php)(/.*)$;
    fastcgi_pass unix:/var/run/php/php8.3-fpm.sock;
    fastcgi_param SCRIPT_FILENAME $document_root$fastcgi_script_name;
    include fastcgi_params;
}
```

Now when users to go your root domain (`example.com`), it will automatically redirect to the subdirectory you specified.

Note: for more background on this approach, see Site 5's post: [How to Redirect Your Domain to a Subfolder Using .htaccess](https://qa.site5.com/advanced/how-to-redirect-your-domain-to-a-subfolder-using-htaccess/).

## Moving Specific WordPress Folders

The following links explains how to change specific directories within WordPress:

* [Moving wp-content folder](https://wordpress.org/documentation/article/editing-wp-config-php/#moving-wp-content-folder)
* [Moving Plugin Folder](https://wordpress.org/documentation/article/editing-wp-config-php/#moving-plugin-folder)
* [Moving Themes Folder](https://wordpress.org/documentation/article/editing-wp-config-php/#moving-themes-folder)
* [Moving Uploads Folder](https://wordpress.org/documentation/article/editing-wp-config-php/#moving-uploads-folder)

## See also

* [Using Caddy to give WordPress its own directory](https://caddy.community/t/using-caddy-to-give-wordpress-its-own-directory/13185)

