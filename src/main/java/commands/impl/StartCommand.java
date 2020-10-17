package commands.impl;

import commands.Command;
import commands.CommandAnnotation;
import di.Factories;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import repository.RepositoryImpl;


@CommandAnnotation(name = "/start", description = "register user")
public class StartCommand implements Command {
    @Override
    public SendMessage execute(Update update) {
        long chatID = update.getMessage().getChatId();
        var message = new SendMessage().setChatId(chatID);
        var result = Factories.getRepository(RepositoryImpl.class.getName()).createUser(chatID);
        var messageText = "";
        if (result == null)
            messageText = "User already exists";
        else
            messageText = "User created";

        return message.setText(messageText);
    }
}
