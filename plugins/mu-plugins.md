# Must Use Plugins

Must-use plugins (a.k.a. mu-plugins) are plugins installed in a special directory inside the content folder and which are automatically enabled on all sites in the installation. Must-use plugins do not show in the default list of plugins on the Plugins page of wp-admin (although they do appear in a special Must-Use section) and cannot be disabled except by removing the plugin file from the must-use directory, which is found in **wp-content/mu-plugins** by default. For web hosts, mu-plugins are commonly used to add support for host-specific features, especially those where their absence could break the site.

To change the default directory manually, define `WPMU_PLUGIN_DIR` and `WPMU_PLUGIN_URL` in [wp-config.php](https://wordpress.org/documentation/article/editing-wp-config-php/).

## Features
* Always on, no need to enable via admin and users cannot disable by accident.
* Can be enabled simply by uploading file to the mu-plugins directory, without having to log-in.
* Loaded by PHP, in alphabetical order, before normal plugins, meaning API hooks added in an mu-plugin apply to all other plugins even if they run hooked-functions in the global namespace.

## Caveats
Despite its suitability for many special cases, the mu-plugins system is not always ideal and has several downsides that make it inappropriate in certain circumstances. Below are several important caveats to keep in mind:

* Plugins in the must-use directory will not appear in the update notifications nor show their update status on the plugins page, so you are responsible for learning about and performing updates on your own.
* Activation hooks are not executed in plugins added to the must-use plugins folder. These hooks are used by many plugins to run installation code that sets up the plugin initially and/or uninstall code that cleans up when the plugin is deleted. Plugins depending on these hooks may not function in the mu-plugins folder, and as such all plugins should be carefully tested specifically in the mu-plugins directory before being deployed to a live site.
* WordPress only looks for PHP files right inside the mu-plugins directory, and (unlike for normal plugins) not for files in subdirectories. You may want to create a proxy PHP loader file inside the mu-plugins directory:

```
<?php // mu-plugins/load.php
require WPMU_PLUGIN_DIR.'/my-plugin/my-plugin.php';
```

## History and Naming

The _mu-plugins_ directory was originally implemented by WPMU (Multi-User) to offer site admins an easy way to activate plugins by default on all blogs in the farm. There was a need for this feature because at the time the multi-user-specific code did not offer ways of achieving this effect using the site admin section (today the renamed “Multisite WordPress” has features to manage plugins from inside the admin).

The code handling /mu-plugins/ was merged into the main WordPress code on 03/07/09 with [this changeset](https://core.trac.wordpress.org/changeset/10737) a full 10 months before the wpmu codebase was initially merged, and all WP sites could take advantage of autoloaded plugins, whether they had MU/Multisite enabled or not. The feature is useful for all types of WP installations depending on circumstances, so this makes sense.

In this process the name “mu plugins” became a misnomer because it did not apply exclusively to multisite installs and because “MU” was not even being used anymore to refer to WP installations with multiple blogs. Despite this, the name was kept and **re-interpreted to mean “must-use plugins”**, i.e. these are plugins that must always be used, thus they are autoloaded on all sites regardless of the settings in the Plugins pane of wp-admin.

Thus “Must-Use” is effectively a [Backronym](http://en.wikipedia.org/wiki/Backronym), like [PHP](https://wordpress.org/documentation/article/wordpress-glossary/#PHP) (which originally meant “Personal Home Page” but was later re-interpreted as meaning “PHP Hypertext Preprocessor”, which is also a [Recursive Acronym](http://en.wikipedia.org/wiki/Recursive_acronym)).

## Source Code
* `get_mu_plugins()` is located in [wp-admin/includes/plugin.php](https://core.trac.wordpress.org/browser/tags/4.5.3/src/wp-admin/includes/plugin.php#L0).
* `wp_get_mu_plugins()` is located in [wp-includes/load.php](https://core.trac.wordpress.org/browser/tags/4.5.3/src/wp-includes/load.php#L0).

## Changelog

- 2022-09-11: Original content from [Must Use Plugins](https://wordpress.org/documentation/article/must-use-plugins/). Minor additions and copy-editing.
