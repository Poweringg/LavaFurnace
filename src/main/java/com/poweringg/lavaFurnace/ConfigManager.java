package com.poweringg.lavaFurnace;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import static org.bukkit.Bukkit.getLogger;

public class ConfigManager {
    private final JavaPlugin plugin;

    //config vars
    public boolean cfg_allow_furnace;
    public boolean cfg_allow_blast;
    public boolean cfg_allow_smoker;
    public static List<Material> cfg_fuel_materials = new ArrayList<>();
    public int cfg_burn_time;
    public boolean cfg_enable_hoppers;
    public boolean cfg_enable_particles;
    public boolean cfg_debug;


    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    //get and apply values from config
    public void loadConfig() {
        if(cfg_debug) getLogger().info("[LavaFurnace]: Loading config...");
        FileConfiguration config = plugin.getConfig();

        cfg_allow_furnace = config.getBoolean("furnace");
        cfg_allow_blast = config.getBoolean("blast-furnace");
        cfg_allow_smoker = config.getBoolean("smoker");

        List<String> cfg_fuel_materials_get = config.getStringList("fuel-materials");

        cfg_burn_time = config.getInt("burn-time");
        cfg_enable_hoppers = config.getBoolean("enable-hoppers");
        cfg_enable_particles = config.getBoolean("enable-particles");

        cfg_debug = config.getBoolean("show-debug-in-console");

        //create new fuel material list
        cfg_fuel_materials = cfg_fuel_materials_get.stream()
                .map(String::toUpperCase)
                .map(name -> {
                    try {
                        return Material.valueOf(name);
                    } catch (IllegalArgumentException e) {
                        getLogger().warning("[LavaFurnace]: Invalid material name in config 'fuel-materials': " + name);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();


        //debug only
        if(cfg_debug){
            getLogger().info("[LavaFurnace]: Allowed furnaces:" + (cfg_allow_furnace ? " FURNACE " : "") + (cfg_allow_blast ? " BLAST FURNACE " : "") + (cfg_allow_smoker ? " SMOKER " : ""));
            getLogger().info("[LavaFurnace]: Infinite fuel materials: " + cfg_fuel_materials.stream().map(Material::name).collect(Collectors.joining(", ")));
            getLogger().info("[LavaFurnace]: Furnace burn time in ticks: "+cfg_burn_time);
            getLogger().info("[LavaFurnace]: Hopper support enabled: "+cfg_enable_hoppers);
            getLogger().info("[LavaFurnace]: Enable particles on furnaces: "+cfg_enable_particles);
            getLogger().info("[LavaFurnace]: Configuration loaded.");
        }
    }


    //reload configuration, called via command
    public void reloadConfig() {
        plugin.reloadConfig();
        loadConfig();
    }


}
