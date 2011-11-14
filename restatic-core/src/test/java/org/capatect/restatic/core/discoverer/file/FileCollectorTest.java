package org.capatect.restatic.core.discoverer.file;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author Jamie Craane
 */
public class FileCollectorTest {
    @Ignore
    @Test
    public void findFilesPropertiesFiles() {
        FileFilter filter = AntStylePatternFileNameFilter.create("*.properties");
        File baseDir = new File(System.getProperty("basedir", "restatic-core"));
        File path = new File(baseDir, "src/test/resources");
        FileCollector fileCollector = new FileCollector(path, filter);
        List<File> matchedFiles = fileCollector.collect();
        assertEquals(2, matchedFiles.size());
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void emptyFirstFilter() {

    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void emptyAdditionalFilters() {

    }

    @Ignore
    @Test
    public void findSpecificResourceBundle() {
//        FileFilter filter = new AntStylePatternFileNameFilter();
//        File baseDir = new File(System.getProperty("basedir", "restatic-core"));
//        File path = new File(baseDir, "src/test/resources");
//        FileCollector fileCollector = new FileCollector(path, filter);
//        List<File> matchedFiles = fileCollector.collect();
//        assertEquals(2, matchedFiles.size());
    }
}
