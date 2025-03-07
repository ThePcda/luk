//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.constant;

import java.net.URISyntaxException;
import java.nio.file.Path;

import com.pcda.luk.ltc.manager.WorkspaceConfigManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class FileConstant {

    private FileConstant() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    /*
     * Strings
     */

    public static final String CONFIG_FOLDER_NAME = "config";
    public static final String LOGS_FOLDER_NAME = "logs";
    public static final String SAVE_FOLDER_NAME = "save";

    public static final String CONFIG_FILE_NAME = "conf.json";
    public static final String PROPERTIES_NAME = "ext.properties";
    public static final String PROGRESSION_FILE_NAME = "save.dat";

    /*
     * Paths
     */

    public static final Path JAR_FILE_PATH = getJarFilePath();

    public static final Path CONFIG_FOLDER_PATH = JAR_FILE_PATH.getParent().getParent().resolve(CONFIG_FOLDER_NAME);
    public static final Path LOGS_FOLDER_PATH = JAR_FILE_PATH.getParent().getParent().resolve(LOGS_FOLDER_NAME);
    public static final Path SAVE_FOLDER_PATH = JAR_FILE_PATH.getParent().getParent().resolve(SAVE_FOLDER_NAME);

    public static final Path CONFIG_FILE_PATH = CONFIG_FOLDER_PATH.resolve(CONFIG_FILE_NAME);
    public static final Path PROPERTIES_FILE_PATH = CONFIG_FOLDER_PATH.resolve(PROPERTIES_NAME);
    public static final Path PROGRESSION_FILE_PATH = SAVE_FOLDER_PATH.resolve(PROGRESSION_FILE_NAME);

    private static Path getJarFilePath() {
        try {
            return Path.of(WorkspaceConfigManager.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (final URISyntaxException e) {
            log.error("Couldn't resolve jar path. {}", e);
            System.exit(1); //TODO check how this works
            throw new RuntimeException("Couldn't resolve jar path | Programm can't run");
        }
    }

}
