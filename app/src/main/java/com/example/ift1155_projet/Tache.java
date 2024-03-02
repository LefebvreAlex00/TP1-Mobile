package com.example.ift1155_projet;

import java.io.Serializable;
import java.util.Date;

public class Tache implements Serializable {
    public int id;
    public String titre;
    public String description;
    public Date dateRemise;
    public Date dateCreation;
    public String createur;
    private static int increment = 0;

    public boolean etat;
    public int urgence;

    public Tache(String titre,String auteur,String description,Date dateEcheance,boolean etat,int importance){
        this.id=++increment;
        this.titre=titre;
        this.createur=auteur;
        this.description=description;
        this.dateRemise=dateEcheance;
        this.urgence=importance;
        this.etat=etat;
        this.dateCreation = new Date();
    }

    //Je vais utiliser Tache vide comme un randomiser pour peupler l'application au démarrage
    public Tache(){

    }

    public String getNom() {
        return titre;
    }
    public String getDescription() {
        return description;
    }

}
