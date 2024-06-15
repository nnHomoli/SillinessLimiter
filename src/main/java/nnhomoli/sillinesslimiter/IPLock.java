package nnhomoli.sillinesslimiter;

import nnhomoli.sillinesslimiter.data.*;
import nnhomoli.sillinesslimiter.cmds.*;
import nnhomoli.sillinesslimiter.misc.Listener;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public final class IPLock extends JavaPlugin {
    public static Logger log;
    public static data pdata;
    public static LangLoader lang;
    public static HashMap<Player, Object> confirmations = new HashMap<>();
    public static ArrayList<Pattern> dynamic_ranges = new ArrayList<>(Arrays.asList(
                    Pattern.compile("(\\d{1,3}\\.)\\*"),
                    Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.)\\*"),
                    Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.)\\*")));
    public static Pattern ip_pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");


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


        getServer().getPluginManager().registerEvents(new Listener(), this);
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

    public static boolean isIPLinked(String PlayerName, String IP) {
        Object dynamic = IPLock.pdata.get(PlayerName + ";dynamic");
        List<Object> ip = IPLock.pdata.getList(PlayerName);
        int checked = 0;
        if(ip != null || dynamic != null) {
            if (ip != null && ip.contains(IP))
                checked++;
            if (dynamic != null && Pattern.compile(dynamic.toString()).matcher(IP).matches())
                checked++;
            return checked >= 1;
        }
        return true;
    }
}
