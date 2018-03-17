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

public class Accueil extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "Accueil";
    private Button lancer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);


        lancer = findViewById(R.id.buttonsub);                              //Bouton pour lancer l'application
        lancer.setEnabled( false );                                         //Desactivation du bouton (tant que rien n'a été saisis)
        lancer.setOnClickListener( new View.OnClickListener() {             //Envoye du contenu du editText à l'Activité principale
            @Override
            public void onClick(View view) {
                Accueil.this.sendMessage( view );
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


    /**
     * Envoye du contenu du editText à l'Activité "Main"
     * @param view la vue de l'Activité
     */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, Main.class);
        EditText prenom = findViewById(R.id.editText);                      //Champs de saisi où l'utilisateur saisiras son prenom
        prenom.addTextChangedListener(new TextWatcher() {                   //Listener activant le bouton "lancer" lorsque quelquechose fut rentré dans le champs de saisi
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    lancer.setEnabled(false);
                } else {
                    lancer.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        //envoye du message
        String message = prenom.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
