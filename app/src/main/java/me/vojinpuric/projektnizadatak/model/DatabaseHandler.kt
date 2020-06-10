package me.vojinpuric.projektnizadatak.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import me.vojinpuric.projektnizadatak.helpers.*


class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(kreirajTabeluProizvoda())
        predefinisaniProizvodi(db!!)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //
    }

    fun ocitajProizvode(): List<Proizvod> {
        val db = this.readableDatabase
        val lista = mutableListOf<Proizvod>()
        val selectQuery = "SELECT * FROM $TABLE_PROIZVODI"
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            return lista
        }
        if (cursor!!.moveToFirst()) {
            do {
                lista.add(
                    Proizvod(cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_NAZIV)),
                        cursor.getString(cursor.getColumnIndex(KEY_SLIKA)),
                        cursor.getInt(cursor.getColumnIndex(KEY_STANJE)),
                        cursor.getString(cursor.getColumnIndex(KEY_OPIS)),
                        cursor.getInt(cursor.getColumnIndex(KEY_ISPORUKA)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_CENA))
                    ))
            }while (cursor.moveToNext())
        }
        return lista.toList()
    }

    fun noviIdProizvoda(): Int {
        val db = this.readableDatabase
        val count = DatabaseUtils.queryNumEntries(db, TABLE_PROIZVODI)

        return count.toInt() + 1
    }

    fun dodajUBazu(proizvod: Proizvod,db:SQLiteDatabase = this.writableDatabase) {
        val values = ContentValues().apply {
            put(KEY_ID, proizvod.id)
            put(KEY_NAZIV, proizvod.naziv)
            put(KEY_CENA, proizvod.cena)
            put(KEY_STANJE, proizvod.stanje)
            put(KEY_OPIS, proizvod.opis)
            put(KEY_SLIKA, proizvod.slika)
            put(KEY_ISPORUKA, proizvod.isporuka)
        }

        db?.insert(TABLE_PROIZVODI, null, values)

    }

    private fun predefinisaniProizvodi(db:SQLiteDatabase) {
        dodajUBazu(
            Proizvod(
                1,
                "Dell P2719H IPS monitor",
                "https://www.gigatron.rs/img/products/medium/image5b854a2cdf309.png",
                3,
                "Dijagonala:27\"Tip panela:IPSRezolucija:1920 x 1080 Full HDVreme odziva:8msOdnos stranica:16 : 9",
                2,
                27.999
            ),db
        )
    }

    private fun kreirajTabeluProizvoda() = ("CREATE TABLE " + TABLE_PROIZVODI + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAZIV + " TEXT," + KEY_CENA + " REAL," + KEY_SLIKA + " TEXT," + KEY_ISPORUKA + " INTEGER," + KEY_STANJE + " INTEGER," + KEY_OPIS + " TEXT" + ")")

}
