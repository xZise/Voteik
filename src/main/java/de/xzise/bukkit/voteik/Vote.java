package de.xzise.bukkit.voteik;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.EnumMap;
import java.util.Set;
import java.util.Map;
import java.util.TreeSet;

import org.bukkit.entity.Player;

public class Vote {
    
    private static int nextId = 0;
    
    private final int id;
    private final String question;
    private final String creator;
    private final Set<String> voted;
    private final Map<VoteResult, MutableInteger> votes;
    private final Date finalDate;
    
    public Vote(String question, String creator, Calendar finalDate) {
        this.question = question;
        this.creator = creator;
        this.voted = new TreeSet<String>();
        this.votes = new EnumMap<VoteResult, MutableInteger>(VoteResult.class);
        this.finalDate = finalDate == null ? null : finalDate.getTime();
        this.id = nextId++;
    }
    
    public Vote(String question, String creator, Calendar finalDate, Set<String> voted, Map<VoteResult, ? extends Number> votes, int id) {
        this.question = question;
        this.creator = creator;
        this.finalDate = finalDate == null ? null : finalDate.getTime();
        this.voted = new HashSet<String>(voted);
        this.votes = new EnumMap<VoteResult, MutableInteger>(VoteResult.class);
        for (VoteResult result : VoteResult.values()) {
            this.votes.put(result, new MutableInteger(getNumber(votes.get(result))));
        }
        this.id = id;
        if (this.id >= nextId) {
            nextId++;
        }
    }
    
    private static int getNumber(Number number, int def) {
        return number == null ? def : number.intValue();
    }
    
    private static int getNumber(Number number) {
        return getNumber(number, 0);
    }
    
    public boolean vote(Player player, VoteResult result) {
        if (!this.expired() && this.voted.add(player.getName())) {
            MutableInteger integer = this.votes.get(result);
            if (integer == null) {
                integer = new MutableInteger(0);
                this.votes.put(result, integer);
            }
            integer.inc();
            return true;
        } else {
            return false;
        }
    }
    
    public int getVoteCount() {
        int count = 0;
        for (Number number : this.votes.values()) {
            count += number.intValue();
        }
        return count;
    }
    
    public int getVoteCount(VoteResult result) {
        return getNumber(this.votes.get(result));
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
    
    public String getCreator() {
        return this.creator;
    }
    
    public Date getFinalDate() {
        return this.finalDate;
    }
    
    public boolean expired() {
        return this.finalDate == null ? false : this.finalDate.before(new Date());
    }
    
}
