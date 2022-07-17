package de.webcode.system;

import de.webcode.system.commands.WarnCommand;
import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.file.FileService;
import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public final class ServerSystem extends JavaPlugin {
    private static ServerSystem instance;
    private FileService fileService;
    private LanguageService languageService;

    @Override
    public void onEnable() {
        instance = this;

        this.fileService = new FileService();


        this.languageService = new LanguageService("de_de.json");

        registerCommands();
    }

    private void registerCommands(){
        getCommand("warn").setExecutor(new WarnCommand());
    }

    public FileService getFileService() {
        return fileService;
    }

    public LanguageService getLanguageService() {
        return languageService;
    }

    public static ServerSystem getInstance() {
        return instance;
    }
}
