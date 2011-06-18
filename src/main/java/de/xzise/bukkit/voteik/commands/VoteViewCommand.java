package de.xzise.bukkit.voteik.commands;

import java.util.Calendar;

import org.bukkit.command.CommandSender;

import de.xzise.MinecraftUtil;
import de.xzise.bukkit.voteik.Vote;
import de.xzise.bukkit.voteik.VoteManager;
import de.xzise.bukkit.voteik.VoteResult;
import de.xzise.commands.CommonHelpableSubCommand;

public class VoteViewCommand extends CommonHelpableSubCommand {

    private final VoteManager manager;
    
    public VoteViewCommand(VoteManager manager) {
        super("view");
        this.manager = manager;
    }
    
    public String[] getFullHelpText() {
        return new String[] { "Shows the vote informations." };
    }

    public String getSmallHelpText() {
        return "Views a vote";
    }

    public String getCommand() {
        return "vote view <id>";
    }

    public boolean execute(CommandSender sender, String[] parameters) {
        if (parameters.length == 2) {
            Integer id = MinecraftUtil.tryAndGetInteger(parameters[1]);
            if (id != null) {
                Vote vote = this.manager.getVote(id);
                if (vote != null) {
                    sender.sendMessage("Vote #" + id + " by "+ vote.getCreator());
                    sender.sendMessage(vote.getQuestion());
                    StringBuilder result = new StringBuilder("Result: ");
                    for (VoteResult voteResult : VoteResult.values()) {
                        result.append(voteResult.getNames()[0]);
                        result.append(": ");
                        result.append(vote.getVoteCount(voteResult));
                        result.append(", ");
                    }
                    result.append("Total: ");
                    result.append(vote.getVoteCount());
                    sender.sendMessage(result.toString());
                    Calendar finalDate = vote.getFinalDate();
                    if (finalDate != null) {
                        sender.sendMessage("This vote runs until: " + finalDate.toString());
                    } else {
                        sender.sendMessage("This vote has no expiration date.");
                    }
                } else {
                    sender.sendMessage("The id is not a valid vote.");
                }
            } else {
                sender.sendMessage("The id has to be an integer.");
            }
            
            return true;
        } else {
            return false;
        }
    }

}
