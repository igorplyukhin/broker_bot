package commands.impl;

import commands.Command;
import commands.CommandAnnotation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@CommandAnnotation(name = "/unknown", description = "no such command")
public class UnknownCommand extends Command {
    public UnknownCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        return createNewMessage().setText("Not Implemented");
    }
}
