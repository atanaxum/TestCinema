package com.example.autumn.testcinema;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        //Bouton pour lancer l'application
        Button lancer = findViewById(R.id.buttonsub);
        //Envoye du contenu du editText à l'Activité principale
        lancer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lancer l'activité Main
                Accueil.this.startActivity(new Intent( Accueil.this, Main.class ));
                //Animer la transition
                overridePendingTransition(R.anim.slide_vertical_in, R.anim.slide_vertical_out );
            }
        } );

        //Mise en place de l'animation des element de la page lors du lancement
        LinearLayout l1 =  findViewById(R.id.l1);
        LinearLayout l2 =  findViewById(R.id.l2);

        Animation uptodown = AnimationUtils.loadAnimation(this,R.anim.popup_vers_bas );
        Animation downtoup = AnimationUtils.loadAnimation(this,R.anim.popup_vers_haut );

        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);
    }

}
