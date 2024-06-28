# WordPress Users and Roles


WordPress itself defines 5 default types of users (6 if WordPress Multisite is enabled). They are:

*   Super Administrator (If WordPress Multisite is enabled) - a superuser with access to the special WordPress Multisite administration features and all other normal administration features.
*   Administrator (slug: 'administrator') - a superuser for the individual WordPress website with access to all of the administration features in the website.
*   Editor (slug: 'editor') - a user who can publish posts and manage the posts of other users.
*   Author (slug: 'author') - a user who can publish posts and manage the user's own posts.
*   Contributor (slug: 'contributor') - a user who can write and manage the user's own posts but cannot publish them.
*   Subscriber (slug: 'subscriber') - a user who can manage the user's own profile only.

Super Administrators, Administrators, and Editors are all considered "trusted" users, meaning they have capabilities that could be abused to damage or compromise a WordPress site.

When WordPress is first installed, an Administrator account is automatically set up.

Plugins and themes can modify existing, as well as add additional types of, users and capabilities to WordPress beyond the defaults. These additional options are commonly used by plugins and themes to manage the functionality they add to WordPress.
