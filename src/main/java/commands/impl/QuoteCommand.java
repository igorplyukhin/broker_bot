package commands.impl;

import brokerBot.BrokerBot;
import commands.replyCommand.ReplyCommand;
import commands.replyCommand.ReplyCommandAnnotation;
import commands.command.Command;
import commands.command.CommandAnnotation;
import enums.UserState;
import keyboard.KeyboardFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import yahoofinance.Stock;

import java.io.IOException;

@ReplyCommandAnnotation(name = UserState.WAITING_QUOTE_COMMAND, description = "Send quote price to user")
@CommandAnnotation(name = "/get_quote", description = "Show user's balance")
public class QuoteCommand extends Command implements ReplyCommand {
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
        return newMessage().setText(text).setReplyMarkup(new ReplyKeyboardRemove());
    }

    @Override
    public SendMessage execute() {
        BrokerBot.Repository.setUserState(getChatID(), UserState.WAITING_QUOTE_COMMAND);
        var message = newMessage().setText("Choose quote");
        var keyboard = new KeyboardFactory().buildAllStocksKeyboard();
        return message.setReplyMarkup(keyboard);
    }

    private String stockToString(Stock stock) {
        var sb = new StringBuilder();
        sb.append(stock.getName());
        sb.append(" (");
        sb.append(stock.getCurrency());
        sb.append(")\r\n");
        sb.append(stock.getQuote());
        return sb.toString();
    }
}
