package de.webcode.system.utils.chat;

import de.webcode.system.utils.ItemFactory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ChatType {
    ADMIN(ItemFactory.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZTU0Y2JlODc4NjdkMTRiMmZiZGYzZjE4NzA4OTQzNTIwNDhkZmVjZDk2Mjg0NmRlYTg5M2IyMTU0Yzg1In19fQ==", "Admin Chat"),"§8[§c§lADMIN-CHAT§8] ", "system.chat.admin", new ArrayList<>()),
    SERVER_TEAM(ItemFactory.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjlhNjAwYWIwYTgzMDk3MDY1Yjk1YWUyODRmODA1OTk2MTc3NDYwOWFkYjNkYmQzYTRjYTI2OWQ0NDQwOTU1MSJ9fX0=", "Team Chat"),"§8[§9SERVERTEAM-CHAT§8] §f","system.chat.team", Arrays.asList(ChatType.ADMIN)),
    STANDART(ItemFactory.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjAyYWYzY2EyZDVhMTYwY2ExMTE0MDQ4Yjc5NDc1OTQyNjlhZmUyYjFiNWVjMjU1ZWU3MmI2ODNiNjBiOTliOSJ9fX0=", "Standart Chat"),"","system.chat.standart", Arrays.asList(ChatType.ADMIN, ChatType.SERVER_TEAM)),
    BROADCAST(ItemFactory.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQ5YjYzYzNiNzQ1ZjVkZjg1MTE3NTk3MmVlZmQ3N2VjYTUyNjlkNDg2N2M0ZWRhMTU5NGZmM2U2NjM0NjU4In19fQ==", "Broadcast Chat"),"§8[§aServer-Ruf§8] §f", "system.chat.standart", Arrays.asList(ChatType.STANDART, ChatType.SERVER_TEAM, ChatType.ADMIN));

    private List<ChatType> visibleChats;
    private String chatPermission;
    private String chatPrefix;
    private ItemStack itemStack;

    ChatType(ItemStack itemStack, String chatPrefix, String chatPermission, List<ChatType> visibleChats){
        this.itemStack = itemStack;
        this.chatPrefix = chatPrefix;
        this.chatPermission = chatPermission;
        this.visibleChats = visibleChats;
    }

    public ItemStack getItemStack() {
        return itemStack;
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
