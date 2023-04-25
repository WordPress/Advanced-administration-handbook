# Apache HTTPD / .htaccess

## .htaccess

The `.htaccess` is a distributed configuration file, and is how Apache handles configuration changes on a per-directory basis.

WordPress uses this file to manipulate how Apache serves files from its root directory, and subdirectories thereof. Most notably, WP modifies this file to be able to handle pretty permalinks.

This page may be used to restore a corrupted `.htaccess` file (e.g. a misbehaving plugin).

### Basic WP

```
# BEGIN WordPress

RewriteEngine On
RewriteRule .\* - \[E=HTTP\_AUTHORIZATION:%{HTTP:Authorization}\]
RewriteBase /
RewriteRule ^index\\.php$ - \[L\]
RewriteCond %{REQUEST\_FILENAME} !-f
RewriteCond %{REQUEST\_FILENAME} !-d
RewriteRule . /index.php \[L\]

# END WordPress
```

### Multisite

#### WordPress 3.5 and up

If you activated Multisite on WordPress 3.5 or later, use one of these.

##### WordPress >=3.5 Subfolder Example

```
# BEGIN WordPress Multisite
# Using subfolder network type: https://wordpress.org/documentation/article/htaccess/#multisite

RewriteEngine On
RewriteRule .\* - \[E=HTTP\_AUTHORIZATION:%{HTTP:Authorization}\]
RewriteBase /
RewriteRule ^index\\.php$ - \[L\]

# add a trailing slash to /wp-admin
RewriteRule ^(\[\_0-9a-zA-Z-\]+/)?wp-admin$ $1wp-admin/ \[R=301,L\]

RewriteCond %{REQUEST\_FILENAME} -f \[OR\]
RewriteCond %{REQUEST\_FILENAME} -d
RewriteRule ^ - \[L\]
RewriteRule ^(\[\_0-9a-zA-Z-\]+/)?(wp-(content|admin|includes).\*) $2 \[L\]
RewriteRule ^(\[\_0-9a-zA-Z-\]+/)?(.\*\\.php)$ $2 \[L\]
RewriteRule . index.php \[L\]

# END WordPress Multisite
```

##### WordPress >=3.5 SubDomain Example

```
# BEGIN WordPress Multisite
# Using subdomain network type: https://wordpress.org/documentation/article/htaccess/#multisite

RewriteEngine On
RewriteRule .\* - \[E=HTTP\_AUTHORIZATION:%{HTTP:Authorization}\]
RewriteBase /
RewriteRule ^index\\.php$ - \[L\]

# add a trailing slash to /wp-admin
RewriteRule ^wp-admin$ wp-admin/ \[R=301,L\]

RewriteCond %{REQUEST\_FILENAME} -f \[OR\]
RewriteCond %{REQUEST\_FILENAME} -d
RewriteRule ^ - \[L\]
RewriteRule ^(wp-(content|admin|includes).\*) $1 \[L\]
RewriteRule ^(.\*\\.php)$ $1 \[L\]
RewriteRule . index.php \[L\]

# END WordPress Multisite
```

#### WordPress 3.4 and below

If you originally installed WordPress with 3.4 or older and activated Multisite then, you need to use one of these:

##### WordPress <=3.4 SubFolder Example

WordPress 3.0 through 3.4.2

```
# BEGIN WordPress Multisite
# Using subfolder network type: https://wordpress.org/documentation/article/htaccess/#multisite

RewriteEngine On
RewriteBase /
RewriteRule ^index\\.php$ - \[L\]

# uploaded files
RewriteRule ^(\[\_0-9a-zA-Z-\]+/)?files/(.+) wp-includes/ms-files.php?file=$2 \[L\]

# add a trailing slash to /wp-admin
RewriteRule ^(\[\_0-9a-zA-Z-\]+/)?wp-admin$ $1wp-admin/ \[R=301,L\]

RewriteCond %{REQUEST\_FILENAME} -f \[OR\]
RewriteCond %{REQUEST\_FILENAME} -d
RewriteRule ^ - \[L\]
RewriteRule ^\[\_0-9a-zA-Z-\]+/(wp-(content|admin|includes).\*) $1 \[L\]
RewriteRule ^\[\_0-9a-zA-Z-\]+/(.\*\\.php)$ $1 \[L\]
RewriteRule . index.php \[L\]

# END WordPress Multisite
```

