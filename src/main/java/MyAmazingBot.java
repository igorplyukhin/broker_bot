import entities.User;
import enums.Currency;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

public class MyAmazingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId());
            switch (parseCommand(update.getMessage().getText())) {
                case "/start" -> {
                    User result = BrokerBot.repository.createUser(update.getMessage().getChatId());
                    if (result == null)
                        message.setText("User already exists");
                    else
                        message.setText("User created");
                }
                case "/balance" -> {
                    User user = BrokerBot.repository.getUser(update.getMessage().getChatId());
                    if (user == null)
                        message.setText("User does not exist");
                    else
                        message.setText(user.toStringBalance());
                }
            }
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
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

    private String parseCommand(String text){
        String[] splitText = text.split(" ");
        return splitText[0];
    }
}