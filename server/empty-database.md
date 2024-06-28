# Emptying a Database Table

Plugins which generate site statistics for you can rapidly create large amounts of data — every visitor causes something to be written to the database. Ordinarily, this is not a problem, but if your database size is limited by your host it could be. Also, if you are moving the database for whatever reason, its size will impact the export and import time. This page will show you how to empty a table, thus resetting its contents and size to zero. This does not stop the statistics plugins from working or otherwise damage your database.

[phpMyAdmin](https://developer.wordpress.org/advanced-administration/upgrade/phpmyadmin/) is the name of the program used to manipulate your database. A good hosting package will have this included. [Accessing phpMyAdmin](https://developer.wordpress.org/advanced-administration/security/backup/#accessing-phpmyadmin) offers information on accessing phpMyAdmin under various server control panels.

The procedure outlined in this article has been tried and tested using phpMyAdmin versions 2.5.3 and 2.5.7 pl1 running on Unix.

**Note:** When making significant changes like this to your database, you should always create a BACKUP!

See [WordPress Backups](https://developer.wordpress.org/advanced-administration/security/backup/) and [Backing Up Your Database](https://developer.wordpress.org/advanced-administration/security/backup/database/) for details.

## The Process

1. Login to phpMyAdmin on your server.

2. From the left side bar, select your WordPress database.

![Database selection on the left side bar](https://user-images.githubusercontent.com/90067869/189547314-a8bbe78e-70b6-4533-b14e-196a5db35840.png)

3. All the tables in the WordPress database will appear.

![Table list](https://user-images.githubusercontent.com/90067869/189547350-944a1066-e81d-404b-8eca-9125161eb5d4.png)

4. Click “Erase” button of the table you wish to empty. For this example, we will be emptying the “wp_dstats2” table.

**Note:** Your table may well have a different name, check the plugin’s documentation to find out what it is. DO NOT empty a table that is used by the WordPress core. (Please see the list under [Database_Description](https://codex.wordpress.org/Database_Description) for those specific table names.)

![Clicking the “Empty” button](https://user-images.githubusercontent.com/90067869/189547374-2088ff00-3c19-420d-86b7-fbcd0df6ed6d.png)

7. You will now get a confirmation screen.

![image](https://user-images.githubusercontent.com/90067869/189547394-d6a58758-7a2d-420c-9cd6-33de864b3078.png)

**This is your last chance to check that you have the right table and database selected** — phpMyAdmin has no UNDO function, so once changes are committed, you are stuck with them. Unless, of course, you made that suggested back up.

8. Click “OK” and you will be returned to viewing all the tables in your database with the specified table’s contents emptied.

## Changelog

- 2022-09-11: Original content from [Emptying a Database Table](https://wordpress.org/documentation/article/emptying-a-database-table/).
