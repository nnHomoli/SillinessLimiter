package nnhomoli.sillinesslimiter.cmds;


import nnhomoli.sillinesslimiter.SillinessLimiter;
import nnhomoli.sillinesslimiter.data.userdata;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public final class sillyreload implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;
    private final userdata user;
    private final LangLoader lang;
    public sillyreload(JavaPlugin p, userdata d, LangLoader l) {
        this.plugin = p;
        this.user = d;
        this.lang = l;
    }

    @Override
    public boolean onCommand(@Nonnull final CommandSender sender,@Nonnull final Command command, @Nonnull final String s, String[] args) {
        if(args.length == 0 || args[0].equals("Config")) {
            this.plugin.reloadConfig();
            if(plugin instanceof SillinessLimiter p) {p.SetupConfig();}
        }
        if(args.length == 0 || args[0].equals("Language")) lang.reload();
        if(args.length == 0 || args[0].equals("PlayerData")) user.reload();

        plugin.getLogger().info("Reloaded SillinessLimiter");
        if(sender instanceof Player) sender.sendMessage(lang.getString("plugin_reload"));

        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull final CommandSender commandSender, @Nonnull final Command command, @Nonnull final String s, String[] args) {
        if(args.length == 1) return List.of("Config", "Language", "PlayerData");
        return null;
    }
}
