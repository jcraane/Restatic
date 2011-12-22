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

import org.capatect.restatic.core.configuration.Configuration;
import org.capatect.restatic.core.configuration.builder.ConfigurationBuilder;
import org.capatect.restatic.core.discoverer.file.FileCollector;
import org.capatect.restatic.core.generator.ResourceClassGenerator;
import org.capatect.restatic.core.model.ResModel;
import org.capatect.restatic.core.parser.ResourceBundleParser;
import org.easymock.IArgumentMatcher;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * @author Jamie Craane
 */
public class RestaticCoreImplTest {
    @Test
    public void generateSources() throws NoSuchFieldException, IllegalAccessException {
        File rootPath = FileTestUtils.getRootPath("src/test/generator-test");

        File bundle1 = new File("bundle1.properties");
        File bundle2 = new File("bundle1.properties");
        List<File> collectedFiles = Arrays.asList(bundle1, bundle2);

        Configuration configuration = new ConfigurationBuilder().
                addSourceDirectory(rootPath).
                toOutputDirectory(FileTestUtils.getRootPath("target/generated-sources/restatic")).getConfiguration();

        ResModel resModel = ResModel.create(configuration);

        reportMatcher(new IArgumentMatcher() {
            @Override
            public boolean matches(final Object o) {
                return o instanceof File;
            }

            @Override
            public void appendTo(final StringBuffer stringBuffer) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        FileCollector fileCollector = createMock(FileCollector.class);
        expect(fileCollector.collect(rootPath)).andReturn(collectedFiles);

        ResourceBundleParser parser = createMock(ResourceBundleParser.class);
        expect(parser.parse(collectedFiles)).andReturn(resModel);

        ResourceClassGenerator generator = createMock(ResourceClassGenerator.class);
        generator.generate(resModel);

        replay(fileCollector, parser, generator);

        RestaticCore restaticCore = new RestaticCoreImpl(configuration);
        setField(restaticCore, "fileCollector", fileCollector);
        setField(restaticCore, "resourceBundleParser", parser);
        setField(restaticCore, "resourceClassGenerator", generator);
        restaticCore.run();

        verify(fileCollector, parser, generator);
    }

    private void setField(RestaticCore restaticCore, String fieldName, Object object) throws NoSuchFieldException, IllegalAccessException {
        Field field = restaticCore.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(restaticCore, object);
    }
}
