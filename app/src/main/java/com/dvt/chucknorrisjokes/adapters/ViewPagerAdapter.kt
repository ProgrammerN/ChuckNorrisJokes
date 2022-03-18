package com.dvt.chucknorrisjokes.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dvt.chucknorrisjokes.model.Joke
import com.dvt.chucknorrisjokes.databinding.JokeItemBinding
import com.dvt.chucknorrisjokes.util.RandomColor

/**
 * View Pager Adapter for the joke list
 */
class ViewPagerAdapter(private val condition: ConditionViewPager) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    private lateinit var dataValue: List<Joke>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder =
        ViewPagerViewHolder(JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

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

        init {
            binding.apply {
                root.setBackgroundColor(
                    binding.root.context.resources.getColor(RandomColor.randomBackgroundColor())
                )
            }
        }

        fun bind(joke: Joke, position: Int, fullSize: Int) {
            binding.apply {
                Glide.with(itemView)
                    .load(joke.iconUrl)
                    .into(imageViewIcon)
                textViewJoke.text = joke.value

                fabShare.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, joke.value)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    binding.root.context.startActivity(shareIntent)
                }

                fabFavorite.setOnClickListener {
                    //TODO: add and remove from to favorites
                }

                animationView.isVisible = fullSize > 1 && position < fullSize
            }
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