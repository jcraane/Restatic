package org.capatect.restatic.core;
/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    public static File getRootPath(String testDir) {
        File baseDir = new File(System.getProperty("basedir", "restatic-core"));
        File rootPath = new File(baseDir, testDir);
        return rootPath;
    }
}
