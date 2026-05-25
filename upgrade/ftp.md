# Updating WordPress using FTP

## FTP clients

There are two common ways to upload and manage files on your site:

1. Use the file manager provided by your host's control panel.
2. Use an FTP, FTPS, or SFTP client.

Both options are interfaces for working with files on your server. The best choice depends on the task, your access level, and what your host supports.

## Before transferring files

Before uploading, replacing, or deleting WordPress files, make sure you have a current backup of both your site files and database. Also confirm the correct site directory with your host, especially if your account contains more than one site or staging environment.

When replacing files, avoid changing file permissions unless you know they are incorrect. Incorrect permissions can prevent WordPress, themes, plugins, or the web server from reading or writing files.

## When to use a control panel file manager

A control panel file manager can be useful for quick, one-off tasks in a browser, such as:

* Renaming a file.
* Uploading a single file.
* Checking which files are on the server.
* Making a small emergency edit when you cannot install software on your computer.

For larger uploads, a browser-based file manager can be slower or more limited. Some hosts also limit upload size, timeout duration, or ZIP extraction features in the control panel.

## When to use an FTP, FTPS, or SFTP client

An FTP, FTPS, or SFTP client is often a better choice for larger or repeatable file-management tasks, such as:

* Uploading many files or folders.
* Replacing WordPress core files during a manual update.
* Uploading a theme or plugin.
* Using transfer queues.
* Resuming interrupted transfers.
* Working faster with drag-and-drop and bulk operations.
* Viewing hidden files, such as `.htaccess`, when your client is configured to show them.

FTP, or File Transfer Protocol, sends data in clear text. This is a security risk because your files and password are not encrypted during transfer.

Use **SFTP** or **FTPS** instead of plain FTP whenever your host supports it. SFTP and FTPS transfer files over an encrypted connection. You may need to contact your host to confirm which protocol, host name, port, username, and authentication method you should use.

## Using FileZilla

FileZilla is a popular FTP, FTPS, and SFTP client. Like WordPress, it is released under the GPL.

The following pages can help you set up and use FileZilla:

1. [Setting up FileZilla for your website](https://developer.wordpress.org/advanced-administration/upgrade/ftp/filezilla/)
2. [Setting permissions](https://developer.wordpress.org/advanced-administration/server/file-permissions/)
3. [FileZilla documentation](https://wiki.filezilla-project.org/Documentation)

Want to try a different FTP, FTPS, or SFTP client? You can compare available clients and choose one that supports your operating system and your host's required protocol.
