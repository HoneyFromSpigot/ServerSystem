package de.webcode.system.utils.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ChatType {
    ADMIN("§8[§c§lADMIN-CHAT§8] ", "system.chat.admin", new ArrayList<>()),
    SERVER_TEAM("§8[§9SERVERTEAM-CHAT§8] §f","system.chat.team", Arrays.asList(ChatType.ADMIN)),
    STANDART("","system.chat.standart", Arrays.asList(ChatType.ADMIN, ChatType.SERVER_TEAM)),
    BROADCAST("§8[§aServer-Ruf§8] §f", "system.chat.standart", Arrays.asList(ChatType.STANDART, ChatType.SERVER_TEAM, ChatType.ADMIN));

    private List<ChatType> visibleChats;
    private String chatPermission;
    private String chatPrefix;

    ChatType(String chatPrefix, String chatPermission, List<ChatType> visibleChats){
        this.chatPrefix = chatPrefix;
        this.chatPermission = chatPermission;
        this.visibleChats = visibleChats;
    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public String getChatPermission() {
        return chatPermission;
    }

    public List<ChatType> getVisibleChats() {
        return visibleChats;
    }
}
