package commands.impl;

import commands.command.Command;
import commands.command.CommandAnnotation;
import commands.replyCommand.ReplyCommand;
import enums.CommandName;
import enums.UserState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@CommandAnnotation(name = CommandName.ADD_QUOTE)
public class AddQuote extends Command implements ReplyCommand {
    public AddQuote(Update update) {super(update);}

    @Override
    public SendMessage execute() {
        return null;
    }

    @Override
    public SendMessage handleReply(UserState userState, String response) {
        return null;
    }
}
