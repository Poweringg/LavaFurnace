
# LavaFurnace 🔥 — Infinite Fuel for your Furnaces

LavaFurnace is a lightweight Minecraft plugin designed to revolutionize how furnaces work. No more worrying about running out of coal or other fuel — simply place lava, fire, or any configurable block or liquid next to your furnace, and it will burn infinitely! This opens up new possibilities for automation and advanced furnace setups, making it perfect for survival servers or custom game modes that want to simplify smelting mechanics without losing immersion.


## Features✨
* 🔥 Supports: **Furnace**, **Smoker**, and **Blast Furnace**

* 🧱 Configure any block or liquid that, when placed next to a furnace, provides an infinite fuel source

* ⚙️ Choose exactly which type of furnace will support infinite fuel

* 🚀 Option to enable or disable automatic ignition of furnaces when items are inserted by hoppers

* ⏱️ Customize how long a furnace stays lit after being activated without fuel

* 🛡️ Permission-based control allowing players to use infinite fuel furnaces only on specific furnace types


## Configuration
You can find configuration file under folder **LavaFurnace/config.yml**
| Setting | Value     | Description                |
| :-------- | :------- | :------------------------- |
| `furnace` | `true` | Enable support for normal Furnaces |
| `smoker` | `true` | Enable support for Smokers |
| `blast-furnace` | `true` | Enable support for Blast Furnaces |
| `fuel-materials` | `- LAVA` | List of materials, which will act as an infinite fuel source for furnaces. [See more]([url](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html)). |


## Permissions
