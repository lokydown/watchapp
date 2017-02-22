package app.watchapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.orm.SugarRecord;

import java.util.List;

import app.watchapp.R;
import app.watchapp.activity.MainActivity;
import app.watchapp.adapter.WatchAdapter;
import app.watchapp.pojo.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Douglas on 21/02/2017.
 */

public class WatchFragment extends Fragment {

    @BindView(R.id.rvWatchlist) RecyclerView recyclerView;
    @BindView(R.id.tvWatchlistInfo) TextView tvWatchlistInfo;

    private WatchAdapter adapter;
    private List<Movie> movies;
    private View fragmentView;
    private Unbinder unbinder;


    public WatchFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.watch_fragment, container, false);

        unbinder = ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showFloatingActionButton();
        }

        render();
    }

    public void render() {
        renderList();
        renderListInfo();
    }

    public void renderList() {
        movies = Lists.newArrayList(SugarRecord.listAll(Movie.class, "id"));
        adapter = new WatchAdapter(this, fragmentView.getContext(), movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void renderListInfo() {
        tvWatchlistInfo.setText(fragmentView.getContext().getString(R.string.info_list_null));

        if (movies != null) {
            if (movies.size() == 1) {
                tvWatchlistInfo.setText(movies.size() + " " + fragmentView.getContext().getString(R.string.info_list_size_tiny));
            } else if (movies.size() >= 2) {
                tvWatchlistInfo.setText(movies.size() + " " + fragmentView.getContext().getString(R.string.info_list_size));
            }
        }
    }
}
