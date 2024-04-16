package com.example.ift1155_projet;

import static com.example.ift1155_projet.MainActivity.taches;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
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

public class ModifierTache extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int receivedPosition;
    Tache tacheModifier;
    private Spinner spinner;
    private Spinner spinner2;

    int prio;
    Boolean urgence;
    String[] spinnerItems = {"Basse", "Moyenne", "Haute"};
    String[] spinnerItems2 = {"Active", "Fermer"};

    EditText tvTitre;
    EditText tvAuteur;
    EditText tvDescription;
    Button buttonModifierDate;
    Date dateEcheanceChoisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_modifier_tache);
        spinnerItems2[0]=getString(R.string.active);
        spinnerItems2[1]=getString(R.string.fermer);

        spinnerItems[0]=getString(R.string.basse);
        spinnerItems[1]=getString(R.string.moyenne);
        spinnerItems[2]=getString(R.string.haute);

        Intent intent = getIntent();
        receivedPosition = intent.getIntExtra("POSITION", 0);
        tacheModifier=taches.get(receivedPosition);

         tvTitre = findViewById(R.id.modifierTitre);
         tvAuteur = findViewById(R.id.modifierAuteur);
         tvDescription = findViewById(R.id.modifierDescription);
         buttonModifierDate = findViewById(R.id.modifierdate);





        spinner =findViewById(R.id.modifierSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(tacheModifier.urgence);


        spinner2 =findViewById(R.id.spinnerEtat);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerItems2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
        spinner2.setSelection(tacheModifier.etat? 0:1);

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        buttonModifierDate.setText(dt.format(tacheModifier.dateRemise));
        tvTitre.setText(tacheModifier.titre);
        tvAuteur.setText(tacheModifier.createur);
        tvDescription.setText(tacheModifier.description);


        buttonModifierDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mAnnee = c.get(Calendar.YEAR);
                int mMois = c.get(Calendar.MONTH);
                int mJour = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ModifierTache.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int annee, int mois, int jour) {
                        final int mHeure = c.get(Calendar.HOUR_OF_DAY);
                        final int mMinute = c.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(ModifierTache.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int heure, int minute) {
                                String datetime = minute==0? annee + "/" + (mois + 1) + "/" + jour + " " + heure + ":" + minute+0:annee + "/" + (mois + 1) + "/" + jour + " " + heure + ":" + minute;
                                buttonModifierDate.setText(datetime);
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
    public void modifier(View v){
        String descriptionText = tvDescription.getText().toString();
        String titreText = tvTitre.getText().toString();
        String auteurText = tvAuteur.getText().toString();

        if(descriptionText.equals("")||titreText.equals("")||auteurText.equals("")){
            Toast.makeText(this, getString(R.string.certains_champs_ne_sont_pas_remplis),
                    Toast.LENGTH_LONG).show();
        }else{
            taches.get(receivedPosition).titre=titreText;
            taches.get(receivedPosition).description=descriptionText;
            taches.get(receivedPosition).createur=auteurText;
            if(dateEcheanceChoisi!=null){
                taches.get(receivedPosition).dateRemise=dateEcheanceChoisi;
            }
            taches.get(receivedPosition).urgence=prio;
            taches.get(receivedPosition).etat=urgence;



            Intent i = new Intent(ModifierTache.this,MainActivity.class);
            startActivity(i);
        }

    }


    public void fermer(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
       Adapter x = parent.getAdapter();
       Object y =x.getItem(0);
       String yString=y.toString();
       if(yString.equals("Basse")||yString.equals("Low")){
           prio=position;
       }else{
           urgence= position==0;
       }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}