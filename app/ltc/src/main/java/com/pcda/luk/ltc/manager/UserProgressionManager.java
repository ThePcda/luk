//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

import com.pcda.luk.ltc.constant.FileConstant;
import com.pcda.luk.ltc.progression.UserProgression;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UserProgressionManager {

    private static final class InstanceHolder {
        private static final UserProgressionManager INSTANCE = new UserProgressionManager();

        private InstanceHolder() { /* empty constructor */ }
    }

    private UserProgression userProgression;

    private UserProgressionManager() { /* empty constructor */ }

    public static void read() {
        log.debug("Start reading and initializing user progresseion");
        if (!Files.exists(FileConstant.SAVE_FOLDER_PATH)) {
            try {
                Files.createDirectories(FileConstant.SAVE_FOLDER_PATH);
            } catch (IOException e) {
                log.error("Couldn't create save directory. {}", e);
                return;
            }
        }
        if (!Files.exists(FileConstant.PROGRESSION_FILE_PATH) || !Files.isRegularFile(FileConstant.PROGRESSION_FILE_PATH)) {
            InstanceHolder.INSTANCE.userProgression = new UserProgression(0);
            log.debug("No save found. Created new user progression");
            return;
        }
        try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FileConstant.PROGRESSION_FILE_PATH.toFile()))) {
            InstanceHolder.INSTANCE.userProgression = (UserProgression) ois.readObject();
            log.debug("Successfully read and initialized user progression");
        } catch (final IOException | ClassNotFoundException e) {
            log.error("Couldn't read user progression. {}", e);
        }
    }

    public static void incrementExecutionAmount() {
        if (!isLoaded()) {
            log.debug("Tried to modify user progression but it wasn't loaded");
            return;
        }
        InstanceHolder.INSTANCE.userProgression.setExecutionAmount(InstanceHolder.INSTANCE.userProgression.getExecutionAmount() + 1);
    }

    public static void saveUserProgression() {
        log.debug("Start saving user progression");
        if (!isLoaded()) {
            log.debug("Tried to save user progression but it wasn't loaded");
            return;
        }
        try (final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileConstant.PROGRESSION_FILE_PATH.toFile()))) {
            oos.writeObject(InstanceHolder.INSTANCE.userProgression);
            log.debug("Successfully saved user progression");
        } catch (IOException e) {
            log.debug("Couldn't save user progression. {}", e);
        }
    }

    public static String userProgressionAsString() {
        return InstanceHolder.INSTANCE.userProgression.toString();
    }

    private static boolean isLoaded() {
        return InstanceHolder.INSTANCE.userProgression != null;
    }

}
