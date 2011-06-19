package de.xzise.bukkit.voteik.commands;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.xzise.MinecraftUtil;
import de.xzise.bukkit.voteik.Vote;
import de.xzise.bukkit.voteik.VoteManager;
import de.xzise.bukkit.voteik.VoteResult;
import de.xzise.commands.CommonHelpableSubCommand;

public class VoteViewCommand extends CommonHelpableSubCommand {

    private final VoteManager manager;
    private final DateFormat formatter;
    
    public VoteViewCommand(VoteManager manager) {
        super("view");
        this.manager = manager;
        //TODO: Allow configurable formats
        this.formatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.LONG, Locale.ENGLISH);
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
                    int total = vote.getVoteCount();
                    result.append(total);
                    sender.sendMessage(result.toString());
                    
                    //@formatter:off
                    sender.sendMessage(
                        BarCommand.getBar(vote, VoteResult.PRO, total, 100) +
                        BarCommand.getBar(vote, VoteResult.CONTRA, total, 100) + 
                        BarCommand.getBar(vote, VoteResult.ABSTENTION, total, 100));
                    //@formatter:on

                    Date finalDate = vote.getFinalDate();
                    if (finalDate != null) {
                        String expirationString = "This vote runs until: " + this.formatter.format(finalDate);
                        if (vote.expired()) {
                            expirationString += " " + ChatColor.RED + "(expired)";
                        }
                        sender.sendMessage(expirationString);
                    } else {
                        sender.sendMessage("This vote has no expiration date.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "The id is not a valid vote.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "The id has to be an integer.");
            }
            
            return true;
        } else {
            return false;
        }
    }

}
