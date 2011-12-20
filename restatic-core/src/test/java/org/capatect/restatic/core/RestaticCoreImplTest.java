/*
 *
 *  * Copyright 2002-2011 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  
 */

package org.capatect.restatic.core;

import org.capatect.restatic.core.discoverer.file.FileCollector;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * @author Jamie Craane
 */
public class RestaticCoreImplTest {
    @Test
    public void generateSources() {
        FileCollector fileCollector = mock(FileCollector.class);

        File bundle1 = new File("bundle1.properties");
        File bundle2 = new File("bundle1.properties");
        when(fileCollector.collect()).thenReturn(Arrays.asList(bundle1, bundle2));

        // TODO: Add other mock classes and verify behavior.

        verify(fileCollector);
    }
}
