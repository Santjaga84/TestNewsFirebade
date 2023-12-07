package com.example.testnewsapp.ui.blog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testnewsapp.R
import com.example.testnewsapp.databinding.FragmentBlogBinding
import com.example.testnewsapp.databinding.FragmentSplashBinding


class BlogFragment : Fragment() {

    private var binding: FragmentBlogBinding? = null
    private val mBinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBlogBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }


}