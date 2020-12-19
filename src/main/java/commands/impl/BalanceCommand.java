package commands.impl;

import brokerBot.BrokerBot;
import commands.command.Command;
import commands.command.CommandAnnotation;
import enums.CommandName;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@CommandAnnotation(name = CommandName.BALANCE, description = "Show user's balance")
public class BalanceCommand extends Command {
    public BalanceCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        var message = newMessage();
        var user = BrokerBot.Repository.getUser(getChatID());
        return message.setText(user.toStringBalance());
    }
}
