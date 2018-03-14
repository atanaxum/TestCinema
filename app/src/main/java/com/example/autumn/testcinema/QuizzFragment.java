package com.example.autumn.testcinema;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
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
    private final int id;
    private Button btnValider;

    ViewPager page;
    BaseDataQuestion bdq;

    public QuizzFragment(int id ) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.quiz_question,container,false);
        bdq = new BaseDataQuestion( v.getContext() );

        int resQuest =getResources().getIdentifier("question"+ this.getNumero(),"string", v.getContext().getPackageName());

        TextView quest = v.findViewById(R.id.question);
        quest.setText(getString( resQuest ));

        RadioGroup rg = v.findViewById( R.id.rg );

        for(int i = 1; i<=3; i++){
            int resText =getResources().getIdentifier("question"+ this.getNumero()+"_rep"+i,"string", v.getContext().getPackageName());
            int res =getResources().getIdentifier("reponse"+i,"id", v.getContext().getPackageName());
            RadioButton btnRep = v.findViewById( res );
            btnRep.setText( getString( resText ) );

        }

        page = getActivity().findViewById(R.id.container);

        int value = rg.indexOfChild( v.findViewById( rg.getCheckedRadioButtonId()));

        btnValider = v.findViewById(R.id.valider);
        btnValider.setOnClickListener(new QuestionListener( this.getNumero(), value ));


        return v;
    }

    public int getNumero(){
        return this.id;
    }


    public class QuestionListener implements View.OnClickListener {
        private int id;
        private int usr;

        public QuestionListener(int id, int usr){
            this.id = id;
            this.usr = usr;
        }

        public void onClick(View v) {
            page.setCurrentItem(this.id+1);
            //bdq.insertResponse( this.id, this.usr);
            //String t = String.valueOf( bdq.getReponseUtilisateur( this.id ) );
            //Toast.makeText(v.getContext(), t , Toast.LENGTH_LONG).show();
        }

    }
}
