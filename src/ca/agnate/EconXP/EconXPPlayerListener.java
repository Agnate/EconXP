package ca.agnate.EconXP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerListener;

import ca.agnate.EconXP.EconXPMsg.Msg;

public class EconXPPlayerListener extends PlayerListener {
    
    EconXP plugin;
    
    public EconXPPlayerListener (EconXP aPlugin) {
        plugin = aPlugin;
    }
    
    /*public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().split(" ");

        // If there is no command, ignore it.
        if (args.length <= 0) {
            return;
        }
        
        // If it's not an EconXP command, ignore it.
        if (args[0].equalsIgnoreCase(plugin.commandPrefix)) {
            return;
        }
        
        Player p = event.getPlayer();
        //p.sendMessage("WARNING!");
        
        if (args[1].equalsIgnoreCase("add")) {
            if ( plugin.has(p, Node.GIVE) == false ) {
                //plugin.tellPlayer(p, Msg.NOT_ALLOWED);
                return;
            }
        }
    }*/
    
    public void onCommand ( CommandSender sender, Command command, String label, String[] args ) {
        
    }
}
