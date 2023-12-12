package com.example.testnewsapp.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.testnewsapp.R
import com.example.testnewsapp.databinding.FragmentDetailsBinding
import com.example.testnewsapp.model.BlogPost


class DetailsFragment : Fragment() {

    private val detailsViewModel by activityViewModels<DetailsViewModel>()
    private var binding: FragmentDetailsBinding? = null
    private val mBinding get() = binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("MyLog", "DetailsFragment onCreateView")
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("MyLog", "DetailsFragment onViewCreated")

        detailsViewModel.liveDataBlogPost.observe(viewLifecycleOwner) { selectedBlogPost ->
            // Используйте selectedBlogPost здесь для отображения данных
            detailsViewModel.setSelectedBlogPost(selectedBlogPost)

            Log.d("MyLog", "Observed selectedBlogPost: $selectedBlogPost")
            Glide.with(this).load(selectedBlogPost.image).into(mBinding.ivDetailsImage)
            mBinding.tvDetailsTitle.text = selectedBlogPost.category.name
            mBinding.tvTitle.text =selectedBlogPost.title
            mBinding.tvDetailsContent.text = selectedBlogPost.content

        }
    }
}