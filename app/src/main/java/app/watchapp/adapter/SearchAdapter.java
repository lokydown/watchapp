package app.watchapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.VectorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import app.watchapp.R;
import app.watchapp.pojo.Movie;
import app.watchapp.pojo.MovieList;
import app.watchapp.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Douglas on 21/02/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CustomViewHolder> {

    private MovieList movies;
    private Context context;

    public SearchAdapter(Context context, MovieList movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public SearchAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SearchAdapter.CustomViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {
        final Movie movie = movies.getMovies().get(i);

        Picasso.with(context).load(movie.getPosterUrl())
                .placeholder(R.drawable.ic_add_black_18dp)
                .error(R.drawable.ic_clear_black_18dp)
                .into(customViewHolder.ivPoster);

        customViewHolder.tvImdbId.setText(movie.getImdbID());
        customViewHolder.tvTitle.setText(movie.getTitle());
        customViewHolder.tvInfo.setText(movie.getType().toUpperCase() + " | " + movie.getYear());

        customViewHolder.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movie.find(Movie.class, "imdb_id = ?", movie.getImdbID()).isEmpty()) {
                    new AlertDialog.Builder(context)
                        .setTitle(context.getResources().getString(R.string.action_duplicated))
                        .setMessage(context.getResources().getString(R.string.warn_duplicate_movie))
                        .setPositiveButton(context.getResources().getString(R.string.action_yes),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    saveMovie(customViewHolder, movie);
                                }
                            })
                        .setNegativeButton(context.getResources().getString(R.string.action_no),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                        .create()
                        .show();
                } else {
                    saveMovie(customViewHolder, movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != movies ? movies.getMovies().size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivResultThumbnail) ImageView ivPoster;
        @BindView(R.id.tvResultImdbid) TextView tvImdbId;
        @BindView(R.id.tvResultTitle) TextView tvTitle;
        @BindView(R.id.tvResultInfo) TextView tvInfo;
        @BindView(R.id.btAdd) Button btAdd;

        public CustomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void saveMovie(CustomViewHolder customViewHolder, Movie movie) {
        if (customViewHolder.ivPoster.getDrawable() != null) {
            if (!(customViewHolder.ivPoster.getDrawable() instanceof VectorDrawable)) {
                movie.setPosterImageBitmapToByte(((BitmapDrawable) customViewHolder.ivPoster.getDrawable()).getBitmap());
            }
        }
        movie.save();

        Toast.makeText(context, context.getResources().getString(R.string.info_successfully_added), Toast.LENGTH_SHORT).show();
    }
}