package commands.impl;

import API.YahooAPI;
import commands.Command;
import commands.CommandAnnotation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import yahoofinance.YahooFinance;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.Collection;

@CommandAnnotation(name = "/market", description = "Show user's balance")
public class MarketCommand implements Command {
    private static final String[] stocks = new String[]{"AMD", "IBM", "AAPl", "INTC", "BABA", "TSLA", "AIR.PA", "YHOO"};

    @Override
    public SendMessage execute(Update update) {
        long chatID = update.getMessage().getChatId();
        var message = new SendMessage().setChatId(chatID);
        var quotes = "";
        try{
            quotes = quotesToString(YahooFinance.get(MarketCommand.stocks).values());
        } catch (IOException e){
            quotes = "API Error";
        }
        return message.setText(quotes);
    }

    private String quotesToString(Collection<yahoofinance.Stock> quotes){
        var res = new StringBuilder();
        for (var q : quotes){
            res.append(q.toString());
            res.append("\r\n");
        }
        return  res.toString();
    }

}
