# Caching Security

While caching can significantly improve the performance of WordPress websites, caching can expose WordPress websites to new vulnerabilities if the caching providers are not configured correctly. Some common vulnerabilities include but are not limited to websites accessing the cached data for other websites or caching applications serving the wrong cached data or files. Each kind of caching application usually has security settings and configuration to provide a safe environment for enjoying the performance benefits of caching.

## OpCache Security

PHP opcode caching can significantly improve the performance of PHP processing for WordPress websites, as outlined in the Performance section of the WordPress Hosting Handbook. However, when improperly configured PHP opcode caching can enable users to access other users' PHP files without authorization. There are important PHP configuration options for opcode caching that mitigate vulnerabilities such as accessing files without authorization.

### Validate permission

The following setting makes PHP check that the current user has the necessary permissions to access the cached file. It should be enabled at the root php.ini configuration level to prevent users from accessing other users cached files.  
`opcache.validate_permission = On`

This setting is not enabled by default. It is also only available as of PHP 7.0.14.

### Validate root

The following setting prevents PHP users from accessing files outside of the chroot'd directory to which they normally would not have access. It should also be added to the root php.ini configuration level to prevent unauthorized access to files.  
`opcache.validate_root = On`

This setting is not enabled by default. It is also only available as of PHP 7.0.14.

### Restrict API

Normally, any PHP user can access the opcache API for viewing the currently cached files and for managing the PHP opcode cache. With some PHP configurations, however, the PHP opcode cache shares the same memory between all users on the server. Sharing the PHP opcode cache between all users means all users can view and access the PHP opcode cache and can access other users' cached PHP files. Restricting the Opcache API prevents PHP scripts run in unauthorized directories from viewing cached files and interacting with the PHP opcode cache manually from within PHP scripts. The following setting defines the directory path PHP scripts must start with to be able to access the Opcache API.  
`opcache.restrict_api = '/some/folder/path'`

The default value for the setting is `''`, which means there are no restrictions on which PHP scripts can access the Opcache API. This setting should be defined in the root php.ini for your PHP configuration in order to prevent users from overriding it.

## Object Caching Security

There are several solutions for providing database object caching for WordPress. Each comes with its own configuration requirements for providing a secure environment while using database object caching.

### Redis

Redis is a lightweight, high-performance key-value database server commonly used to cache the results from WordPress database queries. In its default configuration, Redis uses a single database and does not require a username and password to access the database. Redis should also only be accessible from authorized network hosts.

#### Redis databases

Redis provides 16 databases, number 0 to 15 by default. Redis clients should be configured to use different databases instead of the default database (number 0). Redis can be configured to have additional databases, but that is outside the scope of this document.

#### Redis user credentials

If Redis is going to be used for database object caching, the Redis server should be configured to require access credentials.

#### Redis network hosts

The Redis server in its default configuration listens on port 6379. The port can be changed in Redis's configuration, but whatever port is used should be protected by a firewall to prevent unauthorized access.

#### Redis cache key salt

If using Redis for database object caching, using a unique Redis cache key salt will help prevent cache collisions -- when two websites try to cache content using the same key. Cache collisions can result in websites accessing the cached data for other websites and can cause other undesirable and unexpected behaviors. The Redis cache key salt is usually configured through the Redis caching plugin or Redis client used to enable Redis database object caching in WordPress websites.

### Memcached

Memcached is a memory object caching solution commonly used to provide database object caching for WordPress. One of the most important configuration concerns for memcached is preventing memcached from being accessed by the public internet. Putting memcached servers behind a firewall is one of the most important parts of using memcached securely for WordPress database object caching.
