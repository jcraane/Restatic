package org.capatect.restatic.core.discoverer.file;

import org.capatect.restatic.core.FileTestUtils;
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
        File path = new File(FileTestUtils.getSystemIndependentPath("restatic-core/src/test/resources"));
        FileCollector fileCollector = new FileCollector(path, filter);
        List<File> matchedFiles = fileCollector.collect();
        assertEquals(2, matchedFiles.size());
    }
}
