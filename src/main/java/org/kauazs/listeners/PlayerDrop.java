package org.kauazs.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import static org.kauazs.managers.ParkourManager.endParkour;
import static org.kauazs.managers.ParkourManager.inParkour;

public class PlayerDrop implements Listener {
    @EventHandler
    public void PlayerClickEvent(InventoryClickEvent e) {
        if (inParkour(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerDropEvent(PlayerDropItemEvent e) {
        if (inParkour(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
