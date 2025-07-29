package com.poweringg.lavaFurnace;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    private final LavaFurnace plugin;
    public Commands(LavaFurnace plugin) {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("lavafurnace.admin")) {
                sender.sendMessage("§c[LavaFurnace]: No permissions.");
                return true;
            }

            //call config reload
            plugin.getConfigManager().reloadConfig();
            sender.sendMessage("§a[LavaFurnace]: Reload done.");
            return true;
        }

        sender.sendMessage("§7[LavaFurnace] Usage: /lavafurnace reload");
        return true;
    }
}