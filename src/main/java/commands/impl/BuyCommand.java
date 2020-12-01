package commands.impl;

import commands.replyCommand.ReplyCommand;
import commands.replyCommand.ReplyCommandAnnotation;
import brokerBot.BrokerBot;
import commands.command.Command;
import commands.command.CommandAnnotation;
import entities.transaction.TransactionImpl;
import enums.CommandName;
import enums.UserState;
import enums.TransactionType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import repository.Repository;
import yahoofinance.Stock;

import java.io.IOException;


@ReplyCommandAnnotation(name = UserState.WAITING_BUY_PURCHASE, description = "buy stock")
@ReplyCommandAnnotation(name = UserState.WAITING_BUY_CHOOSE_COUNT, description = "aaa")
@CommandAnnotation(name = CommandName.BUY, description = "buy stock")
public class BuyCommand extends Command implements ReplyCommand {
    public BuyCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage handleReply(UserState userState, String response) {
        return switch (userState) {
            case WAITING_BUY_CHOOSE_COUNT -> handleSelectCount(response);
            case WAITING_BUY_PURCHASE -> handlePurchase(response);
            default -> newMessage().setText("Critical error");
        };
    }

    @Override
    public SendMessage execute() {
        BrokerBot.Repository.setUserState(getChatID(), UserState.WAITING_BUY_CHOOSE_COUNT);
        var user = BrokerBot.Repository.getUser(getChatID());
        var message = newMessage().setText("Выбери акцию");
        var keyboard = BrokerBot.keyboardFac.buildAllStocksKeyboard(user);
        return message.setReplyMarkup(keyboard);
    }

    private SendMessage handleSelectCount(String quoteName) {
        BrokerBot.Repository.setUserState(getChatID(), UserState.WAITING_BUY_PURCHASE);
        BrokerBot.Repository.getUser(getChatID()).previousReplies.set(0, quoteName);
        var keyboard = BrokerBot.keyboardFac.buildNumberKeyboard();
        return newMessage().setText("Выбери количество").setReplyMarkup(keyboard);
    }

    private SendMessage handlePurchase(String strCount) {
        var repository = BrokerBot.Repository;
        repository.setUserState(getChatID(), UserState.DEFAULT);
        var user = repository.getUser(getChatID());
        var strStock = user.previousReplies.get(0);
        var count = Integer.parseInt(strCount);
        Stock stock;
        try {
            stock = repository.getQuote(strStock);
        } catch (IOException e) {
            e.printStackTrace();
            return newMessage().setText(repository.Mock);
        }
        var price = stock.getQuote().getPrice().doubleValue();
        var t = new TransactionImpl(getChatID(), stock, count, price, TransactionType.BUY);
        var result = repository.proceedTransaction(t);

        if (result)
            return newMessage().setText(String.format("Ты купил %d (%s) за %.2f \nТвой баланс %.2f$",
                    count, strStock, price * count, user.getUsdBalance()));
        else
            return newMessage().setText("Недостаточно денег(");
    }
}
