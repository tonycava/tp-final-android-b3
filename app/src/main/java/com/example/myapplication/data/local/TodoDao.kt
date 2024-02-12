package com.example.myapplication.data.local

import androidx.room.*
import com.example.myapplication.model.Todo

@Dao
interface ToDoDao {
	@Query("SELECT * FROM todo ORDER BY created_at DESC")
	fun getAll(): List<Todo>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(todo: Todo)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(vararg todo: Todo)

	@Update
	fun update(vararg todo: Todo)

	@Delete
	fun delete(todo: Todo)

	@Query("DELETE FROM todo")
	fun deleteAll()
}