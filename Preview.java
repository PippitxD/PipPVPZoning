package PippitXD.pvpzone;

import org.bukkit.Location;
import org.bukkit.block.Block;
import java.util.Set;
import java.util.HashSet;

public class Preview {
    private final Location center;
    private final int size;
    private final Set<Location> borderLocations = new HashSet<>();

    public Preview(Location center, int size) {
        this.center = center;
        this.size = size;
        calculateBorder();
    }

    private void calculateBorder() {
        int half = size / 2;
        int y = center.getBlockY();
        for (int dx = -half; dx <= half; dx++) {
            for (int dz = -half; dz <= half; dz++) {
                boolean border = dx == -half || dx == half || dz == -half || dz == half;
                if (!border) continue;
                Location loc = new Location(center.getWorld(), center.getX() + dx, y, center.getZ() + dz);
                borderLocations.add(loc);
            }
        }
    }

    public Set<Location> getBorderLocations() {
        return borderLocations;
    }

    public Location getCenter() {
        return center;
    }

    public int getSize() {
        return size;
    }
}
