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

    public void addWarning(Player player, Warning warning){
        String pName = player.getName();
        int count = getWarningCount(player) + 1;

        playerData.set(pName + ".warning.count", count);
        playerData.set(pName + ".warning." + count + ".mod", warning.getMod().getName());
        playerData.set(pName + ".warning." + count + ".date", warning.getDate());
        playerData.set(pName + ".warning." + count + ".reason", warning.getReason());

        fileService.saveFiles();
    }

    public ArrayList<Warning> getWarnings(Player player){
        int warningCount = getWarningCount(player);

        if(warningCount == 0) return null;

        String pName = player.getName();

        return null;
    }

    public int getWarningCount(Player player){
        String pName = player.getName();
        if(playerData.isSet(pName + ".warning.count")){
            return playerData.getInt(pName + ".warning.count");
        }

        return 0;
    }

    public static PlayerManagementService getService(){
        return ServerSystem.getInstance().getPlayerManagementService();
    }
}
