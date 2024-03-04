package com.example.myapplication.ui.main

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper

abstract class SwipeToDeleteCallback() :
	ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
	override fun onMove(
		recyclerView: androidx.recyclerview.widget.RecyclerView,
		viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
		target: androidx.recyclerview.widget.RecyclerView.ViewHolder
	): Boolean {
		return false
	}

	override fun onSwiped(
		viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
		direction: Int
	) {
		onSwiped(viewHolder, direction, viewHolder.adapterPosition)
	}

	abstract fun onSwiped(
		viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
		direction: Int,
		position: Int
	)
}