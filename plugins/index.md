# Plugins

Plugins extend WordPress by adding or changing features. Many routine plugin tasks, such as activation, deactivation, and updates, can be handled from the WordPress admin area, but some advanced administration tasks require working with plugin files directly or understanding plugins that load outside the normal plugin list.

This section covers advanced plugin administration topics. It is intended for site administrators, developers, and hosting teams who need to manage plugin behavior beyond normal activation, deactivation, and updates.

## Topics

* [Managing Plugins](https://wordpress.org/documentation/article/manage-plugins/): Learn how to install, activate, update, deactivate, and delete plugins from the WordPress admin area.
* [File Editor Screen](editor-screen/): Learn how the built-in plugin file editor works, where to find it, and what to consider before editing plugin code from wp-admin.
* [Must-Use Plugins](mu-plugins/): Learn how must-use plugins load, how they differ from normal plugins, and why hosts or site owners may use them for code that should always run.

## Before editing plugin files

Editing plugin files directly can break your site if the code contains an error. If possible, make changes in a staging environment first and keep a current backup of the original file.

If a change causes an error and you cannot access wp-admin, use [SFTP/FTP](../upgrade/ftp/) or your hosting file manager to restore the file from a backup or temporarily rename the affected plugin directory.

For a directly loaded must-use plugin, move its file out of the must-use plugins directory or rename it so it no longer ends in `.php`, for example `my-plugin.php.off`. WordPress loads every `.php` file in that directory. If a loader file loads code from a subdirectory, rename the loader file, not the subdirectory.

## Plugin development

This section focuses on administering plugins on an existing WordPress site. It does not cover how to build, package, or submit plugins.

For information about building plugins, see the [Plugin Developer Handbook](https://developer.wordpress.org/plugins/).
