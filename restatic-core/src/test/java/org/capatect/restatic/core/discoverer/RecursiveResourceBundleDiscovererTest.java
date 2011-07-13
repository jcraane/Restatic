package org.capatect.restatic.core.discoverer;

import org.capatect.restatic.core.FileTestUtils;
import org.junit.Test;

import java.io.File;

/**
 * @author: Jamie Craane
 *
 * Tests the discovery of resource bundles in source trees. Since there is no practical way to emulate file systems in memory
 * with Java <= 6, we use the src/test/resources directory to store our resource bundles. The same packaging scheme for the
 * resources is used as for the testcases.
 */
public class RecursiveResourceBundleDiscovererTest {
    private final String pathSeparator = File.pathSeparator;
    @Test
    public void findOneResourceBundle() {
        File path = new File(FileTestUtils.getSystemIndependentPath("restatic-core/src/main/resources"));
        ResourceBundleDiscoverer discoverer = new RecursiveResourceBundleDiscoverer(path);
//        List<ResourceBundle> bundles = discoverer.fetchResourceBundles();
//        assertEquals(1, bundles.size());

    }
}
