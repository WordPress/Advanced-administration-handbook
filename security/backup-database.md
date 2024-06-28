## Backing Up Your Database

> It is strongly recommended that you backup your database at regular intervals and before an upgrade.

[Restoring your database from backup](https://developer.wordpress.org/advanced-administration/security/backup/) is then possible if something goes wrong. 

**NOTE:** Below steps backup core WordPress database that include all your posts, pages and comments, but DO NOT backup the files and folders such as images, theme files on the server. For whole WordPress site backup, refer [WordPress Backups](https://developer.wordpress.org/advanced-administration/security/backup/).

### Backup using cPanel X {#backup-using-cpanel-x}

cPanel is a popular control panel used by many web hosts. The backup feature can be used to backup your MySQL database. Do not generate a full backup, as these are strictly for archival purposes and cannot be restored via cPanel. Look for 'Download a MySQL Database Backup' and click the name of the database. A `*.gz` file will be downloaded to your local drive.

There is no need to unzip this file to restore it. Using the same cPanel program, browse to the gz file and upload it. Once the upload is complete, the bottom of the browser will indicate dump complete. If you are uploading to a new host, you will need to recreate the database user along with the matching password. If you change the password, make the corresponding change in the wp-config.php file.

### Using phpMyAdmin {#using-phpmyadmin}

[phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/) is the name of the program used to manipulate your database.

Information below has been tried and tested using phpMyAdmin version 4.4.13 connects to MySQL version 5.6.28 running on Linux.

[![phpmyadmin_top](https://wordpress.org/documentation/files/2018/11/phpmyadmin_top.jpg)](https://wordpress.org/documentation/files/2018/11/phpmyadmin_top.jpg)

#### Quick backup process {#quick-backup-process}

When you backup all tables in the WordPress database without compression, you can use simple method. To restore this backup, your new database should not have any tables.

1. Log into phpMyAdmin on your server
2. From the left side window, select your WordPress database. In this example, the name of database is "wp".
3. The right side window will show you all the tables inside your WordPress database. Click the 'Export' tab on the top set of tabs.

[![](https://wordpress.org/documentation/files/2018/11/phpmyadmin_dbtop.jpg)](https://wordpress.org/documentation/files/2018/11/phpmyadmin_dbtop.jpg)

4. Ensure that the Quick option is selected, and click 'Go' and you should be prompted for a file to download. Save the file to your computer. Depending on the database size, this may take a few moments.

[![phpmyadmin_quick_export](https://wordpress.org/documentation/files/2018/11/phpmyadmin_quick_export.jpg)](https://wordpress.org/documentation/files/2018/11/phpmyadmin_quick_export.jpg)

#### Custom backup process {#custom-backup-process}

If you want to change default behavior, select Custom backup.  In above Step 4, select Custom option. Detailed options are displayed.

[![phpmyadmin_custom_export](https://wordpress.org/documentation/files/2018/11/phpmyadmin_custom_export.jpg)](https://wordpress.org/documentation/files/2018/11/phpmyadmin_custom_export.jpg)

##### The Table section {#the-table-section}

All the tables in the database are selected. If you have other programs that use the database, then choose only those tables that correspond to your WordPress install. They will be the ones with that start with "wp_" or whatever 'table_prefix' you specified in your 'wp-config.php' file.

If you only have your WordPress blog installed, leave it as is (or click 'Select All' if you changed the selection)

##### The Output section {#the-output-section}

Select 'zipped' or 'gzipped' from Compression box to compress the data.  

[![phpmyadmin_export_output](https://wordpress.org/documentation/files/2018/11/phpmyadmin_export_output.jpg)](https://wordpress.org/documentation/files/2018/11/phpmyadmin_export_output.jpg)

##### The Format section {#the-format-section}

Ensure that the SQL is selected. Unlike CSV or other data formats, this option exports a sequence of SQL commands.

In the Format-specific options section, leave options as they are.

[![phpmyadmin_export_formatspecific](https://wordpress.org/documentation/files/2018/11/phpmyadmin_export_formatspecific.jpg)](https://wordpress.org/documentation/files/2018/11/phpmyadmin_export_formatspecific.jpg)

##### The Object creation options section {#the-object-creation-options-section}

Select Add DROP TABLE / VIEW / PROCEDURE / FUNCTION / EVENT / TRIGGER statement. Before table creation on target database, it will call DROP statement to delete the old existing table if it exist.

[![phpmyadmin_export_object](https://wordpress.org/documentation/files/2018/11/phpmyadmin_export_object.jpg)](https://wordpress.org/documentation/files/2018/11/phpmyadmin_export_object.jpg)

### The Data creation options section {#the-data-creation-options-section}

Leave options as they are.

[![phpmyadmin_export_data](https://wordpress.org/documentation/files/2018/11/phpmyadmin_export_data.jpg)](https://wordpress.org/documentation/files/2018/11/phpmyadmin_export_data.jpg)

Now click 'Go' at the bottom of the window and you should be prompted for a file to download. Save the file to your computer. Depending on the database size, this may take a few moments.

**Remember** – you have NOT backed up the files and folders – such as images – but all your posts and comments are now safe.

### Using Straight MySQL/MariaDB Commands {#using-straight-mysql-mariadb-commands}

phpMyAdmin cannot handle large databases so using straight MySQL/MariaDB code will help.

Change your directory to the directory you want to export backup to:

```
user@linux:~> cd files/blog
user@linux:~/files/blog>
```

Use the `mysqldump` command with your MySQL server name, user name and database name. It prompts you to input password (For help, try: `man mysqldump`).

**To backup all database tables**

```
mysqldump --add-drop-table -h mysql_hostserver -u mysql_username -p mysql_databasename﻿
```

**To backup only certain tables from the database**

```
mysqldump --add-drop-table -h mysql_hostserver -u mysql_username -p mysql_databasename﻿
```

Example:

```
user@linux:~/files/blog> mysqldump --add-drop-table -h db01.example.net -u dbocodex -p wp > blog.bak.sql
Enter password: (type password)
```

**Use bzip2 to compress the backup file**

```
user@linux:~/files/blog> bzip2 blog.bak.sql
```

You can do the same thing that above two commands do in one line:

```
user@linux:~/files/blog> mysqldump --add-drop-table -h db01.example.net -u dbocodex -p wp | bzip2 -c > blog.bak.sql.bz2
Enter password: (type password)
```

The `bzip2 -c` after the `|` (pipe) means the backup is compressed on the fly, and the `> blog.bak.sql.bz2` sends the bzip output to a file named `blog.bak.sql.bz2`.

Despite bzip2 being able to compress most files more effectively than the older compression algorithms (.Z, .zip, .gz), it is [considerably slower](https://en.wikipedia.org/wiki/Bzip2) (compression and decompression). If you have a large database to backup, gzip is a faster option to use.

```
user@linux:~/files/blog> mysqldump --add-drop-table -h db01.example.net -u dbocodex -p wp | gzip > blog.bak.sql.gz
```

### Using MySQL Workbench {#using-mysql-workbench}

[MySQL Workbench](https://dev.mysql.com/downloads/workbench/) (formerly known as My SQL Administrator) is a program for performing administrative operations, such as configuring your MySQL server, monitoring its status and performance, starting and stopping it, managing users and connections, performing backups, restoring backups and a number of other administrative tasks.

You can perform most of those tasks using a command line interface such as that provided by [mysqladmin](https://dev.mysql.com/doc/refman/8.0/en/mysqladmin.html) or [mysql](https://dev.mysql.com/doc/refman/8.0/en/mysql.html), but MySQL Workbench is advantageous in the following respects:

* Its graphical user interface makes it more intuitive to use.
* It provides a better overview of the settings that are crucial for the performance, reliability, and security of your MySQL servers.
* It displays performance indicators graphically, thus making it easier to determine and tune server settings.
* It is available for Linux, Windows and MacOS X, and allows a remote client to backup the database across platforms. As long as you have access to the MySQL databases on the remote server, you can backup your data to wherever you have write access.
* There is no limit to the size of the database to be backed up as there is with phpMyAdmin.

Information below has been tried and tested using MySQL Workbench version 6.3.6 connects to MySQL version 5.6.28 running on Linux.

[![mysql_workbench_top](https://wordpress.org/documentation/files/2018/11/mysql_workbench_top.jpg)](https://wordpress.org/documentation/files/2018/11/mysql_workbench_top.jpg)

#### Backing Up the Database {#backing-up-the-database}

This assumes you have already installed MySQL Workbench and set it up so that you can login to the MySQL Database Server either locally or remotely. Refer to the documentation that comes with the installation package of MySQL Workbench for your platform for installation instructions or [online document](https://dev.mysql.com/doc/workbench/en/).

1. Launch the MySQL Workbench
2. Click your database instance if it is displayed on the top page. Or, Click Database -> Connect Database from top menu, enter required information and Click OK.
3. Click Data Export in left side window.

[![mysql_workbench_export](https://wordpress.org/documentation/files/2018/11/mysql_workbench_export.jpg)](https://wordpress.org/documentation/files/2018/11/mysql_workbench_export.jpg)

1. Select your WordPress databases that you want to backup.
2. Specify target directory on Export Options. You need write permissions in the directory to which you are writing the backup.
3. Click Start Export on the lower right of the window.

[![mysql_workbench_export2](https://wordpress.org/documentation/files/2018/11/mysql_workbench_export2.jpg)](https://wordpress.org/documentation/files/2018/11/mysql_workbench_export2.jpg)

#### Restoring From a Backup {#restoring-from-a-backup}

1. Launch the MySQL Workbench
2. Click your database instance if it is displayed on the top page. Or, Click Database -> Connect Database, and Click OK.
3. Click Data Import/Restore in left side window.
4. Specify folder where you have backup files. Click "…" at the right of Import from Dump Project Folder, select backup folder, and click Open.
5. Click Start Import on the lower right of the window. The database restore will commence.

[![mysql_workbench_import](https://wordpress.org/documentation/files/2018/11/mysql_workbench_import.jpg)](https://wordpress.org/documentation/files/2018/11/mysql_workbench_import.jpg)

### MySQL GUI Tools {#mysql-gui-tools}

In addition to MySQL Workbench, there are many GUI tools that let you backup (export) your database.

| Name | OS (Paid edition) | OS (Free edition) | |
|---|---|---|
| [MySQL Workbench](http://www.mysql.com/products/workbench/) | Windows/Mac/Linux | Windows/Mac/Linux | See [above](https://developer.wordpress.org/advanced-administration/security/backup/database/#Using_MySQL_Workbench) |
| [EMS SQL Management Studio for MySQL](http://sqlmanager.net/en/products/studio/mysql) | Windows | | |
| [Aqua Data Studio](http://www.aquafold.com/) | Windows/Mac/Linux | Windows/Mac/Linux (14 days trial) | Available in 9 languages |
| [Navicat for MySQL](https://www.navicat.com/en/products/navicat-for-mysql) | Windows/Mac/Linux | Windows/Mac/Linux (14 days trial) | Available in 8 languages |
| [SQLyog](http://www.webyog.com/en/) | Windows | | |
| [Toad for MySQL](https://www.toadworld.com/) | | Windows | |
| [HeidiSQL](http://www.heidisql.com/) | | Windows | |
| [Sequel Pro](http://sequelpro.com/) | Mac | CocoaMySQL successor | |
| [Querious](http://www.araelium.com/querious/) | | Mac | |

### Using WordPress Database Backup Plugin {#using-wordpress-database-backup-plugin}

You can find plugins that can help you back up your database in the [WordPress Plugin Directory](https://wordpress.org/plugins/search/database+backup/).

The instructions below are for the plugin called [WP-DB-Backup:](https://wordpress.org/plugins/wp-db-backup/)

#### Installation {#installation}

1. Search for "WP-DB-Backup" on [Administration](https://wordpress.org/documentation/article/administration-screens/) > [Plugins](https://wordpress.org/documentation/article/administration-screens/#plugins-add-functionality-to-your-blog) > [Add New](https://wordpress.org/documentation/article/administration-screens/#add-new-plugins).
2. Click Install Now.
3. Activate the plugin.

#### Backing up {#backing-up}

1. Navigate to [Administration](https://wordpress.org/documentation/article/administration-screens/) > [Tools](https://wordpress.org/documentation/article/administration-screens/#tools-managing-your-blog) > Backup
2. Core WordPress tables will always be backed up. Select some options from Tables section.

[![wp-db-backup_table](https://wordpress.org/documentation/files/2018/11/wp-db-backup_table.jpg)](https://wordpress.org/documentation/files/2018/11/wp-db-backup_table.jpg)

3. Select the Backup Options; the backup can be downloaded, or emailed.

4. Finally, click on the Backup Now! button to actually perform the backup. You can also schedule regular backups.

[![wp-db-backup_settings](https://wordpress.org/documentation/files/2018/11/wp-db-backup_settings.jpg)](https://wordpress.org/documentation/files/2018/11/wp-db-backup_settings.jpg)

#### Restoring the Data {#restoring-the-data}

The file created is a standard SQL file. If you want information about how to upload that file, look at [Restoring Your Database From Backup](https://developer.wordpress.org/advanced-administration/security/backup/).

### More Resources {#more-resources}

* [Backup Plugins on the official WordPress.org repository](https://wordpress.org/plugins/search.php?q=backup)
* [WordPress Backups](https://developer.wordpress.org/advanced-administration/security/backup/)

### External Resources {#external-resources}

* [How to Schedule Daily Backup of WordPress Database](https://www.narga.net/schedule-backup-wordpress-database/)

## Restoring Your Database From Backup

### Using phpMyAdmin {#using-phpmyadmin}

[phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/) is a program used to manipulate databases remotely through a web interface. A good hosting package will have this included. For information on backing up your WordPress database, see [Backing Up Your Database](https://developer.wordpress.org/advanced-administration/security/backup/database/).

Information here has been tested using [phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/) 4.0.5 running on Unix.

The following instructions will **replace** your current database with the backup, **reverting** your database to the state it was in when you backed up.

#### Restore Process {#restore-process}

Using phpMyAdmin, follow the steps below to restore a MySQL/MariaDB database.

1. Login to [phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/).
2. Click "Databases" and select the database that you will be importing your data into.
3. You will then see either a list of tables already inside that database or a screen that says no tables exist. This depends on your setup.
4. Across the top of the screen will be a row of tabs. Click the **Import** tab.
5. On the next screen will be a location of text file box, and next to that a button named **Browse**.
6. Click **Browse**. Locate the backup file stored on your computer.
7. Make sure **SQL** is selected in the **Format** drop-down menu.
8. Click the **Go** button.

Now grab a coffee. This bit takes a while. Eventually you will see a success screen.

If you get an error message, your best bet is to post to the [WordPress support forums](https://wordpress.org/documentation/) to get help.

### Using MySQL/MariaDB Commands {#using-mysql-mariadb-commands}

The restore process consists of unarchiving your archived database dump, and importing it into your MySQL/MariaDB database.

Assuming your backup is a `.bz2` file, created using instructions similar to those given for [Backing up your database using MySQL/MariaDB commands](https://developer.wordpress.org/advanced-administration/security/backup/database/#using-straight-mysqlmariadb-commands), the following steps will guide you through restoring your database:

1. Unzip your `.bz2` file:

```
user@linux:~/files/blog> bzip2 -d blog.bak.sql.bz2
```

**Note:** If your database backup was a `.tar.gz` file called `blog.bak.sql.tar.gz`, then

```
tar -zxvf blog.bak.sql.tar.gz
```

is the command that should be used instead of the above.

2. Put the backed-up SQL back into MySQL/MariaDB:

```
user@linux:~/files/blog> mysql -h mysqlhostserver -u mysqlusername -p databasename < blog.bak.sql  
Enter password: (enter your mysql password)   
user@linux:~/files/blog>
```

## Changelog

- 2022-10-25: Original content from [Backing Up Your Database](https://developer.wordpress.org/advanced-administration/security/backup/database/).
