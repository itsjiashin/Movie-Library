package com.example.movielibrary.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieRepository {
    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;

    MovieRepository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovies = mMovieDao.getAllMovie();
    }
    LiveData<List<Movie>> getAllMovies() {
        return mAllMovies;
    }
    void insert(Movie movie) {
        MovieDatabase.databaseWriteExecutor.execute(() -> mMovieDao.addMovie(movie));
    }

    void deleteAll(){
        MovieDatabase.databaseWriteExecutor.execute(()->{
            mMovieDao.deleteAllMovies();
        });
    }

    void deleteByYear(String year){
        MovieDatabase.databaseWriteExecutor.execute(()->{
            mMovieDao.deleteByYear(year);
        });
    }
}
