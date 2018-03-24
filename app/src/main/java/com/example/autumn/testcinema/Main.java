package com.example.autumn.testcinema;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    private BaseData bdq;
    TextView large;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar =  findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        bdq = new BaseData(this);
        this.inintBDQ();

        large = findViewById(R.id.large_text);
        large.setText("Bonjour "+this.getBDQ().getNom( 1 )+" (Vous pouvez changer le nom dans les parametres)\n"+getString( R.string.welcome ));

        //auto generated
        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lancer le Test et attendre le resultat
                Intent startSecondActivityIntent = new Intent(Main.this, Quizz.class);
                startActivityForResult(startSecondActivityIntent, 1);
                //Animer la transition
                overridePendingTransition( R.anim.slide_horizontal_in, R.anim.slide_horizontal_out );
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                //Si l'utilisateur s'est trompé, alors....
                if(!result.equals( "Vous avez tout juste!" )){
                    //Les divers films sont separés par les ";"
                    String[] separated = result.split(";");
                    String affichage = "Vous vous etes trompés:\n";
                    for (int i=0; i<separated.length;i++){
                        //Faire un saut de ligne apres chaque film
                        affichage+=separated[i]+"\n";
                    }
                    //Afficher la liste d'erreurs
                    large.setText( affichage );
                }else
                    large.setText( result );
            }
            if (resultCode == Activity.RESULT_CANCELED)
                large.setText("Vous avez quité le test sans le terminer");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Afficher le menu personnalisé
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            //Si l'utilisateur clique sur le btn "Settings"
            case R.id.action_settings:
                Intent intent = new Intent(this, Setting.class);
                this.startActivity(intent);
                break;
                //Si l'utilisateur clique sur le btn "Share"
            case R.id.action_share:
                Toast.makeText(this, "Fonction en developpement\n (Veillez attendre l'API 26)",Toast.LENGTH_LONG).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
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
