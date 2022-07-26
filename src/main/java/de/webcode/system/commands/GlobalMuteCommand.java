package de.webcode.system.commands;

import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.chat.ChatService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GlobalMuteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("system.globalmute")) {
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_permission"));
            return false;
        }

        if(args.length != 1){
            sender.sendMessage(LanguageService.getMessageWithPrefix("command.globalmute.usage"));
            return false;
        }

        if (args[0].equalsIgnoreCase("an")) {
            ChatService.getService().setGlobalMute(true);
            sender.sendMessage("§aGlobal Mute wurde aktiviert!");
            return true;
        } else if (args[0].equalsIgnoreCase("aus")) {
            ChatService.getService().setGlobalMute(false);
            sender.sendMessage("§aGlobal Mute wurde deaktiviert!");
            return false;
        }

        sender.sendMessage(LanguageService.getMessageWithPrefix("command.globalmute.usage"));
        return true;
    }
}
