package mcbe.kake26s.discordchatplugin.exception;

public class NotFoundChannelException extends Exception {

    private static final long serialVersionUID = 1L;

    public NotFoundChannelException(String msg) {
        super(msg);
    }
}
