package com.example.proyectodm.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "dieta.db";
    public static final String TABLE_RECETAS = "t_recetas";
    public static final String TABLE_INGREDIENTES = "t_ingredientes";
    public static final String TABLE_CONTIENE = "t_contiene";
    SQLiteDatabase bd = getWritableDatabase();
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_RECETAS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "descripcion TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_INGREDIENTES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CONTIENE + "(" +
                "id_receta INTEGER NOT NULL," +
                "id_ingrediente INTEGER NOT NULL," +
                "cantidad TEXT," +
                "PRIMARY KEY (id_receta, id_ingrediente)," +
                "FOREIGN KEY (id_receta) REFERENCES TABLE_RECETAS(id)," +
                "FOREIGN KEY (id_ingrediente) REFERENCES TABLE_INGREDIENTES(id)" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_RECETAS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_INGREDIENTES);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CONTIENE);

        onCreate(sqLiteDatabase);
    }
}
