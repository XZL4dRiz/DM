package com.example.proyectodm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {

    private TextView textViewAltura;
    private TextView textViewPeso;
    private TextView textViewEdad;
    private TextView textViewSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Obtener referencias de TextView
        textViewAltura = findViewById(R.id.alturaPerfil);
        textViewPeso = findViewById(R.id.pesoPerfil);
        textViewEdad = findViewById(R.id.edadPerfil);
        textViewSexo = findViewById(R.id.sexoPerfil);

        Button btnContinuar = findViewById(R.id.editProfileButton);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilActivity.this, FormularioActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Vamos a modificar tu perfil...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Obtener el objeto SharedPreferences
        SharedPreferences preferences = getSharedPreferences("PerfilPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Guardar datos actuales en SharedPreferences
        editor.putString("ALTURA", textViewAltura.getText().toString());
        editor.putString("PESO", textViewPeso.getText().toString());
        editor.putString("EDAD", textViewEdad.getText().toString());
        editor.putString("SEXO", textViewSexo.getText().toString());

        // Aplicar cambios
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Obtener el objeto SharedPreferences
        SharedPreferences preferences = getSharedPreferences("PerfilPrefs", MODE_PRIVATE);

        // Cargar datos desde SharedPreferences y mostrarlos en los TextView
        textViewAltura.setText(preferences.getString("ALTURA", ""));
        textViewPeso.setText(preferences.getString("PESO", ""));
        textViewEdad.setText(preferences.getString("EDAD", ""));
        textViewSexo.setText(preferences.getString("SEXO", ""));
    }

}
