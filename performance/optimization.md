# Optimization

Whether you run a high traffic WordPress installation or a small blog on a low cost shared host, you should optimize WordPress and your server to run as efficiently as possible. This article provides a broad overview of WordPress optimization, with specific recommended approaches. However, it's not a detailed technical explanation of each aspect.

If you need a quick fix now, go straight to the Caching section, you'll get the biggest benefit for the smallest hassle there. If you want to get started on a more thorough optimization process immediately, go to [How to Improve Performance on WordPress](#how-to-improve-performance-in-wordpress).

A broad overview of the topic of performance is included below in [Performance factors](#performance-factors) and [Performance testing tools](#performance-testing-tools). Many of the techniques discussed here also apply to WordPress Multisite (MU).

## Performance factors

Several factors can affect the performance of your WordPress blog (or website). Those factors include, but are not limited to, the hosting environment, WordPress configuration, software versions, number of images and their file sizes.

Most of these performance degrading factors are addressed here in this article.

### Hosting

The optimization techniques available to you will depend on your hosting setup.

#### Shared Hosting

This is the most common type of hosting. Your site will be hosted on a server along with many others. The hosting company manage the web server for you, so you have very little control over server settings and so on. In most shared hosting, the user can access the file system of the website root via SFTP and many of the common domain/hosting tasks via a [web hosting control panel](https://developer.wordpress.org/advanced-administration/server/control-panel/).

The areas most relevant to this type of hosting are: [Caching](#caching), [WordPress Performance](#optimizing-your-wordpress-website), and [Content Offloading](#content-offloading).

#### Managed Hosting

Managed hosting is similar to shared hosting, but more locked down to a set of software stacks that the users can run for a particular set of usage scenarios. Hence, the hosting provider manages the software stacks for the users, but with the condition of limiting the software selections. The users typically don't have (or the need) to access the file system and manage any tasks via a [web hosting control panel](https://developer.wordpress.org/advanced-administration/server/control-panel/). Some hosting providers will offer more choice in the selection of software or plug-ins in the upper tier of the hosting plans.

Most of the content platforms on the web today (e.g. blogging / social media) are a form of managed hosting, as their purpose is to provide a platform for a particular set of usage scenarios.

#### Virtual Private Servers and Dedicated Servers

In this hosting scenario, you have control over your own server: the entire file system, SSH, and the ability to install/configure any software on an independent operating system dedicated to the server. The server might be a dedicated piece of hardware or one of many virtual servers sharing the same physical hardware.

The key thing is, you have control over the server settings. In addition to the areas above (caching and WordPress performance), the key areas of interest here are: [Optimize Software](#optimize-software) and [Content Offloading](#content-offloading).

#### Number of Servers

When dealing with very high traffic situations, it may be necessary to employ multiple servers. If you're at this level, you should already have employed all the applicable techniques listed above.

The WordPress database can be easily moved to a different server and only requires a small change to the config file. Likewise, images and other static files can be moved to alternative servers (see [Content Offloading](#content-offloading)).

[Load balancers](https://en.wikipedia.org/wiki/Load_balancing_(computing)) can help spread traffic across multiple web servers but requires a higher level of expertise. If you're employing multiple database servers, the [HyperDB](https://codex.wordpress.org/HyperDB) class provides a drop-in replacement for the standard [WPDB](https://developer.wordpress.org/reference/classes/wpdb/) class, and can handle multiple database servers in both replicated and partitioned structures.

#### Hardware Performance

Your hardware capability will have a huge impact on your site performance. The number of processors, the processor speed, the amount of available memory, disk space, and the disk storage medium are important factors. Hosting providers generally offer higher performance for a higher price.

#### Geographical distance

The distance between your server and your website visitors also has an impact on performance. A Content Delivery Network, or CDN, can mirror static files (like images) across various geographic regions so that all your site visitors have optimal performance.

#### Server Load

The amount of traffic on your server and how it's configured to handle the load will have a huge impact as well. For example, if you don't use a caching solution, performance will slow to a halt as additional page requests come in and stack up, often crashing your web or database server.

If configured properly, most hosting solutions can handle very high traffic amounts. Offloading traffic to other servers can also reduce server load.

Abusive traffic such as login [Brute Force attacks](https://developer.wordpress.org/advanced-administration/security/brute-force/), image hotlinking (other sites linking to your image files from high traffic pages) or DoS attacks can also increase server load. Identifying and blocking these attacks is critical.

#### Software version & performance

Making sure you are using the latest software is also important, as software upgrades often fix bugs and enhance performance. Making sure you're running the latest version of Linux (or Windows), Apache, MySQL/MariaDB, and PHP is essential.

### WordPress Configuration

Your theme will have a huge impact on the performance of your site. A fast lightweight theme will perform much more efficiently than a heavy graphic-laden inefficient one.

The number of plugins and their performance will also have a huge impact on your site's performance. Deactivating and deleting unnecessary plugins is a significant way to improve performance.

Keeping up with WordPress upgrades is also important.

#### Size of Graphics

Making sure the images in your posts are optimized for the web can save time, bandwidth, and increase your search engine ranking.

## Performance testing tools

- [Online web page benchmarking tools](https://duckduckgo.com/?q=online+web+page+benchmarking+tools) can test real life website performance from different locations, browsers, and connection speeds.
- The built-in browser developer tools (e.g. Firefox or Chrome) all have performance measurement tools.

## How to improve performance on WordPress

### Optimizing Your WordPress Website

#### Minimizing Plugins

The first and easiest way to improve WordPress performance is by looking at the plugins. Deactivate and delete any unnecessary plugins. Try selectively disabling plugins to measure server performance.

Is one of your plugins significantly affecting your site's performance? Look at the plugin documentation, ask for support in the appropriate plugin support forum or look for alternative plugins with similar featuresets.

#### Optimizing content

*Image Files*

- Are there any unnecessary images? (e.g. Can you replace some of the images with text?)
- Make sure all image files are optimized. Choose the correct format (JPG/PNG/GIF) and compression for each image.
- Consider using a more modern image format like WebP which is smaller in size. 

*Total File Number/Size*

- Can you reduce the number of files needed to display the average page on your site?
- When still using HTTP/1.x, it's recommended to combine multiple files in a single optimized file.
- Minify CSS and JavaScript files.

### Upgrade Hardware

Paying more for higher service levels at your hosting provider can be very effective. Increasing CPU and memory (RAM) or switching to a host with Solid-State Drives (SSD) or NVMe can make a big difference. Increased number of processors and processor speed will also help. Where possible, try to separate services with different functions – like HTTP and MySQL – on multiple servers or VPS (the servers should ideally be in the same location to reduce latency). If you are on shared hosting, upgrading to a plan with higher resource limits like Disk I/O, IOPS, NPROC and total processes of your hosting plan may help if you are maxing out your limits.

### Optimize Software

Make sure you are running the latest operating system version (e.g. Linux or Windows Server), the latest web server version (e.g. Apache or IIS), database (e.g. MySQL server), and PHP.

If you are unable to perform the following tasks below, your hosting provider may be able to perform some of them for you, or you may seek external help such as freelancers to help you. A good hosting provider will upgrade or move your account to an upgraded server to match the recommended spec, but not all of them will help you manage/optimize your servers, since it's typically outside the scope of hosting plan offerings. If needed, a managed WordPress hosting solution, with a pre-chosen set of software stacks and fixed versions, could be a potential fit for your needs.

**DNS**: Don't run a DNS on your WordPress server. Use a commercial DNS service or your domain registrar's free offering. Using an external service can also make switching between backup servers during maintenance or emergencies much easier. It also provides a degree of fault tolerance. If you host your DNS on external servers, it will reduce the load on your primary web server. It's a simple change, but it will offload some traffic and CPU load.

**Web Server**: Your web server can be configured to increase performance. There is a range of techniques, from web server caching to setting cache headers to reduce load per visitor. Search for your specific web server optimizations (for example, search for "Apache optimization" for more info). Some web servers have higher speed versions you can pay for. There are also a number of ways to tune Apache for higher performance based on your particular hosting and site configuration (e.g. Memcached).

**PHP**: There are various PHP accelerators available which can dramatically improve the performance of your PHP files. This will apply to all PHP files, not just your WordPress installation. Search for PHP optimization for more information (e.g. [APC](https://www.php.net/manual/book.apcu.php) or [OPcache](https://www.php.net/manual/book.opcache.php)). Some WordPress caching plugins offer integrated support for Memcached, APC and other Opcode caching. Newer PHP versions will usually include better performance optimization as well.

**MySQL/MariaDB**: MySQL or MariaDB optimization is a black art in itself. A few simple changes to the query cache settings can have a dramatic effect on WordPress performance because WordPress repeats many queries on every request. Nowadays, with InnoDB being the default storage engine for MySQL, you have to make sure to use that. InnoDB can be optimized and fine-tuned, search the web for [mysql optimization](https://duckduckgo.com/?q=mysql+optimization), [mysql innodb performance](https://duckduckgo.com/?q=mysql+innodb+performance) or [innodb optimization](https://duckduckgo.com/?q=innodb+optimization) for more information and examples. Search the web for [mysql convert myisam to innodb](https://duckduckgo.com/?q=mysql+convert+myisam+to+innodb) for information on how to convert older MyISAM tables to InnoDB.

**Other services**: Don't run a mail server on your WordPress server. For your contact form, use a contact form plug-in along with an external mailing service.

### Caching

#### Caching Plugins

Caching plugins can be easily installed and will cache your WordPress posts and pages as static files. These static files are then served to users, reducing the processing load on the server. This can improve performance several hundred times over for fairly static pages. You can get a list of relevant plugins by searching for [cache](https://wordpress.org/plugins/search/cache/) in the plugins directory.

When combined with a system-level page cache such as Varnish, this can be powerful. If your posts/pages have a lot of dynamic content, configuring caching can be more complex.

#### Server-side Caching

Web server caching is more complex, but is used on very high traffic sites. A wide range of options are available, beyond the scope of this article. The simplest solutions start with the server caching locally, while more complex and involved systems may use multiple caching servers (also known as reverse proxy servers) "in front" of web servers where the WordPress application is currently running. Some web servers can also act as a reverse proxy at the same time. Adding an opcode cache like [Alternative PHP Cache](https://www.php.net/manual/book.apcu.php) (APC) to your server will improve the performance of PHP by many times.

[Varnish Cache](https://varnish-cache.org/) works in concert with some cache plug-ins to store pre-built pages in memory and serve them quickly without requiring execution of the Apache, PHP, and WordPress stack.

As described within, using an external comments plugin for comments instead of native WordPress comments can assist Varnish by not requiring your readers to log in to WordPress and increasing the number of page views that Varnish Cache can serve out of the cache.

#### Browser Caching

Browser caching can help to reduce server load by reducing the number of requests per page. For example, by setting the correct file headers on files that don't change (static files like images, CSS, JavaScript etc.), browsers will then cache these files on the user's computer. This technique allows the browser to check to see if files have changed, instead of simply requesting them. The result is your web server can answer many more 304 responses, confirming that a file is unchanged, instead of 200 responses, which require the file to be sent.

Look into HTTP Cache-Control (specifically `max-age`) and Expires headers, as well as [Entity Tags](https://developer.mozilla.org/docs/Web/HTTP/Headers/ETag) for more information.

Some WordPress cache plug-ins integrate support for browser caching and ETag.

#### Object Caching

Using a __persistent__ Object Cache helps speed up page load times by saving on trips to the database from your web server. For example, your site's options data needs to be available for each page view. Without a persistent object cache, your web server must read those options from the database to handle every page view. Those extra trips to the database slow down your web server's response times such as "Time to first byte" (TTFB) and can quickly overwhelm your database server during traffic spikes.

For your site to use persistent object caching, your hosting provider must offer you a particular type of server, a [cache server](https://en.wikipedia.org/wiki/Category:Database_caching). Ask your hosting provider or external help such as freelancers to help you install and configure a persistent object cache server, then, install an [object cache WP plug-in](https://wordpress.org/plugins/search/object+cache/) that supports the cache server installed:

### Further Reading

- [WP Object Cache](https://developer.wordpress.org/reference/classes/wp_object_cache/)

### Content Offloading

#### Use a Content Delivery Network (CDN)

Using a CDN can greatly reduce the load on your website. Offloading the searching and delivery of images, JavaScript, CSS and theme files to a CDN are not only faster but takes a great load off your WordPress server's own app stack. A CDN is most effective if used with a WordPress caching plugin, described above. Some newer CDN will also include Full Page Caching (FPC) or Edge Caching which will cache the entire HTML content of the website. 

For details, see [list of notable content delivery service providers](https://en.wikipedia.org/wiki/Content_delivery_network#Notable_content_delivery_service_providers).

#### Static Content

Any static files can be offloaded to another server. For example, any static images, JavaScript, or CSS files can be moved to a different server. This is a common technique in very high-performance systems (Google, Flickr, YouTube, etc.) but can also be helpful for smaller sites where a single server is struggling. Also, moving this content onto different hostnames can lay the groundwork for multiple servers in the future.

Some web servers are optimized to serve static files and can do so far more efficiently than more complex web servers like Apache, for example [lighttpd](https://www.lighttpd.net/).

[Cloud storage](https://en.wikipedia.org/wiki/Object_storage#Cloud_storage) is a dedicated static file hosting service on a pay-per-usage basis. With no minimum costs, it might be practical for lower traffic sites which are reaching the peak that a shared or single server can handle.

#### Multiple Hostnames

There can also be user improvements by splitting static files between multiple hostnames. Most browsers will only make 2 simultaneous requests to a host, so if your page requires 16 files, they will be requested 2 at a time. If you spread the 16 files between 4 host names, they will be requested 8 at a time. This can reduce page loading times for the user, but it can increase server load (if the different hosts names are served by the same server) by creating more simultaneous requests. Also, known is "pipelining" can often saturate the visitor's internet connection if overused.

Offloading images is the easiest and simplest place to start. All images files could be evenly split between three hostnames (`assets1.example.com`, `assets2.example.com`, `assets3.example.com` for example). As traffic grows, these hostnames could be moved to your own dedicated servers. Note: Avoid picking a hostname at random as this will affect browser caching and result in more traffic and may also create excessive DNS lookups which do carry a performance penalty.

Likewise, any static JavaScript and CSS files can be offloaded to separate hostnames or servers.

Under HTTP/2 and HTTP/3, "HTTP pipelining" is superseded by multiplexing, so the use of the above techniques may no longer be necessary.

#### Feeds

Your feeds can easily be offloaded to an external feed service that can handle all the feed traffic and only update the feed from your site every few minutes. This can be a big traffic saver.

Likewise, you could offload your own feeds to a separate server (feeds.example.com for example) and then handle your own feed stats / advertising.

### Compression

There are a number of ways to compress files and data on your server so that your pages are delivered more quickly to readers' browsers. Some [cache plug-ins](https://wordpress.org/plugins/search/cache/) described above integrate support for most of the common approaches to compression.

Some WP cache plug-ins support Minify and Tidy to compress and combine your style sheets and JavaScript files. It also supports output compression such as [zlib](https://zlib.net/), see also [Output Compression](https://codex.wordpress.org/Output_Compression).

It's also important to compress your media files – namely images.

### Database Tuning

#### Cleaning Your Database

Some [optimization plug-ins](https://wordpress.org/plugins/search/optimization/) and [database plug-ins](https://wordpress.org/plugins/search/database/) can help you reduce extra clutter in your database.

You can also instruct WordPress to [minimize the number of revisions](https://wordpress.org/documentation/article/revisions/) that it saves of your posts and pages.

### Adding Servers

While it requires additional expertise, adding servers can be a powerful way to increase performance.

You can use [load balancers](https://en.wikipedia.org/wiki/Load_balancing_(computing)) to spread traffic across multiple web servers, and you can use [HyperDB](https://codex.wordpress.org/HyperDB) or database service in the cloud to run more scalable or multiple database servers.

There are various guides on the web, as well as WordCamp presentations, about scaling WordPress sites on cloud services.

### Autoloaded Options

Autoloaded options are configuration settings for plugins and themes that are automatically loaded with every page load on WordPress. Each plugin and theme defines their own options and which options are autoloaded. Having too many autoloaded options can slow down your site. Generally, you should try to keep your site's autoloaded options under 800kb.

By default, autoloaded options are saved in the wp_options table. Autoload can be turned off on an option-by-option basis within this table. For step-by-step instructions on viewing and changing autoloaded options, check with your hosting provider.

If you use a Persistent Object Cache, options (whether autoloaded or not) load faster and more efficiently.

## Additional Resources

### Further Reading

- [High Traffic Tips for WordPress](https://codex.wordpress.org/High_Traffic_Tips_For_WordPress)

### WordCamp Performance Presentations

- [High-Performance WordPress by Iliya Polihronov from WordCamp 2012 (San Francisco)](https://wordpress.tv/2012/09/01/iliya-polihronov-high-performance-wordpress/)
- [WordPress Optimization from WordCamp Israel 2013](https://www.slideshare.net/AlmogBaku/wordpress-optimization-16678718)
- [Copy of the Slides on HyperDB and High Performance from WordCamp 2007 (San Francisco)](https://barry.blog/2007/07/22/high-performance-wordpress/)
   - [Presentation on HyperDB and High Performance from WordCamp 2007 (San Francisco)](https://onemansblog.com/2007/08/16/wordcamp-2007-hyperdb-and-high-performance-wordpress/)
- [50 tips su Web Performance Optimization per siti ad alto traffico WordCamp Bologna (Italy) 2013](https://www.slideshare.net/AndreaCardinali/50-tips-su-web-performance-optimization-per-siti-ad-alto-traffico-wpcamp-bologna-2013)

## Changelog

- 2023-05-03: Revised content to comply with [External Linking Policy](https://make.wordpress.org/docs/handbook/documentation-team-handbook/external-linking-policy/).
- 2022-09-11: Original content from [Optimization](https://wordpress.org/documentation/article/optimization/).
