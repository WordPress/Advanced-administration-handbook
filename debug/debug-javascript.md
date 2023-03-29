# Using Your Browser to Diagnose JavaScript Errors

If you're experiencing issues with your interactive functionality this may be due to JavaScript errors or conflicts. For example, your flyout menus may be broken, your metaboxes don't drag, or your add media buttons aren't working. In order to formulate your support request it helps the team to know what the JavaScript error is.

This guide will show you how to diagnose JavaScript issues in different browsers.

## Step 1: Try Another Browser

Different browsers often implement parts of JavaScript differently. To make sure that this is a JavaScript error, and not a browser error, first of all try opening your site in another browser.

* if the site is not having the same issue in the new browser you know that the error is browser specific
* if the site is having the same error it is not an error that is specific to one browser

Make note of any browsers you are experiencing the error in. You can use this information when you are making a support request.

## Step 2: Enable SCRIPT_DEBUG

You need to turn on [script debugging](https://wordpress.org/documentation/article/debugging-in-wordpress#SCRIPT_DEBUG). Open `wp-config.php` and add the following line before "That's all, stop editing! Happy blogging".

```
define('SCRIPT_DEBUG', true);
```

Check to see if you are still having an issue.

* **Issue is fixed** – turn off script debugging and report the issue on the support forum, telling the volunteers that you turned on script debugging and it solved the problem.
* **Issue persists** – proceed to Step 3.

## Step 3: Diagnosis

### Open the Developer Tools 

* **Chrome**: Type `Cmd-Option-J` (Mac) or `Ctrl-Shift-J` (Windows, Linux, Chrome OS), or nagivate to `View -> Developer -> Developer Tools` in the menu.
* **Firefox**: Type `Cmd-Option-K` (Mad) or `Ctrl-Shift-K` (Windows, Linux, Chrome OS), or navigate to `Web Development -> Web Console` in the menu.
* **Edge**: Follow the instructions for Chrome.
* **Safari**: First, navigate to `Safari -> Preferences`. Click on the `Advanced` tab, then check `Show Develop Menu in menu bar`. Then, in the new `Develop` menu, navigate to `Show JavaScript Console`.
* **Opera**: Navigate to `Tools -> Advanced -> Error Console` in the menu.

### Identify The Error

Often, the text of the error will already be visible in the console. It may look similar to this:

![Screenshot of an example developer tools error page.](https://wordpress.org/documentation/files/2020/07/chrome-devtools.png)

The image above shows the error to be in `jquery.js on line 2`, however remember to copy the whole stack information! Just saying what line is less helpful that showing context.

If no errors are displayed, reload the page; many errors occur only when the page is first loaded.

## Step 4: Reporting

Now that you have diagnosed your error, you should make your support forum request. Go to the [troubleshooting forum](https://wordpress.org/documentation/forum/how-to-and-troubleshooting).

If your problem is with a specific theme or plugin, you can access their dedicated support forum by visiting https://wordpress.org/support/plugin/PLUGINNAME or https://wordpress.org/support/theme/THEMENAME.

Please include the below information:

* the browsers that you are experiencing the problem in
* whether `SCRIPT_DEBUG` fixed the error or not
* the JavaScript error
* the location of the error – both the file name and the line number
* the context of the error – including the whole error stack will help developers
* If possible, a link to the web page showing the error

## Changelog

- 2022-09-11: Original content from [Using Your Browser to Diagnose JavaScript Errors](https://wordpress.org/documentation/article/using-your-browser-to-diagnose-javascript-errors/). Consolidated Developer Tools instuctions, removed IE documentation.
