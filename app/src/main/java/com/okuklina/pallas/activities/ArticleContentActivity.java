package com.okuklina.pallas.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.okuklina.pallas.R;
import com.okuklina.pallas.adapter.DictionaryAdapter;
import com.okuklina.pallas.data.DictionaryLoader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by olgakuklina on 2016-10-30.
 */

public class ArticleContentActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = ArticleContentActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 2;
    private Uri uri;
    private Long data;
    private Cursor mCursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_content_view);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setTitle("");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getActionBar();

        data = getIntent().getLongExtra("article_id", 0);
        Log.v(TAG, "intent data" + data);

        this.getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                uri = data.getData();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return DictionaryLoader.newInstanceForItemId(this, data);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.mCursor = data;
        Log.v(TAG, "mCursor " + mCursor);
        if (this.mCursor != null && !this.mCursor.moveToFirst()) {
            Log.e(TAG, "Error reading item detail cursor");
            this.mCursor.close();
            this.mCursor = null;
        }
        this.bindViews();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.mCursor = null;
        this.bindViews();
    }

    private void bindViews() {

        ImageView imageView = (ImageView) this.findViewById(R.id.image_background);
        if (this.mCursor != null) {
            Log.v(TAG, "bindViews mCursor " + mCursor);
            ColorDrawable userColor = new ColorDrawable(mCursor.getInt(DictionaryLoader.Query.COLOR));
            Picasso pic = Picasso.with(this);
            pic.setLoggingEnabled(true);
            pic.load(this.mCursor.getString(DictionaryLoader.Query.PHOTO_URL))
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .placeholder(userColor)
                    .into(imageView);
        }


    }
}
