package me.swagsteve;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

public final class Egg extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        System.out.println("[100% Egg] Has Been Enabled!");
        getServer().getPluginManager().registerEvents(this,this);
    }

    @EventHandler
    public void EggThrowEvent(PlayerEggThrowEvent e) {

        //Cancel Regular Hatching, Removes Duplicates
        e.setHatching(false);

    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity().getType() == EntityType.EGG) {

            //Summon Chicken At Egg Land Spot
            org.bukkit.entity.Egg egg = (org.bukkit.entity.Egg) event.getEntity();
            @SuppressWarnings("deprecation")
            Player player = (Player) egg.getShooter();
            egg.getLocation().getWorld().spawn(egg.getLocation(), Chicken.class);
        }
    }

    @Override
    public void onDisable() {
        // Plugin startup logic
        System.out.println("[100% Egg] Has Been Disabled!");
    }
}
