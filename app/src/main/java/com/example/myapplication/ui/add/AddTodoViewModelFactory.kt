package com.example.myapplication.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.ITodoRepository

class AddTodoViewModelFactory(private val localRepository: ITodoRepository, private val remoteRepository: ITodoRepository) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(AddTodoViewModel::class.java)) {
			return AddTodoViewModel(localRepository, remoteRepository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}
