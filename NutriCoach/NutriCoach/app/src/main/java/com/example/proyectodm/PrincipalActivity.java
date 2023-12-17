package com.example.proyectodm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.proyectodm.DB.DBRecetas;
import com.example.proyectodm.adaptadores.ListaRecetasAdapter;
import com.example.proyectodm.entidades.Recetas;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity {

    RecyclerView listaRecetas;
    ArrayList<Recetas> listaArrayRecetas;
    ListaRecetasAdapter adapter;
    Spinner spinnerCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Inflar el Spinner
        spinnerCategorias = findViewById(R.id.spinnerCategorias);

        // Configurar un ArrayAdapter para el Spinner con las opciones "Desayuno", "Comida" y "Cena"
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(
                this,
                R.array.categorias_array,
                android.R.layout.simple_spinner_item
        );
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asignar el adaptador al Spinner
        spinnerCategorias.setAdapter(adapterSpinner);

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtener la categoría seleccionada
                String categoriaSeleccionada = parentView.getItemAtPosition(position).toString();

                // Actualizar la lista de recetas según la categoría seleccionada
                actualizarListaRecetas(categoriaSeleccionada);
            }
            public void onNothingSelected(AdapterView<?> parentView) {
                // No se hace nada cuando no se selecciona nada
            }
        });


        listaRecetas = findViewById(R.id.listaRecetas);
        listaRecetas.setLayoutManager(new LinearLayoutManager(this));

        DBRecetas dbRecetas = new DBRecetas(PrincipalActivity.this);
        listaArrayRecetas = new ArrayList<>();
        adapter = new ListaRecetasAdapter(dbRecetas.mostrarRecetas());
        listaRecetas.setAdapter(adapter);
    }

    private void actualizarListaRecetas(String categoria) {
        DBRecetas dbRecetas = new DBRecetas(PrincipalActivity.this);

        // Consultar las recetas según la categoría seleccionada
        if(categoria.equals("Todos")){
            listaArrayRecetas = dbRecetas.mostrarRecetas();
        } else if (categoria.equals("Comida") || categoria.equals("Desayuno") || categoria.equals("Cena")) {
            listaArrayRecetas = dbRecetas.mostrarRecetasPorCategoria(categoria);
        } else {
            // Mostrar todas las recetas si no se ha seleccionado ninguna categoría
            listaArrayRecetas = dbRecetas.mostrarRecetas();
        }
        // Actualizar el adaptador con la nueva lista de recetas
        adapter = new ListaRecetasAdapter(listaArrayRecetas);
        listaRecetas.setAdapter(adapter);
    }
}