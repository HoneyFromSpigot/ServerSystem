package de.webcode.system.commands;

import de.webcode.system.ServerSystem;
import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.PlayerManagementService;
import de.webcode.system.utils.reporting.Warning;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender.hasPermission("system.warn"))){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_permission"));
            return false;
        }

        if(args.length < 2){
            sender.sendMessage(LanguageService.getMessageWithPrefix("command.warn.usage"));
            return false;
        }

        if(args[0].equalsIgnoreCase("reset")){
            //TODO: Reset Warnings
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.target_player_not_found").replace("{player}", args[0]));
            return false;
        }

        if(!(sender instanceof Player)){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_player"));
            return false;
        }

        StringBuilder sb = new StringBuilder();
        String[] clone = args.clone();
        clone[0] = "";

        for(String s : clone){
            sb.append(s + " ");
        }

        String reason = sb.toString();
        int warningcount = PlayerManagementService.getService().getWarningCount(target);

        if(warningcount == 3){
            //TODO: Ban Player
            return false;
        }

        Warning warning = new Warning(target, (Player) sender, reason);
        PlayerManagementService.getService().addWarning(target, warning);

        String message = LanguageService.getMessageWithPrefix("command.warn.warn_player_success_sender").replace("{player}", target.getName()).replace("{i}", "" + warningcount + 1).replace("{reason}", reason);
        String playerMessage = LanguageService.getMessageWithPrefix("command.warn.warn_player_success_player").replace("{reason}", reason).replace("{i}", 1 + warningcount + "");
        sender.sendMessage(message);
        target.sendMessage(playerMessage);

        return true;
    }
}
