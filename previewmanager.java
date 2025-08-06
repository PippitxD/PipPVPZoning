package PippitXD.pvpzone;

import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class PreviewManager {

    private final PvPZonePlugin plugin;
    private final Map<Player, Preview> activePreviews = new HashMap<>();
    // Keep last sent preview so we can clear obsolete blocks
    private final Map<Player, Preview> lastSent = new HashMap<>();
    private final BlockData woolData;

    public PreviewManager(PvPZonePlugin plugin) {
        this.plugin = plugin;
        this.woolData = Material.RED_WOOL.createBlockData();
        // Optional: Periodically refresh previews if you want auto-update for moving players
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : activePreviews.keySet()) {
                    refreshPreview(player);
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // every second
    }

    public void startPreview(Player player, int size) {
        Preview newPreview = new Preview(player.getLocation().getBlock().getLocation(), size);
        activePreviews.put(player, newPreview);
        sendPreview(player, newPreview);
        player.sendMessage("§aPreviewing PvP zone of size " + size + "x" + size + ". Left click to confirm.");
    }

    private void sendPreview(Player player, Preview preview) {
        // Clear previous preview (restore)
        clearSent(player);

        // Send fake blocks
        for (Location loc : preview.getBorderLocations()) {
            player.sendBlockChange(loc, woolData);
        }
        lastSent.put(player, preview);
    }

    // Called periodically or e.g., if player moves and you want to update preview center
    public void refreshPreview(Player player) {
        Preview current = activePreviews.get(player);
        if (current == null) return;
        // Optionally re-center if player moved significantly
        Location newCenter = player.getLocation().getBlock().getLocation();
        if (!newCenter.equals(current.getCenter())) {
            // recreate preview around new position
            Preview updated = new Preview(newCenter, current.getSize());
            activePreviews.put(player, updated);
            sendPreview(player, updated);
        }
    }

    private void clearSent(Player player) {
        Preview previous = lastSent.remove(player);
        if (previous != null) {
            // Restore the real blocks for those locations
            for (Location loc : previous.getBorderLocations()) {
                // Sending the real block back to the player
                player.sendBlockChange(loc, loc.getBlock().getBlockData());
            }
        }
    }

    public void clearPreview(Player player) {
        activePreviews.remove(player);
        clearSent(player);
        player.sendMessage("§cPvP preview cleared.");
    }

    public Preview getPreview(Player player) {
        return activePreviews.get(player);
    }

    public void finalizePreview(Player player) {
        Preview preview = activePreviews.remove(player);
        clearSent(player); // clear the client-side fake preview
        if (preview == null) return;

        // Place real red wool border in world
        for (Location loc : preview.getBorderLocations()) {
            loc.getBlock().setType(Material.RED_WOOL, false);
        }
    }
}
