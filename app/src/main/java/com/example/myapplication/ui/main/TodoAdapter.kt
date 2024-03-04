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

class TodoAdapter(
    private val diffCallback: DiffUtil.ItemCallback<Todo>,
    private val viewModel: MainViewModel
) :
    ListAdapter<Todo, ItemViewHolder>(diffCallback) {

    class ItemViewHolder(
        private val binding: TextRowItemBinding,
        private val viewModel: MainViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?, todo: Todo?) {
            textView.text = text
            binding.todo = todo
            binding.checkBox2.setOnCheckedChangeListener { _, _ ->
                if (todo != null) {
                    viewModel.toggleCompleted(todo)
                }
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
            fun create(parent: ViewGroup, viewModel: MainViewModel): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TextRowItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding, viewModel)
            }
        }
    }

    fun getItemAt(position: Int): Todo? {
        return getItem(position)
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

    companion object {
        @JvmStatic
        @BindingAdapter("checkedState")
        fun CheckBox.setChecked(todo: Todo?) {
            todo?.let {
                this.isChecked = it.completed
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent, viewModel)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.text, current)
    }
}
