# Update Services

Update Services are tools you can use to let other people know you've updated your blog. WordPress automatically notifies popular Update Services that you've updated your blog by sending a [XML-RPC](https://xmlrpc.com/) [ping](https://wordpress.org/documentation/article/glossary/#pingback) each time you create or update a post. In turn, Update Services process the ping and updates their proprietary indices with _your_ update. 

## Common Usage

Most people use [Ping-o-Matic](https://pingomatic.com/) which, with just one "ping" from you, will let many other services know that you've updated. As for why, Ping-O-Matic puts it best:

> Ping-O-Matic is a service to update different search engines that your blog has updated.
> We regularly check downstream services to make sure that they're legit and still work. So while it may appear like we have fewer services, they're the most important ones.

If you do not want the update services to be pinged, remove all the update service URIs listed under "Update Services" on the [Settings](https://wordpress.org/documentation/article/administration-screens/#settings-configuration-settings)->[Writing](https://wordpress.org/documentation/article/settings-writing-screen/) administration screen of your WordPress installation.

![Screenshot of the Update Services screen.](https://wordpress.org/documentation/files/2025/06/update_service.png)

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
By default, editing the Ping Services field is disabled for individual sites in a WordPress Multisite network. To restore this option, you can add a small custom plugin or must-use plugin with the following code:

```
add_action( 'init', function() {
    if ( is_multisite() ) {
        // Allow the Update Services configuration screen to appear.
        add_filter( 'enable_update_services_configuration', '__return_true', 11 );

        // Whitelist the 'ping_sites' option so changes can be saved to the database.
        add_filter( 'whitelist_options', function( $options ) {
            $options['writing'][] = 'ping_sites';
            return $options;
        }, 11 );
    }
} );
```
