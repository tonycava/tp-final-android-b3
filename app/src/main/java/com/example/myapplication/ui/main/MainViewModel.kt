package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.ITodoRepository
import com.example.myapplication.model.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
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

	fun toggleCompleted(todo: Todo): Boolean {
		todo.completed = !todo.completed
		ioScope.launch {
			remoteRepository.completeTodo(todo)
			localRepository.updateTodo(todo)
		}
		return todo.completed
	}

	fun deleteTodo(todo: Todo) {
		ioScope.launch {
			localRepository.removeTodo(todo)
			remoteRepository.removeTodo(todo)
		}
	}

	override fun onCleared() {
		super.onCleared()
		viewModelJob.cancel()
	}

}