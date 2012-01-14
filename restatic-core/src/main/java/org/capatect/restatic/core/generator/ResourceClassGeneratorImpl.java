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

package org.capatect.restatic.core.generator;

import org.capatect.restatic.core.configuration.Configuration;
import org.capatect.restatic.core.model.ResModel;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Implementation of ResourceClassGenerator which generates code based on the ResModel.
 *
 * @author Jamie Craane
 */
public class ResourceClassGeneratorImpl implements ResourceClassGenerator {
    private static final char DELIMITER_CHAR = '$';

    private final Configuration configuration;

    public ResourceClassGeneratorImpl(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void generate(final ResModel resModel) {
        STGroup stringTemplateGroup = new STGroupFile("resourceclass.stg", DELIMITER_CHAR, DELIMITER_CHAR);
        ST stringTemplate = stringTemplateGroup.getInstanceOf("rootClass");
        stringTemplate.add("model", resModel);

        final String renderedTemplate = stringTemplate.render();
        writeTemplateToSourceFile(configuration.getOutputDirectory(), renderedTemplate, resModel);
    }

    private void writeTemplateToSourceFile(final File destination, final String renderedTemplate, final ResModel resModel) {
        BufferedWriter writer = null;
        try {

            File destinationIncludingPackageDir = new File(destination, replacePackageWithPathSeperator(resModel));
            createDestinationDirectory(destinationIncludingPackageDir);
            final File outputSourceFile = new File(destinationIncludingPackageDir, resModel.getRootClassName() + ".java");
            writer = new BufferedWriter(new FileWriter(outputSourceFile));
            writer.write(renderedTemplate);
        } catch (IOException e) {
            throw new GeneratorException("Unable to generate source file, see stacktrace for details.", e);
        } finally {
            closeWriter(writer);
        }
    }

    private String replacePackageWithPathSeperator(final ResModel resModel) {
        return resModel.getRootClassPackage().replaceAll("\\.", "/");
    }

    private void createDestinationDirectory(final File destination) {
        if (!destination.exists()) {
            boolean created = destination.mkdirs();
            if (!created) {
                throw new IllegalArgumentException(String.format("Directory %s could not be created.", destination.getAbsolutePath()));
            }
        }
    }

    private void closeWriter(final BufferedWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }
}
