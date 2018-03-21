package com.example.autumn.testcinema;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class Setting extends AppCompatActivity  {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate( savedInstanceState, persistentState );
        setContentView( R.layout.activity_setting );
        
        ListView liste = (ListView) findViewById(R.id.parametres);
        liste.setFocusable(true);
        liste.setDivider(null);
        
        String[] valeursOther = getResources().getStringArray(R.array.settingsAbout);
        ArrayAdapter<String> adapterOther = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,valeursOther);
        liste.setAdapter(adapterOther);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position){
                    case 0:
                        //Intent newActivity = new Intent(view.getContext(), AboutActivity.class);
                        //startActivity(newActivity);
                        break;
                }
            }
        });
    }
}

