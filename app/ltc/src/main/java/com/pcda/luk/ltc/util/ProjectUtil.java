//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.util;

public final class ProjectUtil {

    private ProjectUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static String getProjectVersion() {
        return ProjectUtil.class.getPackage().getImplementationVersion();
    }

}
