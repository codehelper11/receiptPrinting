package com.dev.billing.util;

import java.io.File;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * User: droid
 * Date: 7/2/14
 * Time: 8:58 PM
 */
public class FileUtil {

    public static final String BASE_DIR_PROPERTY = "base.dir";

    private FileUtil() {
        //utitltiy class with static methods
    }



    public static  String getCompletePath(String file) {
        return isNullOrEmpty(System.getProperty(BASE_DIR_PROPERTY)) ?
                file :
                System.getProperty(BASE_DIR_PROPERTY) + File.separator + file;
    }
}
