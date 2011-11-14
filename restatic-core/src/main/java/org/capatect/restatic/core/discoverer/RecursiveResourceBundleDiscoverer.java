package org.capatect.restatic.core.discoverer;
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

import org.capatect.restatic.core.bundle.ResourceBundle;

import java.io.File;
import java.util.List;

/**
 * Resource bundle discovery which walks a given directory recursively for resource bundles.
 *
 * @author Jamie Craane
 */
public final class RecursiveResourceBundleDiscoverer implements ResourceBundleDiscoverer {
    private final File basePath;

    /**
     * @param basePath The base path to search recursively for resource bundles.
     */
    public RecursiveResourceBundleDiscoverer(final File basePath) {
        this.basePath = basePath;
    }

    @Override
    public List<ResourceBundle> fetchResourceBundles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
