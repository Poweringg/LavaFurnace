package com.poweringg.lavaFurnace;

import org.bukkit.block.BlockFace;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public final class LavaFurnace extends JavaPlugin {

    private static LavaFurnace instance;
    private ConfigManager configManager;

    //allowed ignition sides on furnaces
    public static final BlockFace[] ALLOWED_IGNITION_SIDES = {
            BlockFace.DOWN,
            BlockFace.UP,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };


    @Override
    public void onEnable() {
        instance = this;

        //process config file
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        configManager.loadConfig();

        getCommand("lavafurnace").setExecutor(new Commands(this));

        getServer().getPluginManager().registerEvents(new EventListener(), this);

        PluginDescriptionFile pluginyml = this.getDescription();

        getLogger().info("[LavaFurnace]: Plugin LavaFurnace v"+pluginyml.getVersion()+" by Poweringg has been enabled!");
    }



    @Override
    public void onDisable() {
        getLogger().info("[LavaFurnace]: Plugin LavaFurnace was disabled!");
    }

    public static LavaFurnace getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
