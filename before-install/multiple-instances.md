# Installing Multiple WordPress Instances

If you need multiple WordPress instances, there are three types of installations based on system architecture, or a combination of WordPress instances and databases:

1. WordPress Multisite Network: a single WordPress instance (with multiple sites created within the same WP instance) sharing a single database instance.
2. Single Database: multiple WordPress instances sharing a single database instance.
3. Multiple Databases: multiple WordPress instances with each instance using its own databases instance.

![](https://wordpress.org/documentation/files/2022/06/multisite_db_layout-1024x469.jpg)

Let's first look at the third type, multiple WordPress instances with multiple databases, because it has the same installation process as a single WordPress instance.

## Multiple WordPress Instances with Multiple Databases {#multiple-wordpress-instances-with-multiple-databases}

You'll need a separate [MySQL database](https://wordpress.org/documentation/article/wordpress-glossary/#mysql) for each instance you plan to install. If you have not yet created these, [basic instructions are found here](https://developer.wordpress.org/advanced-administration/before-install/howto-install/#step-2-create-the-database-and-a-user).

To make sure each WordPress instance connects to the right database you need to add those information to the [wp-config.php](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) file. The lines to change are the following:

```
define('DB_NAME', 'wordpress');    // The name of the database
define('DB_USER', 'username');     // Your MySQL username
define('DB_PASSWORD', 'password'); // The users password
define('DB_HOST', 'localhost' );  // The host of the database
```

`DB_NAME` is the name of the individual database created for that blog hosted on the `DB_HOST` MySQL server. If you are using different user logins for each database, edit `DB_USER` and `DB_PASSWORD` to reflect this as well.

Upload each [wp-config.php](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) file to its specific root/installation directory, and run the installation. See [Installing WordPress](https://developer.wordpress.org/advanced-administration/before-install/howto-install/) for more information.

## The Multisite Feature {#the-multisite-feature}

If you want multiple sites to use WordPress, you can use the multisite feature to create what is referred to as a _network_ of sites. The multisite feature involves installing a single WordPress instance and a single database.

The multisite feature appears to be simpler than other types of multiple WordPress installations, but there are some considerations and restrictions. Refer to the following documents for more detailed information:

* [Before You Create A Network](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/)
* [Create A Network](https://developer.wordpress.org/advanced-administration/multisite/create-network/)
* [Multisite Network Administration](https://developer.wordpress.org/advanced-administration/multisite/administration/)

## Multiple WordPress Instances with a Single Database {#multiple-wordpress-instances-with-a-single-database}

As with the multiple-database solution described above, the [wp-config.php](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) file will vary for each installation. In this case, however, only a single line is unique to each blog:

```
$table_prefix = 'wp_'; // example: 'wp_' or 'b2' or 'mylogin_' 
```

By default, WordPress assigns the table prefix `wp_` to its [MySQL database](https://wordpress.org/documentation/article/wordpress-glossary/#mysql) tables, but this prefix can be anything you choose. This allows you to create unique identifiers for each blog in your database. For example, let's say you have three blogs to set up, with the names _Main_, _Projects_, and _Test_. You should substitute the prefix `wp_` in each blog's  
[wp-config.php](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/):

**Main blog:**
```
$table_prefix = 'main_'; 
```

**Projects blog:**
```
$table_prefix = 'projects_'; 
```

**Test blog:**
```
$table_prefix = 'test_'; 
```

As noted, you may use a prefix of your own making. Those provided here are for example only.

Upload each [wp-config.php](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) file to its specific root/installation directory, and run the installation. See [Installing WordPress](https://developer.wordpress.org/advanced-administration/before-install/howto-install/) for more information.

For enhanced security you can also add multiple users to the same database and give each WordPress Instance their own MySQL user.

## Multiple Databases, Same Users {#multiple-databases-same-users}

You can use the same userbase for all your blogs on the same domain by defining the `CUSTOM_USER_TABLE` and optionally the `CUSTOM_USER_META_TABLE` constants to point to the same `wp_your_blog_users` and `wp_your_blog_usermeta` tables.  
See [Editing wp-config.php/Custom User and Usermeta Tables](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/#custom-user-and-usermeta-tables).

## Changelog


- 2023-02-17: WCAsia Contributor Day - Review and rework
- 2022-10-21: Original content from [Installing Multiple WordPress Instances](https://wordpress.org/support/article/installing-multiple-blogs/).

