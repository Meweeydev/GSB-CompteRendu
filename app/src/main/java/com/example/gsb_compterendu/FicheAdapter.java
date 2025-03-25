package com.example.gsb_compterendu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FicheAdapter extends RecyclerView.Adapter<FicheAdapter.ViewHolder> {
    private List<Fiche> ficheList;

    public FicheAdapter(List<Fiche> ficheList) {
        this.ficheList = ficheList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fiche, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fiche fiche = ficheList.get(position);
        holder.dateVisite.setText(fiche.getDateVisite());
        holder.praticienNom.setText(fiche.getPraticienNom());
        holder.produitNom.setText(fiche.getProduitNom());
        holder.statut.setText(fiche.getStatut());
    }

    @Override
    public int getItemCount() {
        return ficheList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateVisite, praticienNom, produitNom, statut;

        public ViewHolder(View view) {
            super(view);
            dateVisite = view.findViewById(R.id.dateVisite);
            praticienNom = view.findViewById(R.id.praticienNom);
            produitNom = view.findViewById(R.id.produitNom);
            statut = view.findViewById(R.id.statut);
        }
    }
}
