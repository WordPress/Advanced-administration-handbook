# WordPress Users and Roles

WordPress has five default user role types — six if WordPress Multisite is enabled:

1. __Super Admins__ are superusers in WordPress multisite networks. They can create and delete sites on the network and manage the network, all its sites, users, plugins, themes, and options. Super Admins also have regular Administrator privileges within any site in the network. Only an existing Super Admin can grant or remove Super Admin privileges for another user. 
2. __Administrators__ are superusers in single WordPress sites. They can update WordPress core, plugins, and themes. They can install, delete, and edit themes and plugins. Administrators can edit files and users, add users, and delete users. 
3. __Editors__ can create, edit, publish, and delete pages and posts authored by them and other users, including private and published content. They can manage taxonomies, moderate comments, and upload files. 
4. __Authors__ can create, edit, publish, and delete their own posts. They also can upload files. 
5. __Contributors__ can create, edit, and delete their own posts but not publish them.
6. __Subscribers__ have no content privileges and can only read publicly accessible content, but unlike site visitors without an account, subscribers can access the WordPress back-end interface and edit their basic account settings like all other users. 

When a WordPress Multisite network or individual site is first installed, Super Admin and Administrator accounts are automatically created.

Super Administrators, Administrators, and Editors are all considered "trusted" users, meaning they have capabilities that can be abused to damage or compromise a WordPress site. 

Keep in mind that plugins and themes can modify the default user roles and capabilities. Misconfigured or vulnerable plugins and themes might allow any user to exercise arbitrary privileges or escalate a Subscriber, Contributor, Author, or Editor to Administrator privileges. 
