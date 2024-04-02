package justaplugin.sillinesslimiter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class IPLock extends JavaPlugin implements Listener {
    FileConfiguration config = this.getConfig();
    HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("limitsilly").setExecutor(new limitsilly());
        this.getCommand("unlimitsilly").setExecutor(new unlimitsilly());
        this.getCommand("reloadsilly").setExecutor(new reloadsilly());

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PermissionAttachment attachment = player.addAttachment(this);
        attachment.setPermission("justaplugin.sillinesslimiter.limitsilly", true);
        attachment.setPermission("justaplugin.sillinesslimiter.unlimitsilly", true);
        perms.put(player.getUniqueId(), attachment);
            if (config.get(player.getName()) == null) {
                player.sendMessage(ChatColor.RED + "Your ip is not linked, your name might be at risk, use /limitsilly to protect yourself");

            } else {
                if (!config.get(player.getName()).equals(player.getAddress().getAddress().getHostAddress())) {
                    player.kickPlayer(ChatColor.RED + "trying to limit your silliness\n\n" + ChatColor.AQUA + " you joined on account with other ip already linked to it");

                }
            }
    }

    @Override
    public void onLoad() {
        // Plugin load logic
        config.addDefault("Steve", "127.0.0.1");

        this.getLogger().info("Silliness limiter Has been loaded");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
