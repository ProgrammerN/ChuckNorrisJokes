package com.dvt.chucknorrisjokes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dvt.chucknorrisjokes.databinding.JokeItemBinding
import com.dvt.chucknorrisjokes.model.Joke

/**
 * Recycler view Adapter for the joke list
 */
class JokeRecyclerViewAdapter : RecyclerView.Adapter<JokeRecyclerViewAdapter.JokeViewHolder>() {

    private lateinit var mData: List<Joke>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        JokeViewHolder(JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

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

    class JokeViewHolder(private val binding: JokeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
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