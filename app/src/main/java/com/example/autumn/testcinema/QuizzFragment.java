package com.example.autumn.testcinema;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    ViewPager page;

    public QuizzFragment(int id ) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.quiz_question,container,false);
        page = getActivity().findViewById(R.id.container);

        int resQuest = getResources().getIdentifier("question"+ this.getID(),"string", v.getContext().getPackageName());
        TextView quest = v.findViewById(R.id.question);
        quest.setText(getString( resQuest ));

        final RadioGroup rg = v.findViewById( R.id.rg );
        this.setUSR( rg.indexOfChild( v.findViewById( rg.getCheckedRadioButtonId())) );

        btnValider = v.findViewById(R.id.valider);
        btnValider.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = QuizzFragment.this.getID();
                int usr = QuizzFragment.this.getUSR();
                page.setCurrentItem(id);
                ((Quizz)getActivity()).updateData( id,usr );
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
                    Toast.makeText(getActivity().getBaseContext(),String.valueOf( rep ),Toast.LENGTH_SHORT).show();
                    btnValider.setEnabled( true );

                }
            } );
        }

        return v;
    }

    public int getID(){
        return this.id;
    }
    public int getUSR(){
        return this.usr;
    }
    public void setUSR(int usr){ this.usr = usr +1;}

}
