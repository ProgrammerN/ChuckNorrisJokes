package com.dvt.chucknorrisjokes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.dvt.chucknorrisjokes.R
import com.dvt.chucknorrisjokes.databinding.JokeItemBinding
import com.dvt.chucknorrisjokes.extentions.share
import com.dvt.chucknorrisjokes.extentions.toast
import com.dvt.chucknorrisjokes.model.FavoriteJoke
import com.dvt.chucknorrisjokes.model.Joke
import com.dvt.chucknorrisjokes.util.RandomColor
import com.dvt.chucknorrisjokes.viewmodel.JokesViewModel

/**
 * View Pager Adapter for the joke list
 */
class JokeViewPagerAdapter(private val viewModel: JokesViewModel, private val viewLifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<JokeViewPagerAdapter.ViewPagerViewHolder>() {

    private lateinit var dataValue: List<Joke>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder =
        ViewPagerViewHolder(
            JokeItemBinding.inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        if (dataValue[position] != null) {
            holder.bind(dataValue[position], position, dataValue.size)
        }
    }

    /**
     * Sets sets a list of jokes for display.
     * @param list a list of jokes
     */
    fun setJokes(list: List<Joke>) {
        this.dataValue = list
        notifyDataSetChanged()
    }

    /**
     * Sets one random joke for display.
     * @param joke single joke
     */
    fun setJoke(joke: Joke) {
        this.dataValue = listOf(joke)
        notifyDataSetChanged()
    }

    inner class ViewPagerViewHolder(private val binding: JokeItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            viewModel.isFavorite.observe(viewLifecycleOwner) {
                if (it) {
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorited)
                } else {
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                }

                binding.isFavorite = it
            }
        }

        fun bind(mJoke: Joke, mPosition: Int, mFullSize: Int) {
            binding.apply {
                joke = mJoke
                randomColor = RandomColor
                viewPagerViewHolder = this@ViewPagerViewHolder
                fullSize = mFullSize
                position = mPosition
                executePendingBindings()
            }
            viewModel.jokeId.value = mJoke.id
        }

        fun onShareAction(joke: Joke) {
            binding.root.context.share(joke)
        }

        fun onFavoriteAction(joke: Joke) {
            val favoriteJoke = FavoriteJoke(joke.categories, joke.createdAt, joke.iconUrl, joke.id, joke.updatedAt, joke.url, joke.value)
            viewModel.favoriteJoke(favoriteJoke)
            binding.root.context.toast("Joke added to favorites")
        }

        fun onRemoveFavoriteAction(joke: Joke) {
            val favoriteJoke = FavoriteJoke(joke.categories, joke.createdAt, joke.iconUrl, joke.id, joke.updatedAt, joke.url, joke.value)
            viewModel.removeFavoriteJoke(favoriteJoke)
            binding.root.context.toast("Joke removed from favorites")
        }
    }

    override fun getItemCount(): Int {
        if (::dataValue.isInitialized) {
            return dataValue.size
        }
        return 0
    }


}