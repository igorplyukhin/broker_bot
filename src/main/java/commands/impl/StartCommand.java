package commands.impl;

import commands.command.Command;
import commands.command.CommandAnnotation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@CommandAnnotation(name = "/start", description = "register user")
public class StartCommand extends Command {
    public StartCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        return newMessage().setText("\uD83D\uDCC8Hello, I'm Broker Bot\uD83D\uDCC8\n" +
                "1000$ is your start-up capital\n" +
                "You can start from \\market command to see stock prices\n" +
                "Buy some, wait a bit and try to sell at the highest price\n" +
                "Find out if you are good trader :)");

    }
}
