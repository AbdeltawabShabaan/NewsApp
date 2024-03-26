package com.example.newsapp.widget

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.newsapp.Constance
import com.example.newsapp.api.APIManager
import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.api.model.Category
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.SourcesItem
import com.example.newsapp.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val NEWS_ROUTE="news"

@Composable
fun NewsFragment(category: String,navHostController: NavHostController) {
    val sourceList=remember{
        mutableStateOf<List<SourcesItem>>(listOf())
    }
    val newsList=remember{
        mutableStateOf<List<ArticlesItem>>(listOf())
    }
    getNewsSources(category,sourceList)
    Column {
        Spacer(modifier = Modifier.height(30.dp))
        NewsSourceTap(sourceList.value,newsList)
        NewsList(articleList = newsList.value,navHostController)
    }
}


@Composable
fun NewsSourceTap(
    sourcesItemsList:List<SourcesItem>,
    newsResponseState: MutableState<List<ArticlesItem>>
)
{
    var selectedIndex by remember {
        mutableIntStateOf(0)

    }
    if (sourcesItemsList.isNotEmpty())
        ScrollableTabRow(
            selectedTabIndex =selectedIndex ,
            contentColor = Color.Transparent ,
            divider = {},
            indicator = {}
        )
        {
            sourcesItemsList.forEachIndexed { index, sourcesItem ->
                if (selectedIndex==index){
                    getNewsBySource(sourcesItem, newsResponseState =newsResponseState )
                }
                Tab(selected = selectedIndex==index, onClick = {
                    selectedIndex = index

                },
                    selectedContentColor = Color.White ,
                    unselectedContentColor = Color(0xFF39A552),
                    modifier = if (selectedIndex==index) Modifier
                        .padding(end = 2.dp)
                        .background(
                            Color(0xFF39A552),
                            RoundedCornerShape(50)
                        )
                    else
                        Modifier
                            .padding(end = 2.dp)
                            .border(2.dp, Color(0xFF39A552), RoundedCornerShape(50))
                    ,text = { Text(text = sourcesItem.name?:"")}
                )
            }
        }
}


@Composable
fun NewsList(articleList:List<ArticlesItem>,navHostController: NavHostController) {
    LazyColumn{

            items(articleList.size){
                NewsCard(articlesItem = articleList.get(it),navHostController)
            }
        }
    }


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsCard( articlesItem: ArticlesItem, navHostController: NavHostController) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 10.dp),
        onClick = {
            navHostController.navigate("$NEWS_DETAILS/")
        }
    )
    {
        GlideImage(model = articlesItem.urlToImage?:"", contentDescription = "image news", modifier = Modifier.fillMaxWidth().background(color = Color.White, shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 8.dp, bottomEnd = 8.dp)))
        Text(text = articlesItem.author?:"", style = TextStyle(fontSize = 10.sp))
        Text(text = articlesItem.title?:"")
        Text(text = articlesItem.publishedAt?:"", modifier = Modifier.align(Alignment.End))
    }
}

fun getNewsBySource(sourcesItem: SourcesItem,newsResponseState: MutableState<List<ArticlesItem>>?){
    APIManager
        .getNewsServices()
        .getNewsBySource(Constance.API_KEY,sourcesItem.id?:"")
        .enqueue(object :Callback<NewsResponse>{
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                val newsResponse=response.body()
                newsResponseState?.value= newsResponse?.articles!!
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {

            }

        })
}

fun getNewsSources(category: String,sourceList:MutableState<List<SourcesItem>>?){

    APIManager
        .getNewsServices()
        .getNewsSources(Constance.API_KEY,category=category)
        .enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {
                val body=response.body()
                Log.e("TAG","onResponse: ${body?.sources}")
                Log.e("TAG","onResponse: ${body?.status}")
                sourceList?.value=body?.sources?: listOf()
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {

            }

        })
}