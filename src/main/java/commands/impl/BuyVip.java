package commands.impl;

import commands.command.Command;
import commands.command.CommandAnnotation;
import enums.CommandName;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@CommandAnnotation(name = CommandName.BUYVIP)
public class BuyVip extends Command {
    protected BuyVip(Update update) {
        super(update);
    }

    @Override
    public SendMessage execute() {
        return newMessage().setText("⚡️ Акция ⚡\n️До конца 2020 года стоимость VIP аккаунта 29 руб.\n" +
                "Отработай свою стратегию на любых других акциях, например более дешевых акциях Российских компаний");
    }
}
