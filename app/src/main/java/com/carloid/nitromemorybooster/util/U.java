package com.carloid.nitromemorybooster.util;

import android.os.Environment;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
        File[] storageDir = Environment.getExternalStorageDirectory().listFiles();
        // final String[] files = storageDir.list(new ExtensionFileNameFilter(U.makeHashSet(extensions)));

        return 0.0f;
    }

    public static float getPathTotalSpace(String path) {
        return bytesToGigaBytes(new File(path).getTotalSpace());
    }

    public static float getPathFreeSpace(String path) {
        return bytesToGigaBytes(new File(path).getFreeSpace());
    }
}
