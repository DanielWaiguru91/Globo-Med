package com.example.globalmed

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateEmployeeActivity: AppCompatActivity() {

    lateinit var databaseHelper : DatabaseHelper
    private val myCalendar = Calendar.getInstance()
    var empId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        databaseHelper = DatabaseHelper(this)
        val bundle = intent.extras
        bundle?.let {
            empId = bundle.getString(GlobalMedDB.EmployeeEntry.COLUMN_ID)
            var employee = DataManager.getEmployee(databaseHelper, empId!!)

            employee?.let {
                etEmpName.setText(employee.name)
                etDOB.setText(getFormattedDate(employee.dob))
                etRole.setText(employee.role)
                etCity.setText(employee.city)
            }
        }

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

        if (isValid) {
            val updatedName = etEmpName.text.toString()
            val updatedDOB = myCalendar.timeInMillis
            val updatedRole = etRole.text.toString()
            val updatedCity = etCity.text.toString()

            val updatedEmployee = Employee(empId!!, updatedName, updatedDOB, updatedRole, updatedCity)
            DataManager.updateEmployee(databaseHelper, updatedEmployee)

            setResult(Activity.RESULT_OK, Intent())
            Toast.makeText(applicationContext, "Employee updated", Toast.LENGTH_SHORT).show()
            finish()

        }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_delete ->{
                val builder = AlertDialog.Builder(this)
                builder.setMessage(R.string.confirm_sure)
                    .setPositiveButton(R.string.yes){
                        dialog,
                        eId ->
                        val result = DataManager.deleteEmployee(databaseHelper, empId.toString())
                        Toast.makeText(applicationContext, "$result deleted", Toast.LENGTH_LONG).show()
                        setResult(Activity.RESULT_OK, Intent())
                        finish()
                    }
                    .setNegativeButton(R.string.no){
                        dialog,
                            id -> dialog.dismiss()
                    }
                val dialog = builder.create()
                dialog.setTitle("Confirm Deletion?")
                dialog.show()

                true
            }
            else -> return  onOptionsItemSelected(item)
        }
    }
}