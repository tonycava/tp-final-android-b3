package com.example.myapplication.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.Utils
import com.example.myapplication.data.LocalTodoRepository
import com.example.myapplication.data.RemoteTodoRepository
import com.example.myapplication.data.local.Database
import com.example.myapplication.data.remote.FirebaseDatabase
import com.example.myapplication.databinding.AddTodoFragmentBinding
import com.example.myapplication.model.Todo

class AddTodoFragment : Fragment() {

	private var _binding: AddTodoFragmentBinding? = null
	private val binding get() = _binding!!

	private val viewModel by viewModels<AddTodoViewModel> {
		val database = Database.getDb(requireContext())
		val localTodoRepository = LocalTodoRepository(database)
		val remoteDb = FirebaseDatabase.getDatabase()
		val remoteTodoRepository = RemoteTodoRepository(remoteDb)
		AddTodoViewModelFactory(localTodoRepository, remoteTodoRepository)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		addBackListener()
		setupUI()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = DataBindingUtil.inflate(inflater, R.layout.add_todo_fragment, container, false)

		binding.lifecycleOwner = this
		binding.viewModel = viewModel

		return binding.root
	}

	private fun addBackListener() {
		binding.goBackBtn.setOnClickListener {
			Utils.showExitConfirmationDialog(
				requireContext(),
				acceptCallback = {
					parentFragmentManager.popBackStack()
				}
			)
		}

		val callback = object : OnBackPressedCallback(true) {
			override fun handleOnBackPressed() {
				Utils.showExitConfirmationDialog(
					requireContext(),
					acceptCallback = {
						findNavController().navigate(R.id.action_addToDoFragment_to_mainFragment)
					})
			}
		}

		requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
	}

	private fun setupUI() {
		val editText = binding.editText

		binding.addTodoButton.setOnClickListener {
			val todoText = editText.text.toString()

			if (todoText.isEmpty()) {
				Toast.makeText(requireContext(), "Todo can't be empty", Toast.LENGTH_SHORT).show()
				return@setOnClickListener
			}

			val newTodo = Todo(
				text = todoText,
			)

			viewModel.addTodo(newTodo)
			editText.text.clear()
			parentFragmentManager.popBackStack()
		}
	}
}