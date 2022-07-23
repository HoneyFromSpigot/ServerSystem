package de.webcode.system.commands;

import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.chat.ChatMessage;
import de.webcode.system.utils.chat.ChatService;
import de.webcode.system.utils.chat.ChatType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender.hasPermission("system.broadcast"))){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_permission"));
            return false;
        }

        if(args.length < 1){
            sender.sendMessage(LanguageService.getMessageWithPrefix("command.broadcast.usage"));
            return false;
        }

        StringBuilder sb = new StringBuilder();
        String[] clone = args.clone();

        for(String s : clone){
            sb.append(s + " ");
        }

        String message = ChatColor.translateAlternateColorCodes('&', sb.toString());
        ChatMessage chatMessage = new ChatMessage(message, null, ChatType.BROADCAST);
        ChatService.getService().handleMessage(chatMessage);

        return true;
    }
}
