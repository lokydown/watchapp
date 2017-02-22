package app.watchapp.rest;

import app.watchapp.rest.service.OmdbService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Douglas on 21/02/17.
 */

public class OmdbClient {

    public static final String BASE_URL = "http://www.omdbapi.com";
    private OmdbService omdbService;

    public OmdbClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build();

        omdbService = retrofit.create(OmdbService.class);
    }

    public OmdbService getOmdbService() {
        return omdbService;
    }

}
