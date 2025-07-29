package com.poweringg.lavaFurnace;

import java.util.Map;
import java.util.HashMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import static org.bukkit.Bukkit.getLogger;

import static com.poweringg.lavaFurnace.LavaFurnace.ALLOWED_IGNITION_SIDES;


public class EventListener implements Listener {

    JavaPlugin plugin = JavaPlugin.getProvidingPlugin(getClass());
    private final Map<Location, BukkitTask> particleTasks = new HashMap<>(); //active particle effects
    private final ConfigManager config = LavaFurnace.getInstance().getConfigManager(); //get yml config



    //if the player destroys a burning furnace, the ongoing particle effects will be removed
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(config.cfg_enable_particles) {
            Block block = event.getBlock();
            Material type = block.getType();

            if (type == Material.FURNACE || type == Material.BLAST_FURNACE || type == Material.SMOKER) {
                Location loc = block.getLocation();

                //if particles running on this loc, delete them
                BukkitTask task = particleTasks.remove(loc);
                if (task != null) {
                    task.cancel();
                }
            }
        }
    }


    //when player open inventory
    @EventHandler
    public void onFurnaceOpen(InventoryOpenEvent event) {
        InventoryType furnaceType = event.getInventory().getType();

        //allowed types check, stop if type of furnace is not allowed
        boolean isTypeAllowed = switch (furnaceType) {
            case FURNACE -> config.cfg_allow_furnace;
            case BLAST_FURNACE -> config.cfg_allow_blast;
            case SMOKER -> config.cfg_allow_smoker;
            default -> false;
        };
        if (!isTypeAllowed) return;

        //permissions check, stop if plr doesn't have permission
        String permission = switch (furnaceType) {
            case FURNACE -> "lavafurnace.use.furnace";
            case BLAST_FURNACE -> "lavafurnace.use.blast";
            case SMOKER -> "lavafurnace.use.smoker";
            default -> null;
        };
        if (!event.getPlayer().hasPermission(permission)) return;

        //if inventory type is furnace, ignite it
        if (event.getInventory().getHolder() instanceof Furnace furnace) {
            igniteFurnace(furnace, event.getPlayer().getName());
        }
    }

    //when hopper push items into furnace
    @EventHandler
    public void onHopperMoveToFurnace(InventoryMoveItemEvent event) {
        if (!config.cfg_enable_hoppers) return; //only when hopper support is enabled in config

        InventoryType furnaceType = event.getDestination().getType();

        //allowed types check, stop if type of furnace is not allowed
        boolean isTypeAllowed = switch (furnaceType) {
            case FURNACE -> config.cfg_allow_furnace;
            case BLAST_FURNACE -> config.cfg_allow_blast;
            case SMOKER -> config.cfg_allow_smoker;
            default -> false;
        };
        if (!isTypeAllowed) return;

        //if furnacetype is furnace,smoker,blastfurnac, ignite it
        if (event.getDestination().getHolder() instanceof Furnace furnace) {
            igniteFurnace(furnace, "HOPPER");
        }
    }



    //Function to force ignite certain furnace
    private void igniteFurnace(Furnace furnace, String triggerSource) {
        String outputTriggerSource = (triggerSource != null) ? triggerSource : "UNKNOWN";
        Block furnaceBlock = furnace.getBlock();
        Location loc = furnaceBlock.getLocation();

        //do nothing if furnace is already burning
        if (furnace.getBurnTime() > 0) return;

        //check if furnace have ignition material on some of allowed sides, then ignite it
        for (BlockFace face : ALLOWED_IGNITION_SIDES) {
            Material adjacentType = furnaceBlock.getRelative(face).getType();
            if (config.cfg_fuel_materials.contains(adjacentType)) {

                furnace.setBurnTime((short) config.cfg_burn_time);
                furnace.update();
                if(config.cfg_debug) getLogger().info("[LavaFurnace]: "+furnace.getInventory().getType().name()+" at location: "+furnaceBlock.getLocation()+", was ignited with "+adjacentType+", triggered by: "+outputTriggerSource+".");

            }
        }

        //repeat particles on top of furnace if enabled
        if(config.cfg_enable_particles) {
            if (!particleTasks.containsKey(loc)) {
                BukkitTask task = new BukkitRunnable() {
                    int ticks = 0;
                    @Override
                    public void run() {
                        if (ticks++ > 100 || furnace.getBurnTime() <= 0) {
                            particleTasks.remove(loc);
                            cancel();
                            return;
                        }
                        furnaceBlock.getWorld().spawnParticle(Particle.LAVA, loc.clone().add(1.2, 0.5, 0.5), 2, 0.1, 0.1, 0.1, 0.01);
                    }
                }.runTaskTimer(plugin, 0L, 40L); //every 40 ticks
                particleTasks.put(loc, task);
            }
        }
    }





}