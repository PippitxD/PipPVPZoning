package PippitXD.pvpzone;

import org.bukkit.Location;

public class Zone {
    private final String id;
    private final Location corner1;
    private final Location corner2;

    public Zone(String id, Location corner1, Location corner2) {
        this.id = id;
        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    public boolean contains(Location loc) {
        if (!loc.getWorld().equals(corner1.getWorld())) return false;
        double minX = Math.min(corner1.getX(), corner2.getX());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());
        double y = loc.getY();
        return loc.getX() >= minX && loc.getX() <= maxX
                && loc.getZ() >= minZ && loc.getZ() <= maxZ;
    }

    public Location getCorner1() { return corner1; }
    public Location getCorner2() { return corner2; }
    public String getId() { return id; }
}
