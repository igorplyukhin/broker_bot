package answer;


import commands.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


public interface Answer {
    SendMessage handleAnswer(String response);
}