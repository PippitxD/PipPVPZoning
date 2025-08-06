package PippitXD.pvpzone;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ZoneManager {

    private final PvPZonePlugin plugin;
    private final File zoneFile;
    private FileConfiguration zonesConfig;
    private final Map<String, Zone> zones = new HashMap<>();

    public ZoneManager(PvPZonePlugin plugin) {
        this.plugin = plugin;
        this.zoneFile = new File(plugin.getDataFolder(), "zones.yml");
        if (!zoneFile.exists()) {
            plugin.saveResource("zones.yml", false);
        }
        reload();
    }

    public void reload() {
        zonesConfig = YamlConfiguration.loadConfiguration(zoneFile);
        zones.clear();
        if (zonesConfig.contains("zones")) {
            for (String id : zonesConfig.getConfigurationSection("zones").getKeys(false)) {
                String path = "zones." + id + ".";
                String world = zonesConfig.getString(path + "world");
                double x1 = zonesConfig.getDouble(path + "corner1.x");
                double y1 = zonesConfig.getDouble(path + "corner1.y");
                double z1 = zonesConfig.getDouble(path + "corner1.z");
                double x2 = zonesConfig.getDouble(path + "corner2.x");
                double y2 = zonesConfig.getDouble(path + "corner2.y");
                double z2 = zonesConfig.getDouble(path + "corner2.z");
                Location c1 = new Location(Bukkit.getWorld(world), x1, y1, z1);
                Location c2 = new Location(Bukkit.getWorld(world), x2, y2, z2);
                zones.put(id, new Zone(id, c1, c2));
            }
        }
    }

    public void saveZone(Zone zone) {
        String id = zone.getId();
        String base = "zones." + id + ".";
        zonesConfig.set(base + "world", zone.getCorner1().getWorld().getName());
        zonesConfig.set(base + "corner1.x", zone.getCorner1().getX());
        zonesConfig.set(base + "corner1.y", zone.getCorner1().getY());
        zonesConfig.set(base + "corner1.z", zone.getCorner1().getZ());
        zonesConfig.set(base + "corner2.x", zone.getCorner2().getX());
        zonesConfig.set(base + "corner2.y", zone.getCorner2().getY());
        zonesConfig.set(base + "corner2.z", zone.getCorner2().getZ());
        try {
            zonesConfig.save(zoneFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save zone: " + e.getMessage());
        }
        zones.put(id, zone);
    }

    public Collection<Zone> getZones() {
        return zones.values();
    }

    public Optional<Zone> findContainingZone(Location loc) {
        return zones.values().stream().filter(z -> z.contains(loc)).findFirst();
    }
}
