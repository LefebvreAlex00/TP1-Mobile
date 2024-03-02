package com.example.ift1155_projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MesTachesAdapteur extends RecyclerView.Adapter<MonViewHolder> {
    Context context;
    List<Tache> taches;
    private final ClicRecycleListener tache;
    public MesTachesAdapteur(Context context, List<Tache> taches, ClicRecycleListener tache) {
        this.context = context;
        this.taches = taches;
        this.tache = tache;
    }

    @NonNull
    @Override
    public MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MonViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false),tache);
    }
    public void setItems(List<Tache> taches) {
        this.taches = taches;
    }

    @Override
    public void onBindViewHolder(@NonNull MonViewHolder holder, int position) {
        Tache maTache = taches.get(position);
        holder.nomView.setText(taches.get(position).getNom());
        String description = taches.get(position).getDescription();
        if(description.length()>24){
            holder.descriptionView.setText(taches.get(position).getDescription().substring(0,24)+"...");
        }
        else{

            holder.descriptionView.setText(taches.get(position).getDescription());
        }

        switch (maTache.urgence){
            case 0:
                holder.priorite.setBackgroundResource(R.color.lowPrio);
                break;
            case 1:
                holder.priorite.setBackgroundResource(R.color.midPrio);
                break;
            case 2:
                holder.priorite.setBackgroundResource(R.color.highPrio);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return taches.size();
    }
}
