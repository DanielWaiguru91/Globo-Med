package com.example.globalmed

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*

class AddEmployeeActivity : Activity() {

    private val myCalendar = Calendar.getInstance()
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        databaseHelper = DatabaseHelper(this)

        // on clicking ok on the calender dialog
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            etDOB.setText(getFormattedDate(myCalendar.timeInMillis))
        }

        etDOB.setOnClickListener {
            setUpCalender(date)
        }
        btnSave.setOnClickListener {
            saveEmployee()
        }
        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveEmployee() {
        var isValid = true
        etEmpName.error = if (etEmpName?.text.toString().isEmpty()){
            isValid = false
            "Name osRequired!"
        } else null
        etRole.error = if (etRole?.text.toString().isEmpty()){
            isValid = false
            "Role is Required!"
        }else null
        etCity.error = if (etCity?.text.toString().isEmpty()){
            isValid = false
            "Residence City is Required"
        }else null

        if (isValid){
            val name:String = etEmpName?.text.toString()
            val dob: Long = myCalendar.timeInMillis
            val role: String = etRole?.text.toString()
            val city: String = etCity?.text.toString()

            val db = databaseHelper.writableDatabase
            val values = ContentValues()
            values.put(GlobalMedDB.EmployeeEntry.COLUMN_NAME, name)
            values.put(GlobalMedDB.EmployeeEntry.COLUMN_DOB, dob)
            values.put(GlobalMedDB.EmployeeEntry.COLUMN_ROLE, role)
            values.put(GlobalMedDB.EmployeeEntry.COLUMN_CITY, city)

            val result = db.insert(GlobalMedDB.EmployeeEntry.TABLE_NAME, null, values)
            setResult(RESULT_OK, Intent())

            Toast.makeText(applicationContext, "Employee Added", Toast.LENGTH_LONG).show()
        }
        finish()
    }

    private fun setUpCalender(date: DatePickerDialog.OnDateSetListener) {

        DatePickerDialog(
            this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun getFormattedDate(dobInMilis: Long?): String {

        return dobInMilis?.let {
            val sdf = SimpleDateFormat("d MMM, yyyy", Locale.getDefault())
            sdf.format(dobInMilis)
        } ?: "Not Found"
    }
}
