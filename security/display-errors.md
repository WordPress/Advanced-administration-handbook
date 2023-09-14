# Display Errors

## What is display_errors?

`display_errors` is a directive found in PHP, found in the php.ini file. With this option, PHP determines whether or not errors should be printed directly on the page.

## Why does display_errors need to be disabled?

According to [PHP documentation](https://www.php.net/manual/en/errorfunc.configuration.php#ini.display-errors), it should never be enabled on production environments or live sites.

While `display_errors` may provide useful information in debugging scenarios, there are potential security issues that need to be taken into account if it is activated. [See OWASP article about improper error handling.](https://owasp.org/www-community/Improper_Error_Handling)

However, some hosting companies have `display_errors` enabled by default. This may be due to a misconfiguration, such as trying to disable it by using a configuration that does not work in hosting environments where for example PHP is not running as a module, but with PHP FastCGI Process Manager (PHP-FPM).

## How to disable display_errors

Check your hosting control panel to disable `display_errors` or reach out to your hosting provider.

If your PHP is running as Apache module, you may be able to disable display_errors with the following .htaccess configuration:

`<IfModule mod_php8.c> php_flag display_errors off </IfModule>`

If your server uses FastCGI/PHP-FPM, it may be possible disable the display_errors by ensuring that a .user.ini file with the following content:

`display_errors = 0`

If these examples do not work for you, or if you need more instructions, please reach out to your hosting provider.

## Changelog

- 2023-09-14: Setup, and Adding text.
