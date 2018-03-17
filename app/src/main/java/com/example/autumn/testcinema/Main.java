package com.example.autumn.testcinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Main extends AppCompatActivity {
    private static final int CONNECTED_ACTIVITY_ID = 1;                    //ID de l'activité (Quizz), necessaire pour le retour des informations suite au quizz

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar =  findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        // Recuperation du Intent qui à declenché cette activité & recuperation du string saisi par l'utilisateur
        Intent intent = getIntent();
        String message = intent.getStringExtra(Accueil.EXTRA_MESSAGE);

        // test de recuparation
        TextView textView = findViewById(R.id.large_text);
        textView.setText(message);

        //auto generated
        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startSecondActivityIntent = new Intent(Main.this, Quizz.class);
                startActivityForResult(startSecondActivityIntent, CONNECTED_ACTIVITY_ID);
            }
        } );



    }
}
