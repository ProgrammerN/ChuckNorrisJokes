package com.dvt.chucknorrisjokes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvt.chucknorrisjokes.adapters.JokeCategoryAdapter.OnItemClickListener
import com.dvt.chucknorrisjokes.databinding.CategoryItemBinding
import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.util.RandomColor
import javax.inject.Inject

/**
 * Adapter for the category list. Has a reference to the [OnItemClickListener] to listen to click events.
 */

class JokeCategoryAdapter @Inject constructor(private val listener: OnItemClickListener) : ListAdapter<Category, JokeCategoryAdapter.CategoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            binding.randomColor = RandomColor
            binding.categoryViewHolder = this@CategoryViewHolder
            binding.executePendingBindings()
        }
        fun onClickAction() {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val category = getItem(position)
                listener.onItemClick(category)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(category: Category)
    }

    class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem
    }
}