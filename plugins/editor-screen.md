# File Editor Screen

Among the many user-editable files in a standard WordPress installation are the [Plugins](https://wordpress.org/plugins/) files. Though it should be rare that you need to change a Plugin code, the **Plugin File Editor Screen** allows you to edit those Plugin files.

You can find this editor in the following places depending on your theme:

*   If you are using a [Block theme](https://wordpress.org/documentation/article/block-themes/), this editor will be listed under Tools.
*   If you are using a Classic theme, this editor will be listed under Appearance.

![Edit Plugins Screen](https://raw.githubusercontent.com/WordPress/Advanced-administration-handbook/main/assets/edit-plugins.png)

![Edit Plugins warning](https://raw.githubusercontent.com/WordPress/Advanced-administration-handbook/main/assets/edit-plugins-warning.png)

## Edit Plugins

The built-in Plugin File Editor allows you to view or change any Plugin PHP code in the large text (or edit) box that dominates this Screen.

If a particular file is writeable you can make changes and save the file from here. If not, you will see the message **You need to make this file writable before you can save your changes**.

The name of the Plugin file being edited shows up at the top of the text box. Since Plugin files are pure text, no images or pictures can be inserted into the text box.

You can select a Plugin to edit from the dropdown menu on the top right. Just find a Plugin name and click "Select".

### Plugin Files

Below the Plugin Selection Menu is a list of the Plugin files that can be edited. Click on any of the file links to place the text of that file in the text box.

Be very careful editing activated plugin files. The editor does not make backup copies. If you introduce an error that crashes your site, you cannot use the editor to fix the problem. In such a case, use FTP to either upload a functional backup of the problem file or change the folder name of the _/plugins/_ folder under _/wp-content/_, which effectively deactivates all plugins until the folder is renamed correctly.

### Documentation Lookup

Under the editor, there is a dropdown menu listing function names found in the Plugin file you are editing. By selecting a function and clicking the "Lookup" button, you can view its documentation on [Code Reference](https://developer.wordpress.org/reference/) or [PHP](https://www.php.net/).

## Update File

Remember to click this button to save the changes you have made to the Plugin file. After clicking this button you should see a splash message at the top of the screen saying "File edited successfully". If you don't see that message, then your changes are not saved! Note that if a file is not writeable the Update File button will not be available.

## Changelog

- 2023-04-10: Original content from [Plugin File Editor Screen](https://wordpress.org/documentation/article/plugins-editor-screen/). Minor additions and copy-editing.
