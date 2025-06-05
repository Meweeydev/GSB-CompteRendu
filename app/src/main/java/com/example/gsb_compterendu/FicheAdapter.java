package com.example.gsb_compterendu;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FicheAdapter extends RecyclerView.Adapter<FicheAdapter.ViewHolder> {
    private FragmentActivity activity;
    private List<Fiche> ficheList; // ✅ on déclare bien ici

    public FicheAdapter(FragmentActivity activity, List<Fiche> ficheList) {
        this.activity = activity;
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

        holder.btnEdit.setOnClickListener(v -> {
            Fragment fragment = ModifierFicheFragment.newInstance(fiche.getId());

            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();

            activity.findViewById(R.id.fichesRecyclerView).setVisibility(View.GONE);
            activity.findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);
        });

        holder.btnView.setOnClickListener(v -> {
            Fragment fragment = VisualiserFicheFragment.newInstance(fiche.getId());

            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();

            activity.findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);
        });

    }


    @Override
    public int getItemCount() {
        return ficheList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateVisite, praticienNom, produitNom, statut;
        Button btnEdit, btnView, btnDelete;

        public ViewHolder(View view) {
            super(view);
            dateVisite = view.findViewById(R.id.dateVisite);
            praticienNom = view.findViewById(R.id.praticienNom);
            produitNom = view.findViewById(R.id.produitNom);
            statut = view.findViewById(R.id.statut);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnView = view.findViewById(R.id.btnView);
        }
    }

}
