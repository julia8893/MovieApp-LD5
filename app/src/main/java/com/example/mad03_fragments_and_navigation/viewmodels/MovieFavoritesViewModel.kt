package com.example.mad03_fragments_and_navigation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.mad03_fragments_and_navigation.models.Movie
import com.example.mad03_fragments_and_navigation.repositories.MovieRepository
import kotlinx.coroutines.launch

class MovieFavoritesViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    val favoriteMovies: LiveData<List<Movie>> = repository.getAll()

    fun clearTable() {
        viewModelScope.launch {
            repository.clearTable()
        }
    }

    fun addToFavorites(movie: Movie) {

        viewModelScope.launch {
            val movieId = repository.create(movie)
            Log.i("MovieFavoritesViewModel", "ID is $movieId")
        }
    }

    fun editMovie(movie: Movie) {

        viewModelScope.launch {
            repository.update(movie)
        }
    }

    fun deleteMovie(movieId: Long) {

        viewModelScope.launch {
            repository.delete(movieId)
        }
    }

}