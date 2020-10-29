import BrokerBot.BrokerBot;
import com.mashape.unirest.http.exceptions.UnirestException;
import commands.CommandsManager;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

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

