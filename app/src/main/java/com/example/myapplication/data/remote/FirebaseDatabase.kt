package com.example.myapplication.data.remote

import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


object FirebaseDatabase {
    private lateinit var database: DatabaseReference

    fun getDatabase(): DatabaseReference {
        if (!::database.isInitialized) {
            database = Firebase.database.reference
        }
        return database
    }
}
