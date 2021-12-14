package com.galaxe.mysql

import android.provider.BaseColumns

object DatabaseInfo {
    const val SQL_CREATE_TABLE_QUERY = "CREATE TABLE ${TableInfo.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${TableInfo.COLUMN_ITEM_NAME} TEXT," +
            "${TableInfo.COLUMN_ITEM_AGE} INTEGER)"

    const val SQL_DELETE_TABLE_QUERY = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME}"

    object TableInfo: BaseColumns {
        const val TABLE_NAME = "peopleTable"
        const val COLUMN_ITEM_NAME = "name"
        const val COLUMN_ITEM_AGE = "age"
    }

}