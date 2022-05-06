# AnCDK
 A MineCraft Server plugin for CDKeys functions. Using this plugin, you can create CDKs that bind commands. If players enter CDKs, the server would execute the corresponding commands. This plugin can be used as a bridge between servers and the real world. It is widely used in: server sponsorship system, MineCraft auto card issuance, server event prize distribution and many other aspects.
 
 This repo is the Sponge version of this plugin, you may go to the open-source repo link at the bottom of this repo if you need the Bukkit/Spigot version.
 
## Feature

1. Automatic CDK generation of more than 9-bit
2. CDK of same types can be generated in batches
3. Flexible command execution source (console/player)
4. highly customizable configs
5. CDK adding manually
6. Support CDK exporting in batches
7. Support SQL
8. Support multiple languages
9. Feel free to reply if you have any suggestions, it may become a feature of this plugin in future~

## Commands

> The commands of this plugin have several aliases: `ancdkey`, `cdk`, `cdkey`

> Admin Commands:

| Command | Description |
| ---- | ---- |
| /ancdk create <--once> [count] [command] | Create `[count]` of CDK(s) that run `[command]` <br> Use param `console:` in front of your command to specify the command source to console <br> Use param `--once` to let your CDK be a one-time one, otherwise all players can run it once |
| /ancdk export <csv> | Export all CDKs in batches. Use param `csv` can only export cdkeys as a csv file instead of exporting all details |
| /ancdk reload | Reload the config file of the plugin |

> Player commands:

| Command | Description |
| ---- | ---- |
| /ancdk [CDK] | Use `[CDK]` |

## Permissions

> Admin permissions

| Perm Node | Description |
| ---- | ---- |
| `ancdk.admin` | Intent admin permission. Admins don't need other perms any more if you granted this one to them. |
| `ancdk.admin.create` | CDK creating node |
| `ancdk.admin.export` | CDK exporting node |
| `ancdk.admin.reload` | Reloading node |

> Player permissions

| Perm Node | Description |
| ---- | ---- |
| `ancdk.user` | CDK using node |

## Variables
 
> `{player}`                         Presents the player name that run this CDK


## Config

```hocon
Sample config file(cdks.conf): 
CDK that can be executed only once:
"1ll73hur1bhm" {                                 ## CDKey content
    command="econ add {player} 100"              ## The command that will be executed after the cdk is used. Can add "console:" in front of the command to specify the command source to console.
}

CDK that each player can execute once:
"4v1j6bjvtti" {                                             ## CDKey content
    command="console:give {player} minecraft:diamond 5"     ## The command that will be executed after the cdk is used.
    usedPlayer=[                                            ### The player list. Players who have executed this cdk will be listed here. Default value is empty (i.e. []).
        Lileep
    ]
}
```

## Translation

Use a zipping software to locate the `assets/ancdk/lang` folder in the plugin jar, or just download the `lang.zip` file in the release page, then unzip the language file you want to use.

Rename the language file to `language.conf` and replace the file with the same name in the config directory (Default is `config/ancdk`)

Restart the server/Reload the plugin

The translation files that come with the plugin releases now are:

* English

* 中文（简体）

* 中文（繁體）

Feel free to ask if you'd like to participate in translation!

## Tutorial

e.g. 1: Use commands to generate 10 one-time CDKs. The console will give players 100 coins after players run it.

Idea: Execute the `cdk create` command, and bind command: `console:econ add {player} 100`

1. Enter `cdk create --once 10 console:econ add {player} 100`, which generates 10 one-time CDKs that execute the command to give coins

![example1_1](https://github.com/Lileep/AnCDK/blob/sponge/sample_img/example1_1.png)

2. After the command is executed, open the `config/ancdk/cdks.conf` file in the back end to view the generated 10 CDKs

![example1_2](https://github.com/Lileep/AnCDK/blob/sponge/sample_img/example1_2.png)

3. Use any generated CDK in game, such as `/ancdk 43b7039b49ac19`, to use the CDK 

***

e.g. 2: Manually add 1 permanent CDK that can be executed by every player. When players execute it, your console will give 5 diamonds to the users.

Idea: Open the config file and manually write a CDK, the binding command is `console:give {player} minecraft:diamond 5`

1. Open the `config/ancdk/cdks.conf` file with any text editor

2. Write in the sample config file format. Here we use `5diamond` as the content of the CDK, then write the command and an empty list presents for used player in it

![example2](https://github.com/Lileep/AnCDK/blob/sponge/sample_img/example2.png)

3. Enter `/ancdk reload` to reload the plug-in or restart your server, you can then enter `/ancdk 5diamond` in game to run the CDK


## Download

https://wwt.lanzouy.com/b03cxpegj , password: 2dsf

Or download in the release page of this repo

## Open-source Link

Bukkit/Spigot:

[https://github.com/Enron233/AnCDK](https://github.com/Enron233/AnCDK "https://github.com/Enron233/AnCDK")

Sponge:

[https://github.com/Lileep/AnCDK/tree/sponge](https://github.com/Lileep/AnCDK/tree/sponge "https://github.com/Lileep/AnCDK/tree/sponge")

### Bukkit/Spigot version AnCDK is developed by Enron233 (An)~ Thanks for using~~
### Sponge version AnCDK is developed by Lileep
