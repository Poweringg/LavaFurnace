# LavaFurnace 🔥 — Infinite Fuel for your Furnaces

LavaFurnace is a lightweight Minecraft plugin designed to change how fuel in furnaces work. No more worrying about running out of coal or other fuel — simply place lava, fire, or any configurable block or liquid next to your furnace, and it will burn infinitely! This opens up new possibilities for automation and advanced furnace setups, making it perfect for survival servers or custom game modes that want to simplify smelting mechanics without losing immersion.

<br/>

**Tested Minecraft versions: 1.21.8 and newer.**

<br/>

## Features✨
* 🔥 Supports: **Furnace**, **Smoker**, and **Blast Furnace**

* 🧱 Configure any block or liquid that, when placed next to a furnace, provides an infinite fuel source

* ⚙️ Choose exactly which type of furnace will support infinite fuel

* 🚀 Option to enable or disable automatic ignition of furnaces when items are inserted by hoppers

* ⏱️ Customize how long a furnace stays lit after being activated without fuel

* 🛡️ Permission-based control allowing players to use infinite fuel furnaces only on specific furnace types

<br/>

## Configuration
You can find configuration file under folder **LavaFurnace/config.yml**
| Setting  | Default value     | Description                |
| :---------------- | :------- | :--------------------- |
| `furnace` | `true` | Enable support for normal Furnaces |
| `smoker` | `true` | Enable support for Smokers |
| `blast-furnace` | `true` | Enable support for Blast Furnaces |
| `fuel-materials` | `- LAVA` <br/>`- FIRE` | List of materials, which will act as an infinite fuel source for furnaces. Each material on new line, starting with a dash. See [material list](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html). |
| `burn-time` | `20000` | Defines how long the furnace keeps burning after being activated by a player or a hopper. (in Minecraft ticks) |
| `enable-hoppers` | `true` | Enables support for hoppers. When a hopper move items into a furnace, the furnace will ignite automatically, if it has fuel material around it. |
| `enable-particles` | `true` | If enabled, particles will appear on top of the furnace when it is lit. |
| `show-debug-in-console` | `true` | Display additional debug and furnace usage information in the console |

<br/>

## Permissions
| Permission | Description |
| :---------------- | :--------------------- |
| `lavafurnace.use.*` | It will allow the player to use all types of furnaces with infinite fuel. |
| `lavafurnace.use.furnace` | Allows the use of infinite fuel only for the regular furnace. |
| `lavafurnace.use.blast` | Allows the use of infinite fuel only for the Blast furnaces. |
| `lavafurnace.use.smoker` | Allows the use of infinite fuel only for the Smokers. |
| `lavafurnace.admin` | Allows the use of LavaFurnace admin commands. |

<br/>

## Commands
| Command | Description |
| :---------------- | :--------------------- |
| `/lavafurnace reload` | Reloads config. |
