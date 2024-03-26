package com.example.newsapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.widget.CATEGORIES_ROUTE
import com.example.newsapp.widget.CategoriesContent
import com.example.newsapp.widget.DrawerBody
import com.example.newsapp.widget.DrawerHeader
import com.example.newsapp.widget.NEWS_DETAILS
import com.example.newsapp.widget.NEWS_ROUTE
import com.example.newsapp.widget.NewsDetailsFragment
import com.example.newsapp.widget.setting.NEWS_SETTING
import com.example.newsapp.widget.NewsFragment
import com.example.newsapp.widget.setting.LanguageViewModel
import com.example.newsapp.widget.setting.PreferenceManager
import com.example.newsapp.widget.setting.SettingFragment
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferenceManager = PreferenceManager(this)

        setContent {
            NewsAppTheme {
                val scope= rememberCoroutineScope()
                val navController= rememberNavController()
                val drawerState= rememberDrawerState(initialValue = DrawerValue.Closed)
                ModalNavigationDrawer(drawerContent = {
                    Column (modifier = Modifier.fillMaxSize()){
                        DrawerHeader()
                        DrawerBody(navController) {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    }
                }
                , drawerState = drawerState) {
                Scaffold (
                    topBar = { NewsAppBar(drawerState) }
                ){
                    Column (modifier = Modifier.fillMaxSize()){
                        Spacer(modifier = Modifier.height(50.dp))
                        NavHost(
                            navController
                            , startDestination = CATEGORIES_ROUTE
                            , route = "auth"
                        )
                        {
                            composable(route = CATEGORIES_ROUTE){
                                CategoriesContent(navController)

                            }
                            composable(
                                route= "$NEWS_ROUTE/{category}",
                                arguments = listOf(navArgument(name = "category"){
                                    type= NavType.StringType
                                })
                            ){
                                val argument=it.arguments?.getString("category")
                                if (argument != null) {
                                    NewsFragment(argument,navController)
                                }
                            }
                            composable(
                                route="$NEWS_SETTING"

                            ){
                                val languageViewModel:LanguageViewModel=viewModel()
                                SettingFragment(navController,preferenceManager)

                            }
                        }
                    }
                }
            }
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppBar(drawerState: DrawerState) {
    val scope= rememberCoroutineScope()
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "News App",
                style = TextStyle(color = Color.White, fontSize = 22.sp)
            )
        },
        modifier = Modifier.clip(
            RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 26.dp,
                bottomEnd = 26.dp,
            )
        ),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF39A552),
            navigationIconContentColor = Color.White

        ),
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.open()
                }
            }) {
                Icon(Icons.Filled.List, "list")
            }
        },

    )
}






@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true , showSystemUi = true)
@Composable
fun Preview() {
    NewsAppTheme {
        Scaffold (){

        }

    }
}