# Updating WordPress using FTP

## FTP Clients
There are two common ways to upload and manage files on your site:

1. By using the file manager provided in your host’s control panel. Popular file managers: [cPanel](https://documentation.cpanel.net/display/64Docs/File+Manager), [DirectAdmin](https://www.site-helper.com/filemanager.html), [Plesk](https://www.plesk.com/).
2. By using an FTP or SFTP client. This guide will show you how to use [FileZilla](https://filezilla-project.org/).

Neither option is automatically “better” — they’re different interfaces for working with the same kinds of files on your server.

- **When a control panel file manager is a good choice**: Quick, one-off tasks in a browser (renaming a file, uploading a single file, checking what’s on the server, or making a small emergency edit when you can’t install software on your computer).
- **When an FTP/SFTP client is a good choice**: Larger or repeatable tasks (uploading many files/folders, replacing WordPress core during a manual update, uploading a theme/plugin, keeping a queue running, resuming interrupted transfers, or working faster with drag-and-drop and bulk operations).

### Is cPanel “better” than an FTP client?
Not in general. cPanel’s file manager is convenient because it runs in your browser, but it can be slower and more tedious for bulk uploads. An FTP/SFTP client is often more efficient for moving lots of files and folders.

### Does an FTP/SFTP client “automate” the clunky parts of a file manager?
Yes, they often do. Clients typically offer features like transfer queues, recursive folder uploads, overwrite prompts, resumable transfers, and easier bulk operations. The underlying job is the same (copy files to/from the server), but the tooling can be smoother.

### Can one option upload things the other option can’t?
Usually both can upload/download the same website files. Differences are typically **limits and features**, not capability:

- **Control panel limits**: Some hosts limit upload size, timeouts, or ZIP extraction features in the browser.
- **FTP/SFTP client benefits**: Better handling of many small files, easier folder mirroring, and more reliable transfers on slow connections.

FTP or “File Transfer Protocol” has been the most widely used transfer protocol for over thirty years, but it sends your information in the clear, which is a security risk. Use SFTP (Secure File Transfer Protocol) if your host supports it. This transfers your files and your password over a secured connection, and should therefore be used instead of FTP whenever possible. Sometimes you have to contact your host to have SFTP enabled on your account.

Why use FileZilla? Because, like WordPress, it is released under the GPL. So, it is not just free, it is staying that way, too. The following pages will show you how to setup and use Filezilla:

1. [Setting up FileZilla for Your Website](https://developer.wordpress.org/advanced-administration/upgrade/ftp/filezilla/)
2. [Setting Permissions](https://developer.wordpress.org/advanced-administration/server/file-permissions/)
3. [FileZilla’s Extensive Documentation](https://wiki.filezilla-project.org/Documentation)

Want to try a different FTP or SFTP client? [Find more on Wikipedia](https://en.wikipedia.org/wiki/Comparison_of_FTP_clients).

