package com.dvt.chucknorrisjokes.features

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvt.chucknorrisjokes.R
import com.dvt.chucknorrisjokes.data.Joke
import com.dvt.chucknorrisjokes.databinding.FragmentJokesBinding
import com.dvt.chucknorrisjokes.extentions.onQueryTextChanged
import com.dvt.chucknorrisjokes.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JokesFragment : Fragment(R.layout.fragment_jokes) {

    private val viewModel: JokesViewModel by viewModels()

    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentJokesBinding.bind(view)
        val jokeAdapter = JokeAdapter()

        binding.apply {
            recyclerView.apply {
                adapter = jokeAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }

            viewModel.jokes.observe(viewLifecycleOwner) { result ->
                jokeAdapter.setJokes(listOf(result.data) as List<Joke>)
                progressBar.isVisible = result is Resource.Loading && result.data == null
                textViewError.isVisible = result is Resource.Error && result.data == null
                textViewError.text = result.error?.localizedMessage
            }

            viewModel.queryJokeResults.observe(viewLifecycleOwner) { result ->
                jokeAdapter.setJokes(result.data!!)
                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textViewError.text = result.error?.localizedMessage
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_jokes, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }
    }
}