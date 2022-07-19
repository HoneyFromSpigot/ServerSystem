package de.webcode.system.commands;

import de.webcode.system.ServerSystem;
import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.PlayerManagementService;
import de.webcode.system.utils.file.FileService;
import de.webcode.system.utils.reporting.Warning;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            Player target = Bukkit.getPlayer(args[1]);

            if(target == null){
                sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.target_player_not_found").replace("{player}", args[1]));
                return false;
            }

            PlayerManagementService.getService().resetWarnings(target);

            sender.sendMessage(LanguageService.getMessageWithPrefix("command.warn.reset_success").replace("{player}", target.getName()));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == sender){
            sender.sendMessage(LanguageService.getMessageWithPrefix("command.warn.self_warn"));
            return false;
        }

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

        String reason = ChatColor.translateAlternateColorCodes('&', sb.toString());
        int warningcount = PlayerManagementService.getService().getWarningCount(target);

        if(warningcount == 3){
            PlayerManagementService.getService().banPlayer(target, reason);
            sender.sendMessage(LanguageService.getMessageWithPrefix("command.warn.player_banned"));
            FileService fileService = ServerSystem.getInstance().getFileService();
            YamlConfiguration playerData = fileService.getPlayerData();
            String pName = target.getName();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            playerData.set(pName + ".warning.banned.mod", ((Player) sender).getName());
            playerData.set(pName + ".warning.banned.date", dateFormat.format(date));
            playerData.set(pName + ".warning.banned.reason", reason);
            fileService.saveFiles();
            return true;
        }

        Warning warning = new Warning(target, ((Player) sender).getName(), reason);
        PlayerManagementService.getService().addWarning(target, warning);

        String message = LanguageService.getMessageWithPrefix("command.warn.warn_player_success_sender").replace("{player}", target.getName()).replace("{i}", "" + (warningcount + 1)).replace("{reason}", reason);
        sender.sendMessage(message);
        target.kickPlayer(
                "§8----------------------------------------\n" +
                        "§c§lVerwarnung\n\n" +
                        reason +
                        "\n\n§7Dies ist deine " + (warningcount + 1) + " Verwarnung. Nach 3 Verwarnungen wirst du gebannt." +
                        "\n§8----------------------------------------"
        );

        return true;
    }
}
