package com.example.globalmed

import android.content.ContentValues
import com.example.globalmed.GlobalMedDB.EmployeeEntry

object DataManager {
    fun getAllEmployees(databaseHelper: DatabaseHelper): ArrayList<Employee>{
        val employees = ArrayList<Employee>()

        val db = databaseHelper.readableDatabase
        val columns = arrayOf(
            EmployeeEntry.COLUMN_ID,
            EmployeeEntry.COLUMN_NAME,
            EmployeeEntry.COLUMN_DOB,
            EmployeeEntry.COLUMN_ROLE,
            EmployeeEntry.COLUMN_CITY
        )
        val cursor = db.query(
            EmployeeEntry.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )
        val idPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_ID)
        val namePos = cursor.getColumnIndex(EmployeeEntry.COLUMN_NAME)
        val dobPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DOB)
        val rolePos = cursor.getColumnIndex(EmployeeEntry.COLUMN_ROLE)
        val cityPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_CITY)

        while (cursor.moveToNext()){
            val id = cursor.getString(idPos)
            val name = cursor.getString(namePos)
            val dob = cursor.getLong(dobPos)
            val role = cursor.getString(rolePos)
            val city = cursor.getString(cityPos)

            employees.add(Employee(id, name, dob, role, city))
        }
        cursor.close()
        return employees
    }
    fun getEmployee(databaseHelper: DatabaseHelper, empId: String): Employee?{
        var employee: Employee? = null
        val columns = arrayOf(

            EmployeeEntry.COLUMN_NAME,
            EmployeeEntry.COLUMN_DOB,
            EmployeeEntry.COLUMN_ROLE,
            EmployeeEntry.COLUMN_CITY
        )
        val selection: String = EmployeeEntry.COLUMN_ID + " LIKE ? "
        val selectionArgs = arrayOf(empId)

        val db = databaseHelper.readableDatabase
        val cursor = db.query(
            EmployeeEntry.TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val namePos = cursor.getColumnIndex(EmployeeEntry.COLUMN_NAME)
        val dobPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DOB)
        val rolePos = cursor.getColumnIndex(EmployeeEntry.COLUMN_ROLE)
        val cityPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_CITY)

        while (cursor.moveToNext()){
            val name = cursor.getString(namePos)
            val dob = cursor.getLong(dobPos)
            val role = cursor.getString(rolePos)
            val city = cursor.getString(cityPos)

            employee = Employee(empId, name, dob, role, city)
        }
        cursor.close()
        return employee
    }

    fun updateEmployee(databaseHelper: DatabaseHelper, employee: Employee){
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put(EmployeeEntry.COLUMN_NAME, employee.name)
        values.put(EmployeeEntry.COLUMN_DOB, employee.dob)
        values.put(EmployeeEntry.COLUMN_ROLE, employee.role)
        values.put(EmployeeEntry.COLUMN_CITY, employee.city)

        val selection: String = EmployeeEntry.COLUMN_ID + " LIKE ? "
        val selectionArgs = arrayOf(employee.id)

        db.update(EmployeeEntry.TABLE_NAME,values, selection, selectionArgs)
    }
    fun deleteEmployee(databaseHelper: DatabaseHelper, empId: String): Int{
        val db = databaseHelper.writableDatabase
        var selection: String = EmployeeEntry.COLUMN_ID + " LIKE ? "
        val selectionArgs = arrayOf(empId)
        return db.delete(EmployeeEntry.TABLE_NAME, selection, selectionArgs)

    }
}