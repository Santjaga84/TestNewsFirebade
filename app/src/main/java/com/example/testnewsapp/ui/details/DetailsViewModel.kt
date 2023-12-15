package com.example.testnewsapp.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testnewsapp.dao.AppDatabase
import com.example.testnewsapp.dao.FavoriteBlogPost
import com.example.testnewsapp.model.BlogPost
import com.example.testnewsapp.repositoriy.FavoriteBlogRepository
import kotlinx.coroutines.launch

//private val repository: FavoriteBlogRepository
class DetailsViewModel(
    private val repository: FavoriteBlogRepository,
    private val appDatabase: AppDatabase
) : ViewModel() {

    private val mutableBlogPost = MutableLiveData<BlogPost>()
    val liveDataBlogPost: LiveData<BlogPost> get() = mutableBlogPost

    private val mutableIsFavorite = MutableLiveData<Boolean>()
    val mutableIsLiveFavorite: LiveData<Boolean> get() = mutableIsFavorite


    fun setSelectedBlogPost(blogPost: BlogPost) {
        mutableBlogPost.value = blogPost
        Log.d("MyLog", "setSelectedBlogPost: $blogPost")
    }

    private fun addToFavorites(blogPost: BlogPost) {
        viewModelScope.launch {
            if (!repository.isFavorite(blogPost.id.toString())) {
                repository.addToFavorites(blogPost)
                // Обновите состояние в вашем UI, если необходимо
            }
        }
    }

    private fun removeFromFavorites(blogId: String) {
        viewModelScope.launch {
            repository.removeFromFavorites(blogId)
            mutableIsFavorite.value = false
        }
    }

    private suspend fun isFavorite(blogId: String): Boolean {
        return repository.isFavorite(blogId)
    }

    fun toggleFavoriteStatus(blogPost: BlogPost) {
        viewModelScope.launch {
            val blogId = blogPost.id.toString()
            if (isFavorite(blogId)) {
                removeFromFavorites(blogId)
            } else {
                addToFavorites(blogPost)
            }
        }
    }
}