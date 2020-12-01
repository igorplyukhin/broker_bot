package commands.impl;

import brokerBot.BrokerBot;
import commands.command.Command;
import commands.command.CommandAnnotation;
import enums.CommandName;
import enums.Currency;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import repository.Repository;
import yahoofinance.Stock;

import java.io.IOException;
import java.util.Collection;

@CommandAnnotation(name = CommandName.MARKET, description = "Show user's balance")
public class MarketCommand extends Command {
    public MarketCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        var message = newMessage();
        String text;
        try {
            text = quotesToString(BrokerBot.Repository.getQuotes());
        } catch (IOException e) {
            text = Repository.Mock;
        }

        return message.setText(text).enableMarkdown(true);
    }

    private String quotesToString(Collection<Stock> quotes) {
        var sb = new StringBuilder();
        for (var q : quotes) {
            sb.append(String.format("*%s*: %.2f%s (%s)\n", q.getSymbol(), q.getQuote().getPrice(),
                    Currency.valueOf(q.getCurrency()).label, q.getName()));
        }
        return sb.toString();
    }
}
