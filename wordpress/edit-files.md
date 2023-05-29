# Editing Files

There are times when you will need to edit WordPress files, especially if you want to change your [WordPress Theme](https://wordpress.org/documentation/article/worik-with-themes/). WordPress features a [built-in editor](https://developer.wordpress.org/advanced-administration/wordpress/edit-files/#using-the-theme-file-editor-and-plugin-file-editor) that allows you to edit files online, using any internet browser. You can also edit files copied or stored on your computer, and then upload them to your site using an [FTP client](https://developer.wordpress.org/advanced-administration/upgrade/ftp/).

Before editing any of your WordPress files, be sure to do the following:

* Work from copies of backup files when possible, and make sure that you [back up your information](https://developer.wordpress.org/advanced-administration/security/backup/) frequently–while you work, and whenever you make changes. Remember to keep your backups in a safe place!
* When working online, you need to set the appropriate [file permissions](https://developer.wordpress.org/advanced-administration/server/file-permissions/) so that you can modify and save files. If you see a note at the bottom of the WordPress editor panel that says *If this file was writable, you could edit it…* this means that you need to change the file permissions before you can make any changes.
* When modifying files outside the built-in plugin and theme editors, use a [text editor](https://wordpress.org/documentation/article/wordpress-glossary/#text-editor). **It is strongly advisable not to use a word-processing program**. Word processors change quote marks to characters, they sometimes convert specific characters, and they can also add in unwanted code. These changes can cause files to break. For similar reasons, it is also inadvisable to use certain HTML generator programs.

## Using the Theme File Editor and Plugin File Editor

WordPress contains two built-in editors that allow you to edit theme files directly from your browser. They are called the **theme file editor** and the **plugin file editor**.

Please note that, depending on the level of user privileges that you have, you may or may not be able to access these features in the administrative panel of your blog. Please contact your blog or website administrator, to have your privileges adjusted.

### Where can I find these editors?

You can find these editors in the following places, depending on your theme:

* If you are using a [Block theme](https://wordpress.org/documentation/article/block-themes/), both the Theme and Plugin File Editor will be listed under Tools.
* If you are using a Classic theme, the Theme File Editor will be listed under Appearance and the Plugin File Editor will be listed under Plugins.

You can view a file in either of these editors by accessing it from the right-hand sidebar navigation.

More information on editing themes is available at [Theme Developer Handbook](https://developer.wordpress.org/themes/).

Be aware that if the theme you edit is updated, your changes will be overwritten. To better organize your changes and protect them from updates, consider creating a [Child Theme](https://developer.wordpress.org/themes/advanced-topics/child-themes/) in which to keep all your changes.

### What Files Can Be Edited?

The following file types (if [writable](https://developer.wordpress.org/advanced-administration/server/file-permissions/)) can be edited in the plugin editor that is built into the WordPress administrative panel:

* [HTML](https://wordpress.org/documentation/article/glossary#html)
* [PHP](https://wordpress.org/documentation/article/glossary#php)
* [CSS](https://wordpress.org/documentation/article/glossary#css)
* TXT (and related text-like files such as RTF)

In the theme editor, only writable PHP and CSS files can be edited.

### Things You Need to Know

#### Instant Changes

The changes you make to files using the WordPress editors are instant. The changes happen online, in real-time. You and any visitors to your site will see the changes, immediately.

Because of the immediate nature of the changes, it's usually safer to edit copies of your files offline, test the file copies, and then upload your changes when they are verified.

Always make sure you have a current backup before editing files.

#### Editor Features

The built-in WordPress plugin and theme file editors are very basic, allowing you to easily view and edit plugin and theme files on your website. Please note that there are no advanced editor features such as search and replace, line numbers, syntax highlighting, or code completion.

Hint: Use your browser's internal search bar to help find code within the visual editors.

#### File Permissions

To edit a file using the built-in WordPress plugin and theme editors, the permissions for that file must be set to writable (at least 604). You can [change the permissions](https://developer.wordpress.org/advanced-administration/server/file-permissions/) on files by using an [FTP client program](https://developer.wordpress.org/advanced-administration/upgrade/ftp/), a web-based file manager provided by your host, or from the [command-line](https://wordpress.org/documentation/article/glossary#shell) using SSH (secure shell). Your options depend on the type of access your host offers.

#### Make a Mistake? Use Backup Files

Back up all files before editing. If you make a mistake that causes errors, causes a site crash, creates a blank screen, or blocks access to your WordPress Dashboard, delete the changed file and replace it with a good copy from your backup.

No backup? Download a fresh copy of the file you edited from the original source, replace it, and start over. BACK UP FIRST.

#### Security Warning

By default, any user that logs in with administrative permissions can access the WordPress plugin and theme editors, and change any theme or plugin file on your site in real-time.

To combat accidents, errors, or even hacking, you may wish to disable the ability to edit files within the WordPress theme by adding the [DISALLOW_FILE_EDIT](https://wordpress.org/documentation/article/editing-wp-config-php/#disable-the-plugin-and-theme-editor) function to your `wp-config.php` file.

## Editing Files Offline

To edit files offline, you can use any of the [recommended text editors](https://developer.wordpress.org/advanced-administration/wordpress/edit-files/#text-editors) to create and edit files, and an [FTP client](https://developer.wordpress.org/advanced-administration/upgrade/ftp/) to upload them. Make sure to view the results in your browser, to see if the desired changes have taken effect.

**Note**: It is not recommended to change WordPress core files other than [wp-config.php](https://wordpress.org/documentation/article/editing-wp-config-php/). If you must change anything else, take notes about your changes, and store a copy of these notes in a text file in your WordPress root directory. You should also make a backup copy of your WordPress core files, for future reference and upgrades.

## Using Text Editors

### Editors to Avoid

Editors to avoid include any do-it-yourself instant web page software (like Adobe Dreamweaver), or text processor (like Google Docs or Microsoft Word).

**Note:** If you use an external editor such as a word-processor to create and edit files, this can corrupt the file you are editing. See [text editor](https://wordpress.org/documentation/article/wordpress-glossary/#text-editor) in the glossary for a short explanation as to why you should avoid these editors.

### Text Editors

The following [text editors](https://wordpress.org/documentation/article/wordpress-glossary/#text-editor) are acceptable for file editing:

* [BBEdit](https://www.barebones.com/products/bbedit/) (Mac, $)
* [Crimson Editor](http://www.crimsoneditor.com/) (Windows, Free)
* [EditPad](https://www.editpadpro.com/) (Windows)
* [EditPlus](https://www.editplus.com/) (Windows)
* [emacs](https://www.gnu.org/software/emacs/emacs.html) (Mac, Linux, Windows) Open-Source, Free
* [JEdit](http://jedit.org/) (Mac, Linux, Windows)
* [Notepad++](https://notepad-plus-plus.org/) (Windows) Open-Source, Free
* [PSPad](https://www.pspad.com/) (Windows) Free
* [Smultron](https://www.peterborgapps.com/smultron/) (Mac) $
* [SubEthaEdit](https://apps.apple.com/us/app/subethaedit/id728530824) (Mac) Open-Source, Free
* [Sublime Text](https://www.sublimetext.com/) (Mac, Linux, Windows) $
* [TextMate](https://macromates.com/) (Mac) $
* [TextPad](https://www.textpad.com/home) (Windows)
* [UltraEdit-32](https://www.ultraedit.com/) (Mac, Linux, Windows) $
* [vim](https://www.vim.org/) (Mac, Linux, Windows) Open-Source, Free
* [Visual Studio Code](https://code.visualstudio.com/) (Mac, Linux, Windows)
* [NetBeans](https://netbeans.apache.org/) (Mac, Linux, Windows)

## Changelog

- 2023-01-20: Updated broken links. Removed non-existing text editors.
- 2022-09-11: Original content from [Editing Files](https://wordpress.org/documentation/article/editing-files/).