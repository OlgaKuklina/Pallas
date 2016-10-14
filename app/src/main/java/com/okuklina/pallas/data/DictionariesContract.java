package com.okuklina.pallas.data;

import android.net.Uri;

public class DictionariesContract {
    public static final String CONTENT_AUTHORITY = "com.okuklina.pallas";
    public static final Uri BASE_URI = Uri.parse("content://com.okuklina.pallas");

    interface ItemsColumns {
        /**
         * Type: INTEGER PRIMARY KEY AUTOINCREMENT
         */
        String _ID = "_id";
        /**
         * Type: TEXT
         */
        String TITLE = "title";
        /**
         * Type: INTEGER
         */
        String COLOR = "color";
        /**
         * Type: TEXT NOT NULL
         */
        String PHOTO_URL = "photo_url";
        /**
         * Type: INTEGER NOT NULL DEFAULT 0
         */
        String CREATE_DATE = "create_date";
    }

    public static class Items implements DictionariesContract.ItemsColumns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.okuklina.pallas.items";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.okuklina.pallas.items";

        public static final String DEFAULT_SORT = DictionariesContract.ItemsColumns.CREATE_DATE + " DESC";

        /**
         * Matches: /items/
         */
        public static Uri buildDirUri() {
            return DictionariesContract.BASE_URI.buildUpon().appendPath("items").build();
        }

        /**
         * Matches: /items/[_id]/
         */
        public static Uri buildItemUri(long _id) {
            return DictionariesContract.BASE_URI.buildUpon().appendPath("items").appendPath(Long.toString(_id)).build();
        }

        /**
         * Read item ID item detail URI.
         */
        public static long getItemId(Uri itemUri) {
            return Long.parseLong(itemUri.getPathSegments().get(1));
        }
    }

    private DictionariesContract() {
    }
}
