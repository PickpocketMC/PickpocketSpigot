package com.ilove.pickpocket;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.HashMap;

public final class Pickpocket extends JavaPlugin {
    public static double maxDist = 1.75;
    public static double maxDistSq = maxDist * maxDist;

    public static Map<Player, Player> interactingPlayers = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PickEventHandler(), this);
        new BukkitRunnable() {
            @Override
            public void run() {
                PickEventHandler.onServerTick();
            }
        }.runTaskTimer(this, 0L, 1L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
