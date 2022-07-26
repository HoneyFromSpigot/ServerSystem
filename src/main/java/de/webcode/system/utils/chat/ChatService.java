package de.webcode.system.utils.chat;

import de.webcode.system.ServerSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ChatService {

    private HashMap<Player, ChatType> playerChatTypes;
    private boolean globalMute;

    public ChatService(){
        this.playerChatTypes = new HashMap<>();
        YamlConfiguration config = ServerSystem.getInstance().getFileService().getConfig();
        this.globalMute = (config.isSet("globalmute") && config.getBoolean("globalmute"));
    }

    public void setGlobalMute(boolean value) {
        this.globalMute = value;
    }

    public boolean isGlobalMute() {
        return globalMute;
    }

    public ChatType getPlayerChatType(Player player){
        if(playerChatTypes.containsKey(player)){
            return playerChatTypes.get(player);
        }

        playerChatTypes.put(player, ChatType.STANDART);
        return ChatType.STANDART;
    }

    public void handleMessage(ChatMessage chatMessage){
        ChatType type = chatMessage.getType();


        playerChatTypes.keySet().forEach(player -> {
            if(type == getPlayerChatType(player) || type.getVisibleChats().contains(getPlayerChatType(player)) || player.hasPermission(type.getChatPermission())){
                TextComponent prefix = Component.text(chatMessage.getType().getChatPrefix() + (chatMessage.getSender() == null ? "" : "<" + chatMessage.getSender().getName() + "> "));

                if (globalMute && getPlayerChatType(player) == ChatType.STANDART) {
                    return;
                }else{
                    player.sendMessage(prefix.append(chatMessage.getMessage()));
                }
            }
        });
    }

    public void setPlayerChatType(Player player, ChatType chatType){
        playerChatTypes.put(player, chatType);
    }

    public static ChatService getService(){
        return ServerSystem.getInstance().getChatService();
    }
}
