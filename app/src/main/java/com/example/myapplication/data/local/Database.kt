package com.example.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.model.Todo

@Database(entities = [Todo::class], version = 3)
abstract class ToDoDatabase: RoomDatabase() {
	abstract fun toDoDao(): ToDoDao
}

object Database {
	private var db: ToDoDatabase? = null

	fun getDb(context: Context): ToDoDatabase {
		if (db == null) {
			db = Room
				.databaseBuilder(
					context, ToDoDatabase::class.java, "tp-sqlite"
				)
				//.addMigrations(MIGRATION_1_2, MIGRATION_2_3)
				.build()
		}
		return db!!
	}
}