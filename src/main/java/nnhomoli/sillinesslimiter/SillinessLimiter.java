package nnhomoli.sillinesslimiter;

import nnhomoli.sillinesslimiter.data.*;
import nnhomoli.sillinesslimiter.cmds.*;
import nnhomoli.sillinesslimiter.misc.*;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class SillinessLimiter extends JavaPlugin {
    private final userdata data = new userdata(this);
    private final LangLoader lang = new LangLoader(this);
    private final String version = getDescription().getVersion();

    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("silly-limit")).setExecutor(new sillylimit(this,data,lang));
        Objects.requireNonNull(this.getCommand("silly-unlimit")).setExecutor(new sillyunlimit(data,lang));
        Objects.requireNonNull(this.getCommand("silly-confirm")).setExecutor(new sillyconfirm(this,lang,data));
        Objects.requireNonNull(this.getCommand("silly-deny")).setExecutor(new sillydeny(lang,data));
        Objects.requireNonNull(this.getCommand("silly-switch")).setExecutor(new sillyswitch(data,lang));
        Objects.requireNonNull(this.getCommand("silly-reload")).setExecutor(new sillyreload(this,data,lang));
        Objects.requireNonNull(this.getCommand("silly-list")).setExecutor(new sillylist(data,lang));
        Objects.requireNonNull(this.getCommand("silly-dynamic-limit")).setExecutor(new sillydynamiclimit(data,lang));
        Objects.requireNonNull(this.getCommand("silly-dynamic-unlimit")).setExecutor(new sillydynamicunlimit(data,lang));
        Objects.requireNonNull(this.getCommand("silly-help")).setExecutor(new sillyhelp(lang));

        Bukkit.getScheduler().runTaskTimer(this, new ConfirmationTimeout(this,lang,data), 0, 20);

        getServer().getPluginManager().registerEvents(new Listener(this,lang,data), this);
    }

    public void SetupConfig() {
        if(this.getConfig().get("version") == null || !Objects.equals(this.getConfig().get("version"), version)) {
            this.getConfig().set("version", version);
            this.getConfig().setComments("version", List.of("Official repository: https://github.com/nnHomoli/SillinessLimiter"));
        }
        if(this.getConfig().get("Permission-by-default") == null) {
            this.getConfig().set("Permission-by-default", true);
            this.getConfig().setComments("Permission-by-default", List.of("Grants every permission of this plugin commands, except reload. True by default"));
        }
        if(this.getConfig().get("Login-link-message") == null) {
            this.getConfig().set("Login-link-message", true);
            this.getConfig().setComments("Login-link-message", List.of("Display a message when the player joins without linked IP, this can be changed in lang.yml. True by default"));
        }
        if(this.getConfig().get("Max-IP-Allowed") == null) {
            this.getConfig().set("Max-IP-Allowed", 4);
            this.getConfig().setComments("Max-IP-Allowed", List.of("The maximum number of ip that can be linked to the same nickname, 4 by default"));
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
    }

    @Override
    public void onLoad() {
        SetupConfig();
        getLogger().info(this.getName() + " " + version + " Has been loaded");
    }

    @Override
    public void onDisable() {
        data.save();
        getLogger().info(this.getName() + " " + version + " Has been unloaded");
    }
}
