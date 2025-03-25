package com.example.gsb_compterendu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ModifierFicheFragment extends Fragment {

    private static final String ARG_FICHE_ID = "fiche_id";
    private String ficheId;

    public static ModifierFicheFragment newInstance(String ficheId) {
        ModifierFicheFragment fragment = new ModifierFicheFragment();
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
        View view = inflater.inflate(R.layout.fragment_modifier_fiche, container, false);

        TextView textView = view.findViewById(R.id.modifierFicheTextView);
        Button btnFermer = view.findViewById(R.id.btnFermer);

        textView.setText("Modifier la fiche ID : " + ficheId);

        btnFermer.setOnClickListener(v -> {
            // Revenir à la liste (RecyclerView)
            requireActivity().getSupportFragmentManager().popBackStack();

            // Masquer le fragment, réafficher la liste
            requireActivity().findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
            requireActivity().findViewById(R.id.fichesRecyclerView).setVisibility(View.VISIBLE);
        });

        return view;
    }
}
