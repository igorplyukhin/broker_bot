package commands.impl;

import brokerBot.BrokerBot;
import commands.command.Command;
import commands.command.CommandAnnotation;
import enums.CommandName;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@CommandAnnotation(name = CommandName.INCREASE_BALANCE)
public class VipIncreaseBalance extends Command {
    public VipIncreaseBalance(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        var user = BrokerBot.Repository.getUser(getChatID());
        if (!user.isVip)
            return newMessage().setText("У тебя нет VIP аккаунта, шалунишка");
        BrokerBot.Repository.increaseUserBalance(user);
        return newMessage().setText(String.format("Лови аптечку:)\n\nТеперь у тебя:\n%s", user.toStringBalance()));
    }
}
