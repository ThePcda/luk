//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.pcda.luk.ltc.contract.LtcCommand;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;

@Slf4j
public final class CommandManager {

    private static final class InstanceHolder {
        private static final CommandManager INSTANCE = new CommandManager();

        private InstanceHolder() { /* empty constructor */ }
    }

    private final Map<Class<?>, LtcCommand> commands = new HashMap<>();

    private CommandManager() { /* empty constructor */ }

    public static void a() {
        final Reflections r = new Reflections("com.pcda.luk.ltc");
        Set<Class<?>> commandClasses = r.getTypesAnnotatedWith(Command.class);
        for (final Class<?> clazz : commandClasses) {
            System.out.println(clazz);
        }
    }

    public static void register(final Class<?> commandClass, final LtcCommand command) {
        InstanceHolder.INSTANCE.commands.put(commandClass, command);
    }

}
