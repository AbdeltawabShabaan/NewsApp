package com.example.newsapp.widget.setting

import android.annotation.SuppressLint
import android.app.LocaleManager
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.ui.theme.NewsAppTheme
import java.util.Locale

val NEWS_SETTING="setting"

@Composable
fun SettingFragment(navController: NavHostController,preferenceManager: PreferenceManager,languageViewModel: LanguageViewModel = viewModel()) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 30.dp, vertical = 50.dp)) {
        Text(text = stringResource(id = R.string.text_language), style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        MyApp()

    }
}

class LanguagePreferenceManager(context: Context){
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    fun getSelectedOption(): String {
        // Retrieve the selected option from SharedPreferences, defaulting to "Option 1" if not found
        return sharedPreferences.getString("currentValue", "English") ?: "English"
    }

    fun saveSelectedOption(list: String) {
        // Save the selected option to SharedPreferences
        sharedPreferences.edit().putString("currentValue", list).apply()
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@Composable
fun MyApp() {
    val context = LocalContext.current
    val languagePreferenceManager = LanguagePreferenceManager(context)
    val language = listOf("en", "ar")

    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf(language[0]) }
    selectedLanguage = languagePreferenceManager.getSelectedOption()
    val keyboardController = LocalSoftwareKeyboardController.current


    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .border(1.dp, MaterialTheme.colorScheme.primary)
                .clickable { expanded = true }
        ) {
            Text(selectedLanguage)
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    keyboardController?.hide()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                language.forEach { language ->
                    DropdownMenuItem(
                        { Text(text = language) },
                        onClick = {
                            selectedLanguage = language
                            expanded = false
                            keyboardController?.hide()
                            updateLanguage(language, context)
                            languagePreferenceManager.saveSelectedOption(selectedLanguage)
                            localeSelection(context, selectedLanguage)
                        })
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }


}


/************************************** a nether way to set a language ****************************************************/
fun localeSelection(context: Context, localeTag: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList.forLanguageTags(localeTag)
    } else {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(localeTag)
        )
    }
}
fun updateLanguage(language: String, context: Context) {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    context.createConfigurationContext(config)
}

/***************************************************************************************************
 * *************************************************************************************************
 * *************************************************************************************************
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true , showSystemUi = true)
@Composable
fun Preview() {
    NewsAppTheme {
        Scaffold (){

        }

    }
}
