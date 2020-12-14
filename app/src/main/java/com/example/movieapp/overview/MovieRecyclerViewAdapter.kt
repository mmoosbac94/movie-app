package com.example.movieapp.overview


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R
import com.example.movieapp.database.MovieProperty
import com.example.movieapp.databinding.RecyclerviewItemBinding


class MovieRecyclerViewAdapter(var movieList: List<MovieProperty>) :
    RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movieList.size


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: MovieProperty = movieList[position]
        holder.bind(movie)
    }


    class MovieViewHolder(itemBinding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private var movieTitleView: TextView = itemBinding.movieTitle
        private var movieVoteAverageView: TextView = itemBinding.voteAverage
        private var movieImageView: ImageView = itemBinding.movieImage

        fun bind(movieProperty: MovieProperty) {
            movieTitleView.text = movieProperty.title
            movieVoteAverageView.text = movieProperty.voteAverage.toString()
            bindMovieImage(movieProperty.movieImg)
        }

        private fun bindMovieImage(movieImage: String) {
            val movieImageUrl = "https://image.tmdb.org/t/p/w500$movieImage"
            Glide.with(movieImageView.context).load(movieImageUrl).apply(
                RequestOptions().placeholder(
                    R.drawable.loading_animation
                )
            ).into(movieImageView)
        }
    }

}