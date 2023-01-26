# oEmbed

The easy embedding feature is mostly powered by oEmbed, a protocol for consumers (such as your blog) to ask providers (such as YouTube) for the HTML needed to embed content from the provider.

oEmbed is designed to avoid the need to copy and paste HTML from the site hosting the media you wish to embed. It supports different kind of content like videos, images, text, and more.

## Does This Work With Any URL?

No, not by default. The WordPress Core has an [internal whitelist](https://developer.wordpress.org/reference/hooks/oembed_providers/) that will only allow certain URLs to be embeddable for security reasons. The good news is that the whitelist can be modified, and new sites and URLs can be added by registering their handle. 

## How Can I Add or Change Support For Websites?

Adding support for an additional website depends on whether the site supports oEmbed. [oEmbed.com](https://oembed.com/) provides a list of hundreds of [supported provides](https://oembed.com/providers.json).

### Adding Support For An oEmbed-Enabled Site

If a site supports oEmbed, you'll want to call [`wp_oembed_add_provider()`](https://developer.wordpress.org/reference/functions/wp_oembed_add_provider/) to add the site and URL format to the internal whitelist.

### Adding Support For A Non-oEmbed Site

You'll need to register a handler using [`wp_embed_register_handler()`](https://developer.wordpress.org/reference/functions/wp_embed_register_handler/) and provide a callback function that generates the HTML.

### Removing Support for An oEmbed-Enabled Site

If you wish to remove an oEmbed-enabled provider, you'll want to call [`wp_oembed_remove_provider`](https://developer.wordpress.org/reference/functions/wp_oembed_remove_provider/).

## What About oEmbed Discovery?

As of version 4.4, WordPress supports oEmbed discovery, but has severe limitations on what type of content can be embedded via non-whitelisted sites.

Specifically, the HTML and Video content is filtered to only allow links, blockquotes, and iframes, and these are additionally filtered to prevent insertion of malicious content. The HTML is then modified to be sandboxed and to have additional security restrictions placed on them as well.

However, if you feel you are knowledgeable enough to not require this level of safety, you can give `unfiltered_html` users (Administrators and Editors) the ability to embed from websites that have oEmbed discovery tags in their `<head>`.

The oEmbed discovery content for "link" and "photo" types is not quite so heavily filtered in this manner; however, it is properly escaped for security and to prevent any malicious content from being displayed on the site

## Changelog

- 2023-01-25: Review and Update Content. Linked list of whitelisted providers.
- 2022-09-11: Added content from [oEmbed](https://docs.google.com/document/d/1ni59ohlSHeCH_BwRtxUXzGY1LLWqFiaaGQFcCjh_2rQ/).
