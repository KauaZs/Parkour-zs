package org.kauazs.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationGross {
    public static String getLocation(Location location) {
        return location.getX()+","+location.getY()+","+location.getZ()+","+location.getPitch();
    }
    public static Location getFromLocation(World world, String local) {
        double x = Double.parseDouble(local.split(",")[0]);
        double y = Double.parseDouble(local.split(",")[1]);
        double z = Double.parseDouble(local.split(",")[2]);
        return new Location(world, x, y, z);
    }

}
