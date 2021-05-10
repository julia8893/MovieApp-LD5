package com.example.mad03_fragments_and_navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mad03_fragments_and_navigation.database.AppDatabase
import com.example.mad03_fragments_and_navigation.database.MovieDao
import com.example.mad03_fragments_and_navigation.databinding.FragmentMovieDetailBinding
import com.example.mad03_fragments_and_navigation.models.Movie
import com.example.mad03_fragments_and_navigation.models.MovieStore
import com.example.mad03_fragments_and_navigation.repositories.MovieRepository
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModel
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModelFactory

class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var sharedViewModel: MovieFavoritesViewModel
    private lateinit var dao: MovieDao
    private lateinit var factory: MovieFavoritesViewModelFactory
    private lateinit var movieRepository: MovieRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)

        dao = AppDatabase.getInstance(requireActivity().application).MovieDao
        val instance = MovieRepository.getInstance(dao)
        movieRepository = MovieRepository(dao)
        factory = MovieFavoritesViewModelFactory(movieRepository)
        sharedViewModel = ViewModelProvider(this, factory).get(MovieFavoritesViewModel::class.java)
        binding.viewModelMovieFavorites = sharedViewModel
        binding.lifecycleOwner = this

        val args =
            MovieDetailFragmentArgs.fromBundle(requireArguments())   // get navigation arguments

        when (val movieEntry = MovieStore().findMovieByUUID(args.movieId)) {
            null -> {
                Toast.makeText(requireContext(), "Could not load movie data", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigateUp()
            }
            else -> binding.movie = movieEntry
        }

        binding.addToFavorites.setOnClickListener {
            val selectedMovie = MovieStore().findMovieByUUID(args.movieId)
            selectedMovie?.let { movie -> sharedViewModel.addToFavorites(movie) }

            Toast.makeText(requireContext(), "Movie saved to favorites.", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}