package org.kauazs.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static org.kauazs.managers.ParkourManager.inParkour;

public class DamageEvent implements Listener {
    @EventHandler
    public void Damage(EntityDamageEvent e) {
        if(e.getEntityType() == EntityType.PLAYER) {
            if(inParkour(e.getEntity().getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }
}
