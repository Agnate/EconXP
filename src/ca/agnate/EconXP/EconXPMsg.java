package ca.agnate.EconXP;

public class EconXPMsg {
    public static enum Msg {
        COMMAND_NOT_ALLOWED ("I can't let you do that, %", "I can't let you do that, %"),
        COMMAND_UNHANDLED ("Command '%' was not processed.", "Command '%' was not processed."),
        PLAYER_NOT_GIVEN ("You did not pick a player.", "Pick a player."),
        PLAYER_NOT_EXISTS ("Player '%' does not exist.", "Player '%' does not exist."),
        PLAYER_BALANCE ("% balance is @ exp.", "% balance is @ exp."),
        PLAYER_CLEAR ("% was cleared of @ exp.", "% was cleared of @ exp."),
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
            return (s != null) ? msg.replace("%", s) : msg;
        }
        
        public String get(String s, String s2) {
            return (s != null) ? msg.replace("%", s).replace("@", s2) : msg;
        }
        
        public String getSpout(String s) {
            if (spoutMsg == null)
                return get(s);
            
            return (s != null) ? spoutMsg.replace("%", s) : spoutMsg;
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
