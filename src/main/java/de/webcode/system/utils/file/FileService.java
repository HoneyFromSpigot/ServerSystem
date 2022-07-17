package de.webcode.system.utils.file;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileService {
    private File FOLDER, configFile, playerDataFile;

    private YamlConfiguration config, playerData;

    public FileService(){
        this.FOLDER = new File("./plugins/system/");
        this.configFile = new File(FOLDER, "config.yml");
        this.playerDataFile = new File(FOLDER, "playerdata.yml");

        try{
            if(!FOLDER.exists()) FOLDER.mkdirs();
            if(!configFile.exists()) configFile.createNewFile();
            if(!playerDataFile.exists()) playerDataFile.createNewFile();

            this.config = YamlConfiguration.loadConfiguration(configFile);
            this.playerData = YamlConfiguration.loadConfiguration(playerDataFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveFiles(){
        try{
            config.save(configFile);
            playerData.save(playerDataFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public YamlConfiguration getPlayerData() {
        return playerData;
    }
}
