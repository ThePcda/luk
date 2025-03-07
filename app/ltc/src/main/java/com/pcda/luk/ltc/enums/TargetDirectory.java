//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.enums;

import java.nio.file.Path;
import java.util.function.BiFunction;

import com.pcda.luk.ltc.model.WorkspaceConfig;

public enum TargetDirectory {

    WORK((loc, conf) -> getBasePath(loc, conf.getLiferayHomeName()).resolve("work")),
    OSGI_STATE((loc, conf) -> getBasePath(loc, conf.getLiferayHomeName()).resolve("osgi").resolve("state")),
    TOMCAT_TEMP((loc, conf) -> getBasePath(loc, conf.getLiferayHomeName()).resolve(conf.getTomcatHomeName()).resolve("temp")),
    TOMCAT_WORK((loc, conf) -> getBasePath(loc, conf.getLiferayHomeName()).resolve(conf.getTomcatHomeName()).resolve("work"));

    private final BiFunction<String, WorkspaceConfig, Path> pathCreator;

    private TargetDirectory(final BiFunction<String, WorkspaceConfig, Path> pathCreator) {
        this.pathCreator = pathCreator;
    }

    public Path getPath(final String location, final WorkspaceConfig workspaceConfig) {
        return pathCreator.apply(location, workspaceConfig);
    }

    private static Path getBasePath(final String location, final String liferayHomeName) {
        return Path.of(location).resolve("bundles").resolve(liferayHomeName);
    }

}
