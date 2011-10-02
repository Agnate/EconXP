package ca.agnate.EconXP;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import ca.agnate.EconXP.EconXPMsg.Msg;

public class EconXPCommands implements CommandExecutor {
    public static final List<String> COMMANDS = new LinkedList<String>();
    
    static {
        COMMANDS.add("add");                 // ADD
    }
    
    private boolean meanAdmins;
    private Server server;
    private EconXP plugin;
    
    public EconXPCommands (EconXP plugin) {
        this.plugin = plugin;
        server = Bukkit.getServer();
        meanAdmins = (server.getPluginManager().getPlugin("Mean Admins") != null);
    }
    
    /**
     * Handles all command parsing.
     * Unrecognized commands return false, giving the sender a
     * list of valid commands (from plugin.yml).
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Play nice with Mean Admins.
        if (meanAdmins && !COMMANDS.contains(args[0].toLowerCase())) {
            server.getPluginManager().getPlugin("Mean Admins").onCommand(sender, command, label, args);
            return true;
        }
        
        // Determine if the sender is a player (and an op), or the console.
        boolean player  = (sender instanceof Player);
        boolean op      = player && ((Player) sender).isOp();
        boolean console = (sender instanceof ConsoleCommandSender);
        
        // Cast the sender to Player if possible.
        Player p = (player) ? (Player)sender : null;
        
        // If there's nothing after the /econxp, quit.
        if (args.length == 0)
            return false;
        
        // Grab the command base and any arguments.
        String base = args[0].toLowerCase();
        String arg1 = (args.length > 1) ? args[1].toLowerCase() : "";
        String arg2 = (args.length > 2) ? args[2].toLowerCase() : "";
        String arg3 = (args.length > 3) ? args[3].toLowerCase() : "";
        Node cmd = null;
        
        // Command:  /econxp add <player> <value>
        if ( contains(base, Node.ADD.toString()) ) {
            cmd = Node.ADD;
        }
        else if ( contains(base, Node.SUBTRACT.toString()) ) {
            cmd = Node.SUBTRACT;
        }
        
        // If no command is found,
        if ( cmd == null ) {
            plugin.sendMsg(sender, "Command not found.");
            return true;
        }
        
        // Verify that player has permission to use this.
        if ( plugin.has(sender, cmd) == false ) {
            plugin.sendMsg(sender, Msg.COMMAND_NOT_ALLOWED.get(sender.getName()) );
            return true;
        }
        
        // Command:  /econxp add <player> <amount>
        if ( cmd.equals(Node.ADD) ) {
            // Get the target player.
            Player target = validatePlayer(sender, arg1 );
            if ( target == null ) { return true; }
            
            // Get the amount.
            int amount = validateAmount( sender, arg2 );
            if ( amount < 0 ) { return true; }
            
            // Add the experience to the player.
            plugin.addExp(target, amount);
            return true;
        }
        // Command: /econxp subtract <player> <amount>
        else if ( cmd.equals(Node.SUBTRACT) ) {
            // Get the target player.
            Player target = validatePlayer(sender, arg1 );
            if ( target == null ) { return true; }
            
            // Get the amount.
            int amount = validateAmount( sender, arg2 );
            if ( amount < 0 ) { return true; }
            
            // Subtract the experience from the player.
            plugin.removeExp(target, amount);
            return true;
        }
        // Command: /econxp balance <player>
        else if ( cmd.equals(Node.BALANCE) ) {
            // Get the target player.
            Player target = validatePlayer(sender, arg1 );
            if ( target == null ) { return true; }
            
            // Report back the player's balance.
            plugin.sendMsg( sender, Msg.PLAYER_BALANCE.get(target.getName(), ""+plugin.getExp(target)) );
            return true;
        }
        // Command: /econxp clear <player>
        else if ( cmd.equals(Node.CLEAR) ) {
            // Get the target player.
            Player target = validatePlayer(sender, arg1 );
            if ( target == null ) { return true; }
            
            // Report back how much was cleared.
            plugin.sendMsg( sender, Msg.PLAYER_CLEAR.get(target.getName(), ""+plugin.clearExp(target)) );
            return true;
        }
        // Command: /econxp version
        else if ( cmd.equals(Node.VERSION) ) {
            // Report back the version number.
            plugin.sendMsg( sender, "Version #"+plugin.getDescription().getVersion() );
            return true;
        }
        
        // Command wasn't handled.
        plugin.sendMsg(sender, Msg.COMMAND_UNHANDLED.get(cmd.toString()));
        return true;
    }
    
    private Player validatePlayer (CommandSender sender, String player) {
        // If not player name was given,
        if ( player.isEmpty() ) {
            plugin.sendMsg(sender, Msg.PLAYER_NOT_GIVEN.get());
            return null;
        }
        
        // Get the target player.
        Player target = getPlayer( player );
        
        // If there's no target,
        if ( target == null ) {
            plugin.sendMsg(sender, Msg.PLAYER_NOT_EXISTS.get(player));
            return null;
        }
        
        return target;
    }
    private int validateAmount (CommandSender sender, String value) {
        // If no amount was given,
        if ( value.isEmpty() ) {
            plugin.sendMsg(sender, Msg.AMOUNT_NOT_GIVEN.get());
            return -1;
        }
        
        // Get the amount.
        int amount = Integer.parseInt(value);
        
        // If the amount is <= zero,
        if ( amount <= 0 ) {
            plugin.sendMsg(sender, Msg.AMOUNT_INVALID.get(value));
            return -1;
        }
        
        // Success!
        return amount;
    }
    
    private boolean contains (String str, String command) {
        return command.toLowerCase().contains(str.toLowerCase());
    }
    private Player getPlayer (String name) {
        return server.getPlayer(name);
    }
}
