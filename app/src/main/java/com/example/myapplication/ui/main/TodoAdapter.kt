package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.R
import com.example.myapplication.model.Todo
import com.example.myapplication.ui.main.TodoAdapter.ItemViewHolder
import com.example.myapplication.databinding.TextRowItemBinding

class TodoAdapter(private val diffCallback: DiffUtil.ItemCallback<Todo>) :
    ListAdapter<Todo, ItemViewHolder>(diffCallback) {

    class ItemViewHolder(private val binding: TextRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox2)

        fun bind(text: String?) {
            textView.text = text
            checkBox.setOnClickListener {
                viewModel.toggleCompleted()
            }
            binding.viewHolder = this

            itemView.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            binding.hasPendingBindings()
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): ItemViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.text_row_item, parent, false)
                return ItemViewHolder(TextRowItemBinding.bind(view))
            }
        }
    }

    fun getItemAt(position: Int): Todo? {
        return getItem(position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.textView.text = item.text
    }

    class TodoClickListener(
        private val clickListener:
        ((task: Todo, viewHolder: TodoAdapter.ItemViewHolder, adapter: TodoAdapter) -> Unit)
    ) {
        fun onClick(
            task: Todo,
            viewHolder: TodoAdapter.ItemViewHolder,
            adapter: TodoAdapter
        ) = clickListener(task, viewHolder, adapter)
    }

    @BindingAdapter("checkedState")
    fun CheckBox.setChecked(todo: Todo?) {
        todo?.let {
            this.isChecked = it.completed
        }
    }
}
