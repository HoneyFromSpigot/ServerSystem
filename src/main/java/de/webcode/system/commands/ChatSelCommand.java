package de.webcode.system.commands;

import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.inventory.menu.ChatSelectionMenu;
import de.webcode.system.utils.inventory.menuutility.PlayerMenuUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatSelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_player"));
            return false;
        }

        Player player = (Player) sender;
        new ChatSelectionMenu(new PlayerMenuUtility(player)).open();
        return false;
    }
}
