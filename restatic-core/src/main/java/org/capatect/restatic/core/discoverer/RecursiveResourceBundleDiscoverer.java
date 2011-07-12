package org.capatect.restatic.core.discoverer;

import org.capatect.restatic.core.bundle.ResourceBundle;

import java.io.File;
import java.util.List;

/**
 * @author: Jamie Craane
 *
 * Resource bundle discovery which walks a given directory recursively for resource bundles.
 */
public final class RecursiveResourceBundleDiscoverer implements ResourceBundleDiscoverer {
    private final File basePath;

    /**
     * @param basePath The base path to search recursively for resource bundles.
     */
    public RecursiveResourceBundleDiscoverer(final File basePath) {
        this.basePath = basePath;
    }

    @Override
    public List<ResourceBundle> fetchResourceBundles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
