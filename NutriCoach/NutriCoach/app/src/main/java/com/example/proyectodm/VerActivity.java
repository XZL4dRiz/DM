package com.example.proyectodm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.proyectodm.DB.DBRecetas;
import com.example.proyectodm.adaptadores.ListaIngredientesAdapter;
import com.example.proyectodm.entidades.Recetas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class
VerActivity extends AppCompatActivity {

    TextView txtNombre, txtDescripcion;
    RecyclerView txtIngrediente;
    Recetas recetas;
    ArrayList<Recetas> ingredientes;
    ListaIngredientesAdapter adapter;

    int id=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombre = findViewById(R.id.NombreReceta);
        txtDescripcion = findViewById(R.id.DescripcionReceta);
        txtIngrediente = findViewById(R.id.Ingredientes);
        txtIngrediente.setLayoutManager((new LinearLayoutManager(this)));

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            }else{
                id = extras.getInt("ID");
            }
        }else{
            id = (int) savedInstanceState.getSerializable("ID");
        }

        DBRecetas dbRecetas = new DBRecetas(VerActivity.this);
        recetas = dbRecetas.verRecetas(id);

        if(recetas != null){
            txtNombre.setText(recetas.getNombre().toUpperCase());
            txtDescripcion.setText(recetas.getDescripcion());
        }

        ingredientes = new ArrayList<>();
        adapter = new ListaIngredientesAdapter(dbRecetas.verIngredientes(id));
        txtIngrediente.setAdapter(adapter);

        Button btnAgregarAInicio = findViewById(R.id.btnAgregarAInicio);
        btnAgregarAInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarAInicio();
            }
        });
        Button btnEliminarDeInicio = findViewById(R.id.btnEliminarDeSeleccionadas);
        btnEliminarDeInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarDeInicio();
            }
        });

    }

    private void agregarAInicio() {
        // Recuperar la lista actual de IDs de recetas seleccionadas desde SharedPreferences
        SharedPreferences preferences = getSharedPreferences("RecetasSeleccionadas", MODE_PRIVATE);
        Set<String> idRecetasSeleccionadas = preferences.getStringSet("IDRecetasSeleccionadas", new HashSet<>());

        // Agregar el nuevo ID de la receta actual
        idRecetasSeleccionadas.add(String.valueOf(id));

        // Guardar la lista actualizada en SharedPreferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet("IDRecetasSeleccionadas", idRecetasSeleccionadas);
        editor.apply();

        // Muestra un mensaje o realiza alguna acción adicional si lo necesitas.
        Toast.makeText(this, "Receta agregada a las seleccionadas", Toast.LENGTH_SHORT).show();
    }

    private void eliminarDeInicio() {
        // Crear un cuadro de diálogo de confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Está seguro de que desea eliminar esta receta de las seleccionadas?");

        // Agregar botones al cuadro de diálogo
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Recuperar la lista actual de IDs de recetas seleccionadas desde SharedPreferences
                SharedPreferences preferences = getSharedPreferences("RecetasSeleccionadas", MODE_PRIVATE);
                Set<String> idRecetasSeleccionadas = preferences.getStringSet("IDRecetasSeleccionadas", new HashSet<>());

                // Eliminar el ID de la receta actual
                idRecetasSeleccionadas.remove(String.valueOf(id));

                // Guardar la lista actualizada en SharedPreferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putStringSet("IDRecetasSeleccionadas", idRecetasSeleccionadas);
                editor.apply();

                // Mostrar un mensaje adicional
                Toast.makeText(getApplicationContext(), "Receta eliminada de las seleccionadas", Toast.LENGTH_SHORT).show();

                // Volver a la página principal (MainActivity)
                Intent intent = new Intent(VerActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Esto cierra la actividad actual y evita que el usuario pueda volver atrás con el botón de retroceso
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada si el usuario elige no eliminar la receta
            }
        });

        // Mostrar el cuadro de diálogo
        builder.show();
    }
}