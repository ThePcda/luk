//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.command;

import com.pcda.luk.ltc.manager.ProjectManager;
import com.pcda.luk.ltc.manager.UserProgressionManager;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Slf4j
@Command(
    name = "LTC",
    description = "Your tiny clean up fairy <3"
)
public final class LtcMainCommand implements Runnable {

    private final static class InstanceHolder {
        private static final LtcMainCommand INSTANCE = new LtcMainCommand();

        private InstanceHolder() { /* empty constructor */ }
    }

    private LtcMainCommand() { /* empty constructor */ }

    public static final LtcMainCommand getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Option(
        names = {"-v", "--version"},
        description = "Print the current version of this programm"
    )
    private boolean version;

    @Option(
        names = {"-p", "--print-progression"}
    )
    private boolean printProgression;

    @Override
    public void run() {
        if (version) {
            System.out.println(ProjectManager.getAttribute("Implementation-Version"));
        } else if (printProgression) {
            System.out.println(UserProgressionManager.userProgressionAsString());
        }
    }

}
