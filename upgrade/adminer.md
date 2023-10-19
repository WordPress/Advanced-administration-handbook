# Adminer

## What is Adminer?

[Adminer](https://www.adminer.org/), formerly known as phpMinAdmin, is a full-featured database management tool written in PHP. Unlike [phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/), which is a multi-file solution, Adminer consists of a single file that's ready for deployment to the target server. It is available for various databases, including MySQL, MariaDB, PostgreSQL, SQLite, MS SQL, Oracle, Elasticsearch, MongoDB, and others. Since WordPress stores all its data in the MySQL database, Adminer offers a "raw" view of the data, tables, and fields within this database.

## Advantages of Adminer

- **Simple Interface**: Adminer offers a clean and user-friendly interface, unlike some other database management tools.
- **Direct Data Manipulation**: Useful for direct database edits, especially if WordPress stopped working.
- **Lightweight**: Being a single PHP file, it is easy to upload/install and use.

## What is it good for?

Adminer is beneficial for table maintenance, data backups, and direct database edits. Occasionally, in the [Support Forums](https://wordpress.org/support/welcome/#asking-for-support), contributors share beneficial SQL queries that can be executed using tools like Adminer.

## Where can I get it?

Many hosting control panels, like cPanel and Plesk, come with [phpMyAdmin](https://wordpress.org/documentation/article/phpmyadmin/) pre-installed. If unavailable, users can consult with their hosting provider to get database access.

For those who prefer to use Adminer, it can be downloaded from the [Adminer project page](https://www.adminer.org/).

## Installing Adminer

1. Download the latest version of Adminer from the [Adminer download page](https://www.adminer.org/en/#download).
2. Upload the Adminer PHP file into the WordPress root directory (where the `wp-config.php` file is located) using an FTP tool like [FileZilla](https://wordpress.org/documentation/article/using-filezilla/).
3. After uploading the Adminer PHP file to the WordPress root directory, you can access it from your browser by adding the file name to the URL, e.g., `https://example.com/adminer-4.8.1.php`.
4. The database login credentials must be manually filled and can be obtained from the `wp-config.php` file.

## Installing Adminer as a WordPress Plugin

Adminer might also be available as a plugin in the [WordPress plugin repository](https://wordpress.org/plugins/search/database+adminer/). When installing  Adminer as a WordPress plugin, the database login credentials may be automatically inserted from the `wp-config.php` file.

## Security Precautions

To prevent unauthorized access, please ensure that Adminer is either removed or protected after use, especially if it can be accessed publicly. One way to protect it is by restricting access using the `.htaccess` file. If you're unfamiliar with `.htaccess` file restrictions, consider seeking [guidance on hardening WordPress](https://wordpress.org/documentation/article/hardening-wordpress/) or removing Adminer after use.

## Changelog

- 2023-10-19: Include content from [phpMyAdmin](https://wordpress.org/documentation/article/phpmyadmin/).
- 2023-10-19: Include content from [FileZilla](https://wordpress.org/documentation/article/using-filezilla/).
- 2023-10-19: Include content from [Hardening WordPress](https://wordpress.org/documentation/article/hardening-wordpress/)
