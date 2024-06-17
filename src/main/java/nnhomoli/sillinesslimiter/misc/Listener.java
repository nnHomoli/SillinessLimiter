package nnhomoli.sillinesslimiter.misc;

import nnhomoli.sillinesslimiter.IPLock;
import nnhomoli.sillinesslimiter.data.converter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.Arrays;

import static nnhomoli.sillinesslimiter.IPLock.lang;

public class Listener implements org.bukkit.event.Listener {
    private final IPLock plugin;
    private final ArrayList<Permission> perms = new ArrayList<>(Arrays.asList(new Permission("nnhomoli.sillinesslimiter.cmds.sillyunlimit"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillylimit"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillyconfirm"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillydeny"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillylist"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillyswitch"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillydynamiclimit"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillydynamicunlimit"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillyhelp")));

    public Listener(IPLock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onLogin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if(this.plugin.getConfig().getBoolean("Permission-by-default")) {
            PermissionAttachment at = p.addAttachment(IPLock.getPlugin(IPLock.class));
            this.perms.forEach(per -> {
                at.setPermission(per, true);
            });
            p.updateCommands();
        }
        if(this.plugin.getConfig().getBoolean("Login-link-message")) {
            if(IPLock.pdata.getList(p.getName()) == null && IPLock.pdata.get(p.getName() + ";dynamic") == null ||
                    !IPLock.pdata.isEnabled(p.getName())) p.sendMessage(lang.get("login_link_message"));
        }
    }

    @EventHandler
    private void preLogin(AsyncPlayerPreLoginEvent event) {
        String p = event.getName();

        converter.convert(p, this.plugin);

        if(IPLock.pdata.isEnabled(p)) {
            if (IPLock.checkIP(p, event.getAddress().getHostAddress())) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, lang.get("kick_reason"));
            }
        }
    }
}
