//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.progression;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Getter
@Setter
@EqualsAndHashCode
public final class UserProgression implements Serializable {

    private static final long serialVersionUID = 1L;

    private int executionAmount;

    @Override
    public String toString() {
        return String.format("Progression: Usage > %d", executionAmount);
    }

}
