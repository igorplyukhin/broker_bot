package commands.command;

import brokerBot.BrokerBot;
import keyboard.KeyboardFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Command {
    public abstract SendMessage execute();

    private final long chatID;

    public long getChatID() {
        return chatID;
    }

    protected Command(Update update) {
        this.chatID = update.getMessage().getChatId();
    }

    public SendMessage newMessage() {
        var menuKeyboard = BrokerBot.keyboardFac.buildMainMenu();
        return new SendMessage().setChatId(chatID).setReplyMarkup(menuKeyboard);
    }
}
