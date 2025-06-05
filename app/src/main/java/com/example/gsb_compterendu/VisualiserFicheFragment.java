package com.example.gsb_compterendu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class VisualiserFicheFragment extends Fragment {

    private static final String ARG_FICHE_ID = "fiche_id";
    private String ficheId;

    private EditText viewDate, viewCommentaire;
    private TextView viewPraticien, viewStatut;
    private LinearLayout viewProduitsContainer;

    private List<String> produitsCoches;

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
        return inflater.inflate(R.layout.fragment_visualiser_fiche, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewDate = view.findViewById(R.id.viewDate);
        viewPraticien = view.findViewById(R.id.viewPraticien);
        viewCommentaire = view.findViewById(R.id.viewCommentaire);
        viewStatut = view.findViewById(R.id.viewStatut);
        viewProduitsContainer = view.findViewById(R.id.viewProduitsContainer);

        Button btnFermer = view.findViewById(R.id.btnFermerView);
        btnFermer.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
            requireActivity().findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
            requireActivity().findViewById(R.id.fichesRecyclerView).setVisibility(View.VISIBLE);
        });

        loadFicheDetails();
    }

    private void loadFicheDetails() {
        String apiUrl = "https://maxence-philippon.fr/assets/api/show_cr.php?id_cr=" + ficheId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    try {
                        if (response.getInt("status") == 200) {
                            JSONObject data = response.getJSONObject("data");

                            viewDate.setText(data.getString("date_visite"));
                            viewPraticien.setText(data.getString("specialiste"));
                            viewCommentaire.setText(data.getString("commentaire"));
                            viewStatut.setText(data.getString("status"));

                            String produitsStr = data.optString("produits", "");
                            if (!produitsStr.isEmpty()) {
                                produitsCoches = Arrays.asList(produitsStr.split(","));
                                loadProduits();
                            }
                        } else {
                            Toast.makeText(requireContext(), "Erreur : " + response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Erreur de traitement", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(requireContext(), "Erreur réseau", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(requireContext()).add(request);
    }

    private void loadProduits() {
        String apiUrl = "https://maxence-philippon.fr/assets/api/produits.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    try {
                        if (response.getInt("status") == 200) {
                            JSONArray produits = response.getJSONArray("data");

                            for (int i = 0; i < produits.length(); i++) {
                                JSONObject produit = produits.getJSONObject(i);
                                String id = produit.getString("id_produit");
                                String nom = produit.getString("nom");

                                CheckBox checkBox = new CheckBox(requireContext());
                                checkBox.setText(nom);
                                checkBox.setTag(id);
                                checkBox.setEnabled(false);

                                if (produitsCoches.contains(id)) {
                                    checkBox.setChecked(true);
                                }

                                viewProduitsContainer.addView(checkBox);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace());

        Volley.newRequestQueue(requireContext()).add(request);
    }
}
