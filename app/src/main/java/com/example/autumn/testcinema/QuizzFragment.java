package com.example.autumn.testcinema;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Autumn on 14/03/2018.
 */

@SuppressLint("ValidFragment")
public class QuizzFragment extends Fragment{
    private static final String TAG = "QuizzFragment";
    private int id;
    private int usr;
    private Button btnValider;
    private ViewPager page;

    public QuizzFragment(int id) {
        super();
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //Necessaire pour faire fonctionner l'activité en landscape
        setRetainInstance( true );
        View v =inflater.inflate(R.layout.quiz_question,container,false);

        page = getActivity().findViewById(R.id.container);
        //Une reponse utilisateur fixée a 9 (car+1 dans le setUSR()) correspond à "l'utilisateur n'a pas repondu
        this.setUSR( 8 );

        //Recuperation de la question dans les Ressources
        int resQuest = getResources().getIdentifier("question"+ this.getNumero(),"string",v.getContext().getPackageName());
        //Création du champ Text contenant la question
        TextView quest = v.findViewById(R.id.question);
        quest.setText(getString( resQuest ));

        //Ajout d'une animation pour la premier question
        if(this.getNumero()==1){
            Animation uptodown = AnimationUtils.loadAnimation(((Quizz)getActivity()),R.anim.popup_vers_bas );
            quest.setAnimation(uptodown);
        }

        //RadioGroupe contenant les reponses possibles
        final RadioGroup rg = v.findViewById( R.id.rg );
        //Creation du son d'un boutton
        final MediaPlayer sound_button = MediaPlayer.create(v.getContext(), R.raw.button );

        //Création du bouton qui confirme la reponse donnée par l'utilisateur
        btnValider = v.findViewById(R.id.valider);
        btnValider.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"QUESTION REPONDUE = "+QuizzFragment.this.getNumero());
                //Joue le son du bouton
                sound_button.start();

                //Met à jour la BD
                int id = QuizzFragment.this.getNumero();
                int usr = QuizzFragment.this.getUSR();
                ((Quizz)getActivity()).updateData( id,usr );

                //Si c'est la dernier question
                if(QuizzFragment.this.getNumero() == 10){
                    btnValider.setText( "Terminer" );
                    Log.d(TAG,"DERNIERE QUESTION repondue");

                    //Si l'utilisateur à passé des questions
                    if( ((Quizz)getActivity()).getBDQ().getNbReponse() != 10 ){
                        Log.d(TAG,"NB QUESTIONS REPONDUES != NB REPONSES DONNEES");
                        Toast.makeText(QuizzFragment.this.getContext(),"Vous n'avez pas repondu à toutes les questions",Toast.LENGTH_LONG).show();
                    }else {
                        //Resultat du test envoyé à la page principale
                        ((Quizz) getActivity()).sendResultat();
                    }
                }else{
                    //Si c'est pas la derniere question on passe à la question suivante
                    page.setCurrentItem(id);
                }

            }
        } );
        btnValider.setEnabled( false );

        //Gestion des RadioButton
        for(int i = 1; i<=3; i++){
            //Recuperation du text
            int resText =getResources().getIdentifier("question"+ this.getNumero()+"_rep"+i,"string", v.getContext().getPackageName());
            //Recuperation de l'id
            int res =getResources().getIdentifier("reponse"+i,"id", v.getContext().getPackageName());
            RadioButton btnRep = v.findViewById( res );
            btnRep.setText( getString( resText ) );
            //Selectionner une reponse
            btnRep.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = QuizzFragment.this.getNumero();
                    //On passe la reponse de l'utilisateur en parametre
                    QuizzFragment.this.setUSR( rg.indexOfChild( view.findViewById( rg.getCheckedRadioButtonId())) );
                    int usr = QuizzFragment.this.getUSR();
                    int rep = ((Quizz)getActivity()).getBDQ().getReponse( id );
                    //On met a jour la BD
                    ((Quizz)getActivity()).getBDQ().updateData( String.valueOf( id ), rep, usr );
                    //On deverouille le bouton "suivant"
                    btnValider.setEnabled( true );
                }
            } );

            //Esthetique des Radio Button
            if(QuizzFragment.this.getNumero()<=5){
                btnRep.setButtonDrawable( getResources().getDrawable( R.drawable.radio_button_sombre ) );
                btnRep.setTextColor( getResources().getColor( R.color.colorAutumn10 ) );
            }else{
                btnRep.setButtonDrawable( getResources().getDrawable( R.drawable.radio_button_clair ) );
                btnRep.setTextColor( getResources().getColor( R.color.colorAutumn0 ) );
            }
        }


        //Esthetique des Pages (effet degradé)
        int resColor = getResources().getIdentifier("colorAutumn"+ this.getNumero(),"color", v.getContext().getPackageName());
        v.setBackgroundColor( getResources().getColor( resColor ) );
        int resBtn = getResources().getIdentifier("button_style_"+ this.getNumero(),"drawable", v.getContext().getPackageName());
        btnValider.setBackground( getResources().getDrawable( resBtn ) );
        if(this.getNumero()<=5){
            btnValider.setTextColor( getResources().getColor( R.color.colorAutumn10 ) );
            quest.setTextColor( getResources().getColor( R.color.colorAutumn10 ) );
        }else{
            btnValider.setTextColor( getResources().getColor( R.color.colorAutumn0 ) );
            quest.setTextColor( getResources().getColor( R.color.colorAutumn0 ) );
        }

        return v;
    }

    /**
     * Renvoye l'id de la question actuelle
     * @return l'ID
     */
    public int getNumero(){
        return this.id;
    }

    /**
     * Renvoye la reponse donnée par l'utilisateur
     * @return le numero correspondant à la reponse de l'utilisateur
     */
    public int getUSR(){
        return this.usr;
    }

    /**
     * Enregistre la reponse donnée par l'utilisateur
     * @param usr le numero correspondant à la reponse de l'utilisateur
     */
    public void setUSR(int usr){
        //+1 necessaire car les numeros des radioButton = 0,1,2 alors que numero des Reponses dans la BD = 1,2,3
        this.usr = usr +1;
    }



}
