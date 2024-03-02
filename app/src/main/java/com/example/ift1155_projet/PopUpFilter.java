package com.example.ift1155_projet;

import static com.example.ift1155_projet.MainActivity.filtre1;
import static com.example.ift1155_projet.MainActivity.filtre2;
import static com.example.ift1155_projet.MainActivity.ordre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class PopUpFilter implements AdapterView.OnItemSelectedListener {

    //PopupWindow display method
    Tache tache;

    int positionOdre,positionEtat,positionImportance;

    private Spinner spinner;
    private Spinner spinner2;
    private Spinner spinner3;
    String[] spinnerOrdre = {"Creation", "Prioriter"};
    String[] spinnerFiltreEtat = {"Tous","Active", "Fermer"};
    String[] spinnerFiltreImportance = {"Tous","Basse", "Moyenne", "Haute"};
    public PopUpFilter(String ordre,String filter1,String filter2){

    }

    @SuppressLint("SetTextI18n")
    public void showPopupWindow(final View view) {
        spinnerOrdre[0] = view.getContext().getString(R.string.creation);
        spinnerOrdre[1] = view.getContext().getString(R.string.prioriter);
        spinnerFiltreEtat[0]=view.getContext().getString(R.string.tous);
        spinnerFiltreEtat[1]=view.getContext().getString(R.string.active);
        spinnerFiltreEtat[2]=view.getContext().getString(R.string.fermer);
        spinnerFiltreImportance[0]=view.getContext().getString(R.string.tous);
        spinnerFiltreImportance[1]=view.getContext().getString(R.string.basse);
        spinnerFiltreImportance[2]=view.getContext().getString(R.string.moyenne);
        spinnerFiltreImportance[3]=view.getContext().getString(R.string.haute);


        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_filter, null);


        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        spinner = popupView.findViewById(R.id.spinnerOrdre);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, spinnerOrdre);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner2 = popupView.findViewById(R.id.spinnerEtatFiltre);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, spinnerFiltreEtat);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        spinner3 = popupView.findViewById(R.id.spinnerImportanceFiltre);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, spinnerFiltreImportance);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(this);

        Button buttonModifier = popupView.findViewById(R.id.cardButtonFilter);
        Button buttonFermer = popupView.findViewById(R.id.cardButtonFermerFilter);
        buttonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordre = positionOdre==0? view.getContext().getString(R.string.creation):view.getContext().getString(R.string.prioriter);
                switch (positionEtat){
                    case 0:
                        filtre1=view.getContext().getString(R.string.tous);
                        break;
                    case 1:
                        filtre1=view.getContext().getString(R.string.active);
                        break;
                    case 2:
                        filtre1=view.getContext().getString(R.string.fermer);
                        break;
                }
                switch (positionImportance){
                    case 0:
                        filtre2=view.getContext().getString(R.string.tous);
                        break;
                    case 1:
                        filtre2=view.getContext().getString(R.string.basse);
                        break;
                    case 2:
                        filtre2=view.getContext().getString(R.string.moyenne);
                        break;
                    case 3:
                        filtre2=view.getContext().getString(R.string.haute);
                        break;
                }

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);

            }
        });

        buttonFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // popupWindow.dismiss();
                return true;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Adapter x = parent.getAdapter();
        Object y =x.getItem(1);
        String yString = y.toString();
        switch (yString){
            case "Prioriter":
            case "Priority":
                positionOdre=position;
                break;
            case "Active":
                positionEtat=position;
                break;
            case "Basse":
            case "Low":
                positionImportance=position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}