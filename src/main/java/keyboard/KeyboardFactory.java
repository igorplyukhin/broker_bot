package keyboard;

import entities.User;
import enums.CommandName;
import enums.Stock;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class KeyboardFactory {
    private final static String[] numbers = {"1", "2", "5", "10", "25", "50"};
    private final static String[] mainMenuButtons = {CommandName.BALANCE.label, CommandName.BUY.label,
            CommandName.PORTFOLIO.label, CommandName.SELL.label, CommandName.MARKET.label, CommandName.GET_QUOTE.label,
            CommandName.HELP.label, CommandName.TRANSACTIONS.label};

    public ReplyKeyboardMarkup buildAllStocksKeyboard() {
        var stocks = Stock.getNames();
        return buildKeyboardFromArr(stocks, 6);
    }

    public ReplyKeyboardMarkup buildUserStocksKeyboard(User user) {
        var portfolio = user.getPortfolio().keySet().toArray();
        return buildKeyboardFromArr(portfolio, 6);
    }

    public ReplyKeyboardMarkup buildNumberKeyboard() {
        return buildKeyboardFromArr(numbers, 2);
    }

    public ReplyKeyboardMarkup buildMainMenu() {
        return buildKeyboardFromArr(mainMenuButtons, 2).setOneTimeKeyboard(false);
    }

    private ReplyKeyboardMarkup buildKeyboardFromArr(Object[] arr, int rowCount) {
        var keyboardMarkup = new ReplyKeyboardMarkup();
        var keyboard = new ArrayList<KeyboardRow>();
        for (var row = 0; row < arr.length; row += rowCount) {
            var keyboardRow = new KeyboardRow();
            for (var i = row; i < row + rowCount && i < arr.length; i++) {
                keyboardRow.add(arr[i].toString());
            }
            keyboard.add(keyboardRow);
        }
        return keyboardMarkup.setKeyboard(keyboard).setResizeKeyboard(true);
    }
}
