package keyboard;

import enums.Active;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class KeyboardFactory {
    private static final int rowCount = 6;

    public ReplyKeyboardMarkup buildStockChoiceKeyboard() {
        var stocks = Active.getNames();
        var keyboardMarkup = new ReplyKeyboardMarkup().setOneTimeKeyboard(true);
        var keyboard = new ArrayList<KeyboardRow>();
        for (var row = 0; row < stocks.length; row += rowCount) {
            var keyboardRow = new KeyboardRow();
            for (var i = row; i < row + rowCount && i < stocks.length; i++) {
                keyboardRow.add(stocks[i]);
            }
            keyboard.add(keyboardRow);
        }
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
}
