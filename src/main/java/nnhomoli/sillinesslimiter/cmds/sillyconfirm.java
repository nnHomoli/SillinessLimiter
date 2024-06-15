package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.IPLock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import java.util.List;

public class sillyconfirm implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            List<Object> ip = IPLock.pdata.getList(p.getName());
            if (IPLock.confirmations.containsKey(p)) {
                Object value = IPLock.confirmations.get(p);
                Object out = null;
                String key = "";
                String msg = "";
                String log_msg = "";
                if (value instanceof String || value == null) {
                    if(value == null || IPLock.ip_pattern.matcher((String)value).matches()) {

                        if (value == null) ip.remove(p.getAddress().getAddress().getHostAddress());
                        else if (ip != null && ip.contains(value)) {
                            ip.remove(value);
                        } else {
                            if (ip == null) ip = List.of(value);
                            else ip.add(value);
                        }

                        if (ip.isEmpty()) ip = null;

                        key = p.getName();
                        out = ip;

                        msg = (ip != null && ip.contains(value)) ? IPLock.lang.get("limit_success") : IPLock.lang.get("unlimit_success");
                        log_msg = p.getName() + ((value != null) ? " has been linked" : " has been unlinked");
                    } else {
                        key = p.getName() + ";dynamic";
                        out = value;

                        Object check = IPLock.pdata.get(key);
                        if(check != null) out = null;

                        msg = check != null ? IPLock.lang.get("dynamic_unlimit_success") : IPLock.lang.get("dynamic_success") ;
                        log_msg = check != null ? p.getName() + " has unlinked dynamic ip" : p.getName() + " has linked dynamic ip";
                    }

                }
                if (value instanceof Boolean) {

                    key = p.getName() + ";enabled";
                    out = value;

                    msg = (boolean)value ? IPLock.lang.get("auth_enable") : IPLock.lang.get("auth_disable");
                    log_msg = p.getName() + ((boolean)value ? " enabled " : " disabled ") + "their auth";
                }

                IPLock.pdata.set(key, out);
                IPLock.pdata.save();

                p.sendMessage(msg);
                IPLock.log.info(log_msg);

                if (IPLock.getPlugin(IPLock.class).getConfig().getBoolean("check-after-confirm") && IPLock.pdata.isEnabled(p.getName())) {
                    if(!IPLock.isIPLinked(p.getName(), p.getAddress().getAddress().getHostAddress())) p.kickPlayer(IPLock.lang.get("kick_reason"));
                }

                IPLock.confirmations.remove(p);
                } else sender.sendMessage(IPLock.lang.get("confirm_nothing"));
            }
        return true;
    }
}

