package mcbe.kake26s.discordchatplugin;

import mcbe.kake26s.discordchatplugin.event.DiscordEvent;
import mcbe.kake26s.discordchatplugin.exception.NotFoundChannelException;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;


public class Discord{

    // singleton
    private static Discord discord;

    private IDiscordClient client;

    private IChannel channel = null;


    // singleton init
    public static void init() {
        discord = new Discord();
    }

    public static Discord get() {
        return discord;
    }

    public Discord() {
        this.client = this.createClient();
        this.client.getDispatcher().registerListener(new DiscordEvent());
    }

    public IDiscordClient createClient() {
        try {
            ClientBuilder clientBuilder = new ClientBuilder();
            return clientBuilder.withToken(Core.TOKEN).login();

        } catch (DiscordException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setChannel(IChannel channel) {
        this.channel = channel;
    }

    public void sendMessage(String message) {
        RequestBuffer.request(() -> {
            try{
                if (channel == null)
                    throw new NotFoundChannelException("指定したチャンネルが存在しません");

                channel.sendMessage(message);
            } catch (DiscordException e){
                System.err.println("メッセージを送ることができませんでした");
                e.printStackTrace();

            } catch (NotFoundChannelException e) {
                e.printStackTrace();
            }
        });
    }
}
