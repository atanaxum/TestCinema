package com.example.autumn.testcinema;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
        setRetainInstance( true );                                      //Necessaire pour faire fonctionner l'activité en landscape
        View v =inflater.inflate(R.layout.quiz_question,container,false);
        page = getActivity().findViewById(R.id.container);
        this.setUSR( 8 );                                               //Une reponse utilisateur fixée a 9 (car+1 dans le setUSR()) correspond à "l'utilisateur n'a pas repondu

        int resQuest = getResources().getIdentifier(
                "question"+ this.getID(),
                "string",
                v.getContext().getPackageName());                       //Recuperation de la question dans les Ressources
        TextView quest = v.findViewById(R.id.question);                 //Création du champ Text contenant la question
        quest.setText(getString( resQuest ));

        //RadioGroupe contenant les reponses possibles
        final RadioGroup rg = v.findViewById( R.id.rg );
        //Creation du son d'un boutton
        final MediaPlayer sound_button = MediaPlayer.create(v.getContext(), R.raw.button );

        //this.setUSR( rg.indexOfChild( v.findViewById( rg.getCheckedRadioButtonId())) );

        btnValider = v.findViewById(R.id.valider);
        btnValider.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"QUESTION REPONDUE = "+QuizzFragment.this.getID());
                sound_button.start();
                int id = QuizzFragment.this.getID();
                int usr = QuizzFragment.this.getUSR();
                ((Quizz)getActivity()).updateData( id,usr );
                if(QuizzFragment.this.getID() == 10){
                    Log.d(TAG,"DERNIERE QUESTION repondue");
                    if( ((Quizz)getActivity()).getBDQ().getNbReponse() != 10 ){
                        Log.d(TAG,"NB QUESTIONS REPONDUES != NB REPONSES DONNEES");
                        Toast.makeText(QuizzFragment.this.getContext(),"Vous n'avez pas repondu à toutes les questions",Toast.LENGTH_LONG).show();
                    }else
                        ((Quizz)getActivity()).sendResultat();          //Resultat du test envoyé à la page principale
                }else
                    page.setCurrentItem(id);                        //On passe à la question suivante

            }
        } );
        btnValider.setEnabled( false );

        for(int i = 1; i<=3; i++){
            int resText =getResources().getIdentifier("question"+ this.getID()+"_rep"+i,"string", v.getContext().getPackageName());
            int res =getResources().getIdentifier("reponse"+i,"id", v.getContext().getPackageName());
            RadioButton btnRep = v.findViewById( res );
            btnRep.setText( getString( resText ) );
            btnRep.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = QuizzFragment.this.getID();
                    QuizzFragment.this.setUSR( rg.indexOfChild( view.findViewById( rg.getCheckedRadioButtonId())) );
                    int usr = QuizzFragment.this.getUSR();
                    int rep = ((Quizz)getActivity()).getBDQ().getReponse( id );
                    ((Quizz)getActivity()).getBDQ().updateData( String.valueOf( id ), rep, usr );
                    btnValider.setEnabled( true );
                }
            } );
        }
        return v;
    }

    /**
     * Renvoye l'id de la question actuelle
     * @return l'ID
     */
    public int getID(){
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
