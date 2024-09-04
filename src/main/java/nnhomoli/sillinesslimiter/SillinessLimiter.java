package nnhomoli.sillinesslimiter;

import nnhomoli.sillinesslimiter.data.*;
import nnhomoli.sillinesslimiter.cmds.*;
import nnhomoli.sillinesslimiter.misc.*;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;

public final class SillinessLimiter extends JavaPlugin {
    public static Logger log;
    public static data udata;
    public static LangLoader lang;
    public static String version;


    @Override
    public void onEnable() {
        // Plugin startup logic

        this.getCommand("silly-limit").setExecutor(new sillylimit(this));
        this.getCommand("silly-unlimit").setExecutor(new sillyunlimit());
        this.getCommand("silly-confirm").setExecutor(new sillyconfirm(this));
        this.getCommand("silly-deny").setExecutor(new sillydeny());
        this.getCommand("silly-switch").setExecutor(new sillyswitch());
        this.getCommand("silly-reload").setExecutor(new sillyreload(this));
        this.getCommand("silly-list").setExecutor(new sillylist());
        this.getCommand("silly-dynamic-limit").setExecutor(new sillydynamiclimit());
        this.getCommand("silly-dynamic-unlimit").setExecutor(new sillydynamicunlimit());
        this.getCommand("silly-help").setExecutor(new sillyhelp());

        Bukkit.getScheduler().runTaskTimer(this, new ConfirmationTimeout(this), 0, 20);

        getServer().getPluginManager().registerEvents(new Listener(this), this);
    }

    @Override
    public void onLoad() {
        // Plugin load logic
        log = this.getLogger();
        version = this.getDescription().getVersion();

        if(this.getConfig().get("version") == null || !this.getConfig().get("version").equals(version)) {
            this.getConfig().set("version", version);
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
        if(this.getConfig().get("confirmation-timeout") == null) {
            this.getConfig().set("confirmation-timeout", 180);
            this.getConfig().setComments("confirmation-timeout", List.of("Timeout confirmation requests in seconds, 180 by default"));
        }

        this.saveConfig();
        this.reloadConfig();

        lang = new LangLoader(this);
        lang.load();

        udata = new data();
        udata.load(this);

        log.info(this.getName() + " " + version + " Has been loaded");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        udata.save();

        log.info(this.getName() + " " + version + " Has been unloaded");
    }

    public static boolean isAllowed(String PlayerName, String IP) {
        String dynamic = udata.getDynamicIP(PlayerName);
        List<Object> ip = udata.getList(PlayerName);

        boolean allow = false;
        if(dynamic != null) {
            if(IP.contains(dynamic.replace("*", ""))) allow = true;
        }
        if(ip != null) {
            if(ip.contains(IP)) allow = true;
        }
        if(!udata.isEnabled(PlayerName) || udata.isEnabled(PlayerName) && ip == null && dynamic == null) {
            allow = true;
        }
        return allow;
    }
}
