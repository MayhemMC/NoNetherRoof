package com.jmer05.nonetherroof;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public ConsoleCommandSender ConsoleLogger = getServer().getConsoleSender();
	public String NoNetherRoof = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "NoNetherRoof" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET;

	public int NETHER_ROOF = 126;
	public int OVERWORLD_HEIGHT = 300;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		ConsoleLogger.sendMessage(NoNetherRoof + "Enabled");
	}

	@Override
	public void onDisable() {
		
		ConsoleLogger.sendMessage(NoNetherRoof + "Disabled");
	}
	
	public boolean isAllowedAboveNether(Player player) {
		if(player.isOp()) return true;
		if(player.hasPermission("nonetherroof.isallowed")) return true;
		return false;
	}
	
	public Location getFreespot(Location location) {
		Location freespot = location;
		boolean resolved = false;
		
		for(int y = location.getWorld().getName().equals("world_nether") ? NETHER_ROOF : OVERWORLD_HEIGHT; y > 3; y--) {
			if(resolved == false) {
				freespot.setY(y);
				Location freespot2 = freespot;
				freespot2.setY(y + 1);
		
				if(freespot.getBlock().getType().equals(Material.AIR)) {
					if(freespot2.getBlock().getType().equals(Material.AIR)) {
						resolved = true;
						
						while(freespot.getBlock().getType().equals(Material.AIR) ) {
							freespot.setY(freespot.getY() - 1);
						}
						
						freespot.setY(freespot.getY() + 1);
						return freespot;
						
					}
				}
			}
		}
		
		return location;
		
	}
	
	public void lowerPlayer(Player player) {
		Location location = player.getLocation();
		player.teleport(getFreespot(location), TeleportCause.PLUGIN);
	}
	
	/*
	 * ##################
	 * # EVENT HANDLERS #
	 * ##################
	 */
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(!isAllowedAboveNether(player)) {
			
			if(player.getLocation().getWorld().getName().equals("world_nether") && player.getLocation().getBlockY() > NETHER_ROOF) {
				lowerPlayer(player);
			}
			
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if(!isAllowedAboveNether(player)) {
			
			Location location = event.getBlock().getLocation();
			if((location.getWorld().getName().equals("world_nether") && location.getBlockY() > NETHER_ROOF) || (player.getLocation().getWorld().getName().equals("world_nether") && player.getLocation().getBlockY() > NETHER_ROOF)) {
				event.setCancelled(true);
				lowerPlayer(player);
			}
			
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if(!isAllowedAboveNether(player)) {
			
			Location location = event.getBlock().getLocation();
			if((location.getWorld().getName().equals("world_nether") && location.getBlockY() > NETHER_ROOF) || (player.getLocation().getWorld().getName().equals("world_nether") && player.getLocation().getBlockY() > NETHER_ROOF)) {
				event.setCancelled(true);
				lowerPlayer(player);
			}
			
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(!isAllowedAboveNether(player)) {
			
			Location location = player.getLocation();
			if(location.getWorld().getName().equals("world_nether") && location.getBlockY() > NETHER_ROOF) {
				event.setCancelled(true);
				lowerPlayer(player);
			}
			
		}
	}
	
	
	@EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		if(!isAllowedAboveNether(player)) {
			
			Location location = player.getLocation();
			if(location.getWorld().getName().equals("world_nether") && location.getBlockY() > NETHER_ROOF) {
				event.setCancelled(true);
				lowerPlayer(player);
			}
			
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		if(!isAllowedAboveNether(player)) {
			
			Location location = event.getTo();
			if(location.getWorld().getName().equals("world_nether") && location.getBlockY() > NETHER_ROOF) {
				event.setTo(getFreespot(location));
			}
			
		}
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if(!isAllowedAboveNether(player)) {
			
			Location location = player.getLocation();
			if(location.getWorld().getName().equals("world_nether") && location.getBlockY() > NETHER_ROOF) {
				event.setCancelled(true);
				lowerPlayer(player);
			}
			
		}
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent event){
		Player player = event.getPlayer();
		if(!isAllowedAboveNether(player)) {
			
			Location location = player.getLocation();
			if(location.getWorld().getName().equals("world_nether") && location.getBlockY() > NETHER_ROOF) {
				event.setCancelled(true);
				lowerPlayer(player);
			}
			
		}
	}
	
}
