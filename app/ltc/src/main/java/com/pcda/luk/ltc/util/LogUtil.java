//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.pcda.luk.ltc.constant.FileConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class LogUtil {

    private LogUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static final void createLogFile(final List<Exception> exception) {
        final Path logsPath;
        try {
            logsPath = Files.createDirectories(FileConstant.LOGS_FOLDER_PATH);
        } catch (IOException e) {
            log.error("An error occured while creating the logs folder. No logs can be created. {}", e);
            return;
        }
        Path logFile = logsPath.resolve("ltc" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss")) + ".log");
        try {
            Files.createDirectories(logFile);
        } catch (IOException e) {
            log.error("An error occured while creating a log file. {}", e);
            return;
        }
        final StringWriter sw = new StringWriter();
        final PrintWriter  pw = new PrintWriter(sw);
        int counter = 1;
        for (final Exception e : exception) {
            pw.append("============================================================\n");
            pw.append("===Error: " + counter + "=================================================\n");
            pw.append("============================================================\n\n");
            e.printStackTrace(pw);
            pw.append("\n");
            counter++;
        }
        try {
            Files.write(logFile, sw.toString().getBytes());
        } catch (IOException e) {
            log.error("An error occured while writing log content. {}", e);
            return;
        }
    }

}
