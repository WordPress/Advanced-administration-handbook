# Security

## Why should I use HTTPS

HTTPS is an encrypted communication protocol — essentially, a more secure way of browsing the web, since you get a private channel directly between your browser and the web server. That's why most major sites use it.

If a site's using HTTPS, you'll see a little padlock icon in the address field, just as in the screenshot below:

![Screenshot of the "secure site" padlock icon](https://wordpress.org/support/files/2019/03/image.png)

Here are the most common reasons you might want to use HTTPS on your own site:

**Faster.** One might think that HTTPS would make your site slower, since it takes some time to encrypt and decrypt all data. But a lot of efficiency improvements to HTTP are only available when you use HTTPS. As a result, HTTPS will actually make your site faster for almost all visitors.

**Trust.** Users find it easier to trust a secure site. While they don't necessarily know their traffic is encrypted, they do know the little padlock icon means a site cares about their privacy. Tech people will know that any servers between your computer and the web server won't be able to see the information flowing forth and back, and won't be able to change it.

**Payment security.** If you sell anything on your site, users want to know their payment information is secure. HTTPS, and the little padlock, assure that their information travels safely to the web server.

**Search Engine Optimization.** Many search engines will add a penalty to web sites that don't use HTTPS, thus making it harder to reach the best spots in search results.

**Your good name.** Have you noticed that some websites have the text "not secure" next to their address?

That happens when your web browser wants you to know a site is NOT using HTTPS. Browsers want you to think (rightly!) that site owners who can't be bothered using HTTPS (it's free in many cases) aren't worth your time and certainly not your money.

In turn, you don't want browsers suggesting you might be that kind of shady site owner yourself.

## Administration Over HTTPS

To easily enable (and enforce) WordPress administration over SSL, there are two constants that you can define in your site's [wp-config.php](https://codex.wordpress.org/Editing_wp-config.php) file. It is not sufficient to define these constants in a plugin file; they must be defined in your [wp-config.php](https://codex.wordpress.org/Editing_wp-config.php) file. You must also already have SSL configured on the server and a (virtual) host configured for the secure server before your site will work properly with these constants set to true.

**Note:** `FORCE_SSL_LOGIN` was deprecated in [Version 4.0](https://codex.wordpress.org/Version 4.0). Please use `FORCE_SSL_ADMIN`.

### To Force HTTPS Logins and HTTPS Admin Access {#to-force-ssl-logins-and-ssl-admin-access}

The constant `FORCE_SSL_ADMIN` can be set to true in the `wp-config.php` file to force all logins **and** all admin sessions to happen over SSL.

#### Example {#example}

```
define( 'FORCE_SSL_ADMIN', true );
```

### Using a Reverse Proxy {#using-a-reverse-proxy}

If WordPress is hosted behind a reverse proxy that provides SSL, but is hosted itself without SSL, these options will initially send any requests into an infinite redirect loop. To avoid this, you may configure WordPress to recognize the `HTTP_X_FORWARDED_PROTO` header (assuming you have properly configured the reverse proxy to set that header).

#### Example {#example-2}

```
define( 'FORCE_SSL_ADMIN', true );
// in some setups HTTP_X_FORWARDED_PROTO might contain 
// a comma-separated list e.g. http,https
// so check for https existence
if( strpos( $_SERVER['HTTP_X_FORWARDED_PROTO'], 'https') !== false )
	$_SERVER['HTTPS'] = 'on';
```

### Further Information {#further-information}

The rest of this article serves as information in case you're using an older version of WordPress (which ideally you shouldn't!) or your SSL setup is somewhat different (ie. your SSL certificate is for a different domain).

Sometimes, you want your whole wp-admin to run over a secure connection using the https protocol. Conceptually, the procedure works like this:

1. Set up two virtual hosts with the same url (the blog url), one secure, the other not.
2. On the secure virtual host, set up a rewrite rule that shuttles all non-wp-admin traffic to the insecure site.
3. On the insecure virtual host, set up a rewrite rule that shuttles all traffic to wp-admin to the secure host.
4. Put in a filter (via a plugin) that filters the links in wp-admin so that once activated, administrative links are rewritten to use https and that edits cookies to work only over encrypted connections.

The following guide is for WordPress 1.5 and Apache running `mod_rewrite`, using rewrite rules in `httpd.conf` (as opposed to `.htaccess` files) but could easily be modified to fit other hosting scenarios.

#### Virtual Hosts {#virtual-hosts}

You need a (virtual) host configured for the secure server in addition to the non-secure site. In this example, the secure virtual host uses the same `DocumentRoot` as the insecure host. Hypothetically, you could use a host with a different name, such as wpadmin.mysite.com and link the document root to the wpadmin directory.

