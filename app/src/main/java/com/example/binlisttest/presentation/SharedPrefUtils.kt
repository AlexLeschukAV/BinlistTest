package com.example.binlisttest.presentation

import android.content.Context
import com.example.binlisttest.domain.model.BinlistData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class SharedPrefUtils @Inject constructor(private val context: Context) {

    private val sharedPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    companion object {
        private const val PREF_FILE_NAME = "my_preferences"
        private const val KEY = "archive"
        private val gson = Gson()
    }

    fun saveCard(card: BinlistData) {
        val listType = object : TypeToken<List<BinlistData>>() {}.type
        val currentList: ArrayList<BinlistData> = gson.fromJson(sharedPref.getString(KEY, ""), listType) ?: ArrayList()
        currentList.add(card)
        val json = gson.toJson(currentList)
        sharedPref.edit().putString(KEY, json).apply()
    }

    fun loadList(): List<BinlistData> {
        val type = object : TypeToken<List<BinlistData>>() {}.type
        val json = sharedPref.getString(KEY, "") ?: ""
        return Gson().fromJson(json, type) ?: listOf()
    }
}