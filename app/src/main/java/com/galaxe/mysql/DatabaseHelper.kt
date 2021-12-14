package com.galaxe.mysql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.provider.ContactsContract

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private const val DATABASE_NAME = "SoylentGreen.db"
        private const val DATABASE_VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseInfo.SQL_CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DatabaseInfo.SQL_DELETE_TABLE_QUERY)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    //add, fetch, modify/update, delete
    fun addPerson(dbh: DatabaseHelper , name: String, age: Int ){
        //open database
        val db = dbh.writableDatabase
        //prepare values
        val values = ContentValues().apply{
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME, name)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_AGE, age)
        }
        //store values
        val rowID = db.insert(DatabaseInfo.TableInfo.TABLE_NAME, null, values)
        //close database
        db.close()
    }
    fun getAllItems(dbh: DatabaseHelper) : Cursor {
        val db = this.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseInfo.TableInfo.COLUMN_ITEM_NAME,
            DatabaseInfo.TableInfo.COLUMN_ITEM_AGE)
        val selection = ""
        val selectionArgs = null
        val sortOrder = null

        val cursor = db.query(DatabaseInfo.TableInfo.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
        return cursor

    }
    fun updateItem(dbh: DatabaseHelper, oldName: String, newName: String, oldAge: Int, newAge: Int){
        val db = dbh.writableDatabase

        val values = ContentValues().apply{
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME, newName)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_AGE, newAge)
        }

        val selection = "${DatabaseInfo.TableInfo.COLUMN_ITEM_NAME} LIKE ?"
        val selectionArgs = arrayOf(oldName)

        val count = db.update(DatabaseInfo.TableInfo.TABLE_NAME, values, selection, selectionArgs)
    }
    fun deleteItem(dbh: DatabaseHelper, name: String){
        val db = dbh.writableDatabase
        val selection = "${DatabaseInfo.TableInfo.COLUMN_ITEM_NAME} LIKE ?"
        val selectionArgs = arrayOf(name)

        val deletedRows = db.delete(DatabaseInfo.TableInfo.TABLE_NAME, selection, selectionArgs)
    }
    fun deleteAll(dbh: DatabaseHelper){
        val db = dbh.writableDatabase

        db.execSQL("delete from "+ DatabaseInfo.TableInfo.TABLE_NAME);
    }
    fun deleteEverything(db: SQLiteDatabase){
        db.execSQL(DatabaseInfo.SQL_DELETE_TABLE_QUERY)
    }

}