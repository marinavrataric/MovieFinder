package com.example.moviefinder.favorites

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "MyDB"
val TABLE_NAME = "Movies"
val COL_NAME = "title"
val COL_DATE = "releaseDate"
val COL_ID = "id"
val COL_IMAGE = "image"
val COL_USER_ID = "userid"

class DataBaseHandler (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, 1){

    private val sharedPreferences = context.getSharedPreferences("userId", Context.MODE_PRIVATE)

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER," +
                COL_NAME + " VARCHAR(256)," +
                COL_DATE + " STRING," +
                COL_IMAGE + " STRING," +
                COL_USER_ID + " STRING, " +
                " PRIMARY KEY ($COL_USER_ID, $COL_ID) " +
                ")"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun insertData(movieFav: Movie_Fav){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_ID, movieFav.id)
        cv.put(COL_NAME, movieFav.titleFav)
        cv.put(COL_DATE, movieFav.releaseFav)
        cv.put(COL_IMAGE, movieFav.posterPathFav)
        cv.put(COL_USER_ID, sharedPreferences.getString("userId", "unknown"))
        val result = db.insert(TABLE_NAME, null, cv)

        if(result == -1.toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }

    fun readData() : MutableList<Movie_Fav>{
        val list : MutableList<Movie_Fav> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME + " WHERE $COL_USER_ID LIKE \"${sharedPreferences.getString("userId", "unknown")}\""
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                val movieFav = Movie_Fav()
                movieFav.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                movieFav.titleFav = result.getString(result.getColumnIndex(COL_NAME)).toString()
                movieFav.releaseFav = result.getString(result.getColumnIndex(COL_DATE)).toString()
                movieFav.posterPathFav = result.getString(result.getColumnIndex(COL_IMAGE)).toString()
                list.add(movieFav)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun readDataById(movieId: Int) : Movie_Fav? {
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME WHERE $COL_ID = $movieId AND $COL_USER_ID = " + "\"${sharedPreferences.getString("userId", "unknown")}\" LIMIT 1"
        val dbResult = db.rawQuery(query,null)
        val movieFav = Movie_Fav()
        if (dbResult.moveToFirst()) {
            movieFav.id = dbResult.getString(dbResult.getColumnIndex(COL_ID)).toInt()
            movieFav.titleFav = dbResult.getString(dbResult.getColumnIndex(COL_NAME)).toString()
            movieFav.releaseFav = dbResult.getString(dbResult.getColumnIndex(COL_DATE)).toString()
            movieFav.posterPathFav = dbResult.getString(dbResult.getColumnIndex(COL_IMAGE)).toString()
            println(dbResult.getString(dbResult.getColumnIndex(COL_USER_ID)).toString())
        }
        dbResult.close()
        db.close()
        return if(movieFav.id == 0)
            null
        else
            movieFav
    }

    fun deleteData(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COL_ID=$id AND $COL_USER_ID=\"${sharedPreferences.getString("userId", "unknown")}\"", null)
        db.close()
    }
}












