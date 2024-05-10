package com.example.claseaplicacionesmovilessqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MiDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mi_base_de_datos";
    private static final int DATABASE_VERSION = 1;

    public MiDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla
        db.execSQL("CREATE TABLE mi_tabla (_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, edad INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizar la base de datos si es necesario
        db.execSQL("DROP TABLE IF EXISTS mi_tabla");
        onCreate(db);
    }
}

