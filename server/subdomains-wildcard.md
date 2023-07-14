# Configuring Wildcard Subdomains

Wildcard subdomains are useful to allow end users of a domain-based WordPress [multisite](https://wordpress.org/documentation/article/wordpress-glossary/#multisite) network to create new sites on demand. In this type of network each new site has its own subdomain, and the wildcard configuration means that those subdomains do not have to be configured individually. For information on how to create a multisite network, see: [Create A Network](https://developer.wordpress.org/advanced-administration/multisite/create-network/).

This page contains some examples of how to configure wildcard subdomains in different circumstances. If you cannot determine how to set up wildcard subdomains on your particular web server, *contact your webhost* for directions.

## Apache {#apache}

In the `httpd.conf` file, or in the include file containing the `VirtualHost` section for your web account, add a line like this (if it is not already present):

```
ServerAlias *.example.com
```

Also create a wildcard DNS record like:

```
*.example.com A 192.0.2.1
```

## CPanel {#cpanel}

Make a sub-domain named "*" (wildcard) at your CPanel (`*.example.com`). Make sure to point this at the same folder location where your `wp-config.php` file is located.

## Plesk {#plesk}

There are several steps that differ when setting up the server for wildcard subdomains on a server using Plesk Panel compared to a server using cPanel (or no control panel). This article [Configuring Wildcard Subdomains for multi site under Plesk Control Panel](https://codex.wordpress.org/Configuring_Wildcard_Subdomains_for_multi_site_under_Plesk_Control_Panel) details all the steps involved.

## DirectAdmin {#directadmin-panel}

Click "User Panel" -> DNS Management -> add the following three entries using the three columns:

```
* A 192.0.2.1
```

Click "Admin Panel" (If you have no "admin panel" ask your host to do this) -> Custom Httpd -> yourdomain.com -> In the text input area, just paste and "save" precisely the following:

```
ServerAlias *.|DOMAIN|
```

_If you ever need to un-do a custom Httpd: return here, delete text from input area, save._

- DirectAdmin.com: [Apache Wildcard Documentation](https://help.directadmin.com/item.php?id=127). DirectAdmin.com forum: [WordPress wildcard subdomains](http://www.directadmin.com/forum/showthread.php?p=195033).

## Amazon Web Services {#amazon-web-services}

AWS instances are not assigned a permanent IP address by default. This means that a "server's" IP address may change when it is rebooted. To resolve this issue, assign an Elastic IP Address to your server instance and use that IP address when configuring the A record with your registrar.

AWS Elastic Load Balancers cannot be assigned an elastic IP, therefore you must use a CName to give them a friendly URL. You cannot have a CName to a root URL. Therefore you must point the domain root (example.com) at a specific server instance with an Elastic IP address and create a wildcard CName (*.example.com) and point that at your Elastic Load Balancer. In your .htaccess, then just redirect all domain root traffic (example.com) to a specific sub-domain (www.example.com).

**Notes:**

- Some registrars do not currently support wildcard CNames.
- Amazon's Route53 Domain Name Service eliminates the CName issue, but at an additional cost.

## Changelog

- 2023-01-20: Original copied from [Configuring Wildcard Subdomains](https://wordpress.org/documentation/article/configuring-wildcard-subdomains/) and links checked.
