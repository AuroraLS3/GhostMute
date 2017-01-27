package com.djrapitops.ghostmute;

import com.djrapitops.ghostmute.utils.UUIDFetcher;
import java.util.List;
import java.util.UUID;
import static org.bukkit.Bukkit.getOfflinePlayer;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static org.bukkit.Bukkit.getOfflinePlayer;

/**
 *
 * @author Rsl1122
 */
public class GMuteCommand implements CommandExecutor {

    private GhostMute plugin;

    public GMuteCommand(GhostMute plugin) {
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
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "[GhostMute] One playername required!");
            return true;
        }
        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "[GhostMute] One playername required!");
            return true;
        }
        String playername = args[0];
        UUID uuid;
        try {
            uuid = UUIDFetcher.getUUIDOf(playername);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "[GhostMute] UUID Fetch failed!");
            return false;
        }
        if (uuid == null) {
            sender.sendMessage(ChatColor.RED + "[GhostMute] Player doesn't exist! (Case sensitive)");
            return true;
        }
        OfflinePlayer p = getOfflinePlayer(uuid);
        if (p.isOp()) {
            sender.sendMessage(ChatColor.RED + "[GhostMute] Operators can not be muted!");
            return true;
        }
        List<String> muted = plugin.getConfig().getStringList("muted");
        
        if (muted.contains(uuid.toString())) {
            muted.remove(uuid.toString());
            sender.sendMessage(ChatColor.GREEN + "[GhostMute] Player "+playername+" was unmuted!");
        } else {
            muted.add(uuid.toString());
            sender.sendMessage(ChatColor.GREEN + "[GhostMute] Player "+playername+" was muted!");
        }
        plugin.getConfig().set("muted", muted);
        plugin.refreshMuted();
        return true;
    }
}
