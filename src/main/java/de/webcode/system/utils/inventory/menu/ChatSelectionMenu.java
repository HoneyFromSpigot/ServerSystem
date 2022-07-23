package de.webcode.system.utils.inventory.menu;

import de.webcode.system.utils.LanguageService;
import de.webcode.system.utils.chat.ChatService;
import de.webcode.system.utils.chat.ChatType;
import de.webcode.system.utils.chat.ChatTypeItemStack;
import de.webcode.system.utils.inventory.Menu;
import de.webcode.system.utils.inventory.menuutility.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChatSelectionMenu extends Menu {

    public ChatSelectionMenu(PlayerMenuUtility utility){
        super(utility);
    }

    @Override
    public String getMenuName() {
        return "§eChatauswahl";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();

        for(ChatType type : ChatType.values()){
            if (type.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(currentItem.getItemMeta().getDisplayName())) {
                ChatService.getService().setPlayerChatType((Player) e.getWhoClicked(), type);
                e.getWhoClicked().sendMessage(LanguageService.getMessageWithPrefix("command.chatsel.selection_successfull").replace("{chat_name}", currentItem.getItemMeta().getDisplayName()));
                e.getWhoClicked().closeInventory();
            }
        }
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }

        inventory.setItem(17, super.FILLER_GLASS);

        for (int i = 18; i < 27; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }

        Player player = playerMenuUtility.getOwner();
        for (ChatType type : ChatType.values()) {
            ItemStack itemStack = type.getItemStack();
            ChatTypeItemStack chatTypeItemStack = new ChatTypeItemStack(itemStack, type);

            if(player.hasPermission(type.getChatPermission()) || ChatService.getService().getPlayerChatType(player) == type){
                inventory.addItem(chatTypeItemStack);
            }
        }
    }
}
