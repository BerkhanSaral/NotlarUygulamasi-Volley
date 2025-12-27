package com.info.notlaruygulamasi

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper

class VeritabaniYardimcisi(context: Context) :
    SQLiteOpenHelper(context, "notlar.sqlite", null, 1) {


    override fun onCreate(db: android.database.sqlite.SQLiteDatabase?) {

        db?.execSQL("create table notlar (not_id integer primary key autoincrement, ders_adi text , not1 integer, not2 integer) ")
    }

    override fun onUpgrade(
        db: android.database.sqlite.SQLiteDatabase?,
        p1: kotlin.Int,
        p2: kotlin.Int
    ) {
        db?.execSQL("drop table if exists notlar")
        onCreate(db)

    }
}