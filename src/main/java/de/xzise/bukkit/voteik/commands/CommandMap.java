package de.xzise.bukkit.voteik.commands;

import java.util.ArrayList;
import java.util.List;

import de.xzise.bukkit.voteik.Voteik;
import de.xzise.commands.CommonCommandMap;
import de.xzise.commands.CommonHelpCommand;
import de.xzise.commands.HelpCommand;
import de.xzise.commands.SubCommand;

public class CommandMap extends CommonCommandMap {

    public CommandMap(Voteik plugin) {
        super();
        
        HelpCommand helper = new CommonHelpCommand("Voteik");
        SubCommand def = new VoteCommand(plugin); 
        
        List<SubCommand> commands = new ArrayList<SubCommand>();
        commands.add(def);
        commands.add(helper);
        commands.add(new BarCommand(plugin.getManager()));
        commands.add(new VoteStartCommand(plugin));
        commands.add(new VoteViewCommand(plugin.getManager()));
        commands.add(new VoteListCommand(plugin.getManager()));
        
        this.populate(commands);
        this.setHelper(helper);
        this.setDefault(def);
    }
    
}
