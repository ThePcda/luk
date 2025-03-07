//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.pcda.luk.ltc.constant.FileConstant;
import com.pcda.luk.ltc.model.WorkspaceConfig;
import com.pcda.luk.ltc.util.WorkspaceConfigMapperUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class WorkspaceConfigManager {

    private static final class InstanceHolder {
        private static final WorkspaceConfigManager INSTANCE = new WorkspaceConfigManager();

        private InstanceHolder() { /* empty constructor */ }
    }

    private final Map<String, WorkspaceConfig> workspaceConfigs = new HashMap<>();

    private WorkspaceConfigManager() { /* empty constructor */ }

    public static void read() {
        if (!Files.exists(FileConstant.CONFIG_FILE_PATH) || !Files.isRegularFile(FileConstant.CONFIG_FILE_PATH)) {
            log.info("No {} file is present", FileConstant.CONFIG_FILE_NAME);
            return;
        }
        final String jsonConfigContent;
        try {
            jsonConfigContent = new String(Files.readAllBytes(FileConstant.CONFIG_FILE_PATH));
        } catch (IOException e) {
            log.error("An error occured while reading configs", e.toString());
            return;
        }
        final JSONObject jsonConfig = new JSONObject(jsonConfigContent);
        for (final String workspaceName : jsonConfig.keySet()) {
            InstanceHolder.INSTANCE.workspaceConfigs.put(workspaceName, WorkspaceConfigMapperUtil.fromJson(jsonConfig.getJSONObject(workspaceName)));
        }
    }

    public static WorkspaceConfig getWorkspaceConfig(final String workspaceName) {
        return InstanceHolder.INSTANCE.workspaceConfigs.get(workspaceName);
    }

    public static final boolean isWorkspaceConfigPresent(final String workspaceName) {
        return getWorkspaceConfig(workspaceName) != null;
    }

}
