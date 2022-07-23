package de.webcode.system.commands;

import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.chat.ChatService;
import de.webcode.system.utils.chat.ChatType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeamChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender.hasPermission("system.chat.team"))){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_permission"));
            return false;
        }

        if(!(sender instanceof Player)){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_player"));
            return false;
        }

        Player player = (Player) sender;

        if(args.length != 1){
            player.sendMessage(LanguageService.getMessageWithPrefix("command.teamchat.usage"));
            return false;
        }

        if(args[0].equalsIgnoreCase("an")){
            ChatService.getService().setPlayerChatType(player, ChatType.SERVER_TEAM);
            player.sendMessage(LanguageService.getMessageWithPrefix("command.teamchat.toggle_on"));
            return true;
        }else if(args[0].equalsIgnoreCase("aus")){
            ChatService.getService().setPlayerChatType(player, ChatType.STANDART);
            player.sendMessage(LanguageService.getMessageWithPrefix("command.teamchat.toggle_off"));
            return true;
        }

        player.sendMessage(LanguageService.getMessageWithPrefix("command.teamchat.usage"));
        return false;
    }
}
