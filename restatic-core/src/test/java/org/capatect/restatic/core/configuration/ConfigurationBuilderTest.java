package org.capatect.restatic.core.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Jamie Craane
 */
public class ConfigurationBuilderTest {
    private List<File> sourceRootPaths;

    @Before
    public void before() {
        sourceRootPaths = new ArrayList<File>();
        sourceRootPaths.add(new File("/org/capatect/restatic/core/bundleone"));
    }

    @Test
    public void buildWithSourceRootPaths() {
        sourceRootPaths.add(new File("/org/capatect/restatic/core/bundletwo"));
        Configuration configuration = new Configuration.ConfigurationBuilder(sourceRootPaths).build();
        assertNotNull(configuration);
        assertNotNull(configuration.getSourceRootPaths());
        assertEquals(2, configuration.getSourceRootPaths().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullSourceRootPaths() {
        new Configuration.ConfigurationBuilder(null).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullSourceRootPathsInList() {
        sourceRootPaths.add(null);
        new Configuration.ConfigurationBuilder(sourceRootPaths).build();
    }

    @Test
    public void testSafeCopySourceRootPaths() {
        List<File> sourceRootPaths = new ArrayList<File>();
        sourceRootPaths.add(new File("/org/capatect/restatic/core/bundleone"));
        Configuration configuration = new Configuration.ConfigurationBuilder(sourceRootPaths).build();
        sourceRootPaths.remove(0);
        assertEquals(1, configuration.getSourceRootPaths().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableSourceRootPaths() {
        new Configuration.ConfigurationBuilder(sourceRootPaths).build().getSourceRootPaths().remove(0);
    }

    @Test
    public void buildWithResourceBundleFilter() {
        List<String> filters = Arrays.asList("*.properties", "*.xml");
        Configuration configuration = new Configuration.ConfigurationBuilder(sourceRootPaths).addFilters(filters).build();
        assertNotNull(configuration.getFilters());
        assertEquals(2, configuration.getFilters().size());
        assertEquals("*.properties", filters.get(0));
        assertEquals("*.xml", filters.get(1));
    }

    public void defaultFilters() {
        assertEquals("*.properties", new Configuration.ConfigurationBuilder(sourceRootPaths).build().getFilters().get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullFilters() {
        new Configuration.ConfigurationBuilder(sourceRootPaths).addFilters(null).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullFilterInList() {
        List<String> filters = Arrays.asList("*.resources", null);
        new Configuration.ConfigurationBuilder(sourceRootPaths).addFilters(filters).build();
    }

    @Test
    public void testSafeCopyFilters() {
        // We cannot use Arrays.asList here as it returns an unmodifiable list. We want to test that the passed-in filters
        // can NOT be modified.
        List<String> filters = new ArrayList<String>(){{
            add("*.properties");
            add("*.xml");
        }};
        Configuration configuration = new Configuration.ConfigurationBuilder(sourceRootPaths).addFilters(filters).build();
        filters.remove(0);
        assertEquals(2, configuration.getFilters().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableFilters() {
        List<String> filters = Arrays.asList("*.resources", "*.xml");
        new Configuration.ConfigurationBuilder(sourceRootPaths).addFilters(filters).build().getFilters().remove(0);
    }
}
