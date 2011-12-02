package org.capatect.restatic.core.model;
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

/**
 * ResModel abstraction for Restatic. This model is created by the ResourceBundleParser and handed over
 * to the ResourceClassGenerator which generates source files from the ResModel.
 *
 * @author Jamie Craane
 */
public final class ResModel {
    private final String rootClassName;

    public ResModel(final String rootClassName) {
        this.rootClassName = rootClassName;
    }

    public static ResModel create(final String rootClassName) {
        return new ResModel(rootClassName);
    }

    /**
     * @return The name of the root class to generate.
     */
    public String getRootClassName() {
        return rootClassName;
    }
}
