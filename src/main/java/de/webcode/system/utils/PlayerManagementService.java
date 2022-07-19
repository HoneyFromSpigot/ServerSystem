package de.webcode.system.utils;

import de.webcode.system.ServerSystem;
import de.webcode.system.utils.file.FileService;
import de.webcode.system.utils.reporting.Warning;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerManagementService {
    private FileService fileService;
    private YamlConfiguration playerData;

    public PlayerManagementService(){
        this.fileService = ServerSystem.getInstance().getFileService();
        this.playerData = fileService.getPlayerData();
    }

    public void resetWarnings(Player player){
        String pName = player.getName();

        playerData.set(pName + ".warning", null);
        fileService.saveFiles();

        playerData.set(pName + ".warning.count", 0);
        fileService.saveFiles();
    }

    public void addWarning(Player player, Warning warning){
        String pName = player.getName();
        int count = getWarningCount(player) + 1;

        playerData.set(pName + ".warning.count", count);
        playerData.set(pName + ".warning." + count + ".mod", warning.getMod());
        playerData.set(pName + ".warning." + count + ".date", warning.getDate());
        playerData.set(pName + ".warning." + count + ".reason", warning.getReason());

        fileService.saveFiles();
    }

    public ArrayList<Warning> getWarnings(Player player){
        int warningCount = getWarningCount(player);

        if(warningCount == 0) return null;

        String pName = player.getName();

        ArrayList<Warning> warnings = new ArrayList<>();

        for(int i = 1; i < warningCount + 1; i++){
            String mod = playerData.getString(pName + ".warning." + i + ".mod");
            String date = playerData.getString(pName + ".warning." + i + ".date");
            String reason = playerData.getString(pName + ".warning." + i + ".reason");

            Warning w = new Warning(player, mod, reason, date);
            warnings.add(w);
        }

        return warnings;
    }

    public int getWarningCount(Player player){
        String pName = player.getName();
        if(playerData.isSet(pName + ".warning.count")){
            return playerData.getInt(pName + ".warning.count");
        }

        return 0;
    }

    public void banPlayer(Player player, String reason){
        player.banPlayer("\n\n" +
                "§8----------------------------------------\n" +
                "§c§lGebannt\n" +
                "§7Du wurdest wegen folgendem Grund vom Server gebannt: \n\n" +
                reason +
                "\n\n" +
                "§7Für eine Entbannung wende dich bitte an die Administratoren des Servers.\n" +
                "§8----------------------------------------");
        player.kickPlayer(reason);
    }

    public static PlayerManagementService getService(){
        return ServerSystem.getInstance().getPlayerManagementService();
    }
}
