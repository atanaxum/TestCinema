package com.example.autumn.testcinema;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Quizz extends AppCompatActivity {
    private static final String TAG = "Quizz";
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private BaseData bdq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quizz);
        Log.d( TAG,"Allumé" );


        bdq = new BaseData( this );
        this.inintBDQ();
        Log.d( TAG,"BD Crée" );

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.container);
        afficherFragment(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    public BaseData getBDQ(){
        return bdq;
    }

    public void inintBDQ(){
        if(this.getBDQ().getMax()==0){                                  //SI la BD n'existe pas: la construire
            this.addData( 1,3,9 );
            this.addData( 2,2,9 );
            this.addData( 3,2,9 );
            this.addData( 4,1,9 );
            this.addData( 5,3,9 );
            this.addData( 6,2,9 );
            this.addData( 7,1,9 );
            this.addData( 8,3,9 );
            this.addData( 9,1,9 );
            this.addData( 10,2,9 );
        }else{                                                         //SINON on RESET la BD
            for(int i=1; i<=10;i++)
                this.updateData( i,9 );
        }
    }


    private void afficherFragment(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        for(int i = 1; i<=10; i++)
            adapter.ajouterFragment(new QuizzFragment(i),"Question "+i);
        viewPager.setAdapter(adapter);

    }


    public void updateData(int id, int rep) {
        boolean isUpdate = bdq.updateData(String.valueOf( id ), bdq.getReponse( id ), rep);
        if(isUpdate == true)
            Log.d( TAG,"Data Update" );
        else
            Log.d( TAG,"Data not Updated" );
    }

    public  void addData(int id, int rep, int usr) {
        boolean isInserted = bdq.insertData(id, rep, usr);
        if(isInserted == true)
            Log.d( TAG,"Data Insert" );
        else
            Log.d( TAG,"Data not Inserted" );
    }


    public void sendResultat (){
        Intent returnIntent = new Intent();
        String result = "";
        int nbBonneRep = 0;
        for(int i=1; i<=10;i++){
            if(this.getBDQ().getReponse( i ) != this.getBDQ().getReponseUtilisateur( i )){
                int resText =getResources().getIdentifier("question"+ i +"_rep"+this.getBDQ().getReponse( i ),"string", this.getPackageName());
                result+="Question "+i+": "+getString( resText )+"\n";
            }else{
                nbBonneRep++;
            }
        }
        if(nbBonneRep==10)
            result = "Vous avez tout juste!";
        returnIntent.putExtra("result",result);
        setResult( Activity.RESULT_OK,returnIntent);
        finish();
    }
}
