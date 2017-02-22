package app.watchapp;

import android.app.Application;

import com.orm.SugarContext;

import app.watchapp.rest.OmdbClient;

/**
 * Created by Douglas on 21/02/17.
 */

public class App extends Application {
    private static OmdbClient omdbClient;

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);

        omdbClient = new OmdbClient();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    public static OmdbClient getOmdbClient() {
        return omdbClient;
    }
}
