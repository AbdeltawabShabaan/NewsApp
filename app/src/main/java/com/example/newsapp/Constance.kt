package com.example.newsapp

import com.example.newsapp.api.model.Category

object Constance {
    val API_KEY="f9a3057ead5c42689d47f49790ffed9a"
    val Categories= listOf(
        Category(
            "sports",R.drawable.sports,
            R.string.sports,R.color.red
        ),
        Category(
            "technology",R.drawable.politics,
            R.string.technology,R.color.blue
        ),
        Category(
            "health",R.drawable.health,
            R.string.health,R.color.bink
        ),
        Category(
            "business",R.drawable.business,
            R.string.business,R.color.brown_orange
        ),
        Category(
            "general",R.drawable.enviroment,
            R.string.general,R.color.baby_blue
        ),
        Category(
            "science",R.drawable.science,
            R.string.science,R.color.yellow
        ),
    )
}