package com.shuang.meiZhi.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author feng
 * @Description:保存数据到文件
 * @date 2017/3/28
 */
public class SharedPreferencesUtils {
    private static final String FILENAME = "config";
    private static SharedPreferences sharedPreferences = UIUtils.getContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);

    public static SharedPreferences getInstance() {
        return sharedPreferences;
    }

    /**
     * save String type
     * @param key     键
     * @param dfValue 值
     */
    public static void saveString(String key, String dfValue) {
        SharedPreferences.Editor edit = SharedPreferencesUtils.getInstance().edit();
        edit.putString(key, dfValue);
        edit.commit();
    }

    /**
     * get String type
     * @param key 键
     * @return String 没有数据返回 null
     */
    public static String getStringData(String key) {
        return SharedPreferencesUtils.getInstance().getString(key, "");
    }

    /**
     * 获取带默认值的String
     * @param key     键
     * @param dfValue 默认值
     * @return String 没有数据返回 dfValue
     */
    public static String getStringData(String key, String dfValue) {
        return SharedPreferencesUtils.getInstance().getString(key, dfValue);
    }

    /**
     * save boolean type
     * @param key     键
     * @param dfValue boolean
     */
    public static void saveBoolean(String key, boolean dfValue) {
        SharedPreferences.Editor edit = SharedPreferencesUtils.getInstance().edit();
        edit.putBoolean(key, dfValue);
        edit.commit();
    }

    /**
     * get boolen type
     * @param key 键
     * @return boolean 默认返回false
     */
    public static boolean getBoolean(String key) {
        return SharedPreferencesUtils.getInstance().getBoolean(key, false);
    }

    /**
     * get boolean tupe
     * @param key     键
     * @param dfValue 值
     * @return boolean 默认返回 dfValue
     */
    public static boolean getBoolean(String key, boolean dfValue) {
        return SharedPreferencesUtils.getInstance().getBoolean(key, dfValue);
    }

    public static void saveInt(String key, int dfValue) {
        SharedPreferences.Editor edit = SharedPreferencesUtils.getInstance().edit();
        edit.putInt(key, dfValue).commit();
    }

    public static int getInt(String key) {
        return SharedPreferencesUtils.getInstance().getInt(key, -1);
    }
    /**
     * save long type
     * @param key     键
     * @param dfValue long
     */
    public static void saveLong(String key, long dfValue) {
        SharedPreferences.Editor edit = SharedPreferencesUtils.getInstance().edit();
        edit.putLong(key, dfValue);
        edit.commit();
    }

    /**
     * get long type
     * @param key 键
     * @return long
     */
    public static long getLong(String key) {
        return SharedPreferencesUtils.getInstance().getLong(key,0l);
    }

}
