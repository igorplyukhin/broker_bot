import entities.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class BrokerBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId());
            System.out.println(update.getMessage().getText());
            String messageText = defineCommand(update.getMessage().getText(), update.getMessage().getChatId());
            message.setText(messageText);
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }



    private String defineCommand(String messageText, long chatID) {
        switch (parseCommand(messageText)) {
            case "/start" -> {
                User result = Program.repository.createUser(chatID);
                if (result == null)
                    return "User already exists";
                else
                    return "User created";
            }
            case "/balance" -> {
                User user = Program.repository.getUser(chatID);
                if (user == null)
                    return "User does not exist";
                else
                    return user.toStringBalance();
            }
            default -> {
                return "NotImplemented";
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