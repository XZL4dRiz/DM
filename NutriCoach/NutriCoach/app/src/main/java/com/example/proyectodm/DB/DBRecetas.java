package com.example.proyectodm.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.proyectodm.entidades.Recetas;

import java.util.ArrayList;

public class DBRecetas extends DBHelper{

    Context context;

    public DBRecetas(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public ArrayList<Recetas> mostrarRecetas() {

        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        ArrayList<Recetas> listaRecetas = new ArrayList<>();
        Recetas recetas;
        Cursor cursorRecetas;

        cursorRecetas = db.rawQuery("SELECT * FROM " + TABLE_RECETAS + " ORDER BY nombre ASC", null);

        if (cursorRecetas.moveToFirst()) {
            do {
                recetas = new Recetas();
                recetas.setId(cursorRecetas.getInt(0));
                recetas.setNombre(cursorRecetas.getString(1));
                recetas.setDescripcion(cursorRecetas.getString(2));
                listaRecetas.add(recetas);
            } while (cursorRecetas.moveToNext());
        }

        cursorRecetas.close();

        return listaRecetas;
    }

    public Recetas verRecetas(int id) {

        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        Recetas recetas = null;
        Cursor cursorRecetas;

        cursorRecetas = db.rawQuery("SELECT * FROM " + TABLE_RECETAS + " WHERE id = " + id + " LIMIT 1", null);


        if (cursorRecetas.moveToFirst()) {
            recetas = new Recetas();
            recetas.setId(cursorRecetas.getInt(0));
            recetas.setNombre(cursorRecetas.getString(1));
            recetas.setDescripcion(cursorRecetas.getString(2));
        }

        cursorRecetas.close();

        return recetas;
    }

    public ArrayList<Recetas> verIngredientes(int id){
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        Recetas ingredientes = null;
        Cursor cursorContiene, cursorIngredientes;
        ArrayList<Recetas> listaIngredientes = new ArrayList<>();


        cursorContiene = db.rawQuery("SELECT * FROM " + TABLE_CONTIENE + " WHERE id_receta = " + id, null);


        if (cursorContiene.moveToFirst()) {
            do{
                ingredientes = new Recetas();
                int id_ingrediente = cursorContiene.getInt(1);
                cursorIngredientes = db.rawQuery("SELECT * FROM " + TABLE_INGREDIENTES + " WHERE id = " + id_ingrediente, null);
                if (cursorIngredientes.moveToFirst()){
                    ingredientes.setIngredientes(cursorContiene.getString(2).concat(" ").concat(cursorIngredientes.getString(1)));
                    listaIngredientes.add(ingredientes);
                }
                cursorIngredientes.close();
            }while (cursorContiene.moveToNext());
        }
        cursorContiene.close();

        return listaIngredientes;
    }

    public ArrayList<Recetas> mostrarRecetasPorCategoria(String categoria) {
        DBHelper DBHelper = new DBHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        ArrayList<Recetas> listaRecetas = new ArrayList<>();
        Recetas recetas;
        Cursor cursorRecetas;

        // Modificar la consulta para seleccionar recetas por categoría
        String query = "SELECT * FROM " + TABLE_RECETAS + " WHERE momentodia = ?";
        String[] selectionArgs = {categoria};
        cursorRecetas = db.rawQuery(query, selectionArgs);

        if (cursorRecetas.moveToFirst()) {
            do {
                recetas = new Recetas();
                recetas.setId(cursorRecetas.getInt(0));
                recetas.setNombre(cursorRecetas.getString(1));
                recetas.setDescripcion(cursorRecetas.getString(2));
                listaRecetas.add(recetas);
            } while (cursorRecetas.moveToNext());
        }

        cursorRecetas.close();

        return listaRecetas;
    }

}
