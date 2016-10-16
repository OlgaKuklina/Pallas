package com.okuklina.pallas.data;


import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

/**
 * Created by olgakuklina on 2016-10-15.
 */

public class DictionaryLoader extends CursorLoader {

    public static DictionaryLoader newAllDictionariesInstance(Context context) {
        return new DictionaryLoader(context, DictionariesContract.Items.buildDirUri());
    }

    public static DictionaryLoader newInstanceForItemId(Context context, long itemId) {
        return new DictionaryLoader(context, DictionariesContract.Items.buildItemUri(itemId));
    }


    private DictionaryLoader(Context context, Uri uri) {
        super(context, uri, DictionaryLoader.Query.PROJECTION, null, null, DictionariesContract.Items.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                DictionariesContract.Items._ID,
                DictionariesContract.Items.TITLE,
                DictionariesContract.Items.COLOR,
                DictionariesContract.Items.PHOTO_URL,
                DictionariesContract.Items.CREATE_DATE
        };

        int _ID = 0;
        int TITLE = 1;
        int COLOR = 2;
        int PHOTO_URL = 3;
        int CREATE_DATE = 4;
    }
}
