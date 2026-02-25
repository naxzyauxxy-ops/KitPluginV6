package dev.kitplugin.gui;

import dev.kitplugin.KitPlugin;
import dev.kitplugin.kits.Kit;
import dev.kitplugin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitPreviewMenu {

    // Title prefix so we can detect it in the listener
    public static final String TITLE_PREFIX = "§8Preview: ";

    public static void open(KitPlugin plugin, Player player, Kit kit) {
        String title = TITLE_PREFIX + kit.getDisplayName();
        Inventory inv = Bukkit.createInventory(null, 54, title);

        // Background
        ItemStack fill = new ItemBuilder(kit.getBorderMaterial()).name(" ").build();
        for (int i = 0; i < 54; i++) inv.setItem(i, fill);

        // Dark inner area (slots 10-16, 19-25, 28-34)
        ItemStack dark = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(" ").build();
        int[] inner = {10,11,12,13,14,15,16, 19,20,21,22,23,24,25, 28,29,30,31,32,33,34};
        for (int s : inner) inv.setItem(s, dark);

        // ── ARMOR ROW (slots 20–23) ──────────────────────────────
        if (kit.getHelmet() != null)     inv.setItem(20, kit.getHelmet());
        if (kit.getChestplate() != null) inv.setItem(21, kit.getChestplate());
        if (kit.getLeggings() != null)   inv.setItem(22, kit.getLeggings());
        if (kit.getBoots() != null)      inv.setItem(23, kit.getBoots());

        // ── ITEMS ROW (slots 29 onward) ──────────────────────────
        int[] itemSlots = {29, 30, 31, 32, 33};
        var items = kit.getItems();
        for (int i = 0; i < items.size() && i < itemSlots.length; i++) {
            inv.setItem(itemSlots[i], items.get(i).clone());
        }

        // ── BUTTONS ──────────────────────────────────────────────
        // Claim button
        inv.setItem(38, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .name("§a§lCLAIM KIT")
                .lore("§7Click to claim the " + kit.getDisplayName() + " §7kit!")
                .build());

        // Back button
        inv.setItem(42, new ItemBuilder(Material.ARROW)
                .name("§e§lBack to Kits")
                .lore("§7Return to the kit menu.")
                .build());

        player.openInventory(inv);
    }
}
