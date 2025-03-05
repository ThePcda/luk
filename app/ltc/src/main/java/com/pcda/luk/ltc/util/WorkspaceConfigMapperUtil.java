//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.util;

import org.json.JSONObject;

import com.pcda.luk.ltc.constant.WorkspaceConfigConstant;
import com.pcda.luk.ltc.model.DeletionConfig;
import com.pcda.luk.ltc.model.WorkspaceConfig;

public final class WorkspaceConfigMapperUtil {

    private WorkspaceConfigMapperUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static WorkspaceConfig fromJson(final JSONObject jsonObject) {
        final String liferayHomeName = getLiferayHomeName(jsonObject);
        final String tomcatHomeName = getTomcatHomeName(jsonObject);
        final DeletionConfig deletionConfig = DeletionConfigMapperUtil.fromJson(jsonObject);
        return WorkspaceConfig.init(liferayHomeName, tomcatHomeName, deletionConfig);
    }

    private static String getLiferayHomeName(final JSONObject jsonObject) {
        final String liferayHomeName;
        if (jsonObject.keySet().contains(WorkspaceConfigConstant.KEY_LIFERAY_HOME_NAME)) {
            liferayHomeName = jsonObject.getString(WorkspaceConfigConstant.KEY_LIFERAY_HOME_NAME);
        } else {
            liferayHomeName = WorkspaceConfigConstant.DEFAULT_LIFERAY_HOME_NAME;
        }
        return liferayHomeName;
    }

    private static String getTomcatHomeName(final JSONObject jsonObject) {
        final String tomcatHomeName;
        if (jsonObject.keySet().contains(WorkspaceConfigConstant.KEY_TOMCAT_HOME_NAME)) {
            tomcatHomeName = jsonObject.getString(WorkspaceConfigConstant.KEY_TOMCAT_HOME_NAME);
        } else {
            tomcatHomeName = WorkspaceConfigConstant.DEFAULT_TOMCAT_HOME_NAME;
        }
        return tomcatHomeName;
    }

}
