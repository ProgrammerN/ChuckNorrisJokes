package com.dvt.chucknorrisjokes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dvt.chucknorrisjokes.databinding.JokeItemBinding
import com.dvt.chucknorrisjokes.extentions.share
import com.dvt.chucknorrisjokes.model.Joke
import com.dvt.chucknorrisjokes.util.RandomColor

/**
 * View Pager Adapter for the joke list
 */
class ViewPagerAdapter(private val condition: ConditionViewPager) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

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
        condition.condition(position, dataValue.size)
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

        fun bind(joke: Joke, position: Int, fullSize: Int) {
            binding.joke = joke
            binding.randomColor = RandomColor
            binding.viewPagerViewHolder = this@ViewPagerViewHolder
            binding.executePendingBindings()
            binding.apply {
                animationView.isVisible = fullSize > 1 && position + 1 < fullSize
            }
        }

        fun onShareAction(joke: Joke) {
            binding.root.context.share(joke)
        }

        fun onFavoriteAction(joke: Joke) {
            //TODO: add and remove from to favorites
        }
    }

    override fun getItemCount(): Int {
        if (::dataValue.isInitialized) {
            return dataValue.size
        }
        return 0
    }

    interface ConditionViewPager {
        fun condition(position: Int, fullSize: Int)
    }

}