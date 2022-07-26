package de.webcode.system.commands;

import de.webcode.system.ServerSystem;
import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.PlayerManagementService;
import de.webcode.system.utils.inventory.anvilgui.AnvilGUI;
import de.webcode.system.utils.inventory.menu.ReportListMenu;
import de.webcode.system.utils.inventory.menuutility.PlayerMenuUtility;
import de.webcode.system.utils.reporting.PlayerReport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ReportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_player"));
            return false;
        }

        Player player = (Player) sender;

        if(args.length < 1){
            player.sendMessage(LanguageService.getMessageWithPrefix("command.report.usage"));
            return false;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            if (!player.hasPermission("system.report.list")) {
                player.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_permission"));
                return false;
            }

            new ReportListMenu(new PlayerMenuUtility(player)).open();

            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(LanguageService.getMessageWithPrefix("error.command.target_player_not_found").replace("{player}", args[0]));
            return false;
        }

        if(args.length == 1){
            new AnvilGUI.Builder()
                    .onComplete((p, text) -> {
                        PlayerReport report = new PlayerReport(target.getName(), text, player.getName());
                        PlayerManagementService.getService().reportPlayer(target, report);

                        player.sendMessage(LanguageService.getMessageWithPrefix("command.report.success").replace("{player}", target.getName()).replace("{reason}", text));

                        return AnvilGUI.Response.close();
                    })
                    .itemLeft(new ItemStack(Material.PAPER))
                    .text("Grund")
                    .title("§8Report > §e" + target.getName())
                    .plugin(ServerSystem.getInstance())
                    .open(player);

            return true;
        }



        StringBuilder sb = new StringBuilder();
        String[] clone = args.clone();
        clone[0] = "";

        for(String s : clone){
            sb.append(s + " ");
        }

        String reason = ChatColor.translateAlternateColorCodes('&', sb.toString());
        PlayerReport report = new PlayerReport(target.getName(), reason, player.getName());
        PlayerManagementService.getService().reportPlayer(target, report);

        player.sendMessage(LanguageService.getMessageWithPrefix("command.report.success").replace("{player}", target.getName()).replace("{reason}", reason));
        return true;
    }
}
