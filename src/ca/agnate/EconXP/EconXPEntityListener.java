package ca.agnate.EconXP;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EconXPEntityListener extends EntityListener {
	
	protected EconXP plugin;
	
	public EconXPEntityListener(EconXP plugin) {
		this.plugin = plugin;
	}
	
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
}
