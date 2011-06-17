package de.xzise.bukkit.voteik.commands;

import java.util.ArrayList;
import java.util.List;

import de.xzise.bukkit.voteik.VoteManager;
import de.xzise.commands.CommonCommandMap;
import de.xzise.commands.CommonHelpCommand;
import de.xzise.commands.HelpCommand;
import de.xzise.commands.SubCommand;

public class CommandMap extends CommonCommandMap {

    public CommandMap(VoteManager manager) {
        super();
        
        HelpCommand helper = new CommonHelpCommand("Qukkiz");
        SubCommand def = new VoteCommand(manager); 
        
        List<SubCommand> commands = new ArrayList<SubCommand>();
        commands.add(def);
        commands.add(helper);
        commands.add(new VoteStartCommand(manager));
        commands.add(new VoteViewCommand(manager));
        commands.add(new VoteListCommand(manager));
        
        this.populate(commands);
        this.setHelper(helper);
        this.setDefault(def);
    }
    
}
