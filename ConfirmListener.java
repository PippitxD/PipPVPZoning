package PippitXD.pvpzone;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.Location;

import java.util.UUID;

public class ConfirmListener implements Listener {

    private final PvPZonePlugin plugin;

    public ConfirmListener(PvPZonePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        Player player = event.getPlayer();
        Preview preview = plugin.getPreviewManager().getPreview(player);
        if (preview == null) return;

        // Confirm zone
        int size = preview.getSize();
        Location center = preview.getCenter();
        int half = size / 2;

        Location c1 = new Location(center.getWorld(), center.getX() - half, center.getY(), center.getZ() - half);
        Location c2 = new Location(center.getWorld(), center.getX() + half, center.getY(), center.getZ() + half);
        String id = UUID.randomUUID().toString();

        Zone zone = new Zone(id, c1, c2);
        plugin.getZoneManager().saveZone(zone);
        plugin.getPreviewManager().finalizePreview(player); // places real wool border
        player.sendMessage("§aPvP zone confirmed and saved.");
    }
}
