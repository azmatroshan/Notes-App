package com.example.notesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_notes")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val topic: String,
    val notes: String
)
