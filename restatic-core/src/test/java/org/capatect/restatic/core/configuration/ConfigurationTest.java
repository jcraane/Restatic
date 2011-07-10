package org.capatect.restatic.core.configuration;


import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author: Jamie Craane
 */
public class ConfigurationTest {
    @Test
    public void configuration() {
        List<File> sourceRootPaths = new ArrayList<File>();
        sourceRootPaths.add(new File("/org/capatect/restatic/core/onebundle"));

        Configuration configuration = new Configuration(sourceRootPaths);
        assertNotNull(configuration.getSourceRootPaths());
        assertEquals(1, configuration.getSourceRootPaths().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void configurationNullSourceRootPaths() {
        new Configuration(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void configurationNullSourceRootPathsElement() {
        List<File> sourceRootPaths = new ArrayList<File>();
        sourceRootPaths.add(null);
        new Configuration(sourceRootPaths);
    }
}
