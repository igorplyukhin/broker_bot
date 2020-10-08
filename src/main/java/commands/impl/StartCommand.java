package commands.impl;

import commands.Command;
import commands.CommandAnnotation;
import entities.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import repository.Repository;


@CommandAnnotation(name = "/start", description = "register user")
public class StartCommand implements Command {
    @Override
    public SendMessage execute(Update update) {
        long chatID = update.getMessage().getChatId();
        var message = new SendMessage().setChatId(chatID);
        var result = Repository.createUser(chatID);
        var messageText = "";
        if (result == null)
            messageText = "User already exists";
        else
            messageText = "User created";

        message.setText(messageText);
        return message;
    }
}
