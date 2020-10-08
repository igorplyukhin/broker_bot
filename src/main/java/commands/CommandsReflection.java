package commands;

import org.reflections.Reflections;


import java.util.HashMap;
import java.util.Map;

public class CommandsReflection {
    private static final Map<String, Class<? extends Command>> commands = new HashMap<>();

    public static void buildCommands(){
        var commandClasses = new Reflections(Command.class).getSubTypesOf(Command.class);
        for(var command : commandClasses) {
            System.out.println(command);
            if (command.isAnnotationPresent(CommandAnnotation.class)) {
                var annotation = command.getDeclaredAnnotation(CommandAnnotation.class);
                commands.put(annotation.name(), command);
            }
        }
    }
}

