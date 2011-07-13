package org.capatect.restatic.core.discoverer.file;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author: Jamie Craane
 */
public class FileCollectorTest {
    @Test
    public void findPropertiesFiles() {
        FileFilter filter = new ResourceBundleFileFilter();
        File baseDir = new File(System.getProperty("basedir", "restatic-core"));
        File path = new File(baseDir, "src/test/resources");
        FileCollector fileCollector = new FileCollector(path, filter);
        List<File> matchedFiles = fileCollector.collect();
        assertEquals(2, matchedFiles.size());
    }
}
