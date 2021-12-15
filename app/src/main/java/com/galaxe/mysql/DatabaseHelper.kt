package com.galaxe.mysql

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.provider.ContactsContract
import android.widget.TextView

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


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
    fun addPerson(name: String, age: Int ){

        val dbWrite = this.writableDatabase
        //prepare values
        val values = ContentValues().apply{
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME, name)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_AGE, age)
        }
        //store values
        val rowID = dbWrite.insert(DatabaseInfo.TableInfo.TABLE_NAME, null, values)
        //close database

    }
    @SuppressLint("Range")
    fun getAllItems(Name: TextView, Age: TextView){
        val dbRead = this.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseInfo.TableInfo.COLUMN_ITEM_NAME,
            DatabaseInfo.TableInfo.COLUMN_ITEM_AGE)
        val selection = ""
        val selectionArgs = null
        val sortOrder = null

        val cursor = dbRead.query(DatabaseInfo.TableInfo.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)

        Name.text = "Name\n\n"
        Age.text = "Age\n\n"

        with(cursor){
            while(moveToNext() ){
                val itemName = getString(getColumnIndex(com.galaxe.mysql.DatabaseInfo.TableInfo.COLUMN_ITEM_NAME))
                val itemAge = getInt(getColumnIndex(com.galaxe.mysql.DatabaseInfo.TableInfo.COLUMN_ITEM_AGE))
                Name.append(itemName+"\n")
                Age.append(itemAge.toString()+"\n")
            }
        }

        cursor.close()
        dbRead.close()

    }
    fun updateItem(oldName: String, newName: String, oldAge: Int, newAge: Int){

        val dbWrite = this.writableDatabase

        val values = ContentValues().apply{
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME, newName)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_AGE, newAge)
        }

        val selection = "${DatabaseInfo.TableInfo.COLUMN_ITEM_NAME} LIKE ?"
        val selectionArgs = arrayOf(oldName)

        val count = dbWrite.update(DatabaseInfo.TableInfo.TABLE_NAME, values, selection, selectionArgs)

    }
    fun deleteItem(name: String){

        val dbWrite = this.writableDatabase
        val selection = "${DatabaseInfo.TableInfo.COLUMN_ITEM_NAME} LIKE ?"
        val selectionArgs = arrayOf(name)

        val deletedRows = dbWrite.delete(DatabaseInfo.TableInfo.TABLE_NAME, selection, selectionArgs)

    }
    fun deleteAll(){

        val dbWrite = this.writableDatabase
        dbWrite.execSQL("delete from "+ DatabaseInfo.TableInfo.TABLE_NAME);
        //db.delete(DatabaseInfo.TableInfo.TABLE_NAME, null, null)

    }
    fun deleteEverything(){
        val dbWrite = this.writableDatabase
        dbWrite.execSQL(DatabaseInfo.SQL_DELETE_TABLE_QUERY)
    }

}