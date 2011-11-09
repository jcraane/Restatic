package org.capatect.restatic.core;

import java.io.File;

/**
 * @author Jamie Craane
 */
public final class FileTestUtils {
    private FileTestUtils() {
        // Prevent instantiation.
    }

    public static String getSystemIndependentPath(final String path) {
        return path.replaceAll("/", File.separator);
    }
}
