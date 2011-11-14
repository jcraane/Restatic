package org.capatect.restatic.core.discoverer.file;

import org.apache.commons.lang.Validate;
import org.springframework.core.util.AntPathMatcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Filter which matches filenames and directories by using Ant style patterns.
 *
 * This code is inspired by Spring's AntPathMatcher which is in turn inspired
 * by Ant's source code.
 *
 * @author: Jamie Craane
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
//            if (pathMatches(pattern, fileName)) {
//                boolean fileMatches = fileMatches(fileName, pattern);
//
//                if (fileMatches) {
//                    return true;
//                }
//            }
        }

        return false;
    }

    private boolean fileMatches(final String fileName, final String pattern) {
        String name = fileName;
        if (containsPathSeparator(name)) {
            name = extractLastPartAfterPathSeperator(name);
        }

        String lastPartOfPattern = pattern;
        if (containsPathSeparator(lastPartOfPattern)) {
            lastPartOfPattern = extractLastPartAfterPathSeperator(lastPartOfPattern);
        }

        if (containsWildCard(lastPartOfPattern)) {
            return matchByWildCard(name, lastPartOfPattern);
        } else {
            return matchExact(name, lastPartOfPattern);
        }
    }

    private boolean matchExact(final String name, final String lastPartOfPattern) {
        return lastPartOfPattern.equals(name);
    }

    private boolean containsPathSeparator(String path) {
        return path.indexOf(PATH_SEPERATOR) != -1;
    }

    private String extractLastPartAfterPathSeperator(final String name) {
        return name.substring(name.lastIndexOf(PATH_SEPERATOR) + 1, name.length());
    }

    private boolean containsWildCard(final String lastPartOfPattern) {
        return lastPartOfPattern.indexOf("*") >= 0;
    }

    private boolean matchByWildCard(final String name, final String lastPartOfPattern) {
        boolean fileMatches;
        String filterExtension = extractExtension(lastPartOfPattern);
        String fileExtension = extractExtension(name);
        fileMatches = filterExtension.equals(fileExtension);
        return fileMatches;
    }

    private String extractExtension(final String name) {
        return name.substring(name.indexOf(".") + 1, name.length());
    }

    /**
     * Actually match the given <code>path</code> against the given <code>pattern</code>.
     * @param pattern the pattern to match against
     * @param path the path String to test
     * @return <code>true</code> if the supplied <code>path</code> matched, <code>false</code> if it didn't
     *
     * TODO: Rewrite to clean code.
     */
    protected boolean pathMatches(String pattern, String path) {

        if (path.startsWith(PATH_SEPERATOR) != pattern.startsWith(PATH_SEPERATOR)) {
            return false;
        }

        String[] pattDirs = pattern.split(PATH_SEPERATOR);
        String[] pathDirs = path.split(PATH_SEPERATOR);

        int patternIndexStart = 0;
        int patternIndexEnd = pattDirs.length - 1;
        int pathIndexStart = 0;
        int pathIndexEnd = pathDirs.length - 1;

        // Match all elements up to the first **
        while (patternIndexStart <= patternIndexEnd && pathIndexStart <= pathIndexEnd) {
            String patDir = pattDirs[patternIndexStart];
            if ("**".equals(patDir)) {
                break;
            }
            patternIndexStart++;
            pathIndexStart++;
        }

        if (pathIndexStart > pathIndexEnd) {
            // Path is exhausted, only match if rest of pattern is * or **'s
            if (patternIndexStart > patternIndexEnd) {
                return (pattern.endsWith(PATH_SEPERATOR) ? path.endsWith(PATH_SEPERATOR) :
                        !path.endsWith(PATH_SEPERATOR));
            }
            if (patternIndexStart == patternIndexEnd && pattDirs[patternIndexStart].equals("*") && path.endsWith(PATH_SEPERATOR)) {
                return true;
            }
            for (int i = patternIndexStart; i <= patternIndexEnd; i++) {
                if (!pattDirs[i].equals("**")) {
                    return false;
                }
            }
            return true;
        }
        else if (patternIndexStart > patternIndexEnd) {
            // String not exhausted, but pattern is. Failure.
            return false;
        }
        else if ("**".equals(pattDirs[patternIndexStart])) {
            // Path start definitely matches due to "**" part in pattern.
            return true;
        }

        // up to last '**'
        while (patternIndexStart <= patternIndexEnd && pathIndexStart <= pathIndexEnd) {
            String patDir = pattDirs[patternIndexEnd];
            if (patDir.equals("**")) {
                break;
            }
            if (!matchStrings(patDir, pathDirs[pathIndexEnd])) {
                return false;
            }
            patternIndexEnd--;
            pathIndexEnd--;
        }
        if (pathIndexStart > pathIndexEnd) {
            // String is exhausted
            for (int i = patternIndexStart; i <= patternIndexEnd; i++) {
                if (!pattDirs[i].equals("**")) {
                    return false;
                }
            }
            return true;
        }

        while (patternIndexStart != patternIndexEnd && pathIndexStart <= pathIndexEnd) {
            int patIdxTmp = -1;
            for (int i = patternIndexStart + 1; i <= patternIndexEnd; i++) {
                if (pattDirs[i].equals("**")) {
                    patIdxTmp = i;
                    break;
                }
            }
            if (patIdxTmp == patternIndexStart + 1) {
                // '**/**' situation, so skip one
                patternIndexStart++;
                continue;
            }
            // Find the pattern between padIdxStart & padIdxTmp in str between
            // strIdxStart & strIdxEnd
            int patLength = (patIdxTmp - patternIndexStart - 1);
            int strLength = (pathIndexEnd - pathIndexStart + 1);
            int foundIdx = -1;

            strLoop:
            for (int i = 0; i <= strLength - patLength; i++) {
                for (int j = 0; j < patLength; j++) {
                    String subPat = pattDirs[patternIndexStart + j + 1];
                    String subStr = pathDirs[pathIndexStart + i + j];
                    if (!matchStrings(subPat, subStr)) {
                        continue strLoop;
                    }
                }
                foundIdx = pathIndexStart + i;
                break;
            }

            if (foundIdx == -1) {
                return false;
            }

            patternIndexStart = patIdxTmp;
            pathIndexStart = foundIdx + patLength;
        }

        for (int i = patternIndexStart; i <= patternIndexEnd; i++) {
            if (!pattDirs[i].equals("**")) {
                return false;
            }
        }

        return true;
    }

    /**
     * Tests whether or not a string matches against a pattern. The pattern may contain two special characters:<br> '*'
     * means zero or more characters<br> '?' means one and only one character
     * @param pattern pattern to match against. Must not be <code>null</code>.
     * @param str string which must be matched against the pattern. Must not be <code>null</code>.
     * @return <code>true</code> if the string matches against the pattern, or <code>false</code> otherwise.
     */
    private boolean matchStrings(String pattern, String str) {
        AntPathStringMatcher matcher = new AntPathStringMatcher(pattern, str);
        return matcher.matchStrings();
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
