package com.example.proyectodm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectodm.DB.DBEjercicios;

public class NuevoEjercicioActivity extends AppCompatActivity {

    EditText txtNombre, txtDescripcion, txtTiempo;
    Button btnGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ejercicio);

        txtNombre = findViewById(R.id.AñadirNombre);
        txtDescripcion = findViewById(R.id.AñadirDescripcion);
        txtTiempo = findViewById(R.id.AñadirTiempo);
        btnGuardar = findViewById(R.id.AñadirEjercicioBD);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBEjercicios dbEjercicios = new DBEjercicios(NuevoEjercicioActivity.this);
                long id = dbEjercicios.insertarEjercicio(txtNombre.getText().toString(),txtDescripcion.getText().toString(),txtTiempo.getText().toString());
                if (id > 0){
                    Toast.makeText(NuevoEjercicioActivity.this, "EJERCICIO GUARDADO", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NuevoEjercicioActivity.this, PrincipalEjerciciosActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(NuevoEjercicioActivity.this, "ERROR AL GUARDAR EL EJERCICIO", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}