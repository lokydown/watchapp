package app.watchapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.orm.SugarRecord;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import app.watchapp.App;
import app.watchapp.R;
import app.watchapp.event.FABEvent;
import app.watchapp.fragment.SearchFragment;
import app.watchapp.fragment.WatchFragment;
import app.watchapp.pojo.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Douglas on 21/02/2017.
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        setDefaultFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
        App.getEventBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //EventBus.getDefault().unregister(this);
        App.getEventBus().unregister(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.actionRemoveAll) {

            new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.action_reset_list))
                .setMessage(getResources().getString(R.string.warn_reset_list))
                .setPositiveButton(getResources().getString(R.string.action_yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SugarRecord.deleteAll(Movie.class);
                                setDefaultFragment();
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.info_successfully_reset_list), Toast.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.action_no),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .create()
                .show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setDefaultFragment() {
        getFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, new WatchFragment())
            .commit();
    }

    @OnClick(R.id.fab)
    void goToSearch() {
        getFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, new SearchFragment())
            .addToBackStack(null)
            .commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FABEvent event) {
        if (event.getToggle()) {
            fab.show();
        } else {
            fab.hide();
        }
    }

}
