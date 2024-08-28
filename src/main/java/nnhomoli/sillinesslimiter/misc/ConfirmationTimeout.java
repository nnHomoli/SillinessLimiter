package nnhomoli.sillinesslimiter.misc;

import static nnhomoli.sillinesslimiter.cmds.sillyconfirm.confirmations;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.entity.Player;

import java.util.HashMap;


public class ConfirmationTimeout implements Runnable{
    private final SillinessLimiter plugin;
    public static HashMap<Player, Integer> Timeout = new HashMap<>();

    public ConfirmationTimeout(SillinessLimiter plugin) {this.plugin = plugin;}

    @Override
    public void run() {
        for (Player p : confirmations.keySet()) {
            if (!Timeout.containsKey(p)) {
                Timeout.put(p, 0);
            } else {
                if (Timeout.get(p) >= this.plugin.getConfig().getInt("confirmation-timeout")) {
                    confirmations.remove(p);
                    Timeout.remove(p);
                    p.sendMessage(SillinessLimiter.lang.get("timeout"));
                } else {
                    Timeout.put(p, Timeout.get(p) + 1);
                }
            }
        }
    }
}

