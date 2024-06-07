package justaplugin.sillinesslimiter;

import justaplugin.sillinesslimiter.lang.LangLoader;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public final class IPLock extends JavaPlugin implements Listener {
    public static Logger log;
    public static LangLoader lang;
    public static Pattern ip_pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");


    public static HashMap<Player, String> confirmations = new HashMap<>();
    ArrayList<Permission> perms = new ArrayList<>(Arrays.asList(new Permission("justaplugin.sillinesslimiter.sillylimit"),
            new Permission("justaplugin.sillinesslimiter.sillyunlimit"),
            new Permission("justaplugin.sillinesslimiter.sillyconfirm"),
            new Permission("justaplugin.sillinesslimiter.sillymove"),
            new Permission("justaplugin.sillinesslimiter.sillydeny"),
            new Permission("justaplugin.sillinesslimiter.sillylist")));


    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("silly-limit").setExecutor(new limitsilly());
        this.getCommand("silly-unlimit").setExecutor(new unlimitsilly());
        this.getCommand("silly-confirm").setExecutor(new sillyconfirm());
        this.getCommand("silly-deny").setExecutor(new sillydeny());
        this.getCommand("silly-reload").setExecutor(new sillyreload());
        this.getCommand("silly-list").setExecutor(new sillylist());

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        if(IPLock.getPlugin(IPLock.class).getConfig().getBoolean("Permission-by-default")) {
            PermissionAttachment at = p.addAttachment(this);
            perms.forEach(per -> {
                at.setPermission(per, true);
            });
            p.updateCommands();
        }

        //1.0 to 1.1 converter
        if(this.getConfig().getString(p.getName()) != null && this.getConfig().getList(p.getName()) == null) {
            this.getConfig().set(p.getName(), List.of(this.getConfig().getString(p.getName())));
            this.saveConfig();
            log.info("Converted " + p.getName() + " from 1.0 to 1.1");
        };

        if(this.getConfig().getList(p.getName()) == null) {
            if(this.getConfig().getBoolean("Login-link-message")) p.sendMessage(lang.get("login_link_message"));
        }

        else if(!this.getConfig().getList(p.getName()).contains(p.getAddress().getAddress().getHostAddress())) {
                event.setJoinMessage(null);
                p.kickPlayer(lang.get("kick_reason"));
            }
    }

    @Override
    public void onLoad() {
        // Plugin load logic
        log = this.getLogger();

        if(this.getConfig().get("version") == null) {
            this.getConfig().set("version", "1.1");
            this.getConfig().setComments("version", List.of("Official repository: https://github.com/nnHomoli/SillinessLimiter"));
        }

        if(this.getConfig().get("Permission-by-default") == null) {
            this.getConfig().set("Permission-by-default", true);
            this.getConfig().setComments("Permission-by-default", List.of("Grants every permission of this plugin commands, except reload. True by default"));
        }
        if(this.getConfig().get("Login-link-message") == null) {
            this.getConfig().set("Login-link-message", true);
            this.getConfig().setComments("Login-link-message", List.of("Display a message when you join if player ip is not linked, can be changed in lang.yml, true by default"));
        }
        if(this.getConfig().get("Max-IP-Allowed") == null) {
            this.getConfig().set("Max-IP-Allowed", 4);
            this.getConfig().setComments("Max-IP-Allowed", List.of("The maximum number of ip that can be linked to the same name, 4 by default"));
        }

        this.saveConfig();
        this.reloadConfig();

        lang = new LangLoader();
        lang.load(this);

        log.info("Silliness limiter Has been loaded");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
