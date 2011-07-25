package org.capatect.restatic.core.discoverer.file;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Jamie Craane
 */
public class ResourceBundleFileFilterTest {
    @Test
    public void matchesPropertiesFiles() {
        FileFilter filter = new ResourceBundleFileFilter();
        assertTrue(filter.fileMatches("resources.properties"));
        assertTrue(filter.fileMatches("resources.xml"));
        assertFalse(filter.fileMatches("readme.txt"));
    }
}
