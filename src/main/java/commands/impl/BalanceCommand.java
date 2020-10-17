package commands.impl;

import commands.Command;
import commands.CommandAnnotation;
import di.Factories;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import repository.RepositoryImpl;

@CommandAnnotation(name = "/balance", description = "Show user's balance")
public class BalanceCommand implements Command {
    @Override
    public SendMessage execute(Update update) {
        long chatID = update.getMessage().getChatId();
        var message = new SendMessage().setChatId(chatID);
        var user = Factories.getRepository(RepositoryImpl.class.getName()).getUser(chatID);
        var messageText = "";
        if (user == null)
            messageText = "User does not exist";
        else
            messageText = user.toStringBalance();
        return message.setText(messageText);
    }
}
