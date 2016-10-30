package com.okuklina.pallas.activities;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.okuklina.pallas.data.DictionariesContract;
import com.okuklina.pallas.R;

import java.io.File;

import static android.provider.CalendarContract.CalendarCache.URI;

/**
 * Created by olgakuklina on 2016-10-13.
 */

public class CreateDictionaryActivity extends AppCompatActivity {
    private static final String TAG = CreateDictionaryActivity.class.getSimpleName();
    private ColorPickerDialog colorPickerDialog;
    private static final int REQUEST_CODE = 2;
    private Uri uri;
    private static final Uri URI = Uri.parse("content://com.okuklina.pallas/items");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dictionary);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setTitle("");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getActionBar();
        final EditText dictTitle = (EditText) findViewById(R.id.dictionary_title);

        colorPickerDialog = new ColorPickerDialog();
        int[] colors = getResources().getIntArray(R.array.color_picker_array);

        colorPickerDialog.initialize(
                R.string.title, colors, colors[3], 3, 2);


        final ImageButton colorPickerButton = (ImageButton) findViewById(R.id.color_button);
        colorPickerButton.getBackground().setTint(getResources().getColor(R.color.colorPrimary));
        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPickerDialog.show(getFragmentManager(), "colorpicker");

            }
        });

        colorPickerDialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                colorPickerButton.getBackground().setTint(color);
            }
        });

        ImageButton ImagePickerButton = (ImageButton) findViewById(R.id.image_button);
        ImagePickerButton.getBackground().setTint(getResources().getColor(R.color.colorPrimary));
        ImagePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                chooseIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(chooseIntent, REQUEST_CODE);
            }
        });


        ImageButton UriPickerButton = (ImageButton) findViewById(R.id.content_type_button);
        UriPickerButton.getBackground().setTint(getResources().getColor(R.color.colorPrimary));
        UriPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String q = dictTitle.getText().toString();
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH );
                intent.putExtra(SearchManager.QUERY, q);
                startActivity(intent);

            }
        });

        Button okButton = (Button) findViewById(R.id.create_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(DictionariesContract.Items.TITLE, dictTitle.getText().toString());
                Log.v(CreateDictionaryActivity.TAG, "setOnClickListener " + dictTitle.getText().toString());

                values.put(DictionariesContract.Items.COLOR, colorPickerDialog.getSelectedColor());
                if(uri!=null) {
                    values.put(DictionariesContract.Items.PHOTO_URL, uri.toString());
                }

                values.put(DictionariesContract.Items.CREATE_DATE, System.currentTimeMillis());
                getContentResolver().insert(URI, values);
                Intent upIntent = NavUtils.getParentActivityIntent(CreateDictionaryActivity.this);
                NavUtils.navigateUpTo(CreateDictionaryActivity.this, upIntent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
           if(resultCode == RESULT_OK) {
                uri = data.getData();
              // getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
           }
        }
    }
}
