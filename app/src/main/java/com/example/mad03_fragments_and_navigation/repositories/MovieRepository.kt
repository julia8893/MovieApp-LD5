package com.example.mad03_fragments_and_navigation.repositories

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mad03_fragments_and_navigation.database.MovieDao
import com.example.mad03_fragments_and_navigation.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val movieDao: MovieDao) {
    // TODO implement me

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(dao: MovieDao) =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(dao).also { instance = it }
            }
    }

    suspend fun create(movie: Movie): Long {
        return movieDao.create(movie)
    }


    suspend fun update(movie: Movie) {
        movieDao.update(movie)
    }


    suspend fun delete(movieId: Long) {
        movieDao.delete(movieId)
    }


    suspend fun clearTable() =
        withContext(Dispatchers.IO) {
            movieDao.clearTable()
        }

    fun getAll(): LiveData<List<Movie>> {
        return movieDao.getAll()
    }

}