package com.zandor300.spawnershop.handlers;

import com.zandor300.spawnershop.SpawnerShop;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by Zandor on 3/26/15.
 */
public class BuyHandler {

	public static boolean buy(Player player, EntityType type, int amount, int price) {
		EconomyResponse transaction = SpawnerShop.getEconomy().withdrawPlayer(player, price);
		if (transaction.transactionSuccess()) {
			for (int i = 0; i < amount; i++) {
				ItemStack item = new ItemStack(Material.MOB_SPAWNER, 1, type.getTypeId());
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.WHITE + type.getName() + " Spawner");
				meta.setLore(Arrays.asList(type.getName()));
				item.setItemMeta(meta);
				player.getInventory().addItem(item);
			}
			player.updateInventory();
			return true;
		} else {
			SpawnerShop.getChat().sendMessage(player, ChatColor.RED + "Insufficient balance.");
			return false;
		}
	}
}
