package com.dvt.chucknorrisjokes.ui.features

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.dvt.chucknorrisjokes.R
import com.dvt.chucknorrisjokes.adapters.ViewPagerAdapter
import com.dvt.chucknorrisjokes.databinding.FragmentJokesBinding
import com.dvt.chucknorrisjokes.extentions.onQueryTextChanged
import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.model.Joke
import com.dvt.chucknorrisjokes.util.Resource
import com.dvt.chucknorrisjokes.viewmodel.JokesViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * Display a grid of [Joke]s. User can swipe up to view more if there is any.
 */

@AndroidEntryPoint
class JokesFragment : Fragment(R.layout.fragment_jokes) {

    private val viewModel: JokesViewModel by viewModels()
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentJokesBinding.bind(view)
        val jokeAdapter = ViewPagerAdapter(viewModel, viewLifecycleOwner)

        binding.apply {
            viewPager.apply {
                adapter = jokeAdapter
                orientation = ViewPager2.ORIENTATION_VERTICAL
            }
            viewModel.joke.observe(viewLifecycleOwner) { result ->
                Timber.d("Status $result")

                result.data?.let {
                    jokeAdapter.setJoke(it)

                }
                progressBar.isVisible = result is Resource.Loading && result.data == null
                textViewError.isVisible = result is Resource.Error && result.data == null
                textViewError.text = result.error?.localizedMessage
            }

            viewModel.queryJokeResults.observe(viewLifecycleOwner) { result ->
                Timber.d("Status $result")
                result.data?.let {
                    jokeAdapter.setJokes(it)
                }
                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textViewError.text = result.error?.localizedMessage
            }
        }

        setFragmentResultListener("choose_category_request") { _, bundle ->
            val result = bundle.getParcelable<Category>("selected_category")
            if (result != null) {
                viewModel.searchCategory.value = result.category
                binding.apply {
                    viewModel.categoryJokesResult.observe(viewLifecycleOwner) { result ->
                        jokeAdapter.setJokes(result.data!!)
                        progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                        textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                        textViewError.text = result.error?.localizedMessage
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.jokesEvent.collect { event ->
                when (event) {
                    is JokesViewModel.JokesEvent.NavigateToCategoriesScreen -> {
                        val action = JokesFragmentDirections.actionJokesFragmentToChooseCategoryFragment()
                        findNavController().navigate(action)
                    }

                    is JokesViewModel.JokesEvent.NavigateToFavoriteJokesScreen -> {
                        val action = JokesFragmentDirections.actionJokesFragmentToFavoriteJokesFragment()
                        findNavController().navigate(action)
                    }
                }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_categories -> {
                viewModel.chooseCategoryClick()
                true
            }
            R.id.action_favorites -> {
                viewModel.goToFavoriteClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }

}