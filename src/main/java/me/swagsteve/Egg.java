package me.swagsteve;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;

public final class Egg extends JavaPlugin implements Listener {

    //Instance
    private static Egg instance;
    public static Egg getInstance(){
        return instance;
    }

    //Config
    public static Integer egg_percentage;
    public static Boolean is_adult;

    @Override
    public void onEnable() {

        //Instance
        instance = this;

        //Commands
        getCommand("egg-reload").setExecutor(new ReloadCommand());

        //Events
        getServer().getPluginManager().registerEvents(this,this);

        //Config
        File tempConfig = new File(getDataFolder(), "config.yml");
        if (!tempConfig.exists()) {
            getLogger().info("Generating config...");
            saveDefaultConfig();
            reloadConfig();
        }

        //Cache config
        egg_percentage = getConfig().getInt("Setup.egg-percentage");
        is_adult = getConfig().getBoolean("Setup.is-adult");

        //Enabled message
        getLogger().info("Enabled!");
    }

    @EventHandler
    public void EggThrowEvent(PlayerEggThrowEvent e) {
        //Cancel regular hatching, removes duplicates
        e.setHatching(false);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity().getType() == EntityType.EGG) {

            Random r = new Random();
            int chance = r.nextInt(100);
            if (chance < egg_percentage) {

                //Summon Chicken At Egg Land Spot
                org.bukkit.entity.Egg egg = (org.bukkit.entity.Egg) event.getEntity();
                Chicken chicken = (Chicken) egg.getLocation().getWorld().spawnEntity(egg.getLocation(), EntityType.CHICKEN);

                //Set chicken age
                if (is_adult) {
                    chicken.setAdult();
                } else {
                    chicken.setBaby();
                }
            }
        }
    }

    @Override
    public void onDisable() {

        //Disable message
        getLogger().info("Disabled!");

        //Save config
        saveConfig();
    }
}