package org.kauazs.managers;

import org.bukkit.Location;

public class LocationData {
    private Location location;
    private String ID;
    private long startTime;
    public LocationData(Location location, String ID, long startTime){
        this.location = location;
        this.ID = ID;
        this.startTime = startTime;
    }

    public Location getLocation() {
        return location;
    }

    public String getID() {
        return ID;
    }

    public long getTime() {
        return startTime;
    }
}
