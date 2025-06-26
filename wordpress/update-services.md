# Update Services

Update Services are tools you can use to let other people know you've updated your blog. WordPress automatically notifies popular Update Services that you've updated your blog by sending a [XML-RPC](https://xmlrpc.com/) [ping](https://wordpress.org/documentation/article/glossary/#pingback) each time you create or update a post. In turn, Update Services process the ping and updates their proprietary indices with _your_ update. 

## Common Usage

Most people use [Ping-o-Matic](https://pingomatic.com/) which, with just one "ping" from you, will let many other services know that you've updated. As for why, Ping-O-Matic puts it best:

> Ping-O-Matic is a service to update different search engines that your blog has updated.
> We regularly check downstream services to make sure that they're legit and still work. So while it may appear like we have fewer services, they're the most important ones.

If you do not want the update services to be pinged, remove all the update service URIs listed under "Update Services" on the [Settings](https://wordpress.org/documentation/article/administration-screens/#settings-configuration-settings)->[Writing](https://wordpress.org/documentation/article/settings-writing-screen/) administration screen of your WordPress installation.

![Screenshot of the Update Services screen.](https://wordpress.org/documentation/files/2018/10/update_service.png)

Certain web hosts – particularly free ones – disable the PHP functions used to alert update services. If your web host prevents pings, you should stop WordPress from attempting to ping.

## XML-RPC Ping Services

```
https://rpc.pingomatic.com
https://rpc.twingly.com
http://ping.blo.gs/
```

## Alternatives
An alternative is [Feed Shark](https://feedshark.brainbliss.com/), which pings over 60 services for free.

## WordPress Multisite Network
By default, editing the Ping Services for a WordPress Multisite network site is disabled. This can be re-enabled with a plugin such as the [Activate Update Services](https://wordpress.org/plugins/activate-update-services/) plugin.


