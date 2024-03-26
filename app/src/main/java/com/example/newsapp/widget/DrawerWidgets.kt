package com.example.newsapp.widget


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.widget.setting.NEWS_SETTING

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DrawerHeader() {
    Text(
        text= stringResource(id = R.string.news_app),
        modifier = Modifier
            .fillMaxWidth(0.7F)
            .background(color = Color(0xFF39A552), shape = RoundedCornerShape(0.dp))
            .padding(vertical = 50.dp),
        style =TextStyle(color = Color.White, fontSize = 20.sp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun DrawerBody(navHostController: NavHostController,onClickDrawer:()->Unit) {
    Column (modifier = Modifier.fillMaxWidth(0.7F).fillMaxHeight().background(Color.White).padding(top = 50.dp)){
        NewsDrawerItems(iconId = R.drawable.ic_categories, textId = R.string.text_categories, onNewsDrawerItemsClick = {
            navHostController.navigate(CATEGORIES_ROUTE){
                navHostController.navigate(CATEGORIES_ROUTE)
                onClickDrawer()
            }
        })
        Spacer(modifier = Modifier.height(20.dp))
        NewsDrawerItems(iconId = R.drawable.ic_setting, textId =R.string.text_setting, onNewsDrawerItemsClick = {
            navHostController.navigate(NEWS_SETTING)
            onClickDrawer()
        } )
    }
}

@Composable
fun NewsDrawerItems(iconId:Int,textId:Int,onNewsDrawerItemsClick:() -> Unit) {

    Row(modifier = Modifier
        .fillMaxWidth().padding(start = 30.dp)
        .clickable {
            onNewsDrawerItemsClick()
        }
    ) {
        Icon(painter = painterResource(id = iconId), contentDescription ="" )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = stringResource(id = textId), style = TextStyle(fontSize = 18.sp) )
    }
 }