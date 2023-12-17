package com.example.proyectodm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    private EditText editTextAltura, editTextPeso, editTextEdad, editTextSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);


        editTextAltura = findViewById(R.id.editTextAltura);
        editTextPeso = findViewById(R.id.editTextPeso);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextSexo = findViewById(R.id.editTextSexo);

        Button btnContinuar = findViewById(R.id.btnContinuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados
                String altura = editTextAltura.getText().toString();
                String peso = editTextPeso.getText().toString();
                String edad = editTextEdad.getText().toString();
                String sexo = editTextSexo.getText().toString();

                // Crear un Intent para pasar a la siguiente Activity
                Intent intent = new Intent(FormularioActivity.this, PerfilActivity.class);
                intent.putExtra("ALTURA", altura);
                intent.putExtra("PESO", peso);
                intent.putExtra("EDAD", edad);
                intent.putExtra("SEXO", sexo);

                // Guardar datos en SharedPreferences
                SharedPreferences preferences = getSharedPreferences("PerfilPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("ALTURA", altura);
                editor.putString("PESO", peso);
                editor.putString("EDAD", edad);
                editor.putString("SEXO", sexo);
                editor.apply();

                startActivity(intent);

                Toast.makeText(getApplicationContext(), "Datos guardados con éxito", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

