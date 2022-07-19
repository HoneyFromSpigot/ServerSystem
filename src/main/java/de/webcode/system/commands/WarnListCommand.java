package de.webcode.system.commands;

import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.inventory.menu.PlayerWarningListMenu;
import de.webcode.system.utils.inventory.menuutility.TargetPlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarnListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_player"));
            return false;
        }

        if(args.length < 1){
            sender.sendMessage(LanguageService.getMessageWithPrefix("command.warns.usage"));
            return false;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if(target == null){
            player.sendMessage(LanguageService.getMessageWithPrefix("error.command.target_player_not_found").replace("{player}", args[0]));
            return false;
        }
        new PlayerWarningListMenu(new TargetPlayerMenuUtility(player, target)).open();

        return false;
    }
}
