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

import org.apache.commons.lang.Validate;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Resursively scans a given directory (rootPath) for files which matches the given filter.
 * <p/>
 * An instance of this class can be created by using the static factory method createWithPathAndFilter.
 * <p/>
 * This class is NOT threadsafe.
 *
 * @author Jamie Craane
 */
public final class FileCollectorImpl implements FileCollector {
    /**
     * File filter for callbacks.
     */
    private final FileFilter filter;

    private final List<File> matchedFiles = new ArrayList<File>();

    /**
     * Creates a new instance of the FileCollectorImpl with the given rootPath and filter.
     *
     * @param filter An implementation of the FileFilter interface which filters the files from the given rootPath.
     * @return
     */
    public static FileCollector createWithPathAndFilter(final FileFilter filter) {
        return new FileCollectorImpl(filter);
    }

    /**
     * @param fileFilter the file filter to match.
     */
    private FileCollectorImpl(final FileFilter fileFilter) {
        Validate.notNull(fileFilter, "The fileFilter may not be null.");

        this.filter = fileFilter;
    }

    /**
     * Collections all files recursively form the given rootPath which matches the file filter.
     *
     * @param rootPath path to the classFile (usually top level package as com in com.google.common)
     * @return An unmodifiable List of files which matches the FileFilter for all directories under rootPath.
     */
    public List<File> collect(final File rootPath) {
        for (File file : rootPath.listFiles()) {
            recursiveFileSearch(rootPath, file);
        }

        return Collections.unmodifiableList(matchedFiles);
    }

    /**
     * operate a recursive depth first search in the directory. If file is a
     * directory, recursively search into for files. For each file found, check if
     * it passes the filter of DepMaker, and if yes, call proceedFile with the
     * file.
     *
     * @param rootPath path to the classFile (usually top level package as com in com.google.common)
     * @param file     File where to start the recursiveFileSearch
     */
    private void recursiveFileSearch(final File rootPath, final File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                recursiveFileSearch(rootPath, f);
            }
        } else {
            String path = stripRootPathFromFilePath(rootPath, file);
            if (filter.matches(path)) {
                matchedFiles.add(file);
            }
        }
    }

    private String stripRootPathFromFilePath(final File rootPath, final File file) {
        String filePath = file.getPath();
        return filePath.substring(rootPath.getPath().length() + 1, filePath.length());
    }
}
