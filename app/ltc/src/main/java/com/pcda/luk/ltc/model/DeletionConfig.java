//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.model;

import java.util.Map;
import java.util.function.BiConsumer;

import com.pcda.luk.ltc.constant.DeletionConfigConstant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
@AllArgsConstructor(
    access = AccessLevel.PUBLIC,
    staticName = "init"
)
@FieldNameConstants(
    level = AccessLevel.PRIVATE,
    asEnum = true
)
public final class DeletionConfig {

    public static final DeletionConfig DEFAULT_INSTANCE = DeletionConfig.init(false, false, false, false);

    public static final Map<String, BiConsumer<DeletionConfigBuilder, Boolean>> BUILDER_SETTER = Map.of(
        DeletionConfigConstant.KEY_WORK, DeletionConfigBuilder::workDeletionEnabled,
        DeletionConfigConstant.KEY_OSGI_STATE, DeletionConfigBuilder::osgiStateDeletionEnabled,
        DeletionConfigConstant.KEY_TOMCAT_TEMP, DeletionConfigBuilder::tomcatTempDeletionEnabled,
        DeletionConfigConstant.KEY_TOMCAT_WORK, DeletionConfigBuilder::tomcatWorkDeletionEnabled
    );

    private final boolean workDeletionEnabled;
    private final boolean osgiStateDeletionEnabled;
    private final boolean tomcatTempDeletionEnabled;
    private final boolean tomcatWorkDeletionEnabled;

}
