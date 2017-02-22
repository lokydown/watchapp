package app.watchapp.rest.service;

import app.watchapp.pojo.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Douglas on 21/02/17.
 */

public interface OmdbService {

    @GET("?plot=full&r=json")
    Call<MovieList> getMovies(@Query("s") String filter);

}
