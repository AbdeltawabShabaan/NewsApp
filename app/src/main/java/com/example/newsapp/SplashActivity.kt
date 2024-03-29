package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.ui.theme.NewsAppTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                },2500)
                SplashContent()
            }
        }
    }
}

@Composable
fun SplashContent() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(id = R.drawable.pattern), contentScale = ContentScale.FillBounds)
        , verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = R.drawable.logo), contentDescription ="AppLogo"
            ,Modifier.fillMaxSize(0.45F)
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.4F))

        Image(painter = painterResource(id = R.drawable.signture), contentDescription ="signature"
            ,Modifier.fillMaxSize(0.45F)
        )
    }
}

@Preview(showBackground = true , showSystemUi = true)
@Composable
fun GreetingPreview() {
    NewsAppTheme {
        SplashContent()
    }
}