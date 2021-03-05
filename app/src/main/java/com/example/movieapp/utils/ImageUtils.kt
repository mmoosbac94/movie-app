package com.example.movieapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.movieapp.R
import com.example.movieapp.models.MovieProperty


const val MOVIE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
fun loadPicture(movie: MovieProperty): MutableState<Bitmap?> {

    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)

    val movieUrl = MOVIE_BASE_URL + movie.movieImg

    if (movie.movieImg.isEmpty()) {
        bitmapState.value =
            BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.no_image)
        return bitmapState
    }

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(R.drawable.test_image)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(movieUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

    return bitmapState
}