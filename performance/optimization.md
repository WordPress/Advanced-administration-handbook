# Optimization

Whether you run a high traffic WordPress installation or a small blog on a low cost shared host, you should optimize WordPress and your server to run as efficiently as possible. This article provides a broad overview of WordPress optimization with specific recommended approaches. However, it's not a detailed technical explanation of each aspect.

If you need a quick fix now, go straight to the Caching section, you'll get the biggest benefit for the smallest hassle there. If you want to get started on a more thorough optimization process immediately, go to How Do You Improve Performance in WordPress.

A broad overview of the topic of performance is included below in What Affects Performance and How Do You Measure Performance. Many of the techniques discussed here also apply to WordPress Multisite (MU).

## Performance factors

Several factors can affect the performance of your WordPress blog (or website). Those factors include, but are not limited to, the hosting environment, WordPress configuration, software versions, number of graphics and their sizes.

Most of these performance degrading factors are addressed here in this article.

### Hosting

The optimization techniques available to you will depend on your hosting setup.

#### Shared Hosting

This is the most common type of hosting. Your site will be hosted on a server along with many others. The hosting company manage the web server for you, so you have very little control over server settings and so on.

The areas most relevant to this type of hosting are: Caching, WordPress Performance and Content Offloading

#### Virtual Hosting and Dedicated Servers

In this hosting scenario you have control over your own server. The server might be a dedicated piece of hardware or one of many virtual servers sharing the same physical hardware.

