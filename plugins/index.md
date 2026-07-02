# Plugins

Plugins extend WordPress by adding or changing features. Most plugin tasks can be handled from the WordPress admin area, but some advanced administration tasks require working with plugin files directly or understanding plugins that load outside the normal plugin list.

This section covers advanced plugin administration topics. It is intended for site administrators, developers, and hosting teams who need to manage plugin behavior beyond normal activation, deactivation, and updates.

## Topics

* [File Editor Screen](https://developer.wordpress.org/advanced-administration/plugins/editor-screen/): Learn how the built-in plugin file editor works, where to find it, and what to consider before editing plugin code from wp-admin.
* [Must Use Plugins](https://developer.wordpress.org/advanced-administration/plugins/mu-plugins/): Learn how must-use plugins load, how they differ from normal plugins, and why hosts or site owners may use them for code that should always run.

## Before editing plugin files

Editing plugin files directly can break your site if the code contains an error. If possible, make changes in a staging environment first and keep a current backup of the original file.

If a change causes an error and you cannot access wp-admin, use [SFTP/FTP](https://developer.wordpress.org/advanced-administration/upgrade/ftp/) or your hosting file manager to restore the file from a backup or temporarily rename the affected plugin folder.

## Plugin development

This section focuses on administering plugins on an existing WordPress site. It does not cover how to build, package, or submit plugins.

For information about building plugins, see the [Plugin Developer Handbook](https://developer.wordpress.org/plugins/).
