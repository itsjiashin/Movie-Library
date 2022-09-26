package com.example.movielibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movielibrary.provider.Movie;
import com.example.movielibrary.provider.MovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> myList = new ArrayList<String>();
    ArrayAdapter myAdapter;
    DrawerLayout drawer;
    private MovieViewModel mMovieViewModel;
    MyRecyclerViewAdapter adapter;
    DatabaseReference myRef;
    View myFrameLayout;
    int initial_X, initial_Y;
    GestureDetector gestureDetector;
    ScaleGestureDetector scaleGestureDetector;
    View myConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        adapter = new MyRecyclerViewAdapter();

        mMovieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        mMovieViewModel.getAllMovies().observe(this, newData -> {
            adapter.setData(newData);
            adapter.notifyDataSetChanged();
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Movies");



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText title = findViewById(R.id.editTextTitle);
                EditText year = findViewById(R.id.editTextYear);
                EditText country = findViewById(R.id.editTextCountry);
                EditText genre = findViewById(R.id.editTextGenre);
                EditText cost = findViewById(R.id.editTextCost);
                EditText keywords = findViewById(R.id.editTextKeywords);
                String movieTitle = title.getText().toString();
                String movieYear = year.getText().toString();
                String movieCountry = country.getText().toString();
                String movieGenre = genre.getText().toString();
                int movieCost = Integer.parseInt(cost.getText().toString());
                String movieKeywords = keywords.getText().toString();

                String temp = movieTitle + " | " + movieYear;
                myList.add(temp);
                myAdapter.notifyDataSetChanged();

                mMovieViewModel.insert(new Movie(movieTitle, movieYear, movieCountry, movieGenre, movieCost, movieKeywords));
                myRef.push().setValue(new Movie(movieTitle, movieYear, movieCountry, movieGenre, movieCost, movieKeywords));
            }
        });

        ListView listView = findViewById(R.id.listView);
        myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(myAdapter);

        drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        myFrameLayout = findViewById(R.id.frameLayout);
        myConstraintLayout = findViewById(R.id.myConstraintLayout);

        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        scaleGestureDetector = new ScaleGestureDetector(this, new MyScaleGestureDetector());

        myFrameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });

        myConstraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scaleGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });



    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void addMovie(View view){
        EditText newMovie = findViewById(R.id.editTextTitle);
        String movieTitle = newMovie.getText().toString();
        EditText year = findViewById(R.id.editTextYear);
        String movieYear = year.getText().toString();
        EditText country = findViewById(R.id.editTextCountry);
        String movieCountry = country.getText().toString();
        EditText genre = findViewById(R.id.editTextGenre);
        String movieGenre = genre.getText().toString();
        EditText cost = findViewById(R.id.editTextCost);
        int movieCost = Integer.parseInt(cost.getText().toString());
        EditText keywords = findViewById(R.id.editTextKeywords);
        String movieKeywords = keywords.getText().toString();

        String temp = movieTitle + " | " + movieYear;
        myList.add(temp);
        myAdapter.notifyDataSetChanged();

        mMovieViewModel.insert(new Movie(movieTitle, movieYear, movieCountry, movieGenre, movieCost, movieKeywords));
        myRef.push().setValue(new Movie(movieTitle, movieYear, movieCountry, movieGenre, movieCost, movieKeywords));
    }
    


    public void resetField(View view){
        EditText title = findViewById(R.id.editTextTitle);
        EditText year = findViewById(R.id.editTextYear);
        EditText country = findViewById(R.id.editTextCountry);
        EditText genre = findViewById(R.id.editTextGenre);
        EditText cost = findViewById(R.id.editTextCost);
        EditText keywords = findViewById(R.id.editTextKeywords);
        String emptyString = "";
        title.setText(emptyString);
        year.setText(emptyString);
        country.setText(emptyString);
        genre.setText(emptyString);
        cost.setText(emptyString);
        keywords.setText(emptyString);
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.buttonAddMovie) {
                EditText title = findViewById(R.id.editTextTitle);
                EditText year = findViewById(R.id.editTextYear);
                EditText country = findViewById(R.id.editTextCountry);
                EditText genre = findViewById(R.id.editTextGenre);
                EditText cost = findViewById(R.id.editTextCost);
                EditText keywords = findViewById(R.id.editTextKeywords);
                String movieTitle = title.getText().toString();
                String movieYear = year.getText().toString();
                String movieCountry = country.getText().toString();
                String movieGenre = genre.getText().toString();
                int movieCost = Integer.parseInt(cost.getText().toString());
                String movieKeywords = keywords.getText().toString();

                String temp = movieTitle + " | " + movieYear;
                myList.add(temp);
                mMovieViewModel.insert(new Movie(movieTitle, movieYear, movieCountry, movieGenre, movieCost, movieKeywords));
                myRef.push().setValue(new Movie(movieTitle, movieYear, movieCountry, movieGenre, movieCost, movieKeywords));
                myAdapter.notifyDataSetChanged();
            } else if (id == R.id.buttonRemoveLastMovie) {
                myList.remove(myList.size()-1);
                myAdapter.notifyDataSetChanged();
            } else if (id == R.id.buttonRemoveAllMovie){
                mMovieViewModel.deleteAll();
                myRef.removeValue();
            } else if (id == R.id.buttonClose){
                finish();
            } else if (id == R.id.buttonListAllMovies){
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }

            // close the drawer
            drawer.closeDrawers();
            // tell the OS
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.buttonClearFields){
            EditText title = findViewById(R.id.editTextTitle);
            EditText year = findViewById(R.id.editTextYear);
            EditText country = findViewById(R.id.editTextCountry);
            EditText genre = findViewById(R.id.editTextGenre);
            EditText cost = findViewById(R.id.editTextCost);
            EditText keywords = findViewById(R.id.editTextKeywords);
            String emptyString = "";
            title.setText(emptyString);
            year.setText(emptyString);
            country.setText(emptyString);
            genre.setText(emptyString);
            cost.setText(emptyString);
            keywords.setText(emptyString);
        } else if (id == R.id.buttonTotalMovies){
            int numberOfMovies = myList.size();
            Toast.makeText(this, "Total number of movies are " + numberOfMovies, Toast.LENGTH_SHORT).show();
        }
