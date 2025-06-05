package com.example.gsb_compterendu;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ModifierFicheFragment extends Fragment {

    private static final String ARG_FICHE_ID = "fiche_id";
    private String ficheId;

    private EditText editDate, editCommentaire;
    private Spinner spinnerPraticien, spinnerStatut;
    private LinearLayout produitsContainer;

    private final String[] statuts = {
            "En cours de traitement",
            "Modification en attente",
            "Terminer",
            "Refuser"
    };

    private List<String> produitsCoches = new ArrayList<>();
    private List<String> praticienIds = new ArrayList<>();
    private List<String> praticienNoms = new ArrayList<>();
    private String praticienSelectionne = null;

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
        return inflater.inflate(R.layout.fragment_modifier_fiche, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editDate = view.findViewById(R.id.editDate);
        editDate.setInputType(0); // Empêche le clavier de s'ouvrir
        editDate.setOnClickListener(v -> showDatePicker());
        Button btnSupprimer = view.findViewById(R.id.btnSupprimer);
        btnSupprimer.setOnClickListener(v -> afficherPopupSuppression());

        editCommentaire = view.findViewById(R.id.editCommentaire);
        spinnerPraticien = view.findViewById(R.id.spinnerPraticien);
        spinnerStatut = view.findViewById(R.id.spinnerStatut);
        produitsContainer = view.findViewById(R.id.produitsContainer);

        Button btnFermer = view.findViewById(R.id.btnFermer);
        btnFermer.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
            requireActivity().findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
            requireActivity().findViewById(R.id.fichesRecyclerView).setVisibility(View.VISIBLE);
        });

        Button btnEnregistrer = view.findViewById(R.id.btnEnregistrer);
        btnEnregistrer.setOnClickListener(v -> {
            String date = editDate.getText().toString().trim();
            String commentaire = editCommentaire.getText().toString().trim();
            String statutChoisi = spinnerStatut.getSelectedItem().toString();
            int praticienIndex = spinnerPraticien.getSelectedItemPosition();
            String praticienId = praticienIds.get(praticienIndex);
            String dateFormatAPI = "";
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date parsedDate = inputFormat.parse(date);
                dateFormatAPI = apiFormat.format(parsedDate);
            } catch (Exception e) {
                Toast.makeText(requireContext(), "Format de date invalide", Toast.LENGTH_SHORT).show();
                return;
            }
            List<String> produitsIds = new ArrayList<>();
            for (int i = 0; i < produitsContainer.getChildCount(); i++) {
                View child = produitsContainer.getChildAt(i);
                if (child instanceof CheckBox) {
                    CheckBox cb = (CheckBox) child;
                    if (cb.isChecked()) {
                        produitsIds.add(cb.getTag().toString());
                    }
                }
            }

            enregistrerFiche(dateFormatAPI, commentaire, statutChoisi, praticienId, produitsIds);
        });

        ArrayAdapter<String> statutAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                statuts
        );
        statutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatut.setAdapter(statutAdapter);

        loadFicheDetails();
    }

    private void afficherPopupSuppression() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmation")
                .setMessage("Voulez-vous vraiment supprimer ce compte rendu ?")
                .setPositiveButton("Supprimer", (dialog, which) -> supprimerFiche())
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void supprimerFiche() {
        String apiUrl = "https://maxence-philippon.fr/assets/api/delete_cr.php";

        StringRequest request = new StringRequest(Request.Method.POST, apiUrl,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        Toast.makeText(requireContext(), json.optString("message", "Supprimé avec succès"), Toast.LENGTH_SHORT).show();

                        // ✅ Demande au parent de rafraîchir
                        Bundle result = new Bundle();
                        result.putBoolean("refresh", true);
                        getParentFragmentManager().setFragmentResult("refreshRequest", result);

                        requireActivity().getSupportFragmentManager().popBackStack();
                        requireActivity().findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
                        requireActivity().findViewById(R.id.fichesRecyclerView).setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Erreur de traitement", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(requireContext(), "Erreur réseau lors de la suppression", Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_cr", ficheId);
                return params;
            }
        };

        Volley.newRequestQueue(requireContext()).add(request);
    }



    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);

                    // Format affichage : 31/03/2025
                    SimpleDateFormat affichageFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String dateAffichage = affichageFormat.format(selectedDate.getTime());

                    editDate.setText(dateAffichage);
                },
                year, month, day
        );

        datePickerDialog.show();
    }


    private void loadFicheDetails() {
        String apiUrl = "https://maxence-philippon.fr/assets/api/show_cr.php?id_cr=" + ficheId;
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    try {
                        if (response.getInt("status") == 200) {
                            JSONObject data = response.getJSONObject("data");

                            String rawDate = data.getString("date_visite"); // ex: 2025-03-31
                            SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());                            editCommentaire.setText(data.getString("commentaire"));
                            Date parsed = serverFormat.parse(rawDate);
                            String formattedDate = displayFormat.format(parsed);

                            editDate.setText(formattedDate);
                            String statutRecupere = data.getString("status");
                            for (int i = 0; i < statuts.length; i++) {
                                if (statuts[i].equalsIgnoreCase(statutRecupere)) {
                                    spinnerStatut.setSelection(i);
                                    break;
                                }
                            }

                            praticienSelectionne = data.getString("specialiste");

                            String produitsStr = data.optString("produits", "");
                            if (!produitsStr.isEmpty()) {
                                produitsCoches = Arrays.asList(produitsStr.split(","));
                            }

                            loadPraticiens();
                            loadProduitsDisponibles();

                        } else {
                            Toast.makeText(requireContext(), "Erreur: " + response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Erreur traitement JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(requireContext(), "Erreur réseau", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }

    private void loadProduitsDisponibles() {
        String apiUrl = "https://maxence-philippon.fr/assets/api/produits.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

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
                                checkBox.setChecked(produitsCoches.contains(id));
                                produitsContainer.addView(checkBox);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(requireContext(), "Erreur produits", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }

    private void loadPraticiens() {
        String apiUrl = "https://maxence-philippon.fr/assets/api/praticiens.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    try {
                        if (response.getInt("status") == 200) {
                            JSONArray praticiens = response.getJSONArray("data");

                            praticienIds.clear();
                            praticienNoms.clear();

                            for (int i = 0; i < praticiens.length(); i++) {
                                JSONObject obj = praticiens.getJSONObject(i);
                                praticienIds.add(obj.getString("id_praticien"));
                                praticienNoms.add(obj.getString("nom"));
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                                    android.R.layout.simple_spinner_item, praticienNoms);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerPraticien.setAdapter(adapter);

                            if (praticienSelectionne != null) {
                                int index = praticienNoms.indexOf(praticienSelectionne);
                                if (index >= 0) {
                                    spinnerPraticien.setSelection(index);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(requireContext(), "Erreur praticiens", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }

    private void enregistrerFiche(String date, String commentaire, String statut, String praticienId, List<String> produits) {
        String apiUrl = "https://maxence-philippon.fr/assets/api/update_cr.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        StringRequest request = new StringRequest(Request.Method.POST, apiUrl,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        Toast.makeText(requireContext(), json.optString("message", "Modifications enregistrées."), Toast.LENGTH_SHORT).show();

                        Bundle result = new Bundle();
                        result.putBoolean("refresh", true);
                        getParentFragmentManager().setFragmentResult("refreshRequest", result);

                        requireActivity().getSupportFragmentManager().popBackStack();
                        requireActivity().findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
                        requireActivity().findViewById(R.id.fichesRecyclerView).setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Réponse invalide du serveur", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    String message = "Erreur d'enregistrement";
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        try {
                            String responseBody = new String(error.networkResponse.data);
                            JSONObject errorObj = new JSONObject(responseBody);
                            message = errorObj.optString("message", message);
                        } catch (Exception ignored) {}
                    }
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", ficheId);
                params.put("date", date);
                params.put("commentaire", commentaire);
                params.put("practicien", praticienId);

                for (int i = 0; i < produits.size(); i++) {
                    params.put("produit[" + i + "]", produits.get(i));
                }

                return params;
            }
        };

        queue.add(request);
    }

}
