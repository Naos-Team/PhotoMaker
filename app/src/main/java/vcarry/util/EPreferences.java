package vcarry.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.Arrays;

public class EPreferences {
    public static final String DURATION_VALUE = "duration_value";
    public static final String IS_TITLE = "false";
    public static final String LANG_POSITION = "lang_position";
    private static final int MODE_PRIVATE = 0;
    public static final String PREF_AUDIO_PATH = "pref_audio_path";
    public static final String PREF_CB_MUSIC_FILE_POSITION = "pref_cb_music_file_position";
    public static final String PREF_KEY_ANIMATION_INDEX = "pref_key_animation_index";
    public static final String PREF_KEY_FILTER_INDEX = "pref_key_filter_index";
    public static final String PREF_KEY_IS_FIRST_TIME = "is_first_time";
    public static final String PREF_KEY_NOTIFICATION_FLESH = "notification_flesh";
    public static final String PREF_KEY_NOTIFICATION_RING = "notification_ring";
    public static final String PREF_KEY_NOTIFICATION_VIBRATE = "notification_vibrate";
    public static final String PREF_KEY_SAVEING_CAM_IMG = "pref_key_saveing_cam_img";
    public static final String PREF_KEY_VIDEONAME = "videoname";
    public static final String PREF_KEY_VIDEO_FRAME_PATH = "pref_key_video_frame_path";
    public static final String PREF_KEY_VIDEO_QUALITY = "pref_key_video_quality";
    public static final String PREF_KEY_WANT_CAPTURED_ALERT = "pref_key_captured_alert";
    public static final String PREF_KEY_WANT_DAILY_ALERT = "pref_key_daily_alert";
    private static final String PREF_NAME = "slideshow_pref";
    public static final String TITLE_INDEX = "title_index";
    public static final String TITLE_STRING = "title_string";
    String PrefKeyUrl = "all_url";
    private SharedPreferences m_csPref;

    private EPreferences(Context context, String s, int n) {
        this.m_csPref = context.getSharedPreferences(s, n);
    }

    public static EPreferences getInstance(Context context) {
        return new EPreferences(context, PREF_NAME, 0);
    }

    public boolean checkUrlAvailable(String s) {
        return getCsvURL().contains(s);
    }

    public void clear(String s) {
        this.m_csPref.edit().remove(s).commit();
    }

    public void clearPref() {
        Editor edit = this.m_csPref.edit();
        edit.clear();
        edit.commit();
        StringBuilder sb = new StringBuilder();
        sb.append("Pref=>");
        sb.append(getCsvURL());
    }

    public void clearURLPref() {
        Editor edit = this.m_csPref.edit();
        edit.putString(this.PrefKeyUrl, "");
        edit.commit();
    }

    public ArrayList<String> getAllURL() {
        String csvURL = getCsvURL();
        if (csvURL == null || csvURL.equals("") || csvURL.length() <= 0) {
            return null;
        }
        return new ArrayList(Arrays.asList(csvURL.split(",")));
    }

    public boolean getBoolean(String s, boolean b) {
        return this.m_csPref.getBoolean(s, b);
    }

    public String getCsvURL() {
        return this.m_csPref.getString(this.PrefKeyUrl, "");
    }

    public int getInt(String s, int n) {
        return this.m_csPref.getInt(s, n);
    }

    public String getString(String s, String s2) {
        return this.m_csPref.getString(s, s2);
    }

    public int putBoolean(String s, boolean b) {
        Editor edit = this.m_csPref.edit();
        edit.putBoolean(s, b);
        edit.commit();
        return 0;
    }

    public int putInt(String s, int n) {
        Editor edit = this.m_csPref.edit();
        edit.putInt(s, n);
        edit.commit();
        return 0;
    }

    public void putMultipleURLPref(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            putURLValue((String) list.get(i));
        }
    }

    public int putString(String s, String s2) {
        Editor edit = this.m_csPref.edit();
        edit.putString(s, s2);
        edit.commit();
        return 0;
    }

    public void putURLValue(String s) {
        Editor edit = this.m_csPref.edit();
        String csvURL = getCsvURL();
        if (csvURL == null || csvURL.equals("") || csvURL.length() <= 0) {
            edit.putString(this.PrefKeyUrl, s);
        } else {
            String prefKeyUrl = this.PrefKeyUrl;
            StringBuilder sb = new StringBuilder();
            sb.append(csvURL);
            sb.append(",");
            sb.append(s);
            edit.putString(prefKeyUrl, sb.toString());
        }
        edit.commit();
    }

    public void setString(String s, String s2) {
        Editor edit = this.m_csPref.edit();
        edit.putString(s, s2);
        edit.commit();
    }
}
