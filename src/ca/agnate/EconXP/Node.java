package ca.agnate.EconXP;

public enum Node {
    ADD ("add"),
    SUBTRACT ("subtract"),
    BALANCE ("balance"),
    CLEAR ("clear"),
    VERSION ("version");

    private String perm;
    private String prefix = "econxp.";

    private Node (String perm) {
    	this.perm = perm;
    }

    public String toString () {
    	return( prefix + this.perm );
    }
}
