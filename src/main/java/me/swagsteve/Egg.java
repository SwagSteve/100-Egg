package me.swagsteve;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import java.util.Random;

public final class Egg extends JavaPlugin implements Listener {

    //Instance
    private static Egg instance;
    public static Egg getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        getCommand("egg-reload").setExecutor(new ReloadCommand());
        instance = this;
        System.out.println("[100% Egg] Has Been Enabled!");
        getServer().getPluginManager().registerEvents(this,this);
        this.getConfig().options().copyDefaults();
        this.getConfig().addDefault("Setup.EggChance-Of-100", 100);
        saveDefaultConfig();
    }

    @EventHandler
    public void EggThrowEvent(PlayerEggThrowEvent e) {

        //Cancel Regular Hatching, Removes Duplicates
        e.setHatching(false);

    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity().getType() == EntityType.EGG) {

            Random r = new Random();
            int chance = r.nextInt(100);
            if (chance < getConfig().getInt("Setup.EggChance-Of-100")) {
                //Summon Chicken At Egg Land Spot
                org.bukkit.entity.Egg egg = (org.bukkit.entity.Egg) event.getEntity();
                @SuppressWarnings("deprecation")
                Player player = (Player) egg.getShooter();
                egg.getLocation().getWorld().spawn(egg.getLocation(), Chicken.class);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin startup logic
        System.out.println("[100% Egg] Has Been Disabled!");
        saveConfig();
    }
}
