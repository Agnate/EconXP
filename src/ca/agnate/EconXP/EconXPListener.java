package ca.agnate.EconXP;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class EconXPListener implements Listener {
	
	EconXP plugin;
	
	public EconXPListener( EconXP aPlugin ) {
		plugin = aPlugin;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin (PlayerJoinEvent event) {
		// Set the experience so that levels are calculated properly.
		plugin.setExp(event.getPlayer(), plugin.getExp(event.getPlayer()));
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerRespawn (PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		
		// Delay the setting so that it actually works.
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
            public void run()
            {
            	// Set the experience so that levels are calculated properly.
        		plugin.setExp(player, plugin.getExp(player));
            }
        }, 1 );
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDeath (EntityDeathEvent evt) {
		if ( evt instanceof PlayerDeathEvent == false ) {
			return;
		}
		
		PlayerDeathEvent event = (PlayerDeathEvent) evt;
		Player player = (event.getEntity() instanceof Player) ? (Player) event.getEntity() : null;
		
		if ( player == null ) {
			return;
		}
		
		event.setDroppedExp( plugin.calcDroppedExp(player) );
		event.setNewExp( plugin.calcRemainingExp(player) );
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEnchantItem (EnchantItemEvent event) {
		if ( event.isCancelled() ) {
			return;
		}
		
		// Calculate the cost based on level.
		int newLevel = event.getEnchanter().getLevel() - event.getExpLevelCost();
		int newTotal = plugin.convertLevelToExp( newLevel );
		
		// Add in what's remaining in the bar.
		newTotal += Math.floor(event.getEnchanter().getExp() * plugin.getExpToLevel( newLevel ));
		
		// Set the experience so that levels are calculated properly.
		plugin.setExp(event.getEnchanter(), newTotal);
		
		// Fake a cost of no levels so that Bukkit doesn't alter the level.
		event.setExpLevelCost( 0 );
	}
}
