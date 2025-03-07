package com.pcda.luk.ltc.enums;

import java.nio.file.Path;

import com.pcda.luk.ltc.model.WorkspaceConfig;

public enum TargetDirectory2 {

    WORK("work"),
    OSGI_STATE("osgi/state"),
    TOMCAT_TEMP("%s/temp"),
    TOMCAT_WORK("%s/work");

    /**
     * {workspace}/bundles/{liferay-home}
     */
    private static final String BASE_PATH = "%s/bundles/%s";

    private final String subPath;

    private TargetDirectory2(final String subPath) {
        this.subPath = subPath;
    }

    public Path getPath(final String location, final WorkspaceConfig conf) {
        return Path.of(String.format(BASE_PATH, location, conf.getLiferayHomeName()))
            .resolve(subPath.contains("%s") ? String.format(subPath, conf.getTomcatHomeName()) : subPath);
    }

}
