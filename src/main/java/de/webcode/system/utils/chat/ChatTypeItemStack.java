package de.webcode.system.utils.chat;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ChatTypeItemStack extends ItemStack{
    private ChatType type;
    private ItemStack itemStack;

    public ChatTypeItemStack(ItemStack itemStack, ChatType type){
        super(itemStack);
        this.type = type;
        this.itemStack = itemStack;
    }

    public ChatType getChatType() {
        return type;
    }
}
