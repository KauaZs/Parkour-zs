package org.kauazs.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.kauazs.managers.ParkourManager.endParkour;
import static org.kauazs.managers.ParkourManager.inParkour;

public class PlayerDisconnect implements Listener {
    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e) {
        if (inParkour(e.getPlayer().getUniqueId())) {
            endParkour(e.getPlayer(), false);
            e.getPlayer().getInventory().clear();
        }
    }
}
