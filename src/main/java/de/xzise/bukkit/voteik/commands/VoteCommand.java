package de.xzise.bukkit.voteik.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xzise.MinecraftUtil;
import de.xzise.bukkit.voteik.Vote;
import de.xzise.bukkit.voteik.VoteManager;
import de.xzise.bukkit.voteik.VoteResult;
import de.xzise.commands.CommonHelpableSubCommand;
/* Temporary? */
public class VoteCommand extends CommonHelpableSubCommand {

    private final VoteManager manager;
    
    public VoteCommand(VoteManager manager) {
        super("vote");
        this.manager = manager;
    }
    
    public String[] getFullHelpText() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getSmallHelpText() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getCommand() {
        return "vote vote <id> <vote>";
    }

    public boolean execute(CommandSender sender, String[] parameters) {
        if (parameters.length == 3) {
            if (sender instanceof Player) {
                Integer id = MinecraftUtil.tryAndGetInteger(parameters[1]);
                if (id != null) {
                    Vote vote = this.manager.getVote(id);
                    if (vote != null) {
                        VoteResult result = VoteResult.getVoteResult(parameters[2]);
                        if (result != null) {
                            if (vote.vote((Player) sender, result)) {
                                sender.sendMessage(ChatColor.GREEN + "Successfully voted for #" + id + "!");
                            } else {
                                sender.sendMessage(ChatColor.RED + "You already voted.");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Invalid vote.");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "There is no vote with id " + id);
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "The vote id has to be an integer.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Only player can vote.");
            }
            return true;
        } else {
            return false;
        }
    }

}
