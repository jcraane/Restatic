package org.capatect.restatic.core.parser;

import org.capatect.restatic.core.model.ResourceModel;

import java.io.File;
import java.util.List;

/**
 * Parses a list of resource bundles which are represented as a list of File objects a ResourceModel. The ResourceModel is later
 * used to generate source files from.
 *
 * @author Jamie Craane
 */
public interface ResourceBundleParser {
    /**
     * Parses the list of resourceBundles to the ResourceModel.
     *
     * @param resourceBundles a list of resource bundles to parse and transform in a ResourceModel.
     * @return ResourceModel which is parsed from the given resourceBundles.
     */
    ResourceModel parse(List<File> resourceBundles);
}
