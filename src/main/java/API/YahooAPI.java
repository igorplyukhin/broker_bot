package API;

import com.fasterxml.jackson.databind.type.MapLikeType;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class YahooAPI {
    private static List<String> stocks = Arrays.asList("AMD", "IBM", "AAPl");
    public static Object getQuotes() throws UnirestException {
        var response = Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes")
                .queryString("symbols", stocks)
                .queryString("region","US")
                .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .asJson().toString();
        
        return response;
    }
}
