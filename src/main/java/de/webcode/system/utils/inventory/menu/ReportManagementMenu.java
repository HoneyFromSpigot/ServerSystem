package de.webcode.system.utils.inventory.menu;

import de.webcode.system.utils.PlayerManagementService;
import de.webcode.system.utils.inventory.Menu;
import de.webcode.system.utils.inventory.menuutility.TargetPlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ReportManagementMenu extends Menu {

    public ReportManagementMenu(TargetPlayerMenuUtility utility) {
        super(utility);
    }

    @Override
    public String getMenuName() {
        return "§8Report > §e" + ((TargetPlayerMenuUtility) playerMenuUtility).getTarget().getName();
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();
        Player target = ((TargetPlayerMenuUtility) playerMenuUtility).getTarget();
        Player player = ((TargetPlayerMenuUtility) playerMenuUtility).getOwner();

        switch (currentItem.getType()) {
            case ENDER_PEARL:
                break;
            case IRON_SWORD:
                target.kickPlayer("§8----------------------------------------\n" +
                        "§7Noch einmal und man sieht sich nicht mehr :D" +
                        "\n§8----------------------------------------");
                break;
            case DIAMOND_SWORD:
                PlayerManagementService.getService().banPlayer(target, "§cVerstoß gegen die Regeln");
                break;
        }
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }

        inventory.setItem(10, super.FILLER_GLASS);
        inventory.setItem(16, super.FILLER_GLASS);
        inventory.setItem(17, super.FILLER_GLASS);

        for (int i = 18; i < 27; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }

        ItemStack teleport = makeItem(Material.ENDER_PEARL, "§aZum Spieler teleportieren", "", "§7Du wirst unsichtbar zum Spieler teleportiert.");
        ItemStack kick = makeItem(Material.IRON_SWORD, "§eSpieler kicken", "", "§7Der Spieler wird gekickt.");
        ItemStack ban = makeItem(Material.DIAMOND_SWORD, "§cSpieler bannen", "", "§7Der Spieler wird §c§ldauerhaft §7gebannt");

        inventory.setItem(11, teleport);
        inventory.setItem(13, kick);
        inventory.setItem(15, ban);
    }
}
