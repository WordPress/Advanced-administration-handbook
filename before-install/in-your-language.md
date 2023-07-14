# Installing WordPress in your language

_Note: This article is about displaying the WordPress Administrative "back-end" in your language. If you are looking for information on how to localize your "front-end" website, or customize your theme to be localizable, refer to [i18n for WordPress Developers](https://codex.wordpress.org/I18n_for_WordPress_Developers) (and optionally [Internationalization](https://developer.wordpress.org/themes/functionality/internationalization/) and [Localization](https://developer.wordpress.org/themes/functionality/localization/) for theme developers. If you are interested in how to build a multilingual (e.g.: French / English) WordPress site, you can start your journey [here](https://developer.wordpress.org/advanced-administration/wordpress/multilingual/)._

Although WordPress displays in U.S. English by default, it has the built-in capability to be used in any language. The WordPress community has already translated WordPress into many languages, and there are Themes, translation files, and support available in many other languages (see [WordPress in Your Language](https://developer.wordpress.org/advanced-administration/wordpress/multilingual/)).

## Installing language files from the admin dashboard

As of version 4.0, you can have WordPress [automatically install the language of your choice](https://make.wordpress.org/core/2014/09/05/language-chooser-in-4-0/) during the installation process.

For WordPress 4.1 or later, you can [install language packs directly from the Admin back-end](http://wplang.org/wordpress-4-1-install-language-packs-dashboard/) at any time. WordPress will download them and switch the admin back-end to that language. Navigate to **Settings > General > Site Language** and select from the list of available languages. For Multisite Super Admins, you can set the default language using the Network Administration **Settings** panel.

## Manually installing language files

Here are the steps you will need to follow to install an international version of WordPress.

**Note:** If you make an error in the steps or you do not specify the correct language, WordPress will default back to English. For more help Installing WordPress, see [Installing WordPress](https://developer.wordpress.org/advanced-administration/before-install/howto-install/) and [FAQ Installation](https://wordpress.org/documentation/article/faq-installation/).

* Download the `.mo` language file for your language. The naming convention of the `.mo` files is based on the ISO-639 language code (e.g. _pt_ for Portuguese) followed by the ISO-3166 country code (e.g. _PT_ for Portugal or _BR_ for Brazil). So, the Brazilian Portuguese file would be called `pt_BR.mo`, and a non-specific Portuges file would be called `pt.mo`. Complete lists of codes can be found at [(country codes)](http://www.gnu.org/software/gettext/manual/html_chapter/gettext_16.html#Country-Codes) and [(language codes)](http://www.gnu.org/software/gettext/manual/html_chapter/gettext_16.html#Language-Codes).

## Setting the language for your site

### Single-site installations

#### WordPress 4.0 and above

* Change the language in the admin settings screen. `Settings > General > Site Language`.

#### WordPress 3.9.2 and below

* Open your `wp-config.php` file in a [text editor](https://wordpress.org/documentation/article/wordpress-glossary/#Text_editor) and search for:

```
define( 'WPLANG', '' );
```

* Edit this line according to the `.mo` file you’ve just downloaded, e.g. for the Portuguese spoken in Brazil you must add:

```
define ( 'WPLANG', 'pt_BR' );
```

* Note that if the .mo and .po files don’t exist for a language code called for in wp-config.php then there is no error message, but the code is still used in language_attributes(). This is useful for those of us whose language is similar enough to en_US not to require translation, but who don’t want en-US as the language tag in the blog, instead wanting some other variant of English. For example:

```
define ( 'WPLANG', 'en_GB' );
```

* Once you’ve added your language code, save the file.

### Multisite installations

If you have a [site network](https://developer.wordpress.org/advanced-administration/multisite/create-network/) (WordPress multisite), the language is set on a per-blog basis through the "Site language" option in the `Settings > General` subpanel.

You can set the default language for the entire network under the `Network Admin > Settings` screen ("Default Language").

### Adding translation

If you want to add translations for terms that are still displaying in English after installation, visit [translate.wordpress.org](https://translate.wordpress.org) and select your language. To get started, refer [this page](https://make.wordpress.org/polyglots/handbook/tools/glotpress-translate-wordpress-org/) in the [Translator’s Handbook](https://make.wordpress.org/polyglots/handbook/).

## Changelog

- 2022-09-11: Original content from [Installing WordPress in your language](https://wordpress.org/documentation/article/installing-wordpress-in-your-language/).
