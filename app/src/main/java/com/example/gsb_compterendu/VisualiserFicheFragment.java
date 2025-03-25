package com.example.gsb_compterendu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class VisualiserFicheFragment extends Fragment {

    private static final String ARG_FICHE_ID = "fiche_id";
    private String ficheId;

    public static VisualiserFicheFragment newInstance(String ficheId) {
        VisualiserFicheFragment fragment = new VisualiserFicheFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FICHE_ID, ficheId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ficheId = getArguments().getString(ARG_FICHE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualiser_fiche, container, false);

        TextView ficheText = view.findViewById(R.id.ficheDetailsTextView);
        ficheText.setText("Détails de la fiche ID : " + ficheId);

        return view;
    }
}
