package com.example.movielibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielibrary.provider.Movie;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    List<Movie> data = new ArrayList<>();

    public MyRecyclerViewAdapter(){
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
//        Log.d("week6App","onCreateViewHolder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.year.setText(data.get(position).getYear());
        holder.country.setText(data.get(position).getCountry());
        holder.genre.setText(data.get(position).getGenre());
        holder.cost.setText(Integer.toString(data.get(position).getCost()));
        holder.keywords.setText(data.get(position).getKeywords());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity2.mMovieViewModel.deleteByYear(holder.year.getText().toString());

            }
        });

//        Log.d("week6App","onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView year;
        public TextView country;
        public TextView genre;
        public TextView cost;
        public TextView keywords;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.editTextCardTitle);
            year = itemView.findViewById(R.id.editTextCardYear);
            country = itemView.findViewById(R.id.editTextCardCountry);
            genre = itemView.findViewById(R.id.editTextCardGenre);
            cost = itemView.findViewById(R.id.editTextCardCost);
            keywords = itemView.findViewById(R.id.editTextCardKeywords);
        }
    }
}
