package ca.agnate.EconXP;

import java.util.LinkedList;
import java.util.List;

public enum Node {
    ADD ("add"),
    SUBTRACT ("subtract"),
    BALANCE ("balance"),
    BALANCESELF ("balanceself"),
    CLEAR ("clear"),
    VERSION ("version"),
    SET ("set"),
    GIVE ("give"),
    TRANSFER ("transfer"),
    MULTIPLY ("multiply"),
    DIVIDE ("divide");
    
    private static List<Node> all; 
    
    static {
    	// Create a list of all the Nodes.
    	all = new LinkedList<Node> ();
    	all.add(Node.ADD);
    	all.add(Node.SUBTRACT);
    	all.add(Node.BALANCE);
    	all.add(Node.BALANCESELF);
    	all.add(Node.CLEAR);
    	all.add(Node.VERSION);
    	all.add(Node.SET);
    	all.add(Node.GIVE);
    	all.add(Node.TRANSFER);
    	all.add(Node.MULTIPLY);
    	all.add(Node.DIVIDE);
    }
    
    private String perm;
    private String prefix = "econxp.";

    private Node (String perm) {
    	this.perm = perm;
    }

    public String toString () {
    	return( prefix + this.perm );
    }
    
    public static List<Node> allNodes () {
    	return all;
    }
    
    public static List<Node> cloneList ( List<Node> list ) {
    	List<Node> newList = new LinkedList<Node> ();
    	
    	for (Node item : list) {
			newList.add( item );
		}
    	
    	return newList;
    }
}
