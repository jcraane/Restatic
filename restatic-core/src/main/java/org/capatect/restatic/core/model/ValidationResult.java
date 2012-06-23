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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Jamie Craane
 *         <p/>
 *         Holds the validation errors from validation of the resource bundles if validation is enabled.
 */
public final class ValidationResult {
    private List<String> errors = new ArrayList<String>();

    /**
     * @return The list of valdiation errors.
     */
    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    /**
     * Adds a new error to the ValidationResult.
     *
     * @param message
     */
    public void addValidationError(final String message) {
        errors.add(message);
    }

    /**
     * @return True if this validation has 1 or more errors, false otherwise.
     */
    public boolean hasErrors() {
        return getErrors().size() > 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ValidationResult");
        sb.append("{errors=").append(errors);
        sb.append('}');
        return sb.toString();
    }
}
