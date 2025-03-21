package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.data.userdata;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public final class sillydeny implements CommandExecutor {
    private final LangLoader lang;
    private final userdata user;
    public sillydeny(LangLoader l, userdata u) {
        this.lang = l;
        this.user = u;
    }
    @Override
    public boolean onCommand(@Nonnull final CommandSender sender, @Nonnull final Command command, @Nonnull final String s, @Nonnull final String[] strings) {
        if(sender instanceof Player p) {
            if (user.confirmationContainsPlayer(p)) {
                user.removeConfirmation(p);
                p.sendMessage(lang.getString("deny"));
            } else p.sendMessage(lang.getString("deny_nothing"));
        }
        return true;
    }
}
