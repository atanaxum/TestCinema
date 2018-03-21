package com.example.autumn.testcinema;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

