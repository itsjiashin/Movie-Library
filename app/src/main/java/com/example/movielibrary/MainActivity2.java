package com.example.movielibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.movielibrary.provider.Movie;
import com.example.movielibrary.provider.MovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyRecyclerViewAdapter adapter;
    static MovieViewModel mMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager

        adapter = new MyRecyclerViewAdapter();
       // adapter.setData(data);
        recyclerView.setAdapter(adapter);

        mMovieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        mMovieViewModel.getAllMovies().observe(this, newData -> {
            adapter.setData(newData);
            adapter.notifyDataSetChanged();
//            tv.setText(newData.size() + "");
        });

        FloatingActionButton fabBack = findViewById(R.id.floatingActionButtonBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}