package commands.impl;

import brokerBot.BrokerBot;
import commands.command.Command;
import commands.command.CommandAnnotation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@CommandAnnotation(name="/transaction")
public class TransactionsHistory extends Command {
    public TransactionsHistory(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        var text = BrokerBot.Repository.getTransactionHistory(getChatID());
        return newMessage().setText(text);
    }
}
