package com.kyuseon.movies

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context, dbName: String, version: Int) :
    SQLiteOpenHelper(context, dbName, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = """
            create table moviesTBL( title text primary key, image text , pubDate text , userRating double , link text)
            """.trimIndent()
        val query2 = """
            create table recentTBL( num integer primary key autoincrement , word text )
            """.trimIndent()
        db?.execSQL(query)
        db?.execSQL(query2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, newVersion: Int, oldVersion: Int) {
        val query = """
            drop table moviesTBL
        """.trimIndent()
        val query2 = """
            drop table recentTBL
        """.trimIndent()
        db?.execSQL(query)
        db?.execSQL(query2)
        this.onCreate(db)
    }

    fun insertMovies(moviesVO: MoviesVO):Boolean {
        var flag = false
        val query =
            """insert into moviesTBL( title , image , pubDate , userRating , link)
            values('${moviesVO.title}','${moviesVO.image}','${moviesVO.pubDate}','${moviesVO.userRating}','${moviesVO.link}')
        """.trimIndent()
        val db = this.writableDatabase

        try {
            db.execSQL(query)
            flag = true
        } catch (e: Exception) {
            Log.d("Movies", "DBHelper.insertMovies() ${e.printStackTrace()}")
            flag = false
        } finally {
            db.close()
        }
        return flag
    }

    fun selectMovies(): MutableList<MoviesVO>? {
        var moviesList: MutableList<MoviesVO>? = mutableListOf<MoviesVO>()
        var cursor: Cursor? = null
        val query = """
            select * from moviesTBL
        """.trimIndent()
        val db = this.readableDatabase

        try {
            cursor = db.rawQuery(query, null)
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val title = cursor.getString(0)
                    val image = cursor.getString(1)
                    val pubDate = cursor.getString(2)
                    val userRating = cursor.getDouble(3)
                    val link = cursor.getString(4)
                    val moviesVO = MoviesVO(title , image , pubDate , userRating , link)
                    moviesList?.add(moviesVO)
                }
            } else {
                moviesList = null
            }
        } catch (e: Exception) {
            Log.d("selectMovies", "selectMovies : $e")
            moviesList = null
        } finally {
            cursor?.close()
            db.close()
        }
        return moviesList
    }

    fun searchMovies(query: String?): MutableList<MoviesVO>? {
        var moviesList: MutableList<MoviesVO>? = mutableListOf<MoviesVO>()
        var cursor: Cursor? = null
        val query =
            """select * from moviesTBL where title like '%${query}%'""".trimIndent()
        val db = this.readableDatabase

        try {
            cursor = db.rawQuery(query, null)
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val title = cursor.getString(0)//서비스명
                    val image = cursor.getString(1) //대분류명
                    val pubDate = cursor.getString(2) //소분류명
                    val userRating = cursor.getDouble(3) //서비스상태
                    val link = cursor.getString(4)//결제방법,
                    val moviesVO = MoviesVO(title , image , pubDate , userRating , link)
                    moviesList?.add(moviesVO)
                }
            } else {
                moviesList = null
            }
        } catch (e: Exception) {
            Log.d("searchMovies", "searchMovies() ${e.printStackTrace()}")
            moviesList = null
        } finally {
            cursor?.close()
            db.close()
        }

        return moviesList
    }

    fun insertRecent(recentVO: RecentVO):Boolean {
        var flag = false
        val query =
            """insert into recentTBL(word)
            values('${recentVO.word}')
        """.trimIndent()
        val db = this.writableDatabase

        try {
            db.execSQL(query)
            flag = true
        } catch (e: Exception) {
            Log.d("Recent", "DBHelper.insertRecent() ${e.printStackTrace()}")
            flag = false
        } finally {
            db.close()
        }
        return flag
    }

    fun selectRecent(): MutableList<RecentVO>? {
        var recentList: MutableList<RecentVO>? = mutableListOf<RecentVO>()
        var cursor: Cursor? = null
        val query = """
            select * from recentTBL order by num desc limit 10
        """.trimIndent()
        val db = this.readableDatabase

        try {
            cursor = db.rawQuery(query, null)
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val word = cursor.getString(1)
                    val recentVO = RecentVO(word)
                    recentList?.add(recentVO)
                }
            } else {
                recentList = null
            }
        } catch (e: Exception) {
            Log.d("selectRecent", "selectRecent : $e")
            recentList = null
        } finally {
            cursor?.close()
            db.close()
        }
        return recentList
    }
}