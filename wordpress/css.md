# CSS

[tip]As of WordPress 6.2, you can add custom CSS in the Styles area of the Site Editor. Learn more about the [site-wide and per-block custom CSS editors](https://wordpress.org/documentation/article/styles-overview/#applying-custom-css).[/tip]

WordPress relies heavily on the presentation styles within CSS. With the use of [Themes](https://wordpress.org/documentation/article/using-themes/), you have an almost infinite choice of layout options. WordPress Themes make it easy to change your website’s appearance, and open up the field to help you [create your own Theme](https://codex.wordpress.org/Theme_Development) and page layout.

[CSS](https://wordpress.org/documentation/article/glossary/#css) stands for **Cascading Style Sheets**. It allows you to store style presentation information (like colors and layout) separate from your HTML structure. This allows precision control of your website layout and makes your pages faster and easier to update.

This article briefly describes the use of CSS in WordPress, and lists some references for further information. For information on CSS itself, see [Resources for Building on WordPress#CSS](https://developer.wordpress.org/advanced-administration/resources/#CSS).

## [WordPress and CSS](#wordpress-and-css)

WordPress Themes use a combination of [template files](https://codex.wordpress.org/Templates), [template tags](https://codex.wordpress.org/Template_Tags), and CSS files to generate your WordPress site’s look.

### [Template Files](#template-files)

[Template files](https://codex.wordpress.org/Stepping_Into_Templates) are the building blocks which come together to create your site. In the [WordPress Theme structure](https://developer.wordpress.org/advanced-administration/wordpress/site-architecture/), the header, sidebar, content, and footer are all contained within individual files. They join together to create your page. This allows you to customize the building blocks. For example, in the default WordPress Theme, the multi-post view found on the front page, category, [archives](https://codex.wordpress.org/Creating_an_Archive_Index), and [search](https://codex.wordpress.org/Creating_a_Search_Page) web pages on your site, the sidebar is present. Click on any post, you will be taken to the single post view and the sidebar will now be gone. You can [choose which parts and pieces appear](https://codex.wordpress.org/The_Loop_in_Action) on your page, and customize them individually, allowing for a different header or sidebar to appear on all pages within a specific category. And more. For a more extensive introduction to Templates, see [Stepping Into Templates](https://codex.wordpress.org/Stepping_Into_Templates).

### [Template Tags](#template-tags)

Template tags are the bits of code which provide instructions and requests for information stored within the WordPress database. Some of these are highly configurable, allowing you to customize the date, time, lists, and other elements displayed on your website. You can learn more about template tags in [Stepping Into Template Tags](https://codex.wordpress.org/Stepping_Into_Template_Tags).

### [Stylesheet](#stylesheet)

The CSS file is where it all comes together. On every template file within your site there are HTML elements wrapped around your template tags and content. In the stylesheet within each Theme are rules to control the design and layout of each HTML element. Without these instructions, your page would simply look like a long typed page. With these instructions, you can move the building block structures around, making your header very long and filled with graphics or photographs, or simple and narrow. Your site can “float” in the middle of the viewer’s screen with space on the left and right, or stretch across the screen, filling the whole page. Your sidebar can be on the right or left, or even start midway down the page. How you style your page is up to you. But the instructions for styling are found in the `style.css` file within each Theme folder.

## [Custom CSS in WordPress](#custom-css-in-wordpress)

Starting with WordPress 4.7, you can now add custom CSS to your own theme from the [Appearance Customize Screen](https://wordpress.org/documentation/article/appearance-customize-screen/), without the need for additional plugins or directly editing themes and child themes. Just choose the **Additional CSS** tab when customizing your current theme to get started!

Any CSS changes you make will appear in the preview, just like other changes made in the customizer, this means you have time to tweak and perfect the look of your site, without actually changing anything until you are happy with it all!

Keep in mind that the CSS changes are tied in with your theme. This means that if you change to a new theme, your custom CSS styles will no longer be active (of course, if you change back to your previous theme, they will once again be there).

### [Why use Custom CSS?](#why-use-custom-css)

There are a few reasons why:

* If you modify a theme directly and it is updated, then your modifications may be lost. By using Custom CSS you will ensure that your modifications are preserved.
* Using Custom CSS can speed up development time.
* Custom CSS is loaded after the theme’s original CSS and thus allows overriding specific CSS statements, without having to write an entire CSS set from scratch.

## [WordPress Generated Classes](#wordpress-generated-classes)

Several classes for aligning images and block elements (`div`, `p`, `table` etc.) were introduced in WordPress 2.5: `aligncenter`, `alignleft` and `alignright`. In addition the class `alignnone` is added to images that are not aligned, so they can be styled differently if needed.

The same classes are used to align images that have a caption (as of WordPress 2.6). Three additional CSS classes are needed for the captions, and one more for accessibility. Together, the classes are:

```
/* WordPress Core
-------------------------------------------------------------- */
.alignnone {
  margin: 5px 20px 20px 0;
}

.aligncenter,
div.aligncenter {
  display: block;
  margin: 5px auto 5px auto;
}

.alignright {
  float:right;
  margin: 5px 0 20px 20px;
}

.alignleft {
  float: left;
  margin: 5px 20px 20px 0;
}

a img.alignright {
  float: right;
  margin: 5px 0 20px 20px;
}

a img.alignnone {
  margin: 5px 20px 20px 0;
}

a img.alignleft {
  float: left;
  margin: 5px 20px 20px 0;
}

a img.aligncenter {
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.wp-caption {
  background: #fff;
  border: 1px solid #f0f0f0;
  max-width: 96%; /* Image does not overflow the content area */
  padding: 5px 3px 10px;
  text-align: center;
}

.wp-caption.alignnone {
  margin: 5px 20px 20px 0;
}

.wp-caption.alignleft {
  margin: 5px 20px 20px 0;
}

.wp-caption.alignright {
  margin: 5px 0 20px 20px;
}

.wp-caption img {
  border: 0 none;
  height: auto;
  margin: 0;
  max-width: 98.5%;
  padding: 0;
  width: auto;
}

.wp-caption p.wp-caption-text {
  font-size: 11px;
  line-height: 17px;
  margin: 0;
  padding: 0 4px 5px;
}

/* Text meant only for screen readers. */
.screen-reader-text {
  border: 0;
  clip: rect(1px, 1px, 1px, 1px);
  clip-path: inset(50%);
  height: 1px;
  margin: -1px;
  overflow: hidden;
  padding: 0;
  position: absolute !important;
  width: 1px;
  word-wrap: normal !important; /* Many screen reader and browser combinations announce broken words as they would appear visually. */
}

.screen-reader-text:focus {
  background-color: #eee;
  clip: auto !important;
  clip-path: none;
  color: #444;
  display: block;
  font-size: 1em;
  height: auto;
  left: 5px;
  line-height: normal;
  padding: 15px 23px 14px;
  text-decoration: none;
  top: 5px;
  width: auto;
  z-index: 100000;
  /* Above WP toolbar. */
}
```

Each Theme should have these or similar styles in its `style.css` file to be able to display images and captions properly. The exact HTML elements and class and ID values will depend on the structure of the Theme you are using.

## [Templates and CSS](#templates-and-css)

To help you understand more about how CSS works in relationship to your web page, you may wish to read some of the articles cited in these lists:

* [Using Themes](https://wordpress.org/documentation/article/using-themes/) – There are also many advanced articles in this list.
* [Templates](https://codex.wordpress.org/Templates) – Comprehensive list of WordPress Theme and Template articles.
* [Theme Development](https://codex.wordpress.org/Theme_Development) – WordPress Theme Development guide and code standards.

## [WordPress Layout Help](#wordpress-layout-help)

If you are having some problems or questions about your WordPress Theme or layout, begin by checking the website of the Theme author to see if there is an upgrade or whether there are answers to your questions. Here are some other resources:

* [Lessons on Designing Your WordPress Site](https://wordpress.org/documentation/article/wordpress-lessons/)
* [Site Design and Layout](https://codex.wordpress.org/Site_Design_and_Layout) – Comprehensive list of resources related to site design in WordPress.
* [FAQ Layout and Design](https://codex.wordpress.org/FAQ_Layout_and_Design)

## [CSS Resources](#css-resources)

* [Finding Your CSS Styles](https://codex.wordpress.org/Finding_Your_CSS_Styles)
* [CSS Troubleshooting](https://codex.wordpress.org/CSS_Troubleshooting)
* [CSS Fixing Browser Bugs](https://codex.wordpress.org/CSS_Fixing_Browser_Bugs)
* [CSS Coding Standards](https://developer.wordpress.org/coding-standards/wordpress-coding-standards/css/)
* [CSS Shorthand](https://codex.wordpress.org/CSS_Shorthand)
* [Resources for Building on WordPress#CSS](https://developer.wordpress.org/advanced-administration/resources/#CSS)
* [Conditional Comment CSS](https://codex.wordpress.org/Conditional_Comment_CSS)
* [Validating a Website](https://codex.wordpress.org/Validating_a_Website)

## Changelog

- 2022-09-04: Original content from [CSS](https://wordpress.org/documentation/article/css/); ticket [Github](https://github.com/WordPress/Documentation-Issue-Tracker/issues/424).
