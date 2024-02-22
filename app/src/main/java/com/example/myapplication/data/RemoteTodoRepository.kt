package com.example.myapplication.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.Todo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
class RemoteTodoRepository(private val remoteDB: DatabaseReference) : ITodoRepository {
    private val database = remoteDB
    private val todosReference = database.child("todos")
    override val todos: MutableLiveData<List<Todo>> = MutableLiveData(mutableListOf())

    override suspend fun getTodos(): List<Todo> {
        val todos = todosReference.get().await()
        return todos.getValue<List<Todo>>() ?: emptyList()
    }

    override suspend fun addTodo(todo: Todo): Todo {
        database.child("todos").child(todo.id.toString()).setValue(todo)
        return todo
    }

    override fun refresh() {
        database.get().addOnSuccessListener {dataSnapshot ->
            if (dataSnapshot.exists()) {
                val data = dataSnapshot.value as HashMap<*, *>
                val todosList = data.values.mapNotNull {item ->
                    (item as? HashMap<*, *>)?.let {todo ->
                        Todo(
                            todo["id"] as Long,
                            todo["text"] as String,
                            todo["completed"] as Boolean,
                            todo["createdAt"] as Long
                        )
                    }
                }.toList().sortedByDescending { it.createdAt }
                todos.postValue(todosList)
            } else {
                Log.d(TAG, "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }
    }

    override suspend fun getTodo(id: Long): Todo? {
        val todo = todosReference.child(id.toString()).get().await()
        return todo.getValue<Todo>()
    }

    override fun removeTodo(todo: Todo): Todo {
        todosReference.child(todo.id.toString()).removeValue()
        return todo
    }

    override fun completeTodo(todo: Todo): Todo {
        val newTodo = todo.copy(completed = true)
        todosReference.child(todo.id.toString()).setValue(newTodo)
        return newTodo
    }

    override fun updateTodo(newTodo: Todo): Todo {
        todosReference.child(newTodo.id.toString()).setValue(newTodo)
        return newTodo
    }

}