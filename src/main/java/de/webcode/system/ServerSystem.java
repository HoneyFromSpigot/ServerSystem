package de.webcode.system;

import de.webcode.system.commands.WarnCommand;
import de.webcode.system.event.Eventlistener;
import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.PlayerManagementService;
import de.webcode.system.utils.file.FileService;
import de.webcode.system.utils.inventory.InventoryService;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public final class ServerSystem extends JavaPlugin {
    private static ServerSystem instance;
    private FileService fileService;
    private LanguageService languageService;
    private InventoryService inventoryService;
    private PlayerManagementService playerManagementService;

    @Override
    public void onEnable() {
        instance = this;

        this.fileService = new FileService();
        this.languageService = new LanguageService("de_de.json");
        this.inventoryService = new InventoryService();
        this.playerManagementService = new PlayerManagementService();

        registerCommands();
        registerEvents();
    }

    private void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new Eventlistener(), this);
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

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public PlayerManagementService getPlayerManagementService() {
        return playerManagementService;
    }

    public static ServerSystem getInstance() {
        return instance;
    }
}
