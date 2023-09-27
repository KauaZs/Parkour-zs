package org.kauazs.holograms;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.kauazs.Parkour_zs;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.kauazs.utils.LocationGross.getFromLocation;

public class HologramManager {
    static File file = new File(Parkour_zs.getInstance().getDataFolder(), "config.yml");
    static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    static List<ArmorStand> hologram = new ArrayList<>();
    //static HolographicDisplaysAPI api = HolographicDisplaysAPI.get(Parkour_zs.getInstance());

    public static void createHologram(String display, Location location) {
        ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND); //Spawn the ArmorStand

        as.setGravity(false);
        as.setCanPickupItems(false);
        as.setCustomName(display);
        as.setCustomNameVisible(true);
        as.setVisible(false);
        as.setSilent(true);
        as.setMarker(true);
        hologram.add(as);

    }

    public static void spawnHolograms() {
        ConfigurationSection configurationSection = config.getConfigurationSection("config.parkours");

        if(configurationSection == null) return;

        for(String parkourKey : configurationSection.getKeys(false)) {
            ConfigurationSection section = configurationSection.getConfigurationSection(parkourKey);

            if (section != null && section.contains("start")) {
                String local = section.getString("start");
                String worldData = section.getString("world");

                new WorldCreator(worldData).createWorld();
                World world = Bukkit.getWorld(worldData);

                Location location = getFromLocation(world, local).add(0, 2, 0);

                createHologram(ChatColor.GREEN + "Parkour", location);
            }
        }
    }
    public static void removeHolograms() {
        for (ArmorStand hg : hologram) {
            hg.remove();
        }
    }
}
