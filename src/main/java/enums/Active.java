package enums;

import java.util.Arrays;

public enum Active {
    USD, AAPL, GOOGL, AMD, IBM, INTC, BABA, TSLA, AMZN, FB, V, MSFT, NVDA, MA, NFLX, KO, DIS, BAC;

    public static java.lang.String[] getNames() {
        return Arrays.stream(Active.class.getEnumConstants()).map(Enum::name).toArray(java.lang.String[]::new);
    }
}
