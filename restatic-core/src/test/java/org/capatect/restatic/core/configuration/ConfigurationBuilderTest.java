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
public class ConfigurationBuilderTest {
    @Test
    public void buildWithSourceRootPaths() {
        List<File> sourceRootPaths = new ArrayList<File>();
        sourceRootPaths.add(new File("/org/capatect/restatic/core/bundleone"));
        sourceRootPaths.add(new File("/org/capatect/restatic/core/bundletwo"));
        Configuration configuration = new Configuration.ConfigurationBuilder().addSourceRootPaths(sourceRootPaths).build();
        assertNotNull(configuration);
        assertNotNull(configuration.getSourceRootPaths());
        assertEquals(2, configuration.getSourceRootPaths().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullSourceRootPaths() {
        new Configuration.ConfigurationBuilder().addSourceRootPaths(null).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullSourceRootPathsInList() {
        List<File> sourceRootPaths = new ArrayList<File>();
        sourceRootPaths.add(null);
        new Configuration.ConfigurationBuilder().addSourceRootPaths(sourceRootPaths).build();
    }

    @Test
    public void testSafeCopySourceRootPaths() {
        List<File> sourceRootPaths = new ArrayList<File>();
        sourceRootPaths.add(new File("/org/capatect/restatic/core/bundleone"));
        Configuration configuration = new Configuration.ConfigurationBuilder().addSourceRootPaths(sourceRootPaths).build();
        sourceRootPaths.remove(0);
        assertEquals(1, configuration.getSourceRootPaths().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableSourceRootPaths() {
        List<File> sourceRootPaths = new ArrayList<File>();
        sourceRootPaths.add(new File("/org/capatect/restatic/core/bundleone"));
        new Configuration.ConfigurationBuilder().addSourceRootPaths(sourceRootPaths).build().getSourceRootPaths().remove(0);
    }
}
