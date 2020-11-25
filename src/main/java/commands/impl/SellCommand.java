package commands.impl;

import commands.replyCommand.ReplyCommand;
import commands.replyCommand.ReplyCommandAnnotation;
import brokerBot.BrokerBot;
import commands.command.Command;
import commands.command.CommandAnnotation;
import entities.transaction.TransactionImpl;
import enums.Stock;
import enums.UserState;
import enums.TransactionType;
import keyboard.KeyboardFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.io.IOException;


@ReplyCommandAnnotation(name = UserState.WAITING_SELL_CHOOSE_COUNT, description = "bla")
@ReplyCommandAnnotation(name = UserState.WAITING_SELL_COMMAND, description = "blabla")
@CommandAnnotation(name = "/sell", description = "sell command")
public class SellCommand extends Command implements ReplyCommand {
    public SellCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage handleReply(UserState userState, String response) {
        return switch (userState) {
            case WAITING_SELL_CHOOSE_COUNT -> handleSelectCount(response);
            case WAITING_SELL_COMMAND -> handleSell(response);
            default -> newMessage().setText("Critical error");
        };
    }

    @Override
    public SendMessage execute() {
        BrokerBot.Repository.setUserState(getChatID(), UserState.WAITING_SELL_CHOOSE_COUNT);
        var user = BrokerBot.Repository.getUser(getChatID());
        var message = newMessage();
        if (user.getPortfolio().keySet().size() == 0)
            return message.setText("You portfolio is empty");
        var keyboard = new KeyboardFactory().buildUserStocksKeyboard(user);
        return message.setText("Choose quote to sell").setReplyMarkup(keyboard);
    }

    private SendMessage handleSelectCount(String response) {
        var repository = BrokerBot.Repository;
        repository.setUserState(getChatID(), UserState.WAITING_SELL_COMMAND);
        repository.getUser(getChatID()).previousReplies.set(0, response);
        var keyboard = new KeyboardFactory().buildNumberKeyboard();
        return newMessage().setText("Choose number of stocks to sell").setReplyMarkup(keyboard);
    }

    private SendMessage handleSell(String response) {
        var repository = BrokerBot.Repository;
        repository.setUserState(getChatID(), UserState.DEFAULT);
        var strStock = repository.getUser(getChatID()).previousReplies.get(0);
        var message = newMessage().setReplyMarkup(new ReplyKeyboardRemove());
        var user = repository.getUser(getChatID());
        var count = Integer.parseInt(response);
        var stock = Stock.valueOf(strStock);
        double price;
        try {
            price = repository.getQuote(strStock).getQuote().getPrice().doubleValue();
        } catch (IOException e) {
            e.printStackTrace();
            return message.setText("API unreachable try later");
        }
        var T = new TransactionImpl(getChatID(), stock, count, price, TransactionType.SELL);
        var result = repository.proceedTransaction(T);
        if (result)
            return message.setText(String.format("You sold %d %s stock(s) for %.2f \nNow you have %.2f$",
                    count, strStock, price * count, user.getUsdBalance()));
        else
            return message.setText("purchase error");

    }
}
