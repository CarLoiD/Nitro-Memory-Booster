package com.carloid.nitromemorybooster.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

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

import kotlin.jvm.internal.Intrinsics;

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

    public static float bytesToMegaBytes(long bytes) {
        int mb = (1024 * 1024);
        return (float) bytes / mb;
    }

    public static float measureFileTypeUsage(String... extensions) {
        // final String[] files = storageDir.list(new ExtensionFilenameFilter(U.makeHashSet(extensions)));

        return 0.0f;
    }

    public static float getPathTotalSpace(String path) {
        return bytesToGigaBytes(new File(path).getTotalSpace());
    }

    public static float getPathFreeSpace(String path) {
        return bytesToGigaBytes(new File(path).getFreeSpace());
    }

    public static String floatForm(double d) {
        return String.format(java.util.Locale.US, "%.1f", d);
    }

    public static String bytesToHuman(long size) {
        long Kb = 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size < Kb) return size + " bytes";
        if (size >= Kb && size < Mb) return (size / Kb) + " KB";
        if (size >= Mb && size < Gb) return floatForm((double) size / Mb) + " MB";
        if (size >= Gb && size < Tb) return floatForm((double) size / Gb) + " GB";
        if (size >= Tb && size < Pb) return floatForm((double) size / Tb) + " TB";
        if (size >= Pb && size < Eb) return floatForm((double) size / Pb) + " Pb";
        if (size >= Eb) return floatForm((double) size / Eb) + " Eb";

        return "0";
    }

    public static void deleteFile(final String path) {
        File file = new File(path);

        if (file.delete()) {
            Log.d("ACCOUNT_MNG", "Deleted successfully");
        } else {
            Log.d("ACCOUNT_MNG", "File already deleted or non-existent");
        }
    }

    public static Dialog dialogCreate(@NotNull Context c, int resid, boolean cancelable) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        Dialog dialog = new Dialog(c);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(resid);
        dialog.setCancelable(cancelable);
        return dialog;
    }
}
