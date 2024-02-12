package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.ITodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val todoRepository: ITodoRepository) : ViewModel() {
	private val viewModelJob = Job()
	private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

	val todos = todoRepository.todos

	init {
		ioScope.launch {
			todoRepository.refresh(ioScope)
		}
	}

	override fun onCleared() {
		super.onCleared()
		viewModelJob.cancel()
	}

}