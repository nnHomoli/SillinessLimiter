package justaplugin.sillinesslimiter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class IPLock extends JavaPlugin implements Listener {
    public static HashMap<Player, String> confirmations = new HashMap<>();
    HashMap<UUID, PermissionAttachment> attachments = new HashMap<>();
    ArrayList<Permission> perms = new ArrayList<>(Arrays.asList(new Permission("justaplugin.sillinesslimiter.sillylimit"),
            new Permission("justaplugin.sillinesslimiter.sillyunlimit"),
            new Permission("justaplugin.sillinesslimiter.sillyconfirm"),
            new Permission("justaplugin.sillinesslimiter.sillymove"),
            new Permission("justaplugin.sillinesslimiter.sillydeny")));

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("silly-limit").setExecutor(new limitsilly());
        this.getCommand("silly-unlimit").setExecutor(new unlimitsilly());
        this.getCommand("silly-confirm").setExecutor(new sillyconfirm());
        this.getCommand("silly-deny").setExecutor(new sillydeny());
        this.getCommand("silly-move").setExecutor(new movesilly());
        this.getCommand("silly-reload").setExecutor(new sillyreload());

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(this.getConfig().getBoolean("Permission-by-default")) {
            PermissionAttachment at = player.addAttachment(this);
            perms.forEach(per -> {
                at.setPermission(per, true);
            });
            player.updateCommands();
        }

        if(this.getConfig().get(player.getName()) == null) {
            if(this.getConfig().getBoolean("Login-link-message")) player.sendMessage(ChatColor.RED + "Your ip is not linked, your name might be at risk, use /silly-limit to protect yourself");
        }
        else if(!Objects.equals(this.getConfig().get(player.getName()), player.getAddress().getAddress().getHostAddress())) {
                event.setJoinMessage(null);
                player.kickPlayer(ChatColor.RED + "trying to limit your silliness\n\n" + ChatColor.AQUA + " you joined on account with other ip already linked to it");
            }
    }

    @Override
    public void onLoad() {
        // Plugin load logic
        if(this.getConfig().get("Permission-by-default") == null) this.getConfig().set("Permission-by-default", true);
        if(this.getConfig().get("Login-link-message") == null) this.getConfig().set("Login-link-message", true);
        this.saveConfig();
        this.reloadConfig();

        this.getLogger().info("Silliness limiter Has been loaded");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
