package com.carloid.nitromemorybooster.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Set;

public class ExtensionFileNameFilter implements FilenameFilter {

    private final Set<String> extensions;

    public ExtensionFileNameFilter(Set<String> extensions) {
        this.extensions = extensions;
    }

    @Override
    public boolean accept(File dir, String name) {
        final String fileExtension = name.toLowerCase().substring(name.lastIndexOf('.'));
        return extensions.isEmpty() || extensions.contains(fileExtension);
    }
}
