package API;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Arrays;
import java.util.HashMap;


public class YahooAPI {
    private static final String stocks = String.join("%2C", Arrays.asList("AMD", "IBM", "AAPl"));

    public static HashMap<String, Double> getQuotes() throws UnirestException {
        var response = Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes")
                .queryString("symbols", stocks)
                .queryString("region", "US")
                .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "")//paste token
                .asJson()
                .getBody()
                .getObject()
                .getJSONObject("quoteResponse")
                .getJSONArray("result");

        var prices = new HashMap<String, Double>();
        for (int i = 0; i < response.length(); i++) {
            var stock = response.getJSONObject(i);
            prices.put(stock.get("longName").toString(), (Double) stock.get("postMarketPrice"));
        }

        return prices;
    }
}
