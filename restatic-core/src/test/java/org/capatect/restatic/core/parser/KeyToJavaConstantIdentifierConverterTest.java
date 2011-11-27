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

package org.capatect.restatic.core.parser;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Jamie Craane
 */
public class KeyToJavaConstantIdentifierConverterTest {
    public static final String JJ = "";

    @Test(expected = IllegalArgumentException.class)
    public void nullKey() {
        new KeyToJavaConstantIdentifierConverter().convert(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyKey() {
        new KeyToJavaConstantIdentifierConverter().convert("");
    }

    @Test
    public void convertPlainTextKey() {
        assertEquals("LABEL", new KeyToJavaConstantIdentifierConverter().convert("label"));
        assertEquals("FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("firstname"));
        assertEquals("IMPORT", new KeyToJavaConstantIdentifierConverter().convert("import"));
    }

    @Test
    public void convertPlainTextAndIllegalCharactersKey() {
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person.firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person@firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person#firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person!firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person`firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person~firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person$firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person%firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person^firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person&firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person*firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person(firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person)firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person+firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person=firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person{firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person}firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person[firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person]firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person:firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person;firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person\"firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person'firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person>firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person<firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person?firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person/firstname"));
        assertEquals("PERSON_FIRSTNAME", new KeyToJavaConstantIdentifierConverter().convert("person,firstname"));

        assertEquals("ORG_BUTTON_TITLE", new KeyToJavaConstantIdentifierConverter().convert("org.button.title"));
    }

    @Test
    public void convertWithSpaces() {
        assertEquals("A_A", new KeyToJavaConstantIdentifierConverter().convert("  a_a    "));
    }

    @Test
    public void convertIllegalCharactersOnlyKey() {
        assertEquals("_", new KeyToJavaConstantIdentifierConverter().convert("***"));
        assertEquals("_", new KeyToJavaConstantIdentifierConverter().convert("***/:"));
        assertEquals("_A_", new KeyToJavaConstantIdentifierConverter().convert("***/:a*("));
    }
}
