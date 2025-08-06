package PippitXD.pvpzone;

import org.bukkit.plugin.java.JavaPlugin;

public class PvPZonePlugin extends JavaPlugin {

    private ZoneManager zoneManager;
    private PreviewManager previewManager;

    @Override
    public void onEnable() {
        
        saveDefaultConfig();
        zoneManager = new ZoneManager(this);
        previewManager = new PreviewManager(this);

     getCommand("cancelpvp").setExecutor(new CancelPvPCommand(this));
        getCommand("setpvp").setExecutor(new SetPvPCommand(this));
        getServer().getPluginManager().registerEvents(new ConfirmListener(this), this);
        getServer().getPluginManager().registerEvents(new PvPListener(this), this);

        getLogger().info("PvPZoneSelector enabled.");
    }

    public ZoneManager getZoneManager() {
        return zoneManager;
    }

    public PreviewManager getPreviewManager() {
        return previewManager;
    }
}
