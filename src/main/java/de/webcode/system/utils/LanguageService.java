package de.webcode.system.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.webcode.system.ServerSystem;
import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.material.PressureSensor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class LanguageService {
    private File langFile;
    private JsonObject jsonObject;

    public LanguageService(String filename){
        InputStream resource = ServerSystem.getInstance().getResource(filename);
        File file = new File("./plugins/system/language.json");
        if(file.exists()) file.delete();

        try {
            FileUtils.copyInputStreamToFile(resource, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.langFile = file;

        try{
            JsonParser parser = new JsonParser();
            JsonElement parse = parser.parse(new FileReader(langFile));
            this.jsonObject = parse.getAsJsonObject();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getMessage(String key){
        return ChatColor.translateAlternateColorCodes('&', this.jsonObject.get(key).getAsString());
    }

    public static String getMessageWithPrefix(String key){
        LanguageService languageService = ServerSystem.getInstance().getLanguageService();

        String s = languageService.getMessage("prefix") + languageService.getMessage(key);
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
