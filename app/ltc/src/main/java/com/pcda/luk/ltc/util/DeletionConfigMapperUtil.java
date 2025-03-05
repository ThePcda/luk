//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.util;

import java.util.function.BiConsumer;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pcda.luk.ltc.constant.FileConstant;
import com.pcda.luk.ltc.constant.WorkspaceConfigConstant;
import com.pcda.luk.ltc.model.DeletionConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DeletionConfigMapperUtil {

    private DeletionConfigMapperUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static DeletionConfig fromJson(final JSONObject jsonObject) {
        final JSONArray jsonArray;
        if (jsonObject.keySet().contains(WorkspaceConfigConstant.KEY_CLEAN_DIR)) {
            jsonArray = jsonObject.getJSONArray(WorkspaceConfigConstant.KEY_CLEAN_DIR);
        } else {
            return DeletionConfig.DEFAULT_INSTANCE;
        }
        final DeletionConfig.DeletionConfigBuilder builder = DeletionConfig.builder();
        for (final Object obj : jsonArray) {
            final String key;
            try {
                key = (String) obj;
            } catch (ClassCastException e) {
                log.error("{} in {} contained a non string value which isn't permitted. {}", WorkspaceConfigConstant.KEY_CLEAN_DIR, FileConstant.CONFIG_FILE_NAME, e);
                return DeletionConfig.DEFAULT_INSTANCE;
            }
            final BiConsumer<DeletionConfig.DeletionConfigBuilder, Boolean> setter = DeletionConfig.BUILDER_SETTER.get(key);
            if (setter == null) {
                log.warn("{} in {} contained an unknown value: {}.", WorkspaceConfigConstant.KEY_CLEAN_DIR, FileConstant.CONFIG_FILE_NAME, key);
            } else {
                setter.accept(builder, true);
            }
        }
        return builder.build();
    }

}
