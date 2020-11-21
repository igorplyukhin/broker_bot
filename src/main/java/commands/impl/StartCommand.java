package commands.impl;

import brokerBot.BrokerBot;
import commands.Command;
import commands.CommandAnnotation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@CommandAnnotation(name = "/start", description = "register user")
public class StartCommand extends Command {
    public StartCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        var message = createNewMessage();
        var result = BrokerBot.Repository.createUser(getChatID());
        if (result == null)
            return message.setText("User already exists");
        else
            return message.setText("User created");
    }
}
