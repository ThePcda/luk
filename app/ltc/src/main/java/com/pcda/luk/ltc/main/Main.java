//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.main;

import com.pcda.luk.ltc.command.LtcAddCommand;
import com.pcda.luk.ltc.command.LtcClearCommand;
import com.pcda.luk.ltc.command.LtcMainCommand;

import picocli.CommandLine;

public final class Main {

    public static void main(final String[] args) {
        final CommandLine commandLine = new CommandLine(LtcMainCommand.getInstance());

        commandLine.addSubcommand("clear", LtcClearCommand.getInstance());
        commandLine.addSubcommand("add", LtcAddCommand.getInstance());

        System.exit(commandLine.execute(args));
    }

}
