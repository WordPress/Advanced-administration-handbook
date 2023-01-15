# Cache

This article is part of a series on [WordPress Optimization](https://developer.wordpress.org/advanced-administration/performance/optimization/).

WordPress caching is the fastest way to improve performance. If your site is getting hit **right now** install [W3 Total Cache](https://wordpress.org/plugins/w3-total-cache/), [WP Super Cache](https://wordpress.org/plugins/wp-super-cache/) or [Cache Enabler](https://wordpress.org/extend/plugins/cache-enabler/).

## Caching Plugins {#caching-plugins}

Plugins like [W3 Total Cache](https://wordpress.org/plugins/w3-total-cache/), [WP Super Cache](https://wordpress.org/plugins/wp-super-cache/) and [Cache Enabler](https://wordpress.org/plugins/cache-enabler/) can be easily installed and will cache your WordPress posts and pages as static files. These static files are then served to users, reducing the processing load on the server. This can improve performance several hundred times over for fairly static pages.

When combined with a system level page cache such as Varnish, this can be quite powerful.

If your posts/pages have a lot of dynamic content configuring caching can be more complex. Search for "WordPress cache plugin" for more info.

## Browser Caching {#browser-caching}

**Browser caching** can help to reduce server load by reducing the number of requests per page. For example, by setting the correct file headers on files that don't change (static files like images, CSS, JavaScript etc) browsers will then cache these files on your visitor's computer. This technique allows the browser to check to see if files have changed, instead of simply requesting them. The result is your web server can answer many more 304 responses, confirming that a file is unchanged, instead of 200 responses, which require the file to be sent.

Look into HTTP Cache-Control (specifically **max-age**) and Expires headers, as well as [Entity Tags](http://en.wikipedia.org/wiki/HTTP_ETag) for more information.

## Server Caching {#server-caching}

**Web server caching** is more complex but is used in very high traffic sites. A wide range of options are available, beyond the scope of this article. The simplest solutions start with the server caching locally while more complex and involved systems may use multiple caching servers (also known as reverse proxy servers) "in front" of web servers where the WordPress application is actually running.

Adding an opcode cache like [Opcache](https://www.php.net/manual/en/book.opcache.php), or [WinCache](https://www.iis.net/downloads/microsoft/wincache-extension) on IIS, to your server will improve PHP's performance by many times.

[Varnish](https://www.varnish-cache.org/) cache is very powerful when used with a WordPress caching plugin such as W3TC.

## Further Reading {#further-reading}

* [W3 Total Cache Plugin](http://dougal.gunters.org/blog/2009/08/26/w3-total-cache-plugin) (by Dougal Campbell)
* [Holy Shmoly!: WP Super Cache](http://ocaoimh.ie/wp-super-cache/)
* [Core Caching Concepts in WordPress](https://www.tollmanz.com/core-caching-concepts-in-wordpress/)
* [Best Practices for Speeding Up Your Web Site](http://developer.yahoo.com/performance/rules.html) – Expires / Cache-Control Header and ETags (by Yahoo! Developer Network)
* [WebSiteOptimization.com: Use Server Cache Control to Improve Performance](http://www.websiteoptimization.com/speed/tweak/cache/)

## Changelog

- 2022-09-04: Original content from [Optimization – Caching](https://wordpress.org/support/article/optimization-caching/).
