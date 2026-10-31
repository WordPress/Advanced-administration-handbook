# WordPress Multisite / Network

WordPress Multisite is a feature of WordPress that enables you to create sites managed within one installation.

Benefits of using Multisite:

- Reduce hosting costs by sharing resources since every site is on the same server in the same WordPress installation.
- Create a multilingual network with a separate site for each language.
- Set up multi-regional sites, with each site providing content for a specific region.
- Utilize a homogeneous set of plugins and themes across all sites.

Each site is served from the same WordPress installation and can use the same plugins and themes. The content for each site in Multisite has its own unique tables in a shared
database. User data is stored in common tables, and access to individual sites is controlled by the role assigned to each user when the user is added to a site. Each site's media
is stored within `/wp-content/uploads/`: the main site uses that directory, while subsites use separate directories under `/wp-content/uploads/sites/`.

You can create a Multisite network that uses subdirectories or subdomains. For how to map custom domains, see
[WordPress Multisite Domain Mapping](https://developer.wordpress.org/advanced-administration/multisite/domain-mapping/)

You need to have rewrites enabled to use Multisite. Check the
[server requirements](https://developer.wordpress.org/advanced-administration/multisite/prepare-network/#server-requirements) for details.
