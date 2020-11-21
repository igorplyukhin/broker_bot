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


@CommandAnnotation(name = "/sell", description = "sell command")
@AnswerAnnotation(name = State.WAITING_SELL_COMMAND_ANSWER, description = "blabla")
public class SellCommand extends Command implements Answer {
    public SellCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage handleAnswer(String response) {
        var repository = BrokerBot.Repository;
        repository.setUserState(getChatID(), State.DEFAULT);
        var message = newMessage().setReplyMarkup(new ReplyKeyboardRemove());
        var user = repository.getUser(getChatID());
        var count = 1;
        double price;
        try {
            price = repository.getQuote(response).getQuote().getPrice().doubleValue();
        } catch (IOException e) {
            e.printStackTrace();
            return message.setText("API unreachable try later");
        }
        var T = new TransactionImpl(getChatID(), Active.valueOf(response), count, price, TransactionType.SELL);
        var result = repository.proceedTransaction(T);
        if (result)
            return message.setText(String.format("You sold %d stock(s) for %.2f \nNow you have %.2f $",
                    count, price, user.getUsdBalance()));
        else
            return message.setText("purchase error");

    }

    @Override
    public SendMessage execute() {
        BrokerBot.Repository.setUserState(getChatID(), State.WAITING_SELL_COMMAND_ANSWER);
        var user = BrokerBot.Repository.getUser(getChatID());
        var message = newMessage().setText("Choose quote to sell");
        var keyboard = new KeyboardFactory().buildUserStocksKeyboard(user);
        return message.setReplyMarkup(keyboard);
    }
}
