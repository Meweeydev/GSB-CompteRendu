package com.example.gsb_compterendu;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiRequest extends AsyncTask<String, Void, String> {

    private static final String API_URL = "https://maxence-philippon.fr/assets/api/api_connexion.php";

    private String email;
    private String password;
    private LoginCallback callback;

    public interface LoginCallback {
        void onSuccess(String token, String nom, String prenom, String email, String role, String region, int idUtilisateur);
        void onError(String message);
    }

    public ApiRequest(String email, String password, LoginCallback callback) {
        this.email = email;
        this.password = password;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            // Construire les paramètres POST
            String postData = "email=" + email + "&password=" + password;
            OutputStream os = conn.getOutputStream();
            os.write(postData.getBytes());
            os.flush();
            os.close();

            // Lire la réponse
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.e("API_ERROR", "Erreur lors de la requête : " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                if (jsonResponse.has("token")) {
                    String token = jsonResponse.getString("token");
                    String nom = jsonResponse.optString("nom", "");
                    String prenom = jsonResponse.optString("prenom", "");
                    String email = jsonResponse.optString("email", "");
                    String role = jsonResponse.optString("role", "");
                    String region = jsonResponse.optString("region", "");
                    int id_utilisateur = jsonResponse.optInt("id_utilisateur", -1);
                    Log.d("DEBUG", "id_utilisateur extrait: " + id_utilisateur);

                    callback.onSuccess(token, nom, prenom, email, role, region, id_utilisateur);
                } else {
                    callback.onError("Authentification échouée !");
                }
            } catch (Exception e) {
                callback.onError("Erreur JSON : " + e.getMessage());
            }
        } else {
            callback.onError("Erreur de connexion à l'API.");
        }
    }
}
