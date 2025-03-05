//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.constant;

import java.net.URISyntaxException;
import java.nio.file.Path;

import com.pcda.luk.ltc.reader.WorkspaceConfigReader;

public final class FileConstant {

    private FileConstant() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    /*
     * Strings
     */

    public static final String CONFIG_FOLDER_NAME = "config";

    public static final String LOGS_FOLDER_NAME = "logs";

    public static final String CONFIG_FILE_NAME = "conf.json";

    /*
     * Paths
     */

    public static final Path JAR_FILE_PATH = getJarFilePath();

    public static final Path CONFIG_FOLDER_PATH = JAR_FILE_PATH.getParent().getParent().resolve(CONFIG_FOLDER_NAME);

    public static final Path CONFIG_FILE_PATH = CONFIG_FOLDER_PATH.resolve(CONFIG_FILE_NAME);

    public static final Path LOGS_FOLDER_PATH = JAR_FILE_PATH.getParent().getParent().resolve(LOGS_FOLDER_NAME);

    private static final Path getJarFilePath() {
        try {
            return Path.of(WorkspaceConfigReader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            System.out.println("Couldn't resolve jar path");
            e.printStackTrace();
            throw new RuntimeException("Couldn't resolve jar path | Programm can't run");
        }
    }

}
