package de.webcode.system.commands;

import de.webcode.system.utils.LanguageService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("system.clearchat")){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_permission"));
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_player"));
            return false;
        }

        Player player = (Player) sender;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.isOp() || !p.hasPermission("system.clearchat")) {
                for (int i = 0; i < 150; i++) {
                    p.sendMessage("");
                }
            }
        }
        player.sendMessage(LanguageService.getMessageWithPrefix("command.clearchat.success"));
        return true;
    }
}
