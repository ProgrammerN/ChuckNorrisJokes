package com.dvt.chucknorrisjokes.features.display

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
import com.dvt.chucknorrisjokes.data.Category
import com.dvt.chucknorrisjokes.data.Joke
import com.dvt.chucknorrisjokes.databinding.FragmentJokesBinding
import com.dvt.chucknorrisjokes.extentions.onQueryTextChanged
import com.dvt.chucknorrisjokes.extentions.toast
import com.dvt.chucknorrisjokes.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JokesFragment : Fragment(R.layout.fragment_jokes), ViewPagerAdapter.ConditionViewPager {

    private val viewModel: JokesViewModel by viewModels()
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentJokesBinding.bind(view)
        val jokeAdapter = ViewPagerAdapter(this)

        binding.apply {
            viewPager.apply {
                adapter = jokeAdapter
                orientation = ViewPager2.ORIENTATION_VERTICAL
            }

            viewModel.joke.observe(viewLifecycleOwner) { result ->
                jokeAdapter.setJokes(listOf(result.data) as List<Joke>)
                progressBar.isVisible = result is Resource.Loading && result.data == null
                textViewError.isVisible = result is Resource.Error && result.data == null
                textViewError.text = result.error?.localizedMessage

                if (result.data != null) {
                    jokeAdapter.setJokes(listOf(result.data))
                }
            }

            viewModel.queryJokeResults.observe(viewLifecycleOwner) { result ->
                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textViewError.text = result.error?.localizedMessage

                if (result.data!!.isNullOrEmpty()) {
                    jokeAdapter.setJokes(result.data!!)
                }
            }
        }

        setFragmentResultListener("choose_category_request") { _, bundle ->
            val result = bundle.getParcelable<Category>("selected_category")
            if (result != null) {
                viewModel.searchCategory.value = result.category
            }

            binding.apply {
                viewModel.categoryJokesResult.observe(viewLifecycleOwner) { result ->
                    progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                    textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                    textViewError.text = result.error?.localizedMessage

                    if (result.data!!.isNullOrEmpty()) {
                        jokeAdapter.setJokes(result.data!!)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.categoryEvent.collect { event ->
                when (event) {
                    is JokesViewModel.CategoryEvent.NavigateToCategoriesScreen -> {
                        val action = JokesFragmentDirections.actionJokesFragmentToChooseCategoryFragment()

                        findNavController().navigate(action)
                    }
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun condition(position: Int, fullSize: Int) {
        if (position == fullSize) {
        }
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }
}