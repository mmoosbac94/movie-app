package com.example.movieapp

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class Utils {

    companion object {
        fun bindMovieImage(movieImage: String?, movieImageView: ImageView) {
            val movieImageUrl = "https://image.tmdb.org/t/p/w500$movieImage"
            Glide.with(movieImageView.context).load(movieImageUrl).apply(
                RequestOptions().placeholder(
                    R.drawable.loading_animation
                )
            ).into(movieImageView)
        }
    }


}