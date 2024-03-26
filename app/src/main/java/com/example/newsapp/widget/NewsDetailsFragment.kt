package com.example.newsapp.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.api.model.SourcesItem

val NEWS_DETAILS="news-details"
@Composable
fun NewsDetailsFragment(category:String) {
    val sourceList= remember{
        mutableStateOf<List<SourcesItem>>(listOf())
    }
    val newsDetailsList= remember{
        mutableStateOf<List<ArticlesItem>>(listOf())
    }
    getNewsSources(category,sourceList)
    NewsDetailsList(articlesDetailsList = newsDetailsList.value)
}
@Composable
fun NewsDetailsList(articlesDetailsList: List<ArticlesItem>){
    LazyColumn{

        items(articlesDetailsList.size){
            NewsDetailsCard(articlesItem = articlesDetailsList.get(it))
        }
    }
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsDetailsCard(articlesItem: ArticlesItem) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 10.dp)){
        GlideImage(model = articlesItem.urlToImage?:"", contentDescription = "image news", modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp
                )
            ))
        Text(text = articlesItem.author?:"", style = TextStyle(fontSize = 10.sp))
        Text(text = articlesItem.title?:"", style = TextStyle(fontSize = 20.sp))
        Text(text = articlesItem.content?:"", style = TextStyle(fontSize = 20.sp))
        Text(text = articlesItem.publishedAt?:"", modifier = Modifier.align(Alignment.End))
    }
}

@Composable
fun PerviewCard() {

}