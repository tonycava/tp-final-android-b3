package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.ITodoRepository

class MainViewModelFactory(private val todoRepository: ITodoRepository) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
			return MainViewModel(todoRepository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}
