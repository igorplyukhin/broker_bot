package commands.impl;

import BrokerBot.BrokerBot;
import answer.Answer;
import answer.AnswerAnnotation;
import commands.Command;
import commands.CommandAnnotation;
import enums.State;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import repository.Repository;

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
        var quote = BrokerBot.Repository.getQuote("GOOGL").toString();
        if (quote == null)
            return createNewMessage().setText("API is unreachable now");
        return createNewMessage().setText(quote);
    }

    @Override
    public SendMessage execute() {
        BrokerBot.Repository.setUserState(getChatID(), State.WAITING_QUOTE_COMMAND_ANSWER);
        var message = createNewMessage().setText("Choose quote");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup().setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("GOOGL");
        row.add("AAPL");
        row.add("Row 1 Button 3");
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("Row 2 Button 1");
        row.add("Row 2 Button 2");
        row.add("Row 2 Button 3");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }
}
