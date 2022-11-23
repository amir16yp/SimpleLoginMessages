package loginmessage.loginmessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public final class LoginMessage extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
    }

    public Random random = new Random();

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String ParseMsgConfig(String loginMsg, String playerName) {
        for (ChatColor chatColor: ChatColor.values())
        {
            loginMsg = loginMsg.replaceAll("\\$" + chatColor.name(), String.valueOf(chatColor));
        }
        loginMsg = loginMsg.replaceAll("\\$PLAYER", playerName);
        return loginMsg;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        List<String> loginMsgList = this.getConfig().getStringList("login");
        String[] loginMsgArr = loginMsgList.stream().toArray(String[] ::new);
        int rnd = random.nextInt(loginMsgArr.length);
        String loginMsgToParse = loginMsgArr[rnd];
        event.setJoinMessage(ParseMsgConfig(loginMsgToParse, event.getPlayer().getDisplayName()));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        List<String> logoutMsgList = this.getConfig().getStringList("logout");
        String[] logoutMsgArr = logoutMsgList.stream().toArray(String[] ::new);
        int rnd = random.nextInt(logoutMsgArr.length);
        String logoutMsgToParse = logoutMsgArr[rnd];
        event.setQuitMessage(ParseMsgConfig(logoutMsgToParse, event.getPlayer().getDisplayName()));
    }
}
