package de.xzise.bukkit.voteik.commands;

import java.util.Calendar;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.xzise.MinecraftUtil;
import de.xzise.bukkit.voteik.PermissionValues;
import de.xzise.bukkit.voteik.Vote;
import de.xzise.bukkit.voteik.VoteManager;
import de.xzise.bukkit.voteik.Voteik;
import de.xzise.commands.CommonHelpableSubCommand;
import de.xzise.wrappers.permissions.PermissionsHandler;

public class VoteStartCommand extends CommonHelpableSubCommand {

    private final VoteManager manager;
    private final PermissionsHandler permissions;
    
    public VoteStartCommand(Voteik plugin) {
        super("start");
        this.manager = plugin.getManager();
        this.permissions = plugin.getPermissions();
    }
    
    public String[] getFullHelpText() {
        return new String[] { "Starts a new vote." , "If the vote should expire in some days, define the days parameter." };
    }

    public String getSmallHelpText() {
        return "Start a vote";
    }

    public String getCommand() {
        return "vote start <question> [days]";
    }

    public boolean execute(CommandSender sender, String[] parameters) {
        boolean handled = false;
        Calendar finalDate = null;
        if (parameters.length == 3) {
            Integer days = MinecraftUtil.tryAndGetInteger(parameters[2]);
            if (days != null) {
                finalDate = Calendar.getInstance();
                finalDate.add(Calendar.DAY_OF_YEAR, days);
            } else {
                sender.sendMessage(ChatColor.RED + "You have to specify the number of days until finish.");
                handled = true;
            }
        }
        if (finalDate != null || parameters.length == 2) {
            if (permissions.permission(sender, PermissionValues.START)) {
                String creator = MinecraftUtil.getName(sender, "Console", null);
                if (creator != null) {
                    Vote vote = this.manager.startVote(parameters[1], creator, finalDate);
                    sender.sendMessage("Sucessfully created vote #" + ChatColor.GREEN + vote.getId() + ChatColor.WHITE + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "You need a name, if you want to create a vote.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You haven't the permission to start a vote.");
            }
            return true;
        } else {
            return handled;
        }
    }

}
