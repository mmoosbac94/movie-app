package com.example.movieapp

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class Utils {

    companion object {

        fun bindMovieImage(
            movieImage: String?,
            movieImageView: ImageView,
            movieImageUrl: String = "https://image.tmdb.org/t/p/w500$movieImage"
        ) {

            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.loading_animation)
            requestOptions.error(R.drawable.no_image)

            Glide.with(movieImageView.context).setDefaultRequestOptions(requestOptions)
                .load(movieImageUrl).into(movieImageView)
        }
    }


}