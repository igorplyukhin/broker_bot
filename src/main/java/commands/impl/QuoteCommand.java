package commands.impl;

import commands.Command;
import commands.CommandAnnotation;
import di.Factories;
import enums.Actives;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import repository.RepositoryImpl;


@CommandAnnotation(name = "/get_quote", description = "Show user's balance")
public class QuoteCommand implements Command {
    @Override
    public SendMessage execute(Update update) {
        long chatID = update.getMessage().getChatId();
        var message = new SendMessage().setChatId(chatID);
        var quote = Factories.getRepository(RepositoryImpl.class.getName()).getQuote(Actives.AAPL);
        message.setText(quote);
        return message;
    }
}
