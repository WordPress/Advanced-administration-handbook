# Backups

## WordPress Backups

_Note: Want to skip the hard stuff? Skip to Automated Solutions such as [WordPress Plugins](https://wordpress.org/plugins/search.php?q=backup) for backups._

Your WordPress database contains every post, every comment and every link you have on your blog. If your database gets erased or corrupted, you stand to lose everything you have written. There are many reasons why this could happen, and not all are things you can control. With a proper backup of your WordPress database and files, you can quickly restore things back to normal.

Instructions to back up your WordPress site include:

1. WordPress Site and your WordPress Database
2. Automatic WordPress backup options

In addition, support is provided online at the [WordPress Support Forum](https://wordpress.org/documentation/) to help you through the process.

Site backups are essential because problems inevitably occur and you need to be in a position to take action when disaster strikes. Spending a few minutes to make an easy, convenient backup of your database will allow you to spend even more time being creative and productive with your website.

### Backup Questions

_Back up your database regularly, and always before an upgrade._

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

Using [FTP Clients](https://developer.wordpress.org/advanced-administration/upgrade/ftp/) or [UNIX Shell Skills](https://codex.wordpress.org/UNIX_Shell_Skills) you can copy the files to a folder on your computer. Once there, you can compress them into a zip file to save space, allowing you to keep several versions.

Remember, keep at least three backups on file, just in case one is corrupted or lost, and store them in different places and on different mediums (such as CD's, DVDs or hard drives).

### Database Backup Instructions

Back up your WordPress database regularly, and always before an upgrade or a move to a new location. The following information will help you back up your WordPress database using various popular server software packages. For detailed information, contact your website host for more information.

#### Accessing phpMyAdmin

See [phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/) for more information on phpMyAdmin.

While familiarity with phpMyAdmin is not necessary to back up your WordPress database, these instructions should take you step-by-step through the process of finding phpMyAdmin on your server. Then you can follow the instructions below as a simple and easy backup. For more detailed instructions, see Backing Up Your Database.

* Plesk
* cPanel
* Direct Admin
* Ensim
* vDeck
* Ferozo

##### Plesk

On your Websites & Domains screen, click Open button corresponding to the database you have set up during the [WordPress installation](https://developer.wordpress.org/advanced-administration/before-install/howto-install/). This will open **phpMyAdmin** interface:

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
* [Using phpMyAdmin with WordPress](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/)

#### Backup Tools

* [Using phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/)
* [FTP Clients](https://developer.wordpress.org/advanced-administration/upgrade/ftp/)
* [Using FileZilla](https://developer.wordpress.org/advanced-administration/upgrade/ftp/filezilla/)


### Read Further {#read-further}

* [WordPress Backups](https://developer.wordpress.org/advanced-administration/security/backup/)
* [Upgrading WordPress Extended](https://developer.wordpress.org/advanced-administration/upgrade/upgrading/)

## Changelog

- 2022-10-25: Original content from [Restoring Your Database From Backup](https://wordpress.org/documentation/article/restoring-your-database-from-backup/).
- 2022-09-11: Original content from [WordPress Backups](https://wordpress.org/documentation/article/wordpress-backups/).
