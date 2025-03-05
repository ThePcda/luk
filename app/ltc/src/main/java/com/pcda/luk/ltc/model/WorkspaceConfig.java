//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.model;

import com.pcda.luk.ltc.constant.WorkspaceConfigConstant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(
    access = AccessLevel.PUBLIC,
    staticName = "init"
)
public final class WorkspaceConfig {

    public static final WorkspaceConfig DEFAULT_INSTANCE = WorkspaceConfig.init(
        WorkspaceConfigConstant.DEFAULT_LIFERAY_HOME_NAME,
        WorkspaceConfigConstant.DEFAULT_TOMCAT_HOME_NAME,
        DeletionConfig.DEFAULT_INSTANCE
    );

    private final String liferayHomeName;
    private final String tomcatHomeName;
    private final DeletionConfig deletionConfig;

}
