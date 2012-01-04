package org.capatect.restatic.core.generator;

import org.capatect.restatic.core.model.ResModel;

/**
 * Generator which generates source files from a ResModel.
 *
 * @author Jamie Craane
 */
public interface ResourceClassGenerator {
    /**
     * Generates Java source files from the given ResModel to the given destination.
     *
     * @param resModel The ResModel to generate source files from.
     */
    void generate(ResModel resModel);

}
