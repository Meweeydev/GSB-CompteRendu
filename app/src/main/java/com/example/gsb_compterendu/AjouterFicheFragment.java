package com.example.gsb_compterendu;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;

public class AjouterFicheFragment extends Fragment {

    private EditText editDate, editCommentaire;
    private Spinner spinnerPraticien;
    private LinearLayout produitsContainer;

    private String idutilisateur;


    private List<String> praticienIds = new ArrayList<>();
    private List<String> praticienNoms = new ArrayList<>();

    private static final int PICK_FILE_REQUEST = 1;
    private Uri selectedFileUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ajouter_fiche, container, false); // même layout
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editDate = view.findViewById(R.id.editDate);
        editCommentaire = view.findViewById(R.id.editCommentaire);
        spinnerPraticien = view.findViewById(R.id.spinnerPraticien);
        produitsContainer = view.findViewById(R.id.produitsContainer);
        idutilisateur = getArguments().getString("id_utilisateur");

        editDate.setOnClickListener(v -> showDatePicker());

        loadPraticiens();
        loadProduitsDisponibles();

        view.findViewById(R.id.btnFermer).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
            requireActivity().findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
            requireActivity().findViewById(R.id.fichesRecyclerView).setVisibility(View.VISIBLE);
        });


        view.findViewById(R.id.btnEnregistrer).setOnClickListener(v -> enregistrerNouvelleFiche());
        Button btnChoisirFichier = view.findViewById(R.id.btnChoisirFichier);
        TextView txtNomFichier = view.findViewById(R.id.txtNomFichier);

        btnChoisirFichier.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // Images, PDF, etc.
            startActivityForResult(Intent.createChooser(intent, "Sélectionner un fichier"), PICK_FILE_REQUEST);
        });

    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    editDate.setText(sdf.format(selectedDate.getTime()));
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void loadPraticiens() {
        String apiUrl = "https://maxence-philippon.fr/assets/api/praticiens.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        StringRequest request = new StringRequest(Request.Method.GET, apiUrl,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("status") == 200) {
                            JSONArray praticiens = json.getJSONArray("data");

                            for (int i = 0; i < praticiens.length(); i++) {
                                JSONObject praticien = praticiens.getJSONObject(i);
                                praticienIds.add(praticien.getString("id_praticien"));
                                praticienNoms.add(praticien.getString("nom"));
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                                    android.R.layout.simple_spinner_item, praticienNoms);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerPraticien.setAdapter(adapter);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(requireContext(), "Erreur chargement praticiens", Toast.LENGTH_SHORT).show());

        queue.add(request);
    }

    private void loadProduitsDisponibles() {
        String apiUrl = "https://maxence-philippon.fr/assets/api/produits.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        StringRequest request = new StringRequest(Request.Method.GET, apiUrl,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.getInt("status") == 200) {
                            JSONArray produits = json.getJSONArray("data");

                            for (int i = 0; i < produits.length(); i++) {
                                JSONObject produit = produits.getJSONObject(i);
                                String id = produit.getString("id_produit");
                                String nom = produit.getString("nom");

                                CheckBox cb = new CheckBox(requireContext());
                                cb.setText(nom);
                                cb.setTag(id);
                                produitsContainer.addView(cb);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(requireContext(), "Erreur produits", Toast.LENGTH_SHORT).show());

        queue.add(request);
    }




    private void enregistrerNouvelleFiche() {
        String apiUrl = "https://maxence-philippon.fr/assets/api/add_cr.php";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        String date = editDate.getText().toString();
        String commentaire = editCommentaire.getText().toString();
        String praticienId = praticienIds.get(spinnerPraticien.getSelectedItemPosition());
        String id_utilisateur = idutilisateur;

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


        StringRequest request = new StringRequest(Request.Method.POST, apiUrl,
                response -> {
                    Toast.makeText(requireContext(), "Fiche créée avec succès", Toast.LENGTH_SHORT).show();

                    // Refresh
                    Bundle result = new Bundle();
                    result.putBoolean("refresh", true);
                    getParentFragmentManager().setFragmentResult("refreshRequest", result);

                    requireActivity().getSupportFragmentManager().popBackStack();
                    requireActivity().findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
                    requireActivity().findViewById(R.id.fichesRecyclerView).setVisibility(View.VISIBLE);
                },
                error -> Toast.makeText(requireContext(), "Erreur d'enregistrement", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date parsedDate = inputFormat.parse(date);
                    params.put("date", apiFormat.format(parsedDate));
                } catch (Exception e) {
                    params.put("date", "");
                }

                params.put("id_utilisateur", id_utilisateur);

                params.put("commentaire", commentaire);
                params.put("practicien", praticienId);


                for (int i = 0; i < produitsIds.size(); i++) {
                    params.put("produit[" + i + "]", produitsIds.get(i));
                }
                Log.d("POST_PARAMS", params.toString());

                return params;
            }
        };

        queue.add(request);
    }
}


