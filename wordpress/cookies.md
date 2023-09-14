# Cookies

WordPress uses cookies, or tiny pieces of information stored on your computer, to verify who you are. There are cookies for logged in users and for commenters.

## Enable Cookies in Your Browser

WordPress uses cookies for authentication. That means that in order to log in to your WordPress site, you must have cookies enabled in your browser.

You can find information on how to manage those for the most popular browsers here:
- [Google Chrome](https://support.google.com/chrome/answer/95647)
- [Mozilla Firefox](https://support.mozilla.org/en-US/kb/enable-and-disable-cookies-website-preferences)
- [Microsoft Edge](https://privacy.microsoft.com/en-us/windows-10-microsoft-edge-and-privacy)
- [Safari](https://support.apple.com/guide/safari/manage-cookies-and-website-data-sfri11471/mac)
- [Opera](https://help.opera.com/en/latest/web-preferences/#cookies)
- [Brave](https://brave.com/privacy/browser/)

## User's Cookie

Users are those people who have registered an account with the WordPress site.

On login, WordPress uses the `wordpress_[hash]` cookie to store your authentication details. Its use is limited to the Administration Screen area, `/wp-admin/`.

After login, WordPress sets the `wordpress_logged_in_[hash]` cookie, which indicates when you're logged in, and who you are, for most interface use.

WordPress also sets a few `wp-settings-{time}-[UID]` cookies. The number on the end is your individual user ID from the users database table. This is used to customize your view of admin interface, and possibly also the main site interface.

The cookies lifetime can be adjusted with the `auth_cookie_expiration` hook. An example of this can be found at [what's the easiest way to stop wp from ever logging me out](https://wordpress.stackexchange.com/questions/515/whats-the-easiest-way-to-stop-wp-from-ever-logging-me-out).

### Non-Version-Specific Data

The actual cookies contain your username, the expiration time and _hashed_ data that ensures you have a valid session. A _hash_ is the result of a specific mathematical formula applied to some data. In case of this cookies, only 4 characters of your hashed password stored in a hash in your cookie. This ensures that it is impossible to retrieve your password from the cookie. It also ensures that any cookie will invalidated whenever your password is changed.

WordPress uses the two cookies to bypass the password entry portion of `wp-login.php`. If WordPress recognizes that you have valid, non-expired cookies, you go directly to the [WordPress Administration Screen](https://wordpress.org/documentation/article/administration-screens). If you don't have the cookies, or they're expired, or in some other way invalid (like you edited them manually for some reason), WordPress will require you to log in again, in order to obtain new cookies.

## Commenter's Cookie

When visitors comment on your blog, they get cookies stored on their computer too. This is purely a convenience, so that the visitor won't need to re-type all their information again when they want to leave another comment. Three cookies are set for commenters:

- `comment_author_{HASH}`
- `comment_author_email_{HASH}`
- `comment_author_url_{HASH}`

The commenter cookies are set to expire a little under one year from the time they're set.

## WordPress Test Cookie

WordPress will set a temporary cookie named `wordpress_test_cookie` which is to probe the ability of WordPress to set cookies. If writing this cookie fails, you will get the following error message "Cookies are blocked or not supported by your browser."

In case you get this after moving your website, always try to delete your cookies and if you are using a caching plugin, the server cache. This will solve temporary issues.

## Language Cookie

WordPress allows you to alter the language of all translatable strings on login. For this measure WordPress will set a cookie named `wp_lang` which is a session cookie and will store the language key of the selected language.

## References

- [Wikipedia: Cookies](http://en.wikipedia.org/wiki/HTTP_cookie)
- [RFC2965](http://www.faqs.org/rfcs/rfc2965)
- [PHP cookie documentation](http://www.php.net/manual/en/features.cookies.php)

## Changelog

- 2023-06-08: Adding Test Cookie, language cookie and improvements.
- 2022-09-20: Minor adjustments.
- 2022-09-11: Original content from [Cookies](https://wordpress.org/documentation/article/cookies/); added minor adjustments.
