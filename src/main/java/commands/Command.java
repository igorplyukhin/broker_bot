package commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Command {
    private final long chatID;

    public long getChatID() {
        return chatID;
    }

    protected Command(Update update) {
        this.chatID = update.getMessage().getChatId();
    }

    public abstract SendMessage execute();

    public SendMessage getNewMessage() {
        return new SendMessage().setChatId(chatID);
    }
}
