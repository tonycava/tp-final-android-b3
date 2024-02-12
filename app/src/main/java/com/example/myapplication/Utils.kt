package com.example.myapplication

import android.app.AlertDialog
import android.content.Context

class Utils {
	companion object {
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