Please ask your ISP to set up a secure virtual host for you, or if you have administrative access set up your own. Note that [you cannot use name based virtual hosting to identify different SSL servers](http://httpd.apache.org/docs-2.0/ssl/ssl_faq.html#vhosts2).

##### Rewrite Rules For The Insecure Host {#rewrite-rules-for-the-insecure-host}

In the `.htaccess` or virtual host stanza in `httpd.conf` for your insecure host, add this rewrite rule to automatically go to the secure host when you browse to `http://mysite.com/wp-admin/` or `http://mysite.com/wp-login.php`

This should go above the main wordpress rewrite block.

```
RewriteCond %{THE_REQUEST} ^[A-Z]{3,9}\ /(.*)\ HTTP/ [NC]
RewriteCond %{HTTPS} !=on [NC]
RewriteRule ^/?(wp-admin/|wp-login\.php) https://example.com%{REQUEST_URI}%{QUERY_STRING} [R=301,QSA,L]
```

If you are using permalink rewrite rules, this line must come before `RewriteRule ^.*$ - [S=40]`.

An important idea in this block is using `THE_REQUEST`, which ensures only actual http requests are rewritten and not local direct file requests, like an include or fopen.

##### Rewrite Rules For Secure Host (Optional) {#rewrite-rules-for-secure-host-optional}

These rewrite rules are optional. They disable access to the public site over a secure connection. If you wish to remain logged in to the public portion of your site using the plugin below, you must _not_ add these rules, as the plugin disables the cookie over unencrypted connections.

The secure virtual host should have two rewrite rules in an .htaccess file or in the virtual host declaration (see [Using Permalinks](https://codex.wordpress.org/Using Permalinks) for more on rewriting):

```
RewriteRule !^/wp-admin/(.*) - [C]
RewriteRule ^/(.*) http://www.example.com/$1 [QSA,L]
```

The first rule excludes the wp-admin directory from the next rule, which shuffles traffic to the secure site over to the insecure site, to keep things nice and seamless for your audience.

##### Setting WordPress URI {#setting-wordpress-uri}

For some plugins to work, and for other reasons, you may wish to set your WordPress URI in options to reflect the https protocol by making this setting `https://example.com`. Your blog address should not change.

##### Example Config Stanzas {#example-config-stanzas}

NOTE: The below config is not 100% compatible with WordPress 2.8+, WordPress 2.8 uses some files from the wp-includes folder. The redirection that the first set of Rewrite rules introduces may cause security warnings for some users. See [#10079](https://core.trac.wordpress.org/ticket/10079) for more information.

```
<VirtualHost nnn.nnn.nnn.nnn:443>
	ServerName www.example.com

	SSLEngine On
	SSLCertificateFile /etc/apache2/ssl/thissite.crt
	SSLCertificateKeyFile /etc/apache2/ssl/thissite.pem
	SetEnvIf User-Agent ".*MSIE.*" nokeepalive ssl-unclean-shutdown

	DocumentRoot /var/www/mysite

	<IfModule mod_rewrite.c>
		RewriteEngine On
		RewriteRule !^/wp-(admin|includes)/(.*) - [C]
		RewriteRule ^/(.*) http://www.example.com/$1 [QSA,L]
	</IfModule>

</VirtualHost>
```

**Insecure site**

```
<VirtualHost *>
	ServerName www.mysite.com

	DocumentRoot /var/www/ii/mysite

	<Directory /var/www/ii/mysite >
		<IfModule mod_rewrite.c>
			RewriteEngine On
			RewriteBase /
			RewriteCond %{REQUEST_FILENAME} -f [OR]
			RewriteCond %{REQUEST_FILENAME} -d
			RewriteRule ^wp-admin/(.*) https://www.example.com/wp-admin/$1 [C]
			RewriteRule ^.*$ - [S=40]
			RewriteRule ^feed/(feed|rdf|rss|rss2|atom)/?$ /index.php?&feed=$1 [QSA,L]

		</IfModule>
	</Directory>

</VirtualHost>
```

##### Rewrite for Login and Registration {#rewrite-for-login-and-registration}

It is probably a good idea to utilize SSL for user logins and registrations. Consider the following substitute RewriteRules.

###### Insecure

```
RewriteRule ^/wp-(admin|login|register)(.*) https://www.example.com/wp-$1$2 [C]
```

###### Secure

```
RewriteRule !^/wp-(admin|login|register)(.*) - [C]
```

##### Rewrite for sites running on port 443 or port 80 {#rewrite-for-sites-running-on-port-443-or-port-80}

```
# BEGIN WordPress
<IfModule mod_rewrite.c>
RewriteEngine On
RewriteBase /

# For a site running on port 443 or else (http over ssl)
RewriteCond %{SERVER_PORT} !^80$
RewriteRule !^wp-(admin|login|register)(.*) - [C]
RewriteRule ^(.*)$ http://%{SERVER_NAME}/$1 [L]

# For a site running on port 80 (http)
RewriteCond %{SERVER_PORT}  ^80$
RewriteCond %{REQUEST_FILENAME} -f [OR]
RewriteCond %{REQUEST_FILENAME} -d
RewriteRule ^wp-(admin|login|register)(.*) https://%{SERVER_NAME}:10001/wp-$1$2 [L]

RewriteCond %{SERVER_PORT}  ^80$
RewriteCond %{REQUEST_FILENAME} !-f
RewriteCond %{REQUEST_FILENAME} !-d
RewriteRule . /index.php [L]

</IfModule>
```

#### Summary {#summary}

This method does _not_ fix some [inherent security risks](https://wordpress.org/support/topic/24558#post-154136) in WordPress, nor does it protect you against man-in-the-middle attacks or other risks that can cripple secure connections.

However, this _should_ make it much harder for a malicious person to steal your cookies and/or authentication headers and use them to impersonate you and gain access to wp-admin. It also obfuscates the ability to sniff your content, which could be important for legal blogs which may have drafts of documents that need strict protection.

#### Verification {#verification}

On the author's server, logs indicate that both GET and POST requests are over SSL and that all traffic to wp-admin on the insecure host is being shuttled over to the secure host.

Sample POST log line:

```
[Thu Apr 28 09:34:33 2005] [info] Subsequent (No.5) HTTPS request received for child 6 (server foo.com:443)
xx.xxx.xxx.xxx - - [28/Apr/2005:09:34:33 -0500] "POST /wp-admin/post.php HTTP/1.1" 302 - "https://foo.com/wp-admin/post.php?action=edit&post=71" "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.7) Gecko/20050414 Firefox/1.0.3"
```

More testing, preferably with a packet sniffer and some hardcore network analysis tools, would help to confirm.

#### Limitations {#limitations}

The author assumes (but hasn't checked) that if the user has stored cookies/told their browser to remember passwords (not based on form fields but if using certain external auth mechanism) and hits `http://www.example.com/wp-admin/`, those packets are sent in the clear and the cookie/auth headers could be intercepted. Therefore, to ensure maximum security, the user should explicitly use the https host or always log in at the beginning of new sessions.

## Hardening WordPress

Security in WordPress is [taken very seriously](https://wordpress.org/about/security/), but as with any other system there are potential security issues that may arise if some basic security precautions aren't taken. This article will go through some common forms of vulnerabilities, and the things you can do to help keep your WordPress installation secure.

This article is not the ultimate quick fix to your security concerns. If you have specific security concerns or doubts, you should discuss them with people whom you trust to have sufficient knowledge of computer security and WordPress.

### What is Security? {#what-is-security}

Fundamentally, security _is not_ about perfectly secure systems. Such a thing might well be impractical, or impossible to find and/or maintain. What security is though is risk reduction, not risk elimination. It's about employing all the appropriate controls available to you, within reason, that allow you to improve your overall posture reducing the odds of making yourself a target, subsequently getting hacked.

**Website Hosts**

Often, a good place to start when it comes to website security is your hosting environment. Today, there are a number of options available to you, and while hosts offer security to a certain level, it's important to understand where their responsibility ends and yours begins. Here is a good article explaining the complicated dynamic between [web hosts and the security of your website](http://perezbox.com/2014/11/how-hosts-manage-your-website-security/). A secure server protects the privacy, integrity, and availability of the resources under the server administrator's control.

Qualities of a trusted web host might include:

* Readily discusses your security concerns and which security features and processes they offer with their hosting.
* Provides the most recent stable versions of all server software.
* Provides reliable methods for backup and recovery.

Decide which security you need on your server by determining the software and data that needs to be secured. The rest of this guide will help you with this.

**Website Applications**

It's easy to look at web hosts and pass the responsibility of security to them, but there is a tremendous amount of security that lies on the website owner as well. Web hosts are often responsible for the infrastructure on which your website sits, they are not responsible for the application you choose to install.

To understand where and why this is important you must [understand how websites get hacked](https://blog.sucuri.net/2015/05/website-security-how-do-websites-get-hacked.html), Rarely is it attributed to the infrastructure, and most often attributed to the application itself (i.e., the environment you are responsible for).

### Security Themes {#security-themes}

Keep in mind some general ideas while considering security for each aspect of your system:

**Limiting access**

Making smart choices that reduce possible entry points available to a malicious person.

**Containment**

Your system should be configured to minimize the amount of damage that can be done in the event that it is compromised.

**Preparation and knowledge**

Keeping backups and knowing the state of your WordPress installation at regular intervals. Having a plan to backup and recover your installation in the case of catastrophe can help you get back online faster in the case of a problem.

**Trusted Sources**

Do not get plugins/themes from untrusted sources. Restrict yourself to the WordPress.org repository or well known companies. Trying to get plugins/themes from the outside [may lead to issues](http://blog.sucuri.net/2014/03/unmasking-free-premium-wordpress-plugins.html).

### Vulnerabilities on Your Computer {#vulnerabilities-on-your-computer}

Make sure the computers you use are free of spyware, malware, and virus infections. No amount of security in WordPress or on your web server will make the slightest difference if there is a keylogger on your computer.

Always keep your operating system and the software on it, especially your web browser, up to date to protect you from security vulnerabilities. If you are browsing untrusted sites, we also recommend using tools like no-script (or disabling javascript/flash/java) in your browser.

### Vulnerabilities in WordPress {#vulnerabilities-in-wordpress}

Like many modern software packages, WordPress is updated regularly to address new security issues that may arise. Improving software security is always an ongoing concern, and to that end **you should always keep up to date with the latest version of WordPress**. Older versions of WordPress are not maintained with security updates.

#### Updating WordPress {#updating-wordpress}

Main article: [Updating WordPress](https://codex.wordpress.org/Updating_WordPress).

The latest version of WordPress is always available from the main WordPress website at `https://wordpress.org`. Official releases are not available from other sites — **never** download or install WordPress from any website other than `https://wordpress.org`.

Since version 3.7, WordPress has featured automatic updates. Use this functionality to ease the process of keeping up to date. You can also use the WordPress Dashboard to keep informed about updates. Read the entry in the Dashboard or the WordPress Developer Blog to determine what steps you must take to update and remain secure.

If a vulnerability is discovered in WordPress and a new version is released to address the issue, the information required to exploit the vulnerability is almost certainly in the public domain. This makes old versions more open to attack, and is one of the primary reasons you should always keep WordPress up to date.

If you are an administrator in charge of more than one WordPress installation, consider using [Subversion](https://codex.wordpress.org/Installing/Updating_WordPress_with_Subversion) to make management easier.

#### Reporting Security Issues {#reporting-security-issues}

If you think you have found a security flaw in WordPress, you can help by reporting the issue. See the [Security FAQ](https://codex.wordpress.org/Security FAQ) for information on how to report security issues.

If you think you have found a bug, report it. See [Submitting Bugs](https://codex.wordpress.org/Submitting Bugs) for how to do this. You might have uncovered a vulnerability, or a bug that could lead to one.

### Web Server Vulnerabilities {#web-server-vulnerabilities}

The web server running WordPress, and the software on it, can have vulnerabilities. Therefore, make sure you are running secure, stable versions of your web server and the software on it, or make sure you are using a trusted host that takes care of these things for you.

If you're on a shared server (one that hosts other websites besides your own) and a website on the same server is compromised, your website can potentially be compromised too even if you follow everything in this guide. Be sure to ask your [web host](https://wordpress.org/support/article/glossary/#Hosting_provider) what security precautions they take.

### Network Vulnerabilities {#network-vulnerabilities}

The network on both ends — the WordPress server side and the client network side — should be trusted. That means updating firewall rules on your home router and being careful about what networks you work from. An Internet cafe where you are sending passwords over an unencrypted connection, wireless or otherwise, is **not** a trusted network.

Your web host should be making sure that their network is not compromised by attackers, and you should do the same. Network vulnerabilities can allow passwords and other sensitive information to be intercepted.

### Passwords {#passwords}

Many potential vulnerabilities can be avoided with good security habits. A strong password is an important aspect of this.

The goal with your password is to make it hard for other people to guess and hard for a [brute force attack](https://wordpress.org/support/article/brute-force-attacks/) to succeed. Many [automatic password generators](http://www.google.com/?q=password+generator) are available that can be used to create secure passwords.

WordPress also features a password strength meter which is shown when changing your password in WordPress. Use this when changing your password to ensure its strength is adequate.

Things to avoid when choosing a password:

* Any permutation of your own real name, username, company name, or name of your website.
* A word from a dictionary, in any language.
* A short password.
* Any numeric-only or alphabetic-only password (a mixture of both is best).

A strong password is necessary not just to protect your blog content. A hacker who gains access to your administrator account is able to install malicious scripts that can potentially compromise your entire server.

In addition to using a strong password, it's a good idea to enable [two-step authentication](https://wordpress.org/support/article/two-step-authentication/) as an additional security measure.

### FTP {#ftp}

When connecting to your server you should use SFTP encryption if your web host provides it. If you are unsure if your web host provides SFTP or not, just ask them.

Using SFTP is the same as FTP, except your password and other data is encrypted as it is transmitted between your computer and your website. This means your password is never sent in the clear and cannot be intercepted by an attacker.

### File Permissions {#file-permissions}

Some neat features of WordPress come from allowing various files to be writable by the web server. However, allowing write access to your files is potentially dangerous, particularly in a shared hosting environment.

It is best to lock down your file permissions as much as possible and to loosen those restrictions on the occasions that you need to allow write access, or to create specific folders with less restrictions for the purpose of doing things like uploading files.

Here is one possible permission scheme.

All files should be owned by your user account, and should be writable by you. Any file that needs write access from WordPress should be writable by the web server, if your hosting set up requires it, that may mean those files need to be group-owned by the user account used by the web server process.

**`/`**

The root WordPress directory: all files should be writable only by your user account, except `.htaccess` if you want WordPress to automatically generate rewrite rules for you.

**`/wp-admin/`**

The WordPress administration area: all files should be writable only by your user account.

**`/wp-includes/`**

The bulk of WordPress application logic: all files should be writable only by your user account.

**`/wp-content/`**

User-supplied content: intended to be writable by your user account and the web server process.

Within `/wp-content/` you will find:

**`/wp-content/themes/`**

Theme files. If you want to use the built-in theme editor, all files need to be writable by the web server process. If you do not want to use the built-in theme editor, all files can be writable only by your user account.

**`/wp-content/plugins/`**

Plugin files: all files should be writable only by your user account.

Other directories that may be present with `/wp-content/` should be documented by whichever plugin or theme requires them. Permissions may vary.

#### Changing file permissions {#changing-file-permissions}

If you have shell access to your server, you can change file permissions recursively with the following command:

For Directories:

```
find /path/to/your/wordpress/install/ -type d -exec chmod 755 {} \;
```

For Files:

```
find /path/to/your/wordpress/install/ -type f -exec chmod 644 {} \;
```

#### Regarding Automatic Updates {#regarding-automatic-updates}

When you tell WordPress to perform an automatic update, all file operations are performed as the user that owns the files, not as the web server's user. All files are set to 0644 and all directories are set to 0755, and writable by only the user and readable by everyone else, including the web server.

### Database Security {#database-security}

If you run multiple blogs on the same server, it is wise to consider keeping them in separate databases each managed by a different user. This is best accomplished when performing the initial [WordPress installation](https://codex.wordpress.org/Installing_WordPress). This is a containment strategy: if an intruder successfully cracks one WordPress installation, this makes it that much harder to alter your other blogs.

If you administer MySQL yourself, ensure that you understand your MySQL configuration and that unneeded features (such as accepting remote TCP connections) are disabled. See [Secure MySQL Database Design](http://www.securityfocus.com/infocus/1667) for a nice introduction.

#### Restricting Database User Privileges {#restricting-database-user-privileges}

For normal WordPress operations, such as posting blog posts, uploading media files, posting comments, creating new WordPress users and installing WordPress plugins, the MySQL database user only needs data read and data write privileges to the MySQL database; SELECT, INSERT, UPDATE and DELETE.

Therefore any other database structure and administration privileges, such as DROP, ALTER and GRANT can be revoked. By revoking such privileges you are also improving the containment policies.

**Note:** Some plugins, themes and major WordPress updates might require to make database structural changes, such as add new tables or change the schema. In such case, before installing the plugin or updating a software, you will need to temporarily allow the database user the required privileges.

**WARNING:** Attempting updates without having these privileges can cause problems when database schema changes occur. Thus, it is **NOT** recommended to revoke these privileges. If you do feel the need to do this for security reasons, then please make sure that you have a solid backup plan in place first, with regular whole database backups which you have tested are valid and that can be easily restored. A failed database upgrade can usually be solved by restoring the database back to an old version, granting the proper permissions, and then letting WordPress try the database update again. Restoring the database will return it back to that old version and the WordPress administration screens will then detect the old version and allow you to run the necessary SQL commands on it. Most WordPress upgrades do not change the schema, but some do. Only major point upgrades (3.7 to 3.8, for example) will alter the schema. Minor upgrades (3.8 to 3.8.1) will generally not. Nevertheless, **keep a regular backup**.

### Securing wp-admin {#securing-wp-admin}

Adding server-side password protection (such as [BasicAuth](http://en.wikipedia.org/wiki/Basic_access_authentication)) to `/wp-admin/` adds a second layer of protection around your blog's admin area, the login screen, and your files. This forces an attacker or bot to attack this second layer of protection instead of your actual admin files. Many WordPress attacks are carried out autonomously by malicious software bots.

Simply securing the `wp-admin/` directory might also break some WordPress functionality, such as the AJAX handler at `wp-admin/admin-ajax.php`. See the [Resources](https://codex.wordpress.org/#Resources) section for more documentation on how to password protect your `wp-admin/` directory properly.

The most common attacks against a WordPress blog usually fall into two categories.

1. Sending specially-crafted HTTP requests to your server with specific exploit payloads for specific vulnerabilities. These include old/outdated plugins and software.
2. Attempting to gain access to your blog by using "brute-force" password guessing.

The ultimate implementation of this "second layer" password protection is to require an HTTPS SSL encrypted connection for administration, so that all communication and sensitive data is encrypted. _See [Administration Over SSL](https://codex.wordpress.org/Administration_Over_SSL)._

### Securing wp-includes {#securing-wp-includes}

A second layer of protection can be added where scripts are generally not intended to be accessed by any user. One way to do that is to block those scripts using mod_rewrite in the .htaccess file. **Note:** to ensure the code below is not overwritten by WordPress, place it outside the `# BEGIN WordPress` and `# END WordPress` tags in the .htaccess file. WordPress can overwrite anything between these tags.

```
# Block the include-only files.
<IfModule mod_rewrite.c>
RewriteEngine On
RewriteBase /
RewriteRule ^wp-admin/includes/ - [F,L]
RewriteRule !^wp-includes/ - [S=3]
RewriteRule ^wp-includes/[^/]+\.php$ - [F,L]
RewriteRule ^wp-includes/js/tinymce/langs/.+\.php - [F,L]
RewriteRule ^wp-includes/theme-compat/ - [F,L]
</IfModule>
# BEGIN WordPress
```

Note that this won't work well on Multisite, as `RewriteRule ^wp-includes/[^/]+\.php$ - [F,L]` would prevent the ms-files.php file from generating images. Omitting that line will allow the code to work, but offers less security.

### Securing wp-config.php {#securing-wp-config-php}

You can move the `wp-config.php` file to the directory above your WordPress install. This means for a site installed in the root of your webspace, you can store `wp-config.php` outside the web-root folder.

**Note:** Some people assert that [moving wp-config.php has minimal security benefits](http://wordpress.stackexchange.com/q/58391/3898) and, if not done carefully, may actually introduce serious vulnerabilities. [Others disagree](http://wordpress.stackexchange.com/a/74972/24425).

Note that `wp-config.php` can be stored ONE directory level above the WordPress (where wp-includes resides) installation. Also, make sure that only you (and the web server) can read this file (it generally means a 400 or 440 permission).

If you use a server with .htaccess, you can put this in that file (at the very top) to deny access to anyone surfing for it:

```
<files wp-config.php>
order allow,deny
deny from all
</files>
```

### Disable File Editing {#disable-file-editing}

The WordPress Dashboard by default allows administrators to edit PHP files, such as plugin and theme files. This is often the first tool an attacker will use if able to login, since it allows code execution. WordPress has a constant to disable editing from Dashboard. Placing this line in wp-config.php is equivalent to removing the 'edit_themes', 'edit_plugins' and 'edit_files' capabilities of all users:

```
define( 'DISALLOW_FILE_EDIT', true );
```

This will not prevent an attacker from uploading malicious files to your site, but might stop some attacks.

### Plugins {#plugins}

First of all, make sure your plugins are always updated. Also, if you are not using a specific plugin, delete it from the system.

#### Firewall {#firewall}

There are many plugins and services that can act as a firewall for your website. Some of them work by modifying your .htaccess  
file and restricting some access at the Apache level, before it is processed by WordPress. A good example is [iThemes Security](https://wordpress.org/plugins/better-wp-security/) or [All in One WP Security](https://wordpress.org/plugins/all-in-one-wp-security-and-firewall/). Some firewall plugins act at the WordPress level, like [WordFence](https://wordpress.org/plugins/wordfence/) and [Shield](https://wordpress.org/plugins/wp-simple-firewall/), and try to filter attacks as WordPress is loading, but before it is fully processed.

Besides plugins, you can also install a WAF (web firewall) at your web server to filter content before it is processed by WordPress. The most popular open source WAF is ModSecurity.

A website firewall can also be added as intermediary between the traffic from the internet and your hosting server. These services all function as reverse proxies, in which they accept the initial requests and reroute them to your server, stripping it of all malicious requests. They accomplish this by modifying your DNS records, via an A record or full DNS swap, allowing all traffic to pass through the new network first. This causes all traffic to be filtered by the firewall before reaching your site. A few companies offer such service, like [CloudFlare](http://cloudflare.com), [Sucuri](https://sucuri.net/wordpress-security/) and [Incapsula](http://www.incapsula.com).

Additionally, these third parties service providers function as Content Distribution Network (CDNs) by default, introducing performance optimization and global reach.

#### Plugins that need write access {#plugins-that-need-write-access}

If a plugin wants write access to your WordPress files and directories, please read the code to make sure it is legit or check with someone you trust. Possible places to check are the [Support Forums](https://codex.wordpress.org/Using_the_Support_Forums) and [IRC Channel](https://codex.wordpress.org/IRC).

#### Code execution plugins {#code-execution-plugins}

As we said, part of the goal of hardening WordPress is containing the damage done if there is a successful attack. Plugins which allow arbitrary PHP or other code to execute from entries in a database effectively magnify the possibility of damage in the event of a successful attack.

A way to avoid using such a plugin is to use [custom page templates](https://wordpress.org/support/article/pages/#Creating_your_own_Page_Templates) that call the function. Part of the security this affords is active only when you [disallow file editing within WordPress](#File_Permissions).

### Security through obscurity {#security-through-obscurity}

[Security through obscurity](http://en.wikipedia.org/wiki/Security_through_obscurity) is generally an unsound primary strategy. However, there are areas in WordPress where obscuring information _might_ help with security:

1. **Rename the administrative account:** When creating an administrative account, avoid easily guessed terms such as `admin` or `webmaster` as usernames because they are typically subject to attacks first. On an existing WordPress install you may rename the existing account in the MySQL command-line client with a command like `UPDATE wp_users SET user_login = 'newuser' WHERE user_login = 'admin';`, or by using a MySQL frontend like [phpMyAdmin](https://codex.wordpress.org/phpMyAdmin).
2. **Change the table_prefix:** Many published WordPress-specific SQL-injection attacks make the assumption that the table_prefix is `wp_`, the default. Changing this can block at least some SQL injection attacks.

### Data Backups {#data-backups}

Back up your data regularly, including your MySQL databases. See the main article: [Backing Up Your Database](https://wordpress.org/support/article/backing-up-your-database/).

Data integrity is critical for trusted backups. Encrypting the backup, keeping an independent record of MD5 hashes for each backup file, and/or placing backups on read-only media increases your confidence that your data has not been tampered with.

A sound backup strategy could include keeping a set of regularly-timed snapshots of your entire WordPress installation (including WordPress core files and your database) in a trusted location. Imagine a site that makes weekly snapshots. Such a strategy means that if a site is compromised on May 1st but the compromise is not detected until May 12th, the site owner will have pre-compromise backups that can help in rebuilding the site and possibly even post-compromise backups which will aid in determining how the site was compromised.

### Logging {#logging}

Logs are your best friend when it comes to understanding what is happening with your website, especially if you're trying to perform forensics. Contrary to popular beliefs, logs allow you to see what was done and by who and when. Unfortunately the logs will not tell you who, username, logged in, but it will allow you to identify the IP and time and more importantly, the actions the attacker might have taken. You will be able to see any of these attacks via the logs – Cross Site Scripting (XSS), Remote File Inclusion (RFI), Local File Inclusion (LFI) and Directory Traversal attempts. You will also be able to see brute force attempts. There are various [examples and tutorials](https://blog.sucuri.net/2015/08/ask-sucuri-how-did-my-wordpress-website-get-hacked-a-tutorial.html) available to help guide you through the process of parsing and analyzing your raw logs.

If you get more comfortable with your logs you'll be able to see things like, when the theme and plugin editors are being used, when someone updates your widgets and when posts and pages are added. All key elements when doing forensic work on your web server. The are a few WordPress Security plugins that assist you with this as well, like the [Sucuri Auditing tool](https://wordpress.org/plugins/sucuri-scanner/) or the [Audit Trail](https://wordpress.org/plugins/audit-trail/) plugin.

There are two key open-source solutions you'll want on your web server from a security perspective, this is a layered approach to security.

OSSEC can run on any NIX distribution and will also run on Windows. When configured correctly its very powerful. The idea is correlate and aggregate all the logs. You have to be sure to configure it to capture all access_logs and error_logs and if you have multiple websites on the server account for that. You'll also want to be sure to filter out the noise. By default you'll see a lot of noise and you'll want to configure it to be really effective.

### Monitoring {#monitoring}

Sometimes prevention is not enough and you may still be hacked. That's why intrusion detection/monitoring is very important. It will allow you to react faster, find out what happened and recover your site.

#### Monitoring your logs {#monitoring-your-logs}

If you are on a dedicated or virtual private server, in which you have the luxury of root access, you have the ability easily configure things so that you can see what's going on. [OSSEC](http://www.ossec.net) easily facilitates this and here is a little write up that might help you out [OSSEC for Website Security – Part I](https://perezbox.com/2013/03/ossec-for-website-security-part-i/).

#### Monitoring your files for changes {#monitoring-your-files-for-changes}

When an attack happens, it always leave traces. Either on the logs or on the file system (new files, modified files, etc). If you are using [OSSEC](http://www.ossec.net) for example, it will monitor your files and alert you when they change.

##### Goals {#goals}

The goals of file system tracking include:

* Monitor changed and added files
* Log changes and additions
* Ability to revert granular changes
* Automated alerts

##### General approaches {#general-approaches}

Administrators can monitor file system via general technologies such as:

* System utilities
* Revision control
* OS/kernel level monitoring

##### Specific tools {#specific-tools}

Options for file system monitoring include:

* [diff](http://en.wikipedia.org/wiki/Diff_utility) – build clean test copy of your site and compare against production
* [Git](http://git-scm.com/) – source code management
* [inotify](https://en.wikipedia.org/wiki/Inotify) and [incron](http://inotify.aiken.cz/?section=incron&page=doc&lang=en) – OS kernel level file monitoring service that can run commands on filesystem events
* [Watcher](https://github.com/gregghz/Watcher/blob/master/jobs.yml) – Python inotify library
* [OSSEC](http://ossec.net) – Open Source Host-based Intrusion Detection System that performs log analysis, file integrity checking, policy monitoring, rootkit detection, real-time alerting and active response.

##### Considerations {#considerations}

When configuring a file based monitoring strategy, there are many considerations, including the following.

###### Run the monitoring script/service as root

This would make it hard for attackers to disable or modify your file system monitoring solution.

###### Disable monitoring during scheduled maintenance/upgrades

This would prevent unnecessary notifications when you are performing regular maintenance on the site.

###### Monitor only executable filetypes

It may be reasonably safe to monitor only executable file types, such as .php files, etc.. Filtering out non-executable files may reduce unnecessary log entries and alerts.

###### Use strict file system permissions

Read about securing file permissions and ownership. In general, avoid allowing _execute_ and _write_ permissions to the extent possible.

#### Monitoring your web server externally {#monitoring-your-web-server-externally}

If the attacker tries to deface your site or add malware, you can also detect these changes by using a web-based integrity monitor solution. This comes in many forms today, use your favorite search engine and look for Web Malware Detection and Remediation and you'll likely get a long list of service providers.

### Resources {#resources}

* [How to Improve WordPress Security (Infographic)](http://yourescapefrom9to5.com/wordpress-security-infographic)
* [Security Plugins](https://wordpress.org/plugins/tags/security)
* [WordPress Security Cutting Through the BS](http://blog.sucuri.net/2012/08/wordpress-security-cutting-through-the-bs.html)
* [e-Book: Locking Down WordPress](http://build.codepoet.com/2012/07/10/locking-down-wordpress/)
* [wpsecure.net has a few guides on how to lock down WordPress.](http://wpsecure.net/basics/)
* [A Beginners Guide to Hardening WordPress](http://makeawebsitehub.com/wordpress-security/)
* [Brad Williams: Lock it Up (Video)](http://wordpress.tv/2010/01/23/brad-williams-security-boston10/)
* [21 Ways to Secure Your WordPress Site](https://hostingfacts.com/how-to-secure-wordpress/)
* [Official docs on how to password protect directories with an .htaccess file](http://httpd.apache.org/docs/2.2/howto/auth.html)
* [Simple tutorial on how to password protect the WordPress admin area and fix the 404 error](http://www.wpbeginner.com/wp-tutorials/how-to-password-protect-your-wordpress-admin-wp-admin-directory/)

### See Also {#see-also}

* [Security FAQ](https://codex.wordpress.org/Security FAQ)
* [FAQ – My site was hacked](https://codex.wordpress.org/FAQ_My_site_was_hacked)
* [Brute Force Attacks](https://codex.wordpress.org/Brute Force Attacks)
* [WordPress Security Whitepaper](https://wordpress.org/about/security/)

## Brute Force Attacks

Unlike hacks that focus on vulnerabilities in software, a Brute Force Attack aims at being the simplest kind of method to gain access to a site: it tries usernames and passwords, over and over again, until it gets in. Often deemed 'inelegant', they can be very successful when people use passwords like '123456' and usernames like 'admin.'

They are, in short, an attack on the weakest link in any website's security… you.

Due to the nature of these attacks, you may find your server's memory goes through the roof, causing performance problems. This is because the number of http requests (that is the number of times someone visits your site) is so high that servers run out of memory.

This sort of attack is not endemic to WordPress, it happens with every webapp out there, but WordPress is popular and thus a frequent target.

### Protect Yourself {#protect-yourself}

A common attack point on WordPress is to hammer the `wp-login.php` file over and over until they get in or the server dies. You can do some things to protect yourself.

#### Don't use the 'admin' username {#dont-use-the-admin-username}

The majority of attacks assume people are using the username 'admin' due to the fact that early versions of WordPress defaulted to this. If you are still using this username, make a new account, transfer all the posts to that account, and change 'admin' to a subscriber (or delete it entirely).

You can also use the plugin [Change Username](https://wordpress.org/plugins/change-username/) to change your username.

#### Good Passwords {#good-passwords}

The goal with your password is to make it hard for other people to guess and hard for a brute force attack to succeed. Many [automatic password generators](http://www.google.com/?q=password+generator) are available that can be used to create secure passwords.

WordPress also features a password strength meter which is shown when changing your password in WordPress. Use this when changing your password to ensure its strength is adequate.

You can use the [Force Strong Password](https://wordpress.org/plugins/force-strong-passwords/) plugin to force users to set strong passwords.

Things to avoid when choosing a password:

* Any permutation of your own real name, username, company name, or name of your website.
* A word from a dictionary, in any language.
* A short password.
* Any numeric-only or alphabetic-only password (a mixture of both is best).

A strong password is necessary not just to protect your blog content. A hacker who gains access to your administrator account is able to install malicious scripts that can potentially compromise your entire server.

To further increase the strength of your password, you can enable [Two Step Authentication](https://codex.wordpress.org/Two Step Authentication) to further protect your blog.

#### Plugins {#plugins}

There are many [plugins available to limit the number of login attempts](https://wordpress.org/plugins/tags/brute-force) made on your site. Alternatively, there are also many [plugins you can use to block people from accessing wp-admin](https://wordpress.org/plugins/search.php?q=admin+rename) altogether.

### Protect Your Server {#protect-your-server}

If you decide to lock down wp-login.php or wp-admin, you may find you get a 404 or 401 error when accessing those pages. To avoid that, you will need to add the following to your .htaccess file.

```
ErrorDocument 401 default  
```

You can have the 401 point to 401.html, but the point is to aim it at _not_ WordPress.

For Nginx you can use the `error_page` directive but must supply an absolute url.

```
error_page  401  http://example.com/forbidden.html;  
```

On IIS web servers you can use the `httpErrors` element in your web.config, set `errorMode="custom"`:

```
<httpErrors errorMode="Custom">  
	<error statusCode="401"
	subStatusCode="2"
	prefixLanguageFilePath=""
	path="401.htm"
	responseMode="File" />
</httpErrors>
```

#### Password Protect wp-login.php {#password-protect-wp-login-php}

Password protecting your wp-login.php file (and wp-admin folder) can add an extra layer to your server. Because password protecting wp-admin can break any plugin that uses ajax on the front end, it's usually sufficient to just protect wp-login.php.

To do this, you will need to create a .htpasswd file. Many hosts have tools to do this for you, but if you have to do it manually, you can use this [htpasswd generator](http://www.htaccesstools.com/htpasswd-generator/). Much like your .htaccess file (which is a file that is only an extension), .htpasswd will also have no prefix.

You can either put this file outside of your public web folder (i.e. not in /public_html/ or /domain.com/, depending on your host), or you _can_ put it in the same folder, but you'll want to do some extra security work in your .htaccess file if you do.

Speaking of which, once you've uploaded the .htpasswd file, you need to tell .htaccess where it's at. Assuming you've put .htpasswd in your user's home directory and your htpasswd username is mysecretuser, then you put this in your .htaccess:

```
# Stop Apache from serving .ht* files
<Files ~ "^\\.ht">  
	Order allow,deny
	Deny from all
</Files>  

# Protect wp-login.php
<Files wp-login.php>
	AuthUserFile ~/.htpasswd
	AuthName "Private access"
	AuthType Basic
	require user mysecretuser
</Files>
```

The actual location of AuthUserFile depends on your server, and the 'require user' will change based on what username you pick.

If you are using Nginx you can password protect your wp-login.php file using the [HttpAuthBasicModule](http://wiki.nginx.org/HttpAuthBasicModule). This block should be inside your server block.

```
location /wp-login.php {
	auth_basic "Administrator Login";
	auth_basic_user_file .htpasswd;
}
```

The filename path is relative to directory of nginx configuration file nginx.conf

The file should be in the following format:

```
user:pass
user2:pass2
user3:pass3
```

Unfortunately there is no easy way of configuring a password protected wp-login.php on Windows Server IIS. If you use a .htaccess processor like Helicon Ape, you can use the .htaccess example mentioned above. Otherwise you'd have to ask your hosting provider to set up Basic Authentication.

All passwords must be encoded by function `crypt(3)`. You can use an online [htpasswd generator](http://www.htaccesstools.com/htpasswd-generator/) to encrypt your password.

#### Limit Access to wp-login.php by IP {#limit-access-to-wp-login-php-by-ip}

If you are the only person who needs to login to your Admin area and you have a fixed IP address, you can deny wp-login.php (and thus the wp-admin/ folder) access to everyone but yourself via an .htaccess or web.config file. This is often referred to as an _IP whitelist_.

**Note:** Beware your ISP or computer may be changing your IP address frequently, this is called dynamic IP addressing, rather than fixed IP addressing. This could be used for a variety of reasons, such as saving money. If you suspect this to be the case, find out out how change your computer's settings, or contact your ISP to obtain a fixed address, in order to use this procedure.

In all examples you have to replace 203.0.113.15 with your IP address. Your Internet Provider can help you to establish your IP address. Or you can use an online service such as [What Is My IP](http://www.whatismyip.com/).

Examples for multiple IP addresses are also provided. They're ideal if you use more than one internet provider, if you have a small pool of IP addresses or when you have a couple of people that are allowed access to your site's Dashboard.

Create a file in a plain text editor called .htaccess and add:

```
# Block access to wp-login.php.
<Files wp-login.php>
	order deny,allow
	allow from 203.0.113.15
	deny from all
</Files>
```

You can add more than one allowed IP address using:

```
# Block access to wp-login.php.
<Files wp-login.php>
	order deny,allow  
	allow from 203.0.113.15
	allow from 203.0.113.16
	allow from 203.0.113.17
	deny from all
</Files>
```

Are you using Apache 2.4 and Apache module [mod_authz_host](https://httpd.apache.org/docs/2.4/mod/mod_authz_host.html)? Then you have to use a slightly different syntax:

```
# Block access to wp-login.php.
<Files wp-login.php>
	Require ip 203.0.113.15
</Files>
```

If you want to add more than one IP address, you can use:

```
# Block access to wp-login.php.
<Files wp-login.php>
	Require ip 203.0.113.15 203.0.113.16 203.0.113.17
	# or for the entire network:
	# Require ip 203.0.113.0/255.255.255.0
</Files>
```

For Nginx you can add a location block inside your server block that works the same as the Apache example above.

```
error_page  403  http://example.com/forbidden.html;
location /wp-login.php {
	allow   203.0.113.15
	# or for the entire network:
	# allow   203.0.113.0/24;
	deny    all;
}
```

Note that the order of the deny/allow is of the utmost importance. You might be tempted to think that you can switch the access directives order and everything will work. In fact it doesn't. Switching the order in the above example has the result of denying access to all addresses.

Again, on IIS web servers you can use a web.config file to limit IP addresses that have access. It's best to add this in an additional `<location` directive.

```
<location path="wp-admin">
	<system.webServer>
		<security>
			<ipSecurity allowUnlisted="false"> <!-- this rule denies all IP addresses, except the ones mentioned below -->
				<!-- 203.0.113.x is a special test range for IP addresses -->
				<!-- replace them with your own -->
				<add ipAddress="203.0.113.15" allowed="true" />
				<add ipAddress="203.0.113.16" allowed="true" />
			</ipSecurity>
		</security>
	</system.webServer>
</location>
```

#### Deny Access to No Referrer Requests {#deny-access-to-no-referrer-requests}

Extended from [Combatting Comment Spam](https://codex.wordpress.org/Combating_Comment_Spam/Denying_Access#Deny_Access_to_No_Referrer_Requests), you can use this to prevent anyone who isn't submitting the login form from accessing it:

```
# Stop spam attack logins and comments
<IfModule mod_rewrite.c>
	RewriteEngine On
	RewriteCond %{REQUEST_METHOD} POST
	RewriteCond %{REQUEST_URI} .(wp-comments-post|wp-login)\.php*
	RewriteCond %{HTTP_REFERER} !.*example.com.* [OR]
	RewriteCond %{HTTP_USER_AGENT} ^$
	RewriteRule (.*) http://%{REMOTE_ADDR}/$1 [R=301,L]
</ifModule>
```

Nginx – Deny Access to No Referrer Requests

```
location ~* (wp-comments-posts|wp-login)\\.php$ {
	if ($http_referer !~ ^(http://example.com) ) {
		return 405;
	}
}
```

Windows Server IIS – Deny access to no referrer requests:

```
<rule name="block_comments_without_referer" patternSyntax="ECMAScript" stopProcessing="true">
<match url="(.*)" ignoreCase="true" />
	<conditions logicalGrouping="MatchAll">
		<add input="{URL}" pattern="^/(wp-comments-post|wp-login)\.php" negate="false"/>
		<add input="{HTTP_REFERER}" pattern=".*example\.com.*" negate="true" />
		<add input="{HTTP_METHOD}" pattern="POST" />
	</conditions>
	<action type="CustomResponse" statusCode="403" statusReason="Forbidden: Access is denied." statusDescription="No comments without referrer!" />
</rule>
```

Change example.com to your domain. If you're using Multisite with mapped domains, you'll want to change example.com to `(example.com|example.net|example.org)` and so on. If you are using Jetpack comments, don't forget to add jetpack.wordpress.com as referrer: `(example.com|jetpack\.wordpress\com)`

#### ModSecurity {#modsecurity}

If you use ModSecurity, you can follow the advice from [Frameloss – Stopping brute force logins against WordPress](http://www.frameloss.org/2011/07/29/stopping-brute-force-logins-against-wordpress/). This requires root level access to your server, and may need the assistance of your webhost.

If you're using ModSecurity 2.7.3, you can add the rules into your .htaccess file instead.

#### Fail2Ban {#fail2ban}

Fail2ban is a Python daemon that runs in the background. It checks the logfiles that are generated by Apache (or SSH for example), and on certain events can add a firewall rule. It uses a so called filter with a regular expression. If that regular expression happens for example 5 times in 5 minutes, it can block that IP address for 60 minutes (or any other set of numbers).

Installing and setting up Fail2ban requires root access.

#### Blocklists {#blocklists}

It appears that most brute force attacks are from hosts from Russia, Kazachstan and Ukraine. You can choose to block ip-addresses that originate from these countries. There are blocklists available on the internet that you can download. With some shell-scripting, you can then load blockrules with iptables.

You have to be aware that you are blocking legitimate users as well as attackers. Make sure you can support and explain that decision to your customers.

Besides blocklists per country, there are lists with ip-addresses of well-known spammers. You can also use these to block them with iptables. It's good to update these lists regularly.

Setting up of blocklists and iptables requires root access.

#### Cloud/Proxy Services {#cloud-proxy-services}

Services like CloudFlare and Sucuri CloudProxy can also help mitigate these attacks by blocking the IPs before they reach your server.

### See Also {#see-also}

* [Sucuri: Protecting Against WordPress Brute Force Attacks](http://blog.sucuri.net/2013/04/protecting-against-wordpress-brute-force-attacks.html)
* [How to: Protect WordPress from brute-force XML-RPC attacks](https://www.saotn.org/how-to-wordpress-protection-from-brute-force-xml-rpc-attacks/)
* [Liquid Web: ModSecurity Rules To Alleviate Brute Force Attacks](http://kb.liquidweb.com/wordpress-modsecurity-rules/)
* [HostGator: Password Protecting wp-login](http://support.hostgator.com/articles/specialized-help/technical/wordpress/wordpress-login-brute-force-attack)
* [Stopping Brute-force Logins](http://www.frameloss.org/2011/07/29/stopping-brute-force-logins-against-wordpress/)
* [Swiss Army Knife for WordPress (SAK4WP)](https://github.com/orbisius/sak4wp/) – Free Open Source Tool that can help you protect your wp-login.php and /wp-admin/ but not /wp-admin/admin-ajax.php with one click and much more

## Two Step Authentication

Also known as Two-Factor Authentication.

Two-step authentication is showing up all over the Internet as more sites look for better ways to secure logins, which are the weakest part of anything a user does online.

### What is Two-Step Authentication {#what-is-two-step-authentication}

Passwords are the de-facto standard for logging in on the web, but they're relatively easy to break. Even if you make good passwords and change them regularly, they need to be stored wherever you're logging in, and a server breach can leak them. There are three ways to identify a person, things they are, things they have, and things they know.

Logging in with a password is single-step authentication. It relies only on something you know. Two-step authentication, by definition, is a system where you use two of the three possible factors to prove your identity, instead of just one. In practice, however, current two-step implementations still rely on a password you know, but use your Phone or another device to authenticate with something you have.

#### Three Possible Factors {#three-possible-factors}

There are three possible ways to identify users.

##### Something You Are {#something-you-are}

There are a lot of properties that are unique to each user and can be used to identify them. The most popular is fingerprints, but retinas, voice, DNA, or anything else specific to an individual will work. This is called biometric information because these pieces of information all belong to a person's biology.

Biometric factors are interesting because they are not easily forged and the user can never lose or forget them. However, biometric authentication is tricky because a lost fingerprint can never be replaced. If hackers were to gain access to a database of fingerprints, there is no way that users could reset them or get a new set.

In 2013, Apple released TouchID which lets users unlock their iPhones using their fingerprints. This technology is interesting because the fingerprints are stored locally on the phone, not in the cloud where they would be easier for hackers to steal. There are still trade-offs with this kind of approach, but it is the most widespread consumer use of biometric authentication to date.

##### Something You Have {#something-you-have}

Also known as the possession factor, users can be identified by the devices which they carry. Traditionally, a company that wanted to enable two-step authentication would distribute secure keychain fobs to users. The keychain fobs would display a new number every 30 seconds, and that number would be needed to be typed along with the password every time a user logged in.

Modern two-step authentication more frequently relies on a user's smartphone than on a new piece of hardware. One common model of this uses SMS in order to provide an easy second factor. When the user enters their password, they are sent a text message with a unique code. By entering that code, after the password, they supposedly prove that they also have their phone. Unfortunately, SMS is not a secure communication channel, so smartphone apps and [plugins](#plugins-for-two-step-authentication) have been developed to create that secure channel.

##### Something You Know {#something-you-know}

The most familiar form of authentication is the knowledge factor, or password. As old as [Open Sesame](http://en.wikipedia.org/wiki/Open_Sesame_(phrase)), passwords have long been a standard for anonymous authentication. In order for a knowledge factor to work, both parties need to know the password, but other parties must not be able to find or guess it.

The first challenge is in exchanging the password with the trusted party safely. On the web, when you register for a new site, your password needs to be sent to that site's servers and might be intercepted in the process (which is why you should always check for SSL when registering or logging in — [Administration Over SSL](https://wordpress.org/support/article/administration-over-ssl/)).

Once the password has been received, it must be kept secret. The user shouldn't write it down or use it anywhere else, and the site needs to carefully guard its database to ensure that hackers can't access the passwords.

Finally, the password needs to be verified. When a user visits the site, they need to be able to provide the password and have it verified against the stored copy. This exchange can also be intercepted (and so should always be done over SSL — [Administration Over SSL](https://wordpress.org/support/article/administration-over-ssl/)) and exposes the user to another risk.

#### Benefits {#benefits}

There are a lot of different places to increase the security of a site, but the WordPress Security Team [has said](http://vip.wordpress.com/security/) that "The weakest link in the security of anything you do online is your password," so it makes sense to put energy into strengthening that aspect of your site.

#### Drawbacks {#drawbacks}

As the name implies, two-step authentication is adding a step to a process that can already be long and painful. While most very high-security logins are protected by two-step authentication today, most consumer applications barely offer it as an option if they offer it at all. This is because users are less likely to sign up for and log in to a service if it is more difficult.

Two-step authentication can also prevent legitimate logins. If a user forgets their phone at home and has two-step authentication enabled, then they won't be able to access their account. This is one of the main reasons why smartphones have been useful for two-step authentication — users are more likely to be carrying their phones than almost anything else.

#### Plugins for Two-Step Authentication {#plugins-for-two-step-authentication}

You can [search for two-step authentication plugins](https://wordpress.org/plugins/tags/two-factor-authentication) available in the WordPress.org plugin repository. Here are some of the most popular ones to get you started (in alphabetical order):

* [Duo](https://wordpress.org/plugins/duo-wordpress/)
* [Google Authenticator](https://wordpress.org/plugins/google-authenticator/)
* [Rublon](https://wordpress.org/plugins/rublon/)
* [Two-Factor](https://wordpress.org/plugins/two-factor/)
* [WordFence](https://wordpress.org/plugins/wordfence/)

### Related {#related}

* [Brute Force Attacks](https://wordpress.org/support/article/brute-force-attacks/)

## Changelog

- 2022-10-25: Copying the [original content](https://wordpress.org/support/article/why-should-i-use-https/), and [original content](https://wordpress.org/support/article/administration-over-ssl/), and [original content](https://wordpress.org/support/article/hardening-wordpress/), and [original content](https://wordpress.org/support/article/brute-force-attacks/), and [original content](https://wordpress.org/support/article/two-step-authentication/).
