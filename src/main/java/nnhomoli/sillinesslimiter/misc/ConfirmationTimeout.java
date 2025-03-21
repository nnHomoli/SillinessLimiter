package nnhomoli.sillinesslimiter.misc;

import nnhomoli.sillinesslimiter.data.userdata;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;


public final class ConfirmationTimeout implements Runnable{
    private final HashMap<Player, Integer> Timeout = new HashMap<>();

    private final JavaPlugin plugin;
    private final LangLoader lang;
    private final userdata user;

    public ConfirmationTimeout(JavaPlugin plugin, LangLoader l,userdata u) {
        this.plugin = plugin;
        this.lang = l;
        this.user = u;
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(user.confirmationContainsPlayer(p)) {
                if (!Timeout.containsKey(p)) {
                    Timeout.put(p, 0);
                } else {
                    if (Timeout.get(p) >= this.plugin.getConfig().getInt("confirmation-timeout")) {
                        user.removeConfirmation(p);
                        Timeout.remove(p);
                        p.sendMessage(this.lang.getString("timeout"));
                    } else {
                        Timeout.put(p, Timeout.get(p) + 1);
                    }
                }
            } else Timeout.remove(p);
        }
    }
}

