package com.scasc.quiniela.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import com.scasc.quiniela.entidad.Logins;

import java.util.ArrayList;

public class DbLogin extends DbHelper {

    Context context;

    public DbLogin(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarToken(String token) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("token", token);

            id = db.insert(TABLE_LOGIN, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public void eliminarTokens() {
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.rawQuery("DELETE FROM logins",null);
        } catch (Exception ex) {
            ex.toString();
        }
    }

    public ArrayList<Logins> mostrarLogins() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Logins> listaLogins = new ArrayList<>();
        Logins logins;
        Cursor cursorLogins;

        cursorLogins = db.rawQuery(
                "SELECT * FROM " + TABLE_LOGIN +
                        " ORDER BY id DESC", null);

        if (cursorLogins.moveToFirst()) {
            do {
                logins = new Logins();
                logins.setId(cursorLogins.getInt(0));
                logins.setToken(cursorLogins.getString(1));
                listaLogins.add(logins);
            } while (cursorLogins.moveToNext());
        }

        cursorLogins.close();

        return listaLogins;
    }

    public Logins verLogin(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Logins login = null;
        Cursor cursorLogins;

        cursorLogins = db.rawQuery("SELECT * FROM " + TABLE_LOGIN
                + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorLogins.moveToFirst()) {
            login = new Logins();
            login.setId(cursorLogins.getInt(0));
            login.setToken(cursorLogins.getString(1));
        }

        cursorLogins.close();

        return login;
    }
}

