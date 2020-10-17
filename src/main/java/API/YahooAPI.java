package API;

import com.mashape.unirest.http.exceptions.UnirestException;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class YahooAPI {
    private static final String[] stocks = new String[]{"AMD", "IBM", "AAPl", "INTC", "BABA", "TSLA", "AIR.PA", "YHOO"};

    public static void getQuotes() throws UnirestException, IOException {
        Map<String, Stock> stocks = YahooFinance.get(YahooAPI.stocks);
        Stock intel = stocks.get("INTC");
        Stock airbus = stocks.get("AIR.PA");
    }
}
