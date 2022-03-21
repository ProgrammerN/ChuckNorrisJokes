package com.dvt.chucknorrisjokes.ui.features

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.dvt.chucknorrisjokes.R
import com.dvt.chucknorrisjokes.adapters.ViewPagerAdapter
import com.dvt.chucknorrisjokes.databinding.FragmentJokesBinding
import com.dvt.chucknorrisjokes.extentions.toast
import com.dvt.chucknorrisjokes.model.Joke
import com.dvt.chucknorrisjokes.viewmodel.JokesViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Display a grid of [Joke]s. User can swipe up to view more if there is any.
 */

@AndroidEntryPoint
class FavoriteJokesFragment : Fragment(R.layout.fragment_jokes), ViewPagerAdapter.ConditionViewPager {

    private val viewModel: JokesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentJokesBinding.bind(view)
        val jokeAdapter = ViewPagerAdapter(this, viewModel, viewLifecycleOwner)

        binding.apply {
            viewPager.apply {
                adapter = jokeAdapter
                orientation = ViewPager2.ORIENTATION_VERTICAL
            }

            viewModel.favoriteJokes().observe(viewLifecycleOwner) { favoriteJokes ->
                val jokes = mutableListOf<Joke>()
                favoriteJokes?.let {
                    it.listIterator().forEach { favoriteJoke ->
                        val joke = Joke(favoriteJoke.categories, favoriteJoke.createdAt, favoriteJoke.iconUrl, favoriteJoke.id, favoriteJoke.updatedAt, favoriteJoke.url, favoriteJoke.value)
                        jokes.add(joke)
                    }
                }
                jokeAdapter.setJokes(jokes.reversed())
            }
        }
        setHasOptionsMenu(true)
    }

    override fun condition(position: Int, fullSize: Int) {
        if (position == fullSize) {
            //TODO implement on swipe items finished
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_favorite_jokes, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                viewModel.deleteAllFavorites()
                context?.toast("All favorites deleted")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}