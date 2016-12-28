package com.djrapitops.ghostmute.listeners;

import com.djrapitops.ghostmute.GhostMute;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GhostMuteChatListener implements Listener {

    private final GhostMute plugin;

    public GhostMuteChatListener(GhostMute plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (p.hasPermission("GhostMute.bypass")) {
            return;
        }
        if (event.isCancelled()) {
            String format = event.getFormat();
            String msg = event.getMessage();
            String newMsg = format.replace("%1$s", p.getDisplayName()).replace("%2$s", msg);
            p.sendMessage(newMsg);
        }
    }
}
