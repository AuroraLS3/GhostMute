package com.djrapitops.ghostmute;

import com.djrapitops.ghostmute.listeners.GhostMuteChatListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.bukkit.plugin.java.JavaPlugin;

public class GhostMute extends JavaPlugin {

    private GhostMuteChatListener clistener;

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();

        getConfig().options().copyDefaults(true);

        getConfig().options().header("GhostMute Config\n"
                + "debug - Errors are saved in Errors.txt when they occur\n"
        );

        saveConfig();        
        
        getCommand("ghostreload").setExecutor(new ReloadCommand(this));        
        clistener = new GhostMuteChatListener(this);
        
        log("GhostMute Enabled.");
    }

    @Override
    public void onDisable() {
        log("GhostMute Disabled.");
    }

    public GhostMuteChatListener getClistener() {
        return clistener;
    }    
    
    public void log(String message) {
        getLogger().info(message);
    }

    public void logError(String message) {
        getLogger().severe(message);
    }

    public void logToFile(String message) {
        if (getConfig().getBoolean("debug")) {
            File folder = getDataFolder();
            if (!folder.exists()) {
                folder.mkdir();
            }
            File log = new File(getDataFolder(), "Errors.txt");
            try {
                if (!log.exists()) {
                    log.createNewFile();
                }
                FileWriter fw = new FileWriter(log, true);
                try (PrintWriter pw = new PrintWriter(fw)) {
                    pw.println(message + "\n");
                    pw.flush();
                }
            } catch (IOException e) {
                logError("Failed to create Errors.txt file");
            }
        }
    }

}
