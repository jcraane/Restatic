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

import org.capatect.restatic.core.model.ResModel;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.File;

/**
 * Implementation of ResourceClassGenerator which generates code based on the ResModel.
 *
 * @author Jamie Craane
 */
public class ResourceClassGeneratorImpl implements ResourceClassGenerator {

    @Override
    public void generate(final File destination, final ResModel resModel) {
        STGroup stringTemplateGroup = new STGroupFile("resourceclass.stg", '$', '$');
        ST stringTemplate = stringTemplateGroup.getInstanceOf("rootClass");
        stringTemplate.add("model", resModel);

        // TODO: Save to file in configuration.
        System.out.println(stringTemplate.render());
    }
}
