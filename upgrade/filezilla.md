# Using FileZilla

[FileZilla](https://filezilla-project.org/) is an open-source FTP-client and FTP-server developed by Tim Kosse (Germany) et.al. The FTP-client is available for multiple platforms such as Windows, Linux and Mac OS X, and the FTP-server is available for Windows only. (FileZilla supports SFTP, FTPS, and many other file transfer protocols; we will use "FTP" for simplicity.)

FileZilla may be used to manage your WordPress site by uploading and downloading files and images. This article will guide you through the process of installing and using FileZilla FTP-client to manage your WordPress site.

![Screenshot of the FileZilla application](https://wordpress.org/support/files/2018/11/podz_filezilla_1-768x513.gif)

For more information about FileZilla, view the [list of features](https://filezilla-project.org/client_features.php) at the official site.

### Why would I want to download FileZilla?
It’s fast, stable, easy to use, and free. FTP is a standard way to upload or download files between your local system and your web server, and FileZilla is a solid client for everyday FTP needs.

## Setting Up the Options

You will need the following details regarding the FTP account on your server:

1. Your website ftp address (usually ftp://example.com if your URL is http://example.com)
2. Your ftp username
3. Your ftp password

If you do not already have an FTP account on your server, use your cPanel or website administration tool to set one up — it will have all the information needed. If in doubt, ask your host for directions or help regarding an FTP account for your use to access your webspace.

Before connecting the FTP server, you should register it in the Site Manager. Once you register it, you just one click to connect the same server.

To register the FTP server, follow below steps:

1. Click **File > Site Manager** from FileZilla main window.
2. Click **New Site** then name the new connection to what you want (example: My blog server).
3. Enter the ftp address for your website in Host box. Usually, if your website is http://www.example.com, then the ftp address may be ftp://ftp.example.com or ftp://example.com. Some hosts also have a single FTP address for all their customers, so it may be ftp://ftp.examplehost.com/ . Note: Do not put a / at the end unless specifically told to do so on your cPanel or by your host.
4. Leave the Port box blank. Default value 21 should be used. Only change these if your FTP account details explicitly indicate that you should.
5. Select **Normal** from Logon Type box
6. Enter the full username that you have been given in User box. It may be just a username, or it may look like an email address (but it isn’t one). For instance, it would look similar to user or user@example.com.
7. Enter password. Remember that the password might be case sensitive.
8. Click OK.

![Screenshot of the FileZilla site manager window.](https://wordpress.org/support/files/2018/11/podz_filezilla_3-768x446.gif)

## Connecting

In the Toolbar of FileZilla main window, Select the down arrow of most left icon “Open the Site Manager”, and select your FTP server from drop down list.

Or, start Site Manager from **File > Site Manager**, select your FTP server and click ‘Connect’.

If all is well, then in the top window of FileZilla, you will have seen a series of messages, and in the large window, you will see a listing of the files will appear.

## Troubleshooting

If you had a problem, then it is time to go troubleshooting!

Look at the top area of FileZilla main window and check the messages.

1. If there was no attempt to connect, then the ftp address is wrong. All it needs is one character to be incorrect and it will fail. Click the red X, break the connection and click the Site Manager to check what you entered.
2. If it says that the user does not exist or _Incorrect Login_ and so on, check the Site Manager setting and ensure that it reflects what your FTP account and password details provided by your host says, or use the webserver administration interface provided to you by your host to re-check the existence of the FTP account. Check your password carefully. It is case sensitive(capitals and small letters). You may want to ask your web host for some assistance, too.
3. If it says _Could not retrieve directory listing_ you may need to change the Transfer Setting. From Site Manager, select your FTP Server and click *Transfer Settings*’ tab. Select Passive”’ from Transfer mode and click OK.

### Resources

* [Set Code Editor to Open Files in FileZilla](http://wpsites.net/tools/set-application-to-open-file-types-in-filezilla-using-mac/)

## Changelog

- 2022-09-11: Original content from [Using FileZilla](https://wordpress.org/support/article/using-filezilla/).
