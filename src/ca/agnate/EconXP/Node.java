package ca.agnate.EconXP;

public enum Node {
    GIVE ("give"),
    TAKE ("take"),
    VIEW ("view"),
    CLEAR ("clear");

    private String perm;
    private String prefix = "econxp.";

    private Node (String perm) {
    	this.perm = perm;
    }

    public String toString () {
    	return( prefix + this.perm );
    }
}
