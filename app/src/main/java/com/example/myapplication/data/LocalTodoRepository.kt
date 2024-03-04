package com.example.myapplication.data

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.local.ToDoDatabase
import com.example.myapplication.model.Todo
import kotlinx.coroutines.CoroutineScope

class LocalTodoRepository(private val localDS: ToDoDatabase) : ITodoRepository {

	override val todos: MutableLiveData<List<Todo>> = MutableLiveData(mutableListOf())

	override fun refresh() {
		val local = localDS.toDoDao().getAll()
		todos.postValue(local.sortedByDescending { filter -> filter.createdAt })
	}

	override suspend fun getTodos(): List<Todo> {
		return localDS.toDoDao().getAll()
	}

	override suspend fun addTodo(todo: Todo): Todo {
		localDS.toDoDao().insert(todo)
		return todo
	}

	override suspend fun getTodo(id: Long): Todo? {
		return localDS.toDoDao().get(id)
	}

	override fun removeTodo(todo: Todo): Todo {
		localDS.toDoDao().delete(todo)
		return todo
	}

	override fun completeTodo(todo: Todo): Todo {
		localDS.toDoDao().update(todo)
		return todo
	}

	override fun updateTodo(newTodo: Todo): Todo {
		localDS.toDoDao().update(newTodo)
		return newTodo
	}
}