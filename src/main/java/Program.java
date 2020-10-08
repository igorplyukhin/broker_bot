import com.mashape.unirest.http.exceptions.UnirestException;
import commands.CommandsManager;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Program{
    public static void main(String[] args) throws UnirestException {

        /*CommandsManager.buildCommands();
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new BrokerBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
        var r = API.YahooAPI.getQuotes();
        System.out.println(r);
    }
}

