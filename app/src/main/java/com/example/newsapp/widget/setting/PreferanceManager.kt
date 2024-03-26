package com.example.newsapp.widget.setting

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun getSelectedOption(): String?{
        // Retrieve the selected option from SharedPreferences, defaulting to "Option 1" if not found
        return sharedPreferences.getString("selectedLanguage", "en")
    }

    fun saveSelectedOption(selectedLanguage: String) {
        // Save the selected option to SharedPreferences
        sharedPreferences.edit().putString("selectedOption", selectedLanguage).apply()
    }
}
