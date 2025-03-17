package com.example.gsb_compterendu;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class ApiRequest extends AsyncTask<String, Void, String> {

    private static final String API_URL = "https://maxence-philippon.fr/assets/api/api_connexion.php";  // Remplace avec l'URL de ton API

    private String email;
    private String password;
    private LoginCallback callback;

    public interface LoginCallback {
        void onSuccess(String token);
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
                String inputLine;
                StringBuilder response = new StringBuilder();
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
                    callback.onSuccess(token);
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
