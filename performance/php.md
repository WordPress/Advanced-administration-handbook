## PHP

PHP (PHP: Hypertext Preprocessor) is a popular programming language on the Internet. PHP turns dynamic content, like that in WordPress, into HTML, CSS, and JavaScript that web browsers can read. WordPress is written primarily in PHP, and a server must have PHP in order for WordPress to be able to run.

As PHP is an interpreted language, its version and configuration has a large impact on how well and whether WordPress will run.

### Version

When possible, PHP 7.4 or greater should be used to run WordPress. As of the writing of this document, PHP 7.4 is the officially supported version for WordPress while PHP 8.0 and 8.1 are "compatible with exceptions", and PHP 8.2 is on "beta support". PHP 8 is the only major version of PHP still receiving active development and support. The PHP group regularly retires support for older versions of PHP, and older versions are not guaranteed to be updated for security concerns.

At the same time, newer versions of PHP contain both security and performance improvements, while being accompanied by new features and bug fixes, which are not guaranteed to be backwards compatible. However, extreme care must be taken when upgrading the version of PHP. While WordPress is compatible with the latest releases of PHP, sites built to use older versions of PHP may not be compatible due to their included plugins and themes.

If upgrading to PHP 8 is not immediately possible, upgrading to PHP 7.4 should be done as soon as possible. While WordPress _may_ work with older versions of PHP, these versions have reached official End Of Life, and running outdated PHP installations **may expose your site to security vulnerabilities**.

You can find which PHP version is compatible with your WordPress version in the [PHP Compatibility and WordPress Versions](https://make.wordpress.org/core/handbook/references/php-compatibility-and-wordpress-versions/) page.

More information about the support versions of PHP can always be found [on PHP's supported versions page](https://www.php.net/supported-versions.php).

When upgrading PHP, it's a good practice to test sites for compatibility before upgrading. If you offer multiple environments, such as a staging and a production environment, PHP version should be configurable separately for each environments. This will allow users to test newer version of PHP in their non-production environment and resolve any issues before upgrading PHP version in the production environment.

There's a useful [WP-CLI command](https://github.com/danielbachhuber/php-compat-command) for performing a general compatibility check, but be aware that it is not 100% accurate.

### Configuration

PHP is primarily configured using a configuration file, `php.ini`, from which PHP reads all of its settings and configuration at runtime. This usually happens through CGI/FastCGI, or a process manager like PHP-FPM.

Some server environment may allow PHP configurations to be customized with other files like the `.htaccess` or `.user.ini` file. 

You can see detailed information about each of these directives [in the official PHP documentation](https://www.php.net/manual/en/ini.core.php).

#### Timeouts

There are several timeout settings on a system that limit different aspects of a request. When configuring your timeouts, it's important to select values that work well together. For example, it doesn't make sense to have a very high script execution timeout on your PHP service, if the web server (e.g. Apache) timeout is lower than that - in such case, if the request takes longer, it will be killed by the web server no matter your PHP timeout setting is.

Note that processes take different amount of time, depending on the server load, and those limitations are placed to ensure that your server functions properly. If you have high server load, processes may take longer to complete thus causing a cascade effect leading to even more server load. That's why it's a matter of balance between giving enough time for your scripts to be compiled and ensuring that you're within normal server loads.

The primary PHP timeout can be set with the [`max_execution_time`](https://www.php.net/manual/en/info.configuration.php#ini.max-execution-time) `php.ini` directive. This limits code execution, and not system library calls or MySQL queries, [except on Windows](https://www.php.net/manual/en/function.set-time-limit.php), where it does.

The maximum time allowed for data transfer from the web server to PHP is specified with the [`max_input_time`](https://www.php.net/manual/en/info.configuration.php#ini.max-input-time) `php.ini` directive. It is usually used to limit the amount of time allowed to upload files. It's important to note that the amount of time is separate from `max_execution_time`, and defines the amount of time between when the web server calls PHP and execution starts.

Note that these timeouts are often configured per server and you won't be able to modify them if you're on a shared hosting account. The best approach would be to contact your hosting company tech support and see if they can be modified to suit your needs.

#### Memory Limits

The maximum amount of memory that PHP is allowed to use per page render is specified with the [`memory limit`](https://www.php.net/manual/en/ini.core.php#ini.memory-limit) `php.ini` directive.

In addition to setting memory limits within PHP, WordPress has two memory configuration constants that can be changed in the **wp-config.php** file. WordPress will raise the PHP `memory_limit` to these values if it has permission to do so, but if the `php.ini` specifies higher amounts, WordPress will not lower the amount allowed.

The option `WP_MEMORY_LIMIT` declares the amount of memory WordPress should request for rendering the frontend of the website. WordPress default is 40 MB and WordPress MultiSite default is 64 MB.

```
define( 'WP_MEMORY_LIMIT', '128M' );
```

The option `WP_MAX_MEMORY_LIMIT` declares the amount of memory WordPress should request for rendering the backend of the website. WordPress default is 256 MB.

```
define( 'WP_MAX_MEMORY_LIMIT', '256M' );
```

Since the WordPress backend usually requires more memory, there's a separate setting for the amount, that can be set for logged in users. This is mainly required for media uploads. You can have it set higher than the front end limit to ensure your backend has all the resources it needs. Usually, `WP_MEMORY_LIMIT <= WP_MAX_MEMORY_LIMIT`.

#### File Upload Sizes

When uploading media files and other content to WordPress using the WordPress admin dashboard, WordPress uses PHP to process the uploads. PHP's configuration includes limits on the size of files that can be uploaded through PHP and on the size of requests that can be sent to the web server for processing. These will need to align with the server's timeouts, discussed above.

The limit on the size of individual file uploads can be configured using the [`upload_max_filesize`](https://www.php.net/manual/en/ini.core.php#ini.upload-max-filesize) `php.ini` directive.

The limit on the entire size of a request that can be sent from the web server to PHP for processing can be configured using the [`post_max_size`](https://www.php.net/manual/en/ini.core.php#ini.post-max-size) `php.ini` directive. The value for `post_max_size` must be greater than or equal to the value for `upload_max_filesize`. PHP will not process requests larger in size than the value for `post_max_size`.

Note that `post_max_size` applies to every PHP request and not only uploads, so it may become important to address separately if a site processes a large amount of other data included with the request.

Bear in mind that on shared hosting accounts, those limits are usually set on a server level and you may not be able to modify them or increase them above a certain value. In addition to that, different setups have different ways to modify the above mentioned values. Contact your hosting company tech support for additional assistance on that matter.

#### Replacing WordPress' Cron Triggers

The `wp-cron.php` script is responsible for causing certain tasks to be scheduled and executed automatically. Every time someone visits your website, `wp-cron.php` checks whether it is time to execute a job or not. Even though these checks are small and fast they consume time and produce load. For this reason, it's worth considering setting the [`DISABLE_WP_CRON` constant](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/#disable-cron-and-cron-timeout) and using an alternative method to trigger WordPress' cron system. Note, however, that the WordPress cron system is designed with performance in mind and requires minimal resources to operate so it's not mandatory to replace it unless you really need to do so.

## Changelog

- 2023-06-08: New page created.
