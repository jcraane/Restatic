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

package org.capatect.restatic.core.parser;

import org.capatect.restatic.core.configuration.Configuration;
import org.capatect.restatic.core.model.ResModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Class responssible for reading the resourcebundles and transforming it into a ResModel which
 * is later used by the generator for the generation of classes from the resource bundles.
 * <p/>
 * This class optionally validate the resource bundles for correctness when the Configuration.isResourceBundleValidationEnabled
 * returns true.
 *
 * @author Jamie Craane
 */
public final class ResourceBundleParserImpl implements ResourceBundleParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceBundleParserImpl.class);

    private final Configuration configuration;

    /**
     * Creates a instance of the ResourceBundleParserImpl.
     *
     * @param configuration The configuration object which the parser uses to control its behavior.
     */
    public ResourceBundleParserImpl(final Configuration configuration) {
        this.configuration = configuration;
    }

    public ResModel parse(final List<File> resourceBundles) {
        LOGGER.trace("Start parsing the resource bundles");

        final ResModel resModel = ResModel.create(configuration);

        for (File resourceBundle : resourceBundles) {
            resModel.addResourceBundle(resourceBundle);
        }

        if (configuration.isResourceBundleValidationEnabled()) {
            if (!resModel.isValid()) {
                throw new IllegalStateException("One or more of the resource bundles are not valid because they contain" +
                        " not the same number of keys for all locales.");
            }
        }

        return resModel;
    }
}
