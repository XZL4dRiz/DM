package com.example.proyectodm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodm.DB.DBEjercicios;
import com.example.proyectodm.adaptadores.ListaEjerciciosAdapter;
import com.example.proyectodm.entidades.Ejercicios;

import java.util.ArrayList;

public class PrincipalEjerciciosActivity extends AppCompatActivity {

    RecyclerView listaEjercicios;
    ArrayList<Ejercicios> listaArrayEjercicios;
    ListaEjerciciosAdapter adapter;

    Button añadir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_ejercicios);
        añadir = findViewById(R.id.AñadirEjercicio);

        listaEjercicios = findViewById(R.id.listaEjercicios);
        listaEjercicios.setLayoutManager(new LinearLayoutManager(this));

        DBEjercicios dbEjercicios = new DBEjercicios(PrincipalEjerciciosActivity.this);
        listaArrayEjercicios = new ArrayList<>();
        adapter = new ListaEjerciciosAdapter(dbEjercicios.mostrarEjercicios());
        listaEjercicios.setAdapter(adapter);

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalEjerciciosActivity.this, NuevoEjercicioActivity.class);
                startActivity(intent);
            }
        });
    }
}