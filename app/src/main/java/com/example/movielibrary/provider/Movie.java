package com.example.movielibrary.provider;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName =  "Movies")
public class Movie {
    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "cost")
    private int cost;

    @ColumnInfo(name = "keywords")
    private String keywords;

    public Movie(String title, String year, String country, String genre, int cost, String keywords) {
        this.title = title;
        this.year = year;
        this.country = country;
        this.genre = genre;
        this.cost = cost;
        this.keywords = keywords;
    }

    public int getId() {
        return id;
    }

    public void setId(@NonNull int id){
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public String getGenre() {
        return genre;
    }

    public int getCost() {
        return cost;
    }

    public String getKeywords() {
        return keywords;
    }
}
