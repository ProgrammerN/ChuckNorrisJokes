package com.dvt.chucknorrisjokes.features.display

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dvt.chucknorrisjokes.data.Joke
import com.dvt.chucknorrisjokes.databinding.JokeItemBinding
import com.dvt.chucknorrisjokes.util.RandomColor

/**
 * View Pager Adapter for a single jokes or list of jokes
 */
class ViewPagerAdapter(private val condition: ConditionViewPager) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    private lateinit var dataValue: List<Joke>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder =
        ViewPagerViewHolder(JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        if (dataValue[position] != null) {
            holder.bind(dataValue[position])
        }
        condition.condition(position, dataValue.size)
    }

    fun setJokes(mData: List<Joke>) {
        this.dataValue = mData
        notifyDataSetChanged()
    }

    inner class ViewPagerViewHolder(private val binding: JokeItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setBackgroundColor(
                    binding.root.context.resources.getColor(RandomColor.randomBackgroundColor()))
            }
        }

        fun bind(joke: Joke) {
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
                    //TODO: add to favorites
                }
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