package PippitXD.pvpzone;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CancelPvPCommand implements CommandExecutor {

    private final PvPZonePlugin plugin;

    public CancelPvPCommand(PvPZonePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this.");
            return true;
        }
        if (!player.hasPermission("pvpzone.cancel")) {
            player.sendMessage("§cYou don't have permission to cancel PvP previews.");
            return true;
        }

        if (plugin.getPreviewManager().getPreview(player) != null) {
            plugin.getPreviewManager().clearPreview(player);
            player.sendMessage("§cPvP preview cancelled.");
        } else {
            player.sendMessage("§7You don't have an active preview.");
        }
        return true;
    }
}
