package enums;

import java.util.Arrays;

public enum Stock {
    AAPL, GOOGL, AMD, IBM, INTC, BABA, TSLA, AMZN, FB, V, MSFT, NVDA, MA, NFLX, KO, DIS, BAC, F, SPOT, HPQ, NOK, TWTR,
    UBER, APA, PINS, ZM, CSCO, QCOM, JNJ, PEP;
    public static java.lang.String[] getNames() {
        return Arrays.stream(Stock.class.getEnumConstants()).map(Enum::name).toArray(java.lang.String[]::new);
    }
}
