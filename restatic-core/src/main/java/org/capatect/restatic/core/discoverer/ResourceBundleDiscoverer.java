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

import java.util.List;

/**
 * Discoverer for resource bundles.
 *
 * @author Jamie Craane
 */
public interface ResourceBundleDiscoverer {

    /**
     * Fetch the resource bundles and returns them.
     * @return The resource bundles found.
     */
    List<ResourceBundle> fetchResourceBundles();
}
