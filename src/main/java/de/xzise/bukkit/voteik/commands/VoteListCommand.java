package de.xzise.bukkit.voteik.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.xzise.MinecraftUtil;
import de.xzise.bukkit.voteik.Vote;
import de.xzise.bukkit.voteik.VoteManager;
import de.xzise.commands.CommonHelpableSubCommand;

public class VoteListCommand extends CommonHelpableSubCommand {

    private final VoteManager manager;
    
    public VoteListCommand(VoteManager manager) {
        super("list", "ls");
        this.manager = manager;
    }
    
    public String[] getFullHelpText() {
        return new String[] { "List the votes." };
    }

    public String getSmallHelpText() {
        return "List votes";
    }

    public String getCommand() {
        return "vote list [#page]";
    }

    private static String truncate(String string, int charCount, String truncAppendix) {
        if (string.length() <= charCount) {
            return string;
        } else {
            int truncLength = truncAppendix.length();
            return string.substring(0, charCount - truncLength) + truncAppendix;
        }
    }
    
    public boolean execute(CommandSender sender, String[] parameters) {
        Integer page = null;
        if (parameters.length == 2) {
            page = MinecraftUtil.tryAndGetInteger(parameters[1]);
        }
        
        if (parameters.length == 1 || page != null) {
            page = page == null ? 1 : page;
            
            int maxiumumLines = MinecraftUtil.getMaximumLines(sender) - 1;
            
            List<Vote> votes = this.manager.getSortedVotes((page - 1) * maxiumumLines, maxiumumLines);
            
            if (votes.size() == 0) {
                sender.sendMessage(ChatColor.RED + "No votes to list.");
            } else {
                int idWith = -1;
                for (Vote vote : votes) {
                    idWith = Math.max(MinecraftUtil.getWidth(vote.getId(), 10), idWith);
                }
                
                // 40 is width ingame, subtract 1 for the # and 1 for the space
                // #<id>␣<question>
                int questionWidth = 40 - idWith - 1 - 1;
                
                sender.sendMessage("Votes:");
                for (Vote vote : votes) {
                    String question = truncate(vote.getQuestion(), questionWidth, "...");
                    char[] zeros = new char[idWith - MinecraftUtil.getWidth(vote.getId(), 10)];
                    Arrays.fill(zeros, '0');
                    sender.sendMessage("#" + new String(zeros) + vote.getId() + " " + question);
                }
            }
            
            return true;
        } else {
            return false;
        }
    }

}
