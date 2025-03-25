package com.example.gsb_compterendu;

public class Fiche {
    private String id, dateVisite, praticienNom, produitNom, statut;

    public Fiche(String id, String dateVisite, String praticienNom, String produitNom, String statut) {
        this.id = id;
        this.dateVisite = dateVisite;
        this.praticienNom = praticienNom;
        this.produitNom = produitNom;
        this.statut = statut;
    }

    public String getId() { return id; }
    public String getDateVisite() { return dateVisite; }
    public String getPraticienNom() { return praticienNom; }
    public String getProduitNom() { return produitNom; }
    public String getStatut() { return statut; }
}
