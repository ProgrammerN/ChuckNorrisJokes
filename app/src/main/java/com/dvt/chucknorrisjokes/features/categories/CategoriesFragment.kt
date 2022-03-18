package com.dvt.chucknorrisjokes.features.categories

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dvt.chucknorrisjokes.R
import com.dvt.chucknorrisjokes.data.Category
import com.dvt.chucknorrisjokes.databinding.FragmentCategoriesBinding
import com.dvt.chucknorrisjokes.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment(R.layout.fragment_categories), CategoryAdapter.OnItemClickListener {

    private val viewModel: CategoriesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCategoriesBinding.bind(view)

        val categoryAdapter = CategoryAdapter(this)

        binding.apply {
            recyclerViewCategories.apply {
                adapter = categoryAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
                setHasFixedSize(true)
            }

            viewModel.categories.observe(viewLifecycleOwner) { categories ->
                categoryAdapter.submitList(categories.data)
                progressBar.isVisible = categories is Resource.Loading && categories.data == null
                textViewError.isVisible = categories is Resource.Error && categories.data == null
                textViewError.text = categories.error?.localizedMessage
            }
        }
    }

    override fun onItemClick(category: Category) {
        setFragmentResult(
            "choose_category_request",
            bundleOf("selected_category" to category)
        )
        findNavController().popBackStack()
    }
}