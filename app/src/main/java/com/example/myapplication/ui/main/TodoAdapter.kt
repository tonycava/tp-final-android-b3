package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.R
import com.example.myapplication.model.Todo
import com.example.myapplication.ui.main.TodoAdapter.ItemViewHolder

class TodoAdapter(private val diffCallback: DiffUtil.ItemCallback<Todo>) : ListAdapter<Todo, ItemViewHolder>(diffCallback) {

	class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val textView: TextView = itemView.findViewById(R.id.textView)

		fun bind(text: String?) {
			textView.text = text
		}

		companion object {
			fun create(parent: ViewGroup): ItemViewHolder {
				val view: View = LayoutInflater.from(parent.context)
					.inflate(R.layout.text_row_item, parent, false)
				return ItemViewHolder(view)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
		return ItemViewHolder.create(parent)
	}

	override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
		val item = getItem(position)
		holder.bind(item.text)
	}
}
