package com.example.movielibrary.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("select * from Movies")
    LiveData<List<Movie>> getAllMovie();

    @Query("select * from Movies where id=:name")
    List<Movie> getMovie(String name);

    @Insert
    void addMovie(Movie movie);

    @Query("delete from Movies where title= :name")
    void deleteMovie(String name);

    @Query("delete FROM Movies")
    void deleteAllMovies();

    @Query("delete from Movies where year= :year")
    void deleteByYear(String year);

}
