package com.example.movieapp.overview


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R
import com.example.movieapp.Utils
import com.example.movieapp.database.MovieProperty
import com.example.movieapp.databinding.RecyclerviewItemBinding
import com.example.movieapp.search.SearchFragmentDirections


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
        holder.bind(holder, movie)
    }


    class MovieViewHolder(itemBinding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private var movieTitleView: TextView = itemBinding.movieTitle
        private var movieVoteAverageView: TextView = itemBinding.voteAverage
        private var movieImageView: ImageView = itemBinding.movieImage

        fun bind(viewHolder: MovieViewHolder, movieProperty: MovieProperty) {

            initClickListener(viewHolder, movieProperty)

            movieTitleView.text = movieProperty.title
            movieVoteAverageView.text = itemView.context.getString(
                R.string.voteAverage,
                movieProperty.voteAverage.toString()
            )
            Utils.bindMovieImage(movieProperty.movieImg, movieImageView)
        }

        private fun initClickListener(viewHolder: MovieViewHolder, movie: MovieProperty) {
            viewHolder.itemView.setOnClickListener {
                it.findNavController().navigate(
                    if(movie.type == "default") {
                        SearchFragmentDirections.actionSearchFragmentToDetailFragment(movie)
                    } else OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(movie)
                )
            }
        }
    }

}