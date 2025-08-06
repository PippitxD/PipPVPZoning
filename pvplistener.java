package PippitXD.pvpzone;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvPListener implements Listener {

    private final PvPZonePlugin plugin;

    public PvPListener(PvPZonePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;
        if (!(event.getDamager() instanceof Player attacker)) return;

        boolean inZoneVictim = plugin.getZoneManager().findContainingZone(victim.getLocation()).isPresent();
        boolean inZoneAttacker = plugin.getZoneManager().findContainingZone(attacker.getLocation()).isPresent();

        // Only allow PvP if both are inside *some* confirmed PvP zone (could be same or different)
        if (!(inZoneVictim && inZoneAttacker)) {
            event.setCancelled(true);
        }
    }
}
