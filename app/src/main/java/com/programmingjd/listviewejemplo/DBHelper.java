package com.programmingjd.listviewejemplo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.programmingjd.listviewejemplo.model.Carro;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "car.db";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE car (name TEXT,cylinderCapacity TEXT,model TEXT,value TEXT,image TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists car");
        onCreate(db);
    }

    public void insertCity(SQLiteDatabase db, Carro car){
        ContentValues values = new ContentValues();

        //values.put("id", (byte[]) null);
        values.put("name",car.getName());
        values.put("cylinderCapacity",car.getCylinderCapacity());
        values.put("model",car.getModel());
        values.put("value",car.getValue());
        values.put("image",car.getImage());

        db.insert("car",null,values);
    }

    public ArrayList<Carro> selectCar (SQLiteDatabase db){
        ArrayList<Carro> cars = new ArrayList<>();
        Cursor filas = db.rawQuery("select * from car",null);

        if (filas.moveToFirst()){
            do{
                Carro car = new Carro(filas.getString(1),filas.getString(2),filas.getString(3),
                        filas.getString(4),filas.getString(5));
                cars.add(car);
            }while (filas.moveToNext());
        }

        return cars;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from car");
    }
}
