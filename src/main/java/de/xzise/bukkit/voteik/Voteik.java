package de.xzise.bukkit.voteik;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.java.JavaPlugin;

import de.xzise.MinecraftUtil;
import de.xzise.XLogger;
import de.xzise.bukkit.voteik.commands.CommandMap;
import de.xzise.wrappers.permissions.PermissionsHandler;

public class Voteik extends JavaPlugin {
    
    private CommandMap commands;
    private XLogger logger;
    private PermissionsHandler permissions;
    private VoteManager manager;
    
    private static Voteik instance;
    
    public Voteik() {
        instance = this;
    }
    
    public void onDisable() {
        this.logger.disableMsg();
    }
    
    public VoteManager getManager() {
        return this.manager;
    }

    public void onEnable() {
        this.logger = new XLogger(this);
        
        this.permissions = new PermissionsHandler(this.getServer().getPluginManager(), "", this.logger);
        ServerListener serverListener = new ServerListener() {
            public void onPluginEnable(PluginEnableEvent event) {
                Voteik.this.permissions.load(event.getPlugin());
            }

            public void onPluginDisable(PluginDisableEvent event) {
                Voteik.this.permissions.unload(event.getPlugin());
            }
        };
        
        this.manager = new VoteManager();
        
        this.commands = new CommandMap(this);
        
        this.getServer().getPluginManager().registerEvent(Type.PLUGIN_ENABLE, serverListener, Priority.Low, this);
        this.getServer().getPluginManager().registerEvent(Type.PLUGIN_DISABLE, serverListener, Priority.Low, this);
        
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

    public PermissionsHandler getPermissions() {
        return this.permissions;
    }
    
}
