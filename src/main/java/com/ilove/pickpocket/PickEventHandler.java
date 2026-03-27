package com.ilove.pickpocket;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Objects;

public class PickEventHandler implements Listener {
    @EventHandler
    public void onPlayerInteractPlayer(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player target)) return;
        Player interacting = event.getPlayer();
        double distSq = interacting.getLocation().distanceSquared(target.getLocation());
        if (distSq <= Pickpocket.maxDistSq) {
            interacting.openInventory(target.getInventory());
            Pickpocket.interactingPlayers.put(interacting, target);
        }
    }

    public static void onServerTick() {
        Pickpocket.interactingPlayers.keySet().removeIf(player -> {
            if (!(Objects.requireNonNull(Bukkit.getPlayer(player.getUniqueId())).isOnline())) return true;
            Player target = Pickpocket.interactingPlayers.get(player);
            if (player.getOpenInventory().getTopInventory().getType() != InventoryType.PLAYER) {
                return true;
            }
            double distSq = player.getLocation().distanceSquared(target.getLocation());
            if (distSq > Pickpocket.maxDistSq) {
                player.closeInventory();
                return true;
            }
            return false;
        });
    }
}
