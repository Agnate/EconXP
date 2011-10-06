package ca.agnate.EconXP;

public class EconXPMsg {
    public static enum Msg {
        COMMAND_NOT_ALLOWED ("I can't let you do that, %.", "I can't let you do that, %."),
        COMMAND_NOT_FOUND ("Command not found.", "Command not found."),
        COMMAND_UNHANDLED ("Command '%' was not processed.", "Command '%' was not processed."),
        COMMAND_CONSOLE_GIVE_NOT_ALLOWED ("Use the transfer command to transfer between players via the console.", "Use the transfer command to transfer between players via the console."),
        COMMAND_CONSOLE_BALANCESELF_NOT_ALLOWED ("Console cannot use 'balanceself'.", "Console cannot use 'balanceself'."),
        PLAYER_NOT_GIVEN ("You did not pick a player.", "Pick a player."),
        PLAYER_NOT_EXISTS ("Player '%' does not exist.", "Player '%' does not exist."),
        PLAYER_BALANCE ("%'s balance is @ exp.", "%'s balance is @ exp."),
        PLAYER_BALANCESELF ("Your balance is @ exp.", "Your balance is @ exp."),
        PLAYER_CLEAR ("% was cleared of @ exp.", "% was cleared of @ exp."),
        PLAYER_ADD ("% was given @ exp.", "% was given @ exp."),
        PLAYER_SUBTRACT ("% has lost @ exp.", "% has lost @ exp."),
        PLAYER_SET ("% was set to @ exp.", "% was set to @ exp."),
        PLAYER_GIVE("You gave % @ exp.", "You gave % @ exp."),
        PLAYER_TRANSFER("% gave ^ @ exp.", "% gave ^ @ exp."),
        PLAYER_MULTIPLY("%'s exp was multiplied by @, equaling ^.", "%'s exp was multiplied by @, equaling ^."),
        PLAYER_DIVIDE("%'s exp was divided by @, equaling ^.", "%'s exp was divided by @, equaling ^."),
        AMOUNT_NOT_GIVEN ("You did not pick an amount.", "Pick an amount."),
        AMOUNT_INVALID ("The amount is invalid.", "The amount is invalid.");
        
        private String msg, spoutMsg;
        
        private Msg(String msg) {
            this(msg, null);
        }
        
        private Msg(String msg, String spoutMsg) {
            this.msg = msg;
            this.spoutMsg = spoutMsg;
        }
        
        public String get() {
            return msg;
        }
        public String get(String s) {
        	if ( s == null ) { return msg; }
            return get().replace("%", s);
        }
        public String get(String s, String s2) {
        	if ( s == null ) { return msg; }
        	if ( s2 == null ) { return get(s); }
            return get(s).replace("@", s2);
        }
        public String get(String s, String s2, String s3) {
        	if ( s == null ) { return msg; }
        	if ( s2 == null ) { return get(s); }
        	if ( s3 == null ) { return get(s, s2); }
            return get(s, s2).replace("^", s3);
        }
        
        public String getSpout() {
        	return (spoutMsg == null) ? get() : spoutMsg;
        }
        public String getSpout(String s) {
            if ( spoutMsg == null ) { return get(s); }
            if ( s == null ) { return getSpout(); }
            return spoutMsg.replace("%", s);
        }
        public String getSpout(String s, String s2) {
            if (spoutMsg == null) { return get(s, s2); }
            if ( s == null ) { return getSpout(); }
            if ( s2 == null ) { return getSpout(s); }
            return getSpout(s).replace("@", s2);
        }
        public String getSpout(String s, String s2, String s3) {
            if (spoutMsg == null) { return get(s, s2, s3); }
            if ( s == null ) { return getSpout(); }
            if ( s2 == null ) { return getSpout(s); }
            if ( s3 == null ) { return getSpout(s, s2); }
            return getSpout(s, s2).replace("^", s3);
        }
        
        public void set(String msg) {
            this.msg = msg;
        }
        
        public void setSpout(String spoutMsg) {
            this.spoutMsg = spoutMsg;
        }
        
        public boolean hasSpoutMsg() {
            return spoutMsg != null;
        }
        
        public static String get(Msg m) {
            return m.msg;
        }
        
        public static String get(Msg m, String s) {
            return m.msg.replace("%", s);   
        }
        
        public static void set(Msg m, String msg) {
            m.msg = msg;
        }
        
        public String toString() {
            return msg;
        }
    }
    
    
}
