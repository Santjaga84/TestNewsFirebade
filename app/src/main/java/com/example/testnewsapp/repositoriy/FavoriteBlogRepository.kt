package com.example.testnewsapp.repositoriy

import FavoriteBlogPostDao
import com.example.testnewsapp.model.BlogPost

class FavoriteBlogRepository(private val favoriteBlogPostDao: FavoriteBlogPostDao) {

    suspend fun addToFavorites(blogPost: BlogPost) {
        val favoriteBlogPost = BlogPostConverter.toFavoriteBlogPost(blogPost)
        favoriteBlogPostDao.insert(favoriteBlogPost)
    }

    suspend fun removeFromFavorites(blogId: String) {
        // Реализуйте удаление из избранного по идентификатору блога, если необходимо
    }

    suspend fun isFavorite(blogId: String): Boolean {
        return favoriteBlogPostDao.isFavoriteBlogPost(blogId) != null
    }
}