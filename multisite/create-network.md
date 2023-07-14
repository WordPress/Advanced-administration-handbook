# Create A Network

You have the ability to create a [network](https://wordpress.org/documentation/article/glossary/#network) of [sites](https://wordpress.org/documentation/article/glossary/#site) by using the [multisite](https://wordpress.org/documentation/article/glossary/#multisite) feature. This article contains instructions for creating a multisite network. It is advised to read the post "[Before you Create a Network](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/)" first, as it contains important information about planning your network.

A multisite network can be very similar to your own personal version of WordPress.com. End users of your network can create their own sites on demand, just like end users of WordPress.com can create blogs on demand. If you do not have any need to allow end users to create their own sites on demand, you can create a multisite network in which only you, the administrator, can add new sites.

A multisite network is a collection of sites that all share the same WordPress installation core files. They can also share plugins and themes. The individual sites in the network are _virtual_ sites in the sense that they do not have their own directories on your server, although they do have separate directories for media uploads within the shared installation, and they do have separate tables in the database. **NOTE:** [Upgraded and can't find the Network Admin menu?](https://developer.wordpress.org/advanced-administration/multisite/administration/#network-admin-link-location).

## Step 0: Before You Begin {#step-0-before-you-begin}

Compared with a typical single WordPress installation a network installation has additional considerations. You must decide if you want to use subdomains or subfolders and how you want to manage them. Installing themes and plugins is different: for example, each individual site of a network can activate both, but install neither.

This guide describes how to install manually WordPress Multisite in your current WordPress installation. There are also available [ready-to-run packages](https://codex.wordpress.org/User:Beltranrubo/BitNami_Multisite) from BitNami.

**Please read [Before You Create A Network](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/) in full before continuing.**

## Step 1: Prepare Your WordPress {#step-1-prepare-your-wordpress}

Your existing WordPress site will be updated when creating a network. Unless this is a fresh install and you have nothing to lose, please [backup your database and files](https://developer.wordpress.org/advanced-administration/security/backup/).

Verify that [Pretty Permalinks](https://wordpress.org/documentation/article/using-permalinks/) work on your single WP instance.

Also deactivate all active plugins. You can reactivate them again after the network is created.

If you plan to [run WordPress out of its own directory](https://developer.wordpress.org/advanced-administration/server/wordpress-in-directory/), do that _before_ activating Multisite.

## Step 2: Allow Multisite {#step-2-allow-multisite}

To enable the Network Setup menu item, you must first define multisite in the [wp-config.php](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) file.

Open up `wp-config.php` and add this line **above** where it says `/* That's all, stop editing! Happy publishing. */`. If it doesn't say that anywhere, then add the line somewhere above the first line that begins with `require` or `include`:

```
/* Multisite */
define( 'WP_ALLOW_MULTISITE', true );
```

You will need to refresh your browser to continue.

## Step 3: Installing a Network {#step-3-installing-a-network}

The previous step enables the **Network Setup** item in your **Tools menu**. Use that menu item to go to the **Create a Network of WordPress Sites** screen.

To see an example of the Create a Network of WordPress Sites screen, look at [Administration](https://wordpress.org/documentation/article/administration-screens/) > [Tools](https://wordpress.org/documentation/article/administration-screens/#tools-managing-your-blog) > [Network Setup](https://wordpress.org/documentation/article/tools-network-screen/). The screen does not look exactly the same in all circumstances. The example shown is for an installation on `localhost`, which restricts the options available.

[![Create a Network of WordPress Sites page](https://i0.wp.com/wordpress.org/support/files/2018/11/network-create.png?fit=1024%2C743&ssl=1)](https://i0.wp.com/wordpress.org/support/files/2018/11/network-create.png?fit=1024%2C743&ssl=1)
_Create a Network of WordPress Sites page_

**Addresses of Sites in your Network**

You are given the choice between sub-domains and sub-directories, except when [existing settings](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/#wordpress-settings-requirements) restrict your choice.

You must choose one or the other. You can reconfigure your network to use the other choice after installation, despite the advice on the screen, but reconfiguring it might not be easy.

You only need wildcard DNS for on-demand domain-based sites, despite the advice that may be on the screen.

Once more: See [Before You Create A Network](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/).

* **Sub-domains** — a domain-based network in which on-demand sites use subdomains
* **Sub-directories** — a path-based network in which on-demand sites use paths

**Network Details**

These are filled in automatically, but you can make changes. Server Address The domain of the URL you are using to access your WordPress installation. Network Title The title of your network as a whole. Admin E-mail Address Your email address as super admin of the network as a whole.

Double-check the details and press the **Install** button.

**Note:** The installer may perform a check for wildcard subdomains when you have not configured them yet, or when you do not need them at all. Ignore the warning if it does not apply to your network. See the [Server Requirements](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/#server-requirements) section in [Before You Create A Network](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/) for information about wildcard subdomains.

## Step 4: Enabling the Network {#step-4-enabling-the-network}

To enable your network, follow the instructions on the Create a Network of WordPress Sites screen. The instructions that you see are customized for your installation. They might not be the same as the examples you see here.

[![Populated settings when creating a network of sites](https://i0.wp.com/wordpress.org/support/files/2018/11/tools-network-created.png?fit=1024%2C742&ssl=1)](https://i0.wp.com/wordpress.org/support/files/2018/11/tools-network-created.png?fit=1024%2C742&ssl=1)
_Populated settings when creating a network of sites_

Back up your existing `wp-config.php` and `.htaccess` files, unless this is a fresh install and you have nothing to lose.

There are two steps:

1. Add the specified lines to your [wp-config.php](https://developer.wordpress.org/advanced-administration/wordpress/wp-config/) file The extra lines go just after where you added the line in [Step 1: Prepare Your WordPress](https://developer.wordpress.org/advanced-administration/multisite/create-network/#step-1-prepare-your-wordpress).
2. Add the specified lines to your `.htaccess` file If you do not have a `.htaccess` file, then create it in the same directory as your `wp-config.php` file. If you _ALREADY_ have a `.htaccess` file, replace any existing WP lines with these new ones. In some cases you might also have to add Options FollowSymlinks at the start of the file.

After completing these steps, log in again using the link provided. You might have to clear your browser's cache and cookies in order to log in.

## Step 5: Network Admin Settings {#step-5-network-admin-settings}

[![](https://i0.wp.com/wordpress.org/support/files/2018/11/network-admin-link.png?fit=383%2C184&ssl=1)](https://i0.wp.com/wordpress.org/support/files/2018/11/network-admin-link.png?fit=383%2C184&ssl=1)

At the left of your WordPress toolbar, **My Sites** is now the second item. There, all your sites are listed, with handy fly-out menus, as well as a **Network Admin** menu item. Under **Network Admin** you can use the **Dashboard** item to go to the Network Dashboard screen.

Go to the [Settings Screen](https://developer.wordpress.org/advanced-administration/multisite/admin/) to configure network options, and the [Sites Screen](https://developer.wordpress.org/advanced-administration/multisite/admin/#Sites) to manage your sites.

For more information, see: [Network Admin](https://developer.wordpress.org/advanced-administration/multisite/admin/)

[Upgraded and can't find the Network Admin menu?](https://developer.wordpress.org/advanced-administration/multisite/administration/#network-admin-link-location)

## Step 6: Administration {#step-6-administration}

There are some additional things you might need to know about advanced administration of the network, due to the additional complexity of a Multisite. Even if you're familiar with WordPress, the location and behavior of Multisite Network Administration can be confusing.

Read [Multisite Network Administration](https://developer.wordpress.org/advanced-administration/multisite/administration/) for more information.

For help troubleshooting:

* [Debugging a WordPress Network](https://developer.wordpress.org/advanced-administration/debug/debug-network/)

## Related Articles {#related-articles}

* [Hosting WordPress](https://wordpress.org/documentation/article/hosting-wordpress/)
* [Installing Multiple Blogs](https://developer.wordpress.org/advanced-administration/before-install/multiple-instances/)
* [How to adapt my plugin to Multisite?](http://stackoverflow.com/questions/13960514/how-to-adapt-my-plugin-to-multisite/)

## Changelog

- 2022-10-21: Original content from [Create A Network](https://wordpress.org/documentation/article/create-a-network/).
