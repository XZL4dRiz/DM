package com.example.proyectodm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodm.DB.DBEjercicios;
import com.example.proyectodm.DB.DBRecetas;
import com.example.proyectodm.adaptadores.ListaEjerciciosAdapter;
import com.example.proyectodm.adaptadores.ListaRecetasAdapter;
import com.example.proyectodm.entidades.Ejercicios;
import com.example.proyectodm.entidades.Recetas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRecetas,recyclerViewEjercicios;
    private ListaRecetasAdapter recetaAdapter;
    private ListaEjerciciosAdapter ejerciciosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewRecetas = findViewById(R.id.recyclerViewRecetas);
        recyclerViewRecetas.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewEjercicios = findViewById(R.id.recyclerViewEjercicios);
        recyclerViewEjercicios.setLayoutManager(new LinearLayoutManager(this));

        // Mostrar las recetas al iniciar la actividad
        mostrarRecetasSeleccionadas();
        mostrarEjerciciosSeleccionados();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    public void accederRecetas(){
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }

    public void accederEjercicios(){
        Intent intent = new Intent(this, PrincipalEjerciciosActivity.class);
        startActivity(intent);
    }

    public void accederPerfil(){
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
    }

    public void cerrarSesion(){
        Intent intent = new Intent(this, InicioActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean toret = false;
        int id = item.getItemId();

        if(id == R.id.recetas) {
            this.accederRecetas();
            toret = true;
        } else if(id == R.id.ejercicios){
            this.accederEjercicios();
            toret = true;
        } else if(id == R.id.perfil){
        this.accederPerfil();
        toret = true;
        } else if(id == R.id.cerrarSesion){
            this.cerrarSesion();
            toret = true;
    }

        return toret || super.onOptionsItemSelected(item);
    }

    private void mostrarRecetasSeleccionadas() {
        // Obtener la lista de IDs de recetas seleccionadas desde SharedPreferences
        SharedPreferences preferences = getSharedPreferences("RecetasSeleccionadas", MODE_PRIVATE);
        Set<String> idRecetasSeleccionadas = preferences.getStringSet("IDRecetasSeleccionadas", new HashSet<>());

        if (idRecetasSeleccionadas != null) {
            // Obtener las recetas correspondientes desde la base de datos
            DBRecetas dbRecetas = new DBRecetas(this);
            ArrayList<Recetas> recetasSeleccionadas = new ArrayList<>();

            for (String idReceta : idRecetasSeleccionadas) {
                int id = Integer.parseInt(idReceta);
                Recetas receta = dbRecetas.verRecetas(id);
                if (receta != null) {
                    recetasSeleccionadas.add(receta);
                }
            }
            // Verificar si la lista de recetas seleccionadas no está vacía
            if (!recetasSeleccionadas.isEmpty()) {
                // Configurar el adaptador y asignar al RecyclerView
                recetaAdapter = new ListaRecetasAdapter(recetasSeleccionadas);
                recyclerViewRecetas.setAdapter(recetaAdapter);
            } else {
                // Si la lista está vacía dejamos el RecyclerView vacío.
                recyclerViewRecetas.setAdapter(null);
            }
        }
    }

    private void mostrarEjerciciosSeleccionados() {
        // Obtener la lista de IDs de recetas seleccionadas desde SharedPreferences
        SharedPreferences preferences = getSharedPreferences("EjerciciosSeleccionados", MODE_PRIVATE);
        Set<String> idRecetasSeleccionadas = preferences.getStringSet("IDEjerciciosSeleccionados", null);

        if (idRecetasSeleccionadas != null) {
            // Obtener las recetas correspondientes desde la base de datos
            DBEjercicios dbEjercicios = new DBEjercicios(this);
            ArrayList<Ejercicios> EjerciciosSeleccionados = new ArrayList<>();

            for (String idReceta : idRecetasSeleccionadas) {
                int id = Integer.parseInt(idReceta);
                Ejercicios ejercicio = dbEjercicios.verEjercicios(id);
                if (ejercicio != null) {
                    EjerciciosSeleccionados.add(ejercicio);
                }
            }

            // Configurar el adaptador y asignar al RecyclerView
            ejerciciosAdapter = new ListaEjerciciosAdapter(EjerciciosSeleccionados);
            recyclerViewEjercicios.setAdapter(ejerciciosAdapter);
        }
    }

}
