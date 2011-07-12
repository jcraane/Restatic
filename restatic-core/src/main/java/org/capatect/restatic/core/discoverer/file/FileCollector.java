package org.capatect.restatic.core.discoverer.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * @Author Jamie Craane
 *
 * Resursively scans a given directory for files which matches the given filter.
 *
 * This class is NOT threadsafe.
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

    private final List<File> matchedFiles = new ArrayList();

    /**
     * @param rootPath path to the classFile (usually top level package as com in
     *                  com.google.common)
     */
    public FileCollector(File rootPath, FileFilter fileFilter) {
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
    private void recursiveFileSearch(File file) {
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
     * @return A List of files which matches the filefilter for all directories under rootPath.
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
