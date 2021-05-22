package com.example.mad03_fragments_and_navigation.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mad03_fragments_and_navigation.models.Movie

@Dao
interface MovieDao {
    // TODO implement me

    @Insert
    suspend fun create(movie: Movie): Long


    @Update
    suspend fun update(movie: Movie)


    @Query("DELETE FROM my_watchlist WHERE id = :movieId")
    suspend fun delete(movieId: Long)

    @Query("DELETE FROM my_watchlist")
    suspend fun clearTable()

    @Query("SELECT * FROM my_watchlist ORDER BY id")
    fun getAll(): LiveData<List<Movie>>
}