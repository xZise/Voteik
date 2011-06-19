package de.xzise.bukkit.voteik;

import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import de.xzise.MinecraftUtil;

public class VoteManager {

    private final Map<Integer, Vote> votes;
    
    public VoteManager(File config) {
        this.votes = new HashMap<Integer, Vote>();
        
        //TODO: load votes
        Configuration votesFile = new Configuration(new File(config, "votes.yml"));
        votesFile.load();
//        Object o = votesFile.getProperty("votes");
//        System.out.println(o);
//        for (String s : votesFile.getKeys("votes")) {
//            System.out.println(s);
//        }
        Map<String, ConfigurationNode> voteNodes = votesFile.getNodes("votes");
        for (Entry<String, ConfigurationNode> voteEntry : voteNodes.entrySet()) {
            Integer id = MinecraftUtil.tryAndGetInteger(voteEntry.getKey());
            if (id != null) {
                ConfigurationNode voteNode = voteEntry.getValue();
                String creator = voteNode.getString("creator");
                String question = voteNode.getString("question");
                List<String> votedList = voteNode.getStringList("voted", new ArrayList<String>(0));
                Set<String> voted = new HashSet<String>(votedList);
                long ms = getLong(voteNode, "final", -1);
                Calendar finalDate = null;
                if (ms >= 0) {
                    finalDate = Calendar.getInstance();
                    finalDate.setTimeInMillis(ms);
                }
                Map<VoteResult, Integer> votes = new EnumMap<VoteResult, Integer>(VoteResult.class);
                List<String> resultKeys = voteNode.getKeys("result");
                for (String key : resultKeys) {
                    VoteResult result = VoteResult.getVoteResult(key);
                    if (result != null) {
                        votes.put(result, voteNode.getInt("result." + key, 0));
                    } else {
                        //
                    }
                }
                this.addVote(new Vote(question, creator, finalDate, voted, votes, id));
            } else {
                //
            }
        }
    }
    
    private static long getLong(ConfigurationNode node, String path, long def) {
        Object o = node.getProperty(path);
        if (o instanceof Long) {
            return (Long) o;
        } else {
            if (o == null) {
                return def;
            } else {
                return node.getInt(path, 0);
            }
        }
    }
    
    private Vote addVote(Vote vote) {
        this.votes.put(vote.getId(), vote);
        return vote;
    }
    
    public Vote startVote(String question, String creator, Calendar finalDate) {
        return this.addVote(new Vote(question, creator, finalDate));
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
