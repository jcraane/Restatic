package org.capatect.restatic.core.discoverer.file;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Jamie Craane
 */
public class AntStylePatternFileNameFilterTest {
    @Test(expected = IllegalArgumentException.class)
    public void createFilterNullArgumentsNotAllowed() {
        AntStylePatternFileNameFilter.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFilterNullElementsNotAllowed() {
        AntStylePatternFileNameFilter.create(null, null);
    }

    @Test
    public void matchesExactlyOnePropertyFiles() {
        FileFilter filter = AntStylePatternFileNameFilter.create("resources.properties");
        assertTrue(filter.fileMatches("resources.properties"));
        assertFalse(filter.fileMatches("resources.xml"));
    }

    @Test
    public void matchExactDirectoryAndWildCardFile() {
        FileFilter filter = AntStylePatternFileNameFilter.create("org/test/*.properties");
        assertTrue(filter.fileMatches("org/test/resources.properties"));
        assertTrue(filter.fileMatches("org/test/test.properties"));
        assertFalse(filter.fileMatches("org/test/resources.xml"));
    }

    @Test
    public void matchAntStyleWildCardDirectory() {
        FileFilter filter = AntStylePatternFileNameFilter.create("**/*.properties");
        assertTrue(filter.fileMatches("org/test/resources.properties"));
        assertTrue(filter.fileMatches("resources.properties"));
        assertTrue(filter.fileMatches("nl/company/test/bundle.properties"));
        assertFalse(filter.fileMatches("bundle.xml"));
        assertFalse(filter.fileMatches("org/test/bundle.xml"));
    }

    @Test
    public void matchAntStyleWildCardMultipleDirectories() {
        FileFilter filter = AntStylePatternFileNameFilter.create("org/test/**/*.properties");
        assertTrue(filter.fileMatches("org/test/resources.properties"));
        assertTrue(filter.fileMatches("org/test/application/resources.properties"));
        assertTrue(filter.fileMatches("org/test/application/test/resources.properties"));
        assertFalse(filter.fileMatches("resources.properties"));
        assertFalse(filter.fileMatches("bundle.xml"));
        assertFalse(filter.fileMatches("org/test/bundle.xml"));
        assertFalse(filter.fileMatches("nl/company/test/bundle.properties"));

    }
}
