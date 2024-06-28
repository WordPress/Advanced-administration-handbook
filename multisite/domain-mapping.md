# WordPress Multisite Domain Mapping

WordPress multisite subsites may be mapped to a non-network top-level domain. This means a site created as subsite1.networkdomain.com, can be mapped to show as domain.com. This also works for subdirectory sites, so networkdomain.com/subsite1 can also appear at domain.com. Before setting up domain mapping, make sure your network has been correctly set up, and subsites can be created without issues.

Before WordPress 4.5, domain mapping requires a domain mapping plugin like [WordPress MU Domain Mapping](https://wordpress.org/plugins/wordpress-mu-domain-mapping).

In WordPress 4.5+, domain mapping is a native feature.

## Map Domains in DNS {#map-domains-in-dns}

Make sure all the domains you want to use are already mapped to your **DNS** server. The additional domains should be parked upon the master domain.

## Install SSL Certificates {#install-ssl-certificates}

Install SSL for the primary domain and use **SERVER NAME INDICATION** (SNI) for all other domains. Every domain should have SSL installed to ensure encrypted admin login.

## Update WordPress {#update-wordpress}

In the network admin dashboard, click on Sites to show the listing of all the subsites, and then click on edit for the subsite you want to map to. In our example, this is subsite1.mynetwork.com.

In the Site Address (URL) field, enter the full URL to the domain name you're mapping – https://example.com – and click save.

## Edit wp-config.php {#edit-wp-config-php}

If you get an error about cookies being blocked when you try to log in to your network subsite (or log in fails with no error message), open your wp-config.php file and add this line after the other code you added to create the network:

```
define( 'COOKIE_DOMAIN', $_SERVER['HTTP_HOST'] );
```

## Related Articles {#related-articles}

1. [Create A Network](https://developer.wordpress.org/advanced-administration/multisite/create-network/)
2. [MultiSite Network Administration](https://developer.wordpress.org/advanced-administration/multisite/administration/)
3. [Installing Multiple Blogs](https://developer.wordpress.org/advanced-administration/before-install/multiple-instances/)

## Changelog

- 2022-10-25: Original content from [WordPress Multisite Domain Mapping](https://wordpress.org/documentation/article/wordpress-multisite-domain-mapping/).
