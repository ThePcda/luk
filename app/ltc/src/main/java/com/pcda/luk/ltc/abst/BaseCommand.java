//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.abst;

import com.pcda.luk.ltc.contract.LtcCommand;
import com.pcda.luk.ltc.manager.CommandManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseCommand implements LtcCommand {

    private BaseCommand() {
        CommandManager.register(this.getClass(), this);
    }

    @Override
    public final void run() {
        log.info("Start from BaseCommand");
        execute();
        log.info("End from BaseCommand");
    }

    public abstract void execute();

}
