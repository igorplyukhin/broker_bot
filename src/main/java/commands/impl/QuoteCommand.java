package commands.impl;

import brokerBot.BrokerBot;
import answer.Answer;
import answer.AnswerAnnotation;
import commands.Command;
import commands.CommandAnnotation;
import enums.State;
import keyboard.KeyboardFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import yahoofinance.Stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AnswerAnnotation(name = State.WAITING_QUOTE_COMMAND_ANSWER, description = "Send quote price to user")
@CommandAnnotation(name = "/get_quote", description = "Show user's balance")
public class QuoteCommand extends Command implements Answer {
    public QuoteCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage handleAnswer(String response) {
        BrokerBot.Repository.setUserState(getChatID(), State.DEFAULT);
        String text;
        try {
            text = stockToString(BrokerBot.Repository.getQuote(response));
        } catch (IOException e) {
            text = "API is unreachable now";
        }
        return createNewMessage().setText(text);
    }

    @Override
    public SendMessage execute() {
        BrokerBot.Repository.setUserState(getChatID(), State.WAITING_QUOTE_COMMAND_ANSWER);
        var message = createNewMessage().setText("Choose quote");
        var keyboard = new KeyboardFactory().buildOneTimeKeyboard();
        return message.setReplyMarkup(keyboard);
    }

    private String stockToString(Stock stock){
        var sb = new StringBuilder();
        sb.append(stock.getName());
        sb.append(" (");
        sb.append(stock.getCurrency());
        sb.append(")\r\n");
        sb.append(stock.getQuote());
        return sb.toString();
    }
}