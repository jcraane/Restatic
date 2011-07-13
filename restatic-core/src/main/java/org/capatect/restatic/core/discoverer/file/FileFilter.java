package org.capatect.restatic.core.discoverer.file;

/**
 * Listener for callbacks when directories and files are found when exploring a
 * directory, or a jar file for example.
 */
public interface FileFilter {
    /**
     * Filter to apply to (normal - i.e not a directory) files found in the
     * directory. May be a simple filename check as "end with .class"
     *
     * @param name filename
     * @return true if the filename pass the filter, and that proceedFile should
     *         be called on this file later
     */
    public boolean fileMatches(String name);

    /**
     * callback called at the begining of the processing
     */
    public void startProcessing();

    /**
     * callback called at the end of the processing
     */
    public void endOfProcessing();
}
