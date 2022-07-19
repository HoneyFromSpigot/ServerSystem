package de.webcode.system.commands;

import de.webcode.system.utils.LanguageService;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class GamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!sender.hasPermission("system.gamemode")){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_permission"));
            return false;
        }

        if(!(sender instanceof Player)){
            sender.sendMessage(LanguageService.getMessageWithPrefix("error.command.no_player"));
            return false;
        }

        Player player = (Player) sender;

        switch(label){
            case "gmc" -> setGameMode(player, GameMode.CREATIVE);
            case "gma" -> setGameMode(player, GameMode.ADVENTURE);
            case "gms" -> setGameMode(player, GameMode.SURVIVAL);
            case "gmspec" -> setGameMode(player, GameMode.SPECTATOR);
        }

        if(Arrays.asList("gmc", "gma", "gms", "gmspec").contains(label)) return true;

        if(args.length != 1){
            player.sendMessage(LanguageService.getMessageWithPrefix("command.gamemode.usage"));
            return false;
        }

        try{
            int i = Integer.parseInt(args[0]);

            if(i < 0 || i > 3){
                player.sendMessage(LanguageService.getMessageWithPrefix("command.gamemode.usage"));
                return false;
            }

            setGameMode(player, GameMode.getByValue(i));
        } catch (NumberFormatException e){
            player.sendMessage(LanguageService.getMessageWithPrefix("command.gamemode.usage"));
            return false;
        }

        return false;
    }

    private void setGameMode(Player player, GameMode gameMode){
        player.setGameMode(gameMode);

        switch(gameMode){
            case CREATIVE -> player.sendActionBar(Component.text("§8>> Du bist nun im §eKreativ-Modus"));
            case SURVIVAL -> player.sendActionBar(Component.text("§8>> Du bist nun im §eÜberlebens-Modus"));
            case ADVENTURE -> player.sendActionBar(Component.text("§8>> Du bist nun im §eAbenteuer-Modus"));
            case SPECTATOR -> player.sendActionBar(Component.text("§8>> Du bist nun im §eZuschauer-Modus"));
        }
    }

}
