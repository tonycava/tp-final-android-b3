package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.util.UUID

@IgnoreExtraProperties
@Entity(tableName = "todo")
data class Todo(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	var id: Long? = UUID.randomUUID().leastSignificantBits,

	@ColumnInfo(name = "text")
	var text: String,

	@ColumnInfo(name = "completed", defaultValue = "0")
	var completed: Boolean = false,

	@ColumnInfo(name = "created_at")
	var createdAt: Long = System.currentTimeMillis()
) {
	@Exclude
	fun toMap(): Map<String, Any?> {
		return mapOf(
			"id" to id,
			"text" to text,
			"completed" to completed,
			"createdAt" to createdAt
		)
	}
}
