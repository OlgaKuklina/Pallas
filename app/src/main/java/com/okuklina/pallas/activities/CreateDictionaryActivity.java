package com.okuklina.pallas.activities;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.colorpicker.ColorPickerDialog;
import com.okuklina.pallas.data.DictionariesContract;
import com.okuklina.pallas.R;

import static android.provider.CalendarContract.CalendarCache.URI;

/**
 * Created by olgakuklina on 2016-10-13.
 */

public class CreateDictionaryActivity extends AppCompatActivity {
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

        colorPickerDialog = new ColorPickerDialog();
        int[] colors = getResources().getIntArray(R.array.color_picker_array);

        colorPickerDialog.initialize(
                R.string.title, colors, colors[3], 3, 2);

        final EditText dictTitle = (EditText) findViewById(R.id.dictionary_title);
        Button colorPickerButton = (Button) findViewById(R.id.color_button);
        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPickerDialog.show(getFragmentManager(), "colorpicker");
            }
        });
        Button ImagePickerButton = (Button) findViewById(R.id.image_button);
        ImagePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                chooseIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(chooseIntent, REQUEST_CODE);
            }
        });

        Button okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(DictionariesContract.Items.TITLE, dictTitle.getFreezesText());
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
           }
        }
    }
}
