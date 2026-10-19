# Creating Database for WordPress

If you are installing WordPress on your own web server, follow one of the instructions below to create your WordPress database and user account.

## Character set and collation

New WordPress installations default to the `utf8mb4` character set. By default, WordPress creates its own tables with a `utf8mb4` collation when it installs, whatever the default collation of the database is. On MySQL 8.4 and MariaDB 11.4, for example, WordPress creates its tables with `utf8mb4_unicode_520_ci`.

Tables created without their own collation, for example by some plugins, use the default collation of the database. So if you are asked to choose a collation when you create the database, choose one that starts with `utf8mb4`. If the default is already a `utf8mb4` collation, such as `utf8mb4_0900_ai_ci` on MySQL 8, keep it. Avoid `latin1` and `utf8mb3` (called `utf8` on older servers) collations. (Reference: [The utf8mb4 Upgrade](https://make.wordpress.org/core/2015/04/02/the-utf8mb4-upgrade/).)

## Using phpMyAdmin

If your web server has [phpMyAdmin](https://wordpress.org/documentation/article/wordpress-glossary/#phpmyadmin) installed, you may follow these instructions to create your WordPress username and database. If you work on your own computer, most Linux distributions can install phpMyAdmin from their package manager.

_**Note:** These instructions are written for phpMyAdmin 5.2; the phpMyAdmin user interface can vary slightly between versions._

To create the database:

1. Click the **Databases** tab.
2. Under **Create database**, enter a name for your WordPress database. 'wordpress' or 'blog' are good, but most hosting services (especially shared hosting) will require a name beginning with your username and an underscore. So, even if you work on your own computer, we advise that you check your hosting service requirements so that you can follow them on your own server and be able to transfer your database without modification.
3. In the **Collation** dropdown, keep the default if it starts with `utf8mb4`; otherwise, choose a `utf8mb4` collation (see [Character set and collation](#character-set-and-collation)). Click **Create**.

[![The Create database form on the Databases tab in phpMyAdmin, with 'wordpress' as the database name and utf8mb4_0900_ai_ci as the collation](https://raw.githubusercontent.com/WordPress/Advanced-administration-handbook/main/assets/phpmyadmin-create-database.png)](https://raw.githubusercontent.com/WordPress/Advanced-administration-handbook/main/assets/phpmyadmin-create-database.png)

phpMyAdmin Create database form

To create the WordPress user and give it access to the database:

1. With the new database selected, click the **Privileges** tab, then click **Add user account**.

[![The Privileges tab of the wordpress database in phpMyAdmin, listing the users who have access to it and the Add user account link](https://raw.githubusercontent.com/WordPress/Advanced-administration-handbook/main/assets/phpmyadmin-database-privileges.png)](https://raw.githubusercontent.com/WordPress/Advanced-administration-handbook/main/assets/phpmyadmin-database-privileges.png)

phpMyAdmin database Privileges tab

2. Choose a username for WordPress ('wordpress' is good) and enter it in the **User name** field.
3. In **Host name**, choose **Local** if WordPress runs on the same server as the database. Otherwise, check with your hosting provider or system administrator.
4. Choose a secure password (ideally containing a combination of upper- and lower-case letters, numbers, and symbols), and enter it in the **Password** and **Re-type** fields, or click **Generate** to create one.
5. Write down the username and password you chose.
6. Under **Database for user account**, make sure **Grant all privileges on database** is checked for your WordPress database.
7. Leave all options under **Global privileges** unchecked.
8. Click **Go**.

[![The Add user account form in phpMyAdmin, with the Grant all privileges on database wordpress option checked under Database for user account](https://raw.githubusercontent.com/WordPress/Advanced-administration-handbook/main/assets/phpmyadmin-add-user-account.png)](https://raw.githubusercontent.com/WordPress/Advanced-administration-handbook/main/assets/phpmyadmin-add-user-account.png)

phpMyAdmin Add user account form

Make a note of the host name listed after **Server:** at the top of the page. (This will usually be **localhost**.)

## Using the MySQL Client

You can create MySQL users and databases quickly and easily by running mysql from the shell. The syntax is shown below and the dollar sign is the command prompt:

```
$ mysql -u adminusername -p
Enter password:
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 10
Server version: 8.4.11 MySQL Community Server - GPL

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> CREATE DATABASE databasename;
Query OK, 1 row affected (0.00 sec)

mysql> CREATE USER 'wordpressusername'@'hostname' IDENTIFIED BY 'password';
Query OK, 0 rows affected (0.00 sec)

mysql> GRANT ALL PRIVILEGES ON databasename.* TO 'wordpressusername'@'hostname';
Query OK, 0 rows affected (0.00 sec)

mysql> EXIT
Bye
```

MySQL 8 no longer creates a user through `GRANT`, so run `CREATE USER` first, as shown. Older guides use `GRANT ... IDENTIFIED BY`, which MySQL 8 rejects; MariaDB still accepts it.

On MariaDB, you can run the same statements with the `mariadb` client; its welcome message is slightly different. You don't need to run `FLUSH PRIVILEGES` after `CREATE USER` and `GRANT`: the new user can log in right away.

The example shows:

* _adminusername_ is a database account that can create databases and users. It is a safer practice not to use the root account of your system for this. (Any time you can avoid doing work as root you decrease your chance of being exploited.)
* wordpress or blog are good values for _databasename_.
* wordpress is a good value for _wordpressusername_ but you should realize that, since it is used here, the entire world will know it, too.
* _hostname_ will usually be localhost. If you don't know what this value should be, check with your system administrator if you are not the admin for your WordPress host. If you are the system admin, consider using a non-root account to administer your database.
* _password_ should be a difficult-to-guess password, ideally containing a combination of upper- and lower-case letters, numbers, and symbols. One good way of avoiding the use of a word found in a dictionary is to use the first letter of each word in a phrase that you find easy to remember.

If you need to write these values somewhere, avoid writing them in the system that contains the things protected by them. You need to remember the value used for _databasename_, _wordpressusername_, _hostname_, and _password_. Of course, since they are already (or will be shortly) in your wp-config.php file, there is no need to put them somewhere else, too.

## Using Plesk

If your hosting provider supplies the [Plesk](https://www.plesk.com/) hosting control panel and you want to install WordPress manually, follow the instructions below to create a database:

1. Log in to Plesk.
2. Go to **Domains**, select your domain, and click **Databases**.
3. Click **Add Database**, enter the database details, and click **OK**.

The steps vary slightly depending on the Plesk view. For more details, see [How to create a database in Plesk](https://support.plesk.com/hc/en-us/articles/12377341716759-How-to-create-a-database-in-Plesk) in the Plesk documentation.

## Using cPanel

If your hosting provider supplies the [cPanel](https://wordpress.org/documentation/article/wordpress-glossary/#cpanel) hosting control panel, you may follow these simple instructions to create your WordPress username and database. More about hosting control panels can be found in [Control Panels](https://developer.wordpress.org/advanced-administration/server/control-panel/).

1. Log in to your [cPanel](https://wordpress.org/documentation/article/wordpress-glossary/#cpanel).
2. Click **Database Wizard** under the Databases section. (cPanel & WHM renamed it from **MySQL Database Wizard** in version 120.)
3. In the **New Database** field, enter the database name and click **Next Step**.
4. Enter the database user name and a strong password (or click **Password Generator**), and click **Create User**.
5. Select **ALL PRIVILEGES** and click **Next Step**.
6. Write down the values of _hostname_, _username_, _databasename_, and the password you chose. (Note that _hostname_ will usually be **localhost**.)

Your hosting provider may add a prefix, such as your account name and an underscore, to the database and user names. Use the full names in your `wp-config.php` file.

For more details, see [MySQL Database Wizard](https://docs.cpanel.net/cpanel/databases/mysql-database-wizard/) in the cPanel documentation.

## Using DirectAdmin

If you're a regular User of a single-site webhosting account, you can log in normally. Then click **MySQL Management**. (If this is not readily visible, perhaps your host needs to modify your “package” to activate MySQL.) Then follow the last paragraph of this section.

Reseller and Admin accounts may need to click **User Level**. They must first log in as Reseller if the relevant domain is a Reseller's primary domain… or log in as a User if the domain is not a Reseller's primary domain. If it's the Reseller's primary domain, then when logged in as Reseller, simply click **User Level**. However if the relevant domain is not the Reseller's primary domain, then you must log in as a User. Then click **MySQL Management**. (If not readily visible, perhaps you need to return to the Reseller or Admin level, and modify the “Manage user package” or “Manage Reseller package” to enable MySQL.)

In MySQL Management, click on the small words: **Create new database**. Here you are asked to submit two suffixes for the database and its username. For maximum security, use two different sets of 4-6 random characters. Then the password field has a Random button that generates an 8-character password. You may also add more characters to the password for maximum security. Click **Create**. The next screen will summarize the database, username, password and hostname. Be sure to copy and paste these into a text file for future reference.
