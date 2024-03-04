package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.ITodoRepository
import com.example.myapplication.model.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val localRepository: ITodoRepository, private val remoteRepository: ITodoRepository) : ViewModel() {
	private val viewModelJob = Job()
	private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

	val todos = remoteRepository.todos

	init {
		fragmentRefresh()
	}

	fun fragmentRefresh() {
		ioScope.launch {
			localRepository.refresh()
			remoteRepository.refresh()
		}
	}

	fun toggleCompleted(todo: Todo) {
		todo.completed = !todo.completed
		ioScope.launch {
			remoteRepository.completeTodo(todo)
			localRepository.updateTodo(todo)
		}
	}

	fun deleteTodo(todo: Todo): Todo {
		ioScope.launch {
			localRepository.removeTodo(todo)
			remoteRepository.removeTodo(todo)
		}
		return todo
	}

	override fun onCleared() {
		super.onCleared()
		viewModelJob.cancel()
	}

}