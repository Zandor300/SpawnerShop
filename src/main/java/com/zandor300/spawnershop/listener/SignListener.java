package com.zandor300.spawnershop.listener;

import com.zandor300.spawnershop.SpawnerShop;
import com.zandor300.zsutilities.utilities.string.StringUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Zandor on 3/26/15.
 */
public class SignListener implements Listener {

	/*
	 * [Spawner]
	 * <amount>
	 * <type>
	 * <moneyz>
	 */

	@EventHandler
	public void onSignCreation(SignChangeEvent event) {
		Sign sign = null;
		try {
			sign = (Sign) event.getBlock();
		} catch(Exception e) {
			return;
		}

		if(!sign.getLine(0).toLowerCase().contains("[spawner]"))
			return;

		sign.setLine(0, ChatColor.DARK_AQUA + "[Spawner]");
		sign.setLine(1, ChatColor.GREEN + sign.getLine(1));
		sign.setLine(2, ChatColor.RED + sign.getLine(2));
		sign.setLine(3, "$" + sign.getLine(3));
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;

		Block block = event.getClickedBlock();
		if(!block.getType().equals(Material.SIGN) && !block.getType().equals(Material.SIGN_POST)
				&& !block.getType().equals(Material.WALL_SIGN))
			return;

		Sign sign = (Sign) block;
		if(!sign.getLine(0).toLowerCase().contains("[spawner]"))
			return;

		int amount = 0;
		try {
			Integer.valueOf(sign.getLine(1));
		} catch(Exception e) {
			SpawnerShop.getChat().sendMessage(player, "Invalid amount " + sign.getLine(1));
			return;
		}

		EntityType type = EntityType.valueOf(StringUtilities.removeChatColors(
				sign.getLine(2)).replaceAll(" ", "_").toUpperCase());
		if(type == null) {
			SpawnerShop.getChat().sendMessage(player, "Invalid mobtype " + sign.getLine(2));
			return;
		}

		int money = 0;
		try {
			money = Integer.valueOf(sign.getLine(3).replaceAll("$", ""));
		} catch(Exception e) {
			SpawnerShop.getChat().sendMessage(player, "Invalid money " + sign.getLine(3));
			return;
		}


	}
}
