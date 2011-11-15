package org.capatect.restatic.core.parser;

import org.capatect.restatic.core.model.ResourceModel;

import java.io.File;
import java.util.List;

/**
 * @author Jamie Craane
 */
public interface ResourceBundleParser {
    ResourceModel parse(List<File> resourceBundles);
}
