package ca.agnate.EconXP;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EconXP extends JavaPlugin {
    
    protected List<Node> permissionOPs;
    protected Config config;
    
    public void onDisable() {
        // Save config.
        config.saveData();
        
        // Show disabled message.
        System.out.println("[" + this + "] EconXP is disabled.");
    }

    public void onEnable() {
        // Add in permission node checks for OPs.
    	permissionOPs = new LinkedList<Node>();
        permissionOPs.add( Node.ADD );
        permissionOPs.add( Node.SUBTRACT );
        permissionOPs.add( Node.BALANCE );
        permissionOPs.add( Node.CLEAR );
        
        // Setup config information.
        config = new Config (this, "config.yml");
        
        // Set plugin defaults.
        
        
        // Retrieve the config data.
        config.getData();
        
        // Bind the /econxp, /exp commands to EconXPCommands.
        EconXPCommands commandExecutor = new EconXPCommands (this);
        getCommand("econxp").setExecutor(commandExecutor);
        getCommand("exp").setExecutor(commandExecutor);
        
        // Set up listeners
        PluginManager pm = getServer().getPluginManager();
        //final PlayerListener playerListener = new EconXPPlayerListener(this);
        //pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Lowest, this);
        
        // Show enabled message.
        System.out.println("[" + this + "] EconXP is enabled.");
    }

    public static int setExp (Player p, int value) {
        if ( p == null ) { return -1; }
        
        // Set the experience.
        p.setTotalExperience( value );
        
        // Return the value.
        return value;
    }
    public static int getExp (Player p) {
       if ( p == null ) { return -1; }
       
       return p.getTotalExperience(); 
    }
    public static int removeExp (Player p, int value) {
        int remain = getExp(p);
        
        // Remove the exp from the player.
        if ( remain >= 0 ) {
            remain -= value;
        }
        
        // If there's some exp left over, set it to the remaining.
        if ( remain >= 0 ) {
            // Set exp to remaining.
            setExp( p, remain );
            
            // Set remaining to amount removed (in this case, it's value).
            remain = value;
        }
        else {
            // Set exp to zero.
            setExp( p, 0 );
            
            // If remain < 0, adding it to value will give the true amount removed.
            remain = value + remain;
        }
        
        // Return the amount that was removed.
        return remain;
    }
    public static int addExp (Player p, int value) {
        int exp = getExp(p);
        
        // If it is less than 0 (ie. -1), it means there was a problem.
        if ( exp < 0 ) { return 0; }
        
        // Check the value.
        if ( value < 0 ) { return 0; }
        
        // Add in the experience.
        setExp(p, exp + value);
        
        // Return the value added.
        return value;
    }
    public static int multiplyExp (Player p, float multiplier) {
    	// If player doesn't exist, value can't be multiplied.
    	if ( !(p instanceof Player) ) {
    		return 0;
    	}
    	
    	// If the multiplier is negative, cancel transaction.
    	if ( multiplier < 0 ) {
    		return 0;
    	}
    	
    	// Get player's current exp.
        int exp = getExp(p);
        
        // If something is wrong with the exp, cancel.
        if ( exp < 0 ) { return 0; }
    	
    	// Multiply the exp by the multiplier, add it to the player's exp, and return the value.
    	return setExp( p, Math.round(exp * multiplier) );
    }
    public static int divideExp (Player p, float divisor) {
    	// If player doesn't exist, value can't be multiplied.
    	if ( !(p instanceof Player) ) {
    		return 0;
    	}
    	
    	// If the divisor is negative, cancel transaction.
    	if ( divisor < 0 ) {
    		return 0;
    	}
    	
    	// Get player's current exp.
        int exp = getExp(p);
        
        // If something is wrong with the exp, cancel.
        if ( exp < 0 ) { return 0; }
    	
    	// Divide the exp by the divisor, add it to the player's exp, and return the value.
    	return setExp( p, Math.round(exp / divisor) );
    }
    public static int clearExp (Player p) {
        // Get player's current exp.
        int exp = getExp(p);
        
        // Set exp to zero.
        setExp( p, 0 );
        
        // Return the exp they had.
        return exp;
    }
    public static boolean hasExp (Player p, int value) {
        return ( getExp(p) >= value );
    }
    
    public static int giveExp (Player giver, Player receiver, int value) {
    	// If one of the players doesn't exist, no transaction was made.
    	if ( !(giver instanceof Player)  ||  !(receiver instanceof Player) ) {
    		return 0;
    	}
    	
    	// If the value is negative, cancel transaction.
    	if ( value < 0 ) {
    		return 0;
    	}
    	
    	// If  the giver can't afford it, cancel transaction.
    	if ( hasExp(giver, value) == false ) {
    		return 0;
    	}
    	
    	// Remove exp from giver and add it to receiver, and return how much was given.
    	return addExp( receiver, removeExp(giver, value) );
    }
    
    public static boolean sendMsg(CommandSender p, String msg) {
        // If the input sender is null or the string is empty, return.
        if (p == null || msg.equals(""))
            return false;
        
        // Otherwise, send the message with the [EconXP] tag.
        p.sendMessage(ChatColor.AQUA + "[EconXP] " + ChatColor.WHITE + msg);
        return true;
    }
    
    public boolean has(Permissible p, Node n) {
        // return (permissionHandler == null || permissionHandler.has(p, s));
        // return hasSuperPerms(p, s);
        return hasSuperPerms(p, n.toString()) || hasOPPerm(p, n);
    }
    protected boolean hasOPPerm(Permissible p, Node node) {
        // If the node requires OP status, and the player has OP, then true.
        return (permissionOPs == null || permissionOPs.contains(node) == false || p.isOp());
    }
    protected boolean hasSuperPerms(Permissible p, String s) {
        String[] nodes = s.split("\\.");

        String perm = "";
        for (int i = 0; i < nodes.length; i++) {
            perm += nodes[i] + ".";
            if (p.hasPermission(perm + "*"))
                return true;
        }

        return p.hasPermission(s);
    }
}
