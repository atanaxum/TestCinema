package com.example.autumn.testcinema;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

public class Quizz extends AppCompatActivity {
    private static final String TAG = "Quizz";
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private BaseDataQuestion bdq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        //Log.d(TAG, "onCreate: Lancement");
        //COMMENT

        this.initialiserBD();

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);
        afficherFragment(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initialiserBD(){
        bdq = new BaseDataQuestion(this);
        bdq.open();
        bdq.insertQuestion(1,2 );
        bdq.insertQuestion( 2,3 );
        bdq.close();
    }

    private void afficherFragment(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.ajouterFragment(new QuizzFragment(1),"Question 1");
        adapter.ajouterFragment(new QuizzFragment(2),"Question 2");
        viewPager.setAdapter(adapter);

    }









}
