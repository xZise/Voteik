package de.xzise.bukkit.voteik;

import java.util.HashSet;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

import org.bukkit.entity.Player;

public class Vote {

    private static int nextId = 0;
    
    private final int id;
    private final String question;
    private final Set<String> voted;
    private final Map<VoteResult, Integer> votes;
    
    public Vote(String question) {
        this.question = question;
        this.voted = new HashSet<String>();
        Map<VoteResult, Integer> votes;
        try {
            votes = new EnumMap<VoteResult, Integer>(VoteResult.class);
        } catch (ExceptionInInitializerError e) {
            votes = new HashMap<VoteResult, Integer>();
            Voteik.getLogger().info("Couldn't create map with EnumMap class. Nothing broken, only for info.");
        }
        this.votes = votes;
        this.id = nextId++;
        System.out.println("new id: " + this.id);
    }
    
    public Vote(String question, Set<String> voted, Map<VoteResult, Integer> votes, int id) {
        this.question = question;
        this.voted = new HashSet<String>(voted);
        this.votes = new EnumMap<VoteResult, Integer>(votes);
        this.id = id;
        if (this.id >= nextId) {
            nextId++;
        }
    }
    
    private static int getInt(Integer integer, int def) {
        return integer == null ? def : integer;
    }
    
    private static int getInt(Integer integer) {
        return getInt(integer, 0);
    }
    
    public boolean vote(Player player, VoteResult result) {
        if (this.voted.add(player.getName())) {
            int voteCnt = getInt(this.votes.get(result)) + 1;
            this.votes.put(result, voteCnt);
            return true;
        } else {
            return false;
        }
    }
    
    public int getVoteCount() {
        return this.voted.size();
    }
    
    public int getVoteCount(VoteResult result) {
        return getInt(this.votes.get(result), 0);
    }
    
    public boolean voted(String playerName) {
        return this.voted.contains(playerName);
    }

    public int getId() {
        return this.id;
    }
    
    public String getQuestion() {
        return this.question;
    }
    
}
