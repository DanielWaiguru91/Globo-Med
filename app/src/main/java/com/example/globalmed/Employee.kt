package com.example.globalmed

class Employee(
    val id: String,
    val name: String,
    val dob: Long,
    val role: String,
    val city: String
){
    override fun toString(): String {
        return "id: $id, name: $name, dob: $dob, role: $role, city: $city"
    }
}