package commands.impl;

import brokerBot.BrokerBot;
import commands.Command;
import commands.CommandAnnotation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import yahoofinance.Stock;

import java.io.IOException;
import java.util.Collection;

@CommandAnnotation(name = "/market", description = "Show user's balance")
public class MarketCommand extends Command {
    public MarketCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        var message = createNewMessage();
        String text;
        try {
            text = quotesToString(BrokerBot.Repository.getQuotes());
        } catch (IOException e) {
            text = "API service unreachable now";
        }

       return message.setText(text);
    }

    private String quotesToString(Collection<Stock> quotes) {
        var sb = new StringBuilder();
        for (var q : quotes) {
            sb.append(q.toString());
            sb.append("\r\n");
        }
        return sb.toString();
    }


}
