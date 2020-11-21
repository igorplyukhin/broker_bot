package brokerBot;

import commands.CommandsManager;
import enums.State;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import repository.ApiRepository;
import repository.Repository;

import java.lang.reflect.InvocationTargetException;


public class BrokerBot extends TelegramLongPollingBot {
    public static final Repository Repository = new ApiRepository();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                var message = handleUpdate(update);
                execute(message);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException
                    | IllegalAccessException | TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private SendMessage handleUpdate(Update update) throws InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        var userState = Repository.getUserState(update.getMessage().getChatId());
        var userMessage = update.getMessage().getText();
        if (userState != null && userState != State.DEFAULT) {
            return CommandsManager.getAnswer(userState).getDeclaredConstructor(Update.class)
                    .newInstance(update).handleAnswer(userMessage);
        } else {
            var commandName = getCommandName(userMessage);
            return CommandsManager.getCommand(commandName).getDeclaredConstructor(Update.class)
                    .newInstance(update).execute();
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