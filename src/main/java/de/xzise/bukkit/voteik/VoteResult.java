package de.xzise.bukkit.voteik;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;

import de.xzise.MinecraftUtil;

public enum VoteResult {
    PRO(ChatColor.GREEN, "pro", "+"),
    CONTRA(ChatColor.RED, "contra", "-"),
    ABSTENTION(ChatColor.YELLOW, "abstention", "|");
    
    private final String[] names;
    private final ChatColor color;
    
    private static final Map<String, VoteResult> RESULTS = new HashMap<String, VoteResult>();
    
    private VoteResult(ChatColor color, String name, String... names) {
        this.names = MinecraftUtil.concat(name, names);
        this.color = color;
    }
    
    public String[] getNames() {
        return this.names.clone();
    }
    
    public ChatColor getColor() {
        return this.color;
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