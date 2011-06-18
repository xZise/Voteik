package de.xzise.bukkit.voteik;

import de.xzise.wrappers.permissions.Permission;

public enum PermissionValues implements Permission<Boolean> {
    
    VOTE("vote.vote", true),
    START("vote.start", false);
    ;
    
    public final boolean def;
    public final String name;
    
    private PermissionValues(String name, boolean def) {
        this.def = def;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Boolean getDefault() {
        return this.def;
    }

}
