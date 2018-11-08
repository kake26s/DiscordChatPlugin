package mcbe.kake26s.discordchatplugin.event;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;
import mcbe.kake26s.discordchatplugin.Core;
import mcbe.kake26s.discordchatplugin.Discord;
import mcbe.kake26s.discordchatplugin.exception.NotFoundChannelException;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 * Discordに関するイベント
 */

public class DiscordEvent {

    @EventSubscriber
    public void ready(ReadyEvent e) {

        boolean existsChannel = false;

        // ボットが処理するチャンネルを登録
        for (IChannel iChannel : e.getClient().getChannels()) {

            if (iChannel.getName().equals(Core.CHANNEL_NAME)) {
                existsChannel = true;
                Discord.get().setChannel(iChannel);
                iChannel.sendMessage("起動しました :v: ");
            }
        }

        if (!existsChannel)
            (new NotFoundChannelException("指定したチャンネルが存在しません")).printStackTrace();
    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent e) {
        IUser user = e.getAuthor();
        String displayName = user.getDisplayName(e.getGuild());
        String message = e.getMessage().getContent();
        String channelName = e.getChannel().getName();

        if (channelName.equals(Core.CHANNEL_NAME)) {
            String sendText = "[" + TextFormat.AQUA + "Discord" + TextFormat.RESET + "] " + displayName + ": " + message;
            Core.get().getLogger().info(sendText);
            for (Player pl : Server.getInstance().getOnlinePlayers().values()) {
                pl.sendMessage(sendText);
            }
        }
    }
}
