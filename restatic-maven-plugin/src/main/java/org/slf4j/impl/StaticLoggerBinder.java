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

import org.apache.maven.plugin.Mojo;
import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

/**
 * The StaticLoggerBinder used by Slf4j to return the {@link org.slf4j.ILoggerFactory} implementation, in this
 * case a MojoLoggerFactory.
 *
 * @author Jeroen Post
 */
public class StaticLoggerBinder implements LoggerFactoryBinder {

    /**
     * The one and only instance of this class.
     */
    private static StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

    private static final String loggerFactoryClassStr = MojoLoggerFactory.class.getName();

    /**
     * The Mojo containing the Maven Logger.
     */
    private static Mojo mojo;

    /**
     * The ILoggerFactory instance returned by the {@link #getLoggerFactory} method.
     */
    private final ILoggerFactory loggerFactory;

    /**
     * Return the singleton of this class.
     *
     * @return the StaticLoggerBinder singleton.
     */
    public static final StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

    private StaticLoggerBinder() {
        loggerFactory = new MojoLoggerFactory(mojo);
    }

    /**
     * Set the Mojo to be used by the factory.
     *
     * @param aMojo the Mojo containting the Maven Logger.
     */
    public static void setMojo(final Mojo aMojo) {
        mojo = aMojo;
    }

    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    public String getLoggerFactoryClassStr() {
        return loggerFactoryClassStr;
    }
}
