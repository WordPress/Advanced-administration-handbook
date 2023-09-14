# Creating Database for WordPress

If you are installing WordPress on your own web server, follow the one of below instructions to create your WordPress database and user account.

## Using phpMyAdmin

If your web server has [phpMyAdmin](https://wordpress.org/documentation/article/wordpress-glossary/#phpMyAdmin) installed, you may follow these instructions to create your WordPress username and database. If you work on your own computer, on most Linux distributions you can install PhpMyAdmin automatically.

_**Note:** These instructions are written for phpMyAdmin 4.4; the phpMyAdmin user interface can vary slightly between versions._

If a database relating to WordPress does not already exist in the **Database** dropdown on the left, create one:

Choose a name for your WordPress database: 'wordpress' or 'blog' are good, but most hosting services (especially shared hosting) will require a name beginning with your username and an underscore, so, even if you work on your own computer, we advise that you check your hosting service requirements so that you can follow them on your own server and be able to transfer your database without modification. Enter the chosen database name in the **Create database** field and choose the best collation for your language and encoding. In most cases it's better to choose in the “utf8_” series and, if you don't find your language, to choose “utf8mb4_general_ci” (Reference: [\[1\]](https://make.wordpress.org/core/2015/04/02/the-utf8mb4-upgrade/)).

[![phpMyAdmin language encoding dropdown with utf8mb4_general_ci selected](https://i1.wp.com/wordpress.org/support/files/2018/11/phpMyAdmin_create_database_4.4.jpg?fit=688%2C411&ssl=1)](https://i1.wp.com/wordpress.org/support/files/2018/11/phpMyAdmin_create_database_4.4.jpg?fit=688%2C411&ssl=1)

phpMyAdmin language encoding drop down

Click the **phpMyAdmin** icon in the upper left to return to the main page, then click the **Users** tab. If a user relating to WordPress does not already exist in the list of users, create one:

[![phpMyAdmin Users Tab selected](https://wordpress.org/documentation/files/2018/11/users-768x500.jpg)](https://wordpress.org/documentation/files/2018/11/users-768x500.jpg)

phpMyAdmin Users Tab

1. Click **Add user**.
2. Choose a username for WordPress ('wordpress' is good) and enter it in the **User name** field. (Be sure **Use text field:** is selected from the dropdown.)
3. Choose a secure password (ideally containing a combination of upper- and lower-case letters, numbers, and symbols), and enter it in the **Password** field. (Be sure **Use text field:** is selected from the dropdown.) Re-enter the password in the **Re-type**field.
4. Write down the username and password you chose.
5. Leave all options under **Global privileges** at their defaults.
6. Click **Go**.
7. Return to the **Users** screen and click the **Edit privileges** icon on the user you've just created for WordPress.
8. In the **Database-specific privileges** section, select the database you've just created for WordPress under the **Add privileges to the following database** dropdown, and click **Go**.
9. The page will refresh with privileges for that database. Click **Check All** to select all privileges, and click **Go**.
10. On the resulting page, make note of the host name listed after **Server:** at the top of the page. (This will usually be **localhost**.)

[![Databaser Server selected showing 'localhost'](https://i1.wp.com/wordpress.org/support/files/2018/11/phpMyAdmin_server_info_4.4.jpg?fit=682%2C107&ssl=1)](https://i1.wp.com/wordpress.org/support/files/2018/11/phpMyAdmin_server_info_4.4.jpg?fit=682%2C107&ssl=1)

## Using the MySQL Client

You can create MySQL users and databases quickly and easily by running mysql from the shell. The syntax is shown below and the dollar sign is the command prompt:

```
$ mysql -u adminusername -p  
Enter password:  
Welcome to the MySQL monitor. Commands end with ; or \\g.  
Your MySQL connection id is 5340 to server version: 3.23.54  
  
Type 'help;' or '\\h' for help. Type '\\c' to clear the buffer.  
  
mysql> CREATE DATABASE databasename;  
Query OK, 1 row affected (0.00 sec)

mysql> CREATE USER "wordpressusername"@"hostname" IDENTIFIED BY "password";
mysql> GRANT ALL PRIVILEGES ON databasename.* TO "wordpressusername"@"hostname";
Query OK, 0 rows affected (0.00 sec)

mysql> FLUSH PRIVILEGES;  
Query OK, 0 rows affected (0.01 sec)   
  
mysql> EXIT  
Bye  
```

The example shows:

* That root is also the _adminusername_. It is a safer practice to choose a so-called “mortal” account as your mysql admin, so that you are not entering the command “mysql” as the root user on your system. (Any time you can avoid doing work as root you decrease your chance of being exploited.) The name you use depends on the name you assigned as the database administrator using mysqladmin.
* wordpress or blog are good values for _databasename_.
* wordpress is a good value for _wordpressusername_ but you should realize that, since it is used here, the entire world will know it, too.
* _hostname_ will usually be localhost. If you don't know what this value should be, check with your system administrator if you are not the admin for your WordPress host. If you are the system admin, consider using a non-root account to administer your database.
* _password_ should be a difficult-to-guess password, ideally containing a combination of upper- and lower-case letters, numbers, and symbols. One good way of avoiding the use of a word found in a dictionary is to use the first letter of each word in a phrase that you find easy to remember.

If you need to write these values somewhere, avoid writing them in the system that contains the things protected by them. You need to remember the value used for _databasename_, _wordpressusername_, _hostname_, and _password_. Of course, since they are already (or will be shortly) in your wp-config.php file, there is no need to put them somewhere else, too.

## Using Plesk

If your hosting provider supplies the [Plesk](http://www.plesk.com/) hosting control panel and you want to install WordPress manually, follow the instructions below to create a database:

1. Log in to Plesk.
2. Click **Databases** in the Custom Website area of your website on the Websites & Domains page:  

[![Plesk panel highlighting the Custom Website box with the databases button highlighted](https://wordpress.org/documentation/files/2018/11/plesk-db-768x558.png)](https://wordpress.org/documentation/files/2018/11/plesk-db-768x558.png)

Plesk custom website databases

3. Click **Add New Database**, change database name if you want, create database user by providing credentials and click **OK**. You're done!

## Using cPanel

If your hosting provider supplies the [cPanel](https://wordpress.org/documentation/article/wordpress-glossary/#cPanel) hosting control panel, you may follow these simple instructions to create your WordPress username and database. A more complete set of instructions for using cPanel to create the database and user can be found in [Using cPanel](https://wordpress.org/documentation/article/using-cpanel/).

1. Log in to your [cPanel](https://wordpress.org/documentation/article/wordpress-glossary/#cPanel).
2. Click **MySQL Database Wizard** icon under the Databases section.
3. In **Step 1. Create a Database** enter the database name and click Next Step.
4. In **Step 2. Create Database Users** enter the database user name and the password. Make sure to use a strong password. Click Create User.
5. In **Step 3. Add User to Database** click the All Privileges checkbox and click Next Step.
6. In **Step 4. Complete the task** note the database name and user. Write down the values of _hostname_, _username_, _databasename_, and the password you chose. (Note that _hostname_ will usually be **localhost**.)

## Using DirectAdmin

If you're a regular User of a single-site webhosting account, you can log in normally. Then click **MySQL Management**. (If this is not readily visible, perhaps your host needs to modify your “package” to activate MySQL.) Then follow part “c” below.

Reseller accounts Admin accounts may need to click **User Level**. They must first log in as Reseller if the relevant domain is a Reseller's primary domain… or log in as a User if the domain is not a Reseller's primary domain. If it's the Reseller's primary domain, then when logged in as Reseller, simply click **User Level**. However if the relevant domain is not the Reseller's primary domain, then you must log in as a User. Then click **MySQL Management**. (If not readily visible, perhaps you need to return to the Reseller or Admin level, and modify the “Manage user package” or “Manage Reseller package” to enable MySQL.)

In MySQL Management, click on the small words: **Create new database**. Here you are asked to submit two suffixes for the database and its username. For maximum security, use two different sets of 4-6 random characters. Then the password field has a Random button that generates an 8-character password. You may also add more characters to the password for maximum security. Click **Create**. The next screen will summarize the database, username, password and hostname. Be sure to copy and paste these into a text file for future reference.

## Changelog

- 2022-09-11: Original content from [Creating Database for WordPress](https://wordpress.org/documentation/article/creating-database-for-wordpress/).
