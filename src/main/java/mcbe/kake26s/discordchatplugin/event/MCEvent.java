package mcbe.kake26s.discordchatplugin.event;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.utils.TextFormat;
import mcbe.kake26s.discordchatplugin.Discord;

public class MCEvent implements Listener{

    // TODO: プレイヤーだけではなくコンソールも対応
    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player pl = e.getPlayer();
        String name = pl.getName();
        String message = e.getMessage();

        String sendText = name + ": " + message;
        Discord.get().sendMessage(sendText);
    }
}
