package commands.impl;

import BrokerBot.BrokerBot;
import commands.Command;
import commands.CommandAnnotation;
import di.Factories;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@CommandAnnotation(name = "/start", description = "register user")
public class StartCommand extends Command {
    public StartCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        var message = getNewMessage();
        var result = BrokerBot.Repository.createUser(getChatID());
        if (result == null)
            return message.setText("User already exists");
        else
            return message.setText("User created");
    }
}
