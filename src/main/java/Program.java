import com.mashape.unirest.http.exceptions.UnirestException;
import commands.CommandsReflection;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import repository.Repository;

public class Program {

    public static Repository repository = new Repository();

    public static void main(String[] args) throws UnirestException {

        /*CommandsReflection.buildCommands();
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

