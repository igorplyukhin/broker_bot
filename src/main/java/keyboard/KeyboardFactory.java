package keyboard;

import entities.User;
import enums.Active;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class KeyboardFactory {
    private static final int rowCount = 6;

    public ReplyKeyboardMarkup buildKeyboardFromArr(Object[] arr){
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
        var stocks = Active.getNames();
        return buildKeyboardFromArr(stocks);
    }

    public ReplyKeyboardMarkup buildUserStocksKeyboard(User user){
        var portfolio = user.getPortfolio().keySet().toArray();
        return buildKeyboardFromArr(portfolio);
    }
}
