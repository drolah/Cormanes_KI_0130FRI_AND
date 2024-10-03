package com.example.bottomnavigation

data class Profile(
    var name: String,
    var email: String,
    var gender: String,
    var student: Boolean,
    var single: Boolean
) {
    override fun toString(): String {
        return "$name, $email, $gender, Student: $student, Single: $single"
    }
}
