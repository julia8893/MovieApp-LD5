package com.example.mad03_fragments_and_navigation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mad03_fragments_and_navigation.models.Movie
import com.example.mad03_fragments_and_navigation.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieFavoritesViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    // TODO implement me


    val favoriteMovies = getAll()


    private fun getAll() {
        viewModelScope.launch {
            repository.getAll()
        }
    }

    fun clearFavorites() {
        viewModelScope.launch {
            repository.clearTable()
        }
    }

    suspend fun clearTable() = repository.clearTable()


    fun addToFavorites(movie: Movie){

        viewModelScope.launch {
            val movieId = repository.create(movie)
            Log.i("MovieFavoritesViewModel", "ID is $movieId")
        }
    }

    fun createMovie(movie: Movie){
        viewModelScope.launch {
            val movieId = repository.create(movie)
            Log.i("MovieFavoritesViewModel", "ID is $movieId")
        }
    }

}