package com.example.myapplication.ui.add

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.ITodoRepository
import com.example.myapplication.model.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddTodoViewModel(private val todoRepository: ITodoRepository) : ViewModel() {
	private val viewModelJob = Job()
	private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

	fun addTodo(todo: Todo) {
		ioScope.launch {
			todoRepository.addTodo(todo)
		}
	}

	override fun onCleared() {
		super.onCleared()
		viewModelJob.cancel()
	}
}