The key thing is, you have control over the server settings. In addition to the areas above (caching and WordPress performance, the key areas of interest here are: Server Optimization and Content Offloading

#### Number of Servers

When dealing with very high traffic situations it may be necessary to employ multiple servers. If you're at this level, you should already have employed all of the applicable techniques listed above.

The WordPress database can be easily moved to a different server and only requires a small change to the config file. Likewise images and other static files can be moved to alternative servers (see content offloading).

[Amazon's Elastic Load Balancer](https://aws.amazon.com/elasticloadbalancing/) can help spread traffic across multiple web servers but requires a higher level of expertise. If you're employing multiple database servers, the [HyperDB]([https://codex.wordpress.org/HyperDB) class provides a drop-in replacement for the standard [WPDB](https://codex.wordpress.org/Class_Reference/wpdb) class, and can handle multiple database servers in both replicated and partitioned structures.

#### Hardware Performance

Your hardware capability will have a huge impact on your site performance. The number of processors, the processor speed, the amount of available memory and disk space as well as the disk storage medium are important factors. Hosting providers generally offer higher performance for a higher price.

#### Geographical distance

The distance between your server and your website visitors also has an impact on performance. A Content Delivery Network, or CDN, can mirror static files (like images) across various geographic regions so that all your site visitors have optimal performance.

#### Server Load

The amount of traffic on your server and how it's configured to handle the load will have a huge impact as well. For example, if you don't use a caching solution, performance will slow to a halt as additional page requests come in and stack up, often crashing your web or database server.

If configured properly, most hosting solutions can handle very high traffic amounts. Offloading traffic to other servers can also reduce server load.

Abusive traffic such as login [Brute Force attacks](https://developer.wordpress.org/advanced-administration/security/brute-force/), image hotlinking (other sites linking to your image files from high traffic pages) or DoS attacks can also increase server load. Identifying and blocking these attacks is very important.

#### Software version & performance

Making sure you are using the latest software is also important, as software upgrades often fix bugs and enhance performance. Making sure you're running the latest version of Linux (or Windows), Apache, MySQL/MariaDB and PHP is very important.


### WordPress Configuration

Your theme will have a huge impact on the performance of your site. A fast, lightweight theme will perform much more efficiently than a heavy graphic-laden inefficient one.

The number of plugins and their performance will also have a huge impact on your site's performance. Deactivating and deleting unnecessary plugins is a very important way to improve performance.

Keeping up with WordPress upgrades is also important.

#### Size of Graphics

Making sure the images in your posts are optimized for the web can save time, bandwidth and increase your search engine ranking.

## Performance testing tools

- [WebPageTest](https://www.webpagetest.org/) is a tool for testing real life website performance from different locations, browsers and connection speeds.
- [Google PageSpeed Insights](https://developers.google.com/speed/pagespeed/insights/) is way to measure your WordPress site's performance and receive clear, specific feedback on how to make improvements.
- The built-in browser developer tools (ie. Firefox or Chrome) all have performance measurement tools.

## How to improve performance in WordPress

### Optimizing Your WordPress Website

#### Minimizing Plugins

The first and easiest way to improve WordPress performance is by looking at the plugins. Deactivate and delete any unnecessary plugins. Try selectively disabling plugins to measure server performance.

Is one of your plugins significantly affecting your site's performance? Look at the plugin documentation or ask for support in the appropriate plugin support forum.

#### Optimizing content

Image Files
- Are there any unnecessary images? (e.g. Can you replace some of the images with text?)
- Make sure all image files are optimized. Choose the correct format (JPG/PNG/GIF) and compression for each image.

Total File Number/Size
- Can you reduce the number of files needed to display the average page on your site?
- When still using HTTP1, it's recommended to combine multiple files in a single optimized file.
- Minify CSS and JavaScript files. 

### Upgrade Hardware

Paying more for higher service levels at your hosting provider can be very effective. Increasing memory (RAM) or switching to a host with Solid State Drives (SSD) e.g. Digital Ocean can make a big difference. Increased number of processors and processor speed will also help. Where possible, try to separate services with different functions – like HTTP and MySQL – on multiple servers or VPS-es.

### Optimize Software

Make sure you are running the latest operating system version e.g. Linux, Windows Server and the latest web server e.g. Apache or IIS, database e.g. MySQL server and PHP.

Perhaps you are unable to perform the tasks, and follow up on the tips, below. Just ask your hosting provider to do them for you. A good hosting provider will upgrade or move your account to an upgraded server, to match the recommendations. If needed you can switch to a managed WordPress hosting solution.

**DNS:** Don't run a DNS on your WordPress server. Use a commercial service for DNS such as Amazon's Route 53 or your domain registrar's free offering. Using a service such as Amazon can also make switching between backup servers during maintenance or emergencies much easier. It also provides a degree of fault tolerance. If you host your DNS on external servers this will reduce the load on your primary web server. It's a simple change, but it will offload some traffic and CPU load.

**Web Server:** Your web server can be configured to increase performance. There are a range of techniques from web server caching to setting cache headers to reduce load per visitor. Search for your specific web server optimizations (for example, search for "apache optimization" for more info). Some web servers have higher speed versions you can pay for such as [Litespeed](http://www.litespeedtech.com/products/litespeed-web-server/features/apache-drop-in-replacement). There are also a number of ways to tune Apache for higher performance based on your particular hosting and site configuration, e.g. Memcache.

**PHP:** There are various PHP accelerators available which can dramatically improve performance of your PHP files. This will apply to all PHP files, not just your WordPress installation. Search for PHP optimization for more information, f.e. [APC](http://php.net/apc) or [OPcache](https://secure.php.net/manual/en/book.opcache.php). The W3 Total Cache plugin, described below, offers integrated support for Memcache, APC and other Opcode caching.

**MySQL/MariaDB:** MySQL or MariaDB optimization is a black art in itself. A few simple changes to the query cache settings can have a dramatic effect on WordPress performance because WordPress repeats a lot of queries on every request. Nowadays, with InnoDB being the default storage engine for MySQL, you have to make sure to use that. InnoDB can be optimized and fine-tuned, search for "mysql optimization", "mysql innodb performance" or "innodb optimization" or for more information and examples. Search for "mysql convert myisam to innodb" for information on how to convert older MyISAM tables to InnoDB.

A great example of how WordPress has been optimized was presented by [Iliya Polihronov at WordCamp San Francisco 2012](http://wordpress.tv/2012/09/01/iliya-polihronov-high-performance-wordpress/). Iliya does, among other things, server optimization for WordPress.com.

Don't run a mail server on your WordPress server. For your contact form, use something like Contact Form 7 with free Mailgun.

### Caching

#### Caching Plugins

Plugins like [W3 Total Cache](https://wordpress.org/plugins/w3-total-cache/) or [WP Super Cache](https://wordpress.org/plugins/wp-super-cache/) can be easily installed and will cache your WordPress posts and pages as static files. These static files are then served to users, reducing the processing load on the server. This can improve performance several hundred times over for fairly static pages.

When combined with a system level page cache such as Varnish, this can be quite powerful. If your posts/pages have a lot of dynamic content configuring caching can be more complex.

**W3 TOTAL CACHE**

[W3 Total Cache](https://wordpress.org/extend/w3-total-cache/) (W3TC) is the latest generation in WordPress performance plugins, combining the research of [web development authorities](http://developer.yahoo.com/performance/rules.html) to provide an optimal user experience for WordPress sites. These detailed guides walk you through.

W3TC is unique in its ability to optimize server side and client side performance, adding functionality otherwise unavailable natively:

- Page Caching: W3TC helps to decrease response time by creating static HTML versions of pages, allowing web servers to serve them without invoking PHP. It automatically updates the cache when comments are made or pages are edited.
- Minification: Removes unnecessary characters from HTML, CSS and JavaScript files, then respectively combines them before applying HTTP compression on the cached files.
- Database Caching: Database queries (objects) are also cached, allowing many sites to reduce the time needed to generate new pages. This is especially useful for sites that receive a lot of comments.
- Headers: W3TC manages the headers (entity tag, cache-control, expires) which control the caching of files in web browsers, reducing server load and improving the user's perceived performance.
- Content Delivery Network (CDN): Using a CDN allows you to Offloading resources from your hosting account. W3TC moves the requests for images, CSS, JavaScript and other static files to a network of high performance servers. The server closest to the visitor is automatically used to download the files, providing the fastest downloads possible.

W3TC can be used to optimize WordPress in both single and multi-server environments through either shared or dedicated hosting.

**WP SUPER CACHE**

[WP Super Cache](https://wordpress.org/plugins/wp-super-cache/) is a static page caching plugin for WordPress. It generates HTML files that are served directly by Apache without processing comparatively heavy PHP scripts, helping you to make significant speed gains on your WordPress blog.

Using WP Super Cache allows your server to serve cached HTML pages at the same speed it serves regular graphic files. Consider WP Super Cache if your site is struggling to cope with its daily number of visitors, or if it appears on [Digg.com](http://digg.com/), [Slashdot.org](http://slashdot.org/) or any other popular site.

#### Server-side Caching

Web server caching‘ is more complex but is used in very high traffic sites. A wide range of options are available, beyond the scope of this article. The simplest solutions start with the server caching locally while more complex and involved systems may use multiple caching servers (also known as reverse proxy servers) "in front" of web servers where the WordPress application is actually running. Adding an opcode cache like [Alternative PHP Cache](http://pecl.php.net/package/APC) (APC) to your server will improve PHP's performance by many times.

[Varnish Cache](https://www.varnish-cache.org/) works in concert with W3 Total Cache to store pre-built pages in memory and serve them quickly without requiring execution of the Apache, PHP, WordPress stack.

As described within, using a plugin for comments such as Disqus instead of native WordPress comments can assist Varnish by not requiring your readers to login to WordPress and increasing the number of page views that Varnish can serve out of the cache.

#### Browser Caching

Browser caching can help to reduce server load by reducing the number of requests per page. For example, by setting the correct file headers on files that don't change (static files like images, CSS, JavaScript etc) browsers will then cache these files on the user's computer. This technique allows the browser to check to see if files have changed, instead of simply requesting them. The result is your web server can answer many more 304 responses, confirming that a file is unchanged, instead of 200 responses, which require the file to be sent.

Look into HTTP Cache-Control (specifically `max-age`) and Expires headers, as well as [Entity Tags](http://en.wikipedia.org/wiki/HTTP_ETag) for more information.

W3 Total Cache integrates support for browser caching and ETags.

#### Persistent Object Cache

A Persistent Object Cache helps speed up page load times by saving on trips to the database from your web server. For example, your site's options data needs to be available for each page view. Without a persistent object cache, your web server must read those options from the database to handle every page view. Those extra trips to the database slow down your web server's response times (TTFB) and can quickly overwhelm your database server during traffic spikes.

For your site to use persistent object caching, your hosting provider must offer you a particular type of server, a cache server. Popular cache servers are [Redis](https://redis.io/) and [Memcached](https://memcached.org/). Ask your hosting provider to help you install and configure a persistent object cache, and they will recommend the right plugin, such as:

- [Memcached Object Cache](https://wordpress.org/extend/plugins/memcached/) – Provides a persistent backend for the WordPress object cache. A memcached server and the PECL memcached extension are required.
- [Redis Object Cache](https://wordpress.org/plugins/redis-cache/) – Provides a consistent Redis object cache backend for WordPress that works with various Redis clients. A Redis Server is required.

### Further Reading

- [W3 Total Cache Plugin](http://dougal.gunters.org/blog/2009/08/26/w3-total-cache-plugin) (by Dougal Campbell)
- [Holy Shmoly!: WP Super Cache](http://ocaoimh.ie/wp-super-cache/)
- [Best Practices for Speeding Up Your Web Site](http://developer.yahoo.com/performance/rules.html) – Expires / Cache-Control Header and ETags (by Yahoo! Developer Network)
- [WebSiteOptimization.com: Use Server Cache Control to Improve Performance](http://www.websiteoptimization.com/speed/tweak/cache/)
- [WP Object Cache](https://developer.wordpress.org/reference/classes/wp_object_cache/#persistent-cache-plugins))

### Content Offloading

#### Use a content Delivery Network (CDN)

Using a CDN can greatly reduce the load on your website. Offloading the searching and delivery of images, javascript, css and theme files to a CDN is not only faster but takes great load off your WordPress server's own app stack. A CDN is most effective if used in conjunction with a WordPress caching plugin such as W3TC, described above.

[CloudFlare](https://www.cloudflare.com/) is a popular Content Delivery Network, which also offers Internet Security services. Plans start from free, but additional features are available for extra costs. CloudFlare is a fixed-cost CDN, meaning they charge by features instead of usage. CloudFlare allows you to route your sites traffic through their network before coming back to your origin host.

[Amazon Cloudfront](http://aws.amazon.com/cloudfront/) uses the Amazon S3 service to provide Content Delivery Network (CDN) functionality for your static files. A CDN is a service which caches your static files on numerous web servers around the world. Providing faster download performance for your users no matter where they are. It's recommended that you use Cloudfront in tandem with S3 and not only S3 alone; the costs are not significantly different.

[MaxCDN](http://www.maxcdn.com/) is a pay-per-usage Content Delivery Network (CDN) similar to Amazon Cloudfront. Among the differences are support for Video-on-demand as well as "mirroring" (no uploading required) of files, although you can upload them if you prefer.

Another alternative CDN provider is [KeyCDN](https://www.keycdn.com/). They provide step-by-step WordPress integration guides on their [support page](https://www.keycdn.com/support).

KeyCDN and MaxCDN are among the most affordable CDN options available, they're able to beat the pricing of competitors like Amazon because they are a division of a much larger CDN Providers.

#### Static Content

Any static files can be offloaded to another server. For example, any static images, JavaScript or CSS files can be moved to a different server. This is a common technique in very high performance systems (Google, Flickr, YouTube, etc) but can also be helpful for smaller sites where a single server is struggling. Also, moving this content onto different hostnames can lay the groundwork for multiple servers in the future.

Some web servers are optimized to serve static files and can do so far more efficiently than more complex web servers like Apache, for example [lighttpd](http://en.wikipedia.org/wiki/Lighttpd).

[Amazon Simple Storage Service (S3)](http://aws.amazon.com/s3/) is a dedicated static file hosting service on a pay-per-usage basis. With no minimum costs, it might be practical for lower traffic sites which are reaching the peak that a shared or single server can handle.

#### Multiple Hostnames

There can also be user improvements by splitting static files between multiple hostnames. Most browser will only make 2 simultaneous requests to a server, so if you page requires 16 files they will be requested 2 at a time. If you spread that between 4 host names they will be requested 8 at a time. This can reduce page loading times for the user, but it can increase server load by creating more simultaneous requests. Also, known is "pipelining" can often saturate the visitor's internet connection if overused.

Offloading images is the easiest and simplest place to start. All images files could be evenly split between three hostnames (`assets1.example.com`, `assets2.example.com`, `assets3.example.com` for example). As traffic grows, these hostnames could be moved to your own server. Note: Avoid picking a hostname at random as this will affect browser caching and result in more traffic and may also create excessive DNS lookups which do carry a performance penalty.

Likewise any static JavaScript and CSS files can be offloaded to separate hostnames or servers.

#### Feeds

Your feeds can quite easily be offloaded to an external service. Feed tracking services like [Google FeedBurner](http://feedburner.google.com/) will do this automatically, the Feedburner servers will handle all the feed traffic and only update the feed from your site every few minutes. This can be a big traffic saver.

Likewise you could offload your own feeds to a separate server (feeds.yoursite.com for example) and then handle your own feed stats / advertising.

#### Further Reading

- Using Amazon S3 / Cloudfront to offload images: [Amazon S3 and CloudFront with WordPress](http://www.larre.com/2010/01/24/amazon-s3-and-cloudfront-with-wordpress-and-dreamhost/).
- [Reducing Your Website's Bandwidth Usage](http://www.codinghorror.com/blog/archives/000807.html).

### Compression

There are a number of ways to compress files and data on your server so that your pages are delivered more quickly to readers' browsers. W3 Total Cache described above integrates support for most of the common approaches to compression.

W3 Total Cache supports Minify and Tidy to compress and combine your style sheets and javascript files. It also supports output compression such as zlib, see also [Output Compression](https://codex.wordpress.org/Output_Compression).

It's also important to compress your media files – namely images. The [WP Smushit](https://wordpress.org/plugins/wp-smushit/) plugin can help with this.

### Database Tuning

#### Cleaning Your Database

The [WP Optimize](https://wordpress.org/plugins/wp-optimize/) plugin can help you reduce extra clutter in your database.

You can also instruct WordPress to [minimize the number of revisions](https://codex.wordpress.org/Revisions) that it saves of your posts and pages.

### Adding Servers

While it requires additional expertise, adding servers can be a powerful way to increase performance. I highly recommend reading [Architecting a Highly Scalable WordPress Site in AWS](http://www.slideshare.net/harishganesan/scaling-wordpress-in-aws-amazon-ec2) A guide for building a more expensive, highly scalable AWS implementation using Amazon's Relational Data Store (RDS).

You can use Amazon's Elastic Load Balancer to spread traffic across multiple web servers and you can use [HyperDB](https://wordpress.org/plugins/hyperdb/) or Amazon's RDS to run more scalable or multiple database servers.

### Autoloaded Options

Autoloaded options are configuration settings for plugins and themes that are automatically loaded with every page load in WordPress. Each plugin and theme defines their own options and which options are autoloaded. Having too many autoloaded options can slow down your site. Generally, you should try to keep your site's autoloaded options under 800kb.

By default, autoloaded options are saved in the wp_options table. Autoload can be turned off on an option-by-option basis within this table. For step-by-step instructions on viewing and changing autoloaded options, check with your hosting provider.

If you use a Persistent Object Cache, options (whether autoloaded or not) load faster and more efficiently.

## Additional Resources

### Further Reading

- [High Traffic Tips for WordPress](https://codex.wordpress.org/High_Traffic_Tips_For_WordPress)
- [10 Practical Tips To Optimize WordPress hosting](https://www.saotn.org/optimize-wordpress-hosting/)
- [16 Quick Tips to Improve WordPress Performance – INFOGRAPHIC](http://yourescapefrom9to5.com/16-tips-to-improve-wordpress-site-performance-infographic)
- [11 Ways to Speed Up WordPress](http://www.codeinwp.com/blog/ways-to-speed-up-wordpress/)
- [On a quest for ultimate website performance](http://www.i-marco.nl/weblog/archive/2007/05/27/on_a_quest_for_ultimate_websit)
- [WordPress Optimization Infographic](http://www.whoishostingthis.com/blog/2013/11/28/speed-up-wordpress-infographic/)
- [21 easy tweaks to make your WP site load faster](https://hostingfacts.com/how-to-speed-up-your-website/)
- [How to Speed Up WordPress Website with Image Optimization](https://www.mavenecommerce.com/blog/speed-up-wordpress-website-with-image-optimization/)
- [Speeding up WordPress load from 4.23s to 1.33s (Case Study)](http://startbloggingonline.com/speed-up-wordpress/)
- [10 tips for better WordPress optimization](http://www.prelovac.com/vladimir/wordpress-optimization-guide)
- [How To Make Your Site Lightning Fast* By Compressing (deflate/gzip) Your HTML, Javascript, CSS, XML, etc In Apache](http://beerpla.net/2009/06/09/how-to-make-your-site-lightning-fast-by-compressing-deflategzip-your-html-javascript-css-xml-etc-in-apache/)
- [Yahoo! Developer Network: Exceptional Performance](http://developer.yahoo.com/performance/)

### WordCamp Performance Presentations

- [High Performance WordPress = Iliya Polihronov](http://wordpress.tv/2012/09/01/iliya-polihronov-high-performance-wordpress/)
- [WordPress Optimization from WordCamp Israel 2013](http://www.slideshare.net/AlmogBaku/wordpress-optimization-16678718)
- [WordCamp 2007 Video of the Presentation on HyperDB and High Performance](http://onemansblog.com/2007/08/16/wordcamp-2007-hyperdb-and-high-performance-wordpress/)
- [Copy of the Slides on HyperDB and High Performance](http://barry.wordpress.com/2007/07/22/high-performance-wordpress/)
- [50 tips su Web Performance Optimization per siti ad alto traffico WordCamp Bologna (Italy) 2013](http://www.slideshare.net/AndreaCardinali/50-tips-su-web-performance-optimization-per-siti-ad-alto-traffico-wpcamp-bologna-2013)

## Changelog

- 2022-09-11: Original content from [Optimization](https://wordpress.org/support/article/optimization/).
