package com.example.autumn.testcinema;

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
        if(this.getBDQ().getMax()==0){
            this.addData( 1,3,9 );
            this.addData( 2,1,9 );
            this.addData( 3,2,9 );
            this.addData( 4,2,9 );
            this.addData( 5,2,9 );
        }else{
            Toast.makeText(this,"BD exist",Toast.LENGTH_SHORT).show();
        }
    }


    private void afficherFragment(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.ajouterFragment(new QuizzFragment(1),"Question "+1);
        adapter.ajouterFragment(new QuizzFragment(2),"Question "+2);
        adapter.ajouterFragment(new QuizzFragment(3),"Question "+3);
        adapter.ajouterFragment(new QuizzFragment(4),"Question "+4);
        adapter.ajouterFragment(new QuizzFragment(5),"Question "+5);
        viewPager.setAdapter(adapter);

    }


    public void updateData(int id, int rep) {
        boolean isUpdate = bdq.updateData(String.valueOf( id ), bdq.getReponse( id ), rep);
        if(isUpdate == true)
            Toast.makeText(this,"Data Update",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Data not Updated",Toast.LENGTH_SHORT).show();
    }

    public  void addData(int id, int rep, int usr) {
        boolean isInserted = bdq.insertData(id, rep, usr);
        if(isInserted == true)
            Toast.makeText(this,String.valueOf( this.getBDQ().getMax() ),Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Data not Inserted",Toast.LENGTH_SHORT).show();
    }
}
