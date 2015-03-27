package com.zandor300.spawnershop.listener;

import com.zandor300.spawnershop.SpawnerShop;
import com.zandor300.spawnershop.handlers.BuyHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;

/**
 * Created by Zandor on 3/27/15.
 */
public class PlayerListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(!event.getInventory().getName().contains("Spawner Shop"))
			return;

		ItemStack item = event.getCurrentItem();
		if(item == null || item.getType() == null || item.getType().equals(Material.AIR))
			return;

		if(!item.getType().equals(Material.MONSTER_EGG))
			return;

		SpawnEgg egg = new SpawnEgg(item.getData().getData());
		int price = 1;
		if(SpawnerShop.getCustomConfig().getInt("price." + egg.getSpawnedType().getName().toLowerCase()) != 0)
			price = SpawnerShop.getCustomConfig().getInt("price." + egg.getSpawnedType().getName().toLowerCase());
		BuyHandler.buy((Player)event.getWhoClicked(), egg.getSpawnedType(), 1, price);
	}
}
