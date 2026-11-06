# WordPress Multisite Domain Mapping

WordPress multisite subsites may be mapped to a domain outside the network's domain. This means a site created as subsite1.example.net can be mapped to show as example.com. This also works for subdirectory sites, so example.net/subsite1 can also appear at example.com. Before setting up domain mapping, make sure your network has been correctly set up, and subsites can be created without issues.

Before WordPress 4.5, domain mapping required a domain mapping plugin.

In WordPress 4.5+, domain mapping is a native feature.

The main site of the network cannot be mapped this way. On the Edit Site screen, WordPress does not let you change the Site Address (URL) of the main site.

## Map Domains in DNS {#map-domains-in-dns}

Make sure all the domains you want to use point to your server in **DNS**. Your web server must also answer requests for these domains from the same WordPress installation. For example, add them with `ServerAlias` in Apache, or with `server_name` in Nginx. In cPanel, such a domain is called an alias.

## Install SSL Certificates {#install-ssl-certificates}

Every domain should have an SSL certificate, so that logins to the admin area are encrypted. You can use one certificate for each domain, or one certificate that covers several domains. With **Server Name Indication** (SNI), the web server can use the right certificate for each domain on the same IP address.

## Update WordPress {#update-wordpress}

In the Network Admin dashboard, go to **Sites** to see the list of all the subsites, and click **Edit** under the subsite you want to map. In our example, this is subsite1.example.net.

On the **Info** tab, in the **Site Address (URL)** field, enter the full URL of the domain you are mapping, for example `https://example.com`, and click **Save Changes**.

## Edit wp-config.php {#edit-wp-config-php}

If you get an error about cookies being blocked when you try to log in to your network subsite (or log in fails with no error message), open your wp-config.php file and add this line after the other code you added to create the network:

```php
define( 'COOKIE_DOMAIN', $_SERVER['HTTP_HOST'] ?? '' );
```

This is mostly needed on subdomain networks. There, WordPress sets the cookie domain to the network's domain by default, so the login cookie does not work on the mapped domain.

With this line, logging in on a subdomain site does not sign you in on the main domain or on other subdomain sites. Logging in on the main domain still signs you in on all subdomain sites.

## Related Articles {#related-articles}

1. [Create A Network](https://developer.wordpress.org/advanced-administration/multisite/create-network/)
2. [Multisite Network Administration](https://developer.wordpress.org/advanced-administration/multisite/administration/)
3. [Installing Multiple WordPress Instances](https://developer.wordpress.org/advanced-administration/before-install/multiple-instances/)

