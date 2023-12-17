package com.example.proyectodm.DB;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.proyectodm.entidades.Ejercicios;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class DBEjercicios extends DBHelperEjercicios {

    Context context;

    public DBEjercicios(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public ArrayList<Ejercicios> mostrarEjercicios() {

        DBHelperEjercicios DBHelperEjercicios = new DBHelperEjercicios(context);
        SQLiteDatabase dbEjercicios = DBHelperEjercicios.getWritableDatabase();

        ArrayList<Ejercicios> listaEjercicios = new ArrayList<>();
        Ejercicios ejercicios;
        Cursor cursorEjercicios;

        cursorEjercicios = dbEjercicios.rawQuery("SELECT * FROM " + TABLE_EJER + " ORDER BY nombre ASC", null);

        if (cursorEjercicios.moveToFirst()) {
            do {
                ejercicios = new Ejercicios();
                ejercicios.setId(cursorEjercicios.getInt(0));
                ejercicios.setNombre(cursorEjercicios.getString(1));
                ejercicios.setDescripcion(cursorEjercicios.getString(2));
                ejercicios.setTiempo(cursorEjercicios.getString(3));
                listaEjercicios.add(ejercicios);
            } while (cursorEjercicios.moveToNext());
        }

        cursorEjercicios.close();

        return listaEjercicios;
    }

    public Ejercicios verEjercicios(int id) {

        DBHelperEjercicios DBHelperEjercicios = new DBHelperEjercicios(context);
        SQLiteDatabase dbEjercicios = DBHelperEjercicios.getWritableDatabase();

        Ejercicios ejercicios = null;
        Cursor cursorEjercicios;

        cursorEjercicios = dbEjercicios.rawQuery("SELECT * FROM " + TABLE_EJER + " WHERE id = " + id + " LIMIT 1", null);


        if (cursorEjercicios.moveToFirst()) {
            ejercicios = new Ejercicios();
            ejercicios.setId(cursorEjercicios.getInt(0));
            ejercicios.setNombre(cursorEjercicios.getString(1));
            ejercicios.setDescripcion(cursorEjercicios.getString(2));
            ejercicios.setTiempo(cursorEjercicios.getString(3).concat(" ").concat("minutos."));
        }

        cursorEjercicios.close();

        return ejercicios;
    }

    public long insertarEjercicio(String nombre, String descripcion, String tiempo){
        long id = 0;
        try{
            DBHelperEjercicios dbHelperEjercicios = new DBHelperEjercicios(context);
            SQLiteDatabase db = dbHelperEjercicios.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("descripcion", descripcion);
            values.put("tiempo", tiempo);

            id = db.insert(TABLE_EJER, null, values);
        } catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public boolean eliminarEjercicio(int id){
        boolean correcto = false;
        DBHelperEjercicios dbHelperEjercicios = new DBHelperEjercicios(context);
        SQLiteDatabase db = dbHelperEjercicios.getWritableDatabase();

        try{
            db.execSQL(" DELETE FROM " + TABLE_EJER + " WHERE id = " + id);
            correcto = true;

            // Eliminar el ID del conjunto almacenado en SharedPreferences
            SharedPreferences preferences = context.getSharedPreferences("EjerciciosSeleccionados", Context.MODE_PRIVATE);
            Set<String> idEjerciciosSeleccionados = preferences.getStringSet("IDEjerciciosSeleccionados", new HashSet<>());

            // Verificar si el ID está en la lista antes de intentar eliminarlo
            if (idEjerciciosSeleccionados.contains(String.valueOf(id))) {
                idEjerciciosSeleccionados.remove(String.valueOf(id));

                // Guardar los cambios
                SharedPreferences.Editor editor = preferences.edit();
                editor.putStringSet("IDEjerciciosSeleccionados", idEjerciciosSeleccionados);
                editor.apply();
            }
        } catch (Exception ex){
            ex.toString();
        } finally {
            db.close();
        }
        return correcto;
    }
}
