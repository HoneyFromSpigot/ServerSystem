package de.webcode.system;

import de.webcode.system.commands.*;
import de.webcode.system.event.Eventlistener;
import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.PlayerManagementService;
import de.webcode.system.utils.chat.ChatService;
import de.webcode.system.utils.chat.ChatType;
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
    private ChatService chatService;

    @Override
    public void onEnable() {
        instance = this;

        this.fileService = new FileService();
        this.languageService = new LanguageService("de_de.json");
        this.inventoryService = new InventoryService();
        this.playerManagementService = new PlayerManagementService();
        this.chatService = new ChatService();

        registerCommands();
        registerEvents();

        Bukkit.getOnlinePlayers().forEach(player -> {
            ChatService.getService().setPlayerChatType(player, ChatType.STANDART);
        });
    }

    private void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new Eventlistener(), this);
    }

    private void registerCommands(){
        getCommand("chatsel").setExecutor(new ChatSelCommand());
        getCommand("bc").setExecutor(new BroadcastCommand());
        getCommand("tc").setExecutor(new TeamChatCommand());
        getCommand("skick").setExecutor(new SKickCommand());
        getCommand("gm").setExecutor(new GamemodeCommand());
        getCommand("warns").setExecutor(new WarnListCommand());
        getCommand("warn").setExecutor(new WarnCommand());
    }

    public ChatService getChatService() {
        return chatService;
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
