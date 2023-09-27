package org.kauazs;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.kauazs.commands.CreateSetup;
import org.kauazs.listeners.*;

import static org.kauazs.holograms.HologramManager.removeHolograms;
import static org.kauazs.holograms.HologramManager.spawnHolograms;

public final class Parkour_zs extends JavaPlugin {
    FileConfiguration fileConfiguration = getConfig();

    @Getter @Setter
    private static Parkour_zs instance;

    @Override
    public void onEnable() {
        setInstance(this);
       getCommand("create-parkour").setExecutor(new CreateSetup());
       getServer().getPluginManager().registerEvents(new ClickEvent(), this);
       getServer().getPluginManager().registerEvents(new ArmorStandManipulate(), this);
       getServer().getPluginManager().registerEvents(new onPlayerMove(), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerDisconnect(), this);
        getServer().getPluginManager().registerEvents(new PlayerDrop(), this);
       spawnHolograms();

    }

    @Override
    public void onDisable() {
        removeHolograms();
    }
}
