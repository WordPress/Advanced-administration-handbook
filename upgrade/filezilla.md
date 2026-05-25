# Using FileZilla

[FileZilla](https://filezilla-project.org/) is an open-source file transfer client and server. Both the client and the server are available for Windows, Linux, and macOS. FileZilla supports **SFTP** and **FTPS** (encrypted), plus other protocols. If your host supports it, prefer **SFTP** over plain FTP.

FileZilla may be used to manage your WordPress site by uploading and downloading files and images. This article will guide you through the process of setting up and using the FileZilla client to manage your WordPress site.

![Screenshot of the FileZilla application](https://raw.githubusercontent.com/WordPress/Advanced-administration-handbook/main/assets/filezilla_1.gif)

For more information about FileZilla, view the [list of features](https://filezilla-project.org/client_features.php) at the official site.

### Why would I want to download FileZilla?

It's fast, stable, easy to use, and free. FileZilla can upload or download files between your local system and your web server. Whenever possible, use **SFTP** or **FTPS** instead of plain FTP so your connection is encrypted.

## Setting up the options

You will need the following details from your host (for FTP, FTPS, or SFTP):

1. The **host/server name** (often something like `example.com`, `ftp.example.com`, or a host-provided server name)
2. The **protocol** (prefer **SFTP** if available; some hosts offer **FTPS** instead)
3. Your **username**
4. Your **password** (or, for SFTP, sometimes an SSH key)

If you do not already have an FTP, FTPS, or SFTP account on your server, use your hosting control panel or website administration tool to set one up. If in doubt, ask your host which protocol, host name, port, username, and authentication method you should use.

Before connecting to the server, register the connection in the Site Manager. Once you register it, you can connect to the same server again with one click.

To register the server, follow these steps:

1. Click **File → Site Manager** from the FileZilla main window.
2. Click **New Site**, then name the new connection. For example: My blog server.
3. Enter the host/server name in the **Host** box. This may be `ftp.example.com`, `example.com`, or a host-provided value. Check with your host if you do not know. Do not add a `/` at the end unless your host specifically tells you to do so.
4. Choose the correct **Protocol** for your account. Prefer **SFTP** when available. Leave **Port** blank unless your host provides a specific port. Common defaults are 22 for SFTP and 21 for FTP.
5. Select the correct **Logon Type**. For password-based FTP or FTPS accounts, this is often **Normal**. For SFTP accounts, your host may ask you to use a password or an SSH key.
6. Enter the full username that you have been given in the **User** box. It may be just a username, or it may look like an email address (but it isn't one). For instance, it would look similar to `user` or `user@example.com`.
7. Enter the password if your account uses password authentication. Remember that the password might be case-sensitive.
8. Click **OK**.

Avoid saving passwords in FileZilla on shared or public computers. If you are using a shared computer, ask your host whether temporary credentials or key-based SFTP access are available.

![Screenshot of the FileZilla site manager window.](https://raw.githubusercontent.com/WordPress/Advanced-administration-handbook/main/assets/filezilla_3.gif)

## Connecting

Go to the first icon in the toolbar of FileZilla's main window, **Open the Site Manager**, click the down arrow, and select your server from the dropdown list.

Alternatively, start the Site Manager from **File → Site Manager**, select your server, and click **Connect**.

Following the appearance of a series of messages in the small window (Message Log), you should then see the list of the files on your server in the large window (Remote Directory Tree).

## Troubleshooting

If you have a problem, then it's time to start troubleshooting!

Look at the top area of the FileZilla main window and check the messages.

1. If there was no attempt to connect, then the server/host name may be wrong, or the protocol or port may be incorrect. One incorrect character can cause the connection to fail. Click the red X, break the connection, and open the Site Manager to check what you entered.
2. If it says that the user does not exist, _Incorrect Login_, or something similar, check the Site Manager settings against the account details provided by your host. Check your username, password, protocol, and port carefully. Passwords are case-sensitive.
3. If it says _Could not retrieve directory listing_, you may need to change the transfer mode. From Site Manager, select your server and click the **Transfer Settings** tab. Select **Passive** from **Transfer mode** and click **OK**.
4. If an SFTP connection fails but the host and username are correct, confirm with your host whether the account uses password authentication or an SSH key, and whether a custom port is required.
