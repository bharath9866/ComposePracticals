package com.example.utils

import android.content.Context
import android.content.res.Configuration

fun isTabletOrMobile(ctx: Context): Boolean {

    return (ctx.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE

}