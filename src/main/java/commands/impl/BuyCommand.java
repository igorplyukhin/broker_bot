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


@ReplyCommandAnnotation(name = UserState.WAITING_BUY_PURCHASE, description = "buy stock")
@ReplyCommandAnnotation(name = UserState.WAITING_BUY_CHOOSE_COUNT, description = "aaa")
@CommandAnnotation(name = "\uD83D\uDCE5 Купить активы", description = "buy stock")
public class BuyCommand extends Command implements ReplyCommand {
    public BuyCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage handleReply(UserState userState, String response) {
        return switch (userState) {
            case WAITING_BUY_CHOOSE_COUNT -> handleSelectCount(response);
            case WAITING_BUY_PURCHASE -> handlePurchase(response);
            default -> newMessage().setText("default");
        };
    }

    @Override
    public SendMessage execute() {
        BrokerBot.Repository.setUserState(getChatID(), UserState.WAITING_BUY_CHOOSE_COUNT);
        var message = newMessage().setText("Choose quote to buy");
        var keyboard = BrokerBot.keyboardFac.buildAllStocksKeyboard();
        return message.setReplyMarkup(keyboard);
    }

    private SendMessage handleSelectCount(String response) {
        BrokerBot.Repository.setUserState(getChatID(), UserState.WAITING_BUY_PURCHASE);
        BrokerBot.Repository.getUser(getChatID()).previousReplies.set(0, response);
        var keyboard = BrokerBot.keyboardFac.buildNumberKeyboard();
        return newMessage().setText("Choose number of stocks to buy").setReplyMarkup(keyboard);
    }

    private SendMessage handlePurchase(String response) {
        var repository = BrokerBot.Repository;
        repository.setUserState(getChatID(), UserState.DEFAULT);
        var user = repository.getUser(getChatID());
        var strStock = user.previousReplies.get(0);
        var stock = Stock.valueOf(strStock);
        var count = Integer.parseInt(response);
        double price;
        try {
            price = repository.getQuote(strStock).getQuote().getPrice().doubleValue();
        } catch (IOException e) {
            e.printStackTrace();
            return newMessage().setText("Market is unreachable now");
        }
        var t = new TransactionImpl(getChatID(), stock, count, price, TransactionType.BUY);
        var result = repository.proceedTransaction(t);

        if (result)
            return newMessage().setText(String.format("You bought %d %s stock(s) for %.2f \nNow you have %.2f$",
                    count, strStock, price * count, user.getUsdBalance()));
        else
            return newMessage().setText("You don't have enough money");
    }
}
