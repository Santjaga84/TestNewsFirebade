package com.example.testnewsapp.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testnewsapp.model.BlogPost

class DetailsViewModel : ViewModel() {
    private val mutableBlogPost = MutableLiveData<BlogPost>()
    val liveDataBlogPost: LiveData<BlogPost> get() = mutableBlogPost

    init {
        Log.d("MyLog", "DetailsViewModel created")
    }
    fun setSelectedBlogPost(blogPost: BlogPost) {
        mutableBlogPost.value = blogPost
     //   Log.d("MyLog", "setSelectedBlogPost: $blogPost")
    }
}