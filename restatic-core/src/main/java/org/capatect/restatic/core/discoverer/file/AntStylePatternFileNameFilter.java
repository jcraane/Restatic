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
package org.capatect.restatic.core.discoverer.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.core.util.AntPathMatcher;

/**
 * Filter which matches filenames and directories by using Ant style patterns.
 * This Filter supports one or more filters.
 * <p/>
 * This code uses Spring's AntPathMatcher class which is copied into this project to prevent a jar dependency on
 * the Spring project.
 *
 * @author Jamie Craane
 */
public class AntStylePatternFileNameFilter implements FileFilter {
    private final List<String> patterns = new ArrayList<String>();

    private AntStylePatternFileNameFilter(String... patterns) {
        this.patterns.addAll(Arrays.asList(patterns));
    }

    /**
     * Creates an instance of the AntStylePatternFileNameFilter with the given patterns.
     *
     * @param patterns The Ant-style patterns to use in this filter.
     * @return instance of AntStylePatternFileNameFilter with the given patterns.
     * @throws if patterns is null or contains an null element.
     */
    public static AntStylePatternFileNameFilter create(String... patterns) {
        Validate.notNull(patterns, "filter may not be null");
        Validate.noNullElements(patterns);

        return new AntStylePatternFileNameFilter(patterns);
    }

    /**
     * @return An unmodifiable list of the patterns of this filter.
     */
    public List<String> getPatterns() {
        return Collections.unmodifiableList(patterns);
    }

    /**
     * Tests the given filename against the patterns.
     *
     * @param fileName The filename to test for.
     * @return true if the given filename at least matches one pattern.
     */
    @Override
    public boolean matches(final String fileName) {
        final AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (final String pattern : patterns) {
            if (antPathMatcher.match(pattern, fileName)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("patterns", patterns)
                .toString();
    }
}
