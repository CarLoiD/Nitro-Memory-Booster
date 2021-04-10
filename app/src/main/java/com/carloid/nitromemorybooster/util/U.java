package com.carloid.nitromemorybooster.util;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class U {
    @SafeVarargs
    public static <T> Set<T> makeHashSet(T... arguments) {
        Set<T> hashSet = new HashSet<>();
        Collections.addAll(hashSet, arguments);

        return hashSet;
    }

    public static float bytesToGigaBytes(long bytes) {
        int gb = (1024 * 1024) * 1024;
        return (float) bytes / gb;
    }

    public static float measureFileTypeUsage(String... extensions) {
        // final String[] files = storageDir.list(new ExtensionFilenameFilter(U.makeHashSet(extensions)));

        return 0.0f;
    }

    public static void listDirContents(File dir) {
        try {
            Stream<Path> walk = Files.walk(Paths.get(dir.getPath()));
            List<String> result = walk.filter(Files::isRegularFile)
                    .map(Path::toString).collect(Collectors.toList());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static float getPathTotalSpace(String path) {
        return bytesToGigaBytes(new File(path).getTotalSpace());
    }

    public static float getPathFreeSpace(String path) {
        return bytesToGigaBytes(new File(path).getFreeSpace());
    }
}
