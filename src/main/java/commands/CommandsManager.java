package commands;

import org.reflections.Reflections;


import java.security.InvalidKeyException;
import java.security.KeyException;
import java.util.HashMap;
import java.util.Map;

public class CommandsManager {
    private static final Map<String, Class<? extends Command>> commands = new HashMap<>();

    public static void buildCommands() {
        var commandClasses = new Reflections(Command.class).getSubTypesOf(Command.class);
        for (var command : commandClasses) {
            if (command.isAnnotationPresent(CommandAnnotation.class)) {
                var annotation = command.getDeclaredAnnotation(CommandAnnotation.class);
                commands.put(annotation.name(), command);
            }
        }
    }

    public static Class<? extends Command> getCommand(String commandName) throws InvalidKeyException {
        if (!commands.containsKey(commandName))
            throw new InvalidKeyException();
        return commands.get(commandName);
    }
}

