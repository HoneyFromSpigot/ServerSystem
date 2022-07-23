package de.webcode.system.utils.chat;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class ChatMessage {
    private Component message;
    private Player sender;
    private ChatType type;


    public ChatMessage(String message, Player sender, ChatType type) {
        this.message = Component.text(message);
        this.sender = sender;
        this.type = type;
    }

    public ChatMessage(Component message, Player sender, ChatType type) {
        this.message = message;
        this.sender = sender;
        this.type = type;
    }

    public void handle(){
        ChatService.getService().handleMessage(this);
    }

    public Component getMessage() {
        return message;
    }

    public Player getSender() {
        return sender;
    }

    public ChatType getType() {
        return type;
    }
}
