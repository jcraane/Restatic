package org.capatect.restatic.core.discoverer.file;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jamie Craane
 *
 * FileFilter implementation which matches standard Java resource bundles (properties and xml files).
 *
 * Should also match wildcard filters, like *.properties and/or exact filenames like resources.properties.
 *
 * TODO Maybe we should also look at the contents of the file to check if it really is a resource bundle. This is especially true with XML files.
 */
public class FileNameFilter implements FileFilter {
    private final List<String> filters = new ArrayList<String>();

    private FileNameFilter(String filter, String... additionalFilters) {
        filters.add(filter);
    }

    private FileNameFilter(String filter) {
        filters.add(filter);
    }

    public static FileNameFilter create(String filter, String... additionalFilters) {
//        FileNameFilter fileNameFilter = new
        return null;
    }

    public static FileNameFilter create(String filter) {
        return new FileNameFilter(filter);
    }

    @Override
    public boolean fileMatches(final String name) {
        for (String filter : filters) {
            if (filter.equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void startProcessing() {
        System.out.println("Start processing");
    }

    @Override
    public void endOfProcessing() {
        System.out.println("End processing");
    }
}
