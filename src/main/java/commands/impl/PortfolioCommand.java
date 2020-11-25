package commands.impl;

import brokerBot.BrokerBot;
import commands.Command;
import commands.CommandAnnotation;
import enums.Active;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@CommandAnnotation(name="/portfolio", description = "blabla")
public class PortfolioCommand extends Command {
    public PortfolioCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        var user = BrokerBot.Repository.getUser(getChatID());
        return newMessage().setText(user.getPortfolio().toString());
    }
}
