package de.webcode.system;

import de.webcode.system.utils.file.FileService;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerSystem extends JavaPlugin {
    private static ServerSystem instance;
    private FileService fileService;

    @Override
    public void onEnable() {
        instance = this;

        this.fileService = new FileService();
    }

    public FileService getFileService() {
        return fileService;
    }

    public static ServerSystem getInstance() {
        return instance;
    }
}
