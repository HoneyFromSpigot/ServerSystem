package de.webcode.system.utils.file;

import de.webcode.system.ServerSystem;
import org.bukkit.configuration.file.YamlConfiguration;

public interface Filemanagement {
    default void save(){
        ServerSystem.getInstance().getFileService().saveFiles();
    }

    default YamlConfiguration getConfig(){
        return ServerSystem.getInstance().getFileService().getConfig();
    }

    default YamlConfiguration getPlayerData(){
        return ServerSystem.getInstance().getFileService().getPlayerData();
    }

    default FileService getService(){
        return ServerSystem.getInstance().getFileService();
    }
}
