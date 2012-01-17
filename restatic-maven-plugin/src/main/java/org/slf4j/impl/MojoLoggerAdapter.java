/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, soformattingTupleware
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.slf4j.impl;

import java.io.Serializable;

import org.apache.maven.plugin.Mojo;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

/**
 * A {@link org.slf4j.Logger} implementation that wraps the Logger provided by a Maven Mojo.
 *
 * @author Jeroen Post.
 */
public final class MojoLoggerAdapter extends MarkerIgnoringBase implements LocationAwareLogger, Serializable {

    /**
     * The Mojo instance that contains the Maven Logger.
     */
    private Mojo mojo;


    /**
     * Creates a new MojoLoggerAdapter instance with the given Mojo.
     *
     * @param aMojo the Mojo to use for logging.
     */
    MojoLoggerAdapter(final Mojo aMojo) {
        mojo = aMojo;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isTraceEnabled() {
        return mojo.getLog().isDebugEnabled();
    }

    /**
     * {@inheritDoc}
     */
    public void trace(final String aMessage) {
        mojo.getLog().debug(aMessage);
    }

    /**
     * {@inheritDoc}
     */
    public void trace(final String aFormat, final Object anArgument) {
        if (isTraceEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.format(aFormat, anArgument);
            mojo.getLog().debug(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void trace(final String aFormat, final Object anArgumentOne, final Object anArgumentTwo) {
        if (isTraceEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.format(aFormat, anArgumentOne, anArgumentTwo);
            mojo.getLog().debug(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void trace(final String aFormat, final Object[] anArgArray) {
        if (isTraceEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(aFormat, anArgArray);
            mojo.getLog().debug(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void trace(final String aMessage, final Throwable aThrowable) {
        mojo.getLog().debug(aMessage, aThrowable);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isDebugEnabled() {
        return mojo.getLog().isDebugEnabled();
    }

    /**
     * {@inheritDoc}
     */
    public void debug(final String aMessage) {
        mojo.getLog().debug(aMessage);
    }

    /**
     * {@inheritDoc}
     */
    public void debug(final String aFormat, final Object anArgument) {
        if (isDebugEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.format(aFormat, anArgument);
            mojo.getLog().debug(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void debug(final String aFormat, final Object anArgumentOne, final Object anArgumentTwo) {
        if (isDebugEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.format(aFormat, anArgumentOne, anArgumentTwo);
            mojo.getLog().debug(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void debug(final String aFormat, final Object[] anArgArray) {
        if (isDebugEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(aFormat, anArgArray);
            mojo.getLog().debug(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void debug(final String aMessage, final Throwable aThrowable) {
        mojo.getLog().debug(aMessage, aThrowable);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isInfoEnabled() {
        return mojo.getLog().isInfoEnabled();
    }

    /**
     * {@inheritDoc}
     */
    public void info(final String aMessage) {
        mojo.getLog().info(aMessage);
    }

    /**
     * {@inheritDoc}
     */
    public void info(final String aFormat, final Object anArgument) {
        if (isInfoEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.format(aFormat, anArgument);
            mojo.getLog().info(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void info(final String aFormat, final Object anArgumentOne, final Object anArgumentTwo) {
        if (isInfoEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.format(aFormat, anArgumentOne, anArgumentTwo);
            mojo.getLog().info(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void info(final String aFormat, final Object[] anArgArray) {
        if (isInfoEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(aFormat, anArgArray);
            mojo.getLog().info(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void info(final String aMessage, final Throwable aThrowable) {
        mojo.getLog().info(aMessage, aThrowable);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isWarnEnabled() {
        return mojo.getLog().isWarnEnabled();
    }

    /**
     * {@inheritDoc}
     */
    public void warn(final String aMessage) {
        mojo.getLog().warn(aMessage);
    }

    /**
     * {@inheritDoc}
     */
    public void warn(final String aFormat, final Object anArgument) {
        if (isWarnEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.format(aFormat, anArgument);
            mojo.getLog().warn(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void warn(final String aFormat, final Object anArgumentOne, final Object anArgumentTwo) {
        if (isWarnEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.format(aFormat, anArgumentOne, anArgumentTwo);
            mojo.getLog().warn(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void warn(final String aFormat, final Object[] anArgArray) {
        if (isWarnEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(aFormat, anArgArray);
            mojo.getLog().warn(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void warn(final String aMessage, final Throwable aThrowable) {
        mojo.getLog().warn(aMessage, aThrowable);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isErrorEnabled() {
        return mojo.getLog().isErrorEnabled();
    }

    /**
     * {@inheritDoc}
     */
    public void error(final String aMessage) {
        mojo.getLog().error(aMessage);
    }

    /**
     * {@inheritDoc}
     */
    public void error(final String aFormat, final Object anArgument) {
        if (isErrorEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.format(aFormat, anArgument);
            mojo.getLog().error(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void error(final String aFormat, final Object anArgumentOne, final Object anArgumentTwo) {
        if (isErrorEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.format(aFormat, anArgumentOne, anArgumentTwo);
            mojo.getLog().error(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void error(final String aFormat, final Object[] anArgArray) {
        if (isErrorEnabled()) {
            final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(aFormat, anArgArray);
            mojo.getLog().error(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void error(final String aMessage, final Throwable aThrowable) {
        mojo.getLog().error(aMessage, aThrowable);
    }

    /**
     * {@inheritDoc}
     */
    public void log(final Marker aMarker, final String aCallerFQCN, final int aLevel, final String aFormat,
                    final Object[] anArgArray, final Throwable aThrowable) {
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(aFormat, anArgArray);
        final String message = formattingTuple.getMessage();

        switch (aLevel) {
            case LocationAwareLogger.TRACE_INT:
                debug(message, aThrowable);
                break;
            case LocationAwareLogger.DEBUG_INT:
                debug(message, aThrowable);
                break;
            case LocationAwareLogger.INFO_INT:
                info(message, aThrowable);
                break;
            case LocationAwareLogger.WARN_INT:
                warn(message, aThrowable);
                break;
            case LocationAwareLogger.ERROR_INT:
                error(message, aThrowable);
                break;
            default:
                throw new IllegalStateException("Level number " + aLevel + " is not recognized.");
        }
    }
}
