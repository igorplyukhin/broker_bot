package enums;

public enum CommandName {
    BALANCE("\uD83D\uDCB0 Баланс"), BUY("\uD83D\uDCE5 Купить активы"), PORTFOLIO("\uD83D\uDCBC Портфолио"),
    SELL("\uD83D\uDCE4  Продать активы"), MARKET("\uD83D\uDCCA Маркет"), GET_QUOTE("\uD83D\uDCC8 Инфо об акции"),
    HELP("\uD83D\uDC68\u200D\uD83D\uDCBB Помощь"), TRANSACTIONS("\uD83D\uDDC4 Транзакции"), START("/start"),
    UNKNOWN("/unknown");

    public final String label;

    CommandName(String label) {
        this.label = label;
    }
}
