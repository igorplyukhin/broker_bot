package commands.impl;

import BrokerBot.BrokerBot;
import commands.Command;
import commands.CommandAnnotation;
import di.Factories;
import enums.Actives;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import repository.TestRepository;


@CommandAnnotation(name = "/get_quote", description = "Show user's balance")
public class QuoteCommand extends Command {
    public QuoteCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        var quote = BrokerBot.Repository.getQuote(Actives.AAPL);
        return getNewMessage().setText(quote);
    }
}
