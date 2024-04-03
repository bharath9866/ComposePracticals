package com.example.adaptivestreamingplayer.orderApp.modal

import androidx.annotation.DrawableRes
import com.example.adaptivestreamingplayer.R


val yourMind = arrayListOf(
    YourMind(image = R.drawable.ic_food_in_mind_default, text = "Rice items"),
    YourMind(image = R.drawable.ic_food_in_mind_default, text = "Indian"),
    YourMind(image = R.drawable.ic_food_in_mind_default, text = "Curries"),
    YourMind(image = R.drawable.ic_food_in_mind_default, text = "Soups"),
    YourMind(image = R.drawable.ic_food_in_mind_default, text = "Desserts"),
    YourMind(image = R.drawable.ic_food_in_mind_default, text = "Snack"),
    YourMind(image = R.drawable.ic_food_in_mind_default, text = "Rice items"),
    YourMind(image = R.drawable.ic_food_in_mind_default, text = "Indians"),
    YourMind(image = R.drawable.ic_food_in_mind_default, text = "Curries"),
    YourMind(image = R.drawable.ic_food_in_mind_default, text = "Soups"),
    YourMind(image = R.drawable.ic_food_in_mind_default, text = "Desserts"),
    YourMind(image = R.drawable.ic_food_in_mind_default, text = "Snacks"),
)

data class YourMind(
    @DrawableRes val image: Int,
    val text:String
)


val recommendations = arrayListOf(
    Recommended(
        image = R.drawable.ic_recommended_default,
        dishName = "Italian Spaghetti Pasta",
        ratting = 4.2f,
        timer = "30 min . Medium prep."
    ),
    Recommended(
        image = R.drawable.ic_recommended_default,
        dishName = "Italian Spaghetti Pasta",
        ratting = 4.2f,
        timer = "30 min . Medium prep."
    ),
    Recommended(
        image = R.drawable.ic_recommended_default,
        dishName = "Italian Spaghetti Pasta",
        ratting = 4.2f,
        timer = "30 min . Medium prep."
    ),
    Recommended(
        image = R.drawable.ic_recommended_default,
        dishName = "Italian Spaghetti Pasta",
        ratting = 4.2f,
        timer = "30 min . Medium prep."
    ),
    Recommended(
        image = R.drawable.ic_recommended_default,
        dishName = "Italian Spaghetti Pasta",
        ratting = 4.2f,
        timer = "30 min . Medium prep."
    ),
    Recommended(
        image = R.drawable.ic_recommended_default,
        dishName = "Italian Spaghetti Pasta",
        ratting = 4.2f,
        timer = "30 min . Medium prep."
    ),
    Recommended(
        image = R.drawable.ic_recommended_default,
        dishName = "Italian Spaghetti Pasta",
        ratting = 4.2f,
        timer = "30 min . Medium prep."
    ),
    Recommended(
        image = R.drawable.ic_recommended_default,
        dishName = "Italian Spaghetti Pasta",
        ratting = 4.2f,
        timer = "30 min . Medium prep."
    ),
    Recommended(
        image = R.drawable.ic_recommended_default,
        dishName = "Italian Spaghetti Pasta",
        ratting = 4.2f,
        timer = "30 min . Medium prep."
    ),
    Recommended(
        image = R.drawable.ic_recommended_default,
        dishName = "Italian Spaghetti Pasta",
        ratting = 4.2f,
        timer = "30 min . Medium prep."
    ),
    Recommended(
        image = R.drawable.ic_recommended_default,
        dishName = "Italian Spaghetti Pasta",
        ratting = 4.2f,
        timer = "30 min . Medium prep."
    ),
    Recommended(
        image = R.drawable.ic_recommended_default,
        dishName = "Italian Spaghetti Pasta",
        ratting = 4.2f,
        timer = "30 min . Medium prep."
    ),
)
data class Recommended(
    @DrawableRes val image: Int,
    val dishName:String,
    val ratting:Float,
    val timer:String
)