package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.data.userdata;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public final class sillyswitch implements CommandExecutor {
    private final userdata user;
    private final LangLoader lang;
    public sillyswitch(userdata d, LangLoader l) {
        this.user = d;
        this.lang = l;
    }

    @Override
    public boolean onCommand(@Nonnull final CommandSender sender, @Nonnull final Command command, @Nonnull final String s,@Nonnull final String[] strings) {
        if(sender instanceof Player p) {
            if (user.confirmationContainsPlayer(p)) {
                p.sendMessage(lang.getString("confirm_busy"));
                return true;
            }

            boolean out;
            if(user.isEnabled(p.getName())) {
                user.setConfirmation(p, false);
                out = false;
            } else {
                user.setConfirmation(p, true);
                out = true;
            }
            p.sendMessage(lang.getString(out ? "switch_to_true" : "switch_to_false"));
        }
        return true;
    }
}
