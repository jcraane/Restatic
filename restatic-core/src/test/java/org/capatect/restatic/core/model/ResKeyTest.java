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
public class ResKeyTest {
    @Test
    public void create() {
        ResKey resKey = ResKey.createAndConvertConstantIdentifier("key");
        assertEquals("KEY", resKey.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullKey() {
        ResKey.createAndConvertConstantIdentifier(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyKey() {
        ResKey.createAndConvertConstantIdentifier("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullKey() {
        ResKey.KeyToJavaConstantIdentifierConverter.convert(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyKey() {
        ResKey.KeyToJavaConstantIdentifierConverter.convert("");
    }

    @Test
    public void convertPlainTextKey() {
        assertEquals("LABEL", ResKey.KeyToJavaConstantIdentifierConverter.convert("label"));
        assertEquals("FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("firstname"));
        assertEquals("IMPORT", ResKey.KeyToJavaConstantIdentifierConverter.convert("import"));
    }

    @Test
    public void convertPlainTextAndIllegalCharactersKey() {
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person.firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person@firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person#firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person!firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person`firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person~firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person$firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person%firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person^firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person&firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person*firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person(firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person)firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person+firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person=firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person{firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person}firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person[firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person]firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person:firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person;firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person\"firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person'firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person>firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person<firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person?firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person/firstname"));
        assertEquals("PERSON_FIRSTNAME", ResKey.KeyToJavaConstantIdentifierConverter.convert("person,firstname"));
        assertEquals("ORG_BUTTON_TITLE", ResKey.KeyToJavaConstantIdentifierConverter.convert("org.button.title"));
    }

    @Test
    public void convertWithSpaces() {
        assertEquals("A_A", ResKey.KeyToJavaConstantIdentifierConverter.convert("  a_a    "));
    }

    @Test
    public void convertIllegalCharactersOnlyKey() {
        assertEquals("_", ResKey.KeyToJavaConstantIdentifierConverter.convert("***"));
        assertEquals("_", ResKey.KeyToJavaConstantIdentifierConverter.convert("***/:"));
        assertEquals("_A_", ResKey.KeyToJavaConstantIdentifierConverter.convert("***/:a*("));
    }
}
