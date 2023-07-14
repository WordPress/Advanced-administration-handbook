# Network Admin

## Network Admin

**The Network Admin Screen** is the central access point to the various options necessary to administer the [Multisite (or Network)](https://wordpress.org/documentation/article/glossary#multisite) capabilities of WordPress. The information below is directed specifically for Network Administrators. Other users should see [Administration Screens](https://wordpress.org/documentation/article/administration-screens/) for information on using WordPress.

The Network Admin link is only visible after you [Create A Network](https://developer.wordpress.org/advanced-administration/multisite/create-network/). The menu will appear in the upper right of the menu bar, and is only visible to super admins. It has been moved to a separate location with the admin area, and is available from the admin area of any site, as long as you are logged in as the super admin user.

When visiting Network Admin you will see the Dashboard screen. This looks similar to a site dashboard, with one additional widget, and the site specific widgets removed. The Right Now widget has quick links to the Create a Site and Create a User screens, as well as search boxes to quickly find sites and users.

Each screen is accessed via the main navigation menu, presented in the boxes below. The links in those boxes will lead you to sections of this article describing those screens. From those sections, you can navigate to articles detailing more information about each screen.

### Dashboard {#dashboard}

The Dashboard is information central and tells you about your network sites, provides news from the WordPress community, gives access to your plugins, and shows other WordPress news.

### Sites {#sites}

Use the [Network Admin Sites Screen](https://wordpress.org/documentation/articles/network-admin-sites/screen) to review and manage the various sites that are part of your network. These sites will be either subdirectory or subdomain sites as determined by how the network was configured. From this screen you can access Info, Users, Themes, and Settings for each site in your Network.

Use the [Add New Sites Screen](https://developer.wordpress.org/advanced-administration/multisite/admin/#add-site) to add new sites to your network.

### Users {#users}

The [Network Admin Users Screen](https://codex.wordpress.org/Network_Admin_Users_Screen) is where Network Admin personnel manages users and [Add New Users Screen](https://codex.wordpress.org/Network_Admin_Users_Screen#Add_User) is used to add new users.

### Themes {#themes}

The [Network Admin Themes Screen](https://codex.wordpress.org/Network_Admin_Themes_Screen) allows you to control which themes site administrators can use for each site. It does not activate or deactivate which theme a site is currently using. If the network admin disables a theme that is in use, it can still remain selected on that site. If another theme is chosen, the disabled theme will not appear in the site's Appearance > Themes screen. Themes can be enabled on a site by site basis by the network admin on the Edit Site screen you go to via the Edit action link on the Sites screen.

To add new themes, refer to the [Add New Theme](https://codex.wordpress.org/Network_Admin_Themes_Screen#Add_New_Theme) to understand the process of finding and installing new themes for your network.

Use the Theme Editor to edit the various files that comprise your Themes. The [Theme Editor Screen](https://codex.wordpress.org/Network_Admin_Themes_Screen#Theme_Editor) allows you to designate which theme you want to edit then displays the files in that theme. Each file (Template and CSS) in the theme can be edited in the large text box.

### Plugins {#plugins}

The [Network Admin Plugins Screen](https://codex.wordpress.org/Network_Admin_Plugins_Screen) allow you to add new features to your WordPress network that don't come standard with the default installation. There are a rich variety of available Plugins for WordPress, and plugin installation and management is a snap.

Refer to the [Add New Plugins](https://codex.wordpress.org/Network_Admin_Plugins_Screen#Add_New_Plugins) to add new plugins. For information on downloading and installing plugins, see [Managing Plugins](https://wordpress.org/documentation/article/manage-plugins/).

Using the [Plugin Editor](https://codex.wordpress.org/Network_Admin_Plugins_Screen#Plugin_Editor), you can modify the source code of all your plugins.

### Settings {#settings}

The [Network Admin Settings Screen](https://wordpress.org/documentation/article/network-admin-settings-screen/) is where a network admin sets and changes settings for the network as a whole. The first site is the main site in the network and network settings are pulled from that original site's options.

Also, [Network Setup](https://wordpress.org/documentation/article/network-admin-settings-screen/) information that was used when [Creating the Network](https://developer.wordpress.org/advanced-administration/multisite/create-network/) can be accessed.

### Updates {#updates}

The [Network Admin Updates Screen](https://developer.wordpress.org/advanced-administration/multisite/admin/) controls update process of both network and sites. In the [Available Updates Screen](https://developer.wordpress.org/advanced-administration/multisite/admin/#available-updates), you can update WordPress core, themes and plugins. After you updates to the latest version of WordPress, you can upgrade all the sites on your network from [Upgrade Network Screen](https://wordpress.org/documentation/article/network-admin-updates-screen/#upgrade-network).  

## Network Admin Sites Screen

The **Network Admin Sites Screen** allows you to add a new site and control existing sites on your [network](https://wordpress.org/documentation/article/glossary#network).

[![](https://i1.wp.com/wordpress.org/support/files/2019/02/superadmin-sites.png?fit=1277%2C443&ssl=1)](https://wordpress.org/documentation/superadmin-sites-2/)
_Super Admin Sites_

### Sites {#sites}

Lists all sites on this network.  
- **Edit**: Click this link to go to Edit Site Screen to view/edit Settings of the site and add users.  
- **Backend**: Switch Administration Screens to the site's one.  
- **Deactivate / Activate**: Deactivate / Activate the site.  
- **Archive**: Archive the site (same as Deactivate, effectively)  
- **Spam**: Mark the site as spam. Makes it unavailable to use for anyone.  
- **Delete**: Delete the site.  
- **Visit**: Go to the website.

#### Add Site {#add-site}

Fill in the items and click the _Add Site_ button to add a new site into your network.

- **Site Address**: Only the characters a-z and 0-9 permitted.  
- **Site Title**: Name of the site.  
- **Admin Email**: Email address of the administrator of the new site. A new user will be created if the above email address is not in the database.  
The username and password will be mailed to this email address.

#### Edit Site {#edit-site}

The Edit Site screen is split up into tabs for easier data management. It is strongly suggested you not edit these fields unless you're sure you know what you're doing.

**Info**

This data is the basic information of the site. Domain, registration date, time of last update, and if it's public or mature.

[![](https://i2.wp.com/wordpress.org/support/files/2019/02/superadmin-sites-edit.png?fit=1277%2C633&ssl=1)](https://wordpress.org/documentation/superadmin-sites-edit/)
_Edit Site – Super Admin_

- **Users**: Users lists all the users of the site. It also has options to add new users, via either "Add Existing User" (i.e. a user on your network already) or "Add New User" (i.e. a new user to the network).
- **Themes**: Themes shows all the themes on the site. Network enabled themes are not shown on this screen.  
- **Settings**: All site settings are stored in this table. Do not edit anything here unless you know what you're doing.

## Network Admin Updates Screen

The **Network Admin Updates Screen** controls update process in the network. If an update is available, you'll see a notification appear in the Toolbar and navigation menu. Keeping your site updated is important for security. It also makes the internet a safer place for you and your readers. There are two screens under the [Network Admin](https://developer.wordpress.org/advanced-administration/multisite/admin/) > [Updates](https://developer.wordpress.org/advanced-administration/multisite/admin/#updates). In the default [Available Updates Screen](#available-updates), you can update WordPress, themes and plugins. After you updates to the latest version of WordPress, you can upgrade all the sites on your network from [Upgrade Network Screen](#upgrade-nework).

### Available Updates {#available-updates}

On this [Available Updates Screen](#available-updates), you can update to the latest version of WordPress, as well as update your themes and plugins from the [WordPress.org](https://wordpress.org/) repositories.

[![](https://wordpress.org/documentation/files/2019/04/network-available-updates-1024x590.png)](https://wordpress.org/documentation/files/2019/04/network-available-updates.png)

#### How to Update {#how-to-update}

**WordPress** Updating your WordPress installation is a simple one-click procedure: just click on the "Update Now" button when you are notified that a new version is available. In most cases, WordPress will automatically apply maintenance and security updates in the background for you.

**Themes and Plugins**: To update individual themes or plugins from this screen, use the checkboxes to make your selection, then click on the appropriate "Update" button. To update all of your themes or plugins at once, you can check the box at the top of the section to select all before clicking the update button.

**Translation**: Translation files are updated when it is needed. Click the "Update Translation" button when you are notified that a new translation is available.

### Upgrade Network {#upgrade-network}

This [Upgrade Network Screen](#upgrade-network) is used to upgrade all the sites in a [Network](https://wordpress.org/documentation/article/glossary#network) after a [WordPress upgrade](#available-updates) is completed. After a WordPress upgrade, you are reminded to visit the **Upgrade Networks** with a message such as, "Thank you for Updating! Please visit the Upgrade Network page to upgrade all of your sites."

[![](https://i1.wp.com/wordpress.org/support/files/2019/04/superadmin-update.png?fit=1024%2C584&ssl=1)](https://wordpress.org/documentation/files/2019/04/superadmin-update.png)

The Upgrade Networks feature will step through each site, five at a time, and make sure any database changes are applied. This menu item is only visible if you are logged in as a Super Admin role user. You can access it from any site in the network.

If for any reason a site does not get upgraded, each site should be upgraded when the admin for that site logs in to the administration for that site. Sites that have been deactivated will not be upgraded by this process, however, if a site is reactivated, the site will get upgraded when an admin for that site logs in to the dashboard for that site.

**Upgrade Network** Click this button to start the upgrade process. Clicking the Upgrade Network button will step through each site in the network, five at a time, and make sure any database updates are applied.

If a version update to core has not happened, clicking this button won't affect anything.

## Changelog

- 2022-10-21: Original content from [Network Admin](https://wordpress.org/documentation/article/network-admin/), [Network Admin Sites Screen](https://wordpress.org/documentation/article/network-admin-sites-screen/), and [Network Admin Updates Screen](https://wordpress.org/documentation/article/network-admin-updates-screen/).
