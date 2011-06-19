package de.xzise.bukkit.voteik.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.xzise.MinecraftUtil;
import de.xzise.bukkit.voteik.Vote;
import de.xzise.bukkit.voteik.VoteManager;
import de.xzise.bukkit.voteik.VoteResult;
import de.xzise.commands.CommonHelpableSubCommand;

public class BarCommand extends CommonHelpableSubCommand {

    public VoteManager manager;
    
    public BarCommand(VoteManager manager) {
        super("bar");
        this.manager = manager;
    }
    
    @Override
    public String[] getFullHelpText() {
        return new String[] { "Shows a colored bar as result." };
    }

    @Override
    public String getSmallHelpText() {
        return "Result as colored bar.";
    }

    @Override
    public String getCommand() {
        return "vote bar <id>";
    }
    
    public static String getBar(int count) {
        char[] chars = new char[count];
        Arrays.fill(chars, '|');
        return new String(chars);
    }
    
    public static String getBar(Vote vote, VoteResult result, int total, int width) {
        return result.getColor() + getBar(Math.round((vote.getVoteCount(result) / (float) total) * width));
    }
    
    public static int getPercentage(Vote vote, VoteResult result, int total) {
        return Math.round((vote.getVoteCount(result) / (float) total) * 100);
    }
    
    /**
     * Get the bar f
     * @param vote
     * @param result
     * @return
     */
    public static String getBar(Vote vote, VoteResult result, int width) {
        return getBar(vote, result, vote.getVoteCount(), width);
    }
    
    public static String getBar(int value, int total, int max) {
        return getBar(Math.round(value / (float) max * 95)) + ChatColor.WHITE + " " + Math.round(value / (float) total * 100) + "%";
    }

    @Override
    public boolean execute(CommandSender sender, String[] parameters) {
        // 1 pipe = 1 percent
        if (parameters.length == 2) {
            Integer id = MinecraftUtil.tryAndGetInteger(parameters[1]);
            if (id != null) {
                Vote vote = this.manager.getVote(id);
                if (vote != null) {
                    sender.sendMessage("Vote #" + id + " by "+ vote.getCreator());
                    
                    int total = vote.getVoteCount();
                    if (total == 0) {
                        sender.sendMessage(ChatColor.GRAY + getBar(100));
                    } else {
                        int pro = vote.getVoteCount(VoteResult.PRO);
                        int contra = vote.getVoteCount(VoteResult.CONTRA);
                        int abstention = vote.getVoteCount(VoteResult.ABSTENTION);
                        int max = max(pro, contra, abstention);
                        sender.sendMessage(ChatColor.GREEN + getBar(pro, total, max));
                        sender.sendMessage(ChatColor.RED + getBar(contra, total, max));
                        sender.sendMessage(ChatColor.YELLOW + getBar(abstention, total, max));
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
    
    public static int max(int a, int... b) {
        int maximum = a;
        for (int i : b) {
            if (i > maximum) {
                maximum = i;
            }
        }
        return maximum;
    }

}
