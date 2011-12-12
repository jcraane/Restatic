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

/**
 * Exception which is thrown if something went wrong during parsing of the resource bundles.
 *
 * @author Jamie Craane
 */
public class ParseException extends RuntimeException {
    private String pathToResourceBundle;

    public ParseException(final String s, final Throwable throwable, final String pathToResourceBundle) {
        super(s, throwable);
        this.pathToResourceBundle = pathToResourceBundle;
    }

    /**
     * @return The path of the resource bundle which failed parsing.
     */
    public String getPathToResourceBundle() {
        return pathToResourceBundle;
    }
}
