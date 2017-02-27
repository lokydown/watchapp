package app.watchapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import app.watchapp.App;
import app.watchapp.R;
import app.watchapp.activity.MainActivity;
import app.watchapp.adapter.SearchAdapter;
import app.watchapp.pojo.MovieList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Douglas on 21/02/2017.
 */

public class SearchFragment extends Fragment {

    @BindView(R.id.ll_content) LinearLayout layoutContent;
    @BindView(R.id.ll_no_content) LinearLayout layoutNoContent;
    @BindView(R.id.ll_loading) LinearLayout layoutLoading;
    @BindView(R.id.et_filter) EditText etFilter;
    @BindView(R.id.rv_search_result_list) RecyclerView recyclerView;

    private MovieList movies;
    private Unbinder unbinder;
    private View fragmentView;
    private SearchAdapter adapter;

    public SearchFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideFloatingActionButton();
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.actionRemoveAll).setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.search_fragment, container, false);

        unbinder = ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnTextChanged(value = R.id.et_filter, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void autoSearch(Editable editable) {
        setLoadingLayout();
        autoSearchTask(editable.toString());
    }

    private void renderSuggestionMovie() {
        adapter = new SearchAdapter(fragmentView.getContext(), movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void autoSearchTask(String filter) {
        App.getOmdbClient().getOmdbService().getMovies(filter)
            .enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    if (response.isSuccessful()) {
                        movies = response.body();
                        if (movies.getMovies() != null) {
                            if (movies.getMovies().get(0).getError() == null) {
                                renderSuggestionMovie();
                                setDefaultContentLayout();
                                return;
                            }
                        }
                    }
                    setNoContentLayout();
                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                    setNoContentLayout();
                    Toast.makeText(fragmentView.getContext(), fragmentView.getContext().getResources().getString(R.string.err_autosearch_movie), Toast.LENGTH_LONG).show();
                }
            });
    }

    private void setDefaultContentLayout() {
        layoutContent.setVisibility(View.VISIBLE);
        layoutNoContent.setVisibility(View.GONE);
        layoutLoading.setVisibility(View.GONE);
    }

    private void setNoContentLayout() {
        layoutContent.setVisibility(View.GONE);
        layoutNoContent.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);

    }

    private void setLoadingLayout() {
        layoutContent.setVisibility(View.GONE);
        layoutNoContent.setVisibility(View.GONE);
        layoutLoading.setVisibility(View.VISIBLE);
    }

}
