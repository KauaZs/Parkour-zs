package org.kauazs.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {

    public static ItemStack ItemCreate(Material material, String display) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(display);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }



    public static void SetItem(Player p, int slot, ItemStack item) {
        p.getInventory().setItem(slot, item);
    }
}
