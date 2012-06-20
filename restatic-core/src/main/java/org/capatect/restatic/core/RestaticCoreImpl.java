/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.capatect.restatic.core;

import org.capatect.restatic.core.configuration.Configuration;
import org.capatect.restatic.core.discoverer.file.FileCollector;
import org.capatect.restatic.core.discoverer.file.FileCollectorImpl;
import org.capatect.restatic.core.generator.ResourceClassGenerator;
import org.capatect.restatic.core.generator.ResourceClassGeneratorImpl;
import org.capatect.restatic.core.model.ResModel;
import org.capatect.restatic.core.parser.ResourceBundleParser;
import org.capatect.restatic.core.parser.ResourceBundleParserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Provides the core functionality of the Restatic module.
 *
 * @author Jamie Craane
 * @author Jeroen Post
 *         <p/>
 */
public final class RestaticCoreImpl implements RestaticCore {
    /**
     * The Logger instance for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaticCoreImpl.class);

    private FileCollector fileCollector;
    private ResourceBundleParser resourceBundleParser;
    private ResourceClassGenerator resourceClassGenerator;
    private Set<File> sourceDirectories;

    public RestaticCoreImpl(final Configuration configuration) {
        fileCollector = FileCollectorImpl.createWithPathAndFilter(configuration.getFileFilter());
        resourceBundleParser = new ResourceBundleParserImpl(configuration);
        resourceClassGenerator = new ResourceClassGeneratorImpl(configuration);
        sourceDirectories = configuration.getSourceDirectories();
    }

    public void run() {
        LOGGER.debug("run()");

        final List<File> resourceBundles = new ArrayList<File>();
        for (File sourceDirectory : sourceDirectories) {
            resourceBundles.addAll(fileCollector.collect(sourceDirectory));
        }

        final ResModel resModel = resourceBundleParser.parse(resourceBundles);
        resourceClassGenerator.generate(resModel);
    }
}
