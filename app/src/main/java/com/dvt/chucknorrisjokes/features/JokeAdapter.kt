package com.dvt.chucknorrisjokes.features

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dvt.chucknorrisjokes.data.Joke
import com.dvt.chucknorrisjokes.data.SearchResult
import com.dvt.chucknorrisjokes.databinding.JokeItemBinding

class JokeAdapter() : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    private lateinit var mData: List<Joke>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val binding = JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        val currentItem = mData[position]
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        if (::mData.isInitialized) {
            return mData.size
        }
        return 0
    }

    fun setJokes(mData: List<Joke>) {
        this.mData = mData
        notifyDataSetChanged()
    }

    class JokeViewHolder(private val binding: JokeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(joke: Joke) {
            binding.apply {
                Glide.with(itemView)
                    .load(joke.iconUrl)
                    .into(imageViewIcon)
                textViewJoke.text = joke.value
            }
        }
    }
}