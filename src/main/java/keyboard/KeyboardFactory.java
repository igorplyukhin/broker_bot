package keyboard;

import entities.User;
import enums.Stock;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyboardFactory {
    public ReplyKeyboardMarkup buildKeyboardFromArr(Object[] arr, int rowCount){
        var keyboardMarkup = new ReplyKeyboardMarkup().setOneTimeKeyboard(true);
        var keyboard = new ArrayList<KeyboardRow>();
        for (var row = 0; row < arr.length; row += rowCount) {
            var keyboardRow = new KeyboardRow();
            for (var i = row; i < row + rowCount && i < arr.length; i++) {
                keyboardRow.add(arr[i].toString());
            }
            keyboard.add(keyboardRow);
        }
        return keyboardMarkup.setKeyboard(keyboard);
    }

    public ReplyKeyboardMarkup buildAllStocksKeyboard() {
        var stocks = Stock.getNames();
        return buildKeyboardFromArr(stocks ,6);
    }

    public ReplyKeyboardMarkup buildUserStocksKeyboard(User user){
        var portfolio = user.getPortfolio().keySet().toArray();
        return buildKeyboardFromArr(portfolio, 6);
    }

    public ReplyKeyboardMarkup buildNumberKeyboard() {
        var values = Arrays.asList("1", "5", "10", "25", "50", "100");
        return buildKeyboardFromArr(values.toArray(), 2);
    }
}