//        return super.onOptionsItemSelected(item);
            return true;
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            EditText cost = findViewById(R.id.editTextCost);
            int newCost = Integer.parseInt(cost.getText().toString()) + 150;
            cost.setText(newCost + "");
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            EditText title = findViewById(R.id.editTextTitle);
            EditText year = findViewById(R.id.editTextYear);
            EditText country = findViewById(R.id.editTextCountry);
            EditText genre = findViewById(R.id.editTextGenre);
            EditText cost = findViewById(R.id.editTextCost);
            EditText keywords = findViewById(R.id.editTextKeywords);
            String emptyString = "";
            title.setText(emptyString);
            year.setText(emptyString);
            country.setText(emptyString);
            genre.setText(emptyString);
            cost.setText(emptyString);
            keywords.setText(emptyString);
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            EditText year = findViewById(R.id.editTextYear);
            EditText keywords = findViewById(R.id.editTextKeywords);
            int currentYear = Integer.parseInt(year.getText().toString());
            if (Math.abs(e1.getY() - e2.getY()) > 0 && Math.abs(e1.getY() - e2.getY()) <= 20){
                int newYear = currentYear - Math.round(distanceX);
                year.setText(newYear + "");
            }
            else if (Math.abs(e1.getX() - e2.getX()) > 0 && Math.abs(e1.getX()-e2.getX()) <= 20){
                keywords.setText(keywords.getText().toString().toUpperCase());
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (velocityX > 1000 || velocityY > 1000){
                moveTaskToBack(true);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            EditText title = findViewById(R.id.editTextTitle);
            EditText year = findViewById(R.id.editTextYear);
            EditText country = findViewById(R.id.editTextCountry);
            EditText genre = findViewById(R.id.editTextGenre);
            EditText cost = findViewById(R.id.editTextCost);
            EditText keywords = findViewById(R.id.editTextKeywords);
            title.setText("Batman");
            year.setText("2022");
            country.setText("Malaysia");
            genre.setText("Action");
            cost.setText("50");
            keywords.setText("Superhero");
            return super.onDoubleTap(e);
        }
    }

    class MyScaleGestureDetector extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            EditText keywords = findViewById(R.id.editTextKeywords);
            keywords.setText(keywords.getText().toString().toLowerCase());
            return super.onScale(detector);
        }
    }
}


