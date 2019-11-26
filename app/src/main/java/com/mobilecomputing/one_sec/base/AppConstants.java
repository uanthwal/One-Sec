package com.mobilecomputing.one_sec.base;


import com.mobilecomputing.one_sec.BuildConfig;

/**
 * Created by xian on 2017/2/17.
 */

public class AppConstants {

    //intent constant
    public static final String APP_PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    public static final String LOCK_PWD = "lock_pwd";
    public static final String LOCK_PACKAGE_NAME = "lock_package_name";
    public static final String LOCK_FROM = "lock_from";
    public static final String LOCK_FROM_FINISH = "lock_from_finish";
    public static final String LOCK_FROM_SETTING = "lock_from_setting";
    public static final String LOCK_FROM_UNLOCK = "lock_from_unlock";
    public static final String LOCK_FROM_LOCK_MAIN_ACITVITY = "lock_from_lock_main_activity";
    public static final String APP_FIRST_RUN = "app_first_run";

    //shared prefs constant
    public static final String LOCK_STATE = "app_lock_state";//boolean
    public static final String LOCK_FAVITER_NUM = "lock_faviter_num";//int
    public static final String LOCK_SYS_APP_NUM = "lock_sys_app_num";//int
    public static final String LOCK_USER_APP_NUM = "lock_user_app_num";//int
    public static final String LOCK_IS_INIT_FAVITER = "lock_is_init_faviter";//boolean
    public static final String LOCK_IS_INIT_DB = "lock_is_init_db";//boolean
    public static final String LOCK_IS_HIDE_LINE = "lock_is_hide_line";//boolean
    public static final String LOCK_IS_FIRST_LOCK = "is_lock"; //boolean
    public static final String LOCK_AUTO_SCREEN = "lock_auto_screen";//boolean
    public static final String LOCK_AUTO_SCREEN_TIME = "lock_auto_screen_time"; //boolean
    public static final String LOCK_CURR_MILLISECONDS = "lock_curr_milliseconds";//long
    public static final String LOCK_APART_MILLISECONDS = "lock_apart_milliseconds";//long
    public static final String LOCK_APART_TITLE = "lock_apart_title";//string
    public static final String LOCK_LAST_LOAD_PKG_NAME = "last_load_package_name";//string
    public static final String LOCK_AUTO_RECORD_PIC = "AutoRecordPic";//boolean
    public static final String PATTERN_VIBRATION="pattern_vibration";
    // public static final String LOCK_TYPE = "lock_type";//boolean ACCESSIBILITY OR USAGES STATE

    public final static String SHARED_PREF_FILE_NAME = "MEDIA_VAULT";
    public final static String PASSCODE_CONFIRM_PASSCODE_NOT_MATCHING = "PIN and Confirm PIN not matching";
    public final static String PASSCODE_SAVED = "PIN Saved";
    public final static String SECURITY_Q_AND_A = "Security  Question Answer Saved";
    public final static String SECURITY_A_EMPTY = "Please enter Security Answer";
    public final static String INCORRECT_PIN = "Incorrect PIN";
    public final static String INCORRECT_SEC_Q_AND_A = "Incorrect Security Question and Answer";
    public final static String MEDIA_FOLDER = "Media";
    public final static String ALBUM_EXISTS_ERR = "Album name already exists";
}