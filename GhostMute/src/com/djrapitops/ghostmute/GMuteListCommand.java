package com.djrapitops.ghostmute;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.bukkit.Bukkit.getOfflinePlayer;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Rsl1122
 */
public class GMuteListCommand implements CommandExecutor {

    private GhostMute plugin;

    public GMuteListCommand(GhostMute plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmnd, String string, String[] args) {
        boolean console = !(sender instanceof Player);
        if (!console) {
            if (!sender.hasPermission("GhostMute.mute")) {
                sender.sendMessage(ChatColor.RED + "[GhostMute] You do not have permission for this command!");
                return true;
            }
        }
        
        List<String> muteUUIDs = plugin.getConfig().getStringList("muted");
        List<String> muted = new ArrayList<>();
        for (String mutedUUID : muteUUIDs) {
            UUID uuid;
            try {
                uuid = UUID.fromString(mutedUUID);
            } catch (Exception e) {
                continue;
            }
            OfflinePlayer p = getOfflinePlayer(uuid);
            muted.add(p.getName());
        }
        if (!muted.isEmpty()) {
            sender.sendMessage("[GhostMute] Muted Players: "+muted.toString());
        } else {
            sender.sendMessage("[GhostMute] No players are gmuted.");
        }
        return true;
    }
}
