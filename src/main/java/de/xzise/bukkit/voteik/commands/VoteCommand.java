package de.xzise.bukkit.voteik.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xzise.MinecraftUtil;
import de.xzise.bukkit.voteik.PermissionValues;
import de.xzise.bukkit.voteik.Vote;
import de.xzise.bukkit.voteik.VoteManager;
import de.xzise.bukkit.voteik.VoteResult;
import de.xzise.bukkit.voteik.Voteik;
import de.xzise.commands.CommonHelpableSubCommand;
import de.xzise.wrappers.permissions.PermissionsHandler;
/* Temporary "vote vote"? */
public class VoteCommand extends CommonHelpableSubCommand {

    private final VoteManager manager;
    private final PermissionsHandler permissions;
    
    public VoteCommand(Voteik plugin) {
        super("vote");
        this.manager = plugin.getManager();
        this.permissions = plugin.getPermissions();
    }
    
    public String[] getFullHelpText() {
        // Vote Results?
        return new String[] { "Votes for a specific vote." };
    }

    public String getSmallHelpText() {
        return "Set a vote";
    }

    public String getCommand() {
        return "vote vote <id> <vote>";
    }

    public boolean execute(CommandSender sender, String[] parameters) {
        if (parameters.length == 3) {
            if (sender instanceof Player) {
                if (this.permissions.permission(sender, PermissionValues.VOTE)) {
                    Integer id = MinecraftUtil.tryAndGetInteger(parameters[1]);
                    if (id != null) {
                        Vote vote = this.manager.getVote(id);
                        if (vote != null) {
                            VoteResult result = VoteResult.getVoteResult(parameters[2]);
                            if (result != null) {
                                if (vote.voted(((Player) sender).getName())) {
                                    sender.sendMessage(ChatColor.RED + "You already voted.");
                                } else if (vote.expired()) {
                                    sender.sendMessage(ChatColor.RED + "This vote is already expired.");
                                } else {
                                    vote.vote((Player) sender, result);
                                    sender.sendMessage(ChatColor.GREEN + "Successfully voted for #" + id + "!");
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
                    sender.sendMessage(ChatColor.RED + "You have no permission to vote.");
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
