package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public final class sillyhelp implements CommandExecutor {
    private final LangLoader lang;
    public sillyhelp(LangLoader l) {
        this.lang = l;
    }
    @Override
    public boolean onCommand(@Nonnull final CommandSender sender, @Nonnull final Command command, @Nonnull final String s, @Nonnull final String[] strings) {
        if(sender instanceof Player) sender.sendMessage(lang.getString("help"));
        return true;
    }
}
