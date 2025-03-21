package nnhomoli.sillinesslimiter.misc;


import nnhomoli.sillinesslimiter.data.userdata;
import nnhomoli.sillinesslimiter.lang.LangLoader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public final class Listener implements org.bukkit.event.Listener {
    private final ArrayList<Permission> perms = new ArrayList<>(Arrays.asList(new Permission("nnhomoli.sillinesslimiter.cmds.sillyunlimit"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillylimit"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillyconfirm"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillydeny"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillylist"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillyswitch"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillydynamiclimit"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillydynamicunlimit"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillyhelp")));

    private final JavaPlugin plugin;
    private final LangLoader lang;
    private final userdata user;
    public Listener(JavaPlugin plugin,LangLoader l,userdata u) {
        this.plugin = plugin;
        this.lang = l;
        this.user = u;
    }


    @EventHandler
    private void onLogin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if(this.plugin.getConfig().getBoolean("Permission-by-default")) {
            PermissionAttachment at = p.addAttachment(plugin);
            this.perms.forEach(per -> at.setPermission(per, true));
            p.updateCommands();
        }
        if(this.plugin.getConfig().getBoolean("Login-link-message")) {
            if(user.getList(p.getName()) == null && user.getDynamicIP(p.getName()) == null ||
                    !user.isEnabled(p.getName())) p.sendMessage(lang.getString("login_link_message"));
        }
    }

    @EventHandler
    private void preLogin(AsyncPlayerPreLoginEvent event) {
        String p = event.getName();

        user.convert(p);

        if(user.isEnabled(p)) {
            if (user.disallowAddress(p, event.getAddress().getHostAddress())) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, lang.getString("kick_reason"));
            }
        }
    }
}
