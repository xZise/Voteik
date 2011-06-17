package de.xzise.bukkit.voteik;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import de.xzise.MinecraftUtil;
import de.xzise.XLogger;
import de.xzise.bukkit.voteik.commands.CommandMap;

public class Voteik extends JavaPlugin {
    
    private CommandMap commands;
    private XLogger logger;
    
    private static Voteik instance;
    
    public Voteik() {
        instance = this;
    }
    
    public void onDisable() {
        this.logger.disableMsg();
    }

    public void onEnable() {
        this.logger = new XLogger(this);
        
        // Only to say Java: Hey there! There is an enum !
//        VoteResult result = VoteResult.ABSTENTION;
        
        VoteManager manager = new VoteManager();
        
        this.commands = new CommandMap(manager);
        
        this.logger.enableMsg();
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
     // workaround until I get the complete line or a parsed one
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            line.append(arg);
            if (i < args.length - 1) {
                line.append(' ');
            }
        }
        
        return this.commands.executeCommand(sender, MinecraftUtil.parseLine(line.toString()));
    }

    public static XLogger getLogger() {
        return instance.logger;
    }
    
}
