package de.webcode.system.commands;

import de.webcode.system.ServerSystem;
import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.PlayerManagementService;
import de.webcode.system.utils.inventory.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SKickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("system.skick")){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_permission"));
            return false;
        }

        if(args.length < 1){
            sender.sendMessage(LanguageService.getMessageWithPrefix("command.skick.usage"));
            return false;
        }

        if(args.length == 1){
            if (!(sender instanceof Player)) {
                sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_player"));
                return false;
            }

            Player p = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);

            if(target == null){
                p.sendMessage(LanguageService.getMessageWithPrefix("error.command.target_player_not_found"));
                return false;
            }

            new AnvilGUI.Builder()
                    .preventClose()
                    .text("Grund")
                    .title(target.getName() + " kicken")
                    .plugin(ServerSystem.getInstance())
                    .itemLeft(new ItemStack(Material.PAPER))
                    .onComplete((player, s) -> {
                        if(s == null || s.length() == 0){
                            return AnvilGUI.Response.text("Bitte gebe einen gültigen Grund an");
                        }

                        String reason = ChatColor.translateAlternateColorCodes('&', s);
                        PlayerManagementService.getService().kickPlayer(target, reason);
                        player.sendMessage(LanguageService.getMessageWithPrefix("command.skick.player_kick_success"));

                        return AnvilGUI.Response.close();
                    }).open(p);

            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.target_player_not_found"));
            return false;
        }

        StringBuilder sb = new StringBuilder();
        String[] clone = args.clone();
        clone[0] = "";

        for(String s : clone){
            sb.append(s + " ");
        }

        String reason = ChatColor.translateAlternateColorCodes('&', sb.toString());
        PlayerManagementService.getService().kickPlayer(target, reason);
        sender.sendMessage(LanguageService.getMessageWithPrefix("command.skick.player_kick_success"));

        return false;
    }
}