##### WordPress <=3.4 SubDomain Example

```
# BEGIN WordPress Multisite
# Using subdomain network type: https://wordpress.org/documentation/article/htaccess/#multisite

RewriteEngine On
RewriteBase /
RewriteRule ^index\\.php$ - \[L\]

# uploaded files
RewriteRule ^files/(.+) wp-includes/ms-files.php?file=$1 \[L\]

RewriteCond %{REQUEST\_FILENAME} -f \[OR\]
RewriteCond %{REQUEST\_FILENAME} -d
RewriteRule ^ - \[L\]
RewriteRule . index.php \[L\]

# END WordPress Multisite
```

### General Examples

#### Options

Any options preceded by a **+** are added to the options currently in force, and any options preceded by a **–** are removed from the options currently in force.

Possible values for the [Options directive](https://httpd.apache.org/docs/trunk/mod/core.html#options) are any combination of:

**None**

All options are turned off.

**All**

All options except for MultiViews. This is the default setting.

**ExecCGI**

Execution of CGI scripts using mod\_cgi is permitted.

**FollowSymLinks**

The server will follow symbolic links in this directory.

**Includes**

Server-side includes provided by mod\_include are permitted.

**IncludesNOEXEC**

Server-side includes are permitted, but the #exec cmd and #exec cgi are disabled.

**Indexes**

URL maps to a directory, and no DirectoryIndex, a formatted listing of the directory.

**MultiViews**

Content negotiated “MultiViews” are allowed using mod\_negotiation.

**SymLinksIfOwnerMatch**

Only follow symbolic links where target is owned by the same user id as the link.

This will disable all options, and then only enable FollowSymLinks, which is necessary for mod\_rewrite.

```
Options None
Options FollowSymLinks
```

#### DirectoryIndex

[DirectoryIndex Directive](https://httpd.apache.org/docs/trunk/mod/mod_dir.html#directoryindex) sets the file that Apache will serve if a directory is requested.

Several URLs may be given, in which case the server will return the first one that it finds.

```
DirectoryIndex index.php index.html /index.php
```

#### DefaultLanguage

[DefaultLanguage Directive](https://httpd.apache.org/docs/trunk/mod/mod_mime.html#defaultlanguage) will cause all files that do not already have a specific language tag associated with it will use this.

```
DefaultLanguage en
```

#### Default Charset

Set the default character encoding sent in the HTTP header. See [Setting charset information in .htaccess](https://www.w3.org/International/questions/qa-htaccess-charset)

```
AddDefaultCharset UTF-8
```

**Set Charset for Specific Files**

```
AddType 'text/html; charset=UTF-8' .html
```

**Set for specific files**

```
AddCharset UTF-8 .html
```

#### ServerSignature

The [ServerSignature Directive](https://httpd.apache.org/docs/trunk/mod/core.html#serversignature) allows the configuration of a trailing footer line under server-generated documents. Optionally add a line containing the server version and virtual host name to server-generated pages (internal error documents, FTP directory listings, mod_status and mod_info output etc., but not CGI generated documents or custom error documents).

**On**

adds a line with the server version number and ServerName of the serving virtual host

**Off**

suppresses the footer line

**Email**

creates a “mailto:” reference to the ServerAdmin of the referenced document

```
SetEnv SERVER_ADMIN admin@site.com
ServerSignature Email
```

#### Force Files to be Downloaded

The below will cause any requests for files ending in the specified extensions to not be displayed in the browser but instead force a "Save As" dialog so the client can download.

```
AddType application/octet-stream .avi .mpg .mov .pdf .xls .mp4
```

#### HTTP Compression

The [AddOutputFilter Directive](https://httpd.apache.org/docs/trunk/mod/mod_mime.html#addoutputfilter) maps the filename extension extension to the filters which will process responses from the server before they are sent to the client. This is in addition to any filters defined elsewhere, including `SetOutputFilter` and `AddOutputFilterByType`. This mapping is merged over any already in force, overriding any mappings that already exist for the same extension.

See also [Enable Compression](https://developers.google.com/speed/docs/insights/EnableCompression)

```
AddOutputFilterByType DEFLATE text/html text/plain text/xml application/xml application/xhtml+xml text/javascript text/css application/x-javascript
BrowserMatch ^Mozilla/4 gzip-only-text/html
BrowserMatch ^Mozilla/4\\.0\[678\] no-gzip
BrowserMatch \\bMSIE !no-gzip !gzip-only-text/html
```

**Force Compression for certain files**

```
SetOutputFilter DEFLATE
```

#### Send Custom HTTP Headers

The [Header Directive](https://httpd.apache.org/docs/trunk/mod/mod_headers.html#header) lets you send HTTP headers for every request, or just specific files. You can view a sites HTTP Headers using [Firebug](https://getfirebug.com/), [Chrome Dev Tools](https://developer.chrome.com/docs/devtools/), [Wireshark](https://www.wireshark.org/) or [Advanced HTTP Request / Response Headers](https://www.askapache.com/online-tools/http-headers-tool/).

```
Header set X-Pingback "https://example.com/xmlrpc.php"
Header set Content-Language "en-US"
```

#### Unset HTTP Headers

This will unset HTTP headers, using **always** will try extra hard to remove them.

```
Header unset Pragma
Header always unset WP-Super-Cache
Header always unset X-Pingback
```

#### Password Protect Login

This is very useful for protecting the `wp-login.php` file. You can use this [Advanced Htpasswd/Htdigest file creator](https://www.askapache.com/online-tools/htpasswd-generator/).

**Basic Authentication**

```
AuthType Basic
AuthName "Password Protected"
AuthUserFile /full/path/to/.htpasswd
Require valid-user
Satisfy All
```

**Digest Authentication**

```
AuthType Digest
AuthName "Password Protected"
AuthDigestDomain /wp-login.php https://example.com/wp-login.php
AuthUserFile /full/path/to/.htpasswd
Require valid-user
Satisfy All
```

#### Require Specific IP

This is a way to only allow certain IP addresses to be allowed access.

```
ErrorDocument 401 default
ErrorDocument 403 default

Order deny,allow
Deny from all
Allow from 192.0.2.1 localhost
```

#### Protect Sensitive Files

This denies all web access to your wp-config file, error_logs, php.ini, and htaccess/htpasswds.

```
Order deny,allow
Deny from all
```

#### Require SSL

This will force SSL, and require the exact hostname or else it will redirect to the SSL version. Useful in a `/wp-admin/.htaccess` file.

```
SSLOptions +StrictRequire
SSLRequireSSL
SSLRequire %{HTTP_HOST} eq "www.example.com"
ErrorDocument 403 https://www.example.com
```

### External Resources

* [Official Apache HTTP Server Tutorial: .htaccess files](https://httpd.apache.org/docs/trunk/howto/htaccess.html)
* [Official Htaccess Directive Quick Reference](https://httpd.apache.org/docs/trunk/mod/quickreference.html)
* [Htaccess Tutorial](https://www.askapache.com/htaccess/
* [Google PageSpeed for Developers](https://developers.google.com/speed/docs/insights/rules)
* [Stupid Htaccess Tricks](https://perishablepress.com/stupid-htaccess-tricks/)
* [Advanced Mod_Rewrite](https://www.askapache.com/htaccess/crazy-advanced-mod_rewrite-tutorial/)

### See also

* [htaccess for subdirectories](https://codex.wordpress.org/htaccess%20for%20subdirectories)
* [Using Permalinks](https://wordpress.org/documentation/article/customize-permalinks/)
* [Changing File Permissions](https://wordpress.org/documentation/article/changing-file-permissions/)
* [UNIX Shell Skills](https://codex.wordpress.org/UNIX%20Shell%20Skills)
* [Rewrite API](https://codex.wordpress.org/Rewrite%20API)

## Changelog

- 2023-04-25: Original content from [htaccess](https://wordpress.org/documentation/article/htaccess/).
