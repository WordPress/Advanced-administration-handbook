# Network Admin Settings Screen

The **Network Admin Settings** is where a network admin sets and changes settings for the network as a whole. The first site is the main site in the network and network settings are pulled from that original site’s options.

![](https://i0.wp.com/wordpress.org/documentation/files/2020/02/superadmin-options.png?fit=1024%2C751&ssl=1)

## Operational Settings

**Network Name**

What you would like to call this website.

**Network Admin Email**

Registration and support emails will come from this address. An address such as _support@example.com_ is recommended.

## Registration Settings

**Allow new registrations**

Disable or enable registration and who or what can be registered. (Default is disabled.)

* Registration is disabled. (default)
* User accounts may be registered.
* Logged in users may register new sites.
* Both sites and user accounts can be registered.

**Registration notification**

Send the network admin an email notification every time someone registers a site or user account.

**Add New Users**

Allow site administrators to add new users to their site via the "Users -> Add New" page.

**Banned Names**

Users are not allowed to register these sites. Separate names by spaces.

**Limited Email Registrations**

If you want to limit site registrations to certain domains. Enter one domain per line.

**Banned Email Domains**

If you want to ban domains from site registrations. Enter one domain per line.

## New Site Settings

**Welcome Email**

```
The welcome email sent to new site owners.

Dear User,

Your new SITE_NAME blog has been successfully set up at:
BLOG_URL

You can log in to the administrator account with the following information:
Username: USERNAME
Password: PASSWORD
Login Here: BLOG_URLwp-login.php

We hope you enjoy your new blog.
Thanks!

--The Team @ SITE_NAME
```

**Welcome User Email**

```
The welcome email sent to new users.

Dear User,

Your new account is set up.

You can log in with the following information:
Username: USERNAME
Password: PASSWORD
LOGINLINK

Thanks!

--The Team @ SITE_NAME
```

**First Post**

The first post on a new site.

```
Welcome to <a href="SITE_URL">SITE_NAME</a>. This is your first post. Edit or delete it, then start blogging!
```

**First Page**

The first page on a new site.

**First Comment**

The first comment on a new site.

**First Comment Author**

The author of the first comment on a new site.

**First Comment URL**

The URL for the first comment on a new site.

## Upload Settings

**Site upload space**

Limit total size of files uploaded to [ 50 ] MB.

**Upload file types**

Default is `jpg jpeg png gif mp3 mov avi wmv midi mid pdf m2ts`.

Note: Adding arbitrary file types will not work unless a corresponding function is also hooked to [upload_mimes](https://developer.wordpress.org/reference/hooks/upload_mimes/) filter. See the `wp_get_mime_types` function in [wp-includes/functions.php](https://github.com/WordPress/WordPress/blob/master/wp-includes/functions.php) for the current default set of supported mime-types / file extensions. Adding mime types in the 'upload file types' field not listed in the default set will **NOT** work unless you've added them using the [upload_mimes](https://developer.wordpress.org/reference/hooks/upload_mimes/) filter! Uploading files with mime types not supported (without adding them using the filter) will fail with the message "Sorry, this file type is not permitted for security reasons".

**Max upload file size**

Default is [ 1500 ] KB.

## Language Settings

**Default Language**

Default is English.

## Menu Settings

**Enable administration menus**

* Plugins

On WordPress Multisite the default setting for plugins is disabled. This means your users won't have access to the plugin admin panel inside their dashboard unless you first enable access to plugins network wide.

## Changelog

- 2023-04-25: Original content from [Network Admin Settings Screen](https://wordpress.org/documentation/article/network-admin-settings-screen/).
