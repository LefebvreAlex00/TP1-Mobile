package com.example.ift1155_projet;

import static com.example.ift1155_projet.MainActivity.taches;
import static com.example.ift1155_projet.R.string.certains_champs_ne_sont_pas_remplis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreationTache extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText titre;
    EditText auteur;
    EditText description;
    private Spinner spinner;
    String[] spinnerItems = {"Basse", "Moyenne", "Haute"};
    Date dateEcheanceChoisi;
    Button date;
    DatePickerDialog datePickerDialog;
    int prio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_creation_tache);
        spinnerItems[0]=getString(R.string.basse);
        spinnerItems[1]=getString(R.string.moyenne);
        spinnerItems[2]=getString(R.string.haute);
        description = findViewById(R.id.creationDescription);
        titre = findViewById(R.id.creationTitre);
        auteur = findViewById(R.id.creationAuteur);
        date = findViewById(R.id.date);
        prio = 0;
        spinner =findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mAnnee = c.get(Calendar.YEAR);
                int mMois = c.get(Calendar.MONTH);
                int mJour = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreationTache.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int annee, int mois, int jour) {
                        final int mHeure = c.get(Calendar.HOUR_OF_DAY);
                        final int mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(CreationTache.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int heure, int minute) {
                                String datetime = minute==0? annee + "/" + (mois + 1) + "/" + jour + " " + heure + ":" + minute+0:annee + "/" + (mois + 1) + "/" + jour + " " + heure + ":" + minute;
                                date.setText(datetime);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                String dateString = annee + "-" + (mois + 1) + "-" + jour + " " + heure + ":" + minute;
                                try {
                                    dateEcheanceChoisi= dateFormat.parse(dateString);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, mHeure, mMinute, true);
                        timePickerDialog.show();
                    }
                }, mAnnee, mMois, mJour);
                datePickerDialog.show();
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        prio=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void ajouter(View v){

        String descriptionText = description.getText().toString();
        String titreText = titre.getText().toString();
        String auteurText = auteur.getText().toString();

        if(descriptionText.equals("")||titreText.equals("")||auteurText.equals("")){
            Toast.makeText(this, certains_champs_ne_sont_pas_remplis,
                    Toast.LENGTH_LONG).show();
        }else{
            taches.add(new Tache(titre.getText().toString(),auteur.getText().toString(),description.getText().toString(),dateEcheanceChoisi,true,prio));
            Intent i = new Intent(CreationTache.this,MainActivity.class);
            startActivity(i);
        }


    }
    public void fermer(View v){
        Intent i = new Intent(CreationTache.this,MainActivity.class);
        startActivity(i);
    }

}