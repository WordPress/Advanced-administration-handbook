# Loopbacks

=========

Search

In this article
---------------

*   [Troubleshooting loopback issues](#troubleshooting-loopback-issues)
*   [Common troubleshooting steps](#common-troubleshooting-steps)

[↑ Back to top](#wp--skip-link--target)

A loopback is when your own server or website tries to connect to it self.

WordPress uses his functionality to trigger scheduled posts, and other scheduled events that plugins or themes may introduce.

They are also used when making changes in the Plugin- or Theme-editor, by connecting back to the website and making sure that the changes made does not break your website.

[Troubleshooting loopback issues](#troubleshooting-loopback-issues)
-------------------------------------------------------------------

If you are having problems with scheduled posts or other timed events not running, or seeing Site Health warnings about loopbacks failing, you may want to troubleshoot these.

The most common cause of loopback failures is a plugin or theme conflict, you should start by following the normal troubleshooting steps:

[Common troubleshooting steps](#common-troubleshooting-steps)
-------------------------------------------------------------

*   Deactivating **all plugins** (yes, all) to see if this resolves the problem. If this works, re-activate the plugins one by one until you find the problematic plugin(s). If you can’t get into your admin dashboard, try resetting the plugins folder by [SFTP/FTP](https://wordpress.org/documentation/article/ftp-clients/) or PhpMyAdmin (read [“How to deactivate all plugins when you can’t log in to wp-admin”](https://wordpress.org/documentation/article/faq-troubleshooting/) if you need help). Sometimes, an apparently inactive plugin can still cause problems. Also remember to deactivate any plugins in the mu-plugins folder. The easiest way is to rename that folder to `mu-plugins-old`
*   Switching to the Twenty Nineteen theme to rule out any theme-specific problems. If you can’t log in to change themes, you can remove the theme folders via [SFTP/FTP](https://wordpress.org/documentation/article/ftp-clients/) so the only one is `twentytwenty`. That will force your site to use it.
*   If you can install plugins, install “Health Check”: [wordpress.org/plugins/health-check](https://wordpress.org/plugins/health-check/). On the troubleshooting tab, you can click the button to disable all plugins and change the theme for you, while you’re still logged in, **without affecting normal visitors to your site**.

Was this article helpful? How could it be improved? [Cancel reply](/documentation/article/loopbacks/#respond)
-------------------------------------------------------------------------------------------------------------

[Log in](https://login.wordpress.org/?redirect_to=https%3A%2F%2Fwordpress.org%2Fdocumentation%2Farticle%2Floopbacks%2F&locale=en_US) to submit feedback. If you need suppport with something that wasn't covered by this article, please post your question in the [support forums](https://wordpress.org/documentation/forums/).

First published

May 24, 2020

Last updated

January 13, 2023

Get more help
-------------

Can’t find what you need? Lean on the experience of the WordPress community.

*   [Ask questions in the support forums ↗︎](https://wordpress.org/support/forums/)
*   [Check out the developer documentation ↗︎](https://developer.wordpress.org/)




## Changelog

- 2023-01-15: Nothing here, yet.
