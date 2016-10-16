package com.okuklina.pallas.adapter;

import android.app.Activity;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.okuklina.pallas.R;
import com.okuklina.pallas.data.DictionaryLoader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by olgakuklina on 2016-10-15.
 */

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {
    public static final String TAG = DictionaryAdapter.class.getSimpleName();
    private final int screenWidth;
    public DictionaryAdapter(Activity activity, Cursor cursor ) {
        mActivity = activity;
        mCursor = cursor;
        this.screenWidth = this.mActivity.getBaseContext().getResources().getDisplayMetrics().widthPixels;
    }


    public interface OnListItemClickListener {
        void onListItemSelected(long articleId);

        DictionaryAdapter.OnListItemClickListener listner = new DictionaryAdapter.OnListItemClickListener() {
            @Override public void onListItemSelected(long itemId) {
            }
        };
    }

    public DictionaryAdapter setListener(@NonNull DictionaryAdapter.OnListItemClickListener listener) {
        this.mListener = listener;
        return this;
    }

    private DictionaryAdapter.OnListItemClickListener mListener = DictionaryAdapter.OnListItemClickListener.listner;
    private final Activity mActivity;
    private final Cursor mCursor;

    @Override
    public long getItemId(int position) {
        Log.v(DictionaryAdapter.TAG, "getItemId = " + position );
        this.mCursor.moveToPosition(position);
        return this.mCursor.getLong(DictionaryLoader.Query._ID);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        this.mCursor.moveToPosition(position);
        holder.titleView.setBackgroundColor(this.mCursor.getInt(DictionaryLoader.Query.COLOR));
        holder.titleView.setText(this.mCursor.getString(DictionaryLoader.Query.TITLE));
        holder.subtitleView.setBackgroundColor(this.mCursor.getInt(DictionaryLoader.Query.COLOR));
        holder.subtitleView.setText(
                DateUtils.getRelativeTimeSpanString(
                        this.mCursor.getLong(DictionaryLoader.Query.CREATE_DATE),
                        System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_ALL)
                        + " by "
                        + this.mCursor.getString(DictionaryLoader.Query._ID));

        holder.thumbnailView.setLayoutParams(new LinearLayout.LayoutParams(this.screenWidth, (int) (this.screenWidth /2)));
        Picasso pic = Picasso.with(this.mActivity);
        pic.load(this.mCursor.getString(DictionaryLoader.Query.PHOTO_URL))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.color.colorPrimary)
                .into(holder.thumbnailView);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnailView;
        public TextView titleView;
        public TextView subtitleView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.thumbnailView = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.titleView = (TextView) itemView.findViewById(R.id.dictionary_title);
            this.subtitleView = (TextView) itemView.findViewById(R.id.dictionary_subtitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DictionaryAdapter.this.mListener.onListItemSelected(ViewHolder.this.getItemId());
                }
            });
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v(DictionaryAdapter.TAG, "ListItemsAdapter::onCreateViewHolder");

        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        DictionaryAdapter.ViewHolder vh = new DictionaryAdapter.ViewHolder(view);

        return vh;
    }
    @Override
    public int getItemCount() {
        return this.mCursor.getCount();
    }
}
