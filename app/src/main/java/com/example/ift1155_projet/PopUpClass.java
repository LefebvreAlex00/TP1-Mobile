package com.example.ift1155_projet;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

public class PopUpClass {

    //PopupWindow display method
    Tache tache;
    int pos;

    public PopUpClass(Tache tache,int pos){
        this.tache = tache;
        this.pos=pos;
    }

    @SuppressLint("SetTextI18n")
    public void showPopupWindow(final View view) {


        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_card, null);


        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView tvTitre = popupView.findViewById(R.id.titleText);
        TextView tvAuteur = popupView.findViewById(R.id.cardAuteur);
        TextView tvDescription = popupView.findViewById(R.id.cardDescription);
        TextView tvDateCreation = popupView.findViewById(R.id.cardDateCreation);
        TextView tvDateEcheance = popupView.findViewById(R.id.cardDateEcheance);
        TextView tvImportance = popupView.findViewById(R.id.cardImportance);
        TextView tvTempsRestant = popupView.findViewById(R.id.cardTempsRestant);
        TextView tvEtat = popupView.findViewById(R.id.cardEtat);
        final int rotation = ((WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();

            tvAuteur.setText(view.getContext().getString(R.string.auteur)+tache.createur);
            tvDescription.setText(view.getContext().getString(R.string.description)+tache.description);
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            tvDateCreation.setText(view.getContext().getString(R.string.date_de_cr_ation)+dt.format(tache.dateCreation));
            tvDateEcheance.setText(view.getContext().getString(R.string.date_ch_ance)+dt.format(tache.dateRemise));
            Duration diff = Duration.between(tache.dateCreation.toInstant(),tache.dateRemise.toInstant());
            long diffTemps = diff.toHours();
            if(diffTemps>48){
                diffTemps = diff.toDays();
                tvTempsRestant.setText(view.getContext().getString(R.string.jours_restant)+diffTemps);
            }else if(diffTemps==0){
                diffTemps = diff.toMinutes();
                tvTempsRestant.setText(view.getContext().getString(R.string.minutes_restantes)+diffTemps);
            }
            else{

                tvTempsRestant.setText(diffTemps<0? view.getContext().getString(R.string.temps_restant_en_retard): view.getContext().getString(R.string.heures_restantes)+diffTemps);
            }
            tvEtat.setText(tache.etat? view.getContext().getString(R.string.active):view.getContext().getString(R.string.fermer));
            switch (tache.urgence){
                case 0:
                    tvImportance.setText(view.getContext().getString(R.string.priorit_basse));
                    break;
                case 1:
                    tvImportance.setText(view.getContext().getString(R.string.priorit_moyenne));
                    break;
                case 2:
                    tvImportance.setText(view.getContext().getString(R.string.priorit_haute));
                    break;
            }

        tvTitre.setText(tache.titre);

        Button buttonEdit = popupView.findViewById(R.id.cardButtonModifier);
        if(!tache.etat){
            buttonEdit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FCC82F")));
            buttonEdit.setText(view.getContext().getString(R.string.r_ouvrir_la_t_che));
        }


        Button buttonClose = popupView.findViewById(R.id.cardButtonFermer);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!tache.etat){
                    tache.etat=true;
                    popupWindow.dismiss();
                    Toast.makeText(view.getContext(), view.getContext().getString(R.string.la_t_che_est_d_sormais_active), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
                else{
                    Intent intent = new Intent(view.getContext(), ModifierTache.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("POSITION", pos);
                    view.getContext().startActivity(intent);

                }

            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener() {
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

}