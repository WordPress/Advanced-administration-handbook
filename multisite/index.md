# WordPress Multisite / Network

WordPress Multisite is a feature of WordPress that enables you to create sites managed within one installation.

Benefits of using Multisite:
* Reduce hosting costs by sharing resources since every site is on the same server in the same WordPress installation.
* Multilingual can easily be created with each site having its own language and synchronous from the main site.
* Multi-regional sites can be setup with each site being a region with region-specific content.
* Having a homogeneous set of plugins and themes used across all sites.

Each site is served from the same WordPress installation and can use the same plugins and themes.
The content for each site in Multisite has its own unique tables in a shared database. User data is stored in common
tables, and access to individual sites is controlled by the role assigned to each user when the user is added to a site.

You can create a Multisite network that uses subdirectories or subdomains. For how to map custom domains, see
[WordPress Multisite Domain Mapping](https://developer.wordpress.org/advanced-administration/multisite/domain-mapping/)

You need to have rewrites enabled to use Multisite. Check the [server requirements](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/#server-requirements) for details.
