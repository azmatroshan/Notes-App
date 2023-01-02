package com.example.notesapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_notes")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val topic: String,
    val notes: String
): Parcelable
