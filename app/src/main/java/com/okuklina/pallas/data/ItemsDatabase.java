package com.okuklina.pallas.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.okuklina.pallas.data.DictionariesContract.ItemsColumns;

public class ItemsDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pallas.db";
    private static final int DATABASE_VERSION = 2;

    public ItemsDatabase(Context context) {
        super(context, ItemsDatabase.DATABASE_NAME, null, ItemsDatabase.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ItemsProvider.Tables.ITEMS + " ("
                + ItemsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ItemsColumns.TITLE + " TEXT NOT NULL,"
                + ItemsColumns.COLOR + " INTEGER,"
                + ItemsColumns.PHOTO_URL + " TEXT NOT NULL,"
                + ItemsColumns.CREATE_DATE + " INTEGER NOT NULL DEFAULT 0"
                + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemsProvider.Tables.ITEMS);
        this.onCreate(db);
    }
}
