package org.capatect.restatic.core.parser;

import org.capatect.restatic.core.model.ResModel;

import java.io.File;
import java.util.List;

/**
 * Parses a list of resource bundles which are represented as a list of File objects a ResModel. The ResModel is later
 * used to generate source files from.
 *
 * @author Jamie Craane
 */
public interface ResourceBundleParser {
    /**
     * Parses the list of resourceBundles to the ResModel.
     *
     * @param resourceBundles a list of resource bundles to parse and transform in a ResModel.
     * @return ResModel which is parsed from the given resourceBundles.
     */
    ResModel parse(List<File> resourceBundles);
}
