package org.capatect.restatic.core.generator;

import org.capatect.restatic.core.model.ResModel;

import java.io.File;

/**
 * Generator which generates source files from a ResModel.
 *
 * @author Jamie Craane
 */
public interface ResourceClassGenerator {
    /**
     * Generates Java source files from the given ResModel to the given destination.
     *
     * @param destination The destination to put the generated source files in.
     * @param resModel    The ResModel to generate source files from.
     */
    void generate(File destination, ResModel resModel);

}
