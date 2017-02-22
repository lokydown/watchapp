package app.watchapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.watchapp.R;
import app.watchapp.fragment.WatchFragment;
import app.watchapp.pojo.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Douglas on 21/02/2017.
 */

public class WatchAdapter extends RecyclerView.Adapter<WatchAdapter.CustomViewHolder> {

    private List<Movie> movies;
    private WatchFragment fragment;
    private Context context;

    public WatchAdapter(WatchFragment fragment, Context context, List<Movie> movies) {
        this.fragment = fragment;
        this.context = context;
        this.movies = movies;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CustomViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.watch_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        final Movie movie = movies.get(i);

        if (movie.getPosterImage() != null) {
            customViewHolder.ivMovieThumbnail.setImageBitmap(BitmapFactory.decodeByteArray(movie.getPosterImage(), 0, movie.getPosterImage().length));
        }
        customViewHolder.tvImdbId.setText(movie.getImdbID());
        customViewHolder.tvTitle.setText(movie.getTitle());
        customViewHolder.tvInfo.setText(movie.getType().toUpperCase() + " | " + movie.getYear());

        customViewHolder.btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie != null) {
                    new AlertDialog.Builder(context)
                        .setTitle(context.getResources().getString(R.string.action_remove))
                        .setMessage(context.getResources().getString(R.string.warn_remove_movie))
                        .setPositiveButton(context.getResources().getString(R.string.action_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    movie.delete();
                                    movies.remove(i);
                                    fragment.renderListInfo();
                                    notifyDataSetChanged();
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
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != movies ? movies.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivMovieThumbnail) ImageView ivMovieThumbnail;
        @BindView(R.id.tvMovieImdbid) TextView tvImdbId;
        @BindView(R.id.tvMovieTitle) TextView tvTitle;
        @BindView(R.id.tvMovieInfo) TextView tvInfo;
        @BindView(R.id.btRemoveMovie) Button btRemove;

        public CustomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}