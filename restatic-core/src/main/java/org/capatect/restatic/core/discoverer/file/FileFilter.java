package org.capatect.restatic.core.discoverer.file;

/*
 * Copyright 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * <a href="http://www.apache.org/licenses/LICENSE-2.0<br />
" title="http://www.apache.org/licenses/LICENSE-2.0<br />
">http://www.apache.org/licenses/LICENSE-2.0<br />
</a> *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * Listener for callbacks when directories and files are found when exploring a
 * directory, or a jar file for example.
 *
 * @author <a href="mailto:ycoppel@google.com">ycoppel@google.com</a> (Yohann Coppel)
 */
public interface FileFilter {
    /**
     * Filter to apply to (normal - i.e not a directory) files found in the
     * directory. May be a simple filename check as "end with .class"
     *
     * @param name filename
     * @return true if the filename pass the filter, and that proceedFile should
     *         be called on this file later
     */
    public boolean fileMatches(String name);

    /**
     * callback called at the begining of the processing
     */
    public void startProcessing();

    /**
     * callback called at the end of the processing
     */
    public void endOfProcessing();
}
