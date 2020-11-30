package enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Stock {
    AAPL, GOOGL, AMD, IBM, INTC, BABA, TSLA, AMZN, FB, V, MSFT, NVDA, MA, NFLX, KO, DIS, BAC, F, SPOT, HPQ, NOK, TWTR,
    UBER, APA, PINS, ZM, CSCO, QCOM, JNJ, PEP;
    public static List<String> getNames() {
        var l = new ArrayList<String>();
        for (var e: Stock.values())
            l.add(e.toString());
        return l;
    }
}
