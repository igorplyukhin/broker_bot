package commands.impl;

import brokerBot.BrokerBot;
import commands.command.Command;
import commands.command.CommandAnnotation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

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
