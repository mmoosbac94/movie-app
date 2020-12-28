package com.example.movieapp.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.Utils
import com.example.movieapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailBinding.inflate(inflater)

        init()

        return binding.root
    }


    private fun init() {
        val args = DetailFragmentArgs.fromBundle(requireArguments())
        Utils.bindMovieImage(args.movie.movieImg, binding.movieImageView)

        binding.detailTitle.text = args.movie.title
        binding.detailVoteAverage.text =
            context?.getString(R.string.voteAverage, args.movie.voteAverage.toString())
        binding.detailReleaseDate.text = args.movie.releaseDate

    }
}