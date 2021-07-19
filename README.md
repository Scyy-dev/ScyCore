# GenericJavaPlugin
Utility classes used for making Minecraft Java plugins.

The following is a list of all recommended changes when using this template
### Template Variables 
- PLUGIN_NAME - refers to the name of the plugin.
- PLUGIN_PACKAGE - refers to the package the plugin is in (e.g. `me.scyphers.plugins.pluginname`)
- PLUGIN_VERSION - refers to the desired version of the plugin

#### README.md
- Rewrite README to suit plugin

#### build.gradle
- update `group` with PLUGIN_PACKAGE
- update `version` with PLUGIN_VERSION

#### plugin.yml
- update `main` with PLUGIN_PACKAGE and PLUGIN_NAME
- update `name` with PLUGIN_NAME
- update `version` with PLUGIN_VERSION
- update `authors` with all major contributors
- update `commmands` with the commands and descriptions, if any. 
  Make sure not to include permissions with the command, as you can then customise the no permission message 
- update `permissions` with permissions and descriptions, if any

#### Plugin
- rename class and file to PLUGIN_NAME
- update method `onEnable()` to suit commands used in `plugin.yml`
- update method `getSplashText()` with splash text you want to provide

#### CommandFactory
- Update constructor of the factory with new commands and permissions

#### messages.yml
- update `prefix` with a coloured PLUGIN_NAME

#### Misc
- Rename package to PLUGIN_PACKAGE
- Remove any unused classes that are not needed to reduce file size
