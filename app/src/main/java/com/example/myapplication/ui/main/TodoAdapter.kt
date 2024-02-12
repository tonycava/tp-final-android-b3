package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Todo

class TodoAdapter(private var itemList: List<Todo>) : RecyclerView.Adapter<TodoAdapter.ItemViewHolder>() {

	class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val textView: TextView = itemView.findViewById(R.id.textView)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.text_row_item, parent, false)
		return ItemViewHolder(view)
	}

	override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
		val item = itemList[position]
		holder.textView.text = item.text
	}

	override fun getItemCount(): Int {
		return itemList.size
	}

	fun updateData(newItemList: List<Todo>) {
		itemList = newItemList
		notifyDataSetChanged()
	}
}
