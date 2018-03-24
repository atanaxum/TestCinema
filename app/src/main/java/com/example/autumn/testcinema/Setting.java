package com.example.autumn.testcinema;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

        ListView liste = findViewById(R.id.parametres);
        liste.setFocusable(true);
        liste.setDivider(null);

        String[] valeurs = getResources().getStringArray(R.array.settings);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,valeurs);
        liste.setAdapter(adapter);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int position, long l) {
                switch(position){
                    case 0:
                        Setting.this.createPopUp();
                        break;
                    case 1:
                        Toast.makeText(Setting.this, "Par Peter J. et Nikita Y.", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    public void createPopUp(){
        new AlertDialog.Builder(this)
                .setView(R.layout.row)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText editPOP = (EditText) ((AlertDialog) dialog).findViewById(R.id.editNamePOP);
                        String s = String.valueOf(editPOP.getText());
                        bdq.updateName(1,s);
                        String nom = bdq.getNom(1);

                        //un simple Hello pour voir si on passe par ici
                        Toast toast = Toast.makeText(Setting.this, "Votre nom est "+nom, Toast.LENGTH_SHORT);
                        toast.show();

                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
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
}

