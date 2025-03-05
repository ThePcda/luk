//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.constant;

public final class WorkspaceConfigConstant {

    public static final String KEY_LIFERAY_HOME_NAME = "liferay-home-name";
    public static final String KEY_TOMCAT_HOME_NAME = "tomcat-home-name";
    public static final String KEY_CLEAN_DIR = "clean-dir";

    public static final String DEFAULT_LIFERAY_HOME_NAME = "liferay";
    public static final String DEFAULT_TOMCAT_HOME_NAME = "tomcat";

    private WorkspaceConfigConstant() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

}
