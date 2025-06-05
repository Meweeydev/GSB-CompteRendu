package com.example.gsb_compterendu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.Email);
        passwordEditText = findViewById(R.id.Password);
        loginButton = findViewById(R.id.button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    new ApiRequest(email, password, new ApiRequest.LoginCallback() {
                        @Override
                        public void onSuccess(String nom, String prenom, String email, String role, String region, int idUtilisateur) {
                            // Créer un intent pour passer à DashboardActivity
                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            intent.putExtra("nom", nom);
                            intent.putExtra("prenom", prenom);
                            intent.putExtra("email", email);
                            intent.putExtra("role", role);
                            intent.putExtra("region", region);
                            intent.putExtra("id_utilisateur", idUtilisateur);

                            startActivity(intent);
                            finish(); // Fermer l'écran de connexion après connexion réussie
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }).execute();
                } else {
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
