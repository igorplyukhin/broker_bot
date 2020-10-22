package BrokerBot;

import commands.CommandsManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import repository.ApiRepository;
import repository.Repository;
import repository.TestRepository;

import java.lang.reflect.InvocationTargetException;


public class BrokerBot extends TelegramLongPollingBot {
    public static final Repository Repository = new ApiRepository();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var commandName = getCommandName(update.getMessage().getText());
            try {
                var command = CommandsManager.getCommand(commandName)
                        .getDeclaredConstructor(Update.class).newInstance(update);
                var message = command.execute();
                execute(message); // Call method to send the message
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                    | NoSuchMethodException | TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "java_broker_bot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("BrokerBotToken");
    }

    private String getCommandName(String text) {
        String[] splitText = text.split(" ");
        return splitText[0];
    }
}