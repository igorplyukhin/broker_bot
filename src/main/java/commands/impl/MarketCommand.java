package commands.impl;

import commands.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


public class MarketCommand implements Command {

    @Override
    public SendMessage execute(Update update) {
        return null;
    }
}
