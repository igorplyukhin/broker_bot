package commands.impl;

import answer.Answer;
import answer.AnswerAnnotation;
import brokerBot.BrokerBot;
import commands.Command;
import commands.CommandAnnotation;
import entities.transaction.TransactionImpl;
import enums.Active;
import enums.State;
import enums.TransactionType;
import keyboard.KeyboardFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.io.IOException;

@AnswerAnnotation(name = State.WAITING_BUY_COMMAND_ANSWER, description = "buy stock")
@CommandAnnotation(name = "/buy", description = "buy stock")
public class BuyCommand extends Command implements Answer {
    public BuyCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage handleAnswer(String response) {
        BrokerBot.Repository.setUserState(getChatID(), State.DEFAULT);
        var message = newMessage().setReplyMarkup(new ReplyKeyboardRemove());
        var stock = Active.valueOf(response);
        var count = 1;
        double price;
        try {
            price = BrokerBot.Repository.getQuote(response).getQuote().getPrice().doubleValue();
        } catch (IOException e) {
            e.printStackTrace();
            return message.setText("Market is unreachable now");
        }

        var t = new TransactionImpl(getChatID(), stock, count, price, TransactionType.BUY);
        var result = BrokerBot.Repository.proceedTransaction(t);
        if (result)
            return message.setText("You bought " + count + ' ' + stock + " stock(s) " + "for $" + price);
        else
            return message.setText("Purchase error. Probably you don't have enough money");
    }

    @Override
    public SendMessage execute() {
        BrokerBot.Repository.setUserState(getChatID(), State.WAITING_BUY_COMMAND_ANSWER);
        var message = newMessage().setText("Choose quote to buy");
        var keyboard = new KeyboardFactory().buildAllStocksKeyboard();
        return message.setReplyMarkup(keyboard);
    }
}
