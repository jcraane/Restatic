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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides the core functionality of the Restatic module.
 *
 * @author Jamie Craane
 * @author Jeroen Post
 */
public final class RestaticCoreImpl implements RestaticCore {

    /**
     * The Logger instance for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaticCoreImpl.class);

    @Override
    public void generateSources(final Configuration configuration) {
        LOGGER.debug("generateSources(configuration={})", configuration);

        // TODO implement the generation process.

    }
}
