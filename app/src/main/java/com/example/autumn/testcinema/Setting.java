package com.example.autumn.testcinema;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Peter on 24/03/2018.
 */

public class Setting extends AppCompatActivity {
    private static final String TAG = "Nom";
    private EditText edit;
    private BaseData bdq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_setting );

        bdq = new BaseData(this);
        this.inintBDQ();
        Log.d( TAG,"BD Crée" );

        final TextView t = findViewById(R.id.textView);

        edit = findViewById(R.id.editName);
        Button b = findViewById(R.id.valider);
        b.setText("Valider");
        b.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(edit.getText());
                bdq.updateName(1,s);
                String nom = bdq.getNom(1);
            }
        });
        //edit.setHint(bdq.getNom(1));

        ListView liste = findViewById(R.id.parametres);
        liste.setFocusable(true);
        liste.setDivider(null);

        String[] valeursOther = getResources().getStringArray(R.array.settingsAbout); // faut modif la ligne par l
        ArrayAdapter<String> adapterOther = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,valeursOther);
        liste.setAdapter(adapterOther);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position){
                    case 0:
                        Toast.makeText(Setting.this, "Par Peter J. et Nikita Y.", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        break;
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Afficher le menu personnalisé
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }
    
    public BaseData getBDQ(){
        return bdq;
    }

    public void inintBDQ(){
        if(this.getBDQ().getNB()==0){                                  //SI la BD n'existe pas: la construire
            bdq.insertName(1,"Tommy Wiseau");
        }
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        finish();
    }
}

