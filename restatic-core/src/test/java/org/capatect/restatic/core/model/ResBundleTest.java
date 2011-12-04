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

package org.capatect.restatic.core.model;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Jamie Craane
 */
public class ResBundleTest {
    @Test(expected = IllegalArgumentException.class)
    public void createWithNullName() {
        ResBundle.createAndConvertToJavaClassIdentifier("path", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyName() {
        ResBundle.createAndConvertToJavaClassIdentifier("path", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullPath() {
        ResBundle.createAndConvertToJavaClassIdentifier(null, "name");
    }

    @Test
    public void createWithDefaultLocale() {
        ResBundle resBundle = ResBundle.createAndConvertToJavaClassIdentifier("", "resources.properties");
        assertEquals("Resources", resBundle.getBundleClassName());
    }

    @Test
    public void createWithLocale() {
        ResBundle resBundle = ResBundle.createAndConvertToJavaClassIdentifier("", "resources_nl_NL.properties");
        assertEquals("Resources", resBundle.getBundleClassName());
    }

    @Test
    public void createWithPackage() {
        ResBundle resBundle = ResBundle.createAndConvertToJavaClassIdentifier("org.capatect.resources", "resources.properties");
        assertEquals("OrgCapatectResourcesResources", resBundle.getBundleClassName());
    }

    @Test
    public void createWithPackageAlias() {
        ResBundle resBundle = ResBundle.createAndConvertToJavaClassIdentifier("org.capatect.resources", "resources.properties");
        assertEquals("OrgCapatectResourcesResources", resBundle.getBundleClassName());
    }

    @Test
    public void convertResourceBundleToJavaClassIdentifier() {
        String className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("org.capatect.resources", "resources.properties");
        assertEquals("OrgCapatectResourcesResources", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("", "resources.properties");
        assertEquals("Resources", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("org.test", "resources.properties");
        assertEquals("OrgTestResources", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("org.test", "resources_nl_NL.properties");
        assertEquals("OrgTestResources", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("org.test", "resources_en.properties");
        assertEquals("OrgTestResources", className);
    }
}
