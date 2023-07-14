# Finding Server Info

What version of [PHP](https://wordpress.org/documentation/article/glossary#php) are you using? What server software is your site host using? What version of [MySQL](https://wordpress.org/documentation/article/glossary#mysql) do you have? What operating system does your site host use?

![php73win_phpinfo](https://user-images.githubusercontent.com/1508963/201365720-3a13ccab-c44c-43f2-8326-e3a997c5acfa.jpg)

ALT= Top of PHP Info test file results

These are questions often asked by WordPress users as they prepare to [install WordPress](https://developer.wordpress.org/advanced-administration/before-install/howto-install/). Other times, these questions are asked while troubleshooting a problem with a WordPress installation. But don’t spend at lot of time searching your site, or your host’s site, for the answers—there’s a very easy way to get that information.

The easiest way to collect the information is to make use of a PHP function called phpinfo(). The phpinfo() function will query your (or your host’s) server and generate a report with a long list of data. Note: Remember to bookmark this page, because, in the future, a volunteer in the [WordPress Support Forum](https://www.wordpress.org/support/forums/) may ask you to use this method to get information to assist them in troubleshooting a question you asked on the Support Forum.

**Warning:** This file will contain some moderately sensitive information about your server that could help an attacker gain access to it. Make sure that you give the file an obscure filename and delete it as soon as you’re done.

In a text editor, copy and paste the following command:

```
<?php phpinfo(); ?>
```

Make sure there are no spaces before or after the command, just the command, and save the file as something obscure like sffdsajk234.php. It’s important to make the file difficult for hackers to file, because it will contain information that could help them compromise your server.

Upload the file to the root directory of your site. Then type in the address to the file in your browser:

```
http://example.com/sffdsajk234.php
```

The result will be several pages long and it will contain a ton of information. Though your data may be in a different order, for the most part, you just need the summary items that lists things like this:

|                |                                                               |
| -------------- | ------------------------------------------------------------- |
| PHP            | Version 7.3.0                                                 |
| System         | Windows NT DESKTOP-LK01DAN 10.0 build 17763 (Windows 10) i586 |
| Build Date     | Dec 6 2018 01:51:18                                           |
| Server API     | Apache 2.0 Handler                                            |
| Apache Version | Apache/2.4.37 (Win32) OpenSSL/1.1.1a PHP/7.3.0                |

That’s it. Make sure you remember to delete the file once you’re done with it, because leaving it there could help hackers compromise your server.

## Information and Resources

- [PHP.net’s phpinfo Manual](http://us3.php.net/phpinfo)
- [Zend’s PHP Manual on phpinfo](http://www.zend.com/manual/function.phpinfo.php)
- [WordPress Environment PHP library](https://github.com/abelcallejo/wordpress-environment)

## Changelog

- 2022-11-11: Original content from [Finding Server Info](https://wordpress.org/documentation/article/finding-server-info/).
