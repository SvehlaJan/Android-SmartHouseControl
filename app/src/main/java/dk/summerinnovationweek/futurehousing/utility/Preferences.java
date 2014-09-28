package dk.summerinnovationweek.futurehousing.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.Set;
import java.util.TreeSet;

import dk.summerinnovationweek.futurehousing.FutureHousingApplication;
import dk.summerinnovationweek.futurehousing.R;


public class Preferences
{
	private SharedPreferences mSharedPreferences;
	private Context mContext;
	
	public static final int NULL_INT = -1;
	public static final long NULL_LONG = -1l;
	public static final double NULL_DOUBLE = -1.0;
	public static final String NULL_STRING = null;
	public static final Set<String> NULL_STRING_SET = null;
	
	
	public Preferences(Context context)
	{
		if(context==null) context = FutureHousingApplication.getContext();
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		mContext = context;
	}
	
	
	public void clearPreferences()
	{
		Editor editor = mSharedPreferences.edit();
		editor.clear();
		editor.commit();
	}
	
	
	// GETTERS ////////////////////////////////////////////////////////////////////////////////////
	
	
	public long getUserId()
	{
		String key = mContext.getString(R.string.prefs_key_user_id);
		long value = mSharedPreferences.getLong(key, NULL_LONG);
		return value;
	}
	
	
	public String getPassword()
	{
		String key = mContext.getString(R.string.prefs_key_password);
		String value = mSharedPreferences.getString(key, NULL_STRING);
		return value;
	}


	public String getVersion()
	{
		String key = mContext.getString(R.string.prefs_key_version);
		String value = mSharedPreferences.getString(key, NULL_STRING);
		return value;
	}


	public int getLaunch()
	{
		String key = mContext.getString(R.string.prefs_key_launch);
		int value = mSharedPreferences.getInt(key, 0);
		return value;
	}


	public boolean isRated()
	{
		String key = mContext.getString(R.string.prefs_key_rated);
		boolean value = mSharedPreferences.getBoolean(key, false);
		return value;
	}


	public String getFacebookAccessToken()
	{
		String key = mContext.getString(R.string.prefs_key_fb_access_token);
		String value = mSharedPreferences.getString(key, NULL_STRING);
		return value;
	}
	
	
	public long getFacebookAccessExpiration()
	{
		String key = mContext.getString(R.string.prefs_key_fb_expiration);
		long value = mSharedPreferences.getLong(key, NULL_LONG);
		return value;
	}


	public String getGcmRegistrationId()
	{
		String key = mContext.getString(R.string.prefs_key_gcm_registration_id);
		String value = mSharedPreferences.getString(key, NULL_STRING);
		return value;
	}

	public int getGcmVersionCode()
	{
		String key = mContext.getString(R.string.prefs_key_gcm_version_code);
		int value = mSharedPreferences.getInt(key, NULL_INT);
		return value;
	}


	public String getAuthToken()
	{
		String key = mContext.getString(R.string.prefs_key_auth_token);
		String value = mSharedPreferences.getString(key, NULL_STRING);
		return value;
	}


	public String getUserEmail()
	{
		String key = mContext.getString(R.string.prefs_key_email);
		String value = mSharedPreferences.getString(key, NULL_STRING);
		return value;
	}


	public int[] getHouseIds()
	{
		String key = mContext.getString(R.string.prefs_key_house_ids);
		Set<String> set = mSharedPreferences.getStringSet(key, NULL_STRING_SET);
		int[] houseIds = new int[set.size()];
		int counter = 0;
		for (String value : set)
		{
			houseIds[counter] = Integer.parseInt(value);
			counter++;
		}

		return houseIds;
	}


	// SETTERS ////////////////////////////////////////////////////////////////////////////////////
	
	
	public void setUserId(long userId)
	{
		String key = mContext.getString(R.string.prefs_key_user_id);
		Editor editor = mSharedPreferences.edit();
		editor.putLong(key, userId);
		editor.commit();
	}
	
	
	public void setPassword(String password)
	{
		String key = mContext.getString(R.string.prefs_key_password);
		Editor editor = mSharedPreferences.edit();
		editor.putString(key, password);
		editor.commit();
	}


	public void setVersion(String version)
	{
		String key = mContext.getString(R.string.prefs_key_version);
		Editor editor = mSharedPreferences.edit();
		editor.putString(key, version);
		editor.commit();
	}


	public void setLaunch(int launch)
	{
		String key = mContext.getString(R.string.prefs_key_launch);
		Editor editor = mSharedPreferences.edit();
		editor.putInt(key, launch);
		editor.commit();
	}


	public void setRated(boolean rated)
	{
		String key = mContext.getString(R.string.prefs_key_rated);
		Editor editor = mSharedPreferences.edit();
		editor.putBoolean(key, rated);
		editor.commit();
	}


	public void setFacebookAccessToken(String facebookAccessToken)
	{
		String key = mContext.getString(R.string.prefs_key_fb_access_token);
		Editor editor = mSharedPreferences.edit();
		editor.putString(key, facebookAccessToken);
		editor.commit();
	}
	
	
	public void setFacebookAccessExpiration(long expiration)
	{
		String key = mContext.getString(R.string.prefs_key_fb_expiration);
		Editor editor = mSharedPreferences.edit();
		editor.putLong(key, expiration);
		editor.commit();
	}


	public void setGcmRegistrationId(String regId) {
		String key = mContext.getString(R.string.prefs_key_gcm_registration_id);
		Editor editor = mSharedPreferences.edit();
		editor.putString(key, regId);
		editor.commit();
	}

	public void setGcmVersionCode(int appVersion) {
		String key = mContext.getString(R.string.prefs_key_gcm_version_code);
		Editor editor = mSharedPreferences.edit();
		editor.putInt(key, appVersion);
		editor.commit();
	}

	public void setAuthToken(String token) {
		String key = mContext.getString(R.string.prefs_key_auth_token);
		Editor editor = mSharedPreferences.edit();
		editor.putString(key, token);
		editor.commit();
	}

	public void setUserEmail(String email) {
		String key = mContext.getString(R.string.prefs_key_email);
		Editor editor = mSharedPreferences.edit();
		editor.putString(key, email);
		editor.commit();
	}

	public void setHouseIds(int[] houseIds) {
		String key = mContext.getString(R.string.prefs_key_house_ids);
		Editor editor = mSharedPreferences.edit();
		Set<String> set = new TreeSet<String>();
		for (int value : houseIds)
		{
			set.add(String.valueOf(value));
		}
		editor.putStringSet(key, set);
		editor.commit();
	}
}
