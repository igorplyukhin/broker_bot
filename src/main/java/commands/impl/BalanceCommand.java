package commands.impl;

import BrokerBot.BrokerBot;
import commands.Command;
import commands.CommandAnnotation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@CommandAnnotation(name = "/balance", description = "Show user's balance")
public class BalanceCommand extends Command {
    public BalanceCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        var message = getNewMessage();
        var user = BrokerBot.Repository.getUser(getChatID());
        if (user == null)
            return message.setText( "User does not exist");
        else
            return message.setText(user.toStringBalance());
    }
}
