package de.xzise.bukkit.voteik;

import java.util.HashMap;
import java.util.Map;

import de.xzise.MinecraftUtil;

public enum VoteResult {
    PRO("pro", "+"),
    CONTRA("contra", "-"),
    ABSTENTION("absention", "|");
    
    private String[] names;
    
    private static final Map<String, VoteResult> RESULTS = new HashMap<String, VoteResult>();
    
    private VoteResult(String name, String... names) {
        this.names = MinecraftUtil.concat(name, names);
    }
    
    public String[] getNames() {
        return this.names.clone();
    }
    
    public static VoteResult getVoteResult(String name) {
        return RESULTS.get(name.toLowerCase());
    }
    
    static {
        for (VoteResult result : VoteResult.values()) {
            for (String name : result.names) {
                RESULTS.put(name.toLowerCase(), result);   
            }
        }
    }
}