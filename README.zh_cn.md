# AnCDK
 MineCraft服务器CDKey插件，使用这个插件，你可以创建绑定命令的CDK，当玩家输入CDK时，服务器便可以执行相应的命令，本插件可作为连通服务器内与现实世界的桥梁，广泛应用于：“服务器赞助系统、MineCraft无人售卡、服务器活动奖品发放”等多个方面。
 
 此仓库为插件的Sponge版，需要Bukkit/Spigot版的用户可前往仓库最下方的开源仓库链接
 
## 插件特性

1. 全自动一键生成9位以上CDK
2. 可批量生成同类型CDK
3. 可设置命令的执行方式（控制台/玩家）
4. 可一次性执行多条命令
5. 配置文件高度自定义
6. 可自定义添加CDK
7. 支持批量导出CDK
8. 支持SQL
9. 支持多种语言
10. （赶紧点击回复，马上你的建议将成为插件的特♂色~

## 插件命令

> 本插件命令还有如下写法：`ancdkey`, `cdk`, `cdkey`

> 管理员命令:

| 命令 | 功能 |
| ---- | ---- |
| /ancdk create <--once> [数量] [命令] | 创建`[数量]`个执行`[命令]`的CDK <br> 命令前可使用`console:`指定控制台执行 <br> 使用`--once`参数可让CDK为一次性，否则每个玩家均可使用一次此CDK <br>一次执行多个命令可以使用`;`分隔 |
| /ancdk export <csv> | 批量一键导出所有CDK。使用`csv`参数可以仅将所有cdkey导出为csv文件 |
| /ancdk reload | 重载插件配置文件 |

> 玩家命令:

| 命令 | 功能 |
| ---- | ---- |
| /ancdk [CDK] | 使用`[CDK]` |

## 插件权限

> 管理员权限

| 节点 | 描述 |
| ---- | ---- |
| `ancdk.admin` | 总权限。给予此权限后无需剩余管理员权限 |
| `ancdk.admin.create` | 创建CDK |
| `ancdk.admin.export` | 导出CDK |
| `ancdk.admin.reload` | 重载 |

> 用户权限

| 节点 | 描述 |
| ---- | ---- |
| `ancdk.user` | 使用CDK的权限 |

## 插件变量
 
> `{player}`                         代表使用CDK的玩家


## 配置文件

```hocon
配置文件示例（cdks.conf）： 
仅可执行一次的CDK： 
"1ll73hur1bhm" {                                 ## CDKey内容
    command="econ add {player} 100"              ## 使用CDK后要执行的命令。可在命令前添加"console:"来指定是否以控制台身份执行
}

每个玩家均可执行一次的CDK： 
"4v1j6bjvtti" {                                             ## CDKey内容
    command="console:give {player} minecraft:diamond 5"     ## 使用CDK后要执行的命令
    usedPlayer=[                                            ### 玩家列表。执行过的玩家会被记录在此，默认为空（即[]）
        Lileep
    ]
}
```

## 翻译

使用压缩软件找到插件jar包内的`assets/ancdk/lang`文件夹，或者下载发行版中的`lang.zip`文件，随后解压你要使用的语言文件

将语言文件重命名为`language.conf`并替换掉配置目录（默认`config/ancdk`）下的同名文件

重启服务器/重载插件即可

目前本插件自带的翻译文件有：

* English

* 中文（简体）

* 中文（繁體）

欢迎参与翻译工作！

## 使用教程&图文介绍

例1：使用命令生成 10 个一次性CDK，玩家执行后控制台会给其 100 金币

思路：执行`cdk create`命令，绑定命令为`console:econ add {player} 100`

1. 输入`cdk create --once 10 console:econ add {player} 100`，即生成 10 个执行给予金币命令的一次性CDK

![example1_1](https://github.com/Lileep/AnCDK/blob/sponge/sample_img/example1_1.png)

2. 指令执行完毕后打开后台的`config/ancdk/cdks.conf`文件查看生成的 10 个CDK

![example1_2](https://github.com/Lileep/AnCDK/blob/sponge/sample_img/example1_2.png)

3. 在游戏中使用任意一个CDK，如`/ancdk 43b7039b49ac19`，即可领取该CDK

***

例2：手动添加 1 个永久的CDK，每个玩家执行后控制台会为其发放 5 个钻石

思路：打开配置文件并手动写入CDK，绑定命令为`console:give {player} minecraft:diamond 5`

1. 使用任意文本编辑器打开`config/ancdk/cdks.conf`文件

2. 按照配置文件示例格式进行书写。这里我们将`5diamond`作为CDK的内容，并将指令和空的已使用玩家列表写进去

![example2](https://github.com/Lileep/AnCDK/blob/sponge/sample_img/example2.png)

3. 输入`/ancdk reload`重载插件或直接重启，即可在游戏中输入`/ancdk 5diamond`领取该CDK


## 插件下载

https://wwt.lanzouy.com/b03cxpegj 密码:2dsf

或在仓库的release中即可下载

## 开源地址

Bukkit/Spigot:

[https://github.com/Enron233/AnCDK](https://github.com/Enron233/AnCDK "https://github.com/Enron233/AnCDK")

Sponge:

[https://github.com/Lileep/AnCDK/tree/sponge](https://github.com/Lileep/AnCDK/tree/sponge "https://github.com/Lileep/AnCDK/tree/sponge")

### Bukkit/Spigot版AnCDK，由小安开发~ 感谢你的使用~~
### Sponge版AnCDK，由触手百合（Lileep）开发
