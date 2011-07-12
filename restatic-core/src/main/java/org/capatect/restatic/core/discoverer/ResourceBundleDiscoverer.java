package org.capatect.restatic.core.discoverer;

import org.capatect.restatic.core.bundle.ResourceBundle;

import java.util.List;

/**
 * @author: Jamie Craane
 *
 * Discoverer for resource bundles.
 */
public interface ResourceBundleDiscoverer {
    /**
     * Fetch the resource bundles and returns them.
     * @return The resource bundles found.
     */
    List<ResourceBundle> fetchResourceBundles();
}
