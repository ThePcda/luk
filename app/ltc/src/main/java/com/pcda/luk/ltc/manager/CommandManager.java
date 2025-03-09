//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.manager;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.reflections.Reflections;

import com.pcda.luk.ltc.contract.LtcCommand;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Slf4j
public final class CommandManager {

    private static final class InstanceHolder {
        private static final CommandManager INSTANCE = new CommandManager();

        private InstanceHolder() { /* empty constructor */ }
    }

    private final Map<Class<? extends LtcCommand>, LtcCommand> commands = new HashMap<>();

    private CommandManager() { /* empty constructor */ }

    public static void register() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Reflections r = new Reflections("com.pcda.luk.ltc");
        for (final Class<?> clazz : r.getTypesAnnotatedWith(Command.class)) {
            InstanceHolder.INSTANCE.commands.put((Class<? extends LtcCommand>) clazz, (LtcCommand) clazz.getConstructor().newInstance());
        }
    }

    public static LtcCommand get(final Class<? extends LtcCommand> clazz) {
        return InstanceHolder.INSTANCE.commands.get(clazz);
    }

    public static CommandLine initCmd(final Class<? extends LtcCommand> mainCommandClazz) {
         final Map<Class<?>, LtcCommand> commandsCopy = new HashMap<>(InstanceHolder.INSTANCE.commands);
         final CommandLine cmd = new CommandLine(commandsCopy.remove(mainCommandClazz));
         for (final Map.Entry<Class<?>, LtcCommand> entry : commandsCopy.entrySet()) {
             cmd.addSubcommand(entry.getValue().getClass().getAnnotation(Command.class).name(), entry.getValue());
         }
         return cmd;
    }

}
