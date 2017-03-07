package app.watchapp.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Douglas on 21/02/17.
 */

public class MovieList {

    @SerializedName("Search")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
