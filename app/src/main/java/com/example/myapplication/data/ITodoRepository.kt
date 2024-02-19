package com.example.myapplication.data

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.Todo
import kotlinx.coroutines.CoroutineScope

interface ITodoRepository {
	val todos: MutableLiveData<List<Todo>>
	suspend fun getTodos(): List<Todo>
	suspend fun addTodo(todo: Todo): Todo
	fun refresh()
	suspend fun getTodo(id: Long): Todo?
	fun removeTodo(todo: Todo): Todo
	fun completeTodo(todo: Todo): Todo
	fun updateTodo(newTodo: Todo): Todo
}