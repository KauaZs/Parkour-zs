package org.kauazs.listeners;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;
import org.kauazs.Parkour_zs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.kauazs.managers.ParkourManager.*;
import static org.kauazs.utils.ItemManager.ItemCreate;
import static org.kauazs.utils.ItemManager.SetItem;
import static org.kauazs.utils.LocationGross.getFromLocation;
import static org.kauazs.utils.TimeFormat.formatTime;

public class onPlayerMove implements Listener {
    File file = new File(Parkour_zs.getInstance().getDataFolder(), "config.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    @EventHandler
    public void PlayerMove(PlayerMoveEvent e) {

        ConfigurationSection configurationSection = config.getConfigurationSection("config.parkours");

        if (configurationSection == null) return;

        for(String value : configurationSection.getKeys(false)) {
            ConfigurationSection section = configurationSection.getConfigurationSection(value);

            if(section != null && section.contains("start")) {
                String worldName = section.getString("world");
                new WorldCreator(worldName).createWorld();
                World world = Bukkit.getWorld(worldName);

                Location start = getFromLocation(world, section.getString("start"));
                Location at = e.getFrom().getBlock().getLocation();

                if(start.getX() == at.getX() && start.getY() == at.getY() && start.getZ() == at.getZ()) {
                    Player p = e.getPlayer();
                    String ID = section.getName();

                    if (startParkour(p, ID)) {

                        p.sendMessage(ChatColor.GREEN + "Pakour iniciado!");
                        p.getInventory().clear();
                        p.setGameMode(GameMode.ADVENTURE);
                        p.playSound(at, Sound.ENTITY_GENERIC_BURN, SoundCategory.PLAYERS, 100, 100);
                        ItemStack compass = ItemCreate(Material.COMPASS, ChatColor.BLUE + "Voltar para o ultimo checkpoint");
                        ItemStack toc = ItemCreate(Material.REDSTONE_TORCH_ON, ChatColor.GREEN + "Reiniciar Parkour");
                        ItemStack barrier = ItemCreate(Material.BOOK, ChatColor.RED + "Desistir");

                        SetItem(p, 0, compass);
                        SetItem(p, 4, toc);
                        SetItem(p, 8, barrier);
                    }
                    continue;
                }


                Location end = getFromLocation(world, section.getString("end")).add(0, 1, 0);
                Player p = e.getPlayer();

                if(end.getX() == at.getX() && end.getY() == at.getY() && end.getZ() == at.getZ()) {
                    if (endParkour(p, true)) {
                        int diameter = 10;
                        for (int i = 0; i < 5; i++) {
                            Location newLocation = end.add(new Vector(Math.random() - 0.5, 0, Math.random() - 0.5).multiply(diameter));
                            world.spawnEntity(newLocation, EntityType.FIREWORK);
                        }
                        p.getInventory().clear();
                    }
                    continue;
                }

                if (section != null && section.contains("checkpoints")) {
                    if (!inParkour(e.getPlayer().getUniqueId())) return;
                    List<String> checkpoints = (List<String>) section.getList("checkpoints");

                    for (String atP : checkpoints) {

                        Location location = getFromLocation(world, atP).add(0, 1, 0);
                            if (location.getX() == at.getX() && location.getY() == at.getY() && location.getZ() == at.getZ()) {

                                if (setCheckpoint(p, at)) {
                                    p.sendMessage(ChatColor.GREEN + "Você pegou o checkpoint!");
                                    p.playSound(at, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 100, 100);
                                }
                        }
                    }
                }
            }
        }
    }
}
