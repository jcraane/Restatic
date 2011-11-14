package org.capatect.restatic.core.discoverer.file;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Resursively scans a given directory for files which matches the given filter.
 *
 * This class is NOT threadsafe.
 *
 * @author Jamie Craane
 */
public final class FileCollector {

    /**
     * Base directory to explore.
     */
    private final File rootPath;

    /**
     * File filter for callbacks.
     */
    private final FileFilter filter;

    private final List<File> matchedFiles = new ArrayList<File>();

    /**
     * @param rootPath path to the classFile (usually top level package as com in com.google.common)
     * @param fileFilter the file filter to match.
     */
    public FileCollector(final File rootPath, final FileFilter fileFilter) {
        this.rootPath = rootPath;
        this.filter = fileFilter;
    }

    /**
     * operate a recursive depth first search in the directory. If file is a
     * directory, recursively search into for files. For each file found, check if
     * it passes the filter of DepMaker, and if yes, call proceedFile with the
     * file.
     *
     * @param file File where to start the recursiveFileSearch
     */
    private void recursiveFileSearch(final File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                recursiveFileSearch(f);
            }
        } else {
            if (filter.fileMatches(file.getPath())) {
                matchedFiles.add(file);
            }
        }
    }

    /**
     * Collections all files recursively form the given rootPath which matches the file filter.
     *
     * @return A List of files which matches the file filter for all directories under rootPath.
     */
    public List<File> collect() {
        filter.startProcessing();
        for (File file : rootPath.listFiles()) {
            recursiveFileSearch(file);
        }
        filter.endOfProcessing();

        return Collections.unmodifiableList(matchedFiles);
    }
}
