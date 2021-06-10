package com.sureti.utils

import android.content.Context
import android.preference.PreferenceManager

@Suppress("DEPRECATION")
object StorageUtility {
    fun saveDataInPreferences(c: Context?, key: String?, value: String?) {
        val editor = PreferenceManager.getDefaultSharedPreferences(c).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun saveDataInPreferences(c: Context?, key: String?, value: Int) {
        val editor = PreferenceManager.getDefaultSharedPreferences(c).edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun saveDataInPreferences(c: Context?, key: String?, value: Boolean) {
        val editor = PreferenceManager.getDefaultSharedPreferences(c).edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun saveDataInPreferences(c: Context?, key: String?, value: Long) {
        val editor = PreferenceManager.getDefaultSharedPreferences(c).edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getDataFromPreferences(c: Context?, key: String?, defaultValue: Boolean): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(c)
        return preferences.getBoolean(key, defaultValue)
    }

    fun getDataFromPreferences(c: Context?, key: String?, defaultValue: Long): Long {
        val preferences = PreferenceManager.getDefaultSharedPreferences(c)
        return preferences.getLong(key, defaultValue)
    }

    fun getDataFromPreferences(c: Context?, key: String?, defaultValue: String?): String? {
        if (c == null) return defaultValue
        val preferences = PreferenceManager.getDefaultSharedPreferences(c)
        return preferences.getString(key, defaultValue)
    }

    fun getDataFromPreferences(c: Context?, key: String?, defaultValue: Int): Int {
        val preferences = PreferenceManager.getDefaultSharedPreferences(c)
        return preferences.getInt(key, defaultValue)
    }

    fun clearAllPreferences(context: Context?) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.clear()
        editor.apply()
    }
}