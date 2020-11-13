import brokerBot.BrokerBot;
import commands.CommandsManager;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Program{
    public static void main(String[] args) {
        CommandsManager.buildCommands();
        CommandsManager.buildAnswers();
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new BrokerBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

