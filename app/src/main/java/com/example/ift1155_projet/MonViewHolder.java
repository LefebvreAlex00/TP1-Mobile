package com.example.ift1155_projet;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MonViewHolder extends RecyclerView.ViewHolder {
    TextView nomView;
    TextView descriptionView;
    TextView priorite;

    public MonViewHolder(@NonNull View itemView, ClicRecycleListener tache) {
        super(itemView);
        this.nomView = itemView.findViewById(R.id.nomTache);
        this.descriptionView = itemView.findViewById(R.id.dateTache);
        this.priorite = itemView.findViewById(R.id.couleurPriorite);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tache != null) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION);
                    tache.clic(position, v);
                }
            }
        });
    }
}
