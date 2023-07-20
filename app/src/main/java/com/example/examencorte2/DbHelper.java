package com.example.examencorte2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sistema.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "productos";
    public static final String COLUMN_ID = "codigo";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_MARCA = "marca";
    public static final String COLUMN_PRECIO = "precio";
    public static final String COLUMN_TIPO = "tipo";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NOMBRE + " TEXT," +
                    COLUMN_MARCA + " TEXT," +
                    COLUMN_PRECIO + " TEXT," +
                    COLUMN_TIPO + " INTEGER)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
