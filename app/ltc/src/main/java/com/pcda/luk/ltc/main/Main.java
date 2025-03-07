//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.main;

import com.pcda.luk.ltc.command.LtcAddCommand;
import com.pcda.luk.ltc.command.LtcCleanCommand;
import com.pcda.luk.ltc.command.LtcMainCommand;
import com.pcda.luk.ltc.manager.CommandManager;
import com.pcda.luk.ltc.manager.ProjectManager;
import com.pcda.luk.ltc.manager.UserProgressionManager;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
public final class Main {

    static {
        ProjectManager.read();
        UserProgressionManager.read();
    }

    public static void main(final String[] args) {
        CommandManager.a();
        final CommandLine commandLine = new CommandLine(LtcMainCommand.getInstance());

        commandLine.addSubcommand("clean", LtcCleanCommand.getInstance());
        commandLine.addSubcommand("add", LtcAddCommand.getInstance());

        UserProgressionManager.incrementExecutionAmount();
        UserProgressionManager.saveUserProgression();

        System.exit(commandLine.execute(args));
    }

}
