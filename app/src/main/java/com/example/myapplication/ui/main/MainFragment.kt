package com.example.myapplication.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.LocalTodoRepository
import com.example.myapplication.data.local.Database
import com.example.myapplication.databinding.MainFragmentBinding

class MainFragment : Fragment() {
	private var _binding: MainFragmentBinding? = null
	private val binding get() = _binding!!

	private lateinit var adapter: TodoAdapter
	private lateinit var recyclerView: RecyclerView

	private val viewModel by viewModels<MainViewModel> {
		val database = Database.getDb(requireContext())
		val todoRepository = LocalTodoRepository(database)
		MainViewModelFactory(todoRepository)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		observeTodos()
		setUI()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

		binding.lifecycleOwner = this
		binding.viewModel = viewModel

		recyclerView = binding.recyclerView

		Log.d("MainFragment", "hereeeeeeeeeeeeeeeeeeeeee")

		return binding.root
	}

	private fun observeTodos() {
		viewModel.todos.observe(viewLifecycleOwner) {
			Log.d("MainFragment", "OMGGGG ITTTT CHANGEEEEEEEEE")
			Log.d("MainFragment", "todos: $it")
			adapter.updateData(it)
			updateRecyclerViewPadding(it.isNotEmpty())
		}
	}

	private fun setUI() {
		binding.navigateToTodoPage.setOnClickListener {
			findNavController().navigate(R.id.action_mainFragment_to_addToDoFragment)
		}

		adapter = TodoAdapter(viewModel.todos.value ?: listOf())
		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(requireContext())
	}

	private fun updateRecyclerViewPadding(hasItems: Boolean) {
		val padding =
			if (hasItems) R.dimen.recycler_view_padding_with_items else R.dimen.recycler_view_padding_without_items
		val paddingValue = resources.getDimensionPixelSize(padding)
		recyclerView.setPadding(paddingValue, paddingValue, paddingValue, paddingValue)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}
