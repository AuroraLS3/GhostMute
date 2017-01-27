package com.djrapitops.ghostmute;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class ReloadCommand implements CommandExecutor {

    private final GhostMute plugin;

    public ReloadCommand(GhostMute replacer) {
        this.plugin = replacer;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmnd, String string, String[] args) {
        boolean console = !(sender instanceof Player);
        if (!console) {
            if (!sender.hasPermission("GhostMute.reload")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission for this command!");
                return true;
            }
        }
        plugin.reloadConfig();
        plugin.refreshMuted();
        sender.sendMessage(ChatColor.GREEN+"[GhostMute] Plugin config reloaded");
        return true;
    }
}
