package org.kauazs.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

import static org.kauazs.holograms.HologramManager.spawnHolograms;

public class ArmorStandManipulate implements Listener {
    @EventHandler
    public void ArmorStand(PlayerArmorStandManipulateEvent e) {
        if(!e.getRightClicked().isVisible()){
            e.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void EntityDeath(EntityDeathEvent event) {

        if (event.getEntityType() == EntityType.ARMOR_STAND) {
            spawnHolograms();
        }
    }

}
