package com.okuklina.pallas.activities;
import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.okuklina.pallas.adapter.DictionaryAdapter;
import com.okuklina.pallas.data.DictionariesContract;
import com.okuklina.pallas.R;
import com.okuklina.pallas.data.DictionaryLoader;


public class ScrollingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener, DictionaryAdapter.OnListItemClickListener {
    private static final String TAG = ScrollingActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private boolean mIsRefreshing;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        this.mRecyclerView = (RecyclerView) this.findViewById(R.id.items_list_recycler_view);
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipe_refresh_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getActionBar();



        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        this.mSwipeRefreshLayout.setColorSchemeColors(this.getResources().getIntArray(R.array.swipe_to_refresh_progress_colors));

        this.getSupportLoaderManager().initLoader(0, null, this);

        if (savedInstanceState == null) {
            this.onRefresh();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScrollingActivity.this, CreateDictionaryActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return DictionaryLoader.newAllDictionariesInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data == null) {
            Log.v(ScrollingActivity.TAG, "onLoadFinished null");
        }
        else {
            Log.v(ScrollingActivity.TAG, "onLoadFinished " + data.getCount());
        }
        this.mAdapter = new DictionaryAdapter(this, data).setListener(this);
        this.mAdapter.setHasStableIds(true);
        this.mRecyclerView.setAdapter(this.mAdapter);

        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onListItemSelected(long articleId) {
        Intent intent = new Intent(this, ArticleContentActivity.class);
        intent.putExtra("article_id", articleId);
        startActivity(intent);
    }
}


