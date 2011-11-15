package org.capatect.restatic.core.discoverer.file;
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

import org.apache.commons.lang.Validate;
import org.springframework.core.util.AntPathMatcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Filter which matches filenames and directories by using Ant style patterns.
 *
 * This code uses Spring's AntPathMatcher class which is copied into this project to prevent a jar dependency on
 * the Spring project.
 *
 * @author Jamie Craane
 */
public class AntStylePatternFileNameFilter implements FileFilter {
    private static final String PATH_SEPERATOR = File.separator;

    private final List<String> patterns = new ArrayList<String>();

    private AntStylePatternFileNameFilter(String... patterns) {
        this.patterns.addAll(Arrays.asList(patterns));
    }

    public static AntStylePatternFileNameFilter create(String... patterns) {
        Validate.notNull(patterns, "filter may not be null");
        Validate.noNullElements(patterns);

        return new AntStylePatternFileNameFilter(patterns);
    }

    @Override
    public boolean fileMatches(final String fileName) {
        final AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String pattern : patterns) {
            if (antPathMatcher.match(pattern, fileName)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void startProcessing() {
        System.out.println("Start processing");
    }

    @Override
    public void endOfProcessing() {
        System.out.println("End processing");
    }
}
