package com.example.proyectodm;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodm.DB.DBEjercicios;
import com.example.proyectodm.entidades.Ejercicios;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class
VerEjerciciosActivity extends AppCompatActivity {

    TextView txtNombre, txtDescripcion, txtTiempo;
    Ejercicios ejercicios;

    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ejercicios);

        txtNombre = findViewById(R.id.NombreEjercicio);
        txtDescripcion = findViewById(R.id.DescripcionEjercicio);
        txtTiempo = findViewById(R.id.TiempoEjercicio);

        id = getIntent().getIntExtra("ID", -1);

        DBEjercicios dbEjercicios = new DBEjercicios(VerEjerciciosActivity.this);
        ejercicios = dbEjercicios.verEjercicios(id);

        if(ejercicios != null){
            txtNombre.setText(ejercicios.getNombre().toUpperCase());
            txtDescripcion.setText(ejercicios.getDescripcion());
            txtTiempo.setText(ejercicios.getTiempo());
        }

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

        Button btnEliminarDeBD = findViewById(R.id.bttEliminarBD);
        btnEliminarDeBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerEjerciciosActivity.this);
                builder.setMessage("¿Desea eliminar este ejercicio?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Verificar que dbEjercicios esté inicializado correctamente
                            if (dbEjercicios == null) {
                                // Si no está inicializado, muestra un mensaje de error o haz algo apropiado
                                Toast.makeText(VerEjerciciosActivity.this, "Error: BD no inicializada", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Intentar eliminar el ejercicio
                            if (dbEjercicios.eliminarEjercicio(id)) {
                                // Si la eliminación fue exitosa, navegar a la actividad PrincipalEjerciciosActivity
                                Intent intent = new Intent(VerEjerciciosActivity.this, PrincipalEjerciciosActivity.class);
                                startActivity(intent);
                                finish(); // Esto cierra la actividad actual
                            } else {
                                // Si hubo un error al eliminar, muestra un mensaje de error o haz algo apropiado
                                Toast.makeText(VerEjerciciosActivity.this, "Error al eliminar el ejercicio", Toast.LENGTH_SHORT).show();
                            }
                        }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(VerEjerciciosActivity.this, "No se borrará entonces", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

    }

    private void agregarAInicio() {
        // Recuperar la lista actual de IDs de recetas seleccionadas desde SharedPreferences
        SharedPreferences preferences = getSharedPreferences("EjerciciosSeleccionados", MODE_PRIVATE);
        Set<String> idEjerciciosSeleccionados = preferences.getStringSet("IDEjerciciosSeleccionados", new HashSet<>());

        // Verificar si el ID ya está presente para evitar duplicados
        if (!idEjerciciosSeleccionados.contains(String.valueOf(id))) {
            // Agregar el nuevo ID del ejercicio actual
            idEjerciciosSeleccionados.add(String.valueOf(id));

            // Guardar la lista actualizada en SharedPreferences
            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet("IDEjerciciosSeleccionados", idEjerciciosSeleccionados);
            editor.apply();

            // Muestra un mensaje o realiza alguna acción adicional si lo necesitas.
            Toast.makeText(this, "Ejercicio agregado a los seleccionados", Toast.LENGTH_SHORT).show();
        } else {
            // Muestra un mensaje indicando que el ejercicio ya está en la lista si lo deseas.
            Toast.makeText(this, "El ejercicio ya está en la lista", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarDeInicio() {
        // Crear un cuadro de diálogo de confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Está seguro de que desea eliminar este ejercicio de los seleccionados?");

        // Agregar botones al cuadro de diálogo
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Recuperar la lista actual de IDs de ejercicios seleccionados desde SharedPreferences
                SharedPreferences preferences = getSharedPreferences("EjerciciosSeleccionados", MODE_PRIVATE);
                Set<String> idEjerciciosSeleccionados = preferences.getStringSet("IDEjerciciosSeleccionados", new HashSet<>());

                // Eliminar el ID del ejercicio actual
                idEjerciciosSeleccionados.remove(String.valueOf(id));

                // Guardar la lista actualizada en SharedPreferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putStringSet("IDEjerciciosSeleccionados", idEjerciciosSeleccionados);
                editor.apply();

                // Mostrar un mensaje adicional
                Toast.makeText(getApplicationContext(), "Ejercicio eliminado de los seleccionados", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada si el usuario elige no eliminar el ejercicio
            }
        });

        // Mostrar el cuadro de diálogo
        builder.show();
    }
}