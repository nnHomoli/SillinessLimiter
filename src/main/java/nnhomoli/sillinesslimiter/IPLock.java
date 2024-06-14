package nnhomoli.sillinesslimiter;

import nnhomoli.sillinesslimiter.data.*;
import nnhomoli.sillinesslimiter.cmds.*;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public final class IPLock extends JavaPlugin implements Listener {
    public static Logger log;
    public static data pdata;
    public static LangLoader lang;
    public static ArrayList<Pattern> dynamic_ranges = new ArrayList<>(Arrays.asList(
                    Pattern.compile("(\\d{1,3}\\.)\\*"),
                    Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.)\\*"),
                    Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.)\\*")));
    public static Pattern ip_pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");


    public static HashMap<Player, Object> confirmations = new HashMap<>();
    ArrayList<Permission> perms = new ArrayList<>(Arrays.asList(new Permission("nnhomoli.sillinesslimiter.cmds.sillyunlimit"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillylimit"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillyconfirm"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillydeny"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillylist"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillyswitch"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillydynamiclimit"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillydynamicunlimit"),
            new Permission("nnhomoli.sillinesslimiter.cmds.sillyhelp")));


    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("silly-limit").setExecutor(new sillylimit());
        this.getCommand("silly-unlimit").setExecutor(new sillyunlimit());
        this.getCommand("silly-confirm").setExecutor(new sillyconfirm());
        this.getCommand("silly-deny").setExecutor(new sillydeny());
        this.getCommand("silly-switch").setExecutor(new sillyswitch());
        this.getCommand("silly-reload").setExecutor(new sillyreload());
        this.getCommand("silly-list").setExecutor(new sillylist());
        this.getCommand("silly-dynamic-limit").setExecutor(new sillydynamiclimit());
        this.getCommand("silly-dynamic-unlimit").setExecutor(new sillydynamicunlimit());
        this.getCommand("silly-help").setExecutor(new sillyhelp());


        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if(this.getConfig().getBoolean("Permission-by-default")) {
            PermissionAttachment at = p.addAttachment(this);
            perms.forEach(per -> {
                at.setPermission(per, true);
            });
            p.updateCommands();
        }
        if(pdata.getList(p.getName()) == null && pdata.get(p.getName() + ";dynamic") == null || !IPLock.pdata.isEnabled(p.getName())) {
            if(this.getConfig().getBoolean("Login-link-message")) p.sendMessage(lang.get("login_link_message"));
        }
    }

    @EventHandler
    public void preLogin(AsyncPlayerPreLoginEvent event) {
        String p = event.getName();

        converter.convert(p, this);
        List<Object> ip = pdata.getList(p);
        Object dynamic = pdata.get(p + ";dynamic");

        if(IPLock.pdata.isEnabled(p)) {
            if (ip != null && !ip.contains(event.getAddress().getHostAddress()) ||
                    dynamic != null && !Pattern.compile(dynamic.toString()).matcher(event.getAddress().getHostAddress()).matches()) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, lang.get("kick_reason"));
            }
        }
    }

    @Override
    public void onLoad() {
        // Plugin load logic
        log = this.getLogger();

        if(this.getConfig().get("version") == null || !this.getConfig().get("version").equals("1.2")) {
            this.getConfig().set("version", "1.2");
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
        if(this.getConfig().get("check-after-confirm") == null) {
            this.getConfig().set("check-after-confirm", false);
            this.getConfig().setComments("check-after-confirm", List.of("Check if player ip is still linked after confirm, false by default"));
        }

        this.saveConfig();
        this.reloadConfig();

        lang = new LangLoader();
        lang.load(this);

        pdata = new data();
        pdata.load(this);

        log.info("Silliness limiter Has been loaded");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        pdata.save();
    }
}
