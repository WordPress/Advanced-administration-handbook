# Updating WordPress using FTP

## FTP Clients
There are two ways of getting files onto your site, and once there, changing them:

1. By using the file manager provided in your host’s control panel. Popular file managers: [cPanel](https://documentation.cpanel.net/display/64Docs/File+Manager), [DirectAdmin](http://www.site-helper.com/filemanager.html), [Plesk](https://www.plesk.com/).
2. By using an FTP or SFTP client. This guide will show you how to use [FileZilla](https://filezilla-project.org/).

FTP or “File Transfer Protocol” has been the most widely used transfer protocol for over thirty years, but it sends your information in the clear, which is a security risk. Use SFTP (Secure File Transfer Protocol) if your host supports it. This transfers your files and your password over a secured connection, and should therefore be used instead of FTP whenever possible. Sometimes you have to contact your host to have SFTP enabled on your account.

Why use FileZilla? Because, like WordPress, it is released under the GPL. So, it is not just free, it is staying that way, too. The following pages will show you how to setup and use Filezilla:

1. [Setting up FileZilla for Your Website](https://developer.wordpress.org/advanced-administration/upgrade/ftp/filezilla/)
2. [Setting Permissions](https://developer.wordpress.org/advanced-administration/server/file-permissions/)
3. [FileZilla’s Extensive Documentation](https://wiki.filezilla-project.org/Documentation)

Want to try a different FTP or SFTP client? [Find more on Wikipedia](http://en.wikipedia.org/wiki/Comparison_of_FTP_clients).

## Changelog

- 2022-09-11: Original content from [FTP Clients](https://wordpress.org/documentation/article/ftp-clients/). Minor copy-editing.
