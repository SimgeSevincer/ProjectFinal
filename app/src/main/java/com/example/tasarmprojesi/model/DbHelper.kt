package com.example.tasarmprojesi.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "articles.db"
        private const val TABLE_ARTICLES = "articles"

        private const val KEY_ID = "id"
        private const val KEY_CATEGORY = "category"
        private const val KEY_IMAGE = "image"
        private const val KEY_NAME = "name"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DATE = "date"
        private const val KEY_FAV_COUNT = "fav_count"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_ARTICLES_TABLE = ("CREATE TABLE $TABLE_ARTICLES("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_CATEGORY TEXT,"
                + "$KEY_IMAGE TEXT,"
                + "$KEY_NAME TEXT,"
                + "$KEY_DESCRIPTION TEXT,"
                + "$KEY_DATE TEXT,"
                + "$KEY_FAV_COUNT INTEGER"
                + ")")
        db?.execSQL(CREATE_ARTICLES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ARTICLES")
        onCreate(db)
    }

    fun addArticle(article: Article) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ID, article.makaleID)
        values.put(KEY_CATEGORY, article.makaleCategory)
        values.put(KEY_IMAGE, article.makaleImage)
        values.put(KEY_NAME, article.makaleName)
        values.put(KEY_DESCRIPTION, article.makaleDescription)
        values.put(KEY_DATE, article.makaleDate)
        values.put(KEY_FAV_COUNT, article.makaleFavCount)
        val result=db.insert(TABLE_ARTICLES, null, values)
        if (result == -1L) {
            Log.e("DbHelper", "Makale eklenirken bir hata oluştu.")
        } else {
            Log.d("DbHelper", "Makale başarıyla eklendi. ID: $result")
        }

        db.close()
    }
    fun getAllArticles(): List<Article> {
        val articleList = mutableListOf<Article>()
        val selectQuery = "SELECT * FROM $TABLE_ARTICLES"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        while (cursor.moveToNext()) {
            val article = Article(
                cursor.getString(0), // ID sütunu
                cursor.getString(1), // Category sütunu
                cursor.getString(2), // Image sütunu
                cursor.getString(3), // Name sütunu
                cursor.getString(4), // Description sütunu
                cursor.getString(5), // Date sütunu
                cursor.getInt(6)     // FavCount sütunu
            )
            articleList.add(article)
        }

        cursor.close()
        db.close()
        // Log mesajı ekle
        Log.d("DbHelper", "Veritabanından alınan makale sayısı: ${articleList.size}")
        return articleList
    }

}
