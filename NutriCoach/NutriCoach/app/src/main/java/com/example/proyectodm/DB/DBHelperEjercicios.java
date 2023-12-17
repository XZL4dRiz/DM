package com.example.proyectodm.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperEjercicios extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_EJERCICIOS = "ejercicio.db";
    public static final String TABLE_EJER = "t_ejer";

    SQLiteDatabase bdEjercicios = getWritableDatabase();
    public DBHelperEjercicios(@Nullable Context context) {
        super(context, DATABASE_EJERCICIOS, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_EJER + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "descripcion TEXT," +
                "tiempo INTEGER" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_EJER);

        onCreate(sqLiteDatabase);
    }
}
