package com.geek.cinemax.utils

import android.content.Context
import android.content.Intent
import androidx.navigation.NavHostController
import com.geek.cinemax.AppConstants
import com.geek.cinemax.ui.activity.VideoActivity
import dagger.hilt.android.qualifiers.ApplicationContext

fun NavHostController.navigateToMovieDetail(id: Long) {
    this.navigate(
        route = AppConstants.ROUTE_MOVIE_DETAIL_PATH.replace(
            "{${AppConstants.ROUTE_DETAIL_ARG_NAME}}",
            "$id"
        )
    )
}

fun NavHostController.navigateToTvDetail(id: Long) {
    this.navigate(
        route = AppConstants.ROUTE_TV_DETAIL_PATH.replace(
            "{${AppConstants.ROUTE_DETAIL_ARG_NAME}}",
            "$id"
        )
    )
}

fun navigateToVideo(@ApplicationContext context: Context, key: String) {
    val intent = Intent(context, VideoActivity::class.java)
    intent.putExtra(AppConstants.INTENT_VIDEO_KEY, key)
    context.startActivity(intent)
}