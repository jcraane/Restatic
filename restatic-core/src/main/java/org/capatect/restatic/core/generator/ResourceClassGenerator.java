package org.capatect.restatic.core.generator;

import org.capatect.restatic.core.model.ResourceModel;

import java.io.File;

/**
 * @author Jamie Craane
 */
public interface ResourceClassGenerator {
    void generate(File destination, ResourceModel resourceModel);

}
