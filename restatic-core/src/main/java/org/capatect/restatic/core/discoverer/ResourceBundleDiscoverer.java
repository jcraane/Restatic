package org.capatect.restatic.core.discoverer;

import java.util.List;

import org.capatect.restatic.core.bundle.ResourceBundle;

/**
 * Discoverer for resource bundles.
 *
 * @author Jamie Craane
 */
public interface ResourceBundleDiscoverer {

    /**
     * Fetch the resource bundles and returns them.
     * @return The resource bundles found.
     */
    List<ResourceBundle> fetchResourceBundles();
}
