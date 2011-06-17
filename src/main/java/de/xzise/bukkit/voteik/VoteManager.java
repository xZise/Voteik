package de.xzise.bukkit.voteik;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteManager {

    private final Map<Integer, Vote> votes;
    
    public VoteManager() {
        this.votes = new HashMap<Integer, Vote>();
        
        //TODO: load votes
    }
    
    public Vote startVote(String question) {
        Vote vote = new Vote(question);
        this.votes.put(vote.getId(), vote);
        return vote;
    }
    
    public Vote getVote(int id) {
        return this.votes.get(id);
    }
    
    public Vote[] getVotes() {
        return this.votes.values().toArray(new Vote[0]);
    }
    
    public List<Vote> getSortedVotes(int start, int count) {
        List<Vote> ret = new ArrayList<Vote>(count);
        List<Vote> names = new ArrayList<Vote>(this.votes.values());

        final Collator collator = Collator.getInstance();
        collator.setStrength(Collator.SECONDARY);
        Collections.sort(names, new Comparator<Vote>() {

            public int compare(Vote o1, Vote o2) {
                return new Integer(o1.getId()).compareTo(o2.getId());
            }
            
        });

        int size = names.size();
        int index = start;
        int currentCount = index;
        while (index < size && count > 0) {
            Vote vote = names.get(index);
            if (currentCount >= start) {
                ret.add(vote);
                count--;
            } else {
                currentCount++;
            }
            index++;
        }
        return ret;
    }
    
}
