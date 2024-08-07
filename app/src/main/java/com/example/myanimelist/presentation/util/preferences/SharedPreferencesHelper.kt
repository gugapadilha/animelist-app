package com.example.myanimelist.presentation.util.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.myanimelist.domain.model2.Data
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesHelper {

    private const val PREFERENCES_FILE_KEY = "com.example.myanimelist.PREFERENCE_FILE_KEY"
    private const val WATCHED_ANIMES_KEY = "watched_animes"
    private const val FAVORITE_ANIMES_KEY = "favorite_animes"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
    }

    fun saveWatchedAnimes(context: Context, animes: List<Data>) {
        val gson = Gson()
        val json = gson.toJson(animes)
        getSharedPreferences(context).edit().putString(WATCHED_ANIMES_KEY, json).apply()
    }

    fun getWatchedAnimes(context: Context): List<Data> {
        val gson = Gson()
        val json = getSharedPreferences(context).getString(WATCHED_ANIMES_KEY, null)
        val type = object : TypeToken<List<Data>>() {}.type
        return if (json.isNullOrEmpty()) emptyList() else gson.fromJson(json, type)
    }

    fun saveFavoriteAnimes(context: Context, animes: List<Data>) {
        val gson = Gson()
        val json = gson.toJson(animes)
        getSharedPreferences(context).edit().putString(FAVORITE_ANIMES_KEY, json).apply()
    }

    fun getFavoriteAnimes(context: Context): List<Data> {
        val gson = Gson()
        val json = getSharedPreferences(context).getString(FAVORITE_ANIMES_KEY, null)
        val type = object : TypeToken<List<Data>>() {}.type
        return if (json.isNullOrEmpty()) emptyList() else gson.fromJson(json, type)
    }
}