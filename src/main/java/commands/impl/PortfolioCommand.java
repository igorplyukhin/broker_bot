package commands.impl;

import brokerBot.BrokerBot;
import commands.command.Command;
import commands.command.CommandAnnotation;
import enums.CommandName;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import enums.Stock;

import java.util.HashMap;

@CommandAnnotation(name= CommandName.PORTFOLIO, description = "blabla")
public class PortfolioCommand extends Command {
    public PortfolioCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        var user = BrokerBot.Repository.getUser(getChatID());
        return newMessage().setText(portfolioToString(user.getPortfolio())).enableMarkdown(true);
    }

    private String portfolioToString(HashMap<Stock, Integer> portfolio){
        var sb = new StringBuilder();
        for (var e : portfolio.keySet())
            sb.append(e).append(": ").append(portfolio.get(e)).append(" \r\n");
        return sb.toString();
    }
}
