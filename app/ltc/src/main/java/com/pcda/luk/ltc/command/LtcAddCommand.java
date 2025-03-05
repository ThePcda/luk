//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.command;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "Add command",
    requiredOptionMarker = '*'
)
public final class LtcAddCommand implements Runnable {

    private static final class InstanceHolder {
        private static final LtcAddCommand INSTANCE = new LtcAddCommand();

        private InstanceHolder() { /* empty constructor */ }
    }

    @Option(
        names = {"-n", "--ws-name"}
    )
    private String workspaceName;

    private LtcAddCommand() { /* empty constructor */ }

    public static LtcAddCommand getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void run() {
        System.out.println(workspaceName);
    }

}
