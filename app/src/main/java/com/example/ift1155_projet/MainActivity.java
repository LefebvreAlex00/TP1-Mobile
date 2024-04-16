package com.example.ift1155_projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements ClicRecycleListener{

    RecyclerView recyclerView;
    static List<Tache> taches = new ArrayList<Tache>();
    static List<Tache> tacheFiltrer = new ArrayList<Tache>();
    static List<Tache> fullTacheFiltrer = new ArrayList<Tache>();
    static String ordre;
    static String filtre1;
    static String filtre2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        if(ordre==null){
            ordre=getString(R.string.creation);
            filtre1=getString(R.string.tous);
            filtre2=getString(R.string.tous);
        }


        if(taches.isEmpty()){
            for(int i=0;i<20;i++){

                Date d = new Date();
                Random rnd = new Random();
                int total =rnd.nextInt(10)+125;
                boolean trueFalse = total % 4 != 0;
                int importance = total%3;
                d.setYear(total);
                taches.add(new Tache("Titre"+i,"Auteur","description",d,trueFalse,importance));
            }
        }
        tacheFiltrer = new ArrayList<Tache>();
        fullTacheFiltrer= new ArrayList<Tache>();
        filtrer();

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MesTachesAdapteur(getApplicationContext(),fullTacheFiltrer, this));
    }

    private void filtrer() {
        tacheFiltrer.clear();
        HashSet<Integer> addedIds = new HashSet<>();
        if (ordre.equals(getString(R.string.creation))) {
            Collections.sort(taches, new Comparator<Tache>() {
                @Override
                public int compare(Tache t1, Tache t2) {
                    return t1.dateRemise.compareTo(t2.dateRemise);
                }
            });
        } else {
            Collections.sort(taches, new Comparator<Tache>() {
                @Override
                public int compare(Tache t1, Tache t2) {
                    return Integer.compare(t2.urgence, t1.urgence);
                }
            });
        }
        for (Tache tache : taches) {
            if (filtre1.equals("Tous")||filtre1.equals("All")) {

                    tacheFiltrer.add(tache);

            } else if (filtre1.equals(getString(R.string.active)) && tache.etat) {

                    tacheFiltrer.add(tache);

            } else if (filtre1.equals("Fermer") && !tache.etat|| filtre1.equals("Closed")&& !tache.etat) {

                    tacheFiltrer.add(tache);

            }
        }



        for (Tache tache : tacheFiltrer) {
            if (filtre2.equals("Tous")||filtre2.equals("All")) {
                if (addedIds.add(tache.id)) {
                    fullTacheFiltrer.add(tache);
                }
            } else if (filtre2.equals("Basse") && tache.urgence == 0 || filtre2.equals("Low") && tache.urgence == 0) {
                if (addedIds.add(tache.id)) {
                    fullTacheFiltrer.add(tache);
                }
            } else if (filtre2.equals("Moyenne") && tache.urgence == 1||filtre2.equals("Average") && tache.urgence == 1 ) {
                if (addedIds.add(tache.id)) {
                    fullTacheFiltrer.add(tache);
                }
            } else if (filtre2.equals("Haute") && tache.urgence == 2|| filtre2.equals("High") && tache.urgence == 2) {
                if (addedIds.add(tache.id)) {
                    fullTacheFiltrer.add(tache);
                }
            }
        }
    }


    public void filter(View v){
        PopUpFilter popUpFilter = new PopUpFilter(ordre,filtre1,filtre2);
        popUpFilter.showPopupWindow(v);

   }
   public void creation(View v){
       Intent i = new Intent(MainActivity.this,CreationTache.class);
       startActivity(i);
   }
    @Override
    public void clic(int pos,View v) {
        PopUpClass popUpClass = new PopUpClass(fullTacheFiltrer.get(pos),pos);
        popUpClass.showPopupWindow(v);

    }
}