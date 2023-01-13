# Backups

## WordPress Backups

> Note: Want to skip the hard stuff? Skip to Automated Solutions such as [WordPress Plugins](https://wordpress.org/plugins/search.php?q=backup) for backups.

Your WordPress database contains every post, every comment and every link you have on your blog. If your database gets erased or corrupted, you stand to lose everything you have written. There are many reasons why this could happen, and not all are things you can control. With a proper backup of your WordPress database and files, you can quickly restore things back to normal.

Instructions to back up your WordPress site include:

1. WordPress Site and your WordPress Database
2. Automatic WordPress backup options

In addition, support is provided online at the [WordPress Support Forum](https://wordpress.org/support/) to help you through the process.

Site backups are essential because problems inevitably occur and you need to be in a position to take action when disaster strikes. Spending a few minutes to make an easy, convenient backup of your database will allow you to spend even more time being creative and productive with your website.

### Backup Questions

> Back up your database regularly, and always before an upgrade.

**How often should you back up?**

That depends on how often you blog, how often you want to do this, and how you would feel if your database were lost along with a few posts. It is your decision.

**Can you use this method to back up other data?**

Yes. Backups are good all around.

**How many backups should I keep?**

The general rule of thumb is to keep at least three backups and keep them in three different places or forms, like CD/DVDs, different hard drives, a thumbdrive, web disk, your e-mail account, etc. This prevents problems if a single backup becomes corrupted or damaged.

**Can backups be automated?**

Yes. There are several methods of automating the backup process available, and we've listed some in the Automatic WordPress backup section. However, it is highly recommended that you back up those auto backups with a manual backup once in a while to guarantee that the process is working.

**Is there more information on backing up WordPress available?**

Yes. See Backup Resources for more information.

### Backing Up Your WordPress Site

There are two parts to backing up your WordPress site: **Database** and **Files**. You need to back up the entire site, and you need to back up your WordPress database. Below are instructions for backing up your WordPress database for various server programs. We will start with backing up the rest of your WordPress site.

Your WordPress site consists of the following:

1. WordPress Core installation
2. WordPress plugins
3. WordPress themes
4. Images and files
5. JavaScript, PHP, and other code files
6. Additional files and static web pages

All of these are used in various combinations to generate your website. The database contains your posts and a lot of data generated on your site, but it does not include the above elements that all come together to create the look and information on your site. These need to be saved.

Most hosts back up the entire server, including your site, but it takes time to request a copy of your site from their backups, and a speedy recovery is critical. You need to learn how to back up your own site files and restore them.

Here are some methods to backup your site files:

**Website Host Provided Backup Software**

Most website hosts provide software to back up your site. Check with your host to find out what services and programs they provide.

**Create Sync With Your Site**

[WinSCP](http://winscp.net/eng/index.php) and other programs allow you to sync with your website to keep a mirror copy of the content on your server and hard drive updated. It saves time and makes sure you have the latest files in both places.

**Copy Your Files to Your Desktop**

Using [FTP Clients](https://wordpress.org/support/article/ftp-clients/) or [UNIX Shell Skills](https://codex.wordpress.org/UNIX%20Shell%20Skills) you can copy the files to a folder on your computer. Once there, you can compress them into a zip file to save space, allowing you to keep several versions.

Remember, keep at least three backups on file, just in case one is corrupted or lost, and store them in different places and on different mediums (such as CD's, DVDs or hard drives).

### Database Backup Instructions

Back up your WordPress database regularly, and always before an upgrade or a move to a new location. The following information will help you back up your WordPress database using various popular server software packages. For detailed information, contact your website host for more information.

#### Accessing phpMyAdmin

See [phpMyAdmin](https://wordpress.org/support/article/phpmyadmin/) for more information on phpMyAdmin.

While familiarity with phpMyAdmin is not necessary to back up your WordPress database, these instructions should take you step-by-step through the process of finding phpMyAdmin on your server. Then you can follow the instructions below as a simple and easy backup. For more detailed instructions, see Backing Up Your Database.

* Plesk
* cPanel
* Direct Admin
* Ensim
* vDeck
* Ferozo

##### Plesk

On your Websites & Domains screen, click Open button corresponding to the database you have set up during the [WordPress installation](https://wordpress.org/support/article/how-to-install-wordpress/). This will open **phpMyAdmin** interface:

![image](https://user-images.githubusercontent.com/8250598/189548052-05143263-7980-45b5-b2dc-23fc5a8b19fd.png)

If you cannot see the **Open** button, make sure to close the **Start creating your website** prompt:

![image](https://user-images.githubusercontent.com/8250598/189548074-703c1d79-a437-445b-8bf7-ac51272b69f8.png)

Click Select Existing Database to find select your WordPress database:

![image](https://user-images.githubusercontent.com/8250598/189548312-c455cf50-757e-4bf7-9128-825e3cb0832c.png)

##### cPanel

On your main control panel for cPanel, look for the MySQL logo and click the link to MySQL Databases. On the next page, look for **phpMyAdmin** link and click it to access your phpMyAdmin.

![image](https://user-images.githubusercontent.com/8250598/189548290-9e815d91-e598-4b31-8bde-3101ac09bd89.png)

![image](https://user-images.githubusercontent.com/8250598/189548157-74dd7be8-ea45-4ee0-90d4-e16f57225d24.png)

##### Direct Admin

From **Your Account** page, look for **MySQL Management** and click it to access **phpMyAdmin**.

![image](https://user-images.githubusercontent.com/8250598/189548174-6951023a-c593-4f46-af78-5bf43a390279.png)

![image](https://user-images.githubusercontent.com/8250598/189548195-4b1ca6c1-0a6d-4191-8060-c90e59696ee3.png)

##### Ensim

Look for the MySQL Admin logo and click the link. Under **Configuration** choose **MySQL Administration Tool**.

![image](https://user-images.githubusercontent.com/8250598/189548260-d911357c-8681-4c27-a1ad-043d7a678c22.png)

![image](https://user-images.githubusercontent.com/8250598/189548265-2a7b7721-10e9-41d7-a150-ab11422c29cd.png)

##### vDeck

From the main control panel, click **Host Manager**, then click **Databases**. In the next window, click **Admin**. Another window will popup taking you to the phpMyAdmin login screen.

![image](https://user-images.githubusercontent.com/8250598/189548348-9f1135eb-4336-4f45-9fe6-8fa482f758d5.png)

![image](https://user-images.githubusercontent.com/8250598/189548353-75778b1a-686c-44a7-ab15-89b49d94e146.png)

##### Ferozo

Login to your Ferozo Control Panel by using your credentials. Once inside, go to the "Base de Datos" ("Data Base") menu and then click on "Acceso phpMyAdmin" ("Access phpMyAdmin"). A new window will open displaying the phpMyAdmin login screen.

![image](https://user-images.githubusercontent.com/8250598/189548372-ebebffc3-9723-4e4f-b478-df0d38499e58.png)

### Simple Backup with phpMyAdmin

The following is a very simple version of Backing Up Your Database. Once you have discovered how to access your site's phpMyAdmin, follow these simple instructions.

1. Click on **Databases** in your phpMyAdmin panel. (It may not be necessary to do this, depending on your version of phpMyAdmin)

![image](https://user-images.githubusercontent.com/8250598/189548469-d63090c3-4e43-48d4-8039-507957e4a69c.png)

2. You may have several databases. Click the one that holds your WordPress data, the database you created when you [installed WordPress](https://wordpress.org/installation/how-to-install-wordpress/). (In older versions this may be done through a pull-down menu.)

3. Below is a picture of the default tables in the Structure view tab. You may have more tables — this would happen if you have any statistics plugins or anti-spam plugins.

![image](https://user-images.githubusercontent.com/8250598/189548524-84c2f476-7fae-4bc4-b7fb-19b72f72bed8.png)

4. Click **Export**.
		- There are two methods to export, **Quick** and **Custom**; if you choose **Custom**, follow these steps:
				- Select all the tables.
				- In the **Output** section check **Save output to a file** and select **None** for **Compression**. (If your database is very large use a compression method)
				- Select **SQL** from the **Format** drop-down menu.
				- Check "Add DROP TABLE": this can be useful for over-writing an existing database.
				- Check "IF NOT EXISTS": this prevents errors during restores if the tables are already there.
				- Click **Go**. The data will now be saved into your computer.

### Automatic Backups

Various plugins exist to take automatic scheduled backups of your WordPress database. This helps to manage your backup collection easily. You can find automatic backup plugins in the **Plugin Browser** on the WordPress Administration Screens or through the [WordPress Plugin Directory](https://wordpress.org/plugins/).

* [List of Backup Plugins](https://wordpress.org/plugins/tags/backup)

### Backup Resources

* [FTP Backups](http://www.guyrutenberg.com/2010/02/28/improved-ftp-backup-for-wordpress/) – How to automate backing up to an FTP server
* [Incremental Backups](http://www.guyrutenberg.com/2013/03/28/incremental-wordpress-backups-using-duply-duplicity/) – How to make encrypted incremental backups using duplicity
* [Using phpMyAdmin with WordPress](https://wordpress.org/support/article/phpmyadmin/)

#### Backup Tools

* [Using phpMyAdmin](https://wordpress.org/support/article/phpmyadmin/)
* [FTP Clients](https://wordpress.org/support/article/ftp-clients/)
* [Using FileZilla](https://wordpress.org/support/article/using-filezilla/)

## Backing Up Your Database

> It is strongly recommended that you backup your database at regular intervals and before an upgrade.

[Restoring your database from backup](https://wordpress.org/support/article/restoring-your-database-from-backup/) is then possible if something goes wrong. 

**NOTE:** Below steps backup core WordPress database that include all your posts, pages and comments, but DO NOT backup the files and folders such as images, theme files on the server. For whole WordPress site backup, refer [WordPress Backups](https://wordpress.org/support/article/wordpress-backups/).

### Backup using cPanel X {#backup-using-cpanel-x}

cPanel is a popular control panel used by many web hosts. The backup feature can be used to backup your MySQL database. Do not generate a full backup, as these are strictly for archival purposes and cannot be restored via cPanel. Look for 'Download a MySQL Database Backup' and click the name of the database. A \*.gz file will be downloaded to your local drive.

There is no need to unzip this file to restore it. Using the same cPanel program, browse to the gz file and upload it. Once the upload is complete, the bottom of the browser will indicate dump complete. If you are uploading to a new host, you will need to recreate the database user along with the matching password. If you change the password, make the corresponding change in the wp-config.php file.

### Using phpMyAdmin {#using-phpmyadmin}

[phpMyAdmin](https://wordpress.org/support/article/phpmyadmin/) is the name of the program used to manipulate your database.

Information below has been tried and tested using phpMyAdmin version 4.4.13 connects to MySQL version 5.6.28 running on Linux.

[![phpmyadmin_top](https://wordpress.org/support/files/2018/11/phpmyadmin_top.jpg)](https://wordpress.org/support/files/2018/11/phpmyadmin_top.jpg)

#### Quick backup process {#quick-backup-process}

When you backup all tables in the WordPress database without compression, you can use simple method. To restore this backup, your new database should not have any tables.

1. Log into phpMyAdmin on your server
2. From the left side window, select your WordPress database. In this example, the name of database is "wp".
3. The right side window will show you all the tables inside your WordPress database. Click the 'Export' tab on the top set of tabs.

[![](https://wordpress.org/support/files/2018/11/phpmyadmin_dbtop.jpg)](https://wordpress.org/support/files/2018/11/phpmyadmin_dbtop.jpg)

4. Ensure that the Quick option is selected, and click 'Go' and you should be prompted for a file to download. Save the file to your computer. Depending on the database size, this may take a few moments.

[![phpmyadmin_quick_export](https://wordpress.org/support/files/2018/11/phpmyadmin_quick_export.jpg)](https://wordpress.org/support/files/2018/11/phpmyadmin_quick_export.jpg)

#### Custom backup process {#custom-backup-process}

If you want to change default behavior, select Custom backup.  In above Step 4, select Custom option. Detailed options are displayed.

[![phpmyadmin_custom_export](https://wordpress.org/support/files/2018/11/phpmyadmin_custom_export.jpg)](https://wordpress.org/support/files/2018/11/phpmyadmin_custom_export.jpg)

##### The Table section {#the-table-section}

All the tables in the database are selected. If you have other programs that use the database, then choose only those tables that correspond to your WordPress install. They will be the ones with that start with "wp_" or whatever 'table_prefix' you specified in your 'wp-config.php' file.

If you only have your WordPress blog installed, leave it as is (or click 'Select All' if you changed the selection)

##### The Output section {#the-output-section}

Select 'zipped' or 'gzipped' from Compression box to compress the data.  

[![phpmyadmin_export_output](https://wordpress.org/support/files/2018/11/phpmyadmin_export_output.jpg)](https://wordpress.org/support/files/2018/11/phpmyadmin_export_output.jpg)

##### The Format section {#the-format-section}

Ensure that the SQL is selected. Unlike CSV or other data formats, this option exports a sequence of SQL commands.

In the Format-specific options section, leave options as they are.

[![phpmyadmin_export_formatspecific](https://wordpress.org/support/files/2018/11/phpmyadmin_export_formatspecific.jpg)](https://wordpress.org/support/files/2018/11/phpmyadmin_export_formatspecific.jpg)

##### The Object creation options section {#the-object-creation-options-section}

Select Add DROP TABLE / VIEW / PROCEDURE / FUNCTION / EVENT / TRIGGER statement. Before table creation on target database, it will call DROP statement to delete the old existing table if it exist.

[![phpmyadmin_export_object](https://wordpress.org/support/files/2018/11/phpmyadmin_export_object.jpg)](https://wordpress.org/support/files/2018/11/phpmyadmin_export_object.jpg)

### The Data creation options section {#the-data-creation-options-section}

Leave options as they are.

[![phpmyadmin_export_data](https://wordpress.org/support/files/2018/11/phpmyadmin_export_data.jpg)](https://wordpress.org/support/files/2018/11/phpmyadmin_export_data.jpg)

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

[![mysql_workbench_top](https://wordpress.org/support/files/2018/11/mysql_workbench_top.jpg)](https://wordpress.org/support/files/2018/11/mysql_workbench_top.jpg)

#### Backing Up the Database {#backing-up-the-database}

This assumes you have already installed MySQL Workbench and set it up so that you can login to the MySQL Database Server either locally or remotely. Refer to the documentation that comes with the installation package of MySQL Workbench for your platform for installation instructions or [online document](https://dev.mysql.com/doc/workbench/en/).

1. Launch the MySQL Workbench
2. Click your database instance if it is displayed on the top page. Or, Click Database -> Connect Database from top menu, enter required information and Click OK.
3. Click Data Export in left side window.

[![mysql_workbench_export](https://wordpress.org/support/files/2018/11/mysql_workbench_export.jpg)](https://wordpress.org/support/files/2018/11/mysql_workbench_export.jpg)

1. Select your WordPress databases that you want to backup.
2. Specify target directory on Export Options. You need write permissions in the directory to which you are writing the backup.
3. Click Start Export on the lower right of the window.

[![mysql_workbench_export2](https://wordpress.org/support/files/2018/11/mysql_workbench_export2.jpg)](https://wordpress.org/support/files/2018/11/mysql_workbench_export2.jpg)

#### Restoring From a Backup {#restoring-from-a-backup}

1. Launch the MySQL Workbench
2. Click your database instance if it is displayed on the top page. Or, Click Database -> Connect Database, and Click OK.
3. Click Data Import/Restore in left side window.
4. Specify folder where you have backup files. Click "…" at the right of Import from Dump Project Folder, select backup folder, and click Open.
5. Click Start Import on the lower right of the window. The database restore will commence.

[![mysql_workbench_import](https://wordpress.org/support/files/2018/11/mysql_workbench_import.jpg)](https://wordpress.org/support/files/2018/11/mysql_workbench_import.jpg)

### MySQL GUI Tools {#mysql-gui-tools}

In addition to MySQL Workbench, there are many GUI tools that let you backup (export) your database.

| Name | OS (Paid edition) | OS (Free edition) | |
|---|---|---|
| [MySQL Workbench](http://www.mysql.com/products/workbench/) | Windows/Mac/Linux | Windows/Mac/Linux | See [above](https://wordpress.org/support/article/backing-up-your-database/#Using_MySQL_Workbench) |
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

1. Search for "WP-DB-Backup" on [Administration](https://wordpress.org/support/article/administration-screens/) > [Plugins](https://wordpress.org/support/article/administration-screens/#plugins-add-functionality-to-your-blog) > [Add New](https://wordpress.org/support/article/administration-screens/#add-new-plugins).
2. Click Install Now.
3. Activate the plugin.

#### Backing up {#backing-up}

1. Navigate to [Administration](https://wordpress.org/support/article/administration-screens/) > [Tools](https://wordpress.org/support/article/administration-screens/#tools-managing-your-blog) > Backup
2. Core WordPress tables will always be backed up. Select some options from Tables section.

[![wp-db-backup_table](https://wordpress.org/support/files/2018/11/wp-db-backup_table.jpg)](https://wordpress.org/support/files/2018/11/wp-db-backup_table.jpg)

3. Select the Backup Options; the backup can be downloaded, or emailed.

4. Finally, click on the Backup Now! button to actually perform the backup. You can also schedule regular backups.

[![wp-db-backup_settings](https://wordpress.org/support/files/2018/11/wp-db-backup_settings.jpg)](https://wordpress.org/support/files/2018/11/wp-db-backup_settings.jpg)

#### Restoring the Data {#restoring-the-data}

The file created is a standard SQL file. If you want information about how to upload that file, look at [Restoring Your Database From Backup](https://wordpress.org/support/article/restoring-your-database-from-backup/).

### More Resources {#more-resources}

* [Backup Plugins on the official WordPress.org repository](https://wordpress.org/plugins/search.php?q=backup)
* [WordPress Backups](https://wordpress.org/support/article/wordpress-backups/)

### External Resources {#external-resources}

* [How to Schedule Daily Backup of WordPress Database](https://www.narga.net/schedule-backup-wordpress-database/)

## Restoring Your Database From Backup

### Using phpMyAdmin {#using-phpmyadmin}

[phpMyAdmin](https://wordpress.org/support/article/phpmyadmin/) is a program used to manipulate databases remotely through a web interface. A good hosting package will have this included. For information on backing up your WordPress database, see [Backing Up Your Database](https://wordpress.org/support/article/backing-up-your-database/).

Information here has been tested using [phpMyAdmin](https://wordpress.org/support/article/phpmyadmin/) 4.0.5 running on Unix.

The following instructions will **replace** your current database with the backup, **reverting** your database to the state it was in when you backed up.

#### Restore Process {#restore-process}

Using phpMyAdmin, follow the steps below to restore a MySQL/MariaDB database.

1. Login to [phpMyAdmin](https://wordpress.org/support/article/phpmyadmin/).
2. Click "Databases" and select the database that you will be importing your data into.
3. You will then see either a list of tables already inside that database or a screen that says no tables exist. This depends on your setup.
4. Across the top of the screen will be a row of tabs. Click the **Import** tab.
5. On the next screen will be a location of text file box, and next to that a button named **Browse**.
6. Click **Browse**. Locate the backup file stored on your computer.
7. Make sure **SQL** is selected in the **Format** drop-down menu.
8. Click the **Go** button.

Now grab a coffee. This bit takes a while. Eventually you will see a success screen.

If you get an error message, your best bet is to post to the [WordPress support forums](https://wordpress.org/support/) to get help.

### Using MySQL/MariaDB Commands {#using-mysql-mariadb-commands}

The restore process consists of unarchiving your archived database dump, and importing it into your MySQL/MariaDB database.

Assuming your backup is a `.bz2` file, created using instructions similar to those given for [Backing up your database using MySQL/MariaDB commands](https://wordpress.org/support/article/backing-up-your-database/#using-straight-mysqlmariadb-commands), the following steps will guide you through restoring your database:

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

## Backing Up Your WordPress Files

There are two parts to backing up your WordPress site: **Database** and **Files**.

This page talks about **Files** only; if you need to back up your WordPress database, see the [Backing Up Your Database](https://wordpress.org/support/article/backing-up-your-database/).

Your WordPress site consists of the following files:

* WordPress Core Installation
* WordPress Plugins
* WordPress Themes
* Images and Files
* Javascripts, PHP scripts, and other code files
* Additional Files and Static Web Pages

Everything that has anything to do with the look and feel of your site is in a file somewhere and needs to be backed up. Additionally, you must back up all of your files in your WordPress directory (including subdirectories) and your [`.htaccess`](https://codex.wordpress.org/Glossary#.htaccess) file.

While most hosts back up the entire server, including your site, it is better that you back up your own files. The easiest method is to use an [FTP program](https://wordpress.org/support/article/ftp-clients/) to download all of your WordPress files from your host to your local computer.

By default, the files in the directory called wp-content are your own user-generated content, such as edited themes, new plugins, and uploaded files. Pay particular attention to backing up this area, along with your `wp-config.php`, which contains your connection details.

The remaining files are mostly the WordPress Core files, which are supplied by the [WordPress download zip file](https://wordpress.org/download/).

Please read [Backing Up Your WordPress Site](https://wordpress.org/support/article/wordpress-backups/#backing-up-your-wordpress-site) for further information.

Other ways to backup your files include:

**Website Host Provided Backup Software**

Most website hosts provide software to back up your site. Check with your host to find out what services and programs they provide.

**Create Synchs With Your Site**

[WinSCP](http://winscp.net/eng/index.php) and other programs allow you to synchronize with your website to keep a mirror copy of the content on your server and hard drive updated. It saves time and makes sure you have the latest files in both places.

#### Synchronize your files in WinScp {#synchronize-your-files-in-winscp}

1. Log in to your ftp server normally using WinScp.
2. Press the "Synchronize" button. Remote directory will automatically be set to the current ftp directory (often your root directory). Local directory would be set to the local directory as it was when you pressed Synchronize. You may want to change this to some other directory on your computer. Direction should be set to "local" to copy files FROM your web host TO your machine. Synchronization Mode would be set to Synchronize files.
3. Click "OK" to show a summary of actions.
4. Click "OK" again to complete the synchronization.

**Copy Your Files to Your Desktop**

Using [FTP Clients](https://wordpress.org/support/article/ftp-clients/) or [UNIX Shell Skills](https://codex.wordpress.org/UNIX Shell Skills) you can copy the files to a folder on your computer. Once there, you can zip or compress them into a zip file to save space, allowing you to keep several versions.

Normally, there would be no need to copy the WordPress core files, as you can replace them from a fresh download of the WordPress zip file. The important files to back up would be your wp-config.php file, which contains your settings and your wp-content directory (plus its contents) which contains all your theme and plugin files.

### Read Further {#read-further}

* [WordPress Backups](https://wordpress.org/support/article/wordpress-backups/)
* [Upgrading WordPress Extended](https://wordpress.org/support/article/upgrading-wordpress-extended-instructions/)

## Changelog

- 2022-10-25: Copying the [original content](https://wordpress.org/support/article/backing-up-your-database/), and [original content](https://wordpress.org/support/article/backing-up-your-wordpress-files/), and [originial content](https://wordpress.org/support/article/restoring-your-database-from-backup/).
- 2022-09-11: Added the [wordpress backup content](https://wordpress.org/support/article/wordpress-backups/)
