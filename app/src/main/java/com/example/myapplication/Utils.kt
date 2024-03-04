package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import com.example.myapplication.model.Todo

class Utils {
	companion object {

		fun getPaintFlags(todo: Todo): Int {
			return if (todo.completed) {
				Paint.STRIKE_THRU_TEXT_FLAG
			} else {
				0
			}
		}

		inline fun showExitConfirmationDialog(
			context: Context,
			crossinline acceptCallback: () -> Unit = {},
			crossinline cancelCallback: () -> Unit = {}
		) {
			AlertDialog.Builder(context)
				.setTitle("Confirmation")
				.setMessage("Voulez-vous vraiment anuler la création de la tâche ?")
				.setPositiveButton("Oui") { _, _ ->
					acceptCallback()
				}
				.setNegativeButton("Non") { dialog, _ ->
					dialog.dismiss()
					cancelCallback()
				}
				.show()
		}
	}
}