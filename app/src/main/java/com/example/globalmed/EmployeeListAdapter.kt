package com.example.globalmed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class EmployeeListAdapter (private val context: Context) : RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder>(){
    lateinit var employeeList :ArrayList<Employee>
    var TAG = EmployeeListAdapter::class.java.name

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return EmployeeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employeeList[position]
        holder.setData(employee.name, employee.role, position)
        holder.setListener()
    }

    fun setEmployees(allEmployees: ArrayList<Employee>) {
        employeeList = allEmployees
        notifyDataSetChanged()
    }

    inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var pos = 0;
        fun setData(name: String, role: String, pos: Int) {
            itemView.txtFullName.text = name
            itemView.txtRole.text = role
            this.pos = pos
        }

        fun setListener() {
            itemView.setOnClickListener{
                val intent = Intent(context, UpdateEmployeeActivity::class.java)
                intent.putExtra(GlobalMedDB.EmployeeEntry.COLUMN_ID, employeeList[pos].id)
                (context as Activity).startActivityForResult(intent, 2)
            }
        }

    }

}