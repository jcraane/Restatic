package org.capatect.restatic.core.generator;

import org.capatect.restatic.core.model.ResourceModel;

import java.io.File;

/**
 * Generator which generates source files from a ResourceModel.
 *
 * @author Jamie Craane
 */
public interface ResourceClassGenerator {
    /**
     * Generates Java source files from the given ResourceModel to the given destination.
     *
     * @param destination   The destination to put the generated source files in.
     * @param resourceModel The ResourceModel to generate source files from.
     */
    void generate(File destination, ResourceModel resourceModel);

}
