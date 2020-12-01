package commands.impl;

import brokerBot.BrokerBot;
import commands.command.Command;
import commands.command.CommandAnnotation;
import commands.replyCommand.ReplyCommand;
import commands.replyCommand.ReplyCommandAnnotation;
import enums.CommandName;
import enums.UserState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import repository.Repository;
import yahoofinance.Stock;

import java.io.IOException;
import java.util.Locale;

@ReplyCommandAnnotation(name = UserState.WAITING_ADD_QUOTE)
@CommandAnnotation(name = CommandName.ADD_QUOTE)
public class VipAddQuote extends Command implements ReplyCommand {
    public VipAddQuote(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        BrokerBot.Repository.setUserState(getChatID(), UserState.WAITING_ADD_QUOTE);
        String text;
        var user = BrokerBot.Repository.getUser(getChatID());
        if (user.isVip)
            text = "Введи символ акции";
        else
            text = "У тебя нет VIP аккаунта, шалунишка";
        return newMessage().setText(text).setReplyMarkup(new ReplyKeyboardRemove());
    }

    @Override
    public SendMessage handleReply(UserState userState, String response) {
        return switch (userState) {
            case WAITING_ADD_QUOTE -> handleAddition(response);
            default -> newMessage().setText("Error");
        };
    }

    private SendMessage handleAddition(String quoteName) {
        var repo = BrokerBot.Repository;
        repo.setUserState(getChatID(), UserState.DEFAULT);
        var user = repo.getUser(getChatID());
        quoteName = quoteName.toUpperCase(Locale.ROOT);
        Stock stock;
        try {
            stock = repo.getQuote(quoteName);
        } catch (IOException e) {
            e.printStackTrace();
            return newMessage().setText(repo.Mock);
        }
        if (stock == null || stock.getQuote().getPrice() == null )
            return newMessage().setText("Нет такой акции");
        else if(user.getExtraQuotes().contains(quoteName))
            return newMessage().setText("Ты уже добавил эту акцию");
        repo.addExtraQuoteToUser(user, quoteName);
        return newMessage().setText("Успешно добавил твою акцию");
    }
}