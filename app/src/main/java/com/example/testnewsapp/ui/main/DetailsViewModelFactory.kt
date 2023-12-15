package com.example.testnewsapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testnewsapp.repositoriy.FavoriteBlogRepository
import com.example.testnewsapp.ui.details.DetailsViewModel

class DetailsViewModelFactory(
    private val favoriteBlogRepository: FavoriteBlogRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(favoriteBlogRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}