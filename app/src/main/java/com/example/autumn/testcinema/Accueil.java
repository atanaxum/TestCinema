package com.example.autumn.testcinema;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Accueil extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "Accueil";
    private EditText prenom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Button lancer = findViewById(R.id.buttonsub);                       //Bouton pour lancer l'application
        lancer.setOnClickListener( new View.OnClickListener() {             //Envoye du contenu du editText à l'Activité principale
            @Override
            public void onClick(View view) {
                Accueil.this.startActivity(new Intent( Accueil.this, Main.class ));
            }
        } );

        //Mise en place de l'animation lors du lancement
        LinearLayout l1 =  findViewById(R.id.l1);
        LinearLayout l2 =  findViewById(R.id.l2);

        Animation uptodown = AnimationUtils.loadAnimation(this,R.anim.popup_vers_bas );
        Animation downtoup = AnimationUtils.loadAnimation(this,R.anim.popup_vers_haut );

        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);
    }

}
