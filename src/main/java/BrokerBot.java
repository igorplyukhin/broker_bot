import commands.CommandsManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;


public class BrokerBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var commandName = parseCommand(update.getMessage().getText());

            try {
                var command = CommandsManager.getCommand(commandName).getDeclaredConstructor().newInstance();
                var message = command.execute(update);
                execute(message); // Call method to send the message
            }
            catch (InstantiationException | IllegalAccessException | InvocationTargetException
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

    private String parseCommand(String text) {
        String[] splitText = text.split(" ");
        return splitText[0];
    }
}