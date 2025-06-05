package com.example.gsb_compterendu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button logoutButton;
    private String nom;
    private String prenom;
    private String id_utilisateur;

    private RecyclerView recyclerView;
    private FicheAdapter ficheAdapter;
    private List<Fiche> ficheList;
    private String API_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        welcomeText = findViewById(R.id.welcomeText);
        logoutButton = findViewById(R.id.logoutButton);
        recyclerView = findViewById(R.id.fichesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportFragmentManager().setFragmentResultListener("refreshRequest", this, (requestKey, bundle) -> {
            boolean shouldRefresh = bundle.getBoolean("refresh", false);
            if (shouldRefresh) {
                ficheList.clear(); // Nettoyer ancienne liste
                fetchFiches();     // Recharger via API
            }
        });

        Intent intent = getIntent();
        nom = intent.getStringExtra("nom");
        prenom = intent.getStringExtra("prenom");
        int id_utilisateur = intent.getIntExtra("id_utilisateur", -1);

        // Afficher le message de bienvenue
        welcomeText.setText("Bonjour " + nom + " " + prenom +" !");

        // Configurer l'URL de l'API avec l'ID de l'utilisateur
        API_URL = "https://maxence-philippon.fr/assets/api/crvisiteur.php?id_utilisateur=" + id_utilisateur;

        ficheList = new ArrayList<>();
        ficheAdapter = new FicheAdapter(this, ficheList);
        recyclerView.setAdapter(ficheAdapter);

        // Récupérer les fiches
        Log.d("DEBUG", "fetchFiches() appelé !");

        fetchFiches();

        // Déconnexion : Retour à MainActivity
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogoutTask().execute(); // Exécuter la tâche en arrière-plan
            }
        });
        FloatingActionButton fabAddFiche = findViewById(R.id.fabAddFiche);
        fabAddFiche.setOnClickListener(v -> {
            AjouterFicheFragment fragment = new AjouterFicheFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id_utilisateur", String.valueOf(id_utilisateur)); // passe l'ID utilisateur ici
            fragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();

            findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.fichesRecyclerView).setVisibility(View.GONE);
        });

    }

    private void fetchFiches() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("status") == 200) {
                                JSONArray dataArray = response.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject obj = dataArray.getJSONObject(i);
                                    Fiche fiche = new Fiche(
                                            obj.getString("id_cr"),
                                            obj.getString("date_visite"),
                                            obj.getString("praticien_nom"),
                                            obj.getString("produit_nom"),
                                            obj.getString("statu")
                                    );
                                    ficheList.add(fiche);
                                }
                                ficheAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            Log.d("API_RESPONSE", response.toString());

                            Log.e("Volley", "Parsing error", e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error fetching data", error);
            }
        });

        queue.add(request);
    }

    // AsyncTask pour simuler une déconnexion en arrière-plan
    private class LogoutTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1000); // Simule un délai de traitement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Retour à l'écran de connexion
            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Effacer l'historique
            startActivity(intent);
        }
    }
}
