package org.kauazs.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.kauazs.Parkour_zs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.kauazs.holograms.HologramManager.createHologram;
import static org.kauazs.holograms.HologramManager.spawnHolograms;
import static org.kauazs.managers.ParkourManager.*;
import static org.kauazs.utils.LocationGross.getLocation;

public class ClickEvent implements Listener {
    ArrayList<String> checkpoints = new ArrayList();

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) throws IOException {
        ItemStack item = e.getItem();
        Player p = e.getPlayer();

        String ID = getParkour(p);

        if (item == null) return;
        Location local = e.getClickedBlock() != null ? e.getClickedBlock().getLocation() : null;

        File file = new File(Parkour_zs.getInstance().getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (item.getType() == Material.REDSTONE_BLOCK && item.hasItemMeta()) {
            if (local == null || ID == null) return;

            config.set("config.parkours." + ID + ".world", local.getWorld().getName());
            config.set("config.parkours." + ID + ".start", getLocation(local.getBlock().getLocation().add(0, 1, 0)));

            config.save(file);

            p.sendMessage(ChatColor.GREEN + "Ponto de inicio configurado!");
            e.setCancelled(true);

        } else if (item.getType() == Material.GOLD_BLOCK && item.hasItemMeta()) {
            if (local == null || ID == null) return;
            checkpoints.add(getLocation(local.getBlock().getLocation()));
            config.set("config.parkours." + ID + ".checkpoints", checkpoints);
            config.save(file);

            p.sendMessage(ChatColor.GREEN + "Checkpoint adicionado! (" + checkpoints.size() + ")");
            e.setCancelled(true);
        } else if (item.getType() == Material.WOOL && item.hasItemMeta()) {
            if (local == null || ID == null) return;
            config.set("config.parkours." + ID + ".end", getLocation(local.getBlock().getLocation()));
            config.save(file);

            p.sendMessage(ChatColor.GREEN + "Parkour [" + ID + "] configurado com sucesso!");
            p.getInventory().clear();
            e.setCancelled(true);

            spawnHolograms();
        } else if (item.getType() == Material.BARRIER && item.hasItemMeta()) {
            if (local == null || ID == null) return;
            p.getInventory().clear();
            e.setCancelled(true);

            p.sendMessage(ChatColor.RED + "Configuração cancelada!");
            deleteParkour(p);

        } else if(item.getType() == Material.COMPASS && item.hasItemMeta()) {
            if (!inParkour(p.getUniqueId())) return;
            p.teleport(getCheckpoint(p));
        } else if (item.getType() == Material.REDSTONE_TORCH_ON && item.hasItemMeta()) {
            if (!inParkour(p.getUniqueId())) return;
            Location start = resetParkour(p);
            p.teleport(start);
            p.sendMessage(ChatColor.GREEN + "Parkour reiniciado!");
        } else if (item.getType() == Material.BOOK && item.hasItemMeta()) {
            if (!inParkour(p.getUniqueId())) return;
            endParkour(p, false);
            p.getInventory().clear();
            p.sendMessage(ChatColor.RED + "Parkour cancelado!");
        }
    }
}
