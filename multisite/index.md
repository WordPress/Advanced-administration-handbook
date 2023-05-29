# WordPress Multisite / Network

WordPress Multisite is a feature of WordPress that enables you to create several instances of WordPress managed within one installation. You need to have rewrites enabled to use multisite. Check the [server requirements](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/#server-requirements) for details. 

One can use a multisite for a variety of purposes. Multisite is, for example, used by business sites that share some resources, such as the theme or plugins, and have different content for their regions. 

The content in a Multisite has its own unique tables in the database, only the user table is shared between the instances.

You can create a multisite that works with subdirectories ("path-based") or use domains or subdomains ("domain-based"). For how to map the domains, see [WordPress Multisite Domain Mapping](https://developer.wordpress.org/advanced-administration/multisite/domain-mapping/)

## Changelog

- 2023-05-19: First content.