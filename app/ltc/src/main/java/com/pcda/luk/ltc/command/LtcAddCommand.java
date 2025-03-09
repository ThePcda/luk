//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.command;

import com.pcda.luk.ltc.contract.LtcCommand;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "add",
    requiredOptionMarker = '*'
)
public final class LtcAddCommand implements LtcCommand {

    @Option(
        names = {"-n", "--ws-name"}
    )
    private String workspaceName;

    @Override
    public void run() {
        System.out.println(workspaceName);
    }

}
