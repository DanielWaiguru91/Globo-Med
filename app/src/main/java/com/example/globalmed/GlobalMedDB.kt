package com.example.globalmed

import android.provider.BaseColumns
import android.provider.BaseColumns._ID

object GlobalMedDB {



    object EmployeeEntry: BaseColumns{
        const val TABLE_NAME = "employee"
        const val COLUMN_ID = _ID
        const val COLUMN_NAME = "name"
        const val COLUMN_DOB = "dob"
        const val COLUMN_ROLE = "role"
        const val COLUMN_CITY = "city"

        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NAME TEXT NOT NULL, " +
                    "$COLUMN_DOB INTEGER  NOT NULL, " +
                    "$COLUMN_ROLE TEXT NOT NULL, " +
                    "$COLUMN_CITY TEXT NOT NULL)"
        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}