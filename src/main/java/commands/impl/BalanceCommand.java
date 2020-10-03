package commands.impl;

import commands.Command;
import commands.CommandAnnotation;
import entities.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import repository.Repository;

@CommandAnnotation(name = "/balance", description = "Show user's balance")
public class BalanceCommand implements Command {
    @Override
    public SendMessage execute(Update update) {
        long chatID = update.getMessage().getChatId();
        var message = new SendMessage().setChatId(chatID);
        var user = Repository.getUser(chatID);
        var messageText = "";
        if (user == null)
            messageText = "User does not exist";
        else
            messageText = user.toStringBalance();
        return message.setText(messageText);
    }
}
