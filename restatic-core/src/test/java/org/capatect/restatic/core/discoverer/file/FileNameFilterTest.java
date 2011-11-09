package org.capatect.restatic.core.discoverer.file;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author: Jamie Craane
 */
public class FileNameFilterTest {
    @Test
    public void matchesExactlyOnePropertyFiles() {
        FileFilter filter = FileNameFilter.create("resources.properties");
        assertTrue(filter.fileMatches("resources.properties"));
        assertFalse(filter.fileMatches("resources.xml"));
    }
}
