package mcbe.kake26s.discordchatplugin;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import mcbe.kake26s.discordchatplugin.event.MCEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Core extends PluginBase {

    private static Core instance;

    public static String TOKEN = "";
    public static String CHANNEL_NAME = "";

    public static Core get() {
        return instance;
    }

    public void onEnable() {
        System.setProperty("file.encoding", "UTF-8"); // 文字コードを指定

        instance = this;
        this.getLogger().info(TextFormat.GREEN + "loading now...");

        initConstant();
        Discord.init();
        this.getServer().getPluginManager().registerEvents(new MCEvent(), this);

        this.getLogger().info(TextFormat.GREEN + "completed loading!");
    }

    public void onDisable() {
        Discord.get().sendMessage("処理を終了しました :wave: :wave: :wave: ");
    }

    private void initConstant() {
        File file = new File(this.getDataFolder() + "/discord.json");

        if (!file.exists()) {
            try {
                if(!file.createNewFile())
                    (new FileNotFoundException("discord.jsonが存在しません")).printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Config config = new Config(file, Config.JSON);

        if (config.get("token") == null) {
            config.set("token", "");
            config.set("channel", "");
            config.save();
        }

        TOKEN = (String)config.get("token");
        CHANNEL_NAME = (String)config.get("channel");

        if (TOKEN.equals(""))
            throw new NullPointerException("tokenが見つかりません");
        if (CHANNEL_NAME.equals(""))
            throw new NullPointerException("channelが見つかりません");
    }
}
