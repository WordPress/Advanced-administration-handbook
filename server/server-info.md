# Finding Server Info

What version of [PHP](https://wordpress.org/documentation/article/wordpress-glossary/#php) are you using? What server software is your site host using? What version of [MySQL](https://wordpress.org/documentation/article/wordpress-glossary/#mysql) do you have? What operating system does your site host use?

These are questions often asked by WordPress users as they prepare to [install WordPress](https://developer.wordpress.org/advanced-administration/before-install/howto-install/). Other times, these questions are asked while troubleshooting a problem with a WordPress installation. But don’t spend a lot of time searching your site, or your host’s site, for the answers. There are easy ways to get that information.

## Using Site Health {#using-site-health}

If WordPress is already installed, the easiest way is the Site Health screen. It is available since WordPress 5.2, and you do not need to create any file.

1. In the WordPress dashboard, go to **Tools > Site Health** and click the **Info** tab.
2. Open the **Server** section. It shows the server architecture, the web server software, the PHP version and other PHP settings.
3. Open the **Database** section. It shows the database extension, and the server version and client version of MySQL or MariaDB.

To share this information, for example in the support forum, click **Copy site info to clipboard**. The copied text leaves out private details, such as the database username and the database host.

Only administrators can see the Site Health screen. On a multisite network, only Super Admins can see it. For more details, see the [Info section of the Site Health screen](https://wordpress.org/documentation/article/site-health-screen/#info) article.

## Using phpinfo() {#using-phpinfo}

![Top of PHP Info test file results](https://user-images.githubusercontent.com/1508963/201365720-3a13ccab-c44c-43f2-8326-e3a997c5acfa.jpg)

Another way to collect the information is to make use of a PHP function called phpinfo(). The phpinfo() function will query your (or your host’s) server and generate a report with a long list of data. Note: Remember to bookmark this page, because, in the future, a volunteer in the [WordPress Support Forum](https://wordpress.org/support/forums/) may ask you to use this method to get information to assist them in troubleshooting a question you asked on the Support Forum.

**Warning:** This file will contain some moderately sensitive information about your server that could help an attacker gain access to it. Make sure that you give the file an obscure filename and delete it as soon as you’re done.

In a text editor, copy and paste the following command:

```
<?php phpinfo(); ?>
```

Make sure there are no spaces before or after the command, just the command, and save the file as something obscure like sffdsajk234.php. It’s important to make the file difficult for hackers to find, because it will contain information that could help them compromise your server.

Upload the file to the root directory of your site. Then type in the address to the file in your browser:

```
https://example.com/sffdsajk234.php
```

The result will be several pages long and it will contain a ton of information. Though your data may be in a different order, for the most part, you just need the summary items that list things like this:

|                |                                                               |
| -------------- | ------------------------------------------------------------- |
| PHP            | Version 7.3.0                                                 |
| System         | Windows NT DESKTOP-LK01DAN 10.0 build 17763 (Windows 10) i586 |
| Build Date     | Dec 6 2018 01:51:18                                           |
| Server API     | Apache 2.0 Handler                                            |
| Apache Version | Apache/2.4.37 (Win32) OpenSSL/1.1.1a PHP/7.3.0                |

That’s it. Make sure you remember to delete the file once you’re done with it, because leaving it there could help hackers compromise your server.

## Using the command line {#using-the-command-line}

If you can connect to your server with SSH, you can also use these commands on most Linux servers:

```
# PHP version
php -v

# Apache version
apachectl -v

# Nginx version
nginx -v

# Operating system
uname -a
cat /etc/os-release

# MySQL or MariaDB server version (asks for the database user's password)
mysql -u username -p -e "SELECT VERSION();"
```

Replace `username` with your database username. You can find it in the `DB_USER` line of your `wp-config.php` file. If `DB_HOST` in `wp-config.php` is not `localhost`, also add `-h` with the host name, and `-P` with the port number if `DB_HOST` has one. For example, for `db.example.com:3307`, use `mysql -h db.example.com -P 3307 -u username -p -e "SELECT VERSION();"`. For `localhost` with a port, like `localhost:3307`, use `-h 127.0.0.1` instead, because the `mysql` client ignores the port for `localhost`. If `DB_HOST` ends with a socket path, like `localhost:/var/run/mysqld/mysqld.sock`, use `-S /var/run/mysqld/mysqld.sock` instead of `-h` and `-P`.

Some of these commands may not work on shared hosting, because the program is not installed or your user cannot run it. In that case, use Site Health or ask your hosting provider.

The `php` command on the command line can be a different PHP version from the one your website uses. To see the PHP version of your website, use Site Health or phpinfo().

If [WP-CLI](https://make.wordpress.org/cli/handbook/) is installed, `wp cli info` shows the operating system and the PHP version that WP-CLI uses. See [wp cli info](https://developer.wordpress.org/cli/commands/cli/info/).

## Information and Resources

- [PHP.net’s phpinfo Manual](https://www.php.net/phpinfo)
- [WordPress Environment PHP library](https://github.com/abelcallejo/wordpress-environment)

