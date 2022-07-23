package de.webcode.system.event;

import de.webcode.system.utils.chat.ChatMessage;
import de.webcode.system.utils.chat.ChatService;
import de.webcode.system.utils.chat.ChatType;
import de.webcode.system.utils.inventory.Menu;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryHolder;

public class Eventlistener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ChatService.getService().setPlayerChatType(player, ChatType.STANDART);
    }
    @EventHandler
    public void onMenuClick(InventoryClickEvent e){

        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof Menu) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null) {
                return;
            }

            Menu menu = (Menu) holder;
            menu.handleMenu(e);
        }

    }

    @EventHandler
    public void onChatMessage(AsyncChatEvent event){
        Player player = event.getPlayer();
        ChatMessage message = new ChatMessage(event.message(), event.getPlayer(), ChatService.getService().getPlayerChatType(player));
        ChatService.getService().handleMessage(message);
        event.setCancelled(true);
    }

}
