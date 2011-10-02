package ca.agnate.EconXP;

import java.io.File;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;

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
        permissionOPs.add( Node.GIVE );
        permissionOPs.add( Node.TAKE );
        permissionOPs.add( Node.VIEW );
        permissionOPs.add( Node.CLEAR );
        
        // Setup config information.
        config = new Config (this, "config.yml");
        
        // Set plugin defaults.
        

        // Retrieve the config data.
        config.getData();
        
        // Set up listeners
        PluginManager pm = getServer().getPluginManager();
        //final EntityListener entityListener = new SBEntityListener(this);
        //pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Lowest, this);
        
        // Show enabled message.
        System.out.println("[" + this + "] EconXP is enabled.");
    }

    public static void setExp (Player p, int value) {
        if ( p == null ) { return; }
        
        // Set the experience.
        p.setExperience( value );
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
        if ( exp < 0 ) { return exp; }
        
        // Add in the experience.
        exp += value;
        
        // If it's higher than zero, set the exp.
        if ( exp >= 0 ) {
            setExp(p, exp);
        }
        
        // Return the total exp now.
        return exp;
    }
    public static int clearExp (Player p) {
        // Get player's current exp.
        int exp = getExp(p);
        
        // Set exp to zero.
        setExp( p, 0 );
        
        // Return the exp they had.
        return exp;
    }
    
    public boolean has(Player p, Node n) {
        // return (permissionHandler == null || permissionHandler.has(p, s));
        // return hasSuperPerms(p, s);
        return hasSuperPerms(p, n.toString()) || hasOPPerm(p, n);
    }
    protected boolean hasOPPerm(Player p, Node node) {
        // If the node requires OP status, and the player has OP, then true.
        return (permissionOPs == null || permissionOPs.contains(node) == false || p.isOp());
    }
    protected boolean hasSuperPerms(Player p, String s) {
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
