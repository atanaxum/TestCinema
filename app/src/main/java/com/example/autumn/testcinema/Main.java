package com.example.autumn.testcinema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Main extends AppCompatActivity {
    TextView large;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar =  findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        large = findViewById(R.id.large_text);
        large.setText(R.string.welcome);

        //auto generated
        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startSecondActivityIntent = new Intent(Main.this, Quizz.class);
                startActivityForResult(startSecondActivityIntent, 1);
                overridePendingTransition( R.anim.slide_horizontal_in, R.anim.slide_horizontal_out );
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                //large.setText("Vous avez terminé le test!");
                String result = data.getStringExtra("result");
                if(!result.equals( "Vous avez tout juste!" )){
                    String[] separated = result.split(";");

                    String affichage = "Vous vous etes trompés:\n";
                    for (int i=0; i<separated.length;i++)
                        affichage+=separated[i]+"\n";
                    large.setText( affichage );
                }else
                    large.setText( result );
            }
            if (resultCode == Activity.RESULT_CANCELED)
                large.setText("ERROR");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }
}
