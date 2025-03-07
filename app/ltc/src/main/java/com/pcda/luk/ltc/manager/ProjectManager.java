//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.luk.ltc.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ProjectManager {

    private static final class InstanceHolder {
        private static final ProjectManager INSTANCE = new ProjectManager();

        private InstanceHolder() { /* empty constructor */ }
    }

    private Manifest manifest;

    private ProjectManager() { /* empty constructor */ }

    public static void read() {
        log.debug("Start reading and initializing manifest");
        try (final InputStream manifestInputStream = ClassLoader.getSystemResourceAsStream("META-INF/MANIFEST.MF")) {
            if (manifestInputStream == null) {
                log.error("Couldn't load manifest data");
                return;
            }
            InstanceHolder.INSTANCE.manifest = new Manifest(manifestInputStream);
            log.debug("Successfully read and initialized manifest");
        } catch (final IOException e) {
            log.error("An error occured while initializing the manifest object. {}", e);
        }
    }

    public static String getAttribute(final String attributeKey) {
        return InstanceHolder.INSTANCE.manifest.getMainAttributes().getValue(attributeKey);
    }

}
