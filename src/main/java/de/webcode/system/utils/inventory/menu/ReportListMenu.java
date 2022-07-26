package de.webcode.system.utils.inventory.menu;

import de.webcode.system.utils.PlayerManagementService;
import de.webcode.system.utils.inventory.PaginatedMenu;
import de.webcode.system.utils.inventory.menuutility.PlayerMenuUtility;
import de.webcode.system.utils.inventory.menuutility.TargetPlayerMenuUtility;
import de.webcode.system.utils.reporting.PlayerReport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class ReportListMenu extends PaginatedMenu {

    public ReportListMenu(PlayerMenuUtility utility) {
        super(utility);
    }

    @Override
    public String getMenuName() {
        return "§eReports";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ArrayList<ItemStack> items = new ArrayList<>();

        for (PlayerReport report : PlayerManagementService.getService().getAllPlayerReports()) {
            ItemStack is = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) is.getItemMeta();
            meta.setDisplayName("§e" + report.getReportedPlayer());
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Reported von: §6" + report.getReporter());
            lore.add("§7Datum: §6" + report.getDate());
            lore.add("§7Grund: §6");
            lore.add("§7" + report.getReason());
            meta.setLore(lore);
            meta.setOwner(report.getReportedPlayer());
            is.setItemMeta(meta);

            items.add(is);
        }

        if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
            Player player = (Player) e.getWhoClicked();
            String playerName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            Player target = Bukkit.getPlayer(playerName);
            if (target == null) {
                player.sendMessage("§cDer Spieler ist nicht online!");
                player.closeInventory();
                return;
            }

            new ReportManagementMenu(new TargetPlayerMenuUtility(player, target)).open();
        }else if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            p.closeInventory();
        }else if(e.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)){
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("<")){
                if (page != 0) {
                    page = page - 1;
                    super.open();
                }
            }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(">")){
                if (!((index + 1) >= items.size())){
                    page = page + 1;
                    super.open();
                }
            }
        }

    }

    @Override
    public void setMenuItems() {
        addMenuBorder();

        ArrayList<ItemStack> items = new ArrayList<>();

        for (PlayerReport report : PlayerManagementService.getService().getAllPlayerReports()) {
            ItemStack is = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) is.getItemMeta();
            meta.setDisplayName("§e" + report.getReportedPlayer());
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Reported von: §6" + report.getReporter());
            lore.add("§7Datum: §6" + report.getDate());
            lore.add("§7Spielerstatus: §6" + (Bukkit.getPlayer(report.getReportedPlayer()) == null ? "§coffline" : "§aonline"));
            lore.add("§7Grund: §6");
            lore.add("§7" + report.getReason());
            meta.setLore(lore);
            meta.setOwner(report.getReportedPlayer());
            is.setItemMeta(meta);

            items.add(is);
        }

        if(items != null && !items.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= items.size()) break;
                if (items.get(index) != null){
                    inventory.addItem(items.get(index));
                }
            }
        }
    }
}
