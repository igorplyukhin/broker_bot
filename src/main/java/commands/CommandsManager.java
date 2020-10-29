package commands;

import answer.Answer;
import answer.AnswerAnnotation;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

public class CommandsManager {
    private static final Map<String, Class<? extends Command>> commands = new HashMap<>();
    private static final Map<String, Class<? extends Answer>> answers = new HashMap<>();
    private static final Reflections reflections = new Reflections(Command.class);

    public static void buildCommands() {
        var commandClasses = reflections.getSubTypesOf(Command.class);
        for (var command : commandClasses) {
            if (command.isAnnotationPresent(CommandAnnotation.class)) {
                var annotation = command.getDeclaredAnnotation(CommandAnnotation.class);
                commands.put(annotation.name(), command);
            }
        }
    }

    public static void buildAnswers() {
        var answerClasses = reflections.getSubTypesOf(Answer.class);
        for (var answer : answerClasses) {
            if (answer.isAnnotationPresent(AnswerAnnotation.class)) {
                var annotation = answer.getDeclaredAnnotation(AnswerAnnotation.class);
                answers.put(annotation.name(), answer);
            }
        }
    }

    public static Class<? extends Command> getCommand(String commandName) {
        if (!commands.containsKey(commandName))
            return commands.get("/unknown");
        return commands.get(commandName);
    }
}

