package com.example.movieapp.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.fragment.findNavController
import com.example.movieapp.composables.MovieColumn
import com.example.movieapp.models.MovieProperty


class SearchFragment : Fragment() {

    // Access viewModel via Koin
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Scaffold(backgroundColor = Color.Black) {
                        Column {
                            CustomSearchBar()
                            MovieLazyColumn()
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun MovieLazyColumn() {

        val onItemClick = { movie: MovieProperty ->
            requireParentFragment().findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(movie)
            )
        }

        val movieList by viewModel.movieList.observeAsState(emptyList())
        MovieColumn(movies = movieList, onItemClick, cols = 1)
    }


    @Composable
    fun CustomSearchBar() {
        Surface(
            elevation = 8.dp,
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                CustomTextField()
            }
        }
    }

    @Composable
    fun CustomTextField() {

        var text by remember { mutableStateOf("") }

        TextField(
            value = text,
            onValueChange = { newValue ->
                text = newValue
                if (newValue.isNotEmpty()) viewModel.getMovie(newValue)
            },
            label = {
                Text(text = "Search")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier.fillMaxWidth(),
            activeColor = Color.Gray
        )
    }
}