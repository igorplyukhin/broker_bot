package commands.impl;

import BrokerBot.BrokerBot;
import answer.Answer;
import answer.AnswerAnnotation;
import commands.Command;
import commands.CommandAnnotation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@AnswerAnnotation(name = "WAITING_QUOTE_COMMAND_ANSWER", description = "Send quote price to user")
@CommandAnnotation(name = "/get_quote", description = "Show user's balance")
public class QuoteCommand extends Command implements Answer {
    public QuoteCommand(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute(String response) {
        return null;
    }

    @Override
    public SendMessage execute() {
        var quote = BrokerBot.Repository.getQuote("GOOGL").toString();
        if (quote == null)
            return createNewMessage().setText("API is unreachable now");

        return createNewMessage().setText(quote);
    }
}
