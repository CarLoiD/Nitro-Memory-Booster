package com.carloid.nitromemorybooster.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class AccountManager {

    private Context ctx;

    public AccountManager(final Context ctx) {
        this.ctx = ctx;
    }

    public void register(final String username, final String password) {
        try {
            if (isUsernameAvailable(username)) {
                final String fullPath = getBasePath() + username + ".nlg";

                FileOutputStream outUserFile = new FileOutputStream(fullPath);
                outUserFile.write(password.getBytes());
            }
        } catch (Exception exception) {
            Log.e("ACCOUNT_MNG", "Exception thrown");
            exception.printStackTrace();
        }
    }

    public void delete(final String username) {
        if (!isUsernameAvailable(username)) {
            deleteFile(getBasePath() + username + ".nlg");
        }
    }

    public void changePassword(final String username, final String newPassword) {
        try {
            final String fullPath = getBasePath() + username + ".nlg";

            String oldPassword = getPassword(fullPath);
            assert oldPassword != null;

            if (oldPassword.equals(newPassword)) {
                Toast.makeText(ctx, "New password cannot be the same as the previous one!", Toast.LENGTH_LONG).show();
            } else {
                // "Clear" the file contents
                deleteFile(fullPath);

                FileOutputStream outUserFile = new FileOutputStream(fullPath);
                outUserFile.write(newPassword.getBytes());
            }
        } catch (Exception exception) {
            Log.e("ACCOUNT_MNG", "Failed to open the specified user file...");
            exception.printStackTrace();
        }
    }

    public String getPassword(final String username) {
        try {
            FileInputStream userFile = new FileInputStream(getBasePath() + username + ".nlg");

            byte[] bytes = new byte[256];
            int nBytes = userFile.read(bytes);

            return new String(bytes, 0, nBytes);
        } catch (IOException exception) {
            Log.e("ACCOUNT_MNG", "IOException thrown");
            exception.printStackTrace();
        }

        return null;
    }

    public boolean isUsernameAvailable(final String username) {
        return !(new File(getBasePath() + username + ".nlg").exists());
    }

    public boolean login(final String username, final String password) {
        return getPassword(username).equals(password);
    }

    private void deleteFile(final String path) {
        File file = new File(path);

        if (file.delete()) {
            Log.d("ACCOUNT_MNG", "Deleted successfully");
        } else {
            Log.d("ACCOUNT_MNG", "File already deleted or non-existent");
        }
    }

    private String getBasePath() {
        return ctx.getDataDir().getPath() + "/";
    }
}
