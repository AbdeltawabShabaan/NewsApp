package com.example.newsapp.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.Constance
import com.example.newsapp.R
import com.example.newsapp.api.model.Category
val CATEGORIES_ROUTE="categories"

@Composable
fun CategoriesContent(navHostController: NavHostController) {

    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 50.dp)){
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = R.string.pick_your_category)
            , style = TextStyle(
                color = (Color(0XFF4F5A69)), fontSize = 26.sp
            )
        )
        Spacer(modifier = Modifier.height(50.dp))
        LazyVerticalGrid(columns =GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp) ){
            items(6){
                val item=Constance.Categories[it]
                CategoriesCard(item =item, position = it,navHostController)

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesCard(item:Category,position:Int,navHostController: NavHostController) {
    Card (
        onClick = {
                  navHostController.navigate("$NEWS_ROUTE/${item.apiId}")
        }
        ,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
        ,
        shape =  if (position % 2 == 0) {
            RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = 0.dp,
                bottomStart = 16.dp
            )}else
            {
            RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = 16.dp,
                bottomStart = 0.dp
            )}
        ,
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = item.backgroundColor), //Card background color
        )
    ){
        Image(
            painter = painterResource(id = item.drawableResID)
            , contentDescription =""
            , alignment = Alignment.Center
            , modifier = Modifier.fillMaxSize()
        )
        Text(
            text = stringResource(id = item.titleResId)
            , style = TextStyle(fontSize = 18.sp
            , color = Color.White)
            , textAlign = TextAlign.Center
        )
    }
}