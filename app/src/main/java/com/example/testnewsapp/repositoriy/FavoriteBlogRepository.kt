package com.example.testnewsapp.repositoriy

import android.content.Context
import com.example.testnewsapp.dao.AppDatabase
import com.example.testnewsapp.model.BlogPost

class FavoriteBlogRepository(context: Context) {

    private val favoriteBlogPostDao by lazy { AppDatabase.getDatabase(context).favoriteBlogPostDao() }

    suspend fun addToFavorites(blogPost: BlogPost) {
        val favoriteBlogPost = AppDatabase.toFavoriteBlogPost(blogPost)
        favoriteBlogPostDao.insert(favoriteBlogPost)
    }

    suspend fun removeFromFavorites(blogId: String) {
        // Реализуйте удаление из избранного по идентификатору блога, если необходимо
    }

    suspend fun isFavorite(blogId: String): Boolean {
        return favoriteBlogPostDao.isFavoriteBlogPost(blogId) != null
    }

    suspend fun getFavoriteBlogs() = favoriteBlogPostDao.getAllFavorite()
}