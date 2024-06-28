## Backing Up Your WordPress Files

There are two parts to backing up your WordPress site: **Database** and **Files**.

This page talks about **Files** only; if you need to back up your WordPress database, see the [Backing Up Your Database](https://developer.wordpress.org/advanced-administration/security/backup/database/).

Your WordPress site consists of the following files:

* WordPress Core Installation
* WordPress Plugins
* WordPress Themes
* Images and Files
* Javascripts, PHP scripts, and other code files
* Additional Files and Static Web Pages

Everything that has anything to do with the look and feel of your site is in a file somewhere and needs to be backed up. Additionally, you must back up all of your files in your WordPress directory (including subdirectories) and your [`.htaccess`](https://wordpress.org/documentation/article/wordpress-glossary/#.htaccess) file.

While most hosts back up the entire server, including your site, it is better that you back up your own files. The easiest method is to use an [FTP program](https://developer.wordpress.org/advanced-administration/upgrade/ftp/) to download all of your WordPress files from your host to your local computer.

By default, the files in the directory called wp-content are your own user-generated content, such as edited themes, new plugins, and uploaded files. Pay particular attention to backing up this area, along with your `wp-config.php`, which contains your connection details.

The remaining files are mostly the WordPress Core files, which are supplied by the [WordPress download zip file](https://wordpress.org/download/).

Please read [Backing Up Your WordPress Site](https://developer.wordpress.org/advanced-administration/security/backup/#backing-up-your-wordpress-site) for further information.

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

Using [FTP Clients](https://developer.wordpress.org/advanced-administration/upgrade/ftp/) or [UNIX Shell Skills](https://codex.wordpress.org/UNIX_Shell_Skills) you can copy the files to a folder on your computer. Once there, you can zip or compress them into a zip file to save space, allowing you to keep several versions.

Normally, there would be no need to copy the WordPress core files, as you can replace them from a fresh download of the WordPress zip file. The important files to back up would be your wp-config.php file, which contains your settings and your wp-content directory (plus its contents) which contains all your theme and plugin files.

## Changelog

- 2022-10-25: Original content from [Backing Up Your WordPress Files](https://wordpress.org/documentation/article/backing-up-your-wordpress-files/).
