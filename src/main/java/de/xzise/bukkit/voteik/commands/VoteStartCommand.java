package de.xzise.bukkit.voteik.commands;

import org.bukkit.command.CommandSender;

import de.xzise.bukkit.voteik.VoteManager;
import de.xzise.commands.CommonHelpableSubCommand;

public class VoteStartCommand extends CommonHelpableSubCommand {

    private final VoteManager manager;
    
    public VoteStartCommand(VoteManager manager) {
        super("start");
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
        return "vote start <question>";
    }

    public boolean execute(CommandSender sender, String[] parameters) {
        if (parameters.length == 2) {
            this.manager.startVote(parameters[1]);
            return true;
        } else {
            return false;
        }
    }

}
