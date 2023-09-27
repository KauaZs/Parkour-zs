package org.kauazs.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.kauazs.Parkour_zs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static org.kauazs.utils.LocationGross.getFromLocation;
import static org.kauazs.utils.LocationGross.getLocation;
import static org.kauazs.utils.TimeFormat.formatTime;

public class ParkourManager {
    private static HashMap<String, String> setups = new HashMap();
    static HashMap<UUID, LocationData> parkour = new HashMap();

    static File file = new File(Parkour_zs.getInstance().getDataFolder(), "config.yml");
    static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    public static void bootParkour(Player p, String ID) {
        setups.put(p.getName(), ID);
    }

    public static String getParkour(Player p) {
        return setups.get(p.getName());
    }

    public static void deleteParkour(Player p) throws IOException {
        setups.remove(p.getName());
        config.set("config.parkous." + getParkour(p), null);
        config.save(file);
    }

    public static boolean startParkour(Player p, String ID) {
        if (parkour.containsKey(p.getUniqueId())) return false;

        ConfigurationSection configurationSection = config.getConfigurationSection("config.parkours."+ID);
        String local = configurationSection.getString("start");
        World world = Bukkit.getWorld(configurationSection.getString("world"));
        Location location = getFromLocation(world, local);
        LocationData locationData = new LocationData(location, ID, System.currentTimeMillis());
        parkour.put(p.getUniqueId(), locationData);
        return true;
    }

    public static Location getCheckpoint(Player p) {
        return parkour.get(p.getUniqueId()).getLocation();
    }

    public static long getStartTime(Player p) {
        return parkour.get(p.getUniqueId()).getTime();
    }


    public static Location resetParkour(Player p) {
        String ID = parkour.get(p.getUniqueId()).getID();
        ConfigurationSection configurationSection = config.getConfigurationSection("config.parkours."+ID);
        String local = configurationSection.getString("start");
        World world = Bukkit.getWorld(configurationSection.getString("world"));
        Location location = getFromLocation(world, local);

        LocationData locationData = new LocationData(location, ID, System.currentTimeMillis());
        parkour.put(p.getUniqueId(), locationData);

        return location;
    }

    public static Boolean setCheckpoint(Player p, Location location) {
        String ID = parkour.get(p.getUniqueId()).getID();
        Location last = parkour.get(p.getUniqueId()).getLocation();
        long time = parkour.get(p.getUniqueId()).getTime();
        if(last.equals(location)) return false;

        LocationData locationData = new LocationData(location, ID, time);
        parkour.put(p.getUniqueId(), locationData);
        return true;
    }

    public static Boolean endParkour(Player p, Boolean has) {
        if(parkour.get(p.getUniqueId()) == null) return false;
        if(has) p.sendMessage(ChatColor.GREEN + "Parabéns você finalizou o parkour! Em " + formatTime(getStartTime(p), System.currentTimeMillis()));
        parkour.remove(p.getUniqueId());
        return true;
    }

    public static Boolean inParkour(UUID p) {
        if(parkour.get(p) == null) return false;
        return true;
    }

}
