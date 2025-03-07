//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//import com.pcda.lib.util.FileUtil;
import com.pcda.luk.ltc.enums.TargetDirectory2;
import com.pcda.luk.ltc.manager.WorkspaceConfigManager;
import com.pcda.luk.ltc.model.DeletionConfig;
import com.pcda.luk.ltc.model.WorkspaceConfig;
import com.pcda.luk.ltc.util.LogUtil;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Slf4j
@Command(
    name = "Clean command",
    requiredOptionMarker = '*'
)
public final class LtcCleanCommand implements Runnable {

    private static final class InstanceHolder{
        private static final LtcCleanCommand INSTANCE = new LtcCleanCommand();

        private InstanceHolder() { /* empty constructor */ }
    }

    @Option(
        names = {"-l", "--location"},
        description = "The path to the workspace. A '.' can be set to select the current directory",
        required = true
    )
    private String location;

    @Option(
        names = {"-c", "--no-conf"},
        description = "Set this argument to ignore the configurations in conf.json"
    )
    private boolean noConf;

    @Option(
        names = {"-i", "--no-includes"},
        description = "Set this argument to ignore the included arguments (inc-args) in conf.json"
    )
    private boolean noIncludes;

    @Option(
        names = {"-a", "--all"},
        description = "Set this argument to clear all temp directories"
    )
    private boolean clearAll;

    @Option(
        names = "--c-work",
        description = "Set this argument to add the work directory for clean up"
    )
    private boolean clearWork;

    @Option(
        names = "--c-osgi-state",
        description = "Set this argument to add the osgi/state directory for clean up"
    )
    private boolean clearOsgiState;

    @Option(
        names = "--c-tomcat-temp",
        description = "Set this argument to add the tomcat/temp directory for clean up"
    )
    private boolean clearTomcatTemp;

    @Option(
        names = "--c-tomcat-work",
        description = "Set this argument to add the tomcat/work directory for clean up"
    )
    private boolean clearTomcatWork;

    private LtcCleanCommand() { /* empty constructor */ }

    public static LtcCleanCommand getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void run() {
        log.debug("Start running clean command");
        log.debug("Command status: \n{}", this.toString());
        if (".".equals(location)) {
            location = System.getProperty("user.dir");
        }
        final Path path = Path.of(location);
        if (!Files.exists(path)) {
            log.warn("The provided path: {} doesn't exist. Exiting the programm.", location);
            return;
        }
        if (!Files.isDirectory(path)) {
            log.warn("The provided path: {} isn't a directory. Exiting the programm.", location);
            return;
        }

        final WorkspaceConfig workspaceConfig;
        if (!noConf) {
            WorkspaceConfigManager.read();
            final String workspaceName = path.getFileName().toString();
            if (WorkspaceConfigManager.isWorkspaceConfigPresent(workspaceName)) {
                log.info("Found configs for: {}", workspaceName);
                workspaceConfig = WorkspaceConfigManager.getWorkspaceConfig(workspaceName);
            } else {
                log.info("Found no configs for: {}", workspaceName);
                workspaceConfig = WorkspaceConfig.DEFAULT_INSTANCE;
            }
            if (!noIncludes) {
                applyDeletionConfig(workspaceConfig.getDeletionConfig());
            }
        } else {
            workspaceConfig = WorkspaceConfig.DEFAULT_INSTANCE;
        }

        if (clearAll) {
            clearWork = true;
            clearOsgiState = true;
            clearTomcatTemp = true;
            clearTomcatWork = true;
        }

        final List<Path> cleanUpPaths = initCleanUpPaths(workspaceConfig);
        log.info("Selected: {} target directories for clean up", cleanUpPaths.size());
        for (final Path cleanUpPath : cleanUpPaths) {
            log.info("> {}", cleanUpPath.toAbsolutePath().toString());
        }
        System.out.println();
        executeDeletions(cleanUpPaths);
        System.out.println();
        log.debug("Finished running clean command");
    }

    @Override
    public String toString() {
        final StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<(LtcCleanCommand)> \n");
        strBuilder.append("> location: %s \n");
        strBuilder.append("> noConf: %b \n");
        strBuilder.append("> noIncludes: %b \n");
        strBuilder.append("> clearAll: %b \n");
        strBuilder.append("> clearWork: %b \n");
        strBuilder.append("> clearOsgiState: %b \n");
        strBuilder.append("> clearTomcatTemp: %b \n");
        strBuilder.append("> clearTomcatWork: %b \n");
        return String.format(
            strBuilder.toString(),
            location, noConf, noIncludes, clearAll, clearWork, clearOsgiState, clearTomcatTemp, clearTomcatWork
        );
    }

    private void applyDeletionConfig(final DeletionConfig deletionConfig) {
        if (deletionConfig.isWorkDeletionEnabled()) {
            clearWork = true;
        }
        if (deletionConfig.isOsgiStateDeletionEnabled()) {
            clearOsgiState = true;
        }
        if (deletionConfig.isTomcatTempDeletionEnabled()) {
            clearTomcatTemp = true;
        }
        if (deletionConfig.isTomcatWorkDeletionEnabled()) {
            clearTomcatWork = true;
        }
    }

    private List<Path> initCleanUpPaths(final WorkspaceConfig workspaceConfig) {
        final List<Path> cleanUpPaths = new ArrayList<>();
        final Path basePath = Path.of(location).resolve("bundles").resolve(workspaceConfig.getLiferayHomeName());
        if (clearWork) {
            final Path workPath = basePath.resolve("work");
            if (Files.exists(workPath)) {
                cleanUpPaths.add(workPath);
            } else {
                log.warn("{} was targeted for clean up but doesn't exist", workPath.toAbsolutePath().toString());
            }
        }
        if (clearOsgiState) {
            final Path osgiStatePath = basePath.resolve("osgi").resolve("state");
            if (Files.exists(osgiStatePath)) {
                cleanUpPaths.add(osgiStatePath);
            } else {
                log.warn("{} was targeted for clean up but doesn't exist", osgiStatePath.toAbsolutePath().toString());
            }
        }
        if (clearTomcatTemp) {
            final Path tomcatTempPath = basePath.resolve(workspaceConfig.getTomcatHomeName()).resolve("temp");
            if (Files.exists(tomcatTempPath)) {
                cleanUpPaths.add(tomcatTempPath);
            } else {
                log.warn("{} was targeted for clean up but doesn't exist", tomcatTempPath.toAbsolutePath().toString());
            }
        }
        if (clearTomcatWork) {
            final Path tomcatWorkPath = basePath.resolve(workspaceConfig.getTomcatHomeName()).resolve("work");
            if (Files.exists(tomcatWorkPath)) {
                cleanUpPaths.add(tomcatWorkPath);
            } else {
                log.warn("{} was targeted for clean up but doesn't exist", tomcatWorkPath.toAbsolutePath().toString());
            }
        }
        return cleanUpPaths;
    }

    private void executeDeletions(final List<Path> cleanUpPaths) {
        final List<Exception> exceptions = new ArrayList<>();
        for (final Path cleanUpPath : cleanUpPaths) {
            /*try {
                FileUtil.cleanDirectory(cleanUpPath);
                log.info("FINISHED cleaning up: {}", cleanUpPath.toAbsolutePath().toString());
            } catch (IOException e) {
                exceptions.add(e);
                log.error("An error occured for: {}. Please check the logs", cleanUpPath.toAbsolutePath().toString());
            }*/
        }
        if (!exceptions.isEmpty()) {
            LogUtil.createLogFile(exceptions);
        }
    }

}
