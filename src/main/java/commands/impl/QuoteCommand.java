package commands.impl;

import brokerBot.BrokerBot;
import commands.replyCommand.ReplyCommand;
import commands.replyCommand.ReplyCommandAnnotation;
import commands.command.Command;
import commands.command.CommandAnnotation;
import enums.CommandName;
import enums.UserState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import yahoofinance.Stock;

import java.io.IOException;

@ReplyCommandAnnotation(name = UserState.WAITING_QUOTE_COMMAND, description = "Send quote price to user")
@CommandAnnotation(name = CommandName.GET_QUOTE, description = "Show user's balance")
public class QuoteCommand extends Command implements ReplyCommand {
    private static final String urlPrefix = "https://finance.yahoo.com/quote/";

    public QuoteCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage handleReply(UserState userState, String response) {
        BrokerBot.Repository.setUserState(getChatID(), UserState.DEFAULT);
        String text;
        try {
            text = stockToString(BrokerBot.Repository.getQuote(response));
        } catch (IOException e) {
            text = "API is unreachable now";
        }
        return newMessage().setText(text).disableWebPagePreview();
    }

    @Override
    public SendMessage execute() {
        BrokerBot.Repository.setUserState(getChatID(), UserState.WAITING_QUOTE_COMMAND);
        var message = newMessage().setText("Choose quote");
        var keyboard = BrokerBot.keyboardFac.buildAllStocksKeyboard();
        return message.setReplyMarkup(keyboard);
    }

    private String stockToString(Stock stock) {
        return String.format("%s (%s)\r\n%s\r\n%s", stock.getName(), stock.getCurrency(), stock.getQuote(),
                urlPrefix + stock.getSymbol());
    }
}
