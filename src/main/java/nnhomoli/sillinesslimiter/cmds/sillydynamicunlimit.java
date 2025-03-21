package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.data.userdata;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public final class sillydynamicunlimit implements CommandExecutor {
    private final userdata user;
    private final LangLoader lang;
    public sillydynamicunlimit(userdata d, LangLoader l) {
        this.user = d;
        this.lang = l;
    }

    @Override
    public boolean onCommand(@Nonnull final CommandSender sender, @Nonnull final Command command, @Nonnull final String s, @Nonnull final String[] strings) {
        if(sender instanceof Player p){
            if(user.confirmationContainsPlayer(p)) {
                p.sendMessage(lang.getString("confirm_busy"));
                return true;
            }

            Object dyn = user.getDynamicIP(p.getName());
            if(dyn == null) {
                p.sendMessage(lang.getString("dynamic_unlimit_not_linked"));

            } else {
                user.setConfirmation(p, dyn);
                p.sendMessage(lang.getString("dynamic_unlimit"));
            }
        }
        return true;
    }
}
