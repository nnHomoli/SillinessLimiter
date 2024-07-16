package nnhomoli.sillinesslimiter;

import nnhomoli.sillinesslimiter.data.*;
import nnhomoli.sillinesslimiter.cmds.*;
import nnhomoli.sillinesslimiter.misc.Listener;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileInputStream;
import java.util.*;
import java.util.logging.Logger;

public final class SillinessLimiter extends JavaPlugin {
    public static Logger log;
    public static data udata;
    public static LangLoader lang;


    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("server.properties"));
            if(props.getProperty("online-mode").equals("true")) {
                log.info("Online mode detected, automatically disabling " + this.getName());
                this.getServer().getPluginManager().disablePlugin(this);
                return;
            }
        } catch(Exception e) {
            log.info("Failed to check online mode:\n" + e.getMessage());
        }

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


        getServer().getPluginManager().registerEvents(new Listener(this), this);
    }

    @Override
    public void onLoad() {
        // Plugin load logic
        log = this.getLogger();

        if(this.getConfig().get("version") == null || !this.getConfig().get("version").equals("1.2.2")) {
            this.getConfig().set("version", "1.2.2");
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

        udata = new data();
        udata.load(this);

        log.info(this.getName() + " Has been loaded");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        udata.save();

        log.info(this.getName() + " Has been unloaded");
    }

    public static boolean IPNotLinked(String PlayerName, String IP) {
        String dynamic = SillinessLimiter.udata.getDynamicIP(PlayerName);
        List<Object> ip = SillinessLimiter.udata.getList(PlayerName);

        boolean allow = false;
        if(dynamic != null) {
            if(IP.contains(dynamic.replace("*", ""))) allow = true;
        }
        if(ip != null) {
            if(ip.contains(IP)) allow = true;
        }
        return !allow;
    }
}
