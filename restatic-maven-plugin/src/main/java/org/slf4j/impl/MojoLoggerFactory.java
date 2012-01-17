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
package org.slf4j.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.maven.plugin.Mojo;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * MojoLoggerFactory is an implementation of the {@link org.slf4j.ILoggerFactory} returning
 * the appropriate named {@link MojoLoggerAdapter} instance.
 *
 * @author Jeroen Post
 */
public class MojoLoggerFactory implements ILoggerFactory {

    /**
     * The Mojo instance containing the Maven Logger.
     */
    private static Mojo mojo;


    /**
     * Map containing name Logger pairs.
     */
    Map loggerMap;


    /**
     * Creates a new MojoLoggerFactory instance.
     *
     * @param aMojo the Mojo containing the Maven Logger. Not allowed to be null.
     */
    public MojoLoggerFactory(final Mojo aMojo) {
        Validate.notNull(aMojo, "Parameters aMojo is not allowed to be null");

        mojo = aMojo;
        loggerMap = new HashMap();
    }

    /**
     * {@inheritDoc}
     */
    public Logger getLogger(final String aName) {
        Logger slf4jLogger = null;

        synchronized (this) {
            slf4jLogger = (Logger)loggerMap.get(aName);

            if (slf4jLogger == null) {
                slf4jLogger = new MojoLoggerAdapter(mojo);

                loggerMap.put(aName, slf4jLogger);
            }
        }

        return slf4jLogger;
    }
}
