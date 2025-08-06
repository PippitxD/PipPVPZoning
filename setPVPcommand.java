package PippitXD.pvpzone;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetPvPCommand implements CommandExecutor {

    private final PvPZonePlugin plugin;

    public SetPvPCommand(PvPZonePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this.");
            return true;
        }
        if (!player.hasPermission("pvpzone.create")) {
            player.sendMessage("§cYou don't have permission to create PvP zones.");
            return true;
        }
        if (args.length != 1) {
            player.sendMessage("Usage: /setpvp <size> (10–20)");
            return true;
        }
        int size;
        try {
            size = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage("Size must be a number.");
            return true;
        }
        if (size < 10 || size > 20) {
            player.sendMessage("Size must be between 10 and 20.");
            return true;
        }
        plugin.getPreviewManager().startPreview(player, size);
        return true;
    }
}
