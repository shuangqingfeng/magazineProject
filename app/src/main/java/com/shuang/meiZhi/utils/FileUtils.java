package com.shuang.meiZhi.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author feng
 * @Description:
 * @date 2017/5/19
 */
public class FileUtils {
    public static File getExternalFilesDir(Context context, String type, String childName) {
        return new File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), childName);
    }
}
