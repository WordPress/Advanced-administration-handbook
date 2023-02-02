# Control Panels

## cPanel

This tutorial provides step-by-step examples of creating a database and database user using the cPanel MySQL Database Wizard tool.

### Login to your site {#login-to-your-site}

Login to your hosting control panel (cPanel) with information provided by your host company.

### MySQL Database Wizard {#mysql-database-wizard}

Under the Database section, click on the MySQL Database Wizard icon.

[![cpanel-dbwizardicon](https://i3.wp.com/wordpress.org/documentation/files/2018/10/cpanel-dbwizardicon.png)](https://wordpress.org/documentation/files/2018/10/cpanel-dbwizardicon.png)

#### Step 1. Create a Database {#step-1-create-a-database}

Step 1 in the wizard is creating the database. Simply give your database a name. The actual database name will be prepended by your hosting account name. In this example, after clicking Next Step, the database `michaelh_demowp` will be created.

[![cpanel-createdb-step1](https://i3.wp.com/wordpress.org/documentation/files/2018/10/cpanel-createdb-step1.png)](https://wordpress.org/documentation/files/2018/10/cpanel-createdb-step1.png)

#### Step 2. Create Database Users {#step-2-create-database-users}

The next step in the wizard requires creating a database user and assigning that user a password. When entering the password, make sure the password strength meter registers Very Strong for your selected password. Also remember the password you enter as you will need that information later. In this example, dbuser is entered in the Username field, but when the Create User button is clicked, the database user ultimately will be named `michaelh_dbuser`.

[![Step 2. Create Database Users](https://i3.wp.com/wordpress.org/documentation/files/2018/10/cpanel-createdb-step2.png)](https://wordpress.org/documentation/files/2018/10/cpanel-createdb-step2.png)

#### Step 3. Add User to Database {#step-3-add-user-to-database}

In Step 3, you assign the user to the database and you assign the necessary database privileges. In this case, click the All Privileges checkbox and click the Next Step button to assign all privileges to the database user.

[![Step 3. Add User to Database](https://i3.wp.com/wordpress.org/documentation/files/2018/10/cpanel-createdb-step3.png)](https://wordpress.org/documentation/files/2018/10/cpanel-createdb-step3.png)

### Step 4. Complete the task {#step-4-complete-the-task}

In this step, you are notified that the user was added to the database. You have successfully created the database, created the user, and assigned privileges to that user.

[![Step 4. Complete the task](https://i3.wp.com/wordpress.org/documentation/files/2018/10/cpanel-createdb-step4.png)](https://wordpress.org/documentation/files/2018/10/cpanel-createdb-step4.png)

### Editing the WordPress Config File {#editing-the-wordpress-config-file}

Open the file `wp-config-sample.php` using a [text editor](https://htmltomd.com/support/article/glossary/#text-editor). There are the four pieces of information you need to complete in the file. The following is an example; yours may look slightly different:

```
// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define('DB_NAME', 'michaelh_demowp');

/** MySQL database username */
define('DB_USER', 'michaelh_dbuser');

/** MySQL database password */
define('DB_PASSWORD', 'here_the_password_you_set_before_');

/** MySQL hostname */
define('DB_HOST', 'localhost');
```

Note that the prefix `michaelh_` assigned by that cPanel is part of the database and database user. Also note, the **DB_HOST** value for almost all cPanel hosts is **localhost**.

Important! Save the completed file as `wp-config.php`.

### Continuing the Installation {#continuing-the-installation}

The database is created, and user is created and assigned to the database with the proper privileges. And the `wp-config.php` is updated with the database information. At this point it is okay to move to [Step 4 of the Installation process](https://htmltomd.com/support/article/how-to-install-wordpress/#step-4-upload-the-files).

## Plesk

This tutorial provides step-by-step examples of installing Plesk WP Toolkit using the Plesk Web Installer.
 
### Using Web Installer

Install Plesk using Web Installer please open your browser and go to [get.plesk.com](https://get.plesk.com/). For Plesk installation, it is required a fresh Linux server with access to the Internet. You can install Plesk on any supported Linux-based OS.

![Image_1](https://user-images.githubusercontent.com/19301688/189542599-4fce4d63-8060-416e-9fdf-f21ae62c87e1.png)

Provide your server's IP address or hostname, enter your root password, or just add a private key.
 
* Accept the terms of End-User License Agreement and click Install button.
* Relax and wait for some time to let the installation be finalized.
* Click on the Login link. No worries about "secure connection warnings", just make an exception.

### WP Toolkit 

WordPress Toolkit is a single management interface that enables you to easily install, configure, and manage WordPress. It is available if the WordPress Toolkit extension is installed in Plesk. WP Toolkit can install, configure, and manage WordPress version 3.7 or later.

### Installing WordPress

The WP Toolkit extension is free with the Web Pro and the Web Host Plesk editions and is available for a fee for the Web Admin edition.

![image_2](https://user-images.githubusercontent.com/19301688/189542665-78f52a1c-e92b-4d70-bb5d-899ac02cc57e.png)

* For an express installation, click Install (Quick). The latest version of WordPress will be installed, and the default settings will be used. The new instance will be available via HTTPS if SSL/TLS support is enabled for the domain.
* If you want to change the default installation settings, click Install (Custom). This enables you to set up the administrator user, select the desired WordPress version, specify the database name, select auto-update settings, and more.

Note: To install WordPress, WordPress Toolkit retrieves data from wordpress.org. By default, if WordPress Toolkit cannot establish connection in 15 seconds, wordpress.org is considered to be unavailable.

### Managing WordPress Instances

Go to WordPress to see all your WordPress instances. WordPress Toolkit groups information about each instance in blocks we call cards.

![image_3](https://user-images.githubusercontent.com/19301688/189542692-5d6f38b5-1b32-4de8-8f40-2abe9a5d1d86.png)

A card shows a screenshot of your website and features several controls that give you easy access to frequently used tools. The screenshot changes in real time to reflect the changes you make to your website. For example, if you switch the maintenance mode on or change the WordPress theme, the screenshot of the website will change immediately.

### Tools

In the "Tools" section, click to access the following WordPress Toolkit features:

![image_4](https://user-images.githubusercontent.com/19301688/189542713-abf476de-fcbd-4113-9975-1c2961765190.png)

* "Sync" to synchronize the content of your website with another one.
* "Clone" to make a full copy of your website.
* "Manage Files" to manage the website's files in File Manager.
* "Back Up/Restore" to create a backup of your website and restore it if necessary.

The controls below give you easy access to the following settings and tools:
* "Search engine indexing" shows your website in search results of search engines.
* "Caching (nginx)" speeds up the website load time and reduces server load.
* "Debugging" helps you debug a website that is not ready for viewing and being tested or developed.
* "Maintenance mode" hides your website's content from visitors.
* "Password Protection" specifies the password you will use to log in to WordPress from Plesk.

## Changelog

- 2023-01-26: Original copied from [Using cPanel](https://wordpress.org/documentation/article/using-cpanel/).
- 2022-09-11: Original copied for Plesk.
