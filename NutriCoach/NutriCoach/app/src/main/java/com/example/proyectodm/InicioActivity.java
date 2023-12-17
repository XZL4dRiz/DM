package com.example.proyectodm;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectodm.DB.DBHelper;
import com.example.proyectodm.DB.DBHelperEjercicios;

public class InicioActivity extends AppCompatActivity {

    Button btnBBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btnBBDD = findViewById(R.id.IniciarSesion);
        btnBBDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(InicioActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if(db == null){
                    Toast.makeText(InicioActivity.this, "ERROR AL CREAR LA BASE DE DATOS", Toast.LENGTH_LONG).show();
                }
                DBHelperEjercicios dbHelperEjercicios = new DBHelperEjercicios(InicioActivity.this);
                SQLiteDatabase dbEjercicios = dbHelperEjercicios.getWritableDatabase();
                if(dbEjercicios == null){
                    Toast.makeText(InicioActivity.this, "ERROR AL CREAR LA BASE DE DATOS", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btnIrAPrincipal = findViewById(R.id.IniciarSesion);
        btnIrAPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene los valores que el usuario escribe
                EditText usuarioEditText = findViewById(R.id.User);
                EditText passwordEditText = findViewById(R.id.Password);
                String usuario = usuarioEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if ("user".equals(usuario) && "password".equals(password)) {
                    Intent intent = new Intent(InicioActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Autenticación fallida
                    Toast.makeText(InicioActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}