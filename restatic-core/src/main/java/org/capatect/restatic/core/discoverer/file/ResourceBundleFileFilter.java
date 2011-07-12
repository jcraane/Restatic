package org.capatect.restatic.core.discoverer.file;

/**
 * @author: Jamie Craane
 *
 * FileFilter implementation which matches standard Java resource bundles (properties and xml files).
 *
 * TODO Maybe we should also look at the contents of the filter to check if it really is a resource bundle.
 * This is especially true with XML files.
 */
public class ResourceBundleFileFilter implements FileFilter {

    private static final String PROPERTIES_RESOURCE_BUNDLE = ".properties";
    private static final String XML_RESOURCE_BUNDLE = ".xml";

    @Override
    public boolean fileMatches(final String name) {
        return isResourceBundle(name);
    }

    private boolean isResourceBundle(final String name) {
        return name.endsWith(PROPERTIES_RESOURCE_BUNDLE) || name.endsWith(XML_RESOURCE_BUNDLE);
